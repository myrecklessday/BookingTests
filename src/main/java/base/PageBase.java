package base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PageBase {
    protected WebDriver driver;
    private WebDriverWait wait;

    public PageBase(WebDriver driver){
        PageFactory.initElements(driver, this);
        this.driver = driver;
        wait = new WebDriverWait(driver, 10);
    }

    protected void waitForElementVisible(WebElement element){
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    protected WebElement searchElement(By by){
        return driver.findElement(by);
    }

}
