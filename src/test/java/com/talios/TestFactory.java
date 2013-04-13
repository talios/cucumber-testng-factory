package com.talios;

import com.talios.cucumberng.CucumberFactoryBuilder;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.PendingException;
import org.testng.annotations.Factory;

import java.io.File;

/**
 * Unit test for simple App.
 */
public class TestFactory {

    @Factory
    public Object[] create() {
        return new CucumberFactoryBuilder()
                .addOption("--format", "html:target/cucumber")
                .create(new File("src"));
    }

    @Given("^the iTunes file (.*)$")
    public void openItunesFile(String file) {
        System.out.println("Opening " + file);
    }

    @When("^searching for the artist (.*)$")
    public void searching_for_the_artist(String artist) {
        // Express the Regexp above with the code you wish you had
        throw new PendingException();
    }

    @Then("^there should be (\\d+) albums found$")
    public void there_should_be_albums_found(int count) {
        // Express the Regexp above with the code you wish you had
        throw new PendingException();
    }

}
