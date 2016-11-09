package org.fundacionjala.dashboard.ui.pages.content.widget;

import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import org.fundacionjala.dashboard.ui.pages.AbstractBasePage;

/**
 * Class to config the table widget.
 */
public class ConfigWidget extends AbstractBasePage {

    private static final String TYPE_NOT_FOUND_MSG = "Type not found.";

    @FindBy(xpath = "//button[@data-action='save-wizard-config']")
    private WebElement saveButton;

    /**
     * Method to config the table features.project.
     *
     * @param type type of widget.
     * @return {@link TypeWidget}.
     */
    public final TypeWidget clickSaveConfigurationProject(final Widget type) {
        TypeWidget typeWidget;
        saveButton.click();
        switch (type) {
            case TABLE:
                typeWidget = new TableWidget();
                break;
            case INFO:
                typeWidget = new InfoWidget();
                break;
            default:
                throw new WebDriverException(TYPE_NOT_FOUND_MSG);
        }
        return typeWidget;
    }

}
