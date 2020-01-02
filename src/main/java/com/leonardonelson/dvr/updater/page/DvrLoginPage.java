package com.leonardonelson.dvr.updater.page;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DvrLoginPage {

    private static final Logger log = LoggerFactory.getLogger(DvrLoginPage.class);

	private final String loginInput = "loginUserName";
	private final String pwdInput = "loginPassword";
	private final String loginButton = "//span[@class=\"loginbtn\"]";
	private final String configMenuLink = "laConfig";

	private String url;

	private final WebClient webClient;

	private HtmlPage page;

	public DvrLoginPage(final WebClient webClient) {
		this.webClient = webClient;
	}

	public DvrLoginPage open(final String url) throws IOException {
		log.info("Opening DVR Page...");
		page = webClient.getPage(url);
		this.url = url;
		return this;
	}
	
	public DvrLoginPage login(final String username, final String password) throws IOException, InterruptedException {
		log.info("Entering DVR credentials...");
		((HtmlInput) page.getHtmlElementById(loginInput)).setValueAttribute(username);
		((HtmlInput) page.getHtmlElementById(pwdInput)).setValueAttribute(password);
		((HtmlSpan) page.getFirstByXPath(loginButton)).click();
		Thread.sleep(5000);
		page = webClient.getPage(url + "/doc/page/main.asp");
		return this;
	}
	
	public DvrConfigPage navigateToConfigPage() throws IOException, InterruptedException {
		log.info("Successfully logged in.");
		log.info("Navigating to DVR Config Page...");
		page = page.getElementByName(configMenuLink).click();
		return new DvrConfigPage(webClient);
	}
	
}
