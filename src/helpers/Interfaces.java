package src.helpers;

import org.openqa.selenium.By;

public class Interfaces {
    public interface EnumElement {
        public String getKey();
        public By getLocator();
    }
}
