package com.leonardonelson.dvr.updater;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableScheduling
public class Application {

    private WebDriver driver;

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Bean(destroyMethod = "quit")
    public WebDriver webDriver() {
        System.setProperty("webdriver.gecko.driver",
                Application.class.getResource("/drivers/geckodriver.exe").getPath());
        FirefoxOptions options = new FirefoxOptions();
        options.setHeadless(true);
        driver = new FirefoxDriver(options);
        driver.manage().window().setSize(new Dimension(1920,1080));
        driver.manage().window().maximize();
        return driver;
    }

    @Bean
    public RestOperations restOperations() {
       return new RestTemplate();
    }
}