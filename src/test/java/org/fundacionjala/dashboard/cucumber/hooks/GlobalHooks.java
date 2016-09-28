package org.fundacionjala.dashboard.cucumber.hooks;

import cucumber.api.java.Before;
import org.fundacionjala.dashboard.ui.pages.HomePage;
import org.fundacionjala.dashboard.ui.pages.LoginPage;
import org.fundacionjala.dashboard.ui.pages.content.BoardPage;
import org.fundacionjala.dashboard.ui.pages.menu.Profile;
import org.fundacionjala.dashboard.ui.pages.menu.Service;
import org.fundacionjala.dashboard.ui.pages.menu.TopMenu;
import org.fundacionjala.dashboard.utils.Environment;

/**
 * This class stores the global hooks methods required to run the test
 */
public class GlobalHooks {

    private static final String PIVOTAL_TRACKER_SERVICE_NAME = "Pivotal Tracker";

    private boolean beforeAllFlag;
    private BoardPage boardPage;
    @Before
    public void beforeAll() {
        if (!beforeAllFlag) {
            // Login as Primary
            HomePage homePage = LoginPage.loginAsPrimaryUser();

            // Select the Service
            TopMenu topMenu = homePage.goToTopMenu();
            Profile profile = topMenu.clickOnProfileMenu();
            Service service = profile.clickAddServiceButton();
            service.selectServiceDropdown(PIVOTAL_TRACKER_SERVICE_NAME);
            service.setDescriptionTextField("Any Text");
            service.setTokenTextField(Environment.getInstance().getToken());
            service.clickSaveButton();

            topMenu.clickJalasoftIcon();
            boardPage = topMenu.clickAddBoardMenu();

            beforeAllFlag = true;
        }
    }

}
