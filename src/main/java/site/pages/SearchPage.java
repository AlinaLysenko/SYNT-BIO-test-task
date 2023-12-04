package site.pages;

import com.epam.jdi.light.elements.complex.WebList;
import com.epam.jdi.light.elements.composite.WebPage;
import com.epam.jdi.light.elements.pageobjects.annotations.Title;
import com.epam.jdi.light.elements.pageobjects.annotations.Url;
import com.epam.jdi.light.elements.pageobjects.annotations.locators.UI;

@Url("/search") @Title("Repository Search Result")
public class SearchPage  extends WebPage {
    @UI("div.search-title")
    public WebList searchResult;
}
