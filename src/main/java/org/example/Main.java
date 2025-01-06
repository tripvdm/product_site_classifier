package org.example;

import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<List<Double>> lists = Arrays.asList(
                Arrays.asList(0.4, 0.0, 0.0, 0.0),
                Arrays.asList(0.5, 0.2, 0.2, 0.0),
                Arrays.asList(0.3, 0.1, 0.3, 0.0),
                Arrays.asList(0.2, 0.0, 0.0, 0.0),
                Arrays.asList(0.4, 0.0, 0.0, 0.0)
        );

        List<Double> ys = Arrays.asList(0.0, 1.0, 1.0, 0.0, 0.0);

        LogisticRegression logisticRegression = new LogisticRegression(lists, ys);
        logisticRegression.init();

        logisticRegression.train(0.001, 10000000);
        double res = logisticRegression.predict(new double[] {0.5, 0.5, 0.0, 0.0});

        System.out.println("Результат предсказания = " + res);

        if (res > logisticRegression.cost) {
            System.out.println("Сайт товаров и услуг");
        } else {
            System.out.println("Не сайт товаров и услуг");
        }
    }
}