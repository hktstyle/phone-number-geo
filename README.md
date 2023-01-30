# 手机归属地查询（重构版） 


## 简介

根据手机号确定手机号运营商即归属地, 支持包括虚拟运营商的中国大陆手机号查询.

forked from [EeeMt/phone-number-geo](https://github.com/EeeMt/phone-number-geo)

## 数据源

数据源`dat`文件来自[xluohome/phonedata](https://github.com/xluohome/phonedata)提供的数据库, 会不定时同步更新数据库

forked from [EeeMt/phone-number-geo](https://github.com/EeeMt/phone-number-geo)

## 示例
```java
class Demo1{
    public static void main(String[] args){
        PhoneNumberLookup phoneNumberLookup = new PhoneNumberLookup();

        List<String> phones = Arrays.asList("13381112603","15135111980","13410188548", "17705155875", "18587899365", "13614712921", "13369911623", "18308000200");

        phones.forEach(phone -> {
            PhoneNumberInfo found = phoneNumberLookup.lookup(phone).orElse(null);
            if (Objects.nonNull(found)) {
                System.out.println(found.getNumber() + "-" + found.getAttribution().getProvince() + "-" + found.getAttribution().getCity() + "-" + found.getAttribution().getZipCode() + "-" + found.getIsp().getCnName());
            }
        });
    }
}
```

## 对比`libphonenumber`
对比[libphonenumber](https://github.com/google/libphonenumber), `libphonenumber`有更多功能, 包括验证号码格式, 格式化, 时区等, 
但基于[xluohome/phonedata](https://github.com/xluohome/phonedata)提供的`dat`数据库能囊括包含虚拟运营商号段的更多号段.  

至于速度, 未做比较, 但本仓库实现已足够快, 选择时建议更多权衡易用性, 功能和数据覆盖范围.

## Benchmark

工程里已内置四种算法, 跑分情况如下:
```
Benchmark                                   Mode  Cnt        Score       Error  Units
BenchmarkRunner.anotherBinarySearchLookup   avgt    5      390.483 ±     3.544  ns/op
BenchmarkRunner.binarySearchLookup          avgt    5      386.357 ±     3.739  ns/op
BenchmarkRunner.prospectBinarySearchLookup  avgt    5      304.622 ±     1.899  ns/op
BenchmarkRunner.sequenceLookup              avgt    5  1555265.227 ± 48814.379  ns/op
```
性能测试源码位于`me.ihxq.projects.pna.benchmark.BenchmarkRunner`, 基于`JMH`

测试样本在每次启动时生成, 供所有算子测试使用, 所以每次测试结果有差异, 结果可用于横向比较, 不适用于纵向比较.

默认使用的是`me.ihxq.projects.pna.algorithm.BinarySearchAlgorithmImpl`, 
可以通过`new PhoneNumberLookup(new AlgorithmYouLike());`使用其他算法;  

也可自行实现算法, 实现`me.ihxq.projects.pna.algorithm.LookupAlgorithm`即可.

## 感谢
- 感谢[xluohome/phonedata](https://github.com/xluohome/phonedata)共享的数据库
- 也参考了@fengjiajie 的java实现[fengjiajie/phone-number-geo](https://github.com/fengjiajie/phone-number-geo)