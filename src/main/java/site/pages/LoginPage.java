package site.pages;

import com.epam.jdi.light.elements.composite.WebPage;
import com.epam.jdi.light.elements.pageobjects.annotations.Title;
import com.epam.jdi.light.elements.pageobjects.annotations.Url;
import com.epam.jdi.light.elements.pageobjects.annotations.locators.UI;
import com.epam.jdi.light.ui.html.elements.common.Button;
import com.epam.jdi.light.ui.html.elements.common.TextField;

@Url("/login") @Title("Login Page")
public class LoginPage extends WebPage {

    @UI("#login_field")
    public TextField usernameInput;

    @UI("#password")
    public TextField passwordInput;

    @UI("input[name='commit']")
    public Button signInButton;
}
