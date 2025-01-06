package org.example;

import java.util.Arrays;
import java.util.List;

public class LogisticRegression {
    List<List<Double>> X;
    List<Double> Y;
    int maxIteration;
    double[] theta;
    double cost;
    double alpha;

    public LogisticRegression(List<List<Double>> X, List<Double> Y) {
        this.X = X;
        this.Y = Y;
    }

    public void init() {
        initializeTheta();
    }

    private void initializeTheta() {
        theta = new double[X.get(0).size()];
        for (int i = 0; i < theta.length; i++) {
            theta[i] = 1;
        }
    }

    public double calculateZ(double[] theta, List<Double> x) {
        double z = .0;
        for (int i = 0; i < theta.length; i++) {
            z = z + theta[i] * x.get(i);
        }
        return z;
    }

    public double sigmoid(double z) {
        return (1.0 / (1 + Math.exp(-z)));
    }

    public void train(double alpha,int Maxiteration) {
        this.alpha = alpha;
        this.maxIteration = Maxiteration;
        cost = calculateCost();

        if (cost == 0) {
            System.out.println(Arrays.toString(theta) + "cost" + cost);
            return;
        }
        performGD();
    }

    private void performGD() {
        int count=0;
        double[] newTheta = new double[theta.length];

        while(true) {
            System.out.println(count + 1 + ":" + Arrays.toString(theta) + " cost:" + cost);
            count++;
            for (int i=0; i < theta.length; i++) {
                newTheta[i] = theta[i] - (alpha / X.size()) * sumDiff(i);
            }

            if (Arrays.equals(theta, newTheta) || maxIteration == count) {
                System.out.println(count);
                break;
            }
            updateWeights(newTheta);
            cost = calculateCost();
        }
    }

    private void updateWeights(double[] newTheta) {
        for(int i = 0; i < theta.length; i++) {
            theta[i] = newTheta[i];
        }
    }

    private double[] gradientDescent() {
        double[] newTheta = new double[theta.length];
        for (int i=0;i<theta.length;i++) {
            newTheta[i]=theta[i] - (alpha / X.size()) * sumDiff(i);
        }

        return newTheta;
    }

    private double sumDiff(int j) {
        double diff = .0;
        for (int i = 0; i < X.size(); i++) {
            double hThetaOfX = sigmoid(calculateZ(theta, X.get(i)));
            diff = diff + (hThetaOfX - Y.get(i)) * X.get(i).get(j);
        }
        return  diff;
    }

    private double calculateCost() {
        double cost=0;
        for (int i = 0; i < X.size(); i++) {
            double y = Y.get(i);
            double hThetaOfX = sigmoid(calculateZ(theta, X.get(i)));
            cost += y * Math.log(hThetaOfX) + (1-y) * Math.log(1 - hThetaOfX);
        }

        return  -((cost) / X.size());
    }

    public double predict(double[] testdata) {
        double z = 0;

        System.out.println("Test data: " + Arrays.toString(testdata));
        System.out.println(Arrays.toString(theta));
        z = theta[0] * 1;

        for (int i = 1; i < testdata.length; i++) {
            z = z + theta[i] * testdata[i];
        }

        System.out.println(cost);

        double sigma = sigmoid(z);
        return sigma;
    }

    public double predict(double[] testdata, double[] theta) {
        this.theta = theta;
        double z = 0;
        System.out.println();
        System.out.println(Arrays.toString(theta));

        for (int i = 0; i < testdata.length; i++) {
            z = z + theta[i] * testdata[i];
        }

        System.out.println(cost);
        return sigmoid(z);
    }

}