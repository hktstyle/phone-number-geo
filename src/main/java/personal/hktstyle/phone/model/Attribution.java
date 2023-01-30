/*
 * Copyright 2023 易久批信息技术有限公司. All rights reserved.
 */

package personal.hktstyle.phone.model;

import java.io.Serializable;

/**
 * 归属信息
 *
 * Created by hukaiyang on 01/29/2023
 **/
public class Attribution implements Serializable {
    /**
     * 省份
     */
    private String province;
    /**
     * 城市
     */
    private String city;
    /**
     * 邮政编码
     */
    private String zipCode;
    /**
     * 区号
     */
    private String areaCode;

    /**
     * 获取 省份
     */
    public String getProvince() {
        return this.province;
    }

    /**
     * 设置 省份
     */
    public void setProvince(String province) {
        this.province = province;
    }

    /**
     * 获取 城市
     */
    public String getCity() {
        return this.city;
    }

    /**
     * 设置 城市
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * 获取 邮政编码
     */
    public String getZipCode() {
        return this.zipCode;
    }

    /**
     * 设置 邮政编码
     */
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    /**
     * 获取 区号
     */
    public String getAreaCode() {
        return this.areaCode;
    }

    /**
     * 设置 区号
     */
    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }
}
