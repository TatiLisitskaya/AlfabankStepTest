

import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.*;
import io.qameta.allure.selenide.AllureSelenide;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;


public class AlfaBankTest extends BaseTest {
    @Attachment(value = "А что тут у нас?", type = "image/png", fileExtension = "png")
    public byte[] attachScreenshot() {
        return ((TakesScreenshot) WebDriverRunner.getWebDriver()).getScreenshotAs(OutputType.BYTES);
    }

    @Test
    @Feature("Анкета для Альфа-счета")
    @Story("Заполнение анкеты и проверка мобильного номера телефона")
    @Owner("Tatiana Lisitskaya")
    @Severity(SeverityLevel.BLOCKER)
    @Link(value = "Testing", url = "https://alfabank.ru//make-money/savings-account/alfa/")
    @DisplayName("Заполнение анкеты")

    public void testAlfaLambda() {

        SelenideLogger.addListener("allure", new AllureSelenide());
        step("Открытие главной страницы", () -> {
            open(baseUrl);
            attachScreenshot();
        });
        step("Переход на страницу анкеты", () -> {
            $(byText("Вклады")).click();
            $(byLinkText("Альфа-Счёт")).click();
            attachScreenshot();
        });
        step("Заполнение анкеты", () -> {
            $(byName("fullName")).setValue("Ганс Христиан Андерсон");
            $(byName("passportBirthDateField")).setValue("01.01.2000");
            $(byName("phone")).setValue("9999860270");
            $(byName("email")).setValue("mail@mail.com");
            attachScreenshot();
        });
        step("Проверка заполнения анкеты", () -> {
            $("#alfa #form").shouldHave(appear);
            $("[value='Ганс']").shouldBe(visible);
            $("[value='Христиан']").shouldBe(visible);
            $("[value='Андерсон']").shouldBe(visible);
            $(byText("Мужской")).click();
            attachScreenshot();
        });
        step("Проверка телефоного номера", () -> {
            $("[data-test-id=button]").click();
            $("[data-test-id=sms-confirmation-modal]").shouldHave(appear);
            $("[data-test-id=sms-confirmation-modal]").shouldHave(text("Введите код из смс"),
                    text("+7 (999) 986-02-70"));
            attachScreenshot();
        });
    }
}
