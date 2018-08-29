package src;

import src.helpers.mLogger;
import src.pages.MainPage;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {
            mLogger.setup();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("There were problems creating the log files.");
        }
        MainPage.launch(MainPage.class);
//        ChromeTab tab = new ChromeTab();
//        ChromeTab tab2 = new ChromeTab();

    }
}

/**
 this.webAL.add(driver.findElement(By.name("q")));
 WebElement element = this.webAL.get(0);
 element.sendKeys("Cheese!");
 element.submit();
 System.out.println("Page title is: " + driver.getTitle());

 (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
 public Boolean apply(WebDriver d) {
 return d.getTitle().toLowerCase().startsWith("cheese!");
 }
 });
 System.out.println("Page title is: " + driver.getTitle());
 driver.quit();
 **/