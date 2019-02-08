package rock.lexer;

import rock.token.CommentToken;
import rock.token.Token;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class CommentLexer extends Tokenizer {

    enum Sign {
        DOUBLE_SLASH("//", "\n"),
        STAR("/*", "*/");

        String start;
        String end;

        Sign(String start, String end) {
            this.start = start;
            this.end = end;
        }
    }

    @Override
    public Token read(LineReader reader) {
        for (Sign sign : Sign.values()) {
            if (!reader.read(sign.start)) {
                continue;
            }

            int contentStartLineNumber = reader.getLineNumber();
            int contentStartLineOffset = reader.getLineOffset();

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

        data.forEach(s -> System.out.println((s + "        " + lexer.read(new LineReader(new StringReader(s)))).replaceAll("\n", "#{EOL}")));
    }
}
