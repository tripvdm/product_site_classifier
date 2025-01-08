import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ProductServiceTest {
    private static Logger logger;

    private WebDriver driver;

    @BeforeEach
    public void setup() {
        driver = new ChromeDriver();
    }

    @BeforeAll
    public static void init() {
        logger = LoggerFactory.getLogger(ProductServiceTest.class);
    }

    @DisplayName("Проверка 10 сайтов товаров или услуг сырых данных html документов")
    @ParameterizedTest
    @ValueSource(strings = {"https://www.wildberries.ru/catalog/215737385/detail.aspx",
            "https://www.avito.ru/moskva/knigi_i_zhurnaly/bombovoe_izdanie_po_numerologii_2024_god_4517181458?context=H4sIAAAAAAAA_wEfAOD_YToxOntzOjEzOiJsb2NhbFByaW9yaXR5IjtiOjA7fQseF2QfAAAA",
            "https://www.ozon.ru/product/shkaf-etazherka-dlya-kuhni-penal-dlya-vannoy-shkaf-basket-2-1691458703/?asb=tu8SiXDg0IwS8r6QKAG5SoKQ%252FKguXsul4lVDUfNrN%252FM%253D&asb2=jgb1kpeC6B_Ja4Cx3Pw9Eq_oF4EYE_cPcJwzYU6m0IHT9b8pmbcElypq66WactNV&avtc=1&avte=4&avts=1735579423",
            "https://store77.net/noutbuki_1/noutbuk_apple_macbook_air_13_displey_retina_s_tekhnologiey_true_tone_late_2020_m1_8_gb_256_gb_ssd_se_1/",
            "https://www.cian.ru/sale/flat/305736633/",
            "https://ostrovok.ru/hotel/russia/moscow/mid9751549/lemar_hotel/?dates=04.01.2025-05.01.2025&guests=2&price=one&q=2395&sid=790dfbe2-b704-4f8b-b389-609fabc8a7ef&serp_price=lemar_hotel.8500.RUB.sr-019418ae-4d2f-78b3-8f3d-b49b7c1016f4"})
    public void testRightTenServices(String url) throws IOException {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(htmlContent -> {
                    assertNotNull(htmlContent);
                }).join();
    }

    @DisplayName("Проверка 10 сайтов БЕЗ товаров или услуг сырых данных html документов")
    @ParameterizedTest
    @ValueSource(strings = {"https://javarush.com/groups/posts/3365-podkljuchaem-k-nashemu-javarush-proektu-logirovanie-slf4f-i-log4j",
            "https://www.sports.ru",
            "https://www.kommersant.ru",
            "https://www.rbc.ru/politics/26/12/2024/676d59689a79473c9e2467c1",
            "https://finance.mail.ru/2024-12-30/putin-peredal-proizvoditelya-bud-i-klinskogo-vo-vremennoe-upravlenie-64290835/"})
    public void testNotRightTenServices(String url) {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(htmlContent -> {
                    assertNotNull(htmlContent);
                }).join();
    }

}
