package rock.lexer;

import rock.token.NumToken;
import rock.token.Token;
import rock.util.BooleanRef;

import java.util.ArrayList;
import java.util.List;

public class NumberLexer implements ILexer {

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
    public Token lex(int lineNumber, String s, int start) {
        int index = start;
        BooleanRef postDot = new BooleanRef(false);
        int radix = DEC;
        StringBuffer sb = new StringBuffer();

        char first = s.charAt(start);
        if (first == '.') {
            radix = DEC;
            postDot.set(true);
            index++;
            sb.append(first);
        } else if (first == '0') {
            index++;
            if (index >= s.length()) {
                radix = OTC;
            } else {
                char second = s.charAt(start + 1);
                switch (second) {
                    case 'x':
                        radix = HEX;
                        index++;
                        break;
                    case 'b':
                        radix = BIN;
                        index++;
                        break;
                    default:
                        radix = OTC;
                }
            }
        } else if (inRangeOf(DEC, first)) {
            radix = DEC;
        } else {
            return null;
        }

        while (index < s.length() && read(s.charAt(index), radix, postDot, sb)) {
            if (postDot.get() && radix != DEC) {
                break;
            }
            index++;
        }

        String l = sb.toString();
        if (".".equals(l)) {
            return null;
        }
        Number num;
        if (l.length() == 0) {
            num = 0;
        } else {
            num = postDot.get() ? Double.valueOf(l) : Integer.valueOf(l, radix);
        }
        return new NumToken(lineNumber, start, s.substring(start, index), num);
    }

    private boolean read(char ch, int radix, BooleanRef postDot, StringBuffer sb) {
        if (ch == '.') {
            if (postDot.get()) {
                return false;
            } else {
                postDot.set(true);
                sb.append(ch);
                return true;
            }
        } else if (ch =='_') {
            return true;
        }
        boolean r = inRangeOf(radix, ch);
        if (r) {
            sb.append(ch);
        }
        return r;
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
        data.add(".256.toString()  ");
        data.add("256.toString()  ");
        data.add(".25a6  ");
        data.add(".   ");
        data.add("951   ");
        data.add("0   ");
        data.add("0x6f7   ");
        data.add("0b10011   ");
        data.add("0364   ");
        data.add("00   ");
        data.add("1255.9_55   ");
        data.add("1_000_000_000   ");
        NumberLexer lexer = new NumberLexer();

        data.forEach(s -> System.out.println(s + "        " + lexer.lex(0, s, 0)));
    }
}
