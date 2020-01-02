package com.leonardonelson.dvr.updater.page;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlInlineFrame;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.javascript.background.JavaScriptJobManager;
import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLDivElement;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DvrConfigPage {

    private static final Logger log = LoggerFactory.getLogger(DvrConfigPage.class);

	private final String configRedeLinkXpath = "//a[@title='Config. rede']";
	private final String ipv6Input = "ipv6Address";
	private final String logoutButton = "laExit";

	private WebClient webClient;

	private HtmlPage configPage;
	private HtmlPage networkConfigPage;

	public DvrConfigPage(WebClient webClient) {
		this.webClient = webClient;
	}

	public DvrConfigPage openNetworkConfig() throws IOException, InterruptedException {
		configPage = (HtmlPage) this.webClient.getTopLevelWindows().get(0).getEnclosedPage();
		log.info("Config page successfully loaded.");
		log.info("Trying to access Network Settings Page...");
		networkConfigPage = (HtmlPage) configPage.getFrameByName("ContentFrame").getEnclosedPage();

		// DomElement element = networkConfigPage.getElementById("content-left-configtree");
		// log.info("VAI CARALHA".concat(String.valueOf(element.getChildElementCount())));
		log.info(("VAI CARALHA".concat(String.valueOf(networkConfigPage.getElementById("mz_8_tree_5").getChildElementCount()))));
		while(networkConfigPage.getElementById("mz_8_link_8") == null) {
			Thread.sleep(2000);
			webClient.waitForBackgroundJavaScriptStartingBefore(10000);
			webClient.waitForBackgroundJavaScript(10000);
		}
		log.info("TEUTOBA: " + networkConfigPage.getElementById("mz_8_link_8").isDisplayed());
		// log.info(networkConfigPage.getWebResponse().getContentAsString());
		log.info(("VAI CARALHA".concat(String.valueOf(networkConfigPage.getElementById("mz_8_tree_5").getChildElementCount()))));
		((HtmlAnchor) networkConfigPage.getByXPath(configRedeLinkXpath).get(0)).click();
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
