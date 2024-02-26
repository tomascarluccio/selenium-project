package com.shaanstraining.ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

public class HomePage extends BasePage{
	
	public void search(RemoteWebDriver driver, String searchTerm){
		WebElement element = driver.findElement(By.name("q"));
        element.sendKeys(searchTerm);
        element.submit();
	}

}
