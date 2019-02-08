package rock.lexer;

import rock.token.CommentToken;
import rock.token.Token;
import rock.util.LineReader;
import rock.util.Pos;
import rock.util.TokenizerTester;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class CommentTokenizer extends Tokenizer {

    enum Sign {
        DOUBLE_SLASH("//", "\n"),
        STAR("/*", "*/");

        String start;
        String end;

        Sign(String start, String end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public String toString() {
            return start + " ... " + end;
        }
    }

    @Override
    public Token read(LineReader reader, Pos start) {
        for (Sign sign : Sign.values()) {
            //System.out.println(sign);
            if (!reader.read(sign.start)) {
                continue;
            }
            Pos contentStart = reader.checkPoint();
            while (reader.hasMore()) {
                Pos contentEnd = reader.checkPoint();
                if (reader.read(sign.end)) {
                    return new CommentToken(start, reader.substring(start), reader.substring(contentStart, contentEnd));
                }
                reader.read();
            }
            reader.reset(start);
        }
        return null;
    }

    public static void main(String[] args) {
        List<String> data = new ArrayList<>();
        data.add("// Hello world   ");
        data.add("/* this is stars */   ");
        data.add("# // content  ");
        data.add("#");
        data.add("//");
        data.add("////");
        data.add("#//");
        data.add("/**/");
        data.add("/* line 1 \n line2 */");
        data.add("!   ");
        data.add("== ");

        CommentTokenizer lexer = new CommentTokenizer();

        TokenizerTester.test(data, lexer);
    }
}
