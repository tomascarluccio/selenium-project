package com.shaanstraining.ui.pages;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.yaml.snakeyaml.Yaml;

public class GMailPageTest extends BaseTest{
	//WebDriver driver;
	static final Logger log = LogManager.getLogger(GMailPageTest.class);
	
	private static final String YAML_DATA =
						"username: shaanstraining\n" + 
						"password: mytmppsswrd\n";
	private static final String YAML_FILE = "src/test/resources/login-ui.yml";
	
	/*@BeforeMethod
	void setUpMethod() { 
		System.setProperty("webdriver.chrome.driver", "/Users/shaan/Downloads/chromedriver");
        driver = new ChromeDriver();        
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	}*/
	
	public GMailPageTest() {
		super("https://www.gmail.com");
	}
	
	@DataProvider(name = "dp")
	public Object[][] parseYaml() {
	    Yaml yaml = new Yaml();	    
	    Map<String, String> map = new HashMap<String, String>();
	    map = (Map<String, String>) yaml.load(YAML_DATA);

	    return new Object[][] {map.values().toArray()};	   
	}
	
	@DataProvider(name = "dp2")
	public Object[][] parseYamlFile() throws FileNotFoundException {
		Yaml yaml = new Yaml();		
		InputStream iStream = new FileInputStream(new File(YAML_FILE));
		Collection<Map<String, String>> usersCollection = (Collection<Map<String, String>>)yaml.load(iStream);
			
		//declare 2D Array to return
		String[][] users2DArray = new String[usersCollection.size()][2];	
		
		//parse logic into 2D Array
		int i = 0;
		for(Map<String, String> usersMap: usersCollection) { //Iterate over collection of users
			Collection userValuesCollection = usersMap.values();				
			for(Object userValue: userValuesCollection) { //Iterate over collection of user values
				Map<String, String> userMap = (Map<String, String>) userValue;
				for(Map.Entry<String, String> credentials: userMap.entrySet()) { //Iterate over map of each value
					if(credentials.getKey().equals("username"))users2DArray[i][0]= credentials.getValue();
					if(credentials.getKey().equals("password"))users2DArray[i][1]= credentials.getValue();
				}
			}
			i++;
		}
				
		return users2DArray;	   
	}
	
	@Test(dataProvider="dp")
	public void loginTest(String user, String pwd) {		
		log.info("gmailPageTest: username: " + user + "\t password: " + pwd);
		WebDriverWait wait = new WebDriverWait(driver, 6);
		
		//driver.get("https://www.gmail.com");
		
		driver.findElement(By.id("identifierId")).sendKeys(user);
		driver.findElement(By.id("identifierNext")).click();
		
		wait.until(ExpectedConditions.elementToBeClickable(By.id("password")));
		driver.findElement(By.name("password")).sendKeys(pwd);
		
		wait.until(ExpectedConditions.elementToBeClickable(By.id("passwordNext")));
		driver.findElement(By.id("passwordNext")).click();
		
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href^='https://accounts.google.com/SignOutOptions']")));
		driver.findElement(By.cssSelector("a[href^='https://accounts.google.com/SignOutOptions']")).click();
		
		driver.findElement(By.xpath("//a[text()='Sign out']")).click();
	}
	
	@AfterMethod
    public void tearDown(ITestResult result) {
        ((JavascriptExecutor)driver).executeScript("sauce:job-result=" + (result.isSuccess() ? "passed" : "failed"));
        driver.quit();
    }
	
}
