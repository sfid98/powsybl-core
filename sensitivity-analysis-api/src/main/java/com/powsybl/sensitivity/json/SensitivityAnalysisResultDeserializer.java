/**
 * Copyright (c) 2022, RTE (http://www.rte-france.com)
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.powsybl.sensitivity.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.powsybl.sensitivity.SensitivityAnalysisResult;
import com.powsybl.sensitivity.SensitivityFactor;
import com.powsybl.sensitivity.SensitivityValue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Bertrand Rix {@literal <bertrand.rix at artelys.com>}
 */
public class SensitivityAnalysisResultDeserializer extends StdDeserializer<SensitivityAnalysisResult> {

    protected SensitivityAnalysisResultDeserializer() {
        super(SensitivityAnalysisResult.class);
    }

    @Override
    public SensitivityAnalysisResult deserialize(JsonParser parser, DeserializationContext deserializationContext) throws IOException {
        String version = null;
        List<SensitivityValue> sensitivityValues = null;
        List<SensitivityAnalysisResult.SensitivityContingencyStatus> contingencyStatus = null;
        List<SensitivityFactor> factors = null;
        while (parser.nextToken() != JsonToken.END_OBJECT) {
            switch (parser.getCurrentName()) {
                case "version":
                    parser.nextToken(); // skip
                    version = parser.getValueAsString();
                    break;

                case "sensitivityFactors":
                    parser.nextToken();
                    factors = parser.readValueAs(new TypeReference<ArrayList<SensitivityFactor>>() { });
                    break;

                case "sensitivityValues":
                    parser.nextToken();
                    sensitivityValues = parser.readValueAs(new TypeReference<ArrayList<SensitivityValue>>() { });
                    break;

                case "contingencyStatus":
                    parser.nextToken();
                    contingencyStatus = parser.readValueAs(new TypeReference<ArrayList<SensitivityAnalysisResult.SensitivityContingencyStatus>>() {
                    });
                    break;
                default:
                    throw new AssertionError("Unexpected field: " + parser.getCurrentName());
            }
        }

        if (version == null || !version.equals("1.0")) {
            //Only 1.0 version is supported for now
            throw new AssertionError("Version different than 1.0 not supported.");
        }
        return new SensitivityAnalysisResult(factors, contingencyStatus, sensitivityValues);
    }
}
