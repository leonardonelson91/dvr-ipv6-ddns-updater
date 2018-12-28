package com.leonardonelson.dvr.updater.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DvrLoginPage {

	private By loginInput = By.id("loginUserName");
	private By pwdInput = By.id("loginPassword");
	private By loginButton = By.className("loginbtn");
	private By configMenuLink = By.name("laConfig");

	private WebDriver driver;
	
	private WebDriverWait wait;
	
	public DvrLoginPage(WebDriver driver){
		this.driver = driver;
		wait = new WebDriverWait(driver, 10);
	}
	
	public DvrLoginPage open(String url){
		driver.get(url);
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(loginInput)));
		return this;
	}
	
	public DvrLoginPage login(String username, String password){
		driver.findElement(loginInput).sendKeys(username);
		driver.findElement(pwdInput).sendKeys(password);
		driver.findElement(loginButton).click();
		return this;
	}
	
	public DvrLoginPage navigateToConfigPage() {
		wait.until(ExpectedConditions.elementToBeClickable(configMenuLink));
		driver.findElement(configMenuLink).click();
		return this;
	}
	
}
