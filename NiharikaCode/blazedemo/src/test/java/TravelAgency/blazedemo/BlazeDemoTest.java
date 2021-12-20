package TravelAgency.blazedemo;


import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class BlazeDemoTest extends BlazeDemoDriverEngine{

	@BeforeTest
	public void launchBrowserVerifyURL() throws IOException {
		String urlCurrent = "";
		driver = browser();
		try {
			urlCurrent = driver.getCurrentUrl();
			System.out.println("Successfully navigated to URL: " + url);
		}catch(Exception e) {
			Assert.fail("Couldn't get URL");
		}
		Assert.assertEquals(urlCurrent, "https://blazedemo.com/", "URL didn't redirect to secured. Navigation failed!");
	}


	@BeforeTest
	public void verifyPageHeadline() {
		String pageHeadline = "";
		try {
			pageHeadline = driver.findElement(By.xpath("//h1[contains(text(),'Welcome')]")).getText();
			System.out.println("Successfully fetched page headline as: " + pageHeadline);
		}catch(Exception e) {
			Assert.fail("Unable to get page headline value!");
		}
		Assert.assertEquals(pageHeadline, "Welcome to the Simple Travel Agency!", "Couldn't land on the correct page, validation failed!");

	}


	@Test(dataProvider = "getSourceDestinationCity")
	public void verifyDepartureAndDestination(String departureCity, String destinationCity) throws InterruptedException {
		String reservePageHeadline = "";
		System.out.println("Departure City : " + departureCity);
		System.out.println("Destination City: " + destinationCity);
		System.out.println("---Verifying Booking---");
		//select[@name='fromPort']//option[text()='Mexico City']
		try {
			driver.findElement(By.xpath(("//select[@name='fromPort']//option[text()='{departureCity}']").replace("{departureCity}", departureCity))).click();
			System.out.println("Successfully clicked on the source city: " + departureCity);
		}catch(Exception e) {
			Assert.fail("Unable to click on source city: " + departureCity);
		}
		//select[@name='toPort']//option[text()='London']
		try {
			driver.findElement(By.xpath(("//select[@name='toPort']//option[text()='{destinationCity}']").replace("{destinationCity}", destinationCity))).click();
			System.out.println("Successfully clicked on the destination city: " + destinationCity);
		}catch(Exception e) {
			Assert.fail("Unable to click on destination city: " + destinationCity);
		}

		//find flights

		try {
			driver.findElement(By.xpath("//*[@value='Find Flights']")).click();
			System.out.println("Successfully clicked on \"Find Flights\" button");
		}catch(Exception e) {
			Assert.fail("Unable to click on \"Find Flights\" button");

		}

		//verifying if the customer is landed to correct page after search

		try {
			//	Flights from Mexico City to London

			reservePageHeadline = driver.findElement(By.xpath("//div//h3")).getText();

			System.out.println("Successfully fetched reserve page headline after city search as \"" + reservePageHeadline + "\"");

		}catch(Exception e) {
			Assert.fail("Unable to fetch reserve page headline after city search");

		}
		Assert.assertEquals(reservePageHeadline, "Flights from " + departureCity + " to " + destinationCity + ":", "The Reserve Page Headline don't match");


	}

	@Test

	public void verifyFlightBooking() {
		String pageTitle = "";

		//validate that  Choose this flight button is working
		try {
			driver.findElement(By.xpath("(//td//input[@value='Choose This Flight'])[1]")).click();
			pageTitle =  driver.getTitle();
			System.out.println("Page Title: " + pageTitle);
		}catch(Exception e) {
			Assert.fail("Could not click on Choose Button");
		}
		Assert.assertEquals(pageTitle, "BlazeDemo Purchase", "Page Title didn't match!");
	}


	@Test(dataProvider="getCustomerDetails")

	public void verifyFlightPurchasePage(String name, String address, String city, String state,  String zipCode, String cardType, String cardNum,  String month, String year, String nameOnCard) throws InterruptedException {
		String defaultName = "", defaultAddress = "", defaultCity = "", defaultState = "", defaultZip=  "", defaultCardNum = "",
				defaultMonth = "", defaultYear = "", defaultNameOnCard = "";


		//validating if the fields are empty, if not, clear the field and add values

		//NOTE: HAVE NOT ADDED IF ELSE FOR ALL THE CONDITIONS DUE TO TIME CONSTRAINT

		defaultName = driver.findElement(By.xpath("//label[text()='Name']//following-sibling::div//input")).getAttribute("value");
		if(defaultName.isEmpty()) {
			System.out.println("By default, no name is present");
			driver.findElement(By.xpath("//label[text()='Name']//following-sibling::div//input")).sendKeys(name);
		}



		defaultAddress = driver.findElement(By.xpath("//label[text()='Address']//following-sibling::div//input")).getAttribute("value");
		if(defaultAddress.isEmpty()) {
			System.out.println("By default, no Address is present");
			driver.findElement(By.xpath("//label[text()='Address']//following-sibling::div//input")).sendKeys(address);
		}

		defaultCity = driver.findElement(By.xpath("//label[text()='City']//following-sibling::div//input")).getAttribute("value");
		if(defaultCity.isEmpty()) {
			System.out.println("By default, no City is present");
			driver.findElement(By.xpath("//label[text()='City']//following-sibling::div//input")).sendKeys(city);
		}

		defaultState = driver.findElement(By.xpath("//label[text()='State']//following-sibling::div//input")).getAttribute("value");
		if(defaultState.isEmpty()) {
			System.out.println("By default, no State is present");
			driver.findElement(By.xpath("//label[text()='State']//following-sibling::div//input")).sendKeys(state);
		}


		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//input[@value='Purchase Flight']")));
		driver.findElement(By.xpath("//label[text()='Zip Code']//following-sibling::div//input")).sendKeys(zipCode);
		//option[text()='Visa']
		driver.findElement(By.xpath(("//option[text()='{cardType}']").replace("{cardType}", cardType))).click();
		driver.findElement(By.xpath("//label[text()='Credit Card Number']//following-sibling::div//input")).sendKeys(cardNum);

		defaultMonth = driver.findElement(By.xpath("//label[text()='Month']//following-sibling::div//input")).getAttribute("value");
		if(defaultMonth.isEmpty()) {
			System.out.println("By default, no Month is present");
			driver.findElement(By.xpath("//label[text()='Month']//following-sibling::div//input")).sendKeys(month);
		}else {
			System.out.println("By default, month value is present, it should NOT be: " + defaultMonth);
			driver.findElement(By.xpath("//label[text()='Month']//following-sibling::div//input")).clear();
			driver.findElement(By.xpath("//label[text()='Month']//following-sibling::div//input")).sendKeys(month);
		}

		defaultYear = driver.findElement(By.xpath("//label[text()='Year']//following-sibling::div//input")).getAttribute("value");
		if(defaultYear.isEmpty()) {
			System.out.println("By default, no Year is present");
			driver.findElement(By.xpath("//label[text()='Year']//following-sibling::div//input")).sendKeys(year);
		}else {
			System.out.println("By default, year value is present, it should NOT be: " + defaultYear);
			driver.findElement(By.xpath("//label[text()='Year']//following-sibling::div//input")).clear();
			driver.findElement(By.xpath("//label[text()='Year']//following-sibling::div//input")).sendKeys(year);
		}

		driver.findElement(By.xpath("//label[text()='Name on Card']//following-sibling::div//input")).sendKeys(nameOnCard);
		driver.findElement(By.xpath("//input[@value='Purchase Flight']")).click();

		String confirmationPageHeadline = driver.findElement(By.xpath("//h1[text()='Thank you for your purchase today!']")).getText();
		boolean successBooking = false;
		if(confirmationPageHeadline.equals("Thank you for your purchase today!")) {
			successBooking = true;
			Assert.assertTrue(successBooking);
			System.out.println("Successfully booked the flight.");
			String confirmationID=  driver.findElement(By.xpath("//tr//td[text()='Id']//following-sibling::td")).getText();
			System.out.println("Confirmation ID for booking is: " + confirmationID);
		}else
			Assert.fail("Could not book the flight");
		
	}

	@AfterTest
	public void closeDriver() {
		driver.close();
	}

	@DataProvider
	public Object[][] getSourceDestinationCity() {
		Object[][] cityNames = new Object[1][2];
		cityNames[0][0] =  "Mexico City";
		cityNames[0][1] =  "London";
		return cityNames;
	}
	@DataProvider
	public Object[][] getCustomerDetails() {
		Object[][] customerDetails = new Object[1][10];
		customerDetails[0][0] =  "Cutomer Test Name";
		customerDetails[0][1] =  "Address Test Value";
		customerDetails[0][2] =  "City Test Value";
		customerDetails[0][3] =  "State Test Value";
		customerDetails[0][4] =  "12345";
		customerDetails[0][5] =  "American Express";
		customerDetails[0][6] =  "0123456789";
		customerDetails[0][7] =  "12";
		customerDetails[0][8] =  "2021";
		customerDetails[0][9] =  "Customer Name";

		return customerDetails;
	}
}
