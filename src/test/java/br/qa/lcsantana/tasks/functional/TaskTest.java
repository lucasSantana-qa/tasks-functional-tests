package br.qa.lcsantana.tasks.functional;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;

public class TaskTest {

    private static final Duration tempo = Duration.ofSeconds(10);

    public RemoteWebDriver acessarAplicacao() throws MalformedURLException, URISyntaxException {
        ChromeOptions options = new ChromeOptions();
        RemoteWebDriver driver = new RemoteWebDriver(new URI("http://192.168.1.4:4444/").toURL(), options);
        driver.get("http://192.168.1.4:8001/tasks");
        return driver;
    }

    @Test
    public void deveSalvarTarefaComSucesso() throws MalformedURLException, URISyntaxException {
        WebDriver driver = acessarAplicacao();
        try {
            driver.findElement(By.id("addTodo")).click();
            driver.findElement(By.id("task")).sendKeys("Teste funcional selenium");
            driver.findElement(By.id("dueDate")).sendKeys("12/07/2025");
            driver.findElement(By.id("saveButton")).click();

            WebDriverWait wait = new WebDriverWait(driver, tempo);

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("message")));
            Assert.assertEquals("Success!", driver.findElement(By.id("message")).getText());
        }finally {
            driver.quit();
        }
    }

    @Test
    public void naoDeveSalvarTarefaSemDescricao() throws MalformedURLException, URISyntaxException {
        WebDriver driver = acessarAplicacao();
        try {
            driver.findElement(By.id("addTodo")).click();
            driver.findElement(By.id("dueDate")).sendKeys("12/07/2025");
            driver.findElement(By.id("saveButton")).click();

            WebDriverWait wait = new WebDriverWait(driver, tempo);

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("message")));
            Assert.assertEquals("Fill the task description", driver.findElement(By.id("message")).getText());
        }finally {
          driver.quit();
        }
    }

    @Test
    public void naoDeveSalvarTarefaComDataPassada() throws MalformedURLException, URISyntaxException {
        WebDriver driver = acessarAplicacao();
        try {
            driver.findElement(By.id("addTodo")).click();
            driver.findElement(By.id("task")).sendKeys("Teste funcional selenium");
            driver.findElement(By.id("dueDate")).sendKeys("12/07/2020");
            driver.findElement(By.id("saveButton")).click();

            WebDriverWait wait = new WebDriverWait(driver, tempo);

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("message")));
            Assert.assertEquals("Due date must not be in past", driver.findElement(By.id("message")).getText());
        }finally {
            driver.quit();
        }
    }

    @Test
    public void naoDeveSalvarTarefaSemData() throws MalformedURLException, URISyntaxException {
        WebDriver driver = acessarAplicacao();
        try {
            driver.findElement(By.id("addTodo")).click();
            driver.findElement(By.id("task")).sendKeys("Teste funcional selenium");
            driver.findElement(By.id("dueDate")).sendKeys("");
            driver.findElement(By.id("saveButton")).click();

            WebDriverWait wait = new WebDriverWait(driver, tempo);

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("message")));
            Assert.assertEquals("Fill the due date", driver.findElement(By.id("message")).getText());
        } finally {
            driver.quit();
        }
    }
}
