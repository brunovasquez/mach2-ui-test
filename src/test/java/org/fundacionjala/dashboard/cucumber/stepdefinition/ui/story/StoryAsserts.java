package org.fundacionjala.dashboard.cucumber.stepdefinition.ui.story;

import cucumber.api.java.en.Then;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fundacionjala.dashboard.api.Mapper;
import org.fundacionjala.dashboard.cucumber.stepdefinition.api.ResourcesSteps;
import org.fundacionjala.dashboard.cucumber.stepdefinition.ui.AssertTable;
import org.fundacionjala.dashboard.ui.pages.content.ConfigureWidget;
import org.fundacionjala.dashboard.ui.pages.content.widget.TableWidget;
import org.fundacionjala.dashboard.util.Utils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.fundacionjala.dashboard.cucumber.hooks.AssertionHooks.getAssertion;

/**
 * Class to manage Step definition  for table widget of Story features.
 */
public class StoryAsserts {
    private static final Logger LOGGER = LogManager.getLogger(StoryAsserts.class);
    public static final int TIME_TO_WAIT = 5000;
    private static final String CURRENT_ITERATION_NUMBER = "current_iteration_number";
    private static final String NAME = "name";
    private ResourcesSteps resources;
    private List<WebElement> listProjects;
    private JSONArray obj;
    private TableWidget tableWidget;

    /**
     * Constructor where initialize the values.
     *
     * @param newResources ResourcesStep.
     */
    public StoryAsserts(final ResourcesSteps newResources) {
        this.resources = newResources;
        tableWidget = new TableWidget();
    }


    /**
     * Method that calculate the projects quantity.
     *
     * @throws ParseException is and exception.
     */
    @Then("^Verify the projects quantity$")
    public void verifyTheProjectsQuantity() throws ParseException {
        obj = (JSONArray) new JSONParser().parse(resources.getResponseList()
                .get(resources.getResponseList().size() - 1).jsonPath().prettify());
        listProjects = new ConfigureWidget().clickProjectDropdownField();

        getAssertion().assertEquals(listProjects.size(), obj.size());
    }

    /**
     * Method that verifies that the project names displayed
     * in a dropdown are the same of pivotal tracker.
     */
    @Then("^Verify all information displayed in the project dropdown field$")
    public void verifyAllInformationDisplayedInTheProjectDropdownField() {
        List<String> newListProjects = listProjects.stream().map(WebElement::getText).collect(Collectors.toList());
        obj.forEach(element -> getAssertion().assertEquals(
                newListProjects.contains(((JSONObject) element).get("name")), true)
        );
        new ConfigureWidget().clickOut();
    }

    /**
     * Method to Assert the displayed columns in the UI.
     *
     * @param projectName String  that is a name of the project.
     */
    @Then("^Verify the iterations quantity (.*)$")
    public void verifyTheIterationsQuantity(final String projectName) {
        String newProjectName = Mapper.mapEndpoint(projectName);
        ConfigureWidget configureWidget = new ConfigureWidget();
        configureWidget.clickIteration();
        JsonPath jsonPath = Utils.findElementJson(newProjectName, resources.getResponseList());
        LOGGER.info("Iterations quantity displayed in match2: " + configureWidget.getStoryIterationSize());
        LOGGER.info("Iterations quantity displayed in pivotal response: " + jsonPath.get(CURRENT_ITERATION_NUMBER));
        getAssertion().assertEquals(configureWidget.getStoryIterationSize(), jsonPath.get(CURRENT_ITERATION_NUMBER));
        new ConfigureWidget().clickOut();
    }

    /**
     * method to validate  all data of the story table whit pivotal tracker.
     *
     * @param kind kind of Response, it could be story or project.
     */
    @Then("^Validate (story|project) table against pivotal story$")
    public void allInformationOfPivotalTrackerStoryShouldBeDisplayedInStoryTableWidgetOfMach(final String kind) {
        try {
            Thread.sleep(TIME_TO_WAIT);
            List<Map<String, String>> tableProjectValues = tableWidget.getDataFromWidget();
            List<Response> responseList = resources.getResponseList();
            List<Map<String, String>> tableProjectValuesLowers = tableWidget.getConvertLowerCase(tableProjectValues);
            executeListAssert(tableProjectValuesLowers, Utils.filterResponseByKind(responseList, kind));
        } catch (InterruptedException e) {
            LOGGER.error("An interrupt occurred while the thread was sleeping.");
            e.printStackTrace();
        }
    }

    /**
     * Method to validate if a table or story widget is empty.
     */
    @Then("^I expect an empty table story widget$")
    public void iExpectAnEmptyTableStoryWidget() {
        try {
            Thread.sleep(TIME_TO_WAIT);
            List<Map<String, String>> tableProjectValues = tableWidget.getDataFromWidget();
            LOGGER.info("Story quantity values on table widget: " + tableProjectValues.size());
            getAssertion().assertTrue(tableProjectValues.isEmpty(), "Table should be empty");
        } catch (InterruptedException e) {
            LOGGER.error("An interrupt occurred while the thread was sleeping.");
            e.printStackTrace();
        }
    }

    /**
     * Method that given a list of data execute the assertions for each values of the response.
     *
     * @param widgetValues List of Map with the information of the displayed data in UI.
     * @param responseList List with that contain the responses.
     */
    private void executeListAssert(final List<Map<String, String>> widgetValues,
                                   final List<Response> responseList) {
        responseList.forEach(response -> {
            Map<String, String> row = Utils.findElementInArray(response.jsonPath().get(NAME), widgetValues);
            if (!row.isEmpty()) {
                executeAssert(row, response.jsonPath());
            }
        });
    }

    /**
     * Method to execute the asserts for each values of the response.
     *
     * @param map      Map with the information of the displayed data in UI.
     * @param jsonPath Json that contain the response.
     */
    private void executeAssert(final Map<String, String> map, final JsonPath jsonPath) {
        Map<String, AssertTable> strategyMap = mapStrategyStoryWidget(jsonPath);
        Set<String> keys = map.keySet();
        for (String key : keys) {
            LOGGER.info("Current element value from match2: " + map.get(key));
            LOGGER.info("Current element value from json response: " + strategyMap.get(key).executeAssertion());
            getAssertion().assertEquals(map.get(key), strategyMap.get(key).executeAssertion());
        }
    }

    /**
     * Method to map the strategy fo make the assertions for story.
     *
     * @param jsonPath the response values
     * @return the Map with the information to be validated.
     */
    private Map<String, AssertTable> mapStrategyStoryWidget(final JsonPath jsonPath) {
        Map<String, AssertTable> strategyMap = new HashMap<>();

        Map<String, Object> jsonAdd = jsonPath.get();

        if (!jsonAdd.containsKey("estimate")) {
            jsonAdd.put("estimate", " ");
        }

        strategyMap.put(StoryParameters.NAME.toString(), () -> jsonPath.get("name"));
        strategyMap.put(StoryParameters.STATE.toString(), () -> jsonPath.get("current_state").toString());
        strategyMap.put(StoryParameters.POINTS.toString(), () -> jsonAdd.get("estimate").toString());
        strategyMap.put(StoryParameters.TYPE.toString(), () -> jsonPath.get("story_type").toString());
        strategyMap.put(StoryParameters.OWNERS.toString(), () -> " ");

        return strategyMap;
    }
}
