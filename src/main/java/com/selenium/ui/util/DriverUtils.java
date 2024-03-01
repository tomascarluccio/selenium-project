package com.shaanstraining.ui.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class DriverUtils {
	
	private static Properties props;
	private static final String DRIVER_PROP_FILE = "src/main/resources/driver.properties";
	
	public static RemoteWebDriver getDriver(RemoteWebDriver driver, String browser, String baseUrl) throws IOException,
	FileNotFoundException, Exception {
		
		props = new Properties();
		props.load(new FileInputStream(DRIVER_PROP_FILE));
		
		if (isWindows()) {
			if (browser.equalsIgnoreCase("firefox")) {
				FirefoxOptions options = new FirefoxOptions();
				options.setCapability(CapabilityType.BROWSER_VERSION, 48);
				
				System.setProperty("webdriver.gecko.driver", props.getProperty(Constants.FIREFOX_DRIVER_WIN));
				driver = new FirefoxDriver();
			}
			if (browser.equalsIgnoreCase("chrome")) {
				System.setProperty("webdriver.chrome.driver", props.getProperty(Constants.CHROME_DRIVER_WIN));
				driver = new ChromeDriver();
			}
			if (browser.equalsIgnoreCase("iexplore")) {
				InternetExplorerOptions options = new InternetExplorerOptions();
				options.introduceFlakinessByIgnoringSecurityDomains();
				options.ignoreZoomSettings();
				
				System.setProperty("webdriver.ie.driver", props.getProperty(Constants.IE_DRIVER_WIN));	
				driver = new InternetExplorerDriver(options);
			}	
			
		}else if (isMac()){
			if (browser.equalsIgnoreCase("firefox")) {
				FirefoxOptions options = new FirefoxOptions();
				options.setCapability(CapabilityType.BROWSER_VERSION, 48);
				
				System.setProperty("webdriver.gecko.driver", props.getProperty(Constants.FIREFOX_DRIVER_MAC));	
				driver = new FirefoxDriver();
				
			}
			if (browser.equalsIgnoreCase("chrome")) {
				System.setProperty("webdriver.chrome.driver", props.getProperty(Constants.CHROME_DRIVER_MAC));
				driver = new ChromeDriver();
			}	
			
		}		
		driver.get(baseUrl);
		driver.manage().window().maximize();
		
		return driver;
	}
	
	public static RemoteWebDriver getDriver(RemoteWebDriver driver, String hub, String browser, String baseUrl) throws IOException,
	FileNotFoundException, Exception {
		
		DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
		
		/*desiredCapabilities.setBrowserName("Chrome");
		desiredCapabilities.setVersion("71.0");
		desiredCapabilities.setCapability(CapabilityType.PLATFORM, "Windows 10");
		desiredCapabilities.setCapability("username", "shaanstraining");
		desiredCapabilities.setCapability("accessKey", "0c7835c4-0571-4218-ac07-94ecc7c81154");
		desiredCapabilities.setCapability("name", "shaanstraining UI Test");*/
		
		desiredCapabilities.setBrowserName(System.getenv("SELENIUM_BROWSER"));
		desiredCapabilities.setVersion(System.getenv("SELENIUM_VERSION"));
		desiredCapabilities.setCapability(CapabilityType.PLATFORM, System.getenv("SELENIUM_PLATFORM"));
		desiredCapabilities.setCapability("username", System.getenv("SAUCE_USERNAME"));
		desiredCapabilities.setCapability("accessKey", System.getenv("SAUCE_ACCESS_KEY"));
		desiredCapabilities.setCapability("name", "shaanstraining UI Test");
		
		driver = new RemoteWebDriver(new URL(hub), desiredCapabilities);
		driver.get(baseUrl);
		
		return driver;
	}
		
	private static boolean isWindows() {
		String os = System.getProperty("os.name");
		return os.startsWith("Windows");
	}
	
	private static boolean isMac() {
		String os = System.getProperty("os.name");
		return os.startsWith("Mac");
	}

}
