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

    private static final String OS = System.getProperty("os.name").toLowerCase();

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Bean(destroyMethod = "quit")
    public WebDriver webDriver() {
        final String driverOS = isWindows() ? "windows" : "linux";
        final String extension = isWindows() ? ".exe" : "";
        System.setProperty("webdriver.gecko.driver",
                            Application.class.getResource(
                                    "/drivers/" + driverOS + "/geckodriver" + extension).getPath());
        FirefoxOptions options = new FirefoxOptions();
        options.setHeadless(true);
        final WebDriver driver = new FirefoxDriver(options);
        driver.manage().window().setSize(new Dimension(1920,1080));
        driver.manage().window().maximize();
        return driver;
    }

    @Bean
    public RestOperations restOperations() {
       return new RestTemplate();
    }

    public static boolean isWindows() {
        return (OS.indexOf("win") >= 0);
    }

}