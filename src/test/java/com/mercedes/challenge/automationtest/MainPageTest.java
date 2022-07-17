package com.mercedes.challenge.automationtest;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;
import com.mercedes.challenge.automationtest.util.ChallengeUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static com.mercedes.challenge.automationtest.page.MainPage.*;
import static com.mercedes.challenge.automationtest.page.CarConfiguratorPage.*;

/**
 * Class to test the price range of cars on Chrome and Edge browsers
 */
public class MainPageTest {

    private WebDriver driver;

    void setupChromeDriver(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        setUp();
    }

    void setupEdgeDriver(){
        WebDriverManager.edgedriver().setup();
        driver = new EdgeDriver();
        setUp();
    }

    public void setUp() {
        driver.manage().window().maximize();
        driver.get("https://www.mercedes-benz.co.uk");

    }


    @Test
    public void testPricesOnChrome() throws InterruptedException {
        setupChromeDriver();
        testPrices("chrome");
    }

    @Test
    public void testPricesOnEdge() throws InterruptedException {
        setupEdgeDriver();
        testPrices("edge");
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    /**
     * Method responsible to test the prices given the browser string
     * @param browser
     * @throws InterruptedException
     */
    private void testPrices(String browser) throws InterruptedException {

        JavascriptExecutor js = (JavascriptExecutor) driver;

        SearchContext shadowRoot = driver.findElement(By.cssSelector("cmm-cookie-banner")).getShadowRoot();
        acceptCookies(shadowRoot);

        shadowRoot = driver.findElement(By.cssSelector("dh-io-vmos > div")).getShadowRoot();
        selectHatchHoverAndOpenConfigurator(shadowRoot, js);

        //Car configurator com.mercedes.challenge.automationtest.page
        shadowRoot = driver.findElement(By.cssSelector(CAR_CONFIGURATOR_SHADOWROOT)).getShadowRoot();
        selectDieselResults(shadowRoot);

        scrollAndTakeScreenshot(shadowRoot, js, browser);

        List<WebElement> elementList = shadowRoot.findElements(By.cssSelector(GET_RESULTS));

        List<BigDecimal> amountList = calculateValuesAndSaveTxt(elementList, browser);

        BigDecimal highestValue = Collections.max(amountList);
        BigDecimal lowestValue = Collections.min(amountList);

        //Validate that the cars are between 15.000 and 60.000
        Assertions.assertTrue(lowestValue.compareTo(new BigDecimal("15.000")) >= 0);
        Assertions.assertTrue(highestValue.compareTo(new BigDecimal("60.000")) <= 0);
    }

    private void acceptCookies(SearchContext shadowRoot) throws InterruptedException {
        // wait everything to be loaded
        Thread.sleep(1500);
        shadowRoot.findElement(By.cssSelector(COOKIE_BANNER_SELECTOR))
                .click();
    }
    private void selectHatchHoverAndOpenConfigurator(SearchContext shadowRoot, JavascriptExecutor js) throws InterruptedException {
        shadowRoot.findElement(By.cssSelector(SELECT_HATCHBACK)).click();

        WebElement aClassLabel =  shadowRoot.findElement(By.cssSelector(ACLASS_SELECTOR));

        Assertions.assertTrue(aClassLabel.getText().contains("A-Class"));

        WebElement hoverDiv = shadowRoot.findElement(By.cssSelector(HATCHBACK_HOVER));

        Actions hover = new Actions(driver);
        hover.moveToElement(hoverDiv).perform();

        Thread.sleep(1500);

        shadowRoot.findElement(By.cssSelector(BUILD_YOUR_CAR)).click();

        Thread.sleep(1500);
        js.executeScript("window.scrollBy(0,750)", "");
    }
    private void selectDieselResults(SearchContext shadowRoot) throws InterruptedException {

        Thread.sleep(1500);
        shadowRoot.findElement(By.cssSelector(CAR_CONFIGURATOR_EXPAND_FUEL))
                .click();

        shadowRoot.findElement(By.cssSelector(FUEL_DIESEL_SELECTOR))
                .click();
    }
    private void scrollAndTakeScreenshot(SearchContext shadowRoot, JavascriptExecutor js, String browser) throws InterruptedException {
        WebElement screenshotElement = shadowRoot.findElement(By.cssSelector(TAKE_SCREENSHOT));

        js.executeScript("window.scrollTo(arguments[0], arguments[1])", screenshotElement.getLocation().x, screenshotElement.getLocation().y-70);

        Thread.sleep(1500);

        ChallengeUtils.takeScreenshot(screenshotElement,browser);
    }
    private List<BigDecimal> calculateValuesAndSaveTxt(List<WebElement> elementList, String browser) {
        List<BigDecimal> amountList = new ArrayList<>();
        AtomicReference<String> currency = new AtomicReference<>("");
        elementList.forEach(webElement -> {
            String amount = webElement.findElement(By.cssSelector(AMOUNT_SELECTOR)).getText().replace(",",".");
            currency.set(amount.substring(0, 1));
            amountList.add(new BigDecimal(amount.substring(1)));
        });

        ChallengeUtils.saveTxt(currency+Collections.min(amountList).toString(), currency+Collections.max(amountList).toString(), browser);

        return amountList;
    }

}
