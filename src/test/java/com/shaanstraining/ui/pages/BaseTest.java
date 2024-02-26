package com.shaanstraining.ui.pages;

import java.io.FileInputStream;
import java.net.InetAddress;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.remote.RemoteWebDriver;

import com.shaanstraining.ui.util.DriverUtils;

public abstract class BaseTest {
	protected RemoteWebDriver driver;
	private Properties props = getProperties();
	
	public BaseTest(String baseUrl) {
		try {
			//String baseUrl = props.getProperty("baseUrl");
			String browser = props.getProperty("defaultBrowser");
			
			String hub = (String) props.getOrDefault("hub", "");
			if (hub.isEmpty()) {
				driver = DriverUtils.getDriver(driver, browser, baseUrl);
			}else {
				driver = DriverUtils.getDriver(driver, hub, browser, baseUrl);
			}
			
			long implicitWaitTimeout = Long.parseLong(props.getProperty("implicitWaitTimeout"));
			driver.manage().timeouts().implicitlyWait(implicitWaitTimeout, TimeUnit.MILLISECONDS);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	public Properties getProperties() {
		InetAddress host = null;
		props = new Properties();
		try {
            host = InetAddress.getLocalHost();            
            if(host.getHostName().startsWith("qa")) { 
    				props.load(new FileInputStream("src/main/resources/env-qa.properties"));
	    		} else{
	    			props.load(new FileInputStream("src/main/resources/env-regression.properties"));
	    		}
        } catch (Exception ex) {
            ex.printStackTrace();
        }
		return props;
		
	}



}
