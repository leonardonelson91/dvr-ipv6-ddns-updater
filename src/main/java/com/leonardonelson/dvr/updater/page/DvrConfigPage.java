package com.leonardonelson.dvr.updater.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DvrConfigPage {

	private By configRedeLink = By.xpath("//a[@title='Config. rede']");
	private By ipv6Input = By.id("ipv6Address");
	private By logoutButton = By.name("laExit");

	private WebDriver driver;
	private WebDriverWait wait;

	public DvrConfigPage(WebDriver driver){
		this.driver = driver;
		wait = new WebDriverWait(driver, 10);
	}

	public DvrConfigPage openNetworkConfig() {
		driver.switchTo().frame("ContentFrame");
		wait.until(ExpectedConditions.elementToBeClickable(configRedeLink));
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		driver.findElement(configRedeLink).click();
		return this;
	}
	
	public String getIpv6Address(){
		wait.until(ExpectedConditions.visibilityOfElementLocated(ipv6Input));
		final String currentIpv6 = driver.findElement(ipv6Input).getAttribute("value");
		driver.switchTo().defaultContent();
		return currentIpv6;
	}

	public void logout() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(logoutButton));
		driver.findElement(logoutButton).click();
		driver.switchTo().alert().accept();
		driver.switchTo().defaultContent();
	}
}
