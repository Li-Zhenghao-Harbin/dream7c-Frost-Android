package com.dream7c.frost;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DataProcess {
    //Generate
    public List<Integer> generateValueList(int minValue, int maxValue, int count) {
        //随机生成数字
        Random rand = new Random();
        return rand.ints().limit(count).map(num -> Math.abs(num) % (maxValue - minValue + 1) + minValue).boxed().collect(Collectors.toList());
    }

    public List<Character> generateCharList(char minChar, char maxChar, int count) {
        //随机生成字符
        Random rand = new Random();
        return rand.ints().limit(count).map(ch -> Math.abs(ch) % (maxChar - minChar + 1) + minChar).boxed().map(ch -> (char) ch.intValue()).collect(Collectors.toList());
    }

    public List<Integer> generateArray(int initValue, int step, int count, int type) {
        //生成指定数列
        //type=0: 等差数列; type=1: 等比数列
        List<Integer> res = new ArrayList<>();
        res.add(initValue);
        for (int i = 1; i < count; i++) {
            res.add(type == 0 ? res.get(i - 1) + step : res.get(i - 1) * step);
        }
        return res;
    }

    //Random sampling
//    public List<Double> randomSampling(List<Double> list, int selectedCount, boolean repeated) {
//        //随机抽样
//        //repeated: 是否允许重复抽样
//        Random rand=new Random();
//        double []res=new double[selectedCount];
//        List<Double> stp=list.stream().collect(Collectors.toList());
//        for(int a=0,b;a<selectedCount;a++)
//        {
//            if(repeated==true) res[a]=list.get(rand.nextInt()%list.size());
//            else
//            {
//                b=Math.abs(rand.nextInt())%(stp.size()-a);
//                res[a]=stp.get(b);
//                Collections.swap(stp,stp.size()-a-1,b);
//            }
//        }
//        return Arrays.stream(res).boxed().collect(Collectors.toList());
//    }
    public List<String> randomSampling(List<String> list, int selectedCount, boolean repeated) {
        //随机抽样
        //repeated: 是否允许重复抽样
        Random rand = new Random();
        String[] res = new String[selectedCount];
        List<String> stp = new ArrayList<>(list);
        for (int a = 0, b; a < selectedCount; a++) {
            if (repeated) res[a] = list.get(Math.abs(rand.nextInt()) % list.size());
            else {
                b = Math.abs(rand.nextInt()) % (stp.size() - a);
                res[a] = stp.get(b);
                Collections.swap(stp, stp.size() - a - 1, b);
            }
        }
        return Arrays.asList(res);
    }

    //Sort
    public List<Double> ascSort(List<Double> list) {
        //升序排序
        return list.stream().sorted().collect(Collectors.toList());
    }

    public List<Double> descSort(List<Double> list) {
        //降序排序
        return list.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
    }

    //    public List<Double> chaos(List<Double> list)
//    {
//        //乱序
//        Random rand=new Random();
//        List<Integer> stp=IntStream.range(0,list.size()).boxed().collect(Collectors.toList());
//        for(int a=0;a<list.size();a++) Collections.swap(stp,stp.size()-a-1,Math.abs(rand.nextInt())%(stp.size()-a));
//        return IntStream.range(0,list.size()).boxed().map(num->list.get(stp.get(num))).collect(Collectors.toList());
//    }
//    public List<Double> reverse(List<Double> list)
//    {
//        //逆序
//        return IntStream.range(0,list.size()).boxed().map(num->list.get(list.size()-num-1)).collect(Collectors.toList());
//    }
//    public List<String> ascSort(List<String> list)
//    {
//        //升序排序
//        return list.stream().map(Double::parseDouble).sorted().map(String::valueOf).collect(Collectors.toList());
//    }
//    public List<String> descSort(List<String> list)
//    {
//        //降序排序
//        return list.stream().map(Double::parseDouble).sorted(Comparator.reverseOrder()).map(String::valueOf).collect(Collectors.toList());
//    }
    public List<String> chaos(List<String> list) {
        //乱序
        Random rand = new Random();
        List<Integer> stp = IntStream.range(0, list.size()).boxed().collect(Collectors.toList());
        for (int a = 0; a < list.size(); a++)
            Collections.swap(stp, stp.size() - a - 1, Math.abs(rand.nextInt()) % (stp.size() - a));
        return IntStream.range(0, list.size()).boxed().map(num -> list.get(stp.get(num))).collect(Collectors.toList());
    }

    public List<String> reverse(List<String> list) {
        //逆序
        return IntStream.range(0, list.size()).boxed().map(num -> list.get(list.size() - num - 1)).collect(Collectors.toList());
    }

    public List<String> removeRepeat(List<String> list) {
        //删除重复
        return list.stream().distinct().collect(Collectors.toList());
    }
}
