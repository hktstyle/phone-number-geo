package personal.hktstyle.phone.algorithm;

import personal.hktstyle.phone.convert.PhoneNumberInfoConvert;
import personal.hktstyle.phone.model.PhoneNumberInfo;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Optional;

/**
 * 二分查找算子
 *
 * Created by hky on 01/29/2023
 **/
public class BinarySearchAlgorithmImpl implements LookupAlgorithm {
    private ByteBuffer originalByteBuffer;
    private int indicesStartOffset;
    private int indicesEndOffset;

    @Override
    public void loadData(byte[] data) {
        originalByteBuffer = ByteBuffer.wrap(data)
                .asReadOnlyBuffer()
                .order(ByteOrder.LITTLE_ENDIAN);
        //noinspection unused
        int dataVersion = originalByteBuffer.getInt(); // dataVersion not valid, don't know why
        indicesStartOffset = originalByteBuffer.getInt(4);
        indicesEndOffset = originalByteBuffer.capacity();
    }

    /**
     * 对齐
     */
    private int alignPosition(int pos) {
        int remain = (pos - indicesStartOffset) % 9;
        if (pos - indicesStartOffset < 9) {
            return pos - remain;
        } else if (remain != 0) {
            return pos + 9 - remain;
        } else {
            return pos;
        }
    }

    private boolean isInvalidPhoneNumber(String phoneNumber) {
        if (phoneNumber == null) {
            return true;
        }
        int phoneNumberLength = phoneNumber.length();
        if (phoneNumberLength < 7 || phoneNumberLength > 11) {
            return true;
        }
        return false;
    }

    @Override
    public Optional<PhoneNumberInfo> lookup(String phoneNumber) {
        ByteBuffer byteBuffer = originalByteBuffer.asReadOnlyBuffer().order(ByteOrder.LITTLE_ENDIAN);
        if (isInvalidPhoneNumber(phoneNumber)) {
            return Optional.empty();
        }
        int attributionIdentity;
        try {
            attributionIdentity = Integer.parseInt(phoneNumber.substring(0, 7));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
        int left = indicesStartOffset;
        int right = indicesEndOffset;
        int mid = (left + right) / 2;
        mid = alignPosition(mid);
        while (mid >= left && mid <= right) {
            if (mid == right) {
                return Optional.empty();
            }
            int compare = compare(mid, attributionIdentity, byteBuffer);
            if (compare == 0) {
                return extract(phoneNumber, mid, byteBuffer);
            } else if (mid == left) {
                return Optional.empty();
            } else if (compare > 0) {
                int tempMid = (mid + left) / 2;
                right = mid;
                mid = alignPosition(tempMid);
            } else {
                int tempMid = (mid + right) / 2;
                left = mid;
                mid = alignPosition(tempMid);
            }
        }
        return Optional.empty();
    }

    private Optional<PhoneNumberInfo> extract(String phoneNumber, int indexStart, ByteBuffer byteBuffer) {
        byteBuffer.position(indexStart);
        //noinspection unused
        int prefix = byteBuffer.getInt(); // it is necessary
        int infoStartIndex = byteBuffer.getInt();
        byte ispMark = byteBuffer.get();

        byte[] bytes = new byte[determineInfoLength(infoStartIndex, byteBuffer)];
        byteBuffer.get(bytes);

        return PhoneNumberInfoConvert.convertToPhoneNumberInfo(phoneNumber, bytes, ispMark);
    }

    private int determineInfoLength(int infoStartIndex, ByteBuffer byteBuffer) {
        byteBuffer.position(infoStartIndex);
        //noinspection StatementWithEmptyBody
        while ((byteBuffer.get()) != 0) {
            // just to find index of next '\0'
        }
        int infoEnd = byteBuffer.position() - 1;
        byteBuffer.position(infoStartIndex); //reset to info start index
        return infoEnd - infoStartIndex;
    }

    private int compare(int position, int key, ByteBuffer byteBuffer) {
        byteBuffer.position(position);
        int phonePrefix = byteBuffer.getInt();
        return Integer.compare(phonePrefix, key);
    }
}
