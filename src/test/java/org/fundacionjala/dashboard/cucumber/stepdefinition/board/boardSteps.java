package org.fundacionjala.dashboard.cucumber.stepdefinition.board;

import cucumber.api.java.en.When;
import org.fundacionjala.dashboard.ui.pages.content.BoardPage;
import org.fundacionjala.dashboard.ui.pages.content.WidgetPage;

/**
 * Created by BrunoVasquez on 9/28/2016.
 */
public class boardSteps {
    private final BoardPage boardPage;

    /**
     * This construct of board page.
     *
     * @param boardPage that identify the a board specific.
     */
    public boardSteps(BoardPage boardPage) {
        this.boardPage = boardPage;
    }
    /**
     * Method to add a widget to  a board specific.
     */
    @When("^I add a widget in the board specific$")
    public void iAddATableWidgetInTheBoardSpecific$() {
         WidgetPage widgetPage = boardPage.clickAddWidgetMenu();
    }
}
