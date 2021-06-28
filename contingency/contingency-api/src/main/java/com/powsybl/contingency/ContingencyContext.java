/**
 * Copyright (c) 2021, RTE (http://www.rte-france.com)
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package com.powsybl.contingency;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

/**
 * @author Geoffroy Jamgotchian <geoffroy.jamgotchian at rte-france.com>
 * @author Etienne Lesot <etienne.lesot at rte-france.com>
 * <p>
 * provide the context to get information of the network after a security analysis
 * it contains a contingency's id and a context type. Context type defines
 * if we want the information in a pre-contingency state, a post-contingency state or both.
 * contingency's id is defined if informations are needed after
 * a specific contingency computation
 */
public class ContingencyContext {

    private final String contingencyId;

    /**
     * Define if informations needed are in N_Situation, N-k Situation or both
     * if it is N_situation contingencyid is null
     */
    private final ContingencyContextType contextType;

    public ContingencyContext(@JsonProperty("contingencyId") String contingencyId,
                              @JsonProperty("contextType") ContingencyContextType contingencyContextType) {
        this.contextType = Objects.requireNonNull(contingencyContextType);
        this.contingencyId = contingencyId;
    }

    public String getContingencyId() {
        return contingencyId;
    }

    public ContingencyContextType getContextType() {
        return contextType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ContingencyContext that = (ContingencyContext) o;
        return Objects.equals(contingencyId, that.contingencyId) &&
            contextType == that.contextType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(contingencyId, contextType);
    }

    @Override
    public String toString() {
        return "ContingencyContext(" +
            "contingencyId='" + Objects.toString(contingencyId, "") + '\'' +
            ", contextType=" + contextType +
            ')';
    }

    public static ContingencyContext all() {
        return new ContingencyContext(null, ContingencyContextType.ALL);
    }

    public static ContingencyContext none() {
        return new ContingencyContext(null, ContingencyContextType.NONE);
    }

    public static ContingencyContext specificContingency(String contingencyId) {
        return new ContingencyContext(contingencyId, ContingencyContextType.SPECIFIC);
    }
}