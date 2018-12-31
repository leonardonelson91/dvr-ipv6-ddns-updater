package com.leonardonelson.dvr.updater.page;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.IOException;

public class DvrConfigPage {

	private final String configRedeLinkXpath = "//a[@title='Network Settings']";
	private final String ipv6Input = "ipv6Address";
	private final String logoutButton = "laExit";

	private WebClient webClient;

	private HtmlPage configPage;
	private HtmlPage networkConfigPage;

	public DvrConfigPage(WebClient webClient, HtmlPage page) {
		this.webClient = webClient;
		this.configPage = page;
	}

	public DvrConfigPage openNetworkConfig() throws IOException, InterruptedException {
		networkConfigPage = (HtmlPage) configPage.getFrameByName("ContentFrame").getEnclosedPage();
		((HtmlAnchor) networkConfigPage.getFirstByXPath(configRedeLinkXpath)).click();
		Thread.sleep(5000);
		networkConfigPage = (HtmlPage) webClient.getCurrentWindow().getEnclosedPage();
		return this;
	}

	public String getIpv6Address(){
		final String currentIpv6 = ((HtmlInput) networkConfigPage.getElementById(ipv6Input)).getValueAttribute();
		return currentIpv6;
	}

	public void logout() throws IOException {
		webClient.setConfirmHandler((page, message) -> true);
		configPage.getElementByName(logoutButton).click();
	}
}
