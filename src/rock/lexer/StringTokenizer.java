package rock.lexer;

import rock.token.StrToken;
import rock.token.Token;
import rock.util.LineReader;
import rock.util.Pos;
import rock.util.TokenizerTester;

import java.util.ArrayList;
import java.util.List;

import static rock.util.NumberTokenizerUtil.inRangeOf;
import static rock.util.NumberTokenizerUtil.isDigit;
import static rock.util.NumberTokenizerUtil.readInt;

public class StringTokenizer extends Tokenizer {


    @Override
    public Token read(LineReader reader, Pos start) {
        if (reader.read() != '\"') {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        if (!read(reader, builder)) {
            return null;
        }
        return new StrToken(start, reader.substring(start), builder.toString());
    }

    public static boolean isContent(char ch) {
        return ch != '\"';
    }

    public static boolean read(LineReader reader, StringBuilder builder) {
        while (reader.hasMore()) {
            char ch = reader.read();
            if (ch == '\"') {
                return true;
            }
            if (ch != '\\') {
                builder.append(ch);
                continue;
            }
            if (!reader.hasMore()) {
                return false;
            }
            int e = readEscape(reader);
            if (e < 0) {
                return false;
            }
            builder.append((char) e);
        }
        return false;
    }

    public static int readEscape(LineReader reader) {
        if (inRangeOf(8, reader.peek())) {
            return readInt(reader, 8, 3);
        }
        char ch = reader.read();
        if (ch == 'u' && reader.hasMore() && inRangeOf(16, reader.peek())) {
            return readInt(reader, 16, 4);
        }
        switch (ch) {
            case '\'': return '\'';
            case '\"': return '\"';
            case '\\': return '\\';
            case 'n': return '\n';
            case 'r': return '\r';
            case 't': return '\t';
            case 'f': return '\f';
        }
        return ch;
    }

    public static void main(String[] args) {
        System.out.println("\\u35FE == " + "\u35FE");
        System.out.println("\\uCAFE == " + "\uCAFE");
        System.out.println("\\uBABE == " + "\uBABE");
        System.out.println("\\uCAFEBABE == " + "\uCAFEBABE");
        List<String> data = new ArrayList<>();
        data.add("\"Hello My World!\"   ");
        data.add("\"Hello My \n World!\"   ");
        data.add("\"Hello My World!   ");
        data.add("Hello My World!   ");
        data.add("\"\"   ");
        data.add("\"Hello\\\\ World\"   ");
        data.add("\"###  \\u35FE  ***\\\" World\"   ");
        data.add("\"###  \\uCAFE \\uBABE  ***\\\" World\"   ");
        data.add("\"###  \\uCAFEBABE  ***\\\" World\"   ");
        data.add("\"###  \\u4F60\\u597D  ***\\\" World\"   ");
        data.add("\"###  \\u  ***\\\" World\"   ");
        data.add("\"###  \\78  ***\\\" World\"   ");
        data.add("\"###  \\0  ***\\\" World\"   ");
        data.add("\"###  \\8  ***\\\" World\"   ");
        data.add("\"###  \\377  ***\\\" World\"   ");
        data.add("\"###  \\142  ***\\\" World\"   ");
        data.add("\"###  \\1427  ***\\\" World\"   ");
        StringTokenizer lexer = new StringTokenizer();

        TokenizerTester.test(data, lexer);
    }

}
