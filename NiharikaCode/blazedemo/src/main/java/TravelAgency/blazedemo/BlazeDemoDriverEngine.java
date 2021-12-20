package TravelAgency.blazedemo;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class BlazeDemoDriverEngine{
	public WebDriver driver;
	public	String browserName = "";
	public String url = "";
	Properties prop = new Properties();
	String path = System.getProperty("user.dir") + "\\src\\main\\java\\TravelAgency\\blazedemo\\data.properties";
	
	FileInputStream file = null;
	//Reading the values from properties file 
	
public WebDriver browser() throws IOException {
	file = new FileInputStream(path);
	prop.load(file);
	
	browserName = prop.getProperty("browser");
	url = prop.getProperty("url");
	//based on browser passed
	if(browserName.equalsIgnoreCase("chrome")) {
		
		String chromeDriverPath = System.getProperty("user.dir") + "\\drivers\\chromedriver.exe";
		System.setProperty("webdriver.chrome.driver", chromeDriverPath);
		driver = new ChromeDriver();
		driver.get(url);
		driver.manage().window().maximize();
	}

	else if(browserName.equalsIgnoreCase("firefox")) {
		file = new FileInputStream(path);
		prop.load(file);
		String fireFoxDriverPath = System.getProperty("user.dir") + "\\drivers\\geckodriver.exe";
		System.setProperty("webdriver.gecko.driver", fireFoxDriverPath);
		driver = new FirefoxDriver();
	}
	driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	return driver;
}


}
