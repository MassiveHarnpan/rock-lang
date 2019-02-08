package rock.lexer;

import rock.token.IntToken;
import rock.token.Token;
import rock.util.LineReader;
import rock.util.Pos;
import rock.util.TokenizerTester;

import java.util.ArrayList;
import java.util.List;

import static rock.util.NumberTokenizerUtil.*;

public class IntegerTokenizer extends Tokenizer {

    private static Character[] BIN_CHARS = new Character[]{'0', '1'};
    private static Character[] OTC_CHARS = new Character[]{'0', '1', '2', '3', '4', '5', '6', '7'};
    private static Character[] DEC_CHARS = new Character[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    private static Character[] HEX_CHARS = new Character[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static final int NON = -1;
    public static final int BIN = 2;
    public static final int OTC = 8;
    public static final int DEC = 10;
    public static final int HEX = 16;

    public static final String BIN_HEAD = "0b";
    public static final String OTC_HEAD = "0";
    public static final String HEX_HEAD = "0x";


    @Override
    public Token read(LineReader reader, Pos start) {

        if (!isDigit(reader.peek())) {
            return null;
        }

        int radix = DEC;
        int value = 0;

        if (reader.read(BIN_HEAD)) {
            radix = BIN;
        } else if (reader.read(HEX_HEAD)) {
            radix = HEX;
        } else if (reader.peek() == '0') {
            radix = OTC;
        } else {
            radix = DEC;
        }

        if (!inRangeOf(radix, reader.peek())) {
            return null;
        }

        value = readInt(reader, radix);
        return new IntToken(start, reader.substring(start), value);
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
        data.add("0xAbCD   ");
        data.add("0x6f7   ");
        data.add("0b10011   ");
        data.add("0364   ");
        data.add("00   ");
        data.add("1255.9_55   ");
        data.add("1_000_000_000   ");
        IntegerTokenizer lexer = new IntegerTokenizer();



        TokenizerTester.test(data, lexer);
    }
}
