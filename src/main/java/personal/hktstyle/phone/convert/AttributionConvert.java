/*
 * Copyright 2023 易久批信息技术有限公司. All rights reserved.
 */

package personal.hktstyle.phone.convert;

import personal.hktstyle.phone.model.Attribution;

/**
 * Created by hky on 01/29/2023
 */
public class AttributionConvert {

    public static Attribution convertToAttribution(String ori) {
        String[] split = ori.split("\\|");
        if (split.length < 4) {
            throw new IllegalStateException("content format error");
        }

        Attribution attribution = new Attribution();
        attribution.setProvince(convertProvince(split[0]));
        attribution.setCity(convertCity(split[1]));
        attribution.setZipCode(split[2]);
        attribution.setAreaCode(split[3]);
        return attribution;
    }

    private static String convertProvince(String province) {
        if (province.startsWith("广西")) {
            return "广西壮族自治区";
        } else if (province.startsWith("内蒙古")) {
            return "内蒙古自治区";
        } else if (province.startsWith("新疆")) {
            return "新疆维吾尔自治区";
        } else if (province.startsWith("西藏")) {
            return "西藏自治区";
        } else if (province.startsWith("宁夏")) {
            return "宁夏回族自治区";
        } else if (province.startsWith("香港")) {
            return "香港特别行政区";
        } else if (province.startsWith("澳门")) {
            return "澳门特别行政区";
        } else if (province.startsWith("上海")) {
            return "上海市";
        } else if (province.startsWith("北京")) {
            return "北京市";
        } else if (province.startsWith("天津")) {
            return "天津市";
        } else if (province.startsWith("重庆")) {
            return "重庆市";
        }
        return province + "省";
    }

    private static String convertCity(String city) {
        return city + "市";
    }
}
