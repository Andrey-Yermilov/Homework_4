package forms;

import org.openqa.selenium.By;
import webdriver.BaseForm;
import webdriver.elements.*;

/**
 * Main page of onliner
 */
public class MainForm extends  BaseForm {
    private static final String formLocator = "//div/a[@href='http://www.onliner.by/']";
    private Button catalog = new Button(By.xpath("//a[@href='http://catalog.onliner.by/' and @class='b-main-navigation__link']"),"Catalog link");

    public MainForm() {
        super(By.xpath(formLocator), "Onliner.by");
    }

    /**
     * Opens onliner's catalog
     * @return catalog page
     */
    public CatalogForm goToCatalog(){
        catalog.click();
        browser.waitForPageToLoad();
        return new CatalogForm();
    }
}
