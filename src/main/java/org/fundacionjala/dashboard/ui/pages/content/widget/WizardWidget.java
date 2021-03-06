package org.fundacionjala.dashboard.ui.pages.content.widget;

import java.util.HashMap;
import java.util.Map;

import org.fundacionjala.dashboard.ui.pages.AbstractBasePage;
import org.fundacionjala.dashboard.ui.pages.content.ConfigureWidget;
import org.fundacionjala.dashboard.ui.pages.menu.Steps;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Class used to manage the most used options.
 */
public class WizardWidget extends AbstractBasePage {

    //@FindBy(xpath = "//div[@id='mach-wizard']/div/div[3]/div/div/div[2]/div[1]/div[2]/div")
    @FindBy(xpath = "//h4[contains(text(), 'Projects')]")
    private WebElement projectButton;

    @FindBy(xpath = "//h4[contains(text(), 'Story Items')]")
    private WebElement storyButton;

    @FindBy(xpath = "//h4[contains(text(), 'Iteration Velocity')]")
    private WebElement iterationButton;

    @FindBy(xpath = "//h4[contains(text(), 'Burn Down')]")
    private WebElement burnButton;

    @FindBy(xpath = "//h4[contains(text(), 'Story Type Breakdown')]")
    private WebElement storyTypeButton;

    @FindBy(xpath = "//h4[contains(text(), 'Tasks Burn Down')]")
    private WebElement burnTaskButton;

    /**
     * Method to select the 'Open project' options.
     */
    public void clickOpenProject() {
        projectButton.click();
    }

    /**
     * Method to select the 'Open story' options.
     *
     * @return The 'Open story' option selected.
     */
    public ConfigureWidget clickOpenStoryItem() {
        storyButton.click();
        return new ConfigureWidget();
    }

    /**
     * Makes click on a button that opens the burn task configuration.
     *
     * @return the configuration page.
     */
    private ConfigureWidget clickOpenTaskBurnItem() {
        burnTaskButton.click();
        return new ConfigureWidget();
    }

    /**
     * Makes click on a button that opens the story type configuration.
     *
     * @return the configuration page.
     */
    private ConfigureWidget clickOpenStoryTypeItem() {
        storyTypeButton.click();
        return new ConfigureWidget();
    }

    /**
     * Makes click on a button that opens the burn configuration.
     *
     * @return the configuration page.
     */
    private ConfigureWidget clickOpenBurnItem() {
        burnButton.click();
        return new ConfigureWidget();
    }

    /**
     * Makes click on a button that opens the iteration configuration.
     *
     * @return the configuration page.
     */
    private ConfigureWidget clickOpenIteration() {
        iterationButton.click();
        return new ConfigureWidget();
    }

    /**
     * Create an strategy with buttons services types steps, filling a map.
     *
     * @return the configure map with strategies.
     */
    public Map<EnumWizardWidget, Steps> widgetStrategyOption() {
        Map<EnumWizardWidget, Steps> strategyMap = new HashMap<>();
        strategyMap.put(EnumWizardWidget.PROJECT, this::clickOpenProject);
        strategyMap.put(EnumWizardWidget.STORY, this::clickOpenStoryItem);
        strategyMap.put(EnumWizardWidget.ITERATION, this::clickOpenIteration);
        strategyMap.put(EnumWizardWidget.BURN, this::clickOpenBurnItem);
        strategyMap.put(EnumWizardWidget.STORY_TYPE, this::clickOpenStoryTypeItem);
        strategyMap.put(EnumWizardWidget.TASK_BURN, this::clickOpenTaskBurnItem);
        return strategyMap;
    }
}
