import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class CardOrderTest {

    @BeforeAll
    static void setupAll() {
        
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        Configuration.browserCapabilities = options;
    }

    @BeforeEach
    void setup() {
        Selenide.open("http://localhost:9999/");
    }

    @Test
    void shouldSubmitCardOrderFormSuccessfully() {
        $("[data-test-id=city] input").setValue("Москва");
        String date = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(date);
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+79636045632");
        $("[data-test-id=agreement]").click();
        $$("button").find(Condition.text("Забронировать")).click();

        $("[data-test-id=notification]")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + date), Duration.ofSeconds(15));
    }
}
