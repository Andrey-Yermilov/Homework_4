package forms;

import org.openqa.selenium.By;
import webdriver.BaseForm;
import webdriver.elements.*;

/**
 * Catalog form
 */
public class CatalogForm extends BaseForm {
    private static final String formLocator = "//li[1][@class='b-main-navigation__item b-main-navigation__item-active']";
    private Button electronics = new Button(By.xpath("//span[contains(text(), \"Электроника\")]"),"Electronics");
    private Button tv = new Button(By.xpath("//a[@title='Телевизоры']"),"TV");

    public CatalogForm() {
        super(By.xpath(formLocator), "Catalog Onliner.by");
    }

    /**
     * Opens Electronics
     * @return this page
     */
    public CatalogForm goToElectronics(){
        electronics.click();
        return this;
    }

    /**
     * Opens page for TVs
     * @return Page with TVs
     */
    public TVForm goToTV (){
        tv.click();
        browser.waitForPageToLoad();
        return new TVForm();
    }
}
