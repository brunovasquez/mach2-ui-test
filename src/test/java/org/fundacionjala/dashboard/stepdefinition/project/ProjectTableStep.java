package org.fundacionjala.dashboard.stepdefinition.project;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.fundacionjala.dashboard.stepdefinition.api.ResourcesSteps;
import org.fundacionjala.dashboard.ui.page.*;
import org.fundacionjala.dashboard.ui.page.projectwidget.ConfigTableProject;
import org.fundacionjala.dashboard.ui.page.projectwidget.ProjectTableWidget;
import org.fundacionjala.dashboard.utility.Environment;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Class to manage Step definition  for table widget of project.
 */
public class ProjectTableStep {

    private ResourcesSteps resources;
    private ProjectTableWidget projectTableWidget;
    private static final Environment ENVIRONMENT = Environment.getInstance();
    private HomePage homePage;
    private WidgetPage widgetPage;
    Map<String, String> tableProjectValues;

    @Given("^I login with valid credentials to Mach2$")
    public final void iLogin() {
        final String userName = ENVIRONMENT.getUser();
        final String password = ENVIRONMENT.getPassword();
        LoginPage mainPageMach2 = new LoginPage();
        homePage = mainPageMach2.loginWithUser(userName, password);
    }


    /**
     * Method to add a table widget for a project.
     */
    @Given("^I add a table widget in the board$")
    public final void iAddATableWidgetInTheBoard() {
        homePage.clickMenuBoard();
        BoardPanelPage boardPanelPage = homePage.clickAddBoard();
        boardPanelPage.clickAddBoard();
        widgetPage = new WidgetPage();
    }

    /**
     * Method to Select the project option form the UI.
     */
    @When("^I select the Project option$")
    public final void iSelectTheProjectOption() {
        ServiceForm serviceForm = widgetPage.clickOpenTable();
        ConfigTableProject configTableForm = serviceForm.clickOpenProject();
        ProjectTableWidget projectTableWidget = configTableForm.clickSaveConfigurationProject();
        tableProjectValues = projectTableWidget.getProjectTable();
    }

    /**
     * Method to assert the pivotal tracker project with the Mach2 project tables.
     */
    @Then("^All information of Pivotal Tracker projects should be displayed in project table widget of Mach(\\d+)$")
    public final void allInformationOfPivotalTrackerprojectsShouldBeDisplayedInProjectTableWidgetOfMach() {
        assertEquals(tableProjectValues.get("name"), resources.getResponse().jsonPath().get("name"));
        assertEquals(tableProjectValues.get("current_iteration"),
                resources.getResponse().jsonPath().get("current_iteration_number"));
        assertEquals(tableProjectValues.get("week_start_date"),
                resources.getResponse().jsonPath().get("week_start_day"));
    }
}
