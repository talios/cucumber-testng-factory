package com.talios;

import com.talios.cucumberng.CucumberFactoryBuilder;
import org.testng.annotations.Factory;

import java.io.File;

/**
 * Unit test for simple App.
 */
public class AppTestFactory {

    @Factory
    public Object[] create() {
        return CucumberFactoryBuilder.create("com.talios",
                new File("src/test/resources/com/talios/sometest.feature").getPath());
    }


}
