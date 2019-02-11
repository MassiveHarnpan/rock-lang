package rock.lexer;

import rock.token.IdToken;
import rock.token.Token;
import rock.util.LineReader;
import rock.util.Pos;
import rock.util.TokenizerTester;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class IdfTokenizer extends Tokenizer {

    private static String[] WORDS = new String[]{
            "class", "def", "var", "if", "else", "while", "return", "function", "import"
    };

    private static String[] KEYS = new String[]{
            "!=", "-=", "*=", "/=", "^=",
            "==", "!=", ">=", "<=", ">", "<",
            "=", "->", ";", "#", ".", ",", ":", "?",
            "&&", "||", "&", "|", "!",
            "+", "-", "*", "/", "^",
            "{", "}", "(", ")", "[", "]",
    };

    @Override
    public Token read(LineReader reader, Pos start) {
        for (String word : WORDS) {
            //System.out.println(word);
            if (reader.read(word) && !NameTokenizer.isBody(reader.peek())) {
                return new IdToken(start, word);
            }
        }
        for (String key : KEYS) {
            //System.out.println(key);
            if (reader.read(key)) {
                return new IdToken(start, key);
            }
        }
        return null;
    }

    public static void main(String[] args) {
        List<String> data = new ArrayList<>();
        data.add("class name   ");
        data.add("className   ");
        data.add("if not   ");
        data.add("ifNotNull(obj)   ");
        data.add("+=   ");
        data.add("!   ");
        data.add("== ");
        data.add("=");
        data.add("var");
        data.add("[]");
        data.add("^ ");
        data.add("@ ");
        data.add("48 ");
        data.add(")");
        data.add("() ");

        IdfTokenizer lexer = new IdfTokenizer();



        TokenizerTester.test(data, lexer);
    }
}
