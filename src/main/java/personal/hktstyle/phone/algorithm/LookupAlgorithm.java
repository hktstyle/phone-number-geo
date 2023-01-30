package personal.hktstyle.phone.algorithm;


import personal.hktstyle.phone.model.PhoneNumberInfo;

import java.util.Optional;

/**
 * 查找算子
 *
 * Created by hky on 01/29/2023
 **/
public interface LookupAlgorithm {

    /**
     * 装载数据.
     *
     * @param data 来自phone.dat
     */
    void loadData(byte[] data);

    /**
     * 根据电话号码查找归属地
     * @param phoneNumber 电话号码, 11位或前7位
     * @return 电话号码归属信息
     */
    Optional<PhoneNumberInfo> lookup(String phoneNumber);

}
