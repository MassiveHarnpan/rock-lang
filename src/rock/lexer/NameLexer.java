package rock.lexer;

import rock.token.NameToken;
import rock.token.Token;

import java.util.ArrayList;
import java.util.List;

public class NameLexer implements ILexer {
    @Override
    public Token lex(int lineNumber, String s, int start) {
        int index = start;
        if (!isHead(s.charAt(index))) {
            return null;
        }
        index++;
        while (index < s.length() && isBody(s.charAt(index))) {
            index++;
        }
        String literal = s.substring(start, index);
        return new NameToken(lineNumber, start, literal);
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

        NameLexer lexer = new NameLexer();

        data.forEach(s -> System.out.println(s + "        " + lexer.lex(0, s, 0)));
    }

}
