package rock.lexer;

import rock.token.StrToken;
import rock.token.Token;
import rock.util.BooleanRef;

import java.util.ArrayList;
import java.util.List;

public class StringLexer implements ILexer {
    @Override
    public Token lex(int lineNumber, String s, int start) {
        int index = start;
        if (s.charAt(index) != '\"') {
            return null;
        }
        index++;
        StringBuffer sb = new StringBuffer();
        BooleanRef escape = new BooleanRef(false);
        while (index < s.length() && read(s.charAt(index), sb, escape)) {
            index++;
        }
        if (index >= s.length() || s.charAt(index) != '\"') {
            return null;
        }
        index++;
        return new StrToken(lineNumber, start, s.substring(start, index), sb.toString());
    }

    private boolean read(char ch, StringBuffer sb, BooleanRef escape) {
        if (escape.get()) {
            sb.append(ch);
            escape.set(false);
            return true;
        } else if (ch == '\\') {
            escape.set(true);
            return true;
        } else if (ch == '\"') {
            return false;
        } else {
            sb.append(ch);
            return true;
        }
    }

    public static void main(String[] args) {
        List<String> data = new ArrayList<>();
        data.add("\"Hello My World!\"   ");
        data.add("\"Hello My World!   ");
        data.add("Hello My World!   ");
        data.add("\"\"   ");
        data.add("\"Hello\\\\ World\"   ");
        data.add("\"Hello\\\" World\"   ");
        StringLexer lexer = new StringLexer();

        data.forEach(s -> System.out.println(s + "        " + lexer.lex(0, s, 0)));
    }

}
