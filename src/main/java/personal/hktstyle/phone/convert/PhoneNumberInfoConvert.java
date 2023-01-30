/*
 * Copyright 2023 易久批信息技术有限公司. All rights reserved.
 */

package personal.hktstyle.phone.convert;

import personal.hktstyle.phone.model.Attribution;
import personal.hktstyle.phone.model.ISP;
import personal.hktstyle.phone.model.PhoneNumberInfo;

import java.util.Optional;

/**
 * Created by hky on 01/29/2023
 */
public class PhoneNumberInfoConvert {

    public static Optional<PhoneNumberInfo> convertToPhoneNumberInfo(String phoneNumber, byte[] bytes, byte ispMark) {
        String oriString = new String(bytes);
        Attribution attribution = AttributionConvert.convertToAttribution(oriString);

        ISP isp = ISP.of(ispMark).orElse(ISP.UNKNOWN);

        PhoneNumberInfo phoneNumberInfo = new PhoneNumberInfo();
        phoneNumberInfo.setNumber(phoneNumber);
        phoneNumberInfo.setAttribution(attribution);
        phoneNumberInfo.setIsp(isp);
        return Optional.of(phoneNumberInfo);
    }
}
