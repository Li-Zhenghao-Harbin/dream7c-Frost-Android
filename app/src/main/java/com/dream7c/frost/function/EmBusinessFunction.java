package com.dream7c.frost.function;

public enum EmBusinessFunction implements CommonFunction {
    //CommonTitle
    CALCULATE(1, "计算"),
    PROCESS(2, "处理"),

    //DataCalculation
    AUTO_SUM(10000, "自动求和"),
    MAX(10001, "最大值"),
    MIN(10002, "最小值"),
    SUM(10003, "求和"),
    COUNT(10004, "计数"),

    STATISTIC(20000, "统计"),
    RANGE(20001, "极差"),
    AVERAGE(20002, "平均值"),
    GEOMETRIC_MEAN(20003, "几何平均值"),
    HARMONIC_MEAN(20004, "调和平均值"),
    MEDIAN(20005, "中位数"),
    MODE(20006, "众数"),
    PSDV(20007, "总体标准差的方差"),
    SSDV(20008, "样本标准差的方差"),
    PSD(20009, "总体标准差"),
    SSD(20010, "样本标准差"),
    COEFFICIENT_OF_VARIATION(20011, "离散系数"),

    MATH(30000, "数学和三角函数"),
    ABSOLUTE(30001, "绝对值"),
    OPPOSITE(30002, "相反数"),
    CEIL(30003, "向上取整"),
    FLOOR(30004, "向下取整"),
    ROUND(30005, "四舍五入"),
    GCD(30006, "最大公约数"),
    LCM(30007, "最小公倍数"),
    SIN(30008, "正弦值"),
    COS(30009, "余弦值"),
    TAN(30010, "正切值"),

    //DataProcess
    GENERATE(40000, "生成数据"),
    GENERATE_NUMBER(40001, "随机生成数字"),
    GENERATE_CHAR(40002, "随机生成字符"),
    GENERATE_ARRAY1(40003, "等差数列"),
    GENERATE_ARRAY2(40004, "等比数列"),

    RANDOM_SAMPLING(50000, "随机抽样"),

    SORT(60000, "排序"),
    ASC_SORT(60001, "升序排序"),
    DESC_SORT(60002, "降序排序"),
    CHAOS(60003, "乱序"),
    REVERSE(60004, "逆序"),
    REMOVE_REPEAT(60005, "删除重复")
    ;

    private int funcCode;
    private String funcName;

    EmBusinessFunction(int funcCode, String funcName) {
        this.funcCode = funcCode;
        this.funcName = funcName;
    }

    @Override
    public int getFuncCode() {
        return funcCode;
    }

    public void setFuncCode(int funcCode) {
        this.funcCode = funcCode;
    }

    @Override
    public String getFuncName() {
        return funcName;
    }

    public void setFuncName(String funcName) {
        this.funcName = funcName;
    }
}
