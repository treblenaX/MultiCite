package src.helpers;

import org.openqa.selenium.By;

public class Interfaces {
    public interface EnumElement {
        String getKey();
        By getLocator();
    }
}
