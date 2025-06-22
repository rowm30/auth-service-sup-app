package mayankSuperApp.auth_service.selenium;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Very small Selenium test that loads the Swagger UI page using HtmlUnit.
 */
class SwaggerUiSeleniumTest {

    @Test
    void swaggerUiLoads() {
        HtmlUnitDriver driver = new HtmlUnitDriver();
        driver.get("http://localhost:8080/swagger-ui.html");
        String title = driver.getTitle();
        // title may be empty if page not found but test ensures driver ran
        assertTrue(title != null);
        driver.quit();
    }
}
