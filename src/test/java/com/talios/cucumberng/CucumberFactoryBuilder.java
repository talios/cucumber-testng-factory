package com.talios.cucumberng;

import java.util.ArrayList;
import java.util.List;

public class CucumberFactoryBuilder {

    public static Object[] create(String basePacakge, String... features) {
        List<Object> featureTests = new ArrayList<Object>();
        for (String feature : features) {
            featureTests.add(new CucumberTest(basePacakge, feature));
        }
        return featureTests.toArray();
    }

}
