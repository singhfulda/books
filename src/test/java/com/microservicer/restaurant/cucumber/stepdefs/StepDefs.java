package com.microservicer.restaurant.cucumber.stepdefs;

import com.microservicer.restaurant.GolaApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = GolaApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
