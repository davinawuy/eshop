package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
class CreateProductFunctionalTest {

    @LocalServerPort
    private int serverPort;

    @Value("${app.baseUrl:http://localhost}")
    private String testBaseUrl;

    private String baseUrl;

    @BeforeEach
    void setupTest() {
        baseUrl = String.format("%s:%d", testBaseUrl, serverPort);
    }

    private void createProduct(ChromeDriver driver, String name, String quantity) {
        driver.get(baseUrl + "/product/create");

        WebElement nameInput = driver.findElement(By.id("nameInput"));
        nameInput.sendKeys(name);

        WebElement quantityInput = driver.findElement(By.id("quantityInput"));
        quantityInput.sendKeys(quantity);

        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        submitButton.click();

        driver.get(baseUrl + "/product/list");
    }

    @Test
    void newProductDisplaysCorrectName(ChromeDriver driver) {
        String testName = "Test Product Name";
        String testQuantity = "5";
        createProduct(driver, testName, testQuantity);

        WebElement lastRowNameCell = driver.findElement(
                By.xpath("//table/tbody/tr[last()]/td[1]")
        );

        assertEquals(testName, lastRowNameCell.getText());
    }

    @Test
    void newProductDisplaysCorrectQuantity(ChromeDriver driver) {
        String testName = "Test Product Name 2";
        String testQuantity = "10";
        createProduct(driver, testName, testQuantity);

        WebElement createdProductRowQuantity = driver.findElement(
                By.xpath("//td[contains(text(),'" + testQuantity + "')]")
        );
        assertEquals(testQuantity, createdProductRowQuantity.getText());
    }
}
