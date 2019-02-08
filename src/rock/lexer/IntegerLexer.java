package rock.lexer;

import rock.token.IntToken;
import rock.token.Token;

import java.util.ArrayList;
import java.util.List;

public class IntegerLexer implements ILexer {

    private static Character[] BIN_CHARS = new Character[]{'0', '1'};
    private static Character[] OTC_CHARS = new Character[]{'0', '1', '2', '3', '4', '5', '6', '7'};
    private static Character[] DEC_CHARS = new Character[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    private static Character[] HEX_CHARS = new Character[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static final int NON = -1;
    public static final int BIN = 2;
    public static final int OTC = 8;
    public static final int DEC = 10;
    public static final int HEX = 16;



    @Override
    public Token read(int lineNumber, String s, int start) {
        int index = start;

        int radix = DEC;
        int value = 0;

        char ch = s.charAt(index);
        if (ch == '0') {
            index++;
            if (index >= s.length()) {
                return new IntToken(lineNumber, start, s.substring(start, index), 0);
            }
            ch = s.charAt(index);
            if (ch == 'x') {
                radix = HEX;
                index++;
            } else if (ch == 'b'){
                radix = BIN;
                index++;
            } else if (Character.isDigit(ch)){
                radix = OTC;
            } else {
                return new IntToken(lineNumber, start, s.substring(start, index), 0);
            }
        }
        ch = s.charAt(index);
        if (!inRangeOf(radix, ch)) {
            return null;
        }
        value = toInt(ch);
        index++;

        while (index < s.length() && inRangeOf(radix, ch = s.charAt(index))) {
            value = value * radix + toInt(ch);
            index++;
        }

        return new IntToken(lineNumber, start, s.substring(start, index), value);
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

    public boolean inRangeOf(int radix, char ch) {
        if (radix <= 10) {
            return ch >= '0' && ch < ('0' + radix);
        } else {
            return (ch >= '0' && ch <= '9')
                    || (ch >= 'a' && ch < ('a' + radix - 10))
                    || (ch >= 'A' && ch < ('A' + radix - 10));
        }
    }

    public static void main(String[] args) {
        List<String> data = new ArrayList<>();
        data.add("19.562  ");
        data.add("64.   ");
        data.add(".256.write()  ");
        data.add("256.write()  ");
        data.add(".25a6  ");
        data.add(".   ");
        data.add("951   ");
        data.add("0   ");
        data.add("0xCAFE   ");
        data.add("0x6f7   ");
        data.add("0b10011   ");
        data.add("0364   ");
        data.add("00   ");
        data.add("1255.9_55   ");
        data.add("1_000_000_000   ");
        IntegerLexer lexer = new IntegerLexer();

        data.forEach(s -> System.out.println(s + "        " + lexer.read(0, s, 0)));
    }
}
