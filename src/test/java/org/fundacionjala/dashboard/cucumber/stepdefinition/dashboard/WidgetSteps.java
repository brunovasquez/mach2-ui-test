package org.fundacionjala.dashboard.cucumber.stepdefinition.dashboard;

import cucumber.api.java.en.When;
import org.fundacionjala.dashboard.ui.pages.content.BoardPage;
import org.fundacionjala.dashboard.ui.pages.content.WidgetPage;

/**
 * Class to manage Step definition  for table widget of features.project.
 */
public class WidgetSteps {
    BoardPage boardPage = new BoardPage();
    /**
     * Method to add a table widget for a features.project.
     */
    @When("^I add a table widget in the board$")
    public void iAddATableWidgetInTheBoard() {
        WidgetPage widgetPage = boardPage.clickAddWidgetMenu();
    }

}
