package org.example.service;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TermSearcher {
    private final String url;

    private WebDriver driver;

    private final Map<String, Integer> termMap = new HashMap<>();

    public TermSearcher(final String url) {
        this.url = url;
        driver = new ChromeDriver();
    }

    public Map<String, Integer> searchTerms() {
        manageDriver();
        List<WebElement> elements = driver.findElements(By.xpath(".//*"));
        return searchTerms(elements);
    }

    private void manageDriver() {
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        driver.get(url);
    }

    private Map<String, Integer> searchTerms(List<WebElement> elements) {
        for (WebElement webElement : elements) {
            String attribute = webElement.getDomAttribute("class");
            if (attribute != null) {
                if (attribute.toLowerCase().contains("favorite")) {
                    termMap.put(attribute, termMap.getOrDefault(attribute, 0) + 1);
                }
            }
        }

        return termMap;
    }
}
