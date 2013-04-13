package com.talios.cucumberng;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CucumberFactoryBuilder {

    private List<Option> options = new ArrayList<Option>();

    public CucumberFactoryBuilder addOption(String key, String value) {

        options.add(new Option(key,value));
        return this;
    }

    public Object[] create() {
        return create(new File("."));
    }

    public Object[] create(final File baseDirectory) {
        CucumberTestImpl test;
        String sourceClass = findStackTraceSource().getClassName();
        String sourcePackage = sourceClass.substring(0, sourceClass.lastIndexOf("."));

        List<Object> featureTests = new ArrayList<Object>();
//        for (File feature : new File[]{baseDirectory}) {
            List<String> features = addFeature(sourcePackage, baseDirectory);
            for (String feature : features) {

                test = new CucumberTestImpl(sourcePackage, feature);
                for(Option opt : options) {
                      test.addOption(opt.key,opt.value);
                }
                featureTests.add(test);
            }
//        }

        return featureTests.toArray();
    }

    private static List<String> addFeature(String basePacakge, File feature) {
        String basePackagePath = basePacakge.replace(".", File.separator);
        List<String> featureTests = new ArrayList<String>();
        if (!feature.exists()) {
            throw new IllegalArgumentException("feature file does not exist");
        }

        if (feature.isDirectory()) {
            File[] files = feature.listFiles();
            for (File file : files) {
                if (!file.isHidden()) {
                    featureTests.addAll(addFeature(basePacakge, file));
                }
            }
        } else {
            if (feature.getPath().contains(basePackagePath) && feature.getName().endsWith(".feature")) {
                featureTests.add(feature.getPath());
            }
        }

        return featureTests;
    }

      private static StackTraceElement findStackTraceSource() {
          StackTraceElement[] elements = new Exception().fillInStackTrace().getStackTrace();
          for (StackTraceElement element : elements) {
              if (!CucumberFactoryBuilder.class.getName().equals(element.getClassName())) {
                  return element;
              }
          }
          return null;
      }

    private class Option {
        public String key;
        public String value;

        public Option(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }

}
