import com.epam.jdi.light.elements.complex.dropdown.Dropdown;
import com.epam.jdi.light.elements.pageobjects.annotations.JSite;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import site.GitHubSite;
import org.testng.annotations.Test;

import static com.epam.jdi.light.driver.WebDriverUtils.killAllSeleniumDrivers;
import static com.epam.jdi.light.elements.composite.WebPage.openSite;
import static org.testng.Assert.assertTrue;

@JSite("https://github.com")
public class GitHubPageTests {

    @BeforeMethod
    public void setup() {
        openSite(GitHubSite.class);
    }

    @Test
    public void testLoginButtonRedirectsToLoginPage() {
        GitHubSite.homePage.shouldBeOpened();
        GitHubSite.homePage.signInButton.click();
        GitHubSite.loginPage.checkOpened();
        GitHubSite.loginPage.usernameInput.is().visible().and().enabled();
        GitHubSite.loginPage.passwordInput.is().visible().and().enabled();
        GitHubSite.loginPage.signInButton.is().visible().and().enabled();
    }

    @Test
    public void testSignupButtonRedirectsToSignupPage() {
        GitHubSite.homePage.shouldBeOpened();
        GitHubSite.homePage.signUpButton.click();
        GitHubSite.signupPage.checkOpened();
        GitHubSite.signupPage.emailInput.is().visible();
        GitHubSite.signupPage.continueButton.is().visible().and().disabled();
    }

    @Test
    public void testSearchRepository() {
        GitHubSite.homePage.shouldBeOpened();
        GitHubSite.homePage.searchInput.is().visible();
        GitHubSite.homePage.search("jdi").checkOpened();
        GitHubSite.searchPage.searchResult.is().visible();
        GitHubSite.searchPage.searchResult.forEach(e -> assertTrue(e.getText().toLowerCase().contains("jdi")));
    }

    @Test
    public void testNavigationDropdownsAppears() {
        GitHubSite.homePage.shouldBeOpened();
        GitHubSite.homePage.headerItems.is().visible();
        GitHubSite.homePage.headerItems.get(1).click();
        GitHubSite.homePage.headerItems.get(1)
                .finds(".HeaderMenu-dropdown-link").forEach(e -> e.is().displayed());
        GitHubSite.homePage.headerItems.get(2).click();
        GitHubSite.homePage.headerItems.get(2)
                .finds(".HeaderMenu-dropdown-link").forEach(e -> e.is().displayed());
        GitHubSite.homePage.headerItems.get(3).click();
        GitHubSite.homePage.headerItems.get(3)
                .finds(".HeaderMenu-dropdown-link").forEach(e -> e.is().displayed());
    }

    @AfterClass
    public void tearDown() {
        killAllSeleniumDrivers();
    }
}