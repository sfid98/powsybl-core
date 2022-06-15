/**
 * Copyright (c) 2022, RTE (http://www.rte-france.com)
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.powsybl.shortcircuit.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.powsybl.commons.json.JsonUtil;
import com.powsybl.shortcircuit.FaultParameters;

import java.io.IOException;

/**
 * @author Thomas Adam <tadam at silicom.fr>
 */
public class FaultParametersSerializer extends StdSerializer<FaultParameters> {

    public FaultParametersSerializer() {
        super(FaultParameters.class);
    }

    @Override
    public void serialize(FaultParameters parameters, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();

        jsonGenerator.writeStringField("id", parameters.getId());
        JsonUtil.writeOptionalBooleanField(jsonGenerator, "withLimitViolations", parameters.isWithLimitViolations(), false);
        JsonUtil.writeOptionalBooleanField(jsonGenerator, "withVoltageMap", parameters.isWithVoltageMap(), false);
        JsonUtil.writeOptionalBooleanField(jsonGenerator, "withFeederResult", parameters.isWithFeederResult(), false);
        JsonUtil.writeOptionalStringField(jsonGenerator, "studyType", parameters.getStudyType() != null ? parameters.getStudyType().name() : null);
        JsonUtil.writeOptionalDoubleField(jsonGenerator, "subTransStudyReactanceCoefficient", parameters.getSubTransStudyReactanceCoefficient());
        JsonUtil.writeOptionalDoubleField(jsonGenerator, "minVoltageDropProportionalThreshold", parameters.getMinVoltageDropProportionalThreshold());
        JsonUtil.writeOptionalStringField(jsonGenerator, "voltageMapType", parameters.getVoltageMapType() != null ? parameters.getVoltageMapType().name() : null);
        JsonUtil.writeOptionalBooleanField(jsonGenerator, "useResistances", parameters.isUseResistances(), true);
        JsonUtil.writeOptionalBooleanField(jsonGenerator, "useLoads", parameters.isUseLoads(), true);
        JsonUtil.writeOptionalBooleanField(jsonGenerator, "useCapacities", parameters.isUseCapacities(), true);
        JsonUtil.writeOptionalBooleanField(jsonGenerator, "useShunts", parameters.isUseShunts(), false);
        JsonUtil.writeOptionalBooleanField(jsonGenerator, "useTapChangers", parameters.isUseTapChangers(), false);
        JsonUtil.writeOptionalBooleanField(jsonGenerator, "useMutuals", parameters.isUseMutuals(), false);
        JsonUtil.writeOptionalBooleanField(jsonGenerator, "modelVSC", parameters.isModelVSC(), false);
        JsonUtil.writeOptionalStringField(jsonGenerator, "startedGroupsInsideZone", parameters.getStartedGroupsInsideZone() != null ? parameters.getStartedGroupsInsideZone().name() : null);
        JsonUtil.writeOptionalDoubleField(jsonGenerator, "startedGroupsInsideZoneThreshold", parameters.getStartedGroupsInsideZoneThreshold());
        JsonUtil.writeOptionalStringField(jsonGenerator, "startedGroupsOutOfZone", parameters.getStartedGroupsOutOfZone() != null ? parameters.getStartedGroupsOutOfZone().name() : null);
        JsonUtil.writeOptionalDoubleField(jsonGenerator, "startedGroupsOutOfZoneThreshold", parameters.getStartedGroupsOutOfZoneThreshold());
        jsonGenerator.writeEndObject();
    }
}
