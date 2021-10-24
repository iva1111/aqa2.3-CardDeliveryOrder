package ru.netology.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;


class CardDeliveryTest {
    private DataGenerator.RegistrationByCardInfo registrationByCardInfo;

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
        registrationByCardInfo = DataGenerator.Registration.generateByCard("ru");
    }

    @Test
    void shouldSubmitRequest() {

        $("[data-test-id=city] input").setValue(registrationByCardInfo.getCity());
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(DataGenerator.Registration.getDate(4));
        $("[data-test-id=name] input").setValue(registrationByCardInfo.getName());
        $("[data-test-id=phone] input").setValue(registrationByCardInfo.getPhoneNumber());
        $("[data-test-id=agreement]").click();
        $(byText("Запланировать")).click();
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").sendKeys(DataGenerator.Registration.getRescheduledDate(8));
        $(byText("Запланировать")).click();
        $(byText("Перепланировать")).click();
        $("[data-test-id=success-notification]").shouldBe(visible, Duration.ofSeconds(15));
    }
}