package tests;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import webdriver.BaseTest;
import forms.*;

public class OnlinerSearchTest extends BaseTest {

	private String manufacturer;
	private int priceMax;
	private int  yearMin;
	private int screenSizeMin;
	private int screenSizeMax;

	@Test
	@Parameters({ "manufacturer", "priceMax", "yearMin", "screenSizeMin", "screenSizeMax" })
	public void readParams(String manufacturer, int priceMax, int yearMin, int screenSizeMin, int screenSizeMax) throws Throwable{
		this.manufacturer = manufacturer;
		this.priceMax = priceMax;
		this.yearMin = yearMin;
		this.screenSizeMin = screenSizeMin;
		this.screenSizeMax = screenSizeMax;
		xTest();
	}

	@Test (enabled = false)
	public void xTest() throws Throwable {
		super.xTest();
	}

	public void runTest() {
		logger.step(1);
		MainForm mainForm = new MainForm();

		logger.step(2);
		CatalogForm catalogForm = mainForm.goToCatalog();

		logger.step(3);
		catalogForm.goToElectronics();
		TVForm tvForm = catalogForm.goToTV();

		logger.step(4);
		tvForm.chooseManufacturer(manufacturer);
		tvForm.setPriceMax(priceMax);
		tvForm.setYearMin(yearMin);
		tvForm.chooseScreenSizeMin(screenSizeMin);
		tvForm.chooseScreenSizeMax(screenSizeMax);

		logger.step(5,6);
		tvForm.assertItemInfo(manufacturer,priceMax,screenSizeMin,screenSizeMax);
		tvForm.assertItemYear(yearMin);
	}
}


