/**
 * Copyright (c) 2018, RTE (http://www.rte-france.com)
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.powsybl.iidm.network.util;

import com.powsybl.iidm.network.VariantManager;

import java.util.*;

/**
 * @author Geoffroy Jamgotchian <geoffroy.jamgotchian at rte-france.com>
 */
public final class ImmutableVariantManager implements VariantManager {

    private static final Map<VariantManager, ImmutableVariantManager> CACHE = new HashMap<>();

    private final VariantManager variantManager;

    private ImmutableVariantManager(VariantManager variantManager) {
        this.variantManager = variantManager;
    }

    static ImmutableVariantManager of(VariantManager variantManager) {
        Objects.requireNonNull(variantManager);
        return CACHE.computeIfAbsent(variantManager, k -> new ImmutableVariantManager(variantManager));
    }

    @Override
    public Collection<String> getVariantIds() {
        return variantManager.getVariantIds();
    }

    @Override
    public String getWorkingVariantId() {
        return variantManager.getWorkingVariantId();
    }

    @Override
    public void setWorkingVariant(String variantId) {
        variantManager.setWorkingVariant(variantId);
    }

    @Override
    public void cloneVariant(String sourceVariantId, List<String> targetVariantIds) {
        throw ImmutableNetwork.createUnmodifiableNetworkException();
    }

    @Override
    public void cloneVariant(String sourceVariantId, String targetVariantId) {
        throw ImmutableNetwork.createUnmodifiableNetworkException();
    }

    @Override
    public void removeVariant(String variantId) {
        throw ImmutableNetwork.createUnmodifiableNetworkException();
    }

    @Override
    public void allowVariantMultiThreadAccess(boolean allow) {
        variantManager.allowVariantMultiThreadAccess(allow);
    }

    @Override
    public boolean isVariantMultiThreadAccessAllowed() {
        return variantManager.isVariantMultiThreadAccessAllowed();
    }
}
