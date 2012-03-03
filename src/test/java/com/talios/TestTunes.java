package com.talios;

import cucumber.annotation.en.Given;
import cucumber.annotation.en.When;

/**
 * Created with IntelliJ IDEA.
 * User: amrk
 * Date: 28/02/12
 * Time: 10:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestTunes {

    @Given("^the iTunes file (.*)$")
    public void openItunesFile(String file) {
        System.out.println("Opening " + file);
    }

    @When("^playing music$")
    public void playTune() {
        System.out.println("playing tunes");
    }

}
