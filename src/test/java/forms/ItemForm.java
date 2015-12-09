package forms;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import webdriver.BaseForm;
import webdriver.elements.Label;

/**
 * Form with full item description
 */
public class ItemForm extends BaseForm {
    private static final String formLocator = "//h3[contains(text(),\"Описание\")]";
    private Label yearLabel = new Label(By.xpath("//div[@class='product-specs__table-title-inner' and contains(text(),\"Общая информация\")]/../../following-sibling::tr//span[@class='value__text']"), "Year of release");

    public ItemForm(String itemName) {
        super(By.xpath(formLocator), String.format("%s, Onliner.by", itemName));
    }

    /**
     * Checks if year of release is not earlier than expected minimal year
     * @param expectedYearMin Expected minimal year
     * @return this page
     */
    public ItemForm assertYearIsCorrect(int expectedYearMin){
        String actualYear = yearLabel.getText().substring(0,4);
        int actualYearInt = Integer.parseInt(actualYear);
        info(String.format("Year of release: Expected not earlier than '%d', found '%d'", expectedYearMin, actualYearInt));
        Assert.assertTrue(actualYearInt >= expectedYearMin);
        return this;
    }

    /**
     * Switches to previous tab
     * @param tvWinHandle Handle of previous tab
     */
    public void goToPreviousTab(String tvWinHandle){
        WebDriver driver = browser.getDriver();
        yearLabel.sendKeys(Keys.chord(Keys.CONTROL, "w"));
        driver.switchTo().window(tvWinHandle);
    }
}
