package personal.hktstyle.phone.algorithm;


import personal.hktstyle.phone.convert.PhoneNumberInfoConvert;
import personal.hktstyle.phone.model.PhoneNumberInfo;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.Optional;

/**
 * Created by hky on Jan 29, 2023
 **/
public class AnotherBinarySearchAlgorithmImpl implements LookupAlgorithm {
    private ByteBuffer originalByteBuffer;
    private int indicesStartOffset;
    private int indicesEndOffset;

    @Override
    public void loadData(byte[] data) {
        originalByteBuffer = ByteBuffer.wrap(data).asReadOnlyBuffer();
        originalByteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        //noinspection unused
        int dataVersion = originalByteBuffer.getInt();
        indicesStartOffset = originalByteBuffer.getInt(4);
        indicesEndOffset = originalByteBuffer.limit();
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
                break;
            }
            if (mid == left) {
                return Optional.empty();
            }

            if (compare > 0) {
                int tempMid = (mid + left) / 2;
                tempMid = alignPosition(tempMid);
                right = mid;
                int remain = (tempMid - indicesStartOffset) % 9;
                if (tempMid - indicesStartOffset < 9) {
                    mid = tempMid - remain;
                    continue;
                }
                if (remain != 0) {
                    mid = tempMid + 9 - remain;
                } else {
                    mid = tempMid;
                }
            } else {
                int tempMid = (mid + right) / 2;
                tempMid = alignPosition(tempMid);
                left = mid;
                int remain = (tempMid - indicesStartOffset) % 9;
                if (tempMid - indicesStartOffset < 9) {
                    mid = tempMid - remain;
                    continue;
                }
                if (remain != 0) {
                    mid = tempMid + 9 - remain;
                } else {
                    mid = tempMid;
                }
            }
        }

        byteBuffer.position(mid);
        //noinspection unused
        int prefix = byteBuffer.getInt();
        int infoStartIndex = byteBuffer.getInt();
        byte ispMark = byteBuffer.get();
        byteBuffer.position(infoStartIndex);
        int resultBufferSize = 200;
        int increase = 100;
        byte[] bytes = new byte[resultBufferSize];
        byte b;
        int i;
        for (i = 0; (b = byteBuffer.get()) != 0; i++) {
            bytes[i] = b;
            if (i == resultBufferSize - 1) {
                resultBufferSize = resultBufferSize + increase;
                bytes = Arrays.copyOf(bytes, resultBufferSize);
            }
        }

        return PhoneNumberInfoConvert.convertToPhoneNumberInfo(phoneNo, bytes, ispMark);
    }

    private int compare(int position, int key, ByteBuffer byteBuffer) {
        byteBuffer.position(position);
        int phonePrefix;
        try {
            phonePrefix = byteBuffer.getInt();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Integer.compare(phonePrefix, key);
    }
}
