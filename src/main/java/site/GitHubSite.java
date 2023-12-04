package site;

import com.epam.jdi.light.elements.pageobjects.annotations.JSite;
import site.pages.HomePage;
import site.pages.LoginPage;
import site.pages.SearchPage;
import site.pages.SignupPage;

@JSite("https://github.com")
public class GitHubSite {
    public static HomePage homePage;
    public static LoginPage loginPage;
    public static SignupPage signupPage;
    public static SearchPage searchPage;

}
