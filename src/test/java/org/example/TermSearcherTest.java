package org.example;

import org.example.service.TermSearcher;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.StaleElementReferenceException;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TermSearcherTest {

    @DisplayName("Проверка множества не проодуктовых сервисов")
    @ParameterizedTest
    @ValueSource(strings = {"https://javarush.com/groups/posts/3365-podkljuchaem-k-nashemu-javarush-proektu-logirovanie-slf4f-i-log4j",
            "https://www.sports.ru",
            "https://www.kommersant.ru",
            "https://www.rbc.ru/politics/26/12/2024/676d59689a79473c9e2467c1",
            "https://finance.mail.ru/2024-12-30/putin-peredal-proizvoditelya-bud-i-klinskogo-vo-vremennoe-upravlenie-64290835/"})
    void testSearchTermsWhenTermsNotFound(String url) {
        TermSearcher termSearcher = new TermSearcher(url);
        Map<String, Integer> termMap = termSearcher.searchTerms();

        assertTrue(termMap.keySet().size() <= 2);
    }

    @DisplayName("Проверка множества проодуктовых сервисов")
    @ParameterizedTest
    @ValueSource(strings = {"https://www.wildberries.ru/catalog/215737385/detail.aspx",
            "https://www.avito.ru/moskva/knigi_i_zhurnaly/bombovoe_izdanie_po_numerologii_2024_god_4517181458?context=H4sIAAAAAAAA_wEfAOD_YToxOntzOjEzOiJsb2NhbFByaW9yaXR5IjtiOjA7fQseF2QfAAAA",
            "https://www.ozon.ru/product/shkaf-etazherka-dlya-kuhni-penal-dlya-vannoy-shkaf-basket-2-1691458703/?asb=tu8SiXDg0IwS8r6QKAG5SoKQ%252FKguXsul4lVDUfNrN%252FM%253D&asb2=jgb1kpeC6B_Ja4Cx3Pw9Eq_oF4EYE_cPcJwzYU6m0IHT9b8pmbcElypq66WactNV&avtc=1&avte=4&avts=1735579423",
            "https://store77.net/noutbuki_1/noutbuk_apple_macbook_air_13_displey_retina_s_tekhnologiey_true_tone_late_2020_m1_8_gb_256_gb_ssd_se_1/",
            "https://www.cian.ru/sale/flat/305736633/",
            "https://ostrovok.ru/hotel/russia/moscow/mid9751549/lemar_hotel/?dates=04.01.2025-05.01.2025&guests=2&price=one&q=2395&sid=790dfbe2-b704-4f8b-b389-609fabc8a7ef&serp_price=lemar_hotel.8500.RUB.sr-019418ae-4d2f-78b3-8f3d-b49b7c1016f4"})
    void testSearchTermsWhenTermsFoundForSeveralSites(String url) {
        TermSearcher termSearcher = new TermSearcher(url);
        Map<String, Integer> termMap = termSearcher.searchTerms();

        assertNotNull(termMap);
    }

    @Disabled
    void testSearchTermsWhenStaleElementReferenceException() {
        TermSearcher termSearcher = new TermSearcher("https://www.wildberries.ru/catalog/215737385/detail.aspx");

        assertThrows(StaleElementReferenceException.class, termSearcher::searchTerms);
    }

    @Test
    @DisplayName("Проверка, когда сайт - это проодуктовый сервис")
    void testSearchTermsWhenSiteIsProductService() {
        TermSearcher termSearcher = new TermSearcher("https://www.wildberries.ru/catalog/215737385/detail.aspx");

        Map<String, Integer> terms = termSearcher.searchTerms();

        assertNotNull(terms.get("price"));
    }

    @Test
    @DisplayName("Проверка, когда сайт - это не проодуктовый сервис")
    void testSearchTermsWhenSiteIsNotProductService() {
        TermSearcher termSearcher = new TermSearcher("https://www.rbc.ru/politics/26/12/2024/676d59689a79473c9e2467c1");

        Map<String, Integer> terms = termSearcher.searchTerms();

        assertFalse(terms.containsKey("price"));
    }

    @Test
    @DisplayName("Проверка, когда интернет подключен")
    void testSearchTermsWhenNetworkIsConnected() throws IOException {
        assertTrue(testInet("www.google.com"));
    }

    private boolean testInet(String url) throws IOException {
        Socket socket = new Socket();
        InetSocketAddress addr = new InetSocketAddress(url,80);
        try {
            socket.connect(addr,20_000);
            return true;
        } catch (IOException e) {
            return false;
        } finally {
            socket.close();
        }
    }
}