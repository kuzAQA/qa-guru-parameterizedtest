import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import jdk.jfr.Category;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import static com.codeborne.selenide.Selenide.closeWebDriver;

public class BaseTest {
    @BeforeAll
    static void configurationTests() {
        Configuration.holdBrowserOpen = false; //true = не закрывать браузер после тестов
        Configuration.browserSize = "1792x1080";
        Configuration.browserPosition = "0x0";
    }

    @BeforeEach
    void beforeTest() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }
    @AfterEach
    void afterTest() {
        closeWebDriver();
    }
}
