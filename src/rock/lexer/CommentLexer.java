package rock.lexer;

import rock.token.CommentToken;
import rock.token.Token;

import java.util.ArrayList;
import java.util.List;

public class CommentLexer implements ILexer {

    enum Sign {
        //HASH("#", "\n", true),
        DOUBLE_SLASH("//", "\n", true),
        STAR("/*", "*/", false);

        String start;
        String end;
        boolean singleLine;

        Sign(String start, String end, boolean singleLine) {
            this.start = start;
            this.end = end;
            this.singleLine = singleLine;
        }
    }

    @Override
    public Token read(int lineNumber, String s, int start) {
        for (Sign sign : Sign.values()) {
            int contentStart = start + sign.start.length();
            if (contentStart > s.length() || !s.regionMatches(false, start, sign.start, 0, sign.start.length())) {
                continue;
            }
            int index = contentStart;
            int contentEnd = index;
            while (index < s.length()) {
                if (sign.singleLine && s.charAt(index) == '\n') {
                    contentEnd = index;
                    break;
                }
                contentEnd = index;
                int end = index + sign.end.length();
                if (end <= s.length() && s.regionMatches(false, contentEnd, sign.end, 0, sign.end.length())) {
                    index = end;
                    break;
                }
                index++;
            }
            if (sign.singleLine) {
                contentEnd = index;
            }
            return new CommentToken(lineNumber, start, s.substring(start, index), s.substring(contentStart, contentEnd));
        }
        return null;
    }

    public static void main(String[] args) {
        List<String> data = new ArrayList<>();
        data.add("// Hello world   ");
        data.add("/* this is stars */   ");
        data.add("# single line  ");
        data.add("#");
        data.add("//");
        data.add("////");
        data.add("#//");
        data.add("/**/");
        data.add("/* line 1 \n line2 */");
        data.add("!   ");
        data.add("== ");

        CommentLexer lexer = new CommentLexer();

        data.forEach(s -> System.out.println((s + "        " + lexer.read(0, s, 0)).replaceAll("\n", "#{EOL}")));
    }
}
