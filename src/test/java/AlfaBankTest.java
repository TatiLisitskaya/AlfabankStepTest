
import com.codeborne.selenide.SelenideElement;
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
    SelenideElement link = $(byText("Вклады")),
            linkText = $(byLinkText("Альфа-Счёт")),
            name = $(byName("fullName")),
            birthDate = $(byName("passportBirthDateField")),
            phone = $(byName("phone")),
            mail = $(byName("email")),
            form = $("#alfa #form"),
            lastName = $("[value='Ганс']"),
            firstName = $("[value='Христиан']"),
            middleName = $("[value='Андерсон']"),
            gender = $(byText("Мужской")),
            button = $("[data-test-id=button]"),
            smsModal = $("[data-test-id=sms-confirmation-modal]");
    String fullName = "Ганс Христиан Андерсон",
            date = "01.01.2000",
            phoneNumber = "9999860270",
            mailAdress = "mail@mail.com",
            text = "Введите код из смс",
            phoneNumberText = "+7 (999) 986-02-70";

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
            link.click();
            linkText.click();
            attachScreenshot();
        });
        step("Заполнение анкеты", () -> {
            name.setValue(fullName);
            birthDate.setValue(date);
            phone.setValue(phoneNumber);
            mail.setValue(mailAdress);
            attachScreenshot();
        });
        step("Проверка заполнения анкеты", () -> {
            form.shouldHave(appear);
            lastName.shouldBe(visible);
            firstName.shouldBe(visible);
            middleName.shouldBe(visible);
            gender.click();
            attachScreenshot();
        });
        step("Проверка телефоного номера", () -> {
            button.click();
            smsModal.shouldHave(appear);
            smsModal.shouldHave(text(text),
                    text(phoneNumberText));
            attachScreenshot();
        });
    }
}
