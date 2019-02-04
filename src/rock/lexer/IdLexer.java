package rock.lexer;

import rock.ast.Array;
import rock.token.IdToken;
import rock.token.Token;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IdLexer implements ILexer {

    private static String[] WORDS = new String[]{
            "class", "def", "var", "if", "while", "return", "function"
    };

    private static String[] KEYS = new String[]{
            "==", "!=", ">=", "<=", ">", "<",
            "..",
            "=", "->", ";", "#", ".", ",", ":", "?",
            "&", "|", "!",
            "+", "-", "*", "/", "^",
            "{", "}", "(", ")", "[", "]",
    };

    @Override
    public Token lex(int lineNumber, String s, int start) {
        for (String word : WORDS) {
            //System.out.println(word);
            if (checkWord(word, s, start)) {
                return new IdToken(lineNumber, start, word);
            }
        }
        for (String key : KEYS) {
            //System.out.println(key);
            if (checkKey(key, s, start)) {
                return new IdToken(lineNumber, start, key);
            }
        }
        return null;
    }

    private boolean checkWord(String word, String s, int start) {
        int index = start + word.length();
        if (index < s.length() && NameLexer.isBody(s.charAt(index))) {
            return false;
        }
        return index <= s.length() && s.regionMatches(false, start, word, 0, word.length());
    }

    private boolean checkKey(String key, String s, int start) {
        int index = start + key.length();
        return index <= s.length() && s.regionMatches(false, start, key, 0, key.length());
    }

    public static void main(String[] args) {
        List<String> data = new ArrayList<>();
        //data.add("class name   ");
        //data.add("className   ");
        //data.add("if not   ");
        //data.add("ifNotNull(obj)   ");
        //data.add("+=   ");
        //data.add("!   ");
        //data.add("== ");
        data.add("=");
        data.add("var");
        data.add("[]");
        data.add("^ ");
        data.add("@ ");
        data.add("48 ");
        data.add(")");
        data.add(") ");

        IdLexer lexer = new IdLexer();

        data.forEach(s -> System.out.println(s + "        " + lexer.lex(0, s, 0)));
    }
}
