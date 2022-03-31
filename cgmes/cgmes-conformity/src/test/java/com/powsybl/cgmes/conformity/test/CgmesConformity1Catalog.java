/**
 * Copyright (c) 2017-2018, RTE (http://www.rte-france.com)
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package com.powsybl.cgmes.conformity.test;

import com.powsybl.cgmes.model.CgmesModel;
import com.powsybl.cgmes.model.test.FakeCgmesModel;
import com.powsybl.cgmes.model.test.TestGridModel;
import com.powsybl.cgmes.model.test.TestGridModelResources;
import com.powsybl.commons.datasource.ResourceSet;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Luma Zamarreño <zamarrenolm at aia.es>
 */
public final class CgmesConformity1Catalog {

    private CgmesConformity1Catalog() {
    }

    public static TestGridModelResources microGridBaseCaseBE() {
        String base = ENTSOE_CONFORMITY_1 + "/MicroGrid/BaseCase/CGMES_v2.4.15_MicroGridTestConfiguration_BC_BE_v2/";
        String baseBoundary = ENTSOE_CONFORMITY_1
                + "/MicroGrid/BaseCase/CGMES_v2.4.15_MicroGridTestConfiguration_BD_v2/";
        return new TestGridModelResources(
                "MicroGrid-BaseCase-BE",
                expectedMicroGridBaseCaseBE(),
                new ResourceSet(base, "MicroGridTestConfiguration_BC_BE_DL_V2.xml",
                        "MicroGridTestConfiguration_BC_BE_DY_V2.xml",
                        "MicroGridTestConfiguration_BC_BE_EQ_V2.xml",
                        "MicroGridTestConfiguration_BC_BE_GL_V2.xml",
                        "MicroGridTestConfiguration_BC_BE_SSH_V2.xml",
                        "MicroGridTestConfiguration_BC_BE_SV_V2.xml",
                        "MicroGridTestConfiguration_BC_BE_TP_V2.xml"),
                new ResourceSet(baseBoundary, "MicroGridTestConfiguration_EQ_BD.xml",
                        "MicroGridTestConfiguration_TP_BD.xml"));
    }

    public static TestGridModel microGridType4BE() {
        String base = ENTSOE_CONFORMITY_1
                + "/MicroGrid/Type4_T4/CGMES_v2.4.15_MicroGridTestConfiguration_T4_BE_BB_Complete_v2/";
        String baseBoundary = ENTSOE_CONFORMITY_1
                + "/MicroGrid/Type4_T4/CGMES_v2.4.15_MicroGridTestConfiguration_BD_v2/";
        return new TestGridModelResources(
                "MicroGrid-Type4-BE",
                expectedMicroGridType4BE(),
                new ResourceSet(base, "MicroGridTestConfiguration_T4_BE_DL_V2.xml",
                        "MicroGridTestConfiguration_T4_BE_DY_V2.xml",
                        "MicroGridTestConfiguration_T4_BE_EQ_V2.xml",
                        "MicroGridTestConfiguration_T4_BE_GL_V2.xml",
                        "MicroGridTestConfiguration_T4_BE_SSH_V2.xml",
                        "MicroGridTestConfiguration_T4_BE_SV_V2.xml",
                        "MicroGridTestConfiguration_T4_BE_TP_V2.xml"),
                new ResourceSet(baseBoundary, "MicroGridTestConfiguration_EQ_BD.xml",
                        "MicroGridTestConfiguration_TP_BD.xml"));
    }

    public static TestGridModel microGridType4BEOnlyEqTpSsh() {
        String base = ENTSOE_CONFORMITY_1
                + "/MicroGrid/Type4_T4/CGMES_v2.4.15_MicroGridTestConfiguration_T4_BE_BB_Complete_v2/";
        String baseBoundary = ENTSOE_CONFORMITY_1
                + "/MicroGrid/Type4_T4/CGMES_v2.4.15_MicroGridTestConfiguration_BD_v2/";
        return new TestGridModelResources(
                "MicroGrid-Type4-BE",
                expectedMicroGridType4BE(),
                new ResourceSet(base,
                        "MicroGridTestConfiguration_T4_BE_EQ_V2.xml",
                        "MicroGridTestConfiguration_T4_BE_SSH_V2.xml",
                        "MicroGridTestConfiguration_T4_BE_TP_V2.xml"),
                new ResourceSet(baseBoundary, "MicroGridTestConfiguration_EQ_BD.xml",
                        "MicroGridTestConfiguration_TP_BD.xml"));
    }

    public static TestGridModelResources microGridBaseCaseNL() {
        String base = ENTSOE_CONFORMITY_1 + "/MicroGrid/BaseCase/CGMES_v2.4.15_MicroGridTestConfiguration_BC_NL_v2/";
        String baseBoundary = ENTSOE_CONFORMITY_1
                + "/MicroGrid/BaseCase/CGMES_v2.4.15_MicroGridTestConfiguration_BD_v2/";
        return new TestGridModelResources(
                "MicroGrid-BaseCase-NL",
                null,
                new ResourceSet(base, "MicroGridTestConfiguration_BC_NL_DL_V2.xml",
                        "MicroGridTestConfiguration_BC_NL_DY_V2.xml",
                        "MicroGridTestConfiguration_BC_NL_EQ_V2.xml",
                        "MicroGridTestConfiguration_BC_NL_GL_V2.xml",
                        "MicroGridTestConfiguration_BC_NL_SSH_V2.xml",
                        "MicroGridTestConfiguration_BC_NL_SV_V2.xml",
                        "MicroGridTestConfiguration_BC_NL_TP_V2.xml"),
                new ResourceSet(baseBoundary, "MicroGridTestConfiguration_EQ_BD.xml",
                        "MicroGridTestConfiguration_TP_BD.xml"));
    }

    public static TestGridModelResources microGridBaseCaseAssembled() {
        String base = ENTSOE_CONFORMITY_1
                + "/MicroGrid/BaseCase/CGMES_v2.4.15_MicroGridTestConfiguration_BC_Assembled_v2/";
        String baseBoundary = ENTSOE_CONFORMITY_1
                + "/MicroGrid/BaseCase/CGMES_v2.4.15_MicroGridTestConfiguration_BD_v2/";
        return new TestGridModelResources(
                "MicroGrid-BaseCase-Assembled",
                null,
                new ResourceSet(base, "MicroGridTestConfiguration_BC_Assembled_DL_V2.xml",
                        "MicroGridTestConfiguration_BC_Assembled_SV_V2.xml",
                        "MicroGridTestConfiguration_BC_BE_DY_V2.xml",
                        "MicroGridTestConfiguration_BC_BE_EQ_V2.xml",
                        "MicroGridTestConfiguration_BC_BE_GL_V2.xml",
                        "MicroGridTestConfiguration_BC_BE_SSH_V2.xml",
                        "MicroGridTestConfiguration_BC_BE_TP_V2.xml",
                        "MicroGridTestConfiguration_BC_NL_DY_V2.xml",
                        "MicroGridTestConfiguration_BC_NL_EQ_V2.xml",
                        "MicroGridTestConfiguration_BC_NL_GL_V2.xml",
                        "MicroGridTestConfiguration_BC_NL_SSH_V2.xml",
                        "MicroGridTestConfiguration_BC_NL_TP_V2.xml"),
                new ResourceSet(baseBoundary, "MicroGridTestConfiguration_EQ_BD.xml",
                        "MicroGridTestConfiguration_TP_BD.xml"));
    }

    public static TestGridModelResources miniBusBranch() {
        String base = ENTSOE_CONFORMITY_1 + "/MiniGrid/BusBranch/CGMES_v2.4.15_MiniGridTestConfiguration_BaseCase_v3/";
        return new TestGridModelResources(
                "MiniGrid-BusBranch",
                null,
                new ResourceSet(base, "MiniGridTestConfiguration_BC_DL_v3.0.0.xml",
                        "MiniGridTestConfiguration_BC_EQ_v3.0.0.xml",
                        "MiniGridTestConfiguration_BC_SSH_v3.0.0.xml",
                        "MiniGridTestConfiguration_BC_SV_v3.0.0.xml",
                        "MiniGridTestConfiguration_BC_TP_v3.0.0.xml"));
    }

    public static TestGridModelResources miniNodeBreaker() {
        String base = ENTSOE_CONFORMITY_1
                + "/MiniGrid/NodeBreaker/CGMES_v2.4.15_MiniGridTestConfiguration_BaseCase_Complete_v3/";
        String baseBoundary = ENTSOE_CONFORMITY_1
                + "/MiniGrid/NodeBreaker/CGMES_v2.4.15_MiniGridTestConfiguration_Boundary_v3/";
        return new TestGridModelResources(
                "MiniGrid-NodeBreaker",
                expectedMiniNodeBreaker(),
                new ResourceSet(base, "MiniGridTestConfiguration_BC_DL_v3.0.0.xml",
                        "MiniGridTestConfiguration_BC_EQ_v3.0.0.xml",
                        "MiniGridTestConfiguration_BC_SSH_v3.0.0.xml",
                        "MiniGridTestConfiguration_BC_SV_v3.0.0.xml",
                        "MiniGridTestConfiguration_BC_TP_v3.0.0.xml"),
                new ResourceSet(baseBoundary, "MiniGridTestConfiguration_EQ_BD_v3.0.0.xml",
                        "MiniGridTestConfiguration_TP_BD_v3.0.0.xml"));
    }

    public static TestGridModelResources miniNodeBreakerOnlyEQ() {
        String base = ENTSOE_CONFORMITY_1
                + "/MiniGrid/NodeBreaker/CGMES_v2.4.15_MiniGridTestConfiguration_BaseCase_Complete_v3/";
        String baseBoundary = ENTSOE_CONFORMITY_1
                + "/MiniGrid/NodeBreaker/CGMES_v2.4.15_MiniGridTestConfiguration_Boundary_v3/";
        return new TestGridModelResources(
                "MiniGrid-NodeBreaker-Only-EQ",
                expectedMiniNodeBreaker(),
                new ResourceSet(base, "MiniGridTestConfiguration_BC_EQ_v3.0.0.xml"),
                new ResourceSet(baseBoundary, "MiniGridTestConfiguration_EQ_BD_v3.0.0.xml"));
    }

    public static TestGridModelResources smallBusBranch() {
        String base = ENTSOE_CONFORMITY_1
                + "/SmallGrid/BusBranch/CGMES_v2.4.15_SmallGridTestConfiguration_BaseCase_Complete_v3.0.0/";
        String baseBoundary = ENTSOE_CONFORMITY_1
                + "/SmallGrid/BusBranch/CGMES_v2.4.15_SmallGridTestConfiguration_Boundary_v3.0.0/";
        return new TestGridModelResources(
                "SmallGrid-BusBranch",
                null,
                new ResourceSet(base, "SmallGridTestConfiguration_BC_DL_v3.0.0.xml",
                        "SmallGridTestConfiguration_BC_EQ_v3.0.0.xml",
                        "SmallGridTestConfiguration_BC_GL_v3.0.0.xml",
                        "SmallGridTestConfiguration_BC_SSH_v3.0.0.xml",
                        "SmallGridTestConfiguration_BC_SV_v3.0.0.xml",
                        "SmallGridTestConfiguration_BC_TP_v3.0.0.xml"),
                new ResourceSet(baseBoundary, "SmallGridTestConfiguration_EQ_BD_v3.0.0.xml",
                        "SmallGridTestConfiguration_TP_BD_v3.0.0.xml"));
    }

    public static TestGridModelResources smallBusBranchEqTp() {
        String base = ENTSOE_CONFORMITY_1
                + "/SmallGrid/BusBranch/CGMES_v2.4.15_SmallGridTestConfiguration_BaseCase_Complete_v3.0.0/";
        String baseBoundary = ENTSOE_CONFORMITY_1
                + "/SmallGrid/BusBranch/CGMES_v2.4.15_SmallGridTestConfiguration_Boundary_v3.0.0/";
        return new TestGridModelResources(
                "SmallGrid-BusBranch-EQ-TP",
                null,
                new ResourceSet(base, "SmallGridTestConfiguration_BC_EQ_v3.0.0.xml",
                        "SmallGridTestConfiguration_BC_TP_v3.0.0.xml"),
                new ResourceSet(baseBoundary, "SmallGridTestConfiguration_EQ_BD_v3.0.0.xml",
                        "SmallGridTestConfiguration_TP_BD_v3.0.0.xml"));
    }

    public static TestGridModelResources smallNodeBreaker() {
        String base = ENTSOE_CONFORMITY_1
                + "/SmallGrid/NodeBreaker/CGMES_v2.4.15_SmallGridTestConfiguration_BaseCase_Complete_v3.0.0/";
        String baseBoundary = ENTSOE_CONFORMITY_1
                + "/SmallGrid/NodeBreaker/CGMES_v2.4.15_SmallGridTestConfiguration_Boundary_v3.0.0/";
        return new TestGridModelResources(
                "SmallGrid-NodeBreaker",
                null,
                new ResourceSet(base, "SmallGridTestConfiguration_BC_DL_v3.0.0.xml",
                        "SmallGridTestConfiguration_BC_EQ_v3.0.0.xml",
                        "SmallGridTestConfiguration_BC_GL_v3.0.0.xml",
                        "SmallGridTestConfiguration_BC_SSH_v3.0.0.xml",
                        "SmallGridTestConfiguration_BC_SV_v3.0.0.xml",
                        "SmallGridTestConfiguration_BC_TP_v3.0.0.xml"),
                new ResourceSet(baseBoundary, "SmallGridTestConfiguration_EQ_BD_v3.0.0.xml",
                        "SmallGridTestConfiguration_TP_BD_v3.0.0.xml"));
    }

    public static TestGridModelResources smallNodeBreakerHvdc() {
        String base = ENTSOE_CONFORMITY_1
                + "/SmallGrid/NodeBreaker/CGMES_v2.4.15_SmallGridTestConfiguration_HVDC_Complete_v3.0.0/";
        String baseBoundary = ENTSOE_CONFORMITY_1
                + "/SmallGrid/NodeBreaker/CGMES_v2.4.15_SmallGridTestConfiguration_Boundary_v3.0.0/";
        return new TestGridModelResources(
                "SmallGrid-NodeBreaker-HVDC",
                null,
                new ResourceSet(base, "SmallGridTestConfiguration_HVDC_DL_v3.0.0.xml",
                        "SmallGridTestConfiguration_HVDC_EQ_v3.0.0.xml",
                        "SmallGridTestConfiguration_HVDC_GL_v3.0.0.xml",
                        "SmallGridTestConfiguration_HVDC_SSH_v3.0.0.xml",
                        "SmallGridTestConfiguration_HVDC_SV_v3.0.0.xml",
                        "SmallGridTestConfiguration_HVDC_TP_v3.0.0.xml"),
                new ResourceSet(baseBoundary, "SmallGridTestConfiguration_EQ_BD_v3.0.0.xml",
                        "SmallGridTestConfiguration_TP_BD_v3.0.0.xml"));
    }

    public static TestGridModelResources smallNodeBreakerOnlyEQ() {
        String base = ENTSOE_CONFORMITY_1
                + "/SmallGrid/NodeBreaker/CGMES_v2.4.15_SmallGridTestConfiguration_BaseCase_Complete_v3.0.0/";
        String baseBoundary = ENTSOE_CONFORMITY_1
                + "/SmallGrid/NodeBreaker/CGMES_v2.4.15_SmallGridTestConfiguration_Boundary_v3.0.0/";
        return new TestGridModelResources(
                "SmallGrid-NodeBreaker-Only-EQ",
                null,
                new ResourceSet(base, "SmallGridTestConfiguration_BC_EQ_v3.0.0.xml"),
                new ResourceSet(baseBoundary, "SmallGridTestConfiguration_EQ_BD_v3.0.0.xml"));
    }

    public static TestGridModelResources smallNodeBreakerHvdcOnlyEQ() {
        String base = ENTSOE_CONFORMITY_1
                + "/SmallGrid/NodeBreaker/CGMES_v2.4.15_SmallGridTestConfiguration_HVDC_Complete_v3.0.0/";
        String baseBoundary = ENTSOE_CONFORMITY_1
                + "/SmallGrid/NodeBreaker/CGMES_v2.4.15_SmallGridTestConfiguration_Boundary_v3.0.0/";
        return new TestGridModelResources(
                "SmallGrid-NodeBreaker-HVDC-Only-EQ",
                null,
                new ResourceSet(base, "SmallGridTestConfiguration_HVDC_EQ_v3.0.0.xml"),
                new ResourceSet(baseBoundary, "SmallGridTestConfiguration_EQ_BD_v3.0.0.xml"));
    }

    public static TestGridModelResources smallNodeBreakerEqTp() {
        String base = ENTSOE_CONFORMITY_1
                + "/SmallGrid/NodeBreaker/CGMES_v2.4.15_SmallGridTestConfiguration_BaseCase_Complete_v3.0.0/";
        String baseBoundary = ENTSOE_CONFORMITY_1
                + "/SmallGrid/NodeBreaker/CGMES_v2.4.15_SmallGridTestConfiguration_Boundary_v3.0.0/";
        return new TestGridModelResources(
                "SmallGrid-NodeBreaker-EQ-TP",
                null,
                new ResourceSet(base, "SmallGridTestConfiguration_BC_EQ_v3.0.0.xml",
                        "SmallGridTestConfiguration_BC_TP_v3.0.0.xml"),
                new ResourceSet(baseBoundary, "SmallGridTestConfiguration_EQ_BD_v3.0.0.xml",
                        "SmallGridTestConfiguration_TP_BD_v3.0.0.xml"));
    }

    public static TestGridModelResources smallNodeBreakerEqTpSsh() {
        String base = ENTSOE_CONFORMITY_1
                + "/SmallGrid/NodeBreaker/CGMES_v2.4.15_SmallGridTestConfiguration_BaseCase_Complete_v3.0.0/";
        String baseBoundary = ENTSOE_CONFORMITY_1
                + "/SmallGrid/NodeBreaker/CGMES_v2.4.15_SmallGridTestConfiguration_Boundary_v3.0.0/";
        return new TestGridModelResources(
                "SmallGrid-NodeBreaker-EQ-TP",
                null,
                new ResourceSet(base, "SmallGridTestConfiguration_BC_EQ_v3.0.0.xml",
                        "SmallGridTestConfiguration_BC_SSH_v3.0.0.xml",
                        "SmallGridTestConfiguration_BC_TP_v3.0.0.xml"),
                new ResourceSet(baseBoundary, "SmallGridTestConfiguration_EQ_BD_v3.0.0.xml",
                        "SmallGridTestConfiguration_TP_BD_v3.0.0.xml"));
    }

    public static TestGridModelResources smallNodeBreakerHvdcEqTp() {
        String base = ENTSOE_CONFORMITY_1
                + "/SmallGrid/NodeBreaker/CGMES_v2.4.15_SmallGridTestConfiguration_HVDC_Complete_v3.0.0/";
        String baseBoundary = ENTSOE_CONFORMITY_1
                + "/SmallGrid/NodeBreaker/CGMES_v2.4.15_SmallGridTestConfiguration_Boundary_v3.0.0/";
        return new TestGridModelResources(
                "SmallGrid-NodeBreaker-HVDC-EQ-TP",
                null,
                new ResourceSet(base, "SmallGridTestConfiguration_HVDC_EQ_v3.0.0.xml",
                        "SmallGridTestConfiguration_HVDC_TP_v3.0.0.xml"),
                new ResourceSet(baseBoundary, "SmallGridTestConfiguration_EQ_BD_v3.0.0.xml",
                        "SmallGridTestConfiguration_TP_BD_v3.0.0.xml"));
    }

    public static FakeCgmesModel expectedMicroGridBaseCaseBE() {
        return new FakeCgmesModel()
                .modelId("MicroBaseCaseBE")
                .version("unknown")
                .substations("_37e14a0f-5e34-4647-a062-8bfd9305fa9d",
                        "_87f7002b-056f-4a6a-a872-1744eea757e3")
                .voltageLevels("_469df5f7-058f-4451-a998-57a48e8a56fe",
                        "_4ba71b59-ee2f-450b-9f7d-cc2f1cc5e386",
                        "_8bbd7e74-ae20-4dce-8780-c20f8e18c2e0",
                        "_929ba893-c9dc-44d7-b1fd-30834bd3ab85",
                        "_b10b171b-3bc5-4849-bb1f-61ed9ea1ec7c",
                        "_d0486169-2205-40b2-895e-b672ecb9e5fc")
                .terminals("_02a244ca-8bcb-4e25-8613-e948b8ba1f22",
                        "_051d49ba-4360-4372-86bf-50eb8cf29778",
                        "_05a17350-55f5-4a00-9a50-8c0048a25495",
                        "_1182d878-2eaa-4eec-91be-ce7b2b1e7f9a",
                        "_1ef0715a-d5a9-477b-b6e7-b635529ac140",
                        "_22af3121-1a66-4546-bd80-4371f417c644",
                        "_231a4cf8-5069-4d53-96e4-e839f073f1ea",
                        "_2cd21c77-b8b1-4896-95fb-240f45b9ac89",
                        "_302fe23a-f64d-41bd-8a81-78130433916d",
                        "_3c6d83a3-b5f9-41a2-a3d9-cf15d903ed0a",
                        "_4a7363a4-0b21-4f65-8bba-33e3a8f6bac3",
                        "_4bb5407b-b4a5-416c-80ad-1a778ada2b9b",
                        "_4c19ace6-c825-4c5b-87d9-031e6e6a3379",
                        "_53072f42-f77b-47e2-bd9a-e097c910b173",
                        "_53fd6693-57e6-482e-8fbe-dcf3531a7ce0",
                        "_57ae9251-c022-4c67-a8eb-611ad54c963c",
                        "_5b2c65b0-68ce-4530-85b7-385346a3b5e1",
                        "_62fc0a4e-00aa-4bf7-b1a0-3a5b2c0b5492",
                        "_65b8c937-9b25-4b9e-addf-602dbc1337f9",
                        "_699545b9-82b9-4331-bc80-538d73b4ba56",
                        "_70d962fb-a492-4c36-8cad-b5c584df53bd",
                        "_76e9ca77-f805-40ea-8120-5a6d58416d34",
                        "_77f04391-aa23-49b6-b3e9-6089130bb5d5",
                        "_800ada75-8c8c-4568-aec5-20f799e45f3c",
                        "_8f1c492f-a7cc-4160-9a14-54f1743e4850",
                        "_a036b765-1669-4f64-acd3-1e8fbd513312",
                        "_a1b46f53-86f1-497e-bf57-c3b6268bcd6c",
                        "_a4d42d33-ae54-4fe9-ad59-f30da0dfb809",
                        "_ab7ece75-d726-48c8-a924-b0a9325e6d51",
                        "_ad794c0e-b9ec-420b-ada1-97680e3dde05",
                        // Terminal in the boundary
                        "_b9376bea-c55d-40f3-93ca-6a81fa1186a0",
                        "_b9376bea-c75d-49f3-94ca-6a71fa0086a5",
                        "_b9539c41-d114-4280-8a54-8ecec398091e",
                        "_beffa353-7d10-421d-9c08-036b744b1cee",
                        "_c14d2036-72ec-4df3-b1b7-75d8afd9a1fe",
                        "_c3774d3f-f48c-4954-a0cf-b4572eb714fd",
                        "_c41978db-794b-4bae-953e-60fc519e87dd",
                        "_ca0f7e2e-3442-4ada-a704-91f319c0ebe3",
                        "_ca7974cf-b25e-4898-9221-7154233e5eb2",
                        "_cbdf1842-74ed-4fce-a5d4-0296c82cbc92",
                        "_d238885e-d9b6-4edc-8567-6a68c605ed67",
                        "_d5e2e58e-ccf6-47d9-b3bb-3088eb7a9b6c",
                        "_f3b56334-4638-49d3-a6a0-3f417422b8f5",
                        "_f9f29835-8a31-4310-9780-b1ad26f3cbb0",
                        "_fa9e0f4d-8a2f-45e1-9e36-3611600d1c94")
                .operationalLimits("_05b69cbf-0e24-4c98-bd81-9450c751be6f",
                        "_05b69cbf-0e24-4c98-bd81-9450c751be6f1",
                        "_0d6f26df-9f86-4df0-b00c-bfb23870257f",
                        "_0f8bff64-4cfe-4c94-9471-da94b2efcc4f",
                        "_11763596-6f4b-4cd5-a4a0-be649f368e86",
                        "_1594f66e-86bd-45da-aa04-3c2bd8e07d76",
                        "_19627231-9a8b-45e1-815c-b280a66a59ca",
                        "_1b18dc2e-d876-4213-b9c2-ddfde01caa5a",
                        "_1b18dc2e-d876-4213-b9c2-ddfde01caa5a1",
                        "_1c8440dc-e65d-4337-9d3e-7558062228da",
                        "_1c8440dc-e65d-4337-9d3e-7558062228da1",
                        "_217293df-7f75-4838-8557-d422f7b83c2f",
                        "_235676b8-e13c-4400-878f-b0ddc4c9aeae",
                        "_2e27135c-53b6-4bd0-a4c1-5312fdbf0704",
                        "_2e27135c-53b6-4bd0-a4c1-5312fdbf07041",
                        "_31f7b62d-4707-4067-b1d5-44976db91f00",
                        "_31f7b62d-4707-4067-b1d5-44976db91f001",
                        "_3503e388-5b7c-4e29-ae10-1f45a2c0c96b",
                        "_3503e388-5b7c-4e29-ae10-1f45a2c0c96b1",
                        "_3674d58e-946d-4901-8084-eb21afe1565a",
                        "_367fe7fa-1b11-4090-af9a-0abc050fda58",
                        "_3ab4897f-cf5e-418b-8e1c-94f9cde91501",
                        "_3b3fdb5e-dafe-41bb-acfb-eb21be018863",
                        "_3cff44a0-b9a5-4b35-a724-8740d73c07c9",
                        "_3cff44a0-b9a5-4b35-a724-8740d73c07c91",
                        "_3e9ed732-dd10-4f10-bc9d-d399e1e75a78",
                        "_3ebc30bc-3b24-4ee1-9534-da22e2cd66d1",
                        "_3ebc30bc-3b24-4ee1-9534-da22e2cd66d11",
                        "_43d42f99-7c35-4907-a6ea-372b41eb8f77",
                        "_4574ae6a-6ee5-4d9d-9cf3-0bdcc944b208",
                        "_4574ae6a-6ee5-4d9d-9cf3-0bdcc944b2081",
                        "_4af98ccd-29f1-4039-86cd-c23fc2deb3bc",
                        "_50448009-0fad-4656-bce4-438fe76e18cf",
                        "_52d7ccc6-b4a1-48eb-9cfa-f5870b8b7fce",
                        "_58c959fd-3675-4ad4-a221-9647b57073dd",
                        "_5a4a910c-f57f-456b-b9ca-670ab3676adb",
                        "_5b77485f-20a3-4a19-8d15-e4038c81663f",
                        "_61870312-e0be-4dd7-8941-22c108b61c30",
                        "_661b3900-dc24-4af5-8720-3e675b48b747",
                        "_661b3900-dc24-4af5-8720-3e675b48b7471",
                        "_6623e566-3111-4050-9b4e-c98d89ba3e69",
                        "_69ef4dd8-9f2a-48f7-a230-c4cd695f4876",
                        "_69ef4dd8-9f2a-48f7-a230-c4cd695f48762",
                        "_6db95b07-f943-465c-b6ff-844929c07c8b",
                        "_6f35cf24-2d5e-4b9a-ac65-943610878a4b",
                        "_6f70e245-e075-4ed9-9f86-f137b7e33313",
                        "_6f70e245-e075-4ed9-9f86-f137b7e333131",
                        "_7059bdb7-fa2d-4061-aea7-a88760835e2f",
                        "_7156a0ea-cf7a-46b0-91c0-a973e1c6feb2",
                        "_7939fc42-08ef-4ce7-9912-97552a4db39a",
                        "_7fe9c4b1-7ca5-40f1-9eb4-a51caaa33f7c",
                        "_7fe9c4b1-7ca5-40f1-9eb4-a51caaa33f7c1",
                        "_8117eb14-2b1e-4a66-8dfa-54d2fe69c186",
                        "_8117eb14-2b1e-4a66-8dfa-54d2fe69c1861",
                        "_8447ed5e-3d27-42c0-9e67-9affdda0be45",
                        "_84d4dbeb-ef3b-43a1-9a7e-ce5713013498",
                        "_88240efd-b544-4131-bc99-e6b77d4bac88",
                        "_88240efd-b544-4131-bc99-e6b77d4bac881",
                        "_8b68c876-8c3c-486f-a478-04c5f7c879af",
                        "_8b68c876-8c3c-486f-a478-04c5f7c879af1",
                        "_a1cfb7e6-ed0d-4369-b555-007826ba82fb",
                        "_a3ecec82-f9e9-4a3c-9cd9-23ae8756ba3c",
                        "_a5d3cd27-798c-4910-9729-6fc745346601",
                        "_a634eecf-b900-4808-8b74-d91e36c383a0",
                        "_aaa63bb1-fa34-41a3-bd92-0637bfce549c",
                        "_acbd4688-6393-4b43-a9f4-27d8c3f8c309",
                        "_ad0fa884-ec20-4908-9986-48ab09ac55cd",
                        "_b1714414-0394-42b6-b441-a664069554a2",
                        "_bc51e778-64a2-4176-8d7b-8bdbe0b089cb",
                        "_bc51e778-64a2-4176-8d7b-8bdbe0b089cb1",
                        "_be0d07fc-d20a-430d-82e3-93d48d3f220f",
                        "_bea68f9e-5348-40dd-ac14-75c41a6a38bd",
                        "_bfdc67ea-b21a-4f48-a425-0c715c7e16e0",
                        "_bfdc67ea-b21a-4f48-a425-0c715c7e16e02",
                        "_ca002966-c9a3-4a17-a12d-1cd32c9d9a7e",
                        "_cce95c87-f08f-47b7-9cdf-f0189a92572f",
                        "_d055ace9-3bd3-49d8-a0ae-99f4e21efe76",
                        "_d055ace9-3bd3-49d8-a0ae-99f4e21efe761",
                        "_d5a5feb2-8345-487c-a1bc-af3829329391",
                        "_da1cb116-0730-4a00-b795-8ab0b52ad89f",
                        "_ddcb76e0-13ea-413f-9a8f-553d78782f76",
                        "_df2d3155-4436-4542-8d3b-64241c7433be",
                        "_e207f382-e138-4a26-a40d-6c01dda96879",
                        "_e537f16f-6107-47a6-a728-797ac246d777",
                        "_e6c72199-8db4-4674-bdd8-d6808afb115e",
                        "_e80822fa-fc7f-4a11-a084-10b4893b7546",
                        "_e80822fa-fc7f-4a11-a084-10b4893b75461",
                        "_e8889178-5295-439f-916c-e28eff9141be",
                        "_e8889178-5295-439f-916c-e28eff9141be1",
                        "_e9171d11-8877-48ab-a50a-7ede9d4ec4b6",
                        "_ebe4f8e3-7bc8-4162-8bdf-e100742b2363",
                        "_f454fa68-0bd5-4e4e-92d6-6108176ad3bf",
                        "_f454fa68-0bd5-4e4e-92d6-6108176ad3bf1",
                        "_f9f95a0f-f165-4717-b44a-384e7d6af5a1",
                        "_f9f95a0f-f165-4717-b44a-384e7d6af5a11",
                        "_fa8eb432-3107-4562-95fa-7f35d75101b0",
                        "_fc9a338c-df48-4c21-91ea-c9286542d388",
                        "_fc9a338c-df48-4c21-91ea-c9286542d3881",
                        "_fdae97f0-ee12-4d0e-9bba-e994b2ca78fb",
                        "_fdae97f0-ee12-4d0e-9bba-e994b2ca78fb1")
                // Topological nodes only for voltage levels as connectivity node containers
                .topologicalNodes("_5c74cb26-ce2f-40c6-951d-89091eb781b6",
                        "_99b219f3-4593-428b-a4da-124a54630178",
                        "_a81d08ed-f51d-4538-8d1e-fb2d0dbd128e",
                        "_e44141af-f1dc-44d3-bfa4-b674e5c953d7",
                        "_f70f6bad-eb8d-4b8f-8431-4ab93581514e",
                        "_f96d552a-618d-4d0c-a39a-2dea3c411dee",
                        // Boundary
                        "_f03d65b2a51049ffa533e433721145c1",
                        "_d4affe50316740bdbbf4ae9c7cbf3cfd",
                        "_9d25a1f9e5d14d47b6dcde99c4380b40",
                        "_8d7bad8bcc634e0796e362390d9040b6",
                        "_1fa19c281c8f4e1eaad9e1cab70f923e",
                        "_1098b1c9-dc85-40ce-b65c-39ae02a3afaa")
                // No switches are defined, it is a bus branch model
                .acLineSegments("_17086487-56ba-4979-b8de-064025a6b4da",
                        "_78736387-5f60-4832-b3fe-d50daf81b0a6",
                        "_a16b4a6c-70b1-4abf-9a9d-bd0fa47f9fe4",
                        "_b18cd1aa-7808-49b9-a7cf-605eaf07b006",
                        "_b58bf21a-096a-4dae-9a01-3f03b60c24c7",
                        "_ed0c5d75-4a54-43c8-b782-b20d7431630b",
                        "_ffbabc27-1ccd-4fdc-b037-e341706c8d29")
                .transformers("_84ed55f4-61f5-4d9d-8755-bba7b877a246",
                        "_a708c3bc-465d-4fe7-b6ef-6fa6408a62b0",
                        "_b94318f6-6d24-4f56-96b9-df2531ad6543",
                        "_e482b89a-fa84-4ea9-8e70-a83d44790957")
                .transformerEnds("_1912224a-9e98-41aa-84cf-00875bce7264",
                        "_2e21d1ef-2287-434c-a767-1ca807cf2478",
                        "_35651e25-a77a-46a1-92f4-443d6acce90e",
                        "_49ca3fd4-1b54-4c5b-83fd-4dbd0f9fec9d",
                        "_5f68a129-d5d8-4b71-9743-9ca2572ba26b",
                        "_664a19e1-1dc2-48d5-b265-c0630981e61c",
                        "_81a18364-0397-48d3-b850-22a0e34b410f",
                        "_e1f661c0-971d-4ce5-ad39-0ec427f288ab",
                        "_f58281c5-862a-465e-97ec-d809be6e24ab")
                .ratioTapChangers("_83cc66dd-8d93-4a2c-8103-f1f5a9cf7e2e",
                        "_955d9cd0-4a10-4031-b008-60c0dc340a07",
                        "_fe25f43a-7341-446e-a71a-8ab7119ba806")
                .phaseTapChangers("_6ebbef67-3061-4236-a6fd-6ccc4595f6c3")
                .energyConsumers("_1c6beed6-1acf-42e7-ba55-0cc9f04bddd8",
                        "_b1480a00-b427-4001-a26c-51954d2bb7e9",
                        "_cb459405-cc14-4215-a45c-416789205904")
                .shuntCompensators("_002b0a40-3957-46db-b84a-30420083558f",
                        "_d771118f-36e9-4115-a128-cc3d9ce3e3da")
                .synchronousMachines("_3a3b27be-b18b-4385-b557-6735d733baf0",
                        "_550ebe0d-f2b2-48c1-991f-cebea43a21aa")
                .modelProfiles("urn:uuid:0eb1540d-5ca3-40ab-9bf7-c91ec4c5d7c3",
                        "urn:uuid:2399cbd0-9a39-11e0-aa80-0800200c9a66",
                        "urn:uuid:2399cbd0-9a39-11e0-aa80-0800200c9a66",
                        "urn:uuid:2399cbd1-9a39-11e0-aa80-0800200c9a66",
                        "urn:uuid:239caf0a-38bf-468b-a4d8-15f619886c58",
                        "urn:uuid:52b712d1-f3b0-4a59-9191-79f2fb1e4c4e",
                        "urn:uuid:9e383122-10cf-4da3-9785-f404b3541588",
                        "urn:uuid:c2960b34-0a04-4cd1-9c4d-f3112d85ec6c",
                        "urn:uuid:d400c631-75a0-4c30-8aed-832b0d282e73",
                        "urn:uuid:d400c631-75a0-4c30-8aed-832b0d282e73",
                        "urn:uuid:f2f43818-09c8-4252-9611-7af80c398d20");
    }

    private static FakeCgmesModel expectedMiniNodeBreaker() {
        return new FakeCgmesModel()
                .modelId("MiniNodeBreakerBaseCaseComplete")
                .version("unknown")
                .nodeBreaker(true)
                .substations("_3f64f4e2-adfe-4d12-b082-68e7fe4b11c9",
                        "_974565b1-ac55-4901-9f48-afc7ef5486df",
                        "_af9a4ae3-ba2e-4c34-8e47-5af894ee20f4",
                        "_b3e5b4de-b74d-43b4-8db9-784302a12acf",
                        "_d6056127-34f1-43a9-b029-23fddb913bd5")
                .voltageLevels("_051b93ae-9c15-4490-8cea-33395298f031",
                        "_0d68ac81-124d-4d21-afa8-6c503feef5b8",
                        "_347fb7af-642f-4c60-97d9-c03d440b6a82",
                        "_5d9d9d87-ce6b-4213-b4ec-d50de9790a59",
                        "_6f8ef715-bc0a-47d7-a74e-27f17234f590",
                        "_8d4a8238-5b31-4c16-8692-0265dae5e132",
                        "_93778e52-3fd5-456d-8b10-987c3e6bc47e",
                        "_a43d15db-44a6-4fda-a525-2402ff43226f",
                        "_b2707f00-2554-41d2-bde2-7dd80a669e50",
                        "_cd28a27e-8b17-4f23-b9f5-03b6de15203f")
                .terminals("_01a240e9-5607-4844-9d53-5c8b08b5c9a8",
                        "_01df618d-a8cd-493e-ad4a-793ae42c427f",
                        "_01f33c90-d1ca-448f-9d62-13677c5c7a5f",
                        "_02820aae-8266-4c45-96de-5640eff65320",
                        "_04596a73-8298-499d-81d5-2bd97b90bc94",
                        "_04684742-c766-11e0-1111-005056c00008",
                        "_04684742-c766-11e0-2222-005056c00008",
                        "_04684742-c766-11e1-1111-005056c00008",
                        "_04684742-c766-11e1-2222-005056c00008",
                        "_04684742-c766-11e2-1111-005056c00008",
                        "_04684742-c766-11e2-2222-005056c00008",
                        "_0593fd2d-7e55-4a8d-8ddf-4f8a576cf26c",
                        "_07865469-d39d-4234-8cee-fa998fa49bed",
                        "_0787d496-6606-4dc2-a363-3a7b4ab437fc",
                        "_07a28042-960a-4fe9-9d8e-9abcd70078c5",
                        "_08514e88-e6b6-4a2f-ad85-1021568dcece",
                        "_0c19b22d-e00e-4223-b1c0-1ef70038795b",
                        "_0cc10ecd-426c-4aa9-8a40-541c79204113",
                        "_0e0a7e38-7c23-436c-973f-76d23ab89027",
                        "_0f0cd299-c8ad-49b9-9c6f-40b7c90caef9",
                        "_0fa430b4-d98c-43bd-9652-f61fb00e7340",
                        "_0fd8256c-1eb8-4ff8-88c6-5b14842e3c00",
                        "_10af2a5e-765c-4e4c-8c7c-f1e589be90de",
                        "_11e5ac40-a08b-4246-8aa2-a4735171ed44",
                        "_11f4f138-5e43-4a82-88bf-7c57d50813dd",
                        "_12373941-f783-4b0a-a24d-904c0b7a4d2e",
                        "_138c9c5b-9472-4831-ad22-f1b7b319b0a3",
                        "_1425f9f4-9037-4086-9c0f-9b1f007eca48",
                        "_1621a8f0-f63d-4869-b42a-db0d9364db22",
                        "_16918aef-e2ae-421f-bfa4-7d3c1e749bb7",
                        "_171b75ea-d84d-4884-9780-5212780d4154",
                        "_1750b98e-7a97-4951-bfc0-f090eafd1b79",
                        "_17b8d4b2-20a5-4486-8590-95b32b6aaa21",
                        "_187a1ad0-2749-4bd6-8664-a6258e7decc1",
                        "_19a1b3be-397a-4c6f-ad6e-57b6db72e65b",
                        "_1a6456c6-fb39-42a0-b21d-089093ba7c49",
                        "_1b32b063-7f09-4eb5-8071-58501c5d296c",
                        "_1d058982-f5ff-4bcc-abf4-e0037ff511bf",
                        "_1d68fb97-30d7-4c37-8d2e-31324a274ffb",
                        "_1dd51f56-f284-42e8-a7d4-3084da9312bc",
                        "_1dfffea9-1fac-48f1-b466-1312643f3a47",
                        "_21b69381-c805-4fc1-968a-7969c3bc451a",
                        "_224f7bbc-4253-4642-a886-eb9b45154612",
                        "_239cc8c7-6c51-4c18-b9ac-b0c88ad3b38c",
                        "_23ab327a-2d61-4fee-ac8d-ab1f570f9c77",
                        "_23c7d2cf-57aa-46d3-bfe6-571211b901ca",
                        "_25af8438-9b28-446c-bb2c-e9d64aae2111",
                        "_2731a0ed-82af-4e6a-bf8f-65bfb89a2d99",
                        "_284c13ce-0b59-4e75-868a-9db150506504",
                        "_297eac16-1730-41af-911d-ba0c6d64b9bd",
                        "_2a864763-0d92-428b-a813-c665e878dec4",
                        "_2bff438e-6dfd-4ff6-91fc-d94f5cb5441f",
                        "_2ca5c7d4-9b73-44fd-b9f1-e8353ed93d9b",
                        "_2e6ef544-c98e-44ec-a8de-8c44d2cccc33",
                        "_2f11bc44-be19-4a25-8ac0-c0d7fc4816f3",
                        "_2f6359c5-2e17-40e4-9daf-3a8c0e51a680",
                        "_2fa56ee8-8afb-4db2-8fcf-89b9fa135bca",
                        "_2fadea4c-9336-4247-a3ea-a621c79cb544",
                        "_33b68bdf-f081-4428-972f-7f5f0dd33b8c",
                        "_3434b50d-677d-4ad5-96d7-6c7d1be611e6",
                        "_368c0e1a-6dff-4929-81db-19d77f04b242",
                        "_37ed18b5-9040-4b03-9622-9c5481e36d05",
                        "_37fb1b60-4171-4977-a82f-ce6c160b52b2",
                        "_39242d8e-e722-413a-814b-0aabad87cf6e",
                        "_3a845e4b-a2d0-45ab-a557-428ffdaee5ed",
                        "_3d4a821e-3c82-4501-a982-a40a58b99d7e",
                        "_3d6a6b41-0ff8-418a-9530-94c6752b2db0",
                        "_3df2cd7a-edb6-4fab-ac4c-5619d7d6c30a",
                        "_3ee6718d-4e99-4c27-aa05-b9f4c08df9ed",
                        "_3fb40e0a-bb4a-4967-8ba3-3e94b8153554",
                        "_3fc6a85d-1528-46f2-a3a0-502a1adc2da6",
                        "_40a6d6e8-9175-445c-977c-b0daab3c7f0d",
                        "_43f700ce-3882-4906-b41f-b7c4eb2e74e0",
                        "_449bd7ff-89c2-42bf-8f2f-374766f5998d",
                        "_47c7eebe-1623-42a9-999d-3fc362b6da45",
                        "_4915762d-133e-4209-8545-2822d095d7cd",
                        "_4b71493f-425b-4f1d-ab96-f087d6d79d7e",
                        "_4c355eec-daf6-4639-8af6-1c397eb1c061",
                        "_4dec53ca-3ea6-4bd0-a225-b559c8293e91",
                        "_4eaf0e87-ff3b-490f-a9d9-f4b176526d64",
                        "_5042af3b-8c3a-4548-b2fa-a8d655f94402",
                        "_51780311-f1c8-4757-90d3-970f749bad9b",
                        "_51ac3672-026d-4257-9c68-7c7f57a9b55e",
                        "_52cb56c2-0251-431b-b2ab-70de47ceace1",
                        "_5492183a-1e6f-4dee-a506-f389c69f4c83",
                        "_55ee67e4-02df-4acd-8117-c43987a52ff0",
                        "_55f3f6b2-fcef-4cca-9ccb-e729b36231e7",
                        "_563c5f79-a51d-4ce8-a921-0e86dd984bb5",
                        "_563df48e-ea91-4aa6-9e7d-0beb477b4df8",
                        "_56ffd36b-6acc-409b-ba46-7ad7ed7b9702",
                        "_58a19099-c22b-421c-bf57-9a7098fdcada",
                        "_58bca65d-d7b1-4c05-b433-f39e10a916b6",
                        "_5abad0fd-8229-4c16-867c-7cc6b31a91d1",
                        "_5af3b857-165c-4f96-b415-0d6e2e9ca27f",
                        "_5c7f2182-6d27-491a-b1e8-5e48c2bced16",
                        "_5cc19236-ff11-44e9-a321-dc932b96ec2f",
                        "_5d820300-529c-4622-ab38-cc4374bd081c",
                        "_5f5082df-e28b-4a83-85f4-37b18687ba6b",
                        "_6038e540-8447-4790-b307-742767d48f15",
                        "_60de37d6-c659-40e1-bf7b-2ab7d59b5d0f",
                        "_616ef15c-065f-4ca2-8367-d1aad079357e",
                        "_6346b12f-326a-4648-bf6e-7bac1bcc49ee",
                        "_64050cf7-60b8-4d43-a471-55e2386fcdbd",
                        "_654343eb-a916-4b23-8f63-2dbb93f8c60b",
                        "_6a16e43b-81d7-4acc-ba40-cb0e4f099d8a",
                        "_6c1b3247-5c23-4080-854d-96af3af219c7",
                        "_6cd98e8c-ede7-46b1-aa01-7f9f7db26416",
                        "_6d733695-7db0-46fb-b940-e220b2272f57",
                        "_6ea87619-9cd4-4d25-8876-e39ffb092138",
                        "_706707e5-019e-4549-b981-a857f1dfa611",
                        "_7145f995-b4a7-472e-9c58-2f8540ad3925",
                        "_72e34044-2af0-4b04-9ee9-e69aaadc0807",
                        "_734b0038-78d2-4373-82d6-e7ee819fcaf5",
                        "_779905a2-f53f-4a01-b39f-70654cd83b37",
                        "_77e5f07e-b2a4-44f1-a321-68726d99ee04",
                        "_7a6a78cb-5c02-4706-a5ba-020d7d5369d5",
                        "_7ad619e2-6d13-44cc-9ebe-cb674fb137ae",
                        "_7b7d05e5-8d78-46e2-8065-958e57258ceb",
                        "_7cf9455b-5c62-4a22-bbde-fe8c6d7275e0",
                        "_7cfa863e-28f0-4f89-9d1e-1460f0856d80",
                        "_7fe566b9-6bac-4cd3-8b52-8f46e9ba237d",
                        "_81449772-3530-4973-be76-dc0cc7590229",
                        "_824db363-9d54-481d-9012-4cb5ae3f0a25",
                        "_82611054-72b9-4cb0-8621-e418b8962cb1",
                        "_83489ba7-933a-47c9-bb14-9a9a53ec3000",
                        "_8372a156-7579-4ea5-8793-24caf0d24603",
                        "_840b6263-d00b-49c4-8b65-84f4c0ae2a0a",
                        "_85d4137e-4559-4915-985c-0571e218e19c",
                        "_863647cc-d576-484a-bde3-e8998f9f021a",
                        "_8793d235-8e5d-4706-81ed-b569289f5565",
                        "_890ab3df-c021-4f4d-9333-22b01240c9e1",
                        "_89df2b1b-9107-45a8-95ae-16afa04d99b3",
                        "_8bdafc99-ed74-4569-a175-3b69eb0f592c",
                        "_8e922bca-b992-472f-9cce-322c669d50d1",
                        "_8f4a8d13-3695-4115-b765-c93eafb155ee",
                        "_912cc25e-78d9-43a6-a77e-b2b7bb76688b",
                        "_9165ded7-ec43-469a-b006-554d388a63ec",
                        "_917c8eea-f432-4a03-b9b6-03479fd7bc6f",
                        "_933078aa-6687-4922-bb1f-4c1457f7c26f",
                        "_952e500f-4609-412d-a368-5a4d74ec78d9",
                        "_95c8dbad-f5ce-4e51-834e-8da51351dd49",
                        "_9bab684d-c68a-4d54-8884-5b36faf7fcbe",
                        "_9bcf9e20-557c-4d96-8226-e4a56014c3ef",
                        "_9c535ea3-28f2-4029-bc08-60282cec3213",
                        "_9c54c0f9-ffa9-41e1-8d96-e9ccec4ca73e",
                        "_9c86faec-f24c-4cbf-a1e6-9c9e1288de24",
                        "_9ce03e95-44ba-487b-9cf3-04d968077ff9",
                        "_9dca17f5-274f-4716-b0a9-0d0eef5fa400",
                        "_9f666788-e66c-4cfc-827f-de5ccba9fd2d",
                        "_9fff761f-9fa8-40ed-a802-48bbd0632769",
                        "_a01745f7-1965-4c49-b53d-0f3180aa9e17",
                        "_a02cfa66-d32c-45f7-9a4d-ae1e02efcb6a",
                        "_a1158b5f-3b71-4c44-8b15-4150d983f73e",
                        "_a48cca94-4d2d-4e8e-97e7-52f5635b5a3e",
                        "_a9aff7a4-f0a1-4745-8e16-9c80aecc38be",
                        "_a9d77a71-a9b4-44cb-9c4f-e52492b37980",
                        "_ab273d2b-0715-4fbe-91fb-f1113e15759f",
                        "_ac39e330-965f-4a05-bd93-2e338aa1e7ce",
                        "_ac3f7dcd-3ed3-423b-baa8-bdaccbc93834",
                        "_ac5bfe2f-7f34-46ec-9c20-93a034af23d4",
                        "_ac72ebd7-9315-405f-9c62-cef2078ca70d",
                        "_ac754d2e-5ed9-4466-a351-aa861198e255",
                        "_adcc04eb-c3c4-47c2-9004-09b2a43b1dee",
                        "_b1597fc9-786b-4142-a321-2f80bdc61ced",
                        "_b2112f10-1057-4a28-a18d-a1952c791c4c",
                        "_b38253aa-c786-4532-8776-09088beb0a0c",
                        "_b3c8d494-0a24-49fe-94fe-adcb64af5f2c",
                        "_b542f80e-eefe-4771-8eb2-f63529d26ede",
                        "_b58d9a22-7735-418e-b046-8b72c2f65ae9",
                        "_ba0cc755-9201-4d57-8206-3fa57b147583",
                        "_ba98fd7b-b3e5-4b37-b218-df5f1e08ead5",
                        "_baa12311-4df0-4662-964a-701b03f31b73",
                        "_bb2136b7-d699-4b34-9b30-d9047d534974",
                        "_bbf95af6-337a-4307-b305-6733a9fd28f8",
                        "_beb46906-7d88-4689-9a95-65d87344cfe0",
                        "_bf748e1d-94a2-40e1-a1bf-a3a8d88abcd8",
                        "_c086e4b7-3b1b-4a1f-9c89-58ccd10f4491",
                        "_c124c099-9921-4276-b070-3961632dca63",
                        "_c1b14884-b320-4989-b362-de40b5245072",
                        "_c239382f-9dd0-4d7c-ba5c-a4064dd94fca",
                        "_c347ba7b-5eca-4de0-8487-3489436ec008",
                        "_c39420a7-76ad-4bcc-afdf-da398485bf28",
                        "_c394efef-2d00-4236-becd-b9a99545e7b9",
                        "_c4a0c1fc-1d63-4dd9-846b-090626d8584f",
                        "_c833199a-0d18-415d-afa2-86e007434894",
                        "_c83f924b-18d1-4cb9-a91d-616e8b347ac2",
                        "_ca0088fa-c27d-466a-ab97-e2f0029baed7",
                        "_cb18d1c1-2835-4d6f-b98b-3ca2f28c2fa2",
                        "_cb8687b7-c25e-427f-841f-7d1380a2e274",
                        "_cd41ef76-36d6-44e0-80c5-d8b8c77eea5a",
                        "_d2a672b4-28a4-47fa-9102-c1100bc4dc49",
                        "_d5b02a21-e6f5-441c-81f2-4accb1a56ddd",
                        "_d72b6257-6b28-47d2-ba37-61630d9d9647",
                        "_d7f0a22b-afbc-41d2-b919-8fde5d1c5045",
                        "_d8b520f2-938b-4cad-ab26-a339b1908f64",
                        "_d8ddf8a1-e5e1-471e-b6bc-e54368130390",
                        "_daa62f13-06fb-470d-a027-8f09ecfc85d6",
                        "_dbede101-7eec-40b8-9f22-b9cc9109bbd6",
                        "_dd7aaa6c-3b38-4ad7-b7e0-61f0fe0c2ce9",
                        "_ded03fef-e28a-43f4-8e06-7330708e0f81",
                        "_e2854830-a0c3-46ef-92a7-70e7fd1c783b",
                        "_e29ee98e-b807-415d-be0c-9126a3e39b92",
                        "_e3420c0c-d6fc-49ee-9447-74c8e1eba64e",
                        "_e4b01a0e-fd58-41d0-ae5a-583ecadf6b43",
                        "_e562be25-b44e-4f1f-834e-ec46dbfb689e",
                        "_e59d3a8c-8382-413a-95ae-6354dfe85565",
                        "_e6115f33-a5e0-44c3-a300-af9ce4d7614b",
                        "_e661d283-0a97-43df-825f-6ed99a267448",
                        "_e7128a94-8515-4934-949e-8e86ff612c74",
                        "_e7daaf22-0533-4753-a9ef-7f11979a0d59",
                        "_e87a69d3-296b-4497-8dc5-5ae782fdb184",
                        "_ea3b457b-9508-4f84-8715-b7df2019068b",
                        "_ea74068d-472d-458c-9abc-cdd5437616c9",
                        "_eb6825bf-0004-4845-88f2-6e4ba54eab89",
                        "_eb8ed3e6-c0de-47e2-9dd5-52b321d51b70",
                        "_ee0315f9-835c-4b1f-91e6-df688fa5cea0",
                        "_ee65527d-b472-4dca-82b2-46fda4781ca4",
                        "_eed80693-c239-446f-b83e-a566c96f4b12",
                        "_f161d809-ed9e-40ad-bc58-e8a746f8afcb",
                        "_f2255df3-1730-467c-b42b-77e6e8c919bc",
                        "_f48c1439-dd5c-4045-ad78-760321438b23",
                        "_f4f76784-0aaa-48ba-98b7-d8f363e4b833",
                        "_faf9bfb2-81b7-4dc5-a9b5-3d7cc05c6aeb",
                        "_fb51a5d5-4917-492c-9d66-24aeb6f38b41",
                        "_fc2c3298-0a43-4e6c-adb0-24740392b456",
                        "_fc90d1be-938f-4d95-8416-e1516f9bcccc",
                        "_fda2c67c-d8c4-475b-a791-4ab5f6422da1",
                        "_fdec908f-fe51-492d-a385-4db764d5f58b",
                        "_fe141bda-142c-4438-83d0-dae0d0ae199a",
                        "_fe962374-f78f-4ed9-becd-f320150fc65f",
                        "_fecfd76e-409a-42f1-bc4a-eb538bf07b8f",
                        "_fee53b4a-5459-4c52-9a84-c4c8c5af6c44",
                        "_ff096281-10e6-44c9-af78-8e2829cff084")
                .operationalLimits("_03f63965-4a6c-48c1-b859-b1247a48a49f",
                        "_088eb5f2-e13b-4edf-a30a-ff48b7a8ae41",
                        "_10be2112-5068-4819-b82a-a640e4df11a6",
                        "_1162b24f-783f-4fe9-afce-24bac9cb061a",
                        "_1279ed2c-6ab6-46d8-af8c-d32d133f3589",
                        "_13f165da-ae31-466a-826e-4012f2eac2cd",
                        "_18dac733-f9c1-485c-a62d-f90e9958cd99",
                        "_1ad2d2cf-4c58-4709-ab69-6e3fd948b3b1",
                        "_1ea781c3-c283-414b-8f9b-32007d3552a5",
                        "_2087197f-6dad-4625-a751-7d1df6b6ca80",
                        "_25eae7ae-45b2-4485-8721-b9e9e2284ab7",
                        "_2b31b2eb-93d0-4642-8d7f-eb353722cea1",
                        "_2b650521-4b6f-450f-a5f8-afccbccd9260",
                        "_2b7585e3-85ea-496e-b6ed-76349fa65841",
                        "_2fff858a-1bab-41e0-bbbf-7f20d3f38cf1",
                        "_339876d4-a3b4-49f7-8969-c29d7e222897",
                        "_35b531f8-d134-4a3f-a176-7759bde52ece",
                        "_388327a4-d5da-477f-b9dd-a45edaa5b2f0",
                        "_3b71d2e4-8be2-4821-9b04-1c3450aea420",
                        "_3d101345-5841-44af-bbab-88ed4f251baf",
                        "_3e21a1e3-c6a7-4e12-8fa9-20eac1e20dcc",
                        "_3e684ef9-64c7-407e-8e9a-44720b63efac",
                        "_40823ca5-dc1c-42ee-8b0b-83c7de4a3104",
                        "_411da062-b032-4029-8a40-3ff07ad69818",
                        "_427b3d77-d05c-4299-9a39-54b0de217dbb",
                        "_44b184ba-0034-4f10-acd9-5fe976632461",
                        "_46e3ac62-4de9-4a04-8f8f-dc6ce15a125e",
                        "_4b476a28-d0f0-4aa0-8aa7-293840fa64ac",
                        "_4e4fd255-4795-4c56-ac86-2e139e02e799",
                        "_503ef306-11a4-451a-83eb-4b256f6698b2",
                        "_50ef4486-e407-4efb-906a-259e124b975b",
                        "_512568f1-b159-4158-8b32-4edc95d784bb",
                        "_5864da77-5083-45eb-962f-349a6e5a1063",
                        "_5915064d-0ee3-441d-b160-21acc094008b",
                        "_5aab6f38-8337-4c05-921c-afb639bfee4e",
                        "_5b545c73-df9c-4d25-bca2-62a57dd0b01b",
                        "_5b8bbc1e-6e73-43a2-af19-cefabdc56b54",
                        "_5d2a6568-5610-4f15-ae82-d9b6ad5778b7",
                        "_62224afb-e38e-40f3-8af8-8df8e1f2fd95",
                        "_62f48d08-8ac8-4e9a-9430-67ac0f5aab59",
                        "_63551f30-7dda-4ddb-8f27-999f18779da4",
                        "_65bc1d48-d43b-453c-b507-acb0966f8d2d",
                        "_6b58bc5d-b2b2-4fda-ac1a-f715f9827d17",
                        "_6cb3ee02-4526-40a3-9c95-1d17d524aae7",
                        "_76ca5aef-b793-42e7-9f3b-6f0db50631d9",
                        "_799cc174-c824-4015-8889-121bde37245d",
                        "_7a187956-2f45-43bf-b548-464f76810cc1",
                        "_7a5635e4-ea6d-491c-9cdc-73a1db69c3e0",
                        "_7dd09c51-89c1-4391-a4f0-842c95f23f4e",
                        "_84c02220-11c1-4198-b81a-d58c41873fb1",
                        "_91716cae-f695-473a-9586-82daed44ebf7",
                        "_94530374-3c11-4ffb-ad0f-59e38e3a7891",
                        "_9607451a-b19f-4841-93aa-e6757f277b9a",
                        "_96f4092e-628e-4649-8b06-7a96bcc1b989",
                        "_96f545a9-d779-43ba-b3c6-6c506b830cd0",
                        "_a94a2227-8925-41a7-ae14-739cab0a36b2",
                        "_af422211-ccbf-483c-97c9-2bbbd48f78f9",
                        "_afef4cb4-b859-415e-af9a-f2cdfd1d7876",
                        "_b2937835-f761-493b-a52c-b0df96831f5f",
                        "_b378a9d1-836e-41db-9e27-515aab8e516d",
                        "_b3ea9d58-113b-4cbb-887b-9af0de3280ac",
                        "_b43a0fbc-5a9c-44ac-a9a0-7885d5efbd93",
                        "_b49709a1-3039-48d5-9f81-efa5a42ea3da",
                        "_b5092424-435b-4e9b-9ac2-9a68c710a987",
                        "_b5d817a9-1d98-4609-93fe-74da3d295ccf",
                        "_b87b8f1b-b972-4800-88b1-69171f052ffb",
                        "_b9ac6108-d047-49da-a4e7-7fef4fc87baa",
                        "_b9b7509a-b3a1-4dbd-aaab-4db17394f383",
                        "_ba76dd94-af4b-4dca-bb06-60fcb8ef56a2",
                        "_c158c7c6-05a8-431c-951a-3b5641afbba5",
                        "_c3161303-d82f-4ede-b270-d7c2b7822f27",
                        "_c46c0958-4b79-4184-91a1-188c7a5959ce",
                        "_c5d40744-0e35-4d38-86a9-614261483a99",
                        "_c63497a5-89c2-4940-a40f-ef6ececc073d",
                        "_c6e13965-aa0f-43a4-9ab2-293253cb421b",
                        "_c73928d1-3ec0-4aa3-ab2b-35df61b2e18b",
                        "_c7b03b9e-a4f8-443d-8595-ba7152d45c65",
                        "_c936d958-7948-4d0c-bdf2-0377047aae44",
                        "_cc310538-02b9-42cf-8438-8ae55caa5780",
                        "_cd839287-33a5-482c-b848-1e62283f6a1a",
                        "_d1f82a75-6043-4eff-a40b-f6cfc608f190",
                        "_d64b670b-240d-42bb-9222-49ab6c131bdd",
                        "_d6ceab86-06b7-4bb3-91a7-c52e5e3c7557",
                        "_e2220586-2422-4a05-a34f-d932cce7567c",
                        "_e6887eaa-9704-4d02-ac77-1cbbe04cf063",
                        "_e906b8f2-2da9-4975-bf67-66bec9865ea9",
                        "_ea167db4-754b-4848-93a5-be269cfe4644",
                        "_f60bb615-492a-4942-9f93-2c67749d642e",
                        "_f6223efc-90c1-4d6b-8c83-f10c861d80d3",
                        "_f7db6e6d-a44e-4df1-b9ec-8b0711e96763",
                        "_fafc5f7d-b49f-497c-a4a4-d15df5eb8040",
                        "_febfc21c-435b-47a8-851c-c1b8e2400fa6")
                .topologicalNodes("_03163ede-7eec-457f-8641-365982227d7c",
                        "_37edd845-456f-4c3e-98d5-19af0c1cef1e",
                        "_764e0b8a-f2af-4092-b6aa-b4a19e55db98",
                        "_7f5515b2-ca6b-45af-93ee-f196686f0c66",
                        "_87c0d153-e308-4b2b-92a4-4fad53ab1ff9",
                        "_adee76cd-b2b9-48ac-8fd4-0d205a435f59",
                        "_b3d3b4ad-02af-4490-8748-70f6c9a23734",
                        "_c0adab49-d445-4609-a1a3-ebe4ef297cc8",
                        "_c7eda3d2-e92d-4935-8166-5e045d3de045",
                        "_c8726716-e182-4373-b83e-8f60070078cb",
                        "_cd84fa40-ef63-422d-8ee0-d0a0f806719e",
                        // Boundary
                        "_8372a156-7579-4ea5-1111-24caf0d24603",
                        "_8372a156-7579-4ea5-2222-24caf0d24603")
                .switches("_052682ba-a4e5-41d5-9728-0fa4e2e01011",
                        "_06917aa0-6cc2-4d46-b37b-6ed9fec32ccd",
                        "_0bc3b6a5-091e-4b8f-b9d9-a34d327473d0",
                        "_0f02c390-e5bc-4e8f-9936-2d269b8847ad",
                        "_1287758d-606d-44c9-9e93-2f465ebf54b7",
                        "_154f3756-4e07-4f06-9147-ed83de84e265",
                        "_15d6eb77-36d6-48d0-a227-25d29e839517",
                        "_20a4b1b1-5307-471e-85cb-f2b1e059dd4c",
                        "_22e27c40-eb38-4f04-aebd-7ef543798211",
                        "_2791a96c-c66c-44e7-a5dc-8331a8996c76",
                        "_27cb23c0-ad7b-4019-8cdf-205e240f742a",
                        "_285ccb1e-a867-4efc-880e-e871c1929376",
                        "_2af10fbd-4763-4940-b11d-00dcd7627016",
                        "_2b97cb41-b132-4359-ba18-6520d52acd72",
                        "_2c03a3cf-4890-4092-9fe3-c6d139677882",
                        "_2ec6cb16-7516-46f9-8cd8-2a52eea85bb5",
                        "_2ed632f6-36a3-4937-8d5d-ad105c57c071",
                        "_3006c97d-d51e-4e7b-92b1-a9a321f585ce",
                        "_315a9d60-b7ef-4652-bc3b-2b8c5d334dcf",
                        "_35ec1a87-3d57-4274-b9c2-ecb120180fab",
                        "_3a783d1d-1113-414d-ab67-34b139781196",
                        "_3eae0be8-9a2f-4355-89d6-865b340c2ec9",
                        "_4580fb7b-c31b-4b84-ad1d-1ca4d50f4fc5",
                        "_47143596-5947-430d-b14b-e00ac796985a",
                        "_4c49cfd1-4758-476e-95ce-5cd2c56e3165",
                        "_535445e8-b782-49a6-87e7-55946e1bbba1",
                        "_575ed362-86e6-4753-a05c-e0015504a9d1",
                        "_5938ea51-0cfb-4c6c-8d6f-c2bf3254c94a",
                        "_5e9f0079-647e-46da-b0ee-f5f24e127602",
                        "_62156fd8-595d-4ab0-af53-0de3b8056a6e",
                        "_622a9aff-f9d4-49c7-8f29-fd88009c5df0",
                        "_64b4c3d3-0116-4477-af5f-76fe482a4347",
                        "_6557ecb5-b9a7-4089-aa70-58d4d11f503b",
                        "_6b7dc761-737d-4452-9d3f-de9e14a7c801",
                        "_6f28743a-c690-4569-b229-977566414a16",
                        "_74534b01-b13b-49a2-b0d7-d0feb1348ef5",
                        "_788bb5ff-f36e-406b-b6a6-ea1b7268ba03",
                        "_79341979-e66c-4b08-8053-6d97216e0777",
                        "_7d655d91-394a-4f47-8f83-7f1de3baa056",
                        "_81ee8901-1cb8-4c51-ae32-d2adc6ad9077",
                        "_83317cbd-8326-44b8-a9fb-f69b6ba5a1cb",
                        "_84970563-073f-4316-a175-060b544640e0",
                        "_869b6ad5-3975-49ae-81fe-01547a229a25",
                        "_8e56ead3-f4d3-4cd9-a34f-c7340ff7384d",
                        "_8e57f735-6869-463e-959e-b517b15a5ec5",
                        "_8fa214d6-5b7f-4d04-8de7-e5609d9f9deb",
                        "_90e1b509-e6d1-4a3b-8dcc-587f8b362bd3",
                        "_9313f8a4-159d-48c8-92f1-36b1d16ee7c9",
                        "_9bde615c-44c1-4a7f-8801-72545a4e6c59",
                        "_9c0bdbb8-034f-4a0e-838e-5cdde5c91e9b",
                        "_9dc3bedf-0d30-4a56-ba5c-f2e69d7997ee",
                        "_9e8afc23-52fc-40e6-ab85-3d0328a228f2",
                        "_a5a962a6-2f47-4ef1-960f-e29131bcba36",
                        "_a772f653-51f9-498c-afc9-c37b2a49cf32",
                        "_a9b68255-188c-408d-8362-5f3b0a1e730f",
                        "_ac85d2fb-2417-4a86-9c63-d389e6ffae47",
                        "_aecb59fa-9af0-4d30-a865-009c2b35f9cb",
                        "_b0677acc-df2e-4730-9516-fab2403103d0",
                        "_b68ac335-9806-4609-812c-1f47f8738bde",
                        "_b7e79b65-8d3e-4ee9-bf05-12bf2250b12d",
                        "_b81c76fd-24fb-4888-a52e-5a9d0177df1d",
                        "_b8924fbf-6513-4385-b032-21045c0a5b8f",
                        "_ba645318-e12c-4f6a-b5e9-5dfe595ae477",
                        "_c1e02075-4fdf-41e1-b5e4-925bb2396011",
                        "_c217742e-f713-44b7-a0b2-7c78aebc165d",
                        "_c5bac804-64da-4b3a-8bdc-4c199a9d39ac",
                        "_c6727598-4dae-4226-a542-166766d2f702",
                        "_c7f7f032-1e4d-494a-9352-bad1a3056e3f",
                        "_c803aa12-a8c7-4491-8163-1a1c6ea60db8",
                        "_c8b06b9b-8876-4fb5-9279-5a396e3db63d",
                        "_cb5d0327-ad13-4b40-a74c-3d9888823205",
                        "_cd8813aa-3213-4d9a-994d-4a4513e458b4",
                        "_cf48defa-93b9-4723-8e41-8dab315ceee0",
                        "_d0119330-220f-4ed3-ad3c-f893ad0534fb",
                        "_d08c1582-0b3e-46ec-9e97-d70c423eefe1",
                        "_d6665f59-851b-4183-81b0-59c53e1baa6a",
                        "_dcba20ce-b619-471a-8ab0-2371d4d090b0",
                        "_ddb70bc6-7ec9-4042-9816-b7f5a601064e",
                        "_e1e0da2d-79f4-4ca9-8ca2-62532a91edef",
                        "_ea5a6982-018c-4ce6-85f6-78b5ab5dbfc1",
                        "_eb33e134-698f-4258-8361-4b5fa4c2f9de",
                        "_ef2f27d9-acde-4aad-a920-d61c98682f4d",
                        "_f48344b0-09df-498d-9517-3c51ca3ce8e6",
                        "_f5335138-f3b9-400b-abf1-e9b559c887b2",
                        "_f58f46ea-b73b-421e-9fea-a86126a95b10",
                        "_f88b1d2b-2f06-44ff-bfe5-8f1156e13092",
                        "_faf75564-a154-4528-9428-913e6501a21f",
                        "_fbdcf00d-8a07-4c62-9e39-86f459bea2be",
                        "_fdf5cfbe-9bf5-406a-8d04-fafe47afe31d",
                        "_fe1384c9-f1c1-4026-9f5d-10a3b22208b9")
                .acLineSegments("_05597934-b248-491e-803a-68ce6290f502",
                        "_1e7f52a9-21d0-4ebe-9a8a-b29281d5bfc9",
                        "_35df6abe-3087-4c27-a90a-12b5065333f3",
                        "_5150a037-e241-421f-98b2-fe60e5c90303",
                        "_56757c2b-550e-4843-886a-ed193f6eb21e",
                        "_d5d1cc4a-6297-4386-b6ce-16dc26f15feb",
                        "_e95a6228-ceac-4f0a-8b52-d35367b364dc",
                        "_efdd7f46-67e6-46e3-9dcd-a3b6f8c613a4",
                        "_f32baf36-7ea3-4b6a-9452-71e7f18779f8")
                .transformers("_411b5401-0a43-404a-acb4-05c3d7d0c95c",
                        "_5d38b7ed-73fd-405a-9cdb-78425e003773",
                        "_6c89588b-3df5-4120-88e5-26164afb43e9",
                        "_813365c3-5be7-4ef0-a0a7-abd1ae6dc174",
                        "_ceb5d06a-a7ff-4102-a620-7f3ea5fb4a51",
                        "_f1e72854-ec35-46e9-b614-27db354e8dbb")
                .transformerEnds("_03916530-aaea-41c2-b0a2-770d44470839",
                        "_063fef99-e19b-4ebe-92a9-1e8927fdca2a",
                        "_0a33f633-7415-4f95-b3c2-f3ddbee92644",
                        "_3b446af6-630a-46ee-bc76-d5543509b355",
                        "_4864d0c6-f4ca-477a-b944-9927edb37fa6",
                        "_4c7533e8-0c50-443e-8a8a-12574f04daf5",
                        "_59ef0c3b-5e98-4771-8e07-0544bdc64c4a",
                        "_6e5fd46a-a8d2-4f85-baa2-b6efac1ad5fd",
                        "_8f183bc1-d883-5b72-1918-ebb9fbe4b3e7",
                        "_a2ea9c4e-6793-6d52-a476-3dbb1da389cf",
                        "_c98e36e1-a53a-384c-357e-ab34d9335d77",
                        "_d2e671db-1ed5-4f8d-bbbf-54554b175428",
                        "_d99ae781-8809-4e59-b167-fc0106934b25",
                        "_fe6b71c8-5a63-4a10-a699-a6bf376e2e2f")
                .ratioTapChangers("_0522ca48-e644-4d3a-9721-22bb0abd1c8b",
                        "_4a8a5456-91ac-4bc9-b8e2-64eeeef78a1a",
                        "_8de2d157-15d1-42c7-b376-a8ae5b6c0e77")
                .synchronousMachines("_2970a2b7-b840-4e9c-b405-0cb854cd2318",
                        "_392ea173-4f8e-48fa-b2a3-5c3721e93196",
                        "_ca67be42-750e-4ebf-bfaa-24d446e59a22")
                .asynchronousMachines("_062ece1f-ade5-4d20-9c3a-fd8f12d12ec1",
                        "_ba62884d-8800-41a8-9c26-698297d7ebaa",
                        "_f184d87b-5565-45ee-89b4-29e8a42d3ad1")
                .modelProfiles("urn:uuid:2399cbd0-9a39-11e0-aa80-0800200c9a66",
                        "urn:uuid:2399cbd0-9a39-11e0-aa80-0800200c9a66",
                        "urn:uuid:2399cbd1-9a39-11e0-aa80-0800200c9a66",
                        "urn:uuid:239ecbd2-9a39-11e0-aa80-0800200c9a66",
                        "urn:uuid:239ecbd2-9a39-11e0-aa80-0800200c9a66",
                        "urn:uuid:239ecbd2-9a39-11e0-aa80-0800200c9a66",
                        "urn:uuid:239scbd7-9a39-11e0-aa80-0800200c9a66",
                        "urn:uuid:239tcbd4-9a39-11e0-aa80-0800200c9a66",
                        "urn:uuid:239vcbd3-9a39-11e0-aa80-0800200c9a66",
                        "urn:uuid:6fddfd60-de0d-11e2-8f45-b8f6b1180b6d");
    }

    public static CgmesModel expectedMicroGridType4BE() {
        FakeCgmesModel m = expectedMicroGridBaseCaseBE();
        m.voltageLevels("_69ef0dbd-da79-4eef-a02f-690cb8a28361");
        m.terminals("_13dcec71-4b02-4c0c-93a7-8e16db4aa0b7",
                "_67bb74f1-8620-4a32-9d7d-a44092d11d22",
                "_8171fc34-6891-40e0-92d1-da9f4ba69e26");
        m.operationalLimits(
                "_0068a5c1-9212-4366-8e0e-cf621a92a8b7",
                "_0068a5c1-9212-4366-8e0e-cf621a92a8b71",
                "_08322f60-4e75-4a00-a4e0-0c55bf588919",
                "_08322f60-4e75-4a00-a4e0-0c55bf5889191",
                "_1b0850d6-317b-40a3-aa98-040b64f9350c",
                "_1e793cd6-1608-46af-a3f7-b4d1cabc9d58",
                "_2bb91774-4c1f-4dd6-aeb3-87a3009306c0",
                "_2bb91774-4c1f-4dd6-aeb3-87a3009306c01",
                "_2eb0f5f3-1377-4ea8-b794-a86c28039603",
                "_2eb0f5f3-1377-4ea8-b794-a86c280396031",
                "_328d2341-1ab1-417a-9246-8aa0e8124e9c",
                "_328d2341-1ab1-417a-9246-8aa0e8124e9c1",
                "_33d5e916-21fd-4d24-802d-69c0eb173eaf",
                "_33d5e916-21fd-4d24-802d-69c0eb173eaf1",
                "_3707e6e7-69e5-4f10-bc43-2812519c2842",
                "_3707e6e7-69e5-4f10-bc43-2812519c28421",
                "_382e1966-5b98-4257-a0e5-e0cce107f85d",
                "_382e1966-5b98-4257-a0e5-e0cce107f85d1",
                "_3ee6fbe2-5862-473e-b09d-a44522c6bf9e",
                "_3ee6fbe2-5862-473e-b09d-a44522c6bf9e1",
                "_4dca5f0b-dadf-46c9-aa93-5ff360c120fd",
                "_4dca5f0b-dadf-46c9-aa93-5ff360c120fd1",
                "_4f36a64e-0494-47b0-a552-93db9d01eb8a",
                "_4f36a64e-0494-47b0-a552-93db9d01eb8a1",
                "_50c2510a-8f27-44f3-a3a5-64ae99b6c077",
                "_50c2510a-8f27-44f3-a3a5-64ae99b6c0771",
                "_51a7a0f8-8b3c-4a59-9890-55de5f7de19b",
                "_51a7a0f8-8b3c-4a59-9890-55de5f7de19b1",
                "_55d9cdaf-9b02-448b-9206-f12f3c8d2f64",
                "_55d9cdaf-9b02-448b-9206-f12f3c8d2f641",
                "_56a462e6-5117-456b-9d0c-8d62b9a1f14a",
                "_56a462e6-5117-456b-9d0c-8d62b9a1f14a1",
                "_58c959fd-3675-4ad4-a221-9647b57073dd1",
                "_58ccdb26-c046-4189-a1c2-e542a168f853",
                "_58ccdb26-c046-4189-a1c2-e542a168f8531",
                "_5ba29c54-df69-4e8f-8835-58f7deb762d9",
                "_5ba29c54-df69-4e8f-8835-58f7deb762d91",
                "_5f3bd045-4b3a-47f5-acce-6b45d332633b",
                "_5f3bd045-4b3a-47f5-acce-6b45d332633b1",
                "_6236459d-8471-44be-9b66-b271ac407165",
                "_648d7a22-9468-4c53-9528-99c9f82d3e45",
                "_648d7a22-9468-4c53-9528-99c9f82d3e451",
                "_69ef4dd8-9f2a-48f7-a230-c4cd695f48761",
                "_6a000ecb-b731-4cc0-9791-980a4d8c2891",
                "_6a000ecb-b731-4cc0-9791-980a4d8c28911",
                "_6c95290e-c4a6-4fa9-8d48-d4c847a1d2c4",
                "_6c95290e-c4a6-4fa9-8d48-d4c847a1d2c41",
                "_701a0052-ddb1-484a-885c-600c74063a6a",
                "_701a0052-ddb1-484a-885c-600c74063a6a1",
                "_7166b977-0713-4afd-9674-d5073c972321",
                "_7166b977-0713-4afd-9674-d5073c9723211",
                "_78a00357-6831-4773-a317-f0092621fb0c",
                "_78a00357-6831-4773-a317-f0092621fb0c1",
                "_8447ed5e-3d27-42c0-9e67-9affdda0be451",
                "_858ebaaa-eec6-48e1-b579-b10986e0ddb6",
                "_858ebaaa-eec6-48e1-b579-b10986e0ddb61",
                "_86143da2-d202-41e3-a1b4-7eb13ba09334",
                "_86143da2-d202-41e3-a1b4-7eb13ba093341",
                "_89893083-697f-45cd-bdce-14f57fed5914",
                "_89893083-697f-45cd-bdce-14f57fed59141",
                "_8a9d3c6a-e33f-4a3b-828a-4a696602e4d2",
                "_8a9d3c6a-e33f-4a3b-828a-4a696602e4d21",
                "_94489b05-9fba-4d28-a720-f2f731fcbeb4",
                "_94489b05-9fba-4d28-a720-f2f731fcbeb41",
                "_9732c968-c1c4-446a-b47b-9038f5a59724",
                "_9b2c1328-381e-4277-9042-94b0085f2b77",
                "_a47ed63a-6b8a-4ccd-aae8-d6781f644ce5",
                "_a47ed63a-6b8a-4ccd-aae8-d6781f644ce52",
                "_a4fce087-92c7-454e-a901-b3efceafca61",
                "_a4fce087-92c7-454e-a901-b3efceafca611",
                "_a94ca937-819e-4975-a1fd-34598e05d56e",
                "_a94ca937-819e-4975-a1fd-34598e05d56e1",
                "_b0c127b9-ba6a-4c95-9c9f-a331e9237bb0",
                "_b0c127b9-ba6a-4c95-9c9f-a331e9237bb01",
                "_b1714414-0394-42b6-b441-a664069554a21",
                "_b2f157c0-ced9-4692-b430-4864d5a665e4",
                "_b2f157c0-ced9-4692-b430-4864d5a665e41",
                "_b659ed1b-c1a0-459e-a858-6c3f7431f4f2",
                "_b659ed1b-c1a0-459e-a858-6c3f7431f4f21",
                "_bcf693fe-ccc9-443d-90d6-21d3702b4186",
                "_bcf693fe-ccc9-443d-90d6-21d3702b41861",
                "_bfdc67ea-b21a-4f48-a425-0c715c7e16e01",
                "_c1e9a43c-a753-4f7c-b847-b7807c3779b5",
                "_c1e9a43c-a753-4f7c-b847-b7807c3779b51",
                "_c23d7e6c-1e09-452e-a82d-70599ec197b2",
                "_c23d7e6c-1e09-452e-a82d-70599ec197b21",
                "_c8852f92-33cc-44eb-b977-d282bb648d2f",
                "_c8852f92-33cc-44eb-b977-d282bb648d2f1",
                "_c90ed10b-9e23-48b5-a0f0-3ee7729dc83e",
                "_c90ed10b-9e23-48b5-a0f0-3ee7729dc83e1",
                "_cd228cb6-24ad-45f3-b9a1-24361a743d22",
                "_cd228cb6-24ad-45f3-b9a1-24361a743d221",
                "_cdb0571a-cacd-4f39-9537-0bfdf3f0fd1b",
                "_cdb0571a-cacd-4f39-9537-0bfdf3f0fd1b2",
                "_cde82226-7cea-4d13-b0b0-f545a0e08a18",
                "_cde82226-7cea-4d13-b0b0-f545a0e08a181",
                "_d29ef207-67d3-47bb-82ea-9d82074dde55",
                "_d39031b2-8181-4b02-a4fe-2a4a5296dd51",
                "_d39031b2-8181-4b02-a4fe-2a4a5296dd511",
                "_d91f4be3-9191-4cf9-a271-5fd1f54465ce",
                "_d91f4be3-9191-4cf9-a271-5fd1f54465ce1",
                "_dea05113-ef3e-4161-957d-4602c874839e",
                "_df47a1fa-8065-4c82-bc45-f62e92caaf3e",
                "_df47a1fa-8065-4c82-bc45-f62e92caaf3e1",
                "_e16c3321-9a86-4975-ba5e-4788ac070e54",
                "_e16c3321-9a86-4975-ba5e-4788ac070e541",
                "_e1ae19e7-8bb7-42ce-8ae6-24893f16e366",
                "_ebe4f8e3-7bc8-4162-8bdf-e100742b23631",
                "_f2b59bb4-79ce-4f17-8fc6-c006407b6358",
                "_f2b59bb4-79ce-4f17-8fc6-c006407b63581",
                "_f49c1cf8-6dad-442b-8b6e-88d17969887f",
                "_f49c1cf8-6dad-442b-8b6e-88d17969887f1",
                "_f552f5f8-348a-4299-81e6-c8f5a39a6db3",
                "_f552f5f8-348a-4299-81e6-c8f5a39a6db31",
                "_f6fbfd23-8043-4d2a-a547-73d14bd161cf",
                "_f6fbfd23-8043-4d2a-a547-73d14bd161cf1",
                "_f8bd3d90-ecc2-4f16-9de9-22ba0e62c1f3",
                "_f8bd3d90-ecc2-4f16-9de9-22ba0e62c1f31",
                "_fad252e8-cd6e-4226-9c0f-3a9f1e43904d",
                "_fad252e8-cd6e-4226-9c0f-3a9f1e43904d1",
                "_fd227658-0e1b-4ecd-952a-c6b0307b1ea1",
                "_fd227658-0e1b-4ecd-952a-c6b0307b1ea11",
                "_ff466d18-e4f5-439b-a50a-daec2fa41e2c",
                "_ff466d18-e4f5-439b-a50a-daec2fa41e2c1");
        Set<String> tlremove = new HashSet<>(Arrays.asList(
                "_acbd4688-6393-4b43-a9f4-27d8c3f8c309",
                "_1c8440dc-e65d-4337-9d3e-7558062228da1",
                "_bfdc67ea-b21a-4f48-a425-0c715c7e16e02",
                "_1c8440dc-e65d-4337-9d3e-7558062228da",
                "_6f70e245-e075-4ed9-9f86-f137b7e33313",
                "_6f70e245-e075-4ed9-9f86-f137b7e333131",
                "_da1cb116-0730-4a00-b795-8ab0b52ad89f",
                "_5b77485f-20a3-4a19-8d15-e4038c81663f",
                "_69ef4dd8-9f2a-48f7-a230-c4cd695f48762",
                "_aaa63bb1-fa34-41a3-bd92-0637bfce549c",
                "_661b3900-dc24-4af5-8720-3e675b48b747",
                "_2e27135c-53b6-4bd0-a4c1-5312fdbf0704",
                "_661b3900-dc24-4af5-8720-3e675b48b7471",
                "_4af98ccd-29f1-4039-86cd-c23fc2deb3bc",
                "_2e27135c-53b6-4bd0-a4c1-5312fdbf07041",
                "_84d4dbeb-ef3b-43a1-9a7e-ce5713013498",
                "_7939fc42-08ef-4ce7-9912-97552a4db39a",
                "_0d6f26df-9f86-4df0-b00c-bfb23870257f"));
        m.operationalLimits().removeIf(tl -> tlremove.contains(tl.getId("OperationalLimit")));
        m.topologicalNodes("_23b65c6b-2351-4673-89e9-1895c7291543");
        m.transformerEnds(
                "_3c59d1b0-1ee9-4ca3-9086-4fe102b51b21",
                "_ba56158e-0c51-448d-999b-44cb0b3cebf5",
                "_bf76ac9d-0144-48f5-a24a-34ae15a455fb",
                "_e22f3c30-63f5-47bf-a8c4-fee2483d426c");
        Set<String> teremove = new HashSet<>(Arrays.asList(
                "_1912224a-9e98-41aa-84cf-00875bce7264",
                "_49ca3fd4-1b54-4c5b-83fd-4dbd0f9fec9d",
                "_664a19e1-1dc2-48d5-b265-c0630981e61c",
                "_81a18364-0397-48d3-b850-22a0e34b410f"));
        m.transformerEnds().removeIf(te -> teremove.contains(te.getId("TransformerEnd")));
        m.ratioTapChangers()
                .removeIf(rtc -> rtc.getId("RatioTapChanger").equals("_955d9cd0-4a10-4031-b008-60c0dc340a07"));
        m.phaseTapChangers()
                .removeIf(ptc -> ptc.getId("PhaseTapChanger").equals("_6ebbef67-3061-4236-a6fd-6ccc4595f6c3"));
        m.phaseTapChangers(
                "_36b83adb-3d45-4693-8967-96627b5f9ec9",
                "_63454a73-f439-45bb-951a-e7b193986571");
        m.staticVarCompensators("_3c69652c-ff14-4550-9a87-b6fdaccbb5f4");
        m.modelProfiles().clear();
        m.modelProfiles("urn:uuid:1a64ce59-b28c-4b43-bf21-50b51865d777",
                "urn:uuid:2399cbd0-9a39-11e0-aa80-0800200c9a66",
                "urn:uuid:2399cbd0-9a39-11e0-aa80-0800200c9a66",
                "urn:uuid:2399cbd1-9a39-11e0-aa80-0800200c9a66",
                "urn:uuid:75271ec4-af11-4a87-8137-d6d2b43b594d",
                "urn:uuid:806f9f1b-ff69-4fb5-80f9-a8f393d31ebb",
                "urn:uuid:80f1fec9-412f-4ca5-8706-d0d3382e9a71",
                "urn:uuid:8d6b5a63-e64e-4a25-955f-9c5e107382eb",
                "urn:uuid:96adadbe-902b-4cd6-9fc8-01a56ecbee79",
                "urn:uuid:96adadbe-902b-4cd6-9fc8-01a56ecbee79",
                "urn:uuid:af6ed855-b817-447c-b436-51d87f630214");
        return m;
    }

    private static final String ENTSOE_CONFORMITY_1 = "/conformity/cas-1.1.3-data-4.0.3";
}
