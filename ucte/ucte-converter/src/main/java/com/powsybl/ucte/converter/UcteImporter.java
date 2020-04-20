/**
 * Copyright (c) 2016, All partners of the iTesla project (http://www.itesla-project.eu/consortium)
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.powsybl.ucte.converter;

import com.google.auto.service.AutoService;
import com.google.common.base.Enums;
import com.google.common.base.Joiner;
import com.google.common.base.Stopwatch;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.google.common.io.ByteStreams;
import com.powsybl.commons.datasource.DataSource;
import com.powsybl.commons.datasource.ReadOnlyDataSource;
import com.powsybl.entsoe.util.*;
import com.powsybl.iidm.import_.Importer;
import com.powsybl.iidm.network.*;
import com.powsybl.ucte.network.*;
import com.powsybl.ucte.network.ext.UcteNetworkExt;
import com.powsybl.ucte.network.ext.UcteSubstation;
import com.powsybl.ucte.network.ext.UcteVoltageLevel;
import com.powsybl.ucte.network.io.UcteReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.powsybl.ucte.converter.util.UcteConstants.*;

/**
 * @author Geoffroy Jamgotchian <geoffroy.jamgotchian at rte-france.com>
 */
@AutoService(Importer.class)
public class UcteImporter implements Importer {

    private static final Logger LOGGER = LoggerFactory.getLogger(UcteImporter.class);

    private static final float LINE_MIN_Z = 0.05f;

    private static final String[] EXTENSIONS = {"uct", "UCT"};

    private static float getConductance(UcteTransformer ucteTransfo) {
        float g = 0;
        if (!Float.isNaN(ucteTransfo.getConductance())) {
            g = ucteTransfo.getConductance();
        }
        return g;
    }

    private static float getSusceptance(UcteElement ucteElement) {
        float b = 0;
        if (!Float.isNaN(ucteElement.getSusceptance())) {
            b = ucteElement.getSusceptance();
        }
        return b;
    }

    /**
     * If the substation has a more specific geographical information than just its country,
     * returns the corresponding geographical code, otherwise null.
     */
    private static EntsoeGeographicalCode getRegionalGeographicalCode(Substation substation) {
        //Currently only DE has subregions
        if (substation.getCountry().map(country -> country != Country.DE).orElse(true)) {
            return null;
        }
        EntsoeGeographicalCode res = Enums.getIfPresent(EntsoeGeographicalCode.class, substation.getNameOrId().substring(0, 2)).orNull();
        //handle case where a D-node would start with DE ...
        return res == EntsoeGeographicalCode.DE ? null : res;
    }

    private static void createBuses(UcteNetworkExt ucteNetwork, UcteVoltageLevel ucteVoltageLevel, VoltageLevel voltageLevel) {
        for (UcteNodeCode ucteNodeCode : ucteVoltageLevel.getNodes()) {
            UcteNode ucteNode = ucteNetwork.getNode(ucteNodeCode);

            // skip Xnodes
            if (ucteNode.getCode().getUcteCountryCode() == UcteCountryCode.XX) {
                continue;
            }

            if (LOGGER.isTraceEnabled()) {
                LOGGER.trace("Create bus '{}'", ucteNodeCode);
            }

            Bus bus = voltageLevel.getBusBreakerView().newBus()
                    .setId(ucteNodeCode.toString())
                    .add();

            addGeographicalNameProperty(ucteNode, bus);
            bus.setFictitious(UcteNodeStatus.EQUIVALENT == ucteNode.getStatus());

            if (isValueValid(ucteNode.getActiveLoad()) || isValueValid(ucteNode.getReactiveLoad())) {
                createLoad(ucteNode, voltageLevel, bus);
            }

            if (ucteNode.isGenerator()) {
                createGenerator(ucteNode, voltageLevel, bus);
            }
        }
    }

    private static void createBuses(UcteNetworkExt ucteNetwork, Network network) {
        for (UcteSubstation ucteSubstation : ucteNetwork.getSubstations()) {

            // skip substations with only one Xnode
            UcteNodeCode firstUcteNodeCode = ucteSubstation.getNodes().stream()
                    .filter(code -> code.getUcteCountryCode() != UcteCountryCode.XX)
                    .findFirst()
                    .orElse(null);
            if (firstUcteNodeCode == null) {
                continue;
            }

            LOGGER.trace("Create substation '{}'", ucteSubstation.getName());

            Substation substation = network.newSubstation()
                    .setId(ucteSubstation.getName())
                    .setCountry(Country.valueOf(firstUcteNodeCode.getUcteCountryCode().name()))
                    .add();

            EntsoeGeographicalCode regionalCode = getRegionalGeographicalCode(substation);
            if (regionalCode != null) {
                substation.addExtension(EntsoeArea.class, new EntsoeArea(substation, regionalCode));
            }

            for (UcteVoltageLevel ucteVoltageLevel : ucteSubstation.getVoltageLevels()) {
                UcteVoltageLevelCode ucteVoltageLevelCode = ucteVoltageLevel.getNodes().iterator().next().getVoltageLevelCode();

                LOGGER.trace("Create voltage level '{}'", ucteVoltageLevel.getName());

                VoltageLevel voltageLevel = substation.newVoltageLevel()
                        .setId(ucteVoltageLevel.getName())
                        .setNominalV(ucteVoltageLevelCode.getVoltageLevel())
                        .setTopologyKind(TopologyKind.BUS_BREAKER)
                        .add();

                createBuses(ucteNetwork, ucteVoltageLevel, voltageLevel);
            }
        }
    }

    private static boolean isValueValid(float value) {
        return !Float.isNaN(value) && value != 0;
    }

    private static void createLoad(UcteNode ucteNode, VoltageLevel voltageLevel, Bus bus) {
        String loadId = bus.getId() + "_load";

        LOGGER.trace("Create load '{}'", loadId);

        float p0 = 0;
        if (isValueValid(ucteNode.getActiveLoad())) {
            p0 = ucteNode.getActiveLoad();
        }
        float q0 = 0;
        if (isValueValid(ucteNode.getReactiveLoad())) {
            q0 = ucteNode.getReactiveLoad();
        }

        voltageLevel.newLoad()
                .setId(loadId)
                .setBus(bus.getId())
                .setConnectableBus(bus.getId())
                .setP0(p0)
                .setQ0(q0)
                .add();
    }

    private static void createGenerator(UcteNode ucteNode, VoltageLevel voltageLevel, Bus bus) {
        String generatorId = bus.getId() + "_generator";

        LOGGER.trace("Create generator '{}'", generatorId);

        EnergySource energySource = EnergySource.OTHER;
        if (ucteNode.getPowerPlantType() != null) {
            switch (ucteNode.getPowerPlantType()) {
                case C:
                case G:
                case L:
                case O:
                    energySource = EnergySource.THERMAL;
                    break;
                case H:
                    energySource = EnergySource.HYDRO;
                    break;
                case N:
                    energySource = EnergySource.NUCLEAR;
                    break;
                case W:
                    energySource = EnergySource.WIND;
                    break;
                case F:
                    energySource = EnergySource.OTHER;
                    break;
                default:
                    throw new AssertionError("Unexpected UctePowerPlantType value: " + ucteNode.getPowerPlantType());
            }
        }

        Generator generator = voltageLevel.newGenerator()
                .setId(generatorId)
                .setEnergySource(energySource)
                .setBus(bus.getId())
                .setConnectableBus(bus.getId())
                .setMinP(-ucteNode.getMinimumPermissibleActivePowerGeneration())
                .setMaxP(-ucteNode.getMaximumPermissibleActivePowerGeneration())
                .setVoltageRegulatorOn(ucteNode.isRegulatingVoltage())
                .setTargetP(-ucteNode.getActivePowerGeneration())
                .setTargetQ(-ucteNode.getReactivePowerGeneration())
                .setTargetV(ucteNode.getVoltageReference())
                .add();
        generator.newMinMaxReactiveLimits()
                .setMinQ(-ucteNode.getMinimumPermissibleReactivePowerGeneration())
                .setMaxQ(-ucteNode.getMaximumPermissibleReactivePowerGeneration())
                .add();
    }

    private static void createDanglingLine(UcteLine ucteLine, boolean connected,
                                           UcteNode xnode, UcteNodeCode nodeCode, UcteVoltageLevel ucteVoltageLevel,
                                           Network network) {

        LOGGER.trace("Create dangling line '{}' (Xnode='{}')", ucteLine.getId(), xnode.getCode());

        float p0 = 0;
        if (isValueValid(xnode.getActiveLoad())) {
            p0 += xnode.getActiveLoad();
        }
        if (isValueValid(xnode.getActivePowerGeneration())) {
            p0 += xnode.getActivePowerGeneration();
        }
        float q0 = 0;
        if (isValueValid(xnode.getReactiveLoad())) {
            q0 += xnode.getReactiveLoad();
        }
        if (isValueValid(xnode.getReactivePowerGeneration())) {
            q0 += xnode.getReactivePowerGeneration();
        }

        VoltageLevel voltageLevel = network.getVoltageLevel(ucteVoltageLevel.getName());
        DanglingLine dl = voltageLevel.newDanglingLine()
                .setId(ucteLine.getId().toString())
                .setBus(connected ? nodeCode.toString() : null)
                .setConnectableBus(nodeCode.toString())
                .setR(ucteLine.getResistance())
                .setX(ucteLine.getReactance())
                .setG(0f)
                .setB(getSusceptance(ucteLine))
                .setP0(p0)
                .setQ0(q0)
                .setUcteXnodeCode(xnode.getCode().toString())
                .add();
        dl.newExtension(XnodeAdder.class).withCode(xnode.getCode().toString()).add();

        if (ucteLine.getCurrentLimit() != null) {
            dl.newCurrentLimits()
                    .setPermanentLimit(ucteLine.getCurrentLimit())
                    .add();
        }

        addElementNameProperty(ucteLine, dl);
        addGeographicalNameProperty(xnode, dl);
    }

    private static void createCoupler(UcteNetworkExt ucteNetwork, Network network,
                                      UcteLine ucteLine,
                                      UcteNodeCode nodeCode1, UcteNodeCode nodeCode2,
                                      UcteVoltageLevel ucteVoltageLevel1, UcteVoltageLevel ucteVoltageLevel2) {
        LOGGER.trace("Create coupler '{}'", ucteLine.getId());

        if (ucteVoltageLevel1 != ucteVoltageLevel2) {
            throw new UcteException("Coupler between two different voltage levels");
        }

        boolean connected = ucteLine.getStatus() == UcteElementStatus.BUSBAR_COUPLER_IN_OPERATION;

        if (nodeCode1.getUcteCountryCode() == UcteCountryCode.XX &&
                nodeCode2.getUcteCountryCode() != UcteCountryCode.XX) {
            // coupler connected to a XNODE (side 1)
            createDanglingLine(ucteLine, connected, ucteNetwork.getNode(nodeCode1), nodeCode2, ucteVoltageLevel2, network);
        } else if (nodeCode2.getUcteCountryCode() == UcteCountryCode.XX &&
                nodeCode1.getUcteCountryCode() != UcteCountryCode.XX) {
            // coupler connected to a XNODE (side 2)
            createDanglingLine(ucteLine, connected, ucteNetwork.getNode(nodeCode2), nodeCode1, ucteVoltageLevel1, network);
        } else {
            double z = Math.hypot(ucteLine.getResistance(), ucteLine.getReactance());
            createCouplerFromLowImpedanceLine(network, ucteLine, nodeCode1, nodeCode2, ucteVoltageLevel1, ucteVoltageLevel2, connected, z);
        }
    }

    private static void createCouplerFromLowImpedanceLine(Network network, UcteLine ucteLine,
                                                          UcteNodeCode nodeCode1, UcteNodeCode nodeCode2,
                                                          UcteVoltageLevel ucteVoltageLevel1, UcteVoltageLevel ucteVoltageLevel2,
                                                          boolean connected, double z) {
        LOGGER.trace("Create coupler '{}' from low impedance line ({})", ucteLine.getId(), z);

        if (ucteVoltageLevel1 != ucteVoltageLevel2) {
            throw new UcteException("Nodes coupled with a low impedance line are expected to be in the same voltage level");
        }
        VoltageLevel voltageLevel = network.getVoltageLevel(ucteVoltageLevel1.getName());
        Switch couplerSwitch = voltageLevel.getBusBreakerView().newSwitch()
                .setEnsureIdUnicity(true)
                .setId(ucteLine.getId().toString())
                .setBus1(nodeCode1.toString())
                .setBus2(nodeCode2.toString())
                .setOpen(!connected)
                .add();

        addCurrentLimitProperty(ucteLine, couplerSwitch);
        addOrderCodeProperty(ucteLine, couplerSwitch);
        addElementNameProperty(ucteLine, couplerSwitch);
    }

    private static void createStandardLine(Network network, UcteLine ucteLine, UcteNodeCode nodeCode1, UcteNodeCode nodeCode2,
                                           UcteVoltageLevel ucteVoltageLevel1, UcteVoltageLevel ucteVoltageLevel2,
                                           boolean connected) {
        LOGGER.trace("Create line '{}'", ucteLine.getId());

        Line l = network.newLine()
                .setEnsureIdUnicity(true)
                .setId(ucteLine.getId().toString())
                .setVoltageLevel1(ucteVoltageLevel1.getName())
                .setVoltageLevel2(ucteVoltageLevel2.getName())
                .setBus1(connected ? nodeCode1.toString() : null)
                .setBus2(connected ? nodeCode2.toString() : null)
                .setConnectableBus1(nodeCode1.toString())
                .setConnectableBus2(nodeCode2.toString())
                .setR(ucteLine.getResistance())
                .setX(ucteLine.getReactance())
                .setG1(0f)
                .setG2(0f)
                .setB1(getSusceptance(ucteLine) / 2)
                .setB2(getSusceptance(ucteLine) / 2)
                .add();

        addElementNameProperty(ucteLine, l);

        if (ucteLine.getCurrentLimit() != null) {
            int currentLimit = ucteLine.getCurrentLimit();
            l.newCurrentLimits1()
                    .setPermanentLimit(currentLimit)
                    .add();
            l.newCurrentLimits2()
                    .setPermanentLimit(currentLimit)
                    .add();
        }
    }

    private static void createLine(UcteNetworkExt ucteNetwork, Network network,
                                   UcteLine ucteLine,
                                   UcteNodeCode nodeCode1, UcteNodeCode nodeCode2,
                                   UcteVoltageLevel ucteVoltageLevel1, UcteVoltageLevel ucteVoltageLevel2) {
        boolean connected = ucteLine.getStatus() == UcteElementStatus.REAL_ELEMENT_IN_OPERATION
                || ucteLine.getStatus() == UcteElementStatus.EQUIVALENT_ELEMENT_IN_OPERATION;

        double z = Math.hypot(ucteLine.getResistance(), ucteLine.getReactance());

        if (z < LINE_MIN_Z
                && nodeCode1.getUcteCountryCode() != UcteCountryCode.XX
                && nodeCode2.getUcteCountryCode() != UcteCountryCode.XX) {

            createCouplerFromLowImpedanceLine(network, ucteLine, nodeCode1, nodeCode2, ucteVoltageLevel1, ucteVoltageLevel2, connected, z);
        } else {

            if (nodeCode1.getUcteCountryCode() != UcteCountryCode.XX
                    && nodeCode2.getUcteCountryCode() != UcteCountryCode.XX) {

                createStandardLine(network, ucteLine, nodeCode1, nodeCode2, ucteVoltageLevel1, ucteVoltageLevel2, connected);

            } else if (nodeCode1.getUcteCountryCode() == UcteCountryCode.XX
                    && nodeCode2.getUcteCountryCode() != UcteCountryCode.XX) {

                UcteNode xnode = ucteNetwork.getNode(nodeCode1);

                createDanglingLine(ucteLine, connected, xnode, nodeCode2, ucteVoltageLevel2, network);

            } else if (nodeCode1.getUcteCountryCode() != UcteCountryCode.XX
                    && nodeCode2.getUcteCountryCode() == UcteCountryCode.XX) {

                UcteNode xnode = ucteNetwork.getNode(nodeCode2);

                createDanglingLine(ucteLine, connected, xnode, nodeCode1, ucteVoltageLevel1, network);

            } else {
                throw new UcteException("Line between 2 Xnodes");
            }
        }
    }

    private static void createLines(UcteNetworkExt ucteNetwork, Network network) {
        for (UcteLine ucteLine : ucteNetwork.getLines()) {
            UcteNodeCode nodeCode1 = ucteLine.getId().getNodeCode1();
            UcteNodeCode nodeCode2 = ucteLine.getId().getNodeCode2();
            UcteVoltageLevel ucteVoltageLevel1 = ucteNetwork.getVoltageLevel(nodeCode1);
            UcteVoltageLevel ucteVoltageLevel2 = ucteNetwork.getVoltageLevel(nodeCode2);

            switch (ucteLine.getStatus()) {
                case BUSBAR_COUPLER_IN_OPERATION:
                case BUSBAR_COUPLER_OUT_OF_OPERATION:
                    createCoupler(ucteNetwork, network, ucteLine, nodeCode1, nodeCode2, ucteVoltageLevel1, ucteVoltageLevel2);
                    break;

                case REAL_ELEMENT_IN_OPERATION:
                case REAL_ELEMENT_OUT_OF_OPERATION:
                case EQUIVALENT_ELEMENT_IN_OPERATION:
                case EQUIVALENT_ELEMENT_OUT_OF_OPERATION:
                    createLine(ucteNetwork, network, ucteLine, nodeCode1, nodeCode2, ucteVoltageLevel1, ucteVoltageLevel2);
                    break;

                default:
                    throw new AssertionError("Unexpected UcteElementStatus value: " + ucteLine.getStatus());
            }
        }
    }

    private static void createRatioTapChanger(UctePhaseRegulation uctePhaseRegulation, TwoWindingsTransformer transformer) {

        LOGGER.trace("Create ratio tap changer '{}'", transformer.getId());

        RatioTapChangerAdder rtca = transformer.newRatioTapChanger()
                .setLowTapPosition(-uctePhaseRegulation.getN())
                .setTapPosition(uctePhaseRegulation.getNp())
                .setLoadTapChangingCapabilities(!Float.isNaN(uctePhaseRegulation.getU()));
        if (!Float.isNaN(uctePhaseRegulation.getU())) {
            rtca.setLoadTapChangingCapabilities(true)
                    .setRegulating(true)
                    .setTargetV(uctePhaseRegulation.getU())
                    .setTargetDeadband(0.0)
                    .setRegulationTerminal(transformer.getTerminal1());
        }
        for (int i = -uctePhaseRegulation.getN(); i <= uctePhaseRegulation.getN(); i++) {
            float rho = 1 / (1 + i * uctePhaseRegulation.getDu() / 100f);
            rtca.beginStep()
                    .setRho(rho)
                    .setR(0f)
                    .setX(0f)
                    .setG(0f)
                    .setB(0f)
                    .endStep();
        }
        rtca.add();
    }

    private static void createPhaseTapChanger(UcteAngleRegulation ucteAngleRegulation, TwoWindingsTransformer transformer) {

        LOGGER.trace("Create phase tap changer '{}'", transformer.getId());

        PhaseTapChangerAdder ptca = transformer.newPhaseTapChanger()
                .setLowTapPosition(-ucteAngleRegulation.getN())
                .setTapPosition(ucteAngleRegulation.getNp())
                .setRegulationValue(ucteAngleRegulation.getP())
                .setRegulationMode(PhaseTapChanger.RegulationMode.FIXED_TAP);

        for (int i = -ucteAngleRegulation.getN(); i <= ucteAngleRegulation.getN(); i++) {
            float rho;
            float alpha;
            double dx = i * ucteAngleRegulation.getDu() / 100f * Math.cos(Math.toRadians(ucteAngleRegulation.getTheta()));
            double dy = i * ucteAngleRegulation.getDu() / 100f * Math.sin(Math.toRadians(ucteAngleRegulation.getTheta()));
            switch (ucteAngleRegulation.getType()) {
                case ASYM:
                    rho = (float) (1 / Math.hypot(dy, 1 + dx));
                    alpha = (float) Math.toDegrees(Math.atan2(dy, 1 + dx));
                    break;

                case SYMM:
                    rho = 1f;
                    alpha = (float) Math.toDegrees(2 * Math.atan2(dy, 2f * (1 + dx)));
                    break;

                default:
                    throw new AssertionError("Unexpected UcteAngleRegulationType value: " + ucteAngleRegulation.getType());
            }
            ptca.beginStep()
                    .setRho(rho)
                    .setAlpha(-alpha) // minus because in the UCT model PST is on side 2 and side1 on IIDM model
                    .setR(0f)
                    .setX(0f)
                    .setG(0f)
                    .setB(0f)
                    .endStep();
        }
        ptca.add();
    }

    private static TwoWindingsTransformer createXnodeTransfo(UcteNetworkExt ucteNetwork, UcteTransformer ucteTransfo, boolean connected,
                                                             UcteNodeCode xNodeCode, UcteNodeCode ucteOtherNodeCode, UcteVoltageLevel ucteOtherVoltageLevel,
                                                             Substation substation, EntsoeFileName ucteFileName) {
        // transfo connected to a XNODE, create an intermediate YNODE and small impedance line
        // otherNode--transfo--XNODE => otherNode--transfo--YNODE--line--XNODE
        String xNodeName = xNodeCode.toString();
        String yNodeName = ucteFileName.getCountry() != null ? ucteFileName.getCountry() + "_" + xNodeName : "YNODE_" + xNodeName;

        VoltageLevel yVoltageLevel = substation.newVoltageLevel()
                .setId(yNodeName + "_VL")
                .setNominalV(xNodeCode.getVoltageLevelCode().getVoltageLevel()) // nominal voltage of the XNODE
                .setTopologyKind(TopologyKind.BUS_BREAKER)
                .add();

        yVoltageLevel.getBusBreakerView().newBus()
                .setId(yNodeName)
                .add();

        UcteNode ucteXnode = ucteNetwork.getNode(xNodeCode);

        LOGGER.warn("Create small impedance dangling line '{}{}' (transformer connected to XNODE '{}')",
                xNodeName, yNodeName, ucteXnode.getCode());

        float p0 = 0;
        if (isValueValid(ucteXnode.getActiveLoad())) {
            p0 += ucteXnode.getActiveLoad();
        }
        if (isValueValid(ucteXnode.getActivePowerGeneration())) {
            p0 += ucteXnode.getActivePowerGeneration();
        }
        float q0 = 0;
        if (isValueValid(ucteXnode.getReactiveLoad())) {
            q0 += ucteXnode.getReactiveLoad();
        }
        if (isValueValid(ucteXnode.getReactivePowerGeneration())) {
            q0 += ucteXnode.getReactivePowerGeneration();
        }

        // create a small impedance dangling line connected to the YNODE
        DanglingLine yDanglingLine = yVoltageLevel.newDanglingLine()
                .setId(xNodeName + " " + yNodeName)
                .setBus(yNodeName)
                .setConnectableBus(yNodeName)
                .setR(0.0f)
                .setX(LINE_MIN_Z)
                .setG(0f)
                .setB(0f)
                .setP0(p0)
                .setQ0(q0)
                .setUcteXnodeCode(ucteXnode.getCode().toString())
                .add();
        yDanglingLine.newExtension(XnodeAdder.class).withCode(ucteXnode.getCode().toString()).add();

        String voltageLevelId1;
        String voltageLevelId2;
        String busId1;
        String busId2;
        if (ucteXnode.getCode().equals(ucteTransfo.getId().getNodeCode1())) {
            voltageLevelId1 = ucteOtherVoltageLevel.getName();
            voltageLevelId2 = yVoltageLevel.getId();
            busId1 = ucteOtherNodeCode.toString();
            busId2 = yNodeName;
        } else {
            voltageLevelId1 = yVoltageLevel.getId();
            voltageLevelId2 = ucteOtherVoltageLevel.getName();
            busId1 = yNodeName;
            busId2 = ucteOtherNodeCode.toString();
        }

        // create a transformer connected to the YNODE and other node
        return substation.newTwoWindingsTransformer()
                .setEnsureIdUnicity(true)
                .setId(ucteTransfo.getId().toString())
                .setVoltageLevel1(voltageLevelId1)
                .setVoltageLevel2(voltageLevelId2)
                .setBus1(connected ? busId1 : null)
                .setBus2(connected ? busId2 : null)
                .setConnectableBus1(busId1)
                .setConnectableBus2(busId2)
                .setRatedU1(ucteTransfo.getRatedVoltage2())
                .setRatedU2(ucteTransfo.getRatedVoltage1())
                .setR(ucteTransfo.getResistance())
                .setX(ucteTransfo.getReactance())
                .setG(getConductance(ucteTransfo))
                .setB(getSusceptance(ucteTransfo))
                .add();

    }

    private static boolean isConnected(UcteTransformer ucteTransfo) {
        boolean connected;
        switch (ucteTransfo.getStatus()) {
            case REAL_ELEMENT_IN_OPERATION:
            case EQUIVALENT_ELEMENT_IN_OPERATION:
                connected = true;
                break;

            case REAL_ELEMENT_OUT_OF_OPERATION:
            case EQUIVALENT_ELEMENT_OUT_OF_OPERATION:
                connected = false;
                break;

            default:
                throw new AssertionError("Unexpected UcteElementStatus value: " + ucteTransfo.getStatus());
        }
        return connected;
    }

    private static void addTapChangers(UcteNetworkExt ucteNetwork, UcteTransformer ucteTransfo, TwoWindingsTransformer transformer) {
        UcteRegulation ucteRegulation = ucteNetwork.getRegulation(ucteTransfo.getId());
        if (ucteRegulation != null) {
            if (ucteRegulation.getPhaseRegulation() != null) {
                createRatioTapChanger(ucteRegulation.getPhaseRegulation(), transformer);
            }
            if (ucteRegulation.getAngleRegulation() != null) {
                createPhaseTapChanger(ucteRegulation.getAngleRegulation(), transformer);
            }
        }
    }

    private static void createTransformers(UcteNetworkExt ucteNetwork, Network network, EntsoeFileName ucteFileName) {
        for (UcteTransformer ucteTransfo : ucteNetwork.getTransformers()) {
            UcteNodeCode nodeCode1 = ucteTransfo.getId().getNodeCode1();
            UcteNodeCode nodeCode2 = ucteTransfo.getId().getNodeCode2();
            UcteVoltageLevel ucteVoltageLevel1 = ucteNetwork.getVoltageLevel(nodeCode1);
            UcteVoltageLevel ucteVoltageLevel2 = ucteNetwork.getVoltageLevel(nodeCode2);
            UcteSubstation ucteSubstation = ucteVoltageLevel1.getSubstation();
            Substation substation = network.getSubstation(ucteSubstation.getName());

            LOGGER.trace("Create transformer '{}'", ucteTransfo.getId());

            boolean connected = isConnected(ucteTransfo);

            TwoWindingsTransformer transformer;

            if (nodeCode1.getUcteCountryCode() == UcteCountryCode.XX &&
                    nodeCode2.getUcteCountryCode() != UcteCountryCode.XX) {
                // transformer connected to XNODE
                transformer = createXnodeTransfo(ucteNetwork, ucteTransfo, connected, nodeCode1, nodeCode2, ucteVoltageLevel2, substation, ucteFileName);

            } else if (nodeCode2.getUcteCountryCode() == UcteCountryCode.XX &&
                    nodeCode1.getUcteCountryCode() != UcteCountryCode.XX) {
                // transformer connected to XNODE
                transformer = createXnodeTransfo(ucteNetwork, ucteTransfo, connected, nodeCode2, nodeCode1, ucteVoltageLevel1, substation, ucteFileName);

            } else {
                // standard transformer
                transformer = substation.newTwoWindingsTransformer()
                        .setEnsureIdUnicity(true)
                        .setId(ucteTransfo.getId().toString())
                        .setVoltageLevel1(ucteVoltageLevel2.getName())
                        .setVoltageLevel2(ucteVoltageLevel1.getName())
                        .setBus1(connected ? nodeCode2.toString() : null)
                        .setBus2(connected ? nodeCode1.toString() : null)
                        .setConnectableBus1(nodeCode2.toString())
                        .setConnectableBus2(nodeCode1.toString())
                        .setRatedU1(ucteTransfo.getRatedVoltage2())
                        .setRatedU2(ucteTransfo.getRatedVoltage1())
                        .setR(ucteTransfo.getResistance())
                        .setX(ucteTransfo.getReactance())
                        .setG(getConductance(ucteTransfo))
                        .setB(getSusceptance(ucteTransfo))
                        .add();

            }

            if (ucteTransfo.getCurrentLimit() != null) {
                int currentLimit = ucteTransfo.getCurrentLimit();
                transformer.newCurrentLimits2()
                        .setPermanentLimit(currentLimit)
                        .add();
            }

            addElementNameProperty(ucteTransfo, transformer);
            addTapChangers(ucteNetwork, ucteTransfo, transformer);
            addNominalPowerProperty(ucteTransfo, transformer);

        }

    }

    private static String getBusId(Bus bus) {
        return bus != null ? bus.getId() : null;
    }

    private static DanglingLine getMatchingDanglingLine(DanglingLine dl1, Multimap<String, DanglingLine> danglingLinesByXnodeCode) {
        DanglingLine dl2 = null;
        Xnode xnodExtension = dl1.getExtension(Xnode.class);
        if (xnodExtension == null) {
            throw new UcteException("Dangling line " + dl1.getNameOrId() + " doesn't have the Xnode extension");
        }
        String otherXnodeCode = xnodExtension.getCode();
        Iterator<DanglingLine> it = danglingLinesByXnodeCode.get(otherXnodeCode).iterator();
        DanglingLine first = it.next();
        if (it.hasNext()) {
            DanglingLine second = it.next();
            if (dl1 == first) {
                dl2 = second;
            } else if (dl1 == second) {
                dl2 = first;
            } else {
                throw new AssertionError("Inconsistent XNODE index");
            }
            if (it.hasNext()) {
                throw new UcteException("More that 2 dangling lines have the same XNODE " + dl1.getUcteXnodeCode());
            }
        }
        return dl2;
    }

    private static void addElementNameProperty(TieLine tieLine, DanglingLine dl1, DanglingLine dl2) {
        if (dl1.hasProperty(ELEMENT_NAME_PROPERTY_KEY)) {
            tieLine.setProperty(ELEMENT_NAME_PROPERTY_KEY + "_1", dl1.getProperty(ELEMENT_NAME_PROPERTY_KEY));
        }

        if (dl2.hasProperty(ELEMENT_NAME_PROPERTY_KEY)) {
            tieLine.setProperty(ELEMENT_NAME_PROPERTY_KEY + "_2", dl2.getProperty(ELEMENT_NAME_PROPERTY_KEY));
        }
    }

    private static void addElementNameProperty(UcteElement ucteElement, Identifiable identifiable) {
        if (ucteElement.getElementName() != null) {
            identifiable.setProperty(ELEMENT_NAME_PROPERTY_KEY, ucteElement.getElementName());
        }
    }

    private static void addCurrentLimitProperty(UcteLine ucteLine, Switch aSwitch) {
        if (ucteLine.getCurrentLimit() != null) {
            aSwitch.setProperty(CURRENT_LIMIT_PROPERTY_KEY, String.valueOf(ucteLine.getCurrentLimit()));
        }
    }

    private static void addGeographicalNameProperty(UcteNode ucteNode, Identifiable identifiable) {
        if (ucteNode.getGeographicalName() != null) {
            identifiable.setProperty(GEOGRAPHICAL_NAME_PROPERTY_KEY, ucteNode.getGeographicalName());
        }
    }

    private static void addGeographicalNameProperty(UcteNetwork ucteNetwork, TieLine tieLine, DanglingLine dl1, DanglingLine dl2) {
        Optional<UcteNodeCode> optUcteNodeCode = UcteNodeCode.parseUcteNodeCode(dl1.getUcteXnodeCode());

        if (optUcteNodeCode.isPresent()) {
            UcteNode ucteNode = ucteNetwork.getNode(optUcteNodeCode.get());
            tieLine.setProperty(GEOGRAPHICAL_NAME_PROPERTY_KEY, ucteNode.getGeographicalName());
        } else {
            throw new UcteException(NOT_POSSIBLE_TO_IMPORT);
        }
    }

    private static void addOrderCodeProperty(UcteLine ucteLine, Switch sw) {
        String ucteLineId = ucteLine.getId().toString();
        sw.setProperty(ORDER_CODE, String.valueOf(ucteLineId.charAt(ucteLineId.length() - 1)));
    }

    private static void addNominalPowerProperty(UcteTransformer transformer, TwoWindingsTransformer twoWindingsTransformer) {
        twoWindingsTransformer.setProperty(NOMINAL_POWER_KEY, String.valueOf(transformer.getNominalPower()));
    }

    @Override
    public String getFormat() {
        return "UCTE";
    }

    @Override
    public String getComment() {
        return "UCTE-DEF";
    }

    private String findExtension(ReadOnlyDataSource dataSource, boolean throwException) throws IOException {
        for (String ext : EXTENSIONS) {
            if (dataSource.exists(null, ext)) {
                return ext;
            }
        }
        if (throwException) {
            throw new UcteException("File " + dataSource.getBaseName()
                    + "." + Joiner.on("|").join(EXTENSIONS) + " not found");
        }
        return null;
    }

    @Override
    public boolean exists(ReadOnlyDataSource dataSource) {
        try {
            String ext = findExtension(dataSource, false);
            if (ext != null) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(dataSource.newInputStream(null, ext)))) {
                    return new UcteReader().checkHeader(reader);
                }
            }
            return false;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private void mergeXnodeDanglingLines(UcteNetwork ucteNetwork, Network network) {
        Multimap<String, DanglingLine> danglingLinesByXnodeCode = HashMultimap.create();
        for (DanglingLine dl : network.getDanglingLines()) {
            danglingLinesByXnodeCode.put(dl.getExtension(Xnode.class).getCode(), dl);
        }

        Set<DanglingLine> danglingLinesToProcess = Sets.newHashSet(network.getDanglingLines());
        while (!danglingLinesToProcess.isEmpty()) {
            DanglingLine dlToProcess = danglingLinesToProcess.iterator().next();
            DanglingLine dlMatchingDlToProcess = getMatchingDanglingLine(dlToProcess, danglingLinesByXnodeCode);

            if (dlMatchingDlToProcess != null) {
                // lexical sort to always end up with same merge line id
                boolean switchDanglingLinesOrder = dlToProcess.getId().compareTo(dlMatchingDlToProcess.getId()) > 0;
                DanglingLine dlAtSideOne = switchDanglingLinesOrder ? dlMatchingDlToProcess : dlToProcess;
                DanglingLine dlAtSideTwo = switchDanglingLinesOrder ? dlToProcess : dlMatchingDlToProcess;

                createTieLine(ucteNetwork, network, dlAtSideOne, dlAtSideTwo);

                dlToProcess.remove();
                dlMatchingDlToProcess.remove();

                danglingLinesToProcess.remove(dlMatchingDlToProcess);
            }
            danglingLinesToProcess.remove(dlToProcess);
        }
    }

    private void createTieLine(UcteNetwork ucteNetwork, Network network, DanglingLine dlAtSideOne, DanglingLine dlAtSideTwo) {
        // lexical sort to always end up with same merge line id
        String mergeLineId = dlAtSideOne.getId() + " + " + dlAtSideTwo.getId();

        // create XNODE merge extension
        // In case R1 and R2 (resp. X1 and X2) are zero, rdp (resp. xdp) is set to 0.5:
        // by default the line is split in the middle.
        // R1 = 0 and R2 = 0 (resp. X1 = 0 and X2 = 0) are recovered when splitting the mergedXnode anyway.
        double sumR = dlAtSideOne.getR() + dlAtSideTwo.getR();
        double sumX = dlAtSideOne.getX() + dlAtSideTwo.getX();
        float rdp = (sumR == 0.) ? (float) 0.5 : (float) (dlAtSideOne.getR() / sumR);
        float xdp = (sumX == 0.) ? (float) 0.5 : (float) (dlAtSideOne.getX() / sumX);
        double xnodeP1 = dlAtSideOne.getP0();
        double xnodeQ1 = dlAtSideOne.getQ0();
        double xnodeP2 = dlAtSideTwo.getP0();
        double xnodeQ2 = dlAtSideTwo.getQ0();
        String xnodeCode = dlAtSideOne.getExtension(Xnode.class).getCode();

        TieLine mergeLine = network.newTieLine()
                .setId(mergeLineId)
                .setVoltageLevel1(dlAtSideOne.getTerminal().getVoltageLevel().getId())
                .setConnectableBus1(getBusId(dlAtSideOne.getTerminal().getBusBreakerView().getConnectableBus()))
                .setBus1(getBusId(dlAtSideOne.getTerminal().getBusBreakerView().getBus()))
                .setVoltageLevel2(dlAtSideTwo.getTerminal().getVoltageLevel().getId())
                .setConnectableBus2(getBusId(dlAtSideTwo.getTerminal().getBusBreakerView().getConnectableBus()))
                .setBus2(getBusId(dlAtSideTwo.getTerminal().getBusBreakerView().getBus()))
                .line1()
                .setId(dlAtSideOne.getId())
                .setR(dlAtSideOne.getR())
                .setX(dlAtSideOne.getX())
                .setG1(dlAtSideOne.getG())
                .setG2(0.0)
                .setB1(dlAtSideOne.getB())
                .setB2(0.0)
                .setXnodeP(xnodeP1)
                .setXnodeQ(xnodeQ1)
                .line2()
                .setId(dlAtSideTwo.getId())
                .setR(dlAtSideTwo.getR())
                .setX(dlAtSideTwo.getX())
                .setG1(0.0)
                .setG2(dlAtSideTwo.getG())
                .setB1(0.0)
                .setB2(dlAtSideTwo.getB())
                .setXnodeP(xnodeP2)
                .setXnodeQ(xnodeQ2)
                .setUcteXnodeCode(xnodeCode)
                .add();

        addElementNameProperty(mergeLine, dlAtSideOne, dlAtSideTwo);
        addGeographicalNameProperty(ucteNetwork, mergeLine, dlAtSideOne, dlAtSideTwo);

        if (dlAtSideOne.getCurrentLimits() != null) {
            mergeLine.newCurrentLimits1()
                    .setPermanentLimit(dlAtSideOne.getCurrentLimits().getPermanentLimit()).add();
        }
        if (dlAtSideTwo.getCurrentLimits() != null) {
            mergeLine.newCurrentLimits2()
                    .setPermanentLimit(dlAtSideTwo.getCurrentLimits().getPermanentLimit()).add();
        }

        mergeLine.addExtension(MergedXnode.class, new MergedXnode(mergeLine, rdp, xdp, xnodeP1, xnodeQ1, xnodeP2, xnodeQ2,
                dlAtSideOne.getId(), dlAtSideTwo.getId(), xnodeCode));
    }

    @Override
    public void copy(ReadOnlyDataSource fromDataSource, DataSource toDataSource) {
        Objects.requireNonNull(fromDataSource);
        Objects.requireNonNull(toDataSource);
        try {
            String ext = findExtension(fromDataSource, true);
            try (InputStream is = fromDataSource.newInputStream(null, ext);
                 OutputStream os = toDataSource.newOutputStream(null, ext, false)) {
                ByteStreams.copy(is, os);
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public Network importData(ReadOnlyDataSource dataSource, NetworkFactory networkFactory, Properties parameters) {
        try {
            String ext = findExtension(dataSource, true);
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(dataSource.newInputStream(null, ext)))) {

                Stopwatch stopwatch = Stopwatch.createStarted();

                UcteNetworkExt ucteNetwork = new UcteNetworkExt(new UcteReader().read(reader), LINE_MIN_Z);
                String fileName = dataSource.getBaseName();

                EntsoeFileName ucteFileName = EntsoeFileName.parse(fileName);

                Network network = networkFactory.createNetwork(fileName, "UCTE");
                network.setCaseDate(ucteFileName.getDate());
                network.setForecastDistance(ucteFileName.getForecastDistance());

                createBuses(ucteNetwork, network);
                createLines(ucteNetwork, network);
                createTransformers(ucteNetwork, network, ucteFileName);

                mergeXnodeDanglingLines(ucteNetwork, network);

                stopwatch.stop();
                LOGGER.debug("UCTE import done in {} ms", stopwatch.elapsed(TimeUnit.MILLISECONDS));

                return network;
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

}
