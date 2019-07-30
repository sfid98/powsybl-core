package com.powsybl.cgmes.update.elements;

import java.util.HashMap;
import java.util.Map;

import com.powsybl.cgmes.update.CgmesPredicateDetails;
import com.powsybl.cgmes.update.IidmChange;
import com.powsybl.cgmes.update.IidmToCgmes;
import com.powsybl.iidm.network.Load;

public class LoadOnCreate extends IidmToCgmes implements ConversionOnCreate {

    public LoadOnCreate(IidmChange change) {
        super(change);
    }

    @Override
    public Map<CgmesPredicateDetails, String> getIdentifiableAttributes() {
        
        Map<CgmesPredicateDetails, String> mapCgmesPredicateDetails = new HashMap<CgmesPredicateDetails, String>();
        Load newLoad = (Load) change.getIdentifiable();
        Map<String, Object> iidmToCgmesMapper = loadToEnergyConsumer();
        
        mapCgmesPredicateDetails.put((CgmesPredicateDetails) iidmToCgmesMapper.get("rdfType"),
            "cim:EnergyConsumer");

        String name = newLoad.getName();
        if (name != null) {
            mapCgmesPredicateDetails.put((CgmesPredicateDetails) iidmToCgmesMapper.get("name"),
                name);
        }
        
        Double p0 = newLoad.getP0();
        if (!p0.toString().equals("NaN")) {
            mapCgmesPredicateDetails.put((CgmesPredicateDetails) iidmToCgmesMapper.get("p0"),
                p0.toString());
        }
        
        Double q0 = newLoad.getQ0();
        if (!q0.toString().equals("NaN")) {
            mapCgmesPredicateDetails.put((CgmesPredicateDetails) iidmToCgmesMapper.get("q0"),
                q0.toString());
        }

        return mapCgmesPredicateDetails;
    }

}