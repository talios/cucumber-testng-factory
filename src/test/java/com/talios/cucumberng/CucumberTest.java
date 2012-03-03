package com.talios.cucumberng;

import cucumber.cli.DefaultRuntimeFactory;
import cucumber.cli.RuntimeFactory;
import cucumber.formatter.FormatterFactory;
import cucumber.formatter.MultiFormatter;
import cucumber.io.FileResourceLoader;
import gherkin.formatter.Formatter;
import gherkin.formatter.Reporter;
import org.testng.IHookCallBack;
import org.testng.IHookable;
import org.testng.ITest;
import org.testng.ITestResult;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
* Created with IntelliJ IDEA.
* User: amrk
* Date: 28/02/12
* Time: 10:27 PM
* To change this template use File | Settings | File Templates.
*/
public class CucumberTest implements IHookable, ITest {

    private final String basePackage;
    private final String feature;

    private RuntimeFactory runtimeFactory = new DefaultRuntimeFactory();
    FormatterFactory formatterFactory = new FormatterFactory(Thread.currentThread().getContextClassLoader());
    Formatter formatter;

    public CucumberTest(String basePackage, String feature) {
        this.basePackage = basePackage;
        this.feature = feature;
    }


    public String getTestName() {
        return feature;
    }

    public void run(IHookCallBack callBack, ITestResult testResult) {
        formatter = formatterFactory.createFormatter(CucumberTestNgFormatter.class.getName(), System.out);
        ((CucumberTestNgFormatter) formatter).setCurrentTestNgResult(testResult); //CHEATING
        callBack.runTestMethod(testResult);

        if (!testResult.isSuccess()) {
            System.out.println(testResult.getStatus());
//            testResult.setStatus(ITestResult.FAILURE);
//            Assert.fail(testResult.getTestName());
        }
    }

    //this is the single general purpose test that gives a TestNg “shell” to any cucumber runtime
    @Test
    public void runFeatures() throws Throwable {
        List<String> featurePathList = Arrays.asList(feature);
        String[] gluePaths = {basePackage};

        //More or less copy of cucumber.cli.Main.run() but without the System.exit at the end...

        List<String> gluePathList = Arrays.asList(gluePaths);

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        MultiFormatter multiFormatter = new MultiFormatter(classLoader);
        multiFormatter.add(formatter);
//        multiFormatter.add(formatterFactory.createFormatter("progress", System.out));

        cucumber.runtime.Runtime runtime = runtimeFactory.createRuntime(new FileResourceLoader(),
                gluePathList,
                classLoader,
                /*isDryRun*/false);

        Formatter formatterProxy = multiFormatter.formatterProxy();
        Reporter reporterProxy = multiFormatter.reporterProxy();
        runtime.run(featurePathList, /*filters*/new ArrayList<Object>(), formatterProxy, reporterProxy);

        formatter.done();
        formatter.close();
    }
}
