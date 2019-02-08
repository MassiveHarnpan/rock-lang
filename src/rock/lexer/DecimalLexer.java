package rock.lexer;

import rock.token.DecToken;
import rock.token.Token;

import java.util.ArrayList;
import java.util.List;

public class DecimalLexer implements ILexer {
    @Override
    public Token read(int lineNumber, String s, int start) {
        int index = start;

        int radix = 10;
        int ipart = 0;
        double dpart = 0.0;

        char ch;

        while (index < s.length() && isDigit(ch = s.charAt(index))) {
            ipart = (ipart * radix) + toInt(ch);
            index++;
        }

        if (index >= s.length() || s.charAt(index) != '.') {
            return null;
        }
        index++;

        if (index >= s.length() || !isDigit(ch = s.charAt(index))) {
            return null;
        }
        double rr = 1.0 / radix;
        dpart = toInt(ch) * rr;
        rr /= radix;
        index++;

        while (index < s.length() && isDigit(ch = s.charAt(index))) {
            dpart += toInt(ch) * rr;
            rr /= radix;
            index++;
        }
        //System.out.println("ipart = "+ipart+", dpart = " + dpart);
        return new DecToken(lineNumber, start, s.substring(start, index), ipart + dpart);
    }

    public static boolean isDigit(char ch) {
        return ch >= '0' && ch <= '9';
    }

    public static int toInt(char ch) {
        return ch - '0';
    }

    public static void main(String[] args) {
        List<String> data = new ArrayList<>();
        data.add("19.562  ");
        data.add("64.   ");
        data.add("64.0   ");
        data.add("556.5888988  ");
        data.add("556.7  ");
        data.add(".256.write()  ");
        data.add("256.write()  ");
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
        DecimalLexer lexer = new DecimalLexer();

        data.forEach(s -> System.out.println(s + "        " + lexer.read(0, s, 0)));
    }
}
