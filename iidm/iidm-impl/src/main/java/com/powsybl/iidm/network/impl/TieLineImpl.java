/**
 * Copyright (c) 2016, All partners of the iTesla project (http://www.itesla-project.eu/consortium)
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.powsybl.iidm.network.impl;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.powsybl.iidm.network.TieLine;
import com.powsybl.iidm.network.ValidationException;
import com.powsybl.iidm.network.util.Quadripole;
import com.powsybl.iidm.network.util.Quadripole.PiModel;

/**
 * @author Geoffroy Jamgotchian <geoffroy.jamgotchian at rte-france.com>
 */
class TieLineImpl extends LineImpl implements TieLine {

    static class HalfLineImpl implements HalfLine {

        TieLineImpl parent;
        String id;
        String name;
        double xnodeP = Double.NaN;
        double xnodeQ = Double.NaN;
        double r = Double.NaN;
        double x = Double.NaN;
        double g1 = Double.NaN;
        double g2 = Double.NaN;
        double b1 = Double.NaN;
        double b2 = Double.NaN;
        boolean fictitious = false;

        private void setParent(TieLineImpl parent) {
            this.parent = parent;
        }

        private void notifyUpdate(String attribute, Object oldValue, Object newValue) {
            if (Objects.nonNull(parent)) {
                parent.notifyUpdate(() -> getHalfLineAttribute() + "." + attribute, oldValue, newValue);
            }
        }

        @Override
        public String getId() {
            return id;
        }

        void setId(String id) {
            String oldValue = this.id;
            this.id = id;
            notifyUpdate("id", oldValue, id);
        }

        @Override
        public String getName() {
            return name == null ? id : name;
        }

        void setName(String name) {
            String oldValue = this.name;
            this.name = name;
            notifyUpdate("name", oldValue, name);
        }

        @Override
        public double getXnodeP() {
            return xnodeP;
        }

        @Override
        public HalfLineImpl setXnodeP(double xnodeP) {
            double oldValue = this.xnodeP;
            this.xnodeP = xnodeP;
            notifyUpdate("xnodeP", oldValue, xnodeP);
            return this;
        }

        @Override
        public double getXnodeQ() {
            return xnodeQ;
        }

        @Override
        public HalfLineImpl setXnodeQ(double xnodeQ) {
            double oldValue = this.xnodeQ;
            this.xnodeQ = xnodeQ;
            notifyUpdate("xnodeQ", oldValue, xnodeQ);
            return this;
        }

        @Override
        public double getR() {
            return r;
        }

        @Override
        public HalfLineImpl setR(double r) {
            double oldValue = this.r;
            this.r = r;
            notifyUpdate("r", oldValue, r);
            return this;
        }

        @Override
        public double getX() {
            return x;
        }

        @Override
        public HalfLineImpl setX(double x) {
            double oldValue = this.x;
            this.x = x;
            notifyUpdate("x", oldValue, x);
            return this;
        }

        @Override
        public double getG1() {
            return g1;
        }

        @Override
        public HalfLineImpl setG1(double g1) {
            double oldValue = this.g1;
            this.g1 = g1;
            notifyUpdate("g1", oldValue, g1);
            return this;
        }

        @Override
        public double getG2() {
            return g2;
        }

        @Override
        public HalfLineImpl setG2(double g2) {
            double oldValue = this.g2;
            this.g2 = g2;
            notifyUpdate("g2", oldValue, g2);
            return this;
        }

        @Override
        public double getB1() {
            return b1;
        }

        @Override
        public HalfLineImpl setB1(double b1) {
            double oldValue = this.b1;
            this.b1 = b1;
            notifyUpdate("b1", oldValue, b1);
            return this;
        }

        @Override
        public double getB2() {
            return b2;
        }

        @Override
        public HalfLineImpl setB2(double b2) {
            double oldValue = this.b2;
            this.b2 = b2;
            notifyUpdate("b2", oldValue, b2);
            return this;
        }

        @Override
        public boolean isFictitious() {
            return fictitious;
        }

        @Override
        public HalfLineImpl setFictitious(boolean fictitious) {
            boolean oldValue = this.fictitious;
            this.fictitious = fictitious;
            notifyUpdate("fictitious", oldValue, fictitious);
            return this;
        }

        private String getHalfLineAttribute() {
            return this == parent.half1 ? "half1" : "half2";
        }
    }

    private final String ucteXnodeCode;

    private final HalfLineImpl half1;

    private final HalfLineImpl half2;

    TieLineImpl(String id, String name, boolean fictitious, String ucteXnodeCode, HalfLineImpl half1, HalfLineImpl half2) {
        super(id, name, fictitious, Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN);
        this.ucteXnodeCode = ucteXnodeCode;
        this.half1 = attach(half1);
        this.half2 = attach(half2);
    }

    private HalfLineImpl attach(HalfLineImpl half) {
        half.setParent(this);
        return half;
    }

    @Override
    public boolean isTieLine() {
        return true;
    }

    @Override
    public String getUcteXnodeCode() {
        return ucteXnodeCode;
    }

    @Override
    public HalfLineImpl getHalf1() {
        return half1;
    }

    @Override
    public HalfLineImpl getHalf2() {
        return half2;
    }

    @Override
    public HalfLineImpl getHalf(Side side) {
        switch (side) {
            case ONE:
                return half1;
            case TWO:
                return half2;
            default:
                throw new AssertionError("Unknown branch side " + side);
        }
    }

    @Override
    public double getR() {
        return equivalent(half1, half2).r;
    }

    private PiModel equivalent(HalfLineImpl h1, HalfLineImpl h2) {
        return Quadripole.from(PiModel.from(h1)).cascade(Quadripole.from(PiModel.from(h2))).toPiModel();
    }

    private ValidationException createNotSupportedForTieLines() {
        return new ValidationException(this, "direct modification of characteristics not supported for tie lines");
    }

    @Override
    public LineImpl setR(double r) {
        throw createNotSupportedForTieLines();
    }

    @Override
    public double getX() {
        return equivalent(half1, half2).x;
    }

    @Override
    public LineImpl setX(double x) {
        throw createNotSupportedForTieLines();
    }

    @Override
    public double getG1() {
        return equivalent(half1, half2).g1;
    }

    @Override
    public LineImpl setG1(double g1) {
        throw createNotSupportedForTieLines();
    }

    @Override
    public double getB1() {
        return equivalent(half1, half2).b1;
    }

    @Override
    public LineImpl setB1(double b1) {
        throw createNotSupportedForTieLines();
    }

    @Override
    public double getG2() {
        return equivalent(half1, half2).g2;
    }

    @Override
    public LineImpl setG2(double g2) {
        throw createNotSupportedForTieLines();
    }

    @Override
    public double getB2() {
        return equivalent(half1, half2).b2;
    }

    @Override
    public LineImpl setB2(double b2) {
        throw createNotSupportedForTieLines();
    }
}
