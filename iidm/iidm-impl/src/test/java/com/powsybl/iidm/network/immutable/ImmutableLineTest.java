/**
 * Copyright (c) 2019, RTE (http://www.rte-france.com)
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.powsybl.iidm.network.immutable;

import com.powsybl.commons.TestHelper;
import com.powsybl.iidm.network.*;
import com.powsybl.iidm.network.test.EurostagTutorialExample1Factory;
import com.powsybl.iidm.network.test.HvdcTestNetwork;
import com.powsybl.iidm.network.test.NoEquipmentNetworkFactory;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

/**
 * @author Yichen TANG <yichen.tang at rte-france.com>
 */
public class ImmutableLineTest {

    private static final Set<String> INVALID_LINE_METHODS = new HashSet<>();

    static {
        INVALID_LINE_METHODS.add("setR");
        INVALID_LINE_METHODS.add("setX");
        INVALID_LINE_METHODS.add("setG1");
        INVALID_LINE_METHODS.add("setB1");
        INVALID_LINE_METHODS.add("setG2");
        INVALID_LINE_METHODS.add("setB2");
        INVALID_LINE_METHODS.add("newCurrentLimits1");
        INVALID_LINE_METHODS.add("newCurrentLimits2");
        INVALID_LINE_METHODS.add("remove");
    }

    @Test
    public void testImmutableLine() {
        Network network = ImmutableNetwork.of(EurostagTutorialExample1Factory.createWithFixedCurrentLimits());
        Line line = network.getLine("NHV1_NHV2_1");
        assertTrue(line instanceof ImmutableLine);
        ImmutableTestHelper.testInvalidMethods(line, INVALID_LINE_METHODS);
        CurrentLimits currentLimits1 = line.getCurrentLimits1();
        assertTrue(currentLimits1 instanceof ImmutableCurrentLimits);
        Set<String> invalidLimmitMethods = new HashSet<>();
        invalidLimmitMethods.add("setPermanentLimit");
        TestHelper.assertInvalidInvocation(() -> currentLimits1.setPermanentLimit(1.0), "Unmodifiable currentLimits");
    }

    @Test
    public void testImmutableTieLine() {
        Network n = NoEquipmentNetworkFactory.createWithTieLine();
        Network network = ImmutableNetwork.of(n);
        Line line = network.getLine("testTie");
        assertTrue(line.isTieLine());
        assertTrue(line instanceof ImmutableTieLine);
        TieLine tieLine = (TieLine) line;
        ImmutableTestHelper.testInvalidMethods(tieLine, INVALID_LINE_METHODS);

        TieLine.HalfLine half = tieLine.getHalf1();
        Set<String> invalidHalfLineMethods = new HashSet<>();
        invalidHalfLineMethods.add("setR");
        invalidHalfLineMethods.add("setX");
        invalidHalfLineMethods.add("setG1");
        invalidHalfLineMethods.add("setB1");
        invalidHalfLineMethods.add("setG2");
        invalidHalfLineMethods.add("setB2");
        invalidHalfLineMethods.add("setXnodeP");
        invalidHalfLineMethods.add("setXnodeQ");
        ImmutableTestHelper.testInvalidMethods(half, invalidHalfLineMethods);
    }

    @Test
    public void testHvdcLine() {
        Network n = HvdcTestNetwork.createLcc();
        Network network = ImmutableNetwork.of(n);
        HvdcLine hvdcLine = network.getHvdcLine("L");
        assertTrue(hvdcLine instanceof ImmutableHvdcLine);
        assertSame(network, hvdcLine.getNetwork());
        Set<String> expectedInvalidMethods = new HashSet<>();
        expectedInvalidMethods.add("setConvertersMode");
        expectedInvalidMethods.add("setR");
        expectedInvalidMethods.add("setNominalV");
        expectedInvalidMethods.add("setActivePowerSetpoint");
        expectedInvalidMethods.add("setMaxP");
        expectedInvalidMethods.add("remove");
        ImmutableTestHelper.testInvalidMethods(hvdcLine, expectedInvalidMethods);
    }
}
