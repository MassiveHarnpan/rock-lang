package rock.lexer;

import rock.token.DecToken;
import rock.token.Token;
import rock.util.LineReader;
import rock.util.Pos;
import rock.util.TokenizerTester;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import static rock.util.NumberTokenizerUtil.*;

// 允许的形式:：
// 10.56
// 15.9e9
// 15.9e-8
// .65
// .65e7
// .65e23
// 不允许的形式：
// .
// 18.
// 48.9e
// 15.e
// 55.e18
// 66.e-9
// 56.8e-12.6
// e89
public class DecimalTokenizer extends Tokenizer {

    @Override
    public Token read(LineReader reader, Pos start) {

        int iPart = 0; // 整数部分
        double dPart = 0.0; // 小数部分
        boolean positiveE = true;
        int ePart = 0; // 指数部分
        double value = 0;

        if (isDigit(reader.peek())) {
            iPart = readInt(reader, 10);
        }
        value = iPart;

        if (!reader.hasMore() || reader.read() != '.') {
            return null;
        }

        if (!reader.hasMore() || !isDigit(reader.peek())) {
            return null;
        }

        dPart = readDec(reader, 10);
        value += dPart;

        Pos beforeE = reader.checkPoint();

        if (!reader.read('e', 'E')) {
            return new DecToken(start, reader.substring(start), value);
        }
        if (!reader.hasMore()) {
            reader.reset(beforeE);
            return new DecToken(start, reader.substring(start), value);
        }

        if (reader.read('-')) {
            positiveE = false;
        } else if (reader.read('+')) {
            positiveE = true;
        }

        if (!reader.hasMore() || !isDigit(reader.peek())) {
            reader.reset(beforeE);
            return new DecToken(start, reader.substring(start), value);
        }

        ePart = readInt(reader, 10);
        ePart = positiveE ? ePart : -ePart;
        value *= Math.pow(10, ePart);

        return new DecToken(start, reader.substring(start), value);
    }

    public static void main(String[] args) {
        List<String> data = new ArrayList<>();
        data.add("19.562  ");
        data.add("64.   ");
        data.add("64.0   ");
        data.add("556.5888988  ");
        data.add("556.5888988e9  ");
        data.add("67.23e1  ");
        data.add("67.23e2  ");
        data.add("67.23e0  ");
        data.add("556.192e-9  ");
        data.add("5.192E-9  ");
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
        DecimalTokenizer lexer = new DecimalTokenizer();




        TokenizerTester.test(data, lexer);
    }
}
