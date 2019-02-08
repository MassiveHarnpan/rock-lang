package rock.util;

public class NumberTokenizerUtil {

    public static double readDec(LineReader reader, int radix) {
        long r = radix;
        double decimal = 0.0;
        while (reader.hasMore() && inRangeOf(radix, reader.peek())) {
            decimal += toInt(reader.read()) / (double) r;
            r *= radix;
        }
        return decimal;
    }

    public static int readInt(LineReader reader, int radix) {
        int integer = 0;
        while (reader.hasMore() && inRangeOf(radix, reader.peek())) {
            integer = integer * radix + toInt(reader.read());
        }
        return integer;
    }

    public static int readInt(LineReader reader, int radix, int limit) {
        int counter = 0;
        int integer = 0;
        while (reader.hasMore() && counter++ < limit && inRangeOf(radix, reader.peek())) {
            integer = integer * radix + toInt(reader.read());
        }
        return integer;
    }

    public static boolean isDigit(char ch) {
        return ch >= '0' && ch <= '9';
    }

    public static int toInt(char ch) {
        if (ch >= '0' && ch <= '9') {
            return ch - '0';
        } else if (ch >= 'a' && ch <= 'z'){
            return 10 + ch - 'a';
        } else if (ch >= 'A' && ch <= 'Z'){
            return 10 + ch - 'A';
        }
        return -1;
    }

    public static boolean inRangeOf(int radix, char ch) {
        if (radix <= 10) {
            return ch >= '0' && ch < ('0' + radix);
        } else {
            return (ch >= '0' && ch <= '9')
                    || (ch >= 'a' && ch < ('a' + radix - 10))
                    || (ch >= 'A' && ch < ('A' + radix - 10));
        }
    }

}
