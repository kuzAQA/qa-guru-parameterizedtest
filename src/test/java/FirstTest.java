import com.codeborne.selenide.*;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Feature("Тесты для домашки по Allure")
public class FirstTest extends BaseTest {
    SelenideElement headerMenu = $(byAttribute("data-place", "topMenu"));
    SelenideElement breadcrumbs = $("#js-nav-chain");

    @ValueSource(strings = {
            "Сладости, десерты, мороженое",
            "Доступно каждому",
            "Кулинария"
    })
    @Story("Тест c ValueSource")
    @ParameterizedTest(name = "Проверка главного меню Вкусвилл - пункт {0}")
    void vkusvillTestValueSource(String testData) {
        open("https://vkusvill.ru");
        headerMenu
                .$(byLinkText(testData))
                .shouldBe(visible)
                .click();
        breadcrumbs
                .$(byTitle(testData))
                .shouldBe(visible)
                .shouldHave(text(testData));
    }

    @CsvSource(value = {
            "Акции , Бонусы и акции в магазинах «ВкусВилл» | Москва и область",
            "Доступно каждому , Доступно каждому \uD83D\uDC4D ВкусВилл",
            "Кулинария , Кулинария с бесплатной доставкой на дом из «ВкусВилл» | Москва и область",
    },
            delimiter = ','
    )
    @Story("Тест c CsvSource")
    @ParameterizedTest(name = "Проверка главного меню Вкусвилл - {0}, заголовок страницы - {1}")
    void vkusvillTestCsv(String testData, String expectedValue) {
            open("https://vkusvill.ru");
            headerMenu.$(byLinkText(testData)).shouldBe(visible).click();
            assertEquals(expectedValue, WebDriverRunner.getWebDriver().getTitle());
    }


    static Stream<Arguments> vkusvillTestMethodSource() {
        return Stream.of(
                Arguments.of("Новинки", List.of("Все товары категории", "Вторые блюда", "Детское развитие и обучение", "Завтраки")),
                Arguments.of("Хиты", List.of("Все товары категории", "Молочные продукты, яйцо", "Овощи, фрукты и ягоды")),
                Arguments.of("Супермаркет", List.of("Все товары категории", "Новинки", "Акции"))
        );
    }
    @Story("Тест c MethodSource")
    @MethodSource
    @ParameterizedTest(name = "Проверка submenu по категории - {0}")
    void vkusvillTestMethodSource(String testData, List<String> submenu) {
        open("https://vkusvill.ru/goods");
        $("#sections_col_lvl1").$(byLinkText(testData)).click();
        breadcrumbs.$(byTitle(testData)).shouldHave(text(testData));
        assertTrue(Utils.getSubmenuText().containsAll(submenu));
    }
}
