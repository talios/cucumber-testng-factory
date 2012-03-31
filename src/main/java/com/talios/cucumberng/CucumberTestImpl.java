package com.talios.cucumberng;

import cucumber.formatter.HTMLFormatter;
import cucumber.io.FileResourceLoader;
import cucumber.runtime.Runtime;
import cucumber.runtime.RuntimeOptions;
import gherkin.formatter.Formatter;
import org.testng.ITest;
import org.testng.Reporter;
import org.testng.annotations.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.fail;

public class CucumberTestImpl implements ITest {

    private final String basePackage;
    private List<String> features;

    public CucumberTestImpl(String basePackage, List<String> features) {
        this.basePackage = basePackage;
        this.features = features;
    }

    public String getTestName() {
        return "cucumber";
    }

    @Test
    public void cucumber() throws Throwable {

        CucumberTestNgFormatter formatter = new CucumberTestNgFormatter(System.out);

        String[] gluePaths = {basePackage};

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        RuntimeOptions runtimeOptions = new RuntimeOptions();
        runtimeOptions.featurePaths = features;
        runtimeOptions.glue = Arrays.asList(gluePaths);
        runtimeOptions.formatters = Arrays.asList((Formatter) formatter, new HTMLFormatter(new File("target/cucumber")) );

        Runtime runtime = new Runtime(new FileResourceLoader(), classLoader, runtimeOptions);
        runtime.writeStepdefsJson();
        runtime.run();

        formatter.done();
        formatter.close();

        if (formatter.getFailureCount().get() > 0) {
            fail(String.format("%s failed with %d failures, %d skips, %d passes", getTestName(),
                    formatter.getFailureCount().get(),
                    formatter.getSkipCount().get(),
                    formatter.getPassCount().get()));
        } else {
            Reporter.log(String.format("%s skipped with %d failures, %d skips, %d passes", getTestName(),
                    formatter.getFailureCount().get(),
                    formatter.getSkipCount().get(),
                    formatter.getPassCount().get()));
        }

    }
}
