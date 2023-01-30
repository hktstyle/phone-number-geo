package personal.hktstyle.phone.algorithm;

import personal.hktstyle.phone.convert.PhoneNumberInfoConvert;
import personal.hktstyle.phone.model.PhoneNumberInfo;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Optional;

/**
 * Created by hky on 01/29/2023
 **/
public class SequenceLookupAlgorithmImpl implements LookupAlgorithm {
    private ByteBuffer originalByteBuffer;
    private int indicesOffset;

    @Override
    public void loadData(byte[] data) {
        originalByteBuffer = ByteBuffer.wrap(data).asReadOnlyBuffer();
        originalByteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        //noinspection unused
        int dataVersion = originalByteBuffer.getInt();
        indicesOffset = originalByteBuffer.getInt(4);
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public Optional<PhoneNumberInfo> lookup(String phoneNo) {
        ByteBuffer byteBuffer = originalByteBuffer.asReadOnlyBuffer().order(ByteOrder.LITTLE_ENDIAN);
        if (phoneNo == null) {
            return Optional.empty();
        }
        int phoneNoLength = phoneNo.length();
        if (phoneNoLength < 7 || phoneNoLength > 11) {
            return Optional.empty();
        }

        int attributionIdentity;
        try {
            attributionIdentity = Integer.parseInt(phoneNo.substring(0, 7));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }

        for (int i = indicesOffset; i < byteBuffer.limit(); i = i + 8 + 1) {

            byteBuffer.position(i);
            int phonePrefix = byteBuffer.getInt();
            int infoStart = byteBuffer.getInt();
            byte ispMark = byteBuffer.get();
            if (phonePrefix == attributionIdentity) {
                byteBuffer.position(infoStart);
                //noinspection StatementWithEmptyBody
                while ((byteBuffer.get()) != 0) {
                }
                int infoEnd = byteBuffer.position() - 1;
                byteBuffer.position(infoStart);
                int length = infoEnd - infoStart;
                byte[] bytes = new byte[length];
                byteBuffer.get(bytes, 0, length);

                return PhoneNumberInfoConvert.convertToPhoneNumberInfo(phoneNo, bytes, ispMark);
            }
        }
        return Optional.empty();
    }
}
