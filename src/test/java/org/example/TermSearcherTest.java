package org.example;

import org.example.service.TermSearcher;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Map;

class TermSearcherTest {


    @ParameterizedTest
    @ValueSource(strings = {"https://javarush.com/groups/posts/3365-podkljuchaem-k-nashemu-javarush-proektu-logirovanie-slf4f-i-log4j",
            "https://www.sports.ru",
            "https://www.kommersant.ru",
            "https://www.rbc.ru/politics/26/12/2024/676d59689a79473c9e2467c1",
            "https://finance.mail.ru/2024-12-30/putin-peredal-proizvoditelya-bud-i-klinskogo-vo-vremennoe-upravlenie-64290835/"})
    void searchTerms(String url) {
        TermSearcher termSearcher = new TermSearcher(url);
        termSearcher.searchTerms();

        Map<String, Integer> termMap = termSearcher.getTermMap();

    }
}