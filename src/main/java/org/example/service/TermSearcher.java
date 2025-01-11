package org.example.service;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TermSearcher {
    private static final int TIME_OUT_SECONDS = 20;

    private final WebDriver driver;

    private final Map<String, Integer> termMap = new HashMap<>();

    public TermSearcher(final String url) {
        driver = new ChromeDriver();
        driver.get(url);
    }

    public Map<String, Integer> searchTerms() {
        new WebDriverWait(driver, Duration.ofSeconds(TIME_OUT_SECONDS))
                .ignoring(StaleElementReferenceException.class)
                .ignoring(TimeoutException.class)
                .until((WebDriver d) -> {
                    List<WebElement> elements = driver.findElements(By.xpath(".//*"));
                    searchTerms(elements);
                    return true;
                });

        return termMap;
    }

    private void searchTerms(List<WebElement> elements) {
        for (WebElement webElement : elements) {
            String attribute = webElement.getDomAttribute("class");
            if (attribute != null) {
                detectTerm(attribute);
            }
        }
    }

    private void detectTerm(String attribute) {
        ProductTerm[] productTerms = ProductTerm.values();

        for (ProductTerm productTerm : productTerms) {
            String term = productTerm.getTerm();
            if (attribute.toLowerCase().contains(term)) {
                termMap.put(term, termMap.getOrDefault(attribute, 0) + 1);
            }
        }
    }
}
