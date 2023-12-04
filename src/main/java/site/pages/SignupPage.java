package site.pages;

import com.epam.jdi.light.elements.composite.WebPage;
import com.epam.jdi.light.elements.pageobjects.annotations.Title;
import com.epam.jdi.light.elements.pageobjects.annotations.Url;
import com.epam.jdi.light.elements.pageobjects.annotations.locators.UI;
import com.epam.jdi.light.ui.html.elements.common.Button;
import com.epam.jdi.light.ui.html.elements.common.TextField;

@Url("/signup") @Title("Sign up Page")
public class SignupPage extends WebPage {

    @UI("#email")
    public TextField emailInput;

    @UI(".signup-continue-button")
    public Button continueButton;
}
