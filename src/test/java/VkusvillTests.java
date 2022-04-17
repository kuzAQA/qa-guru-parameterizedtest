import com.codeborne.selenide.*;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
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
import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Feature("Тесты для домашки по Allure")
@DisplayName("Тесты главной страницы Вкусвилл")
public class VkusvillTests extends BaseTest {
    Utils util = new Utils();
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
        step("Открыть главную страницу", () -> {
            open("https://vkusvill.ru");
        });
        step("Найти " + testData + " в меню и кликнуть", () -> {
            headerMenu.$(byLinkText(testData)).shouldBe(visible).click();
        });
        step("Найти " + testData + " в хлебных крошках и проверить, что оно совпадает с " + testData, () -> {
            breadcrumbs.$(byTitle(testData)).shouldBe(visible).shouldHave(text(testData));
        });
        util.attachSource();
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
        step("Открыть главную страницу", () -> {
            open("https://vkusvill.ru");
        });
        step("Найти " + testData + " в меню и кликнуть", () -> {
            headerMenu.$(byLinkText(testData)).shouldBe(visible).click();
        });
        step("Проверить title открывшейся страницы", () -> {
            assertEquals(expectedValue, WebDriverRunner.getWebDriver().getTitle());
        });
        util.attachSource();
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
        step("Открыть главную \"Каталог\"", () -> {
            open("https://vkusvill.ru/goods");
        });
        step("Кликнуть на пункт " + testData, () -> {
            $("#sections_col_lvl1").$(byLinkText(testData)).click();
        });
        step("Найти " + testData + " в хлебных крошках и проверить, что оно совпадает с " + testData, () -> {
            breadcrumbs.$(byTitle(testData)).shouldHave(text(testData));
        });
        step("Проверить, что данные в submenu открывшейся страницы совпадают с ожидаемыми", () -> {
            assertTrue(util.getSubmenuText().containsAll(submenu));
        });
        util.attachSource();
    }
}
