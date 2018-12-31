package com.leonardonelson.dvr.updater.page;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;

import java.io.IOException;

public class DvrLoginPage {

	private final String loginInput = "loginUserName";
	private final String pwdInput = "loginPassword";
	private final String loginButton = "//span[@class=\"loginbtn\"]";
	private final String configMenuLink = "laConfig";

	private String url;

	private WebClient webClient;

	private HtmlPage page;

	public DvrLoginPage(WebClient webClient) {
		this.webClient = webClient;
	}

	public DvrLoginPage open(String url) throws IOException {
		page = webClient.getPage(url);
		this.url = url;
		return this;
	}
	
	public DvrLoginPage login(String username, String password) throws IOException, InterruptedException {
		((HtmlInput) page.getHtmlElementById(loginInput)).setValueAttribute(username);
		((HtmlInput) page.getHtmlElementById(pwdInput)).setValueAttribute(password);
		((HtmlSpan) page.getFirstByXPath(loginButton)).click();
		Thread.sleep(5000);
		page = webClient.getPage(url + "/doc/page/main.asp");
		return this;
	}
	
	public DvrConfigPage navigateToConfigPage() throws IOException, InterruptedException {
		HtmlPage configPage = page.getElementByName(configMenuLink).click();
		Thread.sleep(5000);
		configPage = (HtmlPage) webClient.getCurrentWindow().getEnclosedPage();
		return new DvrConfigPage(webClient, page);
	}
	
}
