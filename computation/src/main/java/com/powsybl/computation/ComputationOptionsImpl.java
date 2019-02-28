/**
 * Copyright (c) 2019, RTE (http://www.rte-france.com)
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.powsybl.computation;

import java.util.*;

/**
 * @author Yichen TANG <yichen.tang at rte-france.com>
 */
public class ComputationOptionsImpl implements ComputationOptions {

    private final Map<String, Long> timeoutMap;
    private final Map<String, String> qosMap;

    ComputationOptionsImpl(Map<String, Long> timeoutMap, Map<String, String> qosMap) {
        this.timeoutMap = Collections.unmodifiableMap(timeoutMap);
        this.qosMap = Collections.unmodifiableMap(qosMap);
    }

    @Override
    public OptionalLong getTimeout(String cmdId) {
        Objects.requireNonNull(cmdId);
        Long t = timeoutMap.get(cmdId);
        if (t == null) {
            return OptionalLong.empty();
        } else {
            return OptionalLong.of(t);
        }
    }

    @Override
    public Optional<String> getQos(String commandId) {
        Objects.requireNonNull(commandId);
        String qosFromMap = qosMap.get(commandId);
        if (qosFromMap != null) {
            return Optional.of(qosFromMap);
        }
        return Optional.empty();
    }
}
