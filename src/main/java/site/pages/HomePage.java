package site.pages;

import com.epam.jdi.light.elements.complex.WebList;
import com.epam.jdi.light.elements.composite.WebPage;
import com.epam.jdi.light.elements.pageobjects.annotations.locators.UI;
import com.epam.jdi.light.elements.pageobjects.annotations.smart.SClass;
import com.epam.jdi.light.ui.html.elements.common.Button;
import com.epam.jdi.light.ui.html.elements.common.TextField;

public class HomePage extends WebPage {

    @SClass
    public Button searchInput;

    @UI("#query-builder-test")
    public TextField searchField;

    @UI("#query-builder-test-result-jdi")
    public Button searchButton;

    @UI(".HeaderMenu-link--sign-in")
    public Button signInButton;

    @UI(".HeaderMenu-link--sign-up")
    public Button signUpButton;

    @UI(".HeaderMenu-item")
    public WebList headerItems;

    public HomePage search (String searchValue){
        searchInput.click();
        searchField.sendKeys(searchValue);
        searchButton.click();
        return this;
    }
}
