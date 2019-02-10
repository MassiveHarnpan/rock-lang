package rock.util;

public class ByteArrayUtil {




    public static int readInt(byte[] arr, int index) {
        return ((arr[index] & 0xFF) << 24)
                | ((arr[index + 1] & 0xFF) << 16)
                | ((arr[index + 2] & 0xFF) << 8)
                | (arr[index + 3] & 0xFF);
    }

    public static void writeInt(byte[] arr, int index, int value) {
        arr[index] = (byte) (value >>> 24);
        arr[index + 1] = (byte) (value >>> 16);
        arr[index + 2] = (byte) (value >>> 8);
        arr[index + 3] = (byte) value;
    }

    public static String bytesToString(byte[] bytes, int len) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < bytes.length && i < len; i++) {
            byte b = bytes[i];
            for (int j = 7; j >= 0; j--) {
                builder.append((((b >>> j) & 1) == 0) ? '0' : '1');
            }
            if (i < bytes.length - 1) {
                builder.append(' ');
            }
        }
        return builder.toString();
    }

    public static double readDouble(byte[] arr, int index) {
        long bits = 0L;
        for (int i = 0; i < Double.BYTES; i++) {
            bits |= ((long) (arr[index + i] & 0xFF)) << ((Double.BYTES - i - 1) * 8);
        }
        return Double.longBitsToDouble(bits);
    }


    public static void writeDouble(byte[] arr, int index, double value) {
        long bits = Double.doubleToLongBits(value);
        for (int i = 0; i < Double.BYTES; i++) {
            arr[index + i] = (byte) (bits >>> (((Double.BYTES - i - 1) * 8) & 0xFF));
        }
    }
}
