/**
 * Copyright (c) 2016, All partners of the iTesla project (http://www.itesla-project.eu/consortium)
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.powsybl.iidm.network.impl;

import com.powsybl.commons.PowsyblException;
import com.powsybl.iidm.network.*;
import com.powsybl.iidm.network.impl.NodeBreakerVoltageLevel.ConnectedNode;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author Geoffroy Jamgotchian <geoffroy.jamgotchian at rte-france.com>
 */
class CalculatedBusImpl extends AbstractBus implements CalculatedBus {

    private boolean valid = true;

    private final List<ConnectedNode> connectedNodes;

    CalculatedBusImpl(String id, VoltageLevelExt voltageLevel, List<ConnectedNode> connectedNodes) {
        super(id, voltageLevel);
        this.connectedNodes = Objects.requireNonNull(connectedNodes);
    }

    private void checkValidity() {
        if (!valid) {
            throw new PowsyblException("Bus has been invalidated");
        }
    }

    /**
     * To invalidate the bus when it is a result of calculation and the topology
     * of the substation is modified.
     */
    @Override
    public void invalidate() {
        valid = false;
        voltageLevel = null;
        connectedNodes.clear();
    }

    @Override
    public VoltageLevel getVoltageLevel() {
        checkValidity();
        return super.getVoltageLevel();
    }

    @Override
    public int getConnectedTerminalCount() {
        checkValidity();
        return getTerminals().size();
    }

    @Override
    public Collection<TerminalExt> getConnectedTerminals() {
        return getTerminals().stream().map(t -> (TerminalExt) t).collect(Collectors.toList());
    }

    @Override
    public Stream<TerminalExt> getConnectedTerminalStream() {
        checkValidity();
        return getTerminals().stream().map(Function.identity());
    }

    @Override
    public Collection<NodeTerminal> getTerminals() {
        checkValidity();
        return Collections.unmodifiableCollection(connectedNodes.stream()
            .filter(n -> n.getNodeTerminal().isPresent())
            .map(n -> n.getNodeTerminal().get())
            .collect(Collectors.toList()));
    }

    @Override
    public BusExt setV(double v) {
        checkValidity();
        for (NodeTerminal terminal : getTerminals()) {
            terminal.setV(v);
        }
        return this;
    }

    @Override
    public double getV() {
        checkValidity();
        if (getTerminals().isEmpty()) {
            return Double.NaN;
        }
        return getTerminals().iterator().next().getV();
    }

    @Override
    public BusExt setAngle(double angle) {
        checkValidity();
        for (NodeTerminal terminal : getTerminals()) {
            terminal.setAngle(angle);
        }
        return this;
    }

    @Override
    public double getAngle() {
        checkValidity();
        Collection<NodeTerminal> terminals = getTerminals();
        if (terminals.isEmpty()) {
            return Double.NaN;
        }
        return terminals.iterator().next().getAngle();
    }

    @Override
    public double getP() {
        checkValidity();
        return super.getP();
    }

    @Override
    public double getQ() {
        checkValidity();
        return super.getQ();
    }

    @Override
    public void setConnectedComponentNumber(int connectedComponentNumber) {
        checkValidity();
        for (ConnectedNode node : connectedNodes) {
            node.setConnectedComponentNumber(connectedComponentNumber);
        }
    }

    @Override
    public Component getConnectedComponent() {
        checkValidity();
        NetworkImpl.ConnectedComponentsManager ccm = voltageLevel.getNetwork().getConnectedComponentsManager();
        ccm.update();
        return ccm.getComponent(connectedNodes.get(0).getConnectedComponentNumber());
    }

    @Override
    public void setSynchronousComponentNumber(int componentNumber) {
        checkValidity();
        for (ConnectedNode node : connectedNodes) {
            node.setSynchronousComponentNumber(componentNumber);
        }
    }

    @Override
    public Component getSynchronousComponent() {
        checkValidity();
        NetworkImpl.SynchronousComponentsManager scm = voltageLevel.getNetwork().getSynchronousComponentsManager();
        scm.update();
        return scm.getComponent(connectedNodes.get(0).getSynchronousComponentNumber());
    }

    @Override
    public Iterable<Line> getLines() {
        checkValidity();
        return super.getLines();
    }

    @Override
    public Stream<Line> getLineStream() {
        checkValidity();
        return super.getLineStream();
    }

    @Override
    public Iterable<TwoWindingsTransformer> getTwoWindingsTransformers() {
        checkValidity();
        return super.getTwoWindingsTransformers();
    }

    @Override
    public Stream<TwoWindingsTransformer> getTwoWindingsTransformerStream() {
        checkValidity();
        return super.getTwoWindingsTransformerStream();
    }

    @Override
    public Iterable<ThreeWindingsTransformer> getThreeWindingsTransformers() {
        checkValidity();
        return super.getThreeWindingsTransformers();
    }

    @Override
    public Stream<ThreeWindingsTransformer> getThreeWindingsTransformerStream() {
        checkValidity();
        return super.getThreeWindingsTransformerStream();
    }

    @Override
    public Iterable<Load> getLoads() {
        checkValidity();
        return super.getLoads();
    }

    @Override
    public Stream<Load> getLoadStream() {
        checkValidity();
        return super.getLoadStream();
    }

    /**
     * @deprecated Use {@link #getShuntCompensators()} instead.
     */
    @Override
    @Deprecated
    public Iterable<ShuntCompensator> getShunts() {
        return getShuntCompensators();
    }

    /**
     * @deprecated Use {@link #getShuntCompensatorStream()} instead.
     */
    @Override
    @Deprecated
    public Stream<ShuntCompensator> getShuntStream() {
        return getShuntCompensatorStream();
    }

    @Override
    public Iterable<ShuntCompensator> getShuntCompensators() {
        checkValidity();
        return super.getShuntCompensators();
    }

    @Override
    public Stream<ShuntCompensator> getShuntCompensatorStream() {
        checkValidity();
        return super.getShuntCompensatorStream();
    }

    @Override
    public Iterable<Generator> getGenerators() {
        checkValidity();
        return super.getGenerators();
    }

    @Override
    public Stream<Generator> getGeneratorStream() {
        checkValidity();
        return super.getGeneratorStream();
    }

    @Override
    public Iterable<DanglingLine> getDanglingLines() {
        checkValidity();
        return super.getDanglingLines();
    }

    @Override
    public Stream<DanglingLine> getDanglingLineStream() {
        checkValidity();
        return super.getDanglingLineStream();
    }

    @Override
    public Iterable<StaticVarCompensator> getStaticVarCompensators() {
        checkValidity();
        return super.getStaticVarCompensators();
    }

    @Override
    public Stream<StaticVarCompensator> getStaticVarCompensatorStream() {
        checkValidity();
        return super.getStaticVarCompensatorStream();
    }

    @Override
    public Iterable<LccConverterStation> getLccConverterStations() {
        checkValidity();
        return super.getLccConverterStations();
    }

    @Override
    public Stream<LccConverterStation> getLccConverterStationStream() {
        checkValidity();
        return super.getLccConverterStationStream();
    }

    @Override
    public Iterable<VscConverterStation> getVscConverterStations() {
        checkValidity();
        return super.getVscConverterStations();
    }

    @Override
    public Stream<VscConverterStation> getVscConverterStationStream() {
        checkValidity();
        return super.getVscConverterStationStream();
    }

    @Override
    public void visitConnectedEquipments(TopologyVisitor visitor) {
        checkValidity();
        super.visitConnectedEquipments(visitor);
    }

    @Override
    public void visitConnectedOrConnectableEquipments(TopologyVisitor visitor) {
        checkValidity();
        super.visitConnectedOrConnectableEquipments(visitor);
    }
}
