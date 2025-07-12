package br.qa.lcsantana.tasks.functional;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TaskTest {

    private static WebDriver driver;

    @Before
    public void setup() {
        WebDriverManager.chromedriver().setup();
        if (driver == null) {
            driver = new ChromeDriver();
        }
        driver.get("http://localhost:8001/tasks");
    }

    @After
    public void teardown() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    @Test
    public void deveSalvarTarefaComSucesso() {
        driver.findElement(By.id("addTodo")).click();
        driver.findElement(By.id("task")).sendKeys("Teste funcional selenium");
        driver.findElement(By.id("dueDate")).sendKeys("12/07/2025");
        driver.findElement(By.id("saveButton")).click();

        WebDriverWait wait = new WebDriverWait(driver, 10);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("message")));
        Assert.assertEquals("Success!", driver.findElement(By.id("message")).getText());
    }

    @Test
    public void naoDeveSalvarTarefaSemDescricao() {
        driver.findElement(By.id("addTodo")).click();
        driver.findElement(By.id("dueDate")).sendKeys("12/07/2025");
        driver.findElement(By.id("saveButton")).click();

        WebDriverWait wait = new WebDriverWait(driver, 10);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("message")));
        Assert.assertEquals("Fill the task description", driver.findElement(By.id("message")).getText());
    }

    @Test
    public void naoDeveSalvarTarefaComDataPassada() {
        driver.findElement(By.id("addTodo")).click();
        driver.findElement(By.id("task")).sendKeys("Teste funcional selenium");
        driver.findElement(By.id("dueDate")).sendKeys("12/07/2020");
        driver.findElement(By.id("saveButton")).click();

        WebDriverWait wait = new WebDriverWait(driver, 10);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("message")));
        Assert.assertEquals("Due date must not be in past", driver.findElement(By.id("message")).getText());
    }

    @Test
    public void naoDeveSalvarTarefaSemData() {
        driver.findElement(By.id("addTodo")).click();
        driver.findElement(By.id("task")).sendKeys("Teste funcional selenium");
        driver.findElement(By.id("dueDate")).sendKeys("");
        driver.findElement(By.id("saveButton")).click();

        WebDriverWait wait = new WebDriverWait(driver, 10);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("message")));
        Assert.assertEquals("Fill the due date", driver.findElement(By.id("message")).getText());
    }
}
