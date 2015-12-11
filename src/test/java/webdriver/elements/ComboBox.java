package webdriver.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;


public class ComboBox extends BaseElement {


    public ComboBox(final By locator, final String name) {
        super(locator, name);
    }

    public ComboBox(String string, String name) {
        super(string, name);
    }


    protected String getElementType() {
        return getLoc("loc.combobox");
    }

    public boolean isEnabled(){
        return this.getElement().isEnabled();
    }

    public ComboBox(By locator) {
        super(locator);
    }

    /**
     * Choose value from ComboBox
     * @param value Value in ComboBox should be chosen
     */
    public void chooseValue(final int value) {
        info(String.format(getLoc("loc.text.choose") + " '%1$s'", value));
        Select selectObject = new Select(element);
        selectObject.selectByVisibleText(String.format("%d\"",value));
        sendKeys(Keys.ENTER);
    }
}
