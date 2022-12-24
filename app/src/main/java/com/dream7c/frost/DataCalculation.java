package com.dream7c.frost;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DataCalculation {
    //Sum
    public double max(List<Double> list) {
        //最大值
        return list.stream().mapToDouble(x -> x).summaryStatistics().getMax();
    }

    public double min(List<Double> list) {
        //最小值
        return list.stream().mapToDouble(x -> x).summaryStatistics().getMin();
    }

    public double sum(List<Double> list) {
        //求和
        return list.stream().mapToDouble(x -> x).summaryStatistics().getSum();
    }

    public int count(List<String> list) {
        //计数
        return list.size();
    }

    //Statistic
    public double range(List<Double> list) {
        //极差
        return this.max(list) - this.min(list);
    }

    public double average(List<Double> list) {
        //平均值
        return list.stream().mapToDouble(num -> num).summaryStatistics().getAverage();
    }

    public double geometricMean(List<Double> list) {
        //几何平均值
        return list.stream().reduce(1.0, (res, num) -> res * Math.sqrt(num));
    }

    public double harmonicMean(List<Double> list) {
        //调和平均值
        return list.stream().reduce(1e39, (res, num) -> 1 / (1 / res + 1 / num)) * list.size();
    }

    public double median(List<Double> list) {
        //中位值
        List<Double> stp = list.stream().sorted().collect(Collectors.toList());
        if (list.size() % 2 == 0)
            return (stp.get(list.size() / 2 - 1) + stp.get(list.size() / 2)) / 2;
        else return stp.get(list.size() / 2);
    }

    public List<Double> mode(List<Double> list) {
        //众数
        int maxTime = 0, nowTime = 1;
        List<Double> res = new ArrayList<Double>();
        List<Double> stp = list.stream().sorted().collect(Collectors.toList());
        stp.add(stp.get(stp.size() - 1) + 1);
        for (int a = 1; a <= list.size(); a++) {
            if (Double.doubleToLongBits(stp.get(a)) == Double.doubleToLongBits(stp.get(a - 1)))
                nowTime++;
            else {
                if (nowTime >= maxTime) {
                    if (nowTime > maxTime) {
                        maxTime = nowTime;
                        res.clear();
                    }
                    res.add(stp.get(a - 1));
                }
                nowTime = 1;
            }
        }
        return res;
    }

    private double sdCalc(List<Double> list) {
        //计算sigma{(X_i-X_avg)^2}
        Double avg = this.average(list);
        return list.stream().map(num -> Math.pow(num - avg, 2)).reduce(0.0, Double::sum);
    }

    public double psdv(List<Double> list) {
        //总体标准差的方差
        return this.sdCalc(list) / list.size();
    }

    public double ssdv(List<Double> list) {
        //样本标准差的方差
        return this.sdCalc(list) / (list.size() - 1);
    }

    public double psd(List<Double> list) {
        //总体标准差
        return Math.sqrt(this.sdCalc(list) / list.size());
    }

    public double ssd(List<Double> list) {
        //样本标准差
        return Math.sqrt(this.sdCalc(list) / (list.size() - 1));
    }

    public double coefficientOfVariation(List<Double> list) {
        //离散系数
        return this.psd(list) * list.size() / this.sum(list);
    }

    //Math
    public List<Double> absolute(List<Double> list) {
        //绝对值
        return list.stream().map(Math::abs).collect(Collectors.toList());
    }

    public List<Double> opposite(List<Double> list) {
        //相反数
        return list.stream().map(num -> -num).collect(Collectors.toList());
    }

    public List<Double> ceil(List<Double> list) {
        //向上取整
        return list.stream().map(Math::ceil).collect(Collectors.toList());
    }

    public List<Double> floor(List<Double> list) {
        //向下取整
        return list.stream().map(Math::floor).collect(Collectors.toList());
    }

    public List<Double> round(List<Double> list) {
        //四舍五入
        return list.stream().map(num -> Math.floor(num + 0.5)).collect(Collectors.toList());
    }

    private int GCD(int a, int b) {
        if (b == 0) return a;
        return GCD(b, a % b);
    }

    public double nGCD(List<Integer> list) {
        //最大公约数
        return list.stream().reduce(list.get(0), this::GCD);
    }

    public double nLCM(List<Integer> list) {
        //最小公倍数
        return list.stream().reduce(1, (res, num) -> res * num / this.GCD(res, num));
    }

    //Math
    public List<Double> sin(List<Double> list) {
        //正弦值
        return list.stream().map(Math::sin).collect(Collectors.toList());
    }

    public List<Double> cos(List<Double> list) {
        //余弦值
        return list.stream().map(Math::cos).collect(Collectors.toList());
    }

    public List<Double> tan(List<Double> list) {
        //正切值
        return list.stream().map(Math::tan).collect(Collectors.toList());
    }
}
