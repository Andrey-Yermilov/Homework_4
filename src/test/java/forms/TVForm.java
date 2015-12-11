package forms;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import webdriver.BaseForm;
import webdriver.elements.CheckBox;
import webdriver.elements.ComboBox;
import webdriver.elements.Label;
import webdriver.elements.TextBox;


/**
 * Page with all TVs
 */
public class TVForm extends BaseForm {
    private static final String formLocator = "//h1[contains(text(), \"Телевизоры\")]";
    private TextBox txbPriceMax = new TextBox(By.xpath("//span[contains(text(), \"Цена\")]/../parent::div//input[contains(@data-bind, \"до\")]"), "Maximal price");
    private TextBox txbYearMin = new TextBox(By.xpath("//span[contains(text(), \"Дата выхода на рынок\")]/../parent::div//input[contains(@data-bind, \"от\")]"), "From year");
    private ComboBox cbxScreenSizeMin = new ComboBox(By.xpath("//span[contains(text(), \"Диагональ\")]/../parent::div//select[contains(@data-bind, \"from\")]"), "Min diagonal");
    private ComboBox cbxScreenSizeMax = new ComboBox(By.xpath("//span[contains(text(), \"Диагональ\")]/../parent::div//select[contains(@data-bind, \"to\")]"), "Max diagonal");
    private String tvWinHandle;

    public TVForm() {
        super(By.xpath(formLocator), "TV. Onliner.by");
    }

    /**
     * Specify maximal price
     * @param priceMax maximal price
     * @return this page
     */
    public TVForm setPriceMax(int  priceMax ) {
        txbPriceMax.type(Integer.toString(priceMax));
        return this;
    }

    /**
     * Specify Manufacturer
     * @param manufacturer Manufacturer
     * @return this page
     */
    public TVForm chooseManufacturer(String manufacturer) {
        String manufPath = "//span[contains(text(),\"Производитель\")]/../..//div[@class='schema-filter-control schema-filter-control_more']";
        String currentManufPath = String.format("//span[@class='schema-filter__checkbox-text' and contains(text(),\"%s\")]", manufacturer);
        Label lblManufacturer = new Label(By.xpath(manufPath),"Manufacturer");
        lblManufacturer.click();
        CheckBox chbManufacturer = new CheckBox(By.xpath(currentManufPath), String.format("Manufacturer: %s Check-Box", manufacturer));
        chbManufacturer.click();
        return this;
    }

    /**
     * Specify minimal year
     * @param yearMin minimal year
     * @return this page
     */
    public TVForm setYearMin(int yearMin) {
        txbYearMin.type(Integer.toString(yearMin));
        return this;
    }

    /**
     * Specify minimal screen size
     * @param screenSizeMin minimal screen size
     * @return this page
     */
    public TVForm chooseScreenSizeMin(int screenSizeMin) {
        cbxScreenSizeMin.click();
        cbxScreenSizeMin.chooseValue(screenSizeMin);
        return this;
    }

    /**
     * Specify maximal screen size
     * @param screenSizeMax maximal screen size
     * @return this page
     */
    public TVForm chooseScreenSizeMax(int screenSizeMax) {
        cbxScreenSizeMax.click();
        cbxScreenSizeMax.chooseValue(screenSizeMax);
        return this;
    }

    /**
     * Method asserts if found result has correct price
     * @param i Position of asserted result
     * @param expectedPriceMax Maximal price value
     */
    public void assertPriceIsCorrect (int i, int expectedPriceMax) {
        Label itemPrice = new Label(By.xpath(String.format("(//div[@class='schema-product__group']//div[@class='schema-product__price']//span)[%d]", i)));
        String fullProductPrice = itemPrice.getText();
        String productPrice = fullProductPrice.replaceAll(" ", "");
        int productPriceInt = Integer.parseInt(productPrice.substring(0, (productPrice.length() - 4)));
        info(String.format("Price: Expected less than '%d', found '%d'",  expectedPriceMax, productPriceInt));
        Assert.assertTrue(productPriceInt <= expectedPriceMax);
    }

    /**
     * Method asserts if found result has correct manufacturer
     * @param i Position of asserted result
     * @param expectedManufacturer Expected manufacturer
     */
    public void assertManufacturerIsCorrect (int i,String expectedManufacturer){
        Label itemLabel = new Label(By.xpath(String.format("(//div[@class='schema-product__group']//span[contains(@data-bind, 'full_name')])[%d]", i)));
        String fullProductName = itemLabel.getText();
        info(String.format("Manufacturer: Expected '%s', found '%s'",expectedManufacturer,fullProductName));
        Assert.assertTrue(fullProductName.contains(expectedManufacturer));
    }

    /**
     * Method asserts if found result has correct screen size
     * @param i Position of asserted result
     * @param expectedScreenSizeMin Minimal screen size value
     * @param expectedscreenSizeMax Maximal screen size value
     */
    public void assertSizeIsCorrect (int i, int expectedScreenSizeMin, int expectedscreenSizeMax) {
        Label itemSize = new Label(By.xpath(String.format("(//div[@class='schema-product__group']//div[@class='schema-product__description']/span)[%d]", i)));
        String productDescription = itemSize.getText();
        int actualSizeInt = Integer.parseInt(productDescription.substring(0, productDescription.indexOf("\"")));
        info(String.format("Screen size: Expected between '%d' and '%d', found '%d'", expectedScreenSizeMin,expectedscreenSizeMax, actualSizeInt));
        Assert.assertTrue(actualSizeInt <= expectedscreenSizeMax && actualSizeInt>=expectedScreenSizeMin);
    }

    /**
     * Method asserts if found results have correct manufacturer, price and screen size
     * @param expectedManufacturer Expected manufacturer
     * @param expectedPriceMax Maximal price value
     * @param expectedScreenSizeMin Minimal screen size value
     * @param expectedScreenSizeMax Maximal screen size value
     */
    public void assertItemInfo(String expectedManufacturer, int expectedPriceMax, int expectedScreenSizeMin, int expectedScreenSizeMax){
        int i = 1;
        browser.waitForPageToLoad();
        while (new Label(By.xpath(String.format("(//div[@class='schema-product__group'])[%d]", i)), String.format("Item%d",i)).isPresent()) {
            assertManufacturerIsCorrect(i, expectedManufacturer);
            assertPriceIsCorrect(i,expectedPriceMax);
            assertSizeIsCorrect(i, expectedScreenSizeMin,expectedScreenSizeMax);
            i++;
        }
    }

    /**
     * Method asserts if year not less than expected year for each item from list
     * @param expectedYearMin Minimal expected year
     */
    public void assertItemYear(int expectedYearMin){
        int i=1;
        while (new Label(By.xpath(String.format("(//div[@class='schema-product__group'])[%d]", i)), String.format("Item%d",i)).isPresent()) {
            ItemForm itemForm = goToItemForm(i);
            itemForm.assertYearIsCorrect(expectedYearMin);
            itemForm.goToPreviousTab(tvWinHandle);
            i++;
        }
    }

    /**
     * Method opens item's page in a new tab
     * @param i Position of result
     * @return New Form for item
     */
    public ItemForm goToItemForm(int i){
        Label itemName = new Label(By.xpath(String.format("(//div[@class='schema-product__group']//span[contains(@data-bind, 'full_name')])[%d]",i)));
        String fullProductName = itemName.getText();
        Label itemLink = new Label(By.xpath(String.format("(//div[@class='schema-product__group']//span[contains(@data-bind, 'full_name')]/..)[%d]",i)) /*fullProductName*/);
        WebDriver driver = browser.getDriver();
        tvWinHandle = driver.getWindowHandle();
        itemLink.sendKeys(Keys.chord(Keys.CONTROL, Keys.ENTER));
        itemLink.sendKeys(Keys.chord(Keys.CONTROL, Keys.TAB));
        for(String itemWinHandle : driver.getWindowHandles()){
            driver.switchTo().window(itemWinHandle);
        }
        browser.waitForPageToLoad();
        return new ItemForm(fullProductName);
    }
}
