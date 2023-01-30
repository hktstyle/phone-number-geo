/*
 * Copyright 2023 易久批信息技术有限公司. All rights reserved.
 */

package personal.hktstyle.phone.model;


import java.io.Serializable;

/**
 * 电话号码信息
 *
 * Created by hukaiyang on 01/29/2023
 **/
public class PhoneNumberInfo implements Serializable {
    /**
     * 号码
     */
    private String number;
    /**
     * 归属地信息
     */
    private Attribution attribution;
    /**
     * 运营商
     */
    private ISP isp;

    /**
     * 获取 号码
     */
    public String getNumber() {
        return this.number;
    }

    /**
     * 设置 号码
     */
    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * 获取 归属地信息
     */
    public Attribution getAttribution() {
        return this.attribution;
    }

    /**
     * 设置 归属地信息
     */
    public void setAttribution(Attribution attribution) {
        this.attribution = attribution;
    }

    /**
     * 获取 运营商
     */
    public ISP getIsp() {
        return this.isp;
    }

    /**
     * 设置 运营商
     */
    public void setIsp(ISP isp) {
        this.isp = isp;
    }
}
