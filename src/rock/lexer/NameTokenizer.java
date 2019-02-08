package rock.lexer;

import rock.token.NameToken;
import rock.token.Token;
import rock.util.LineReader;
import rock.util.Pos;
import rock.util.TokenizerTester;

import java.util.ArrayList;
import java.util.List;

public class NameTokenizer extends Tokenizer {

    @Override
    public Token read(LineReader reader, Pos start) {
        if (!isHead(reader.read())) {
            return null;
        }
        while (reader.hasMore() && isBody(reader.peek())) {
            reader.read();
        }
        return new NameToken(start, reader.substring(start));
    }

    public static boolean isHead(char ch) {
        return ch == '_'
                || ch == '$'
                || (ch >= 'a' && ch <= 'z')
                || (ch >= 'A' && ch <= 'Z');
    }

    public static boolean isBody(char ch) {
        return ch == '_'
                || ch == '$'
                || (ch >= 'a' && ch <= 'z')
                || (ch >= 'A' && ch <= 'Z')
                || (ch >= '0' && ch <= '9');
    }

    public static void main(String[] args) {
        List<String> data = new ArrayList<>();
        data.add("aaa   ");
        data.add("b_aa34   ");
        data.add("_89a   ");
        data.add("$18   ");
        data.add("==   ");
        data.add("12a8   ");
        data.add("as_1 ");
        data.add("is_eq   ");
        data.add("Greeter ");
        data.add("className ");

        NameTokenizer lexer = new NameTokenizer();

        TokenizerTester.test(data, lexer);
    }

}
