package personal.hktstyle.phone;


import personal.hktstyle.phone.model.PhoneNumberInfo;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
* PhoneNumberLookup Tester.
*
* Created by hky on Jan 29, 2023
*/
public class PhoneNumberLookupTest {

        /**
    *
    * Method: lookup(String phoneNumber)
    *
    */
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


        /**
    *
    * Method: init()
    *
    */
    public void testInit() throws Exception {
    //TODO: Test goes here...
        /*
        try {
           Method method = PhoneNumberLookup.getClass().getMethod("init");
           method.setAccessible(true);
           method.invoke(<Object>, <Parameters>);
        } catch(NoSuchMethodException e) {
        } catch(IllegalAccessException e) {
        } catch(InvocationTargetException e) {
        }
        */
        }

}
