package ru.netology.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.data.DataGenerator;
import ru.netology.data.DataGenerator.Registration;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.DataGenerator.Registration.getDate;


class CardDeliveryTest {
    private DataGenerator.RegistrationByCardInfo registrationByCardInfo;

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
        registrationByCardInfo = Registration.generateByCard("ru");
    }

    @Test
    void shouldSubmitRequest() {
        $("[data-test-id=city] input").setValue(registrationByCardInfo.getCity());
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(getDate(3));
        $("[data-test-id=name] input").setValue(registrationByCardInfo.getName());
        $("[data-test-id=phone] input").setValue(registrationByCardInfo.getPhoneNumber());
        $("[data-test-id=agreement]").click();
        $(byText("Запланировать")).click();
        $("[data-test-id='success-notification'] .notification__content")
                .shouldHave(text("Встреча успешно запланирована на " + getDate(3)));
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").sendKeys(getDate(4));
        $(byText("Запланировать")).click();
        $("[data-test-id='replan-notification']>.notification__content").
                shouldHave(text("У вас уже запланирована встреча на другую дату. Перепланировать?"));
        $(byText("Перепланировать")).click();
        $("[data-test-id=success-notification]")
                .shouldHave(text("Успешно! Встреча успешно запланирована на " + getDate(4)));
        $("[data-test-id=success-notification]").shouldBe(visible, Duration.ofSeconds(15));
    }

}