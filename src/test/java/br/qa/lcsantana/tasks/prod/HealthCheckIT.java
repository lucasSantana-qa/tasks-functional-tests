package br.qa.lcsantana.tasks.prod;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

public class HealthCheckIT {

    @Test
    public void healthCheck() throws URISyntaxException, MalformedURLException {
        ChromeOptions options = new ChromeOptions();
        RemoteWebDriver driver = new RemoteWebDriver(new URI("http://192.168.1.7:4444").toURL(), options);
        try {
            driver.get("http://192.168.1.7:9999/tasks");
            String version = driver.findElement(By.id("version")).getText();
            Assert.assertTrue("Não foi localizado informação de build",version.startsWith("build"));
        } finally {
            driver.quit();
        }
    }
}
