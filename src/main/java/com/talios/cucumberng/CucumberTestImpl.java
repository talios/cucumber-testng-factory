package com.talios.cucumberng;

import cucumber.runtime.Runtime;
import cucumber.runtime.RuntimeOptions;
import cucumber.runtime.io.FileResourceLoader;
import org.testng.ITest;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Properties;

import static org.testng.Assert.fail;

public class CucumberTestImpl implements ITest {

    private final String basePackage;
    private String feature;
    private String options = "";

    public CucumberTestImpl(String basePackage, String feature) {
        this.basePackage = basePackage;
        this.feature = feature;
    }

    public String getTestName() {
        return feature;
    }

    public CucumberTestImpl addOption(String key, String value) {
        options += key + " " + value + " ";
        return this;
    }

    @Test(testName = "Cucumber", suiteName = "Cucumber")
    public void cucumber() throws Throwable {

        CucumberTestNgFormatter formatter = new CucumberTestNgFormatter(System.out);

        String[] gluePaths = {basePackage};
        Properties props = new Properties();
        props.setProperty("cucumber.options", options);

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        RuntimeOptions runtimeOptions = new RuntimeOptions(props);
        runtimeOptions.featurePaths.addAll(Arrays.asList(feature));
        runtimeOptions.glue.addAll(Arrays.asList(gluePaths));
        runtimeOptions.formatters.add(formatter); //, new HTMLFormatter(new File("target/cucumber")) );

        Runtime runtime = new Runtime(new FileResourceLoader(), classLoader, runtimeOptions);
        runtime.writeStepdefsJson();
        runtime.run();

        formatter.done();
        formatter.close();

        if (formatter.getFailureCount().get() > 0) {
            fail(mkMessage(formatter, "failed"));
        }
        if (formatter.getSkipCount().get() > 0) {
            throw new SkipException(mkMessage(formatter, "skipped"));
        } else {
            Reporter.log(mkMessage(formatter, "passed"));
        }

    }

    private String mkMessage(CucumberTestNgFormatter formatter, final String failed) {
        return String.format("%s %s with %d failures, %d skips, %d passes", getTestName(), failed,
                formatter.getFailureCount().get(),
                formatter.getSkipCount().get(),
                formatter.getPassCount().get());
    }
}
