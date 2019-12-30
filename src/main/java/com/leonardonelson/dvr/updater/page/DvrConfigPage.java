package com.leonardonelson.dvr.updater.page;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DvrConfigPage {

    private static final Logger log = LoggerFactory.getLogger(DvrConfigPage.class);

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
		log.info("Config page successfully loaded.");
		log.info("Trying to access Network Settings Page...");
		networkConfigPage = (HtmlPage) configPage.getFrameByName("ContentFrame").getEnclosedPage();
		((HtmlAnchor) networkConfigPage.getFirstByXPath(configRedeLinkXpath)).click();
		Thread.sleep(5000);
		networkConfigPage = (HtmlPage) webClient.getCurrentWindow().getEnclosedPage();
		log.info("Network Settings Page successfully loaded.");
		return this;
	}

	public String getIpv6Address(){
		log.info("Getting latest DVR IPv6 Address...");
		final String currentIpv6 = ((HtmlInput) networkConfigPage.getElementById(ipv6Input)).getValueAttribute();
		return currentIpv6;
	}

	public void logout() throws IOException {
		log.info("Loggin out from DVR Page...");
		webClient.setConfirmHandler((page, message) -> true);
		configPage.getElementByName(logoutButton).click();
	}
}
