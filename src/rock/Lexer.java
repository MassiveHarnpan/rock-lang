package rock;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {

    public static final String regexPat = "\\s*((//.*)|([0-9]+)|(\"(\\\\\"|\\\\\\|\\\\n|[^\"])*\")|[A-Z_a-z][A-Z_a-z0-9]*|==|<=|>=|&&|\\|\\||\\p{Punct})?";
    private Pattern pattern = Pattern.compile(regexPat);
    private ArrayList<Token> queue = new ArrayList<>();
    private boolean hasMore;
    private LineNumberReader reader;

    public Lexer(Reader reader) {
        hasMore = true;
        this.reader = new LineNumberReader(reader);
    }


    public Token read() throws RockException {
        if (fillQueue(1)) {
            return queue.remove(0);
        } else {
            return Token.EOF;
        }
    }

    public Token peek(int i) throws RockException {
        if (fillQueue(i)) {
            return queue.get(i);
        } else {
            return Token.EOF;
        }
    }


    private boolean fillQueue(int i) throws RockException {
        while (i > queue.size()) {
            if (hasMore) {
                readLine();
            } else {
                return false;
            }
        }
        return true;
    }

    protected void readLine() throws RockException {
        String line;
        try {
            line = reader.readLine();
        } catch (IOException e) {
            throw new RockException(e);
        }
        if (line == null) {
            hasMore = false;
            return;
        }
        int lineNumber = reader.getLineNumber();
        Matcher matcher = pattern.matcher(line);
        matcher.useTransparentBounds(true).useAnchoringBounds(false);
        int pos = 0;
        int endPos = line.length();
        while (pos < endPos) {
            matcher.region(pos, endPos);
            if (matcher.lookingAt()) {
                addToken(lineNumber, matcher);
                pos = matcher.end();
            } else {
                throw new RockException("Bad token at line:" + lineNumber);
            }
        }
        queue.add(new IdToken(lineNumber, Token.EOL));
    }

    protected void addToken(int lineNumber, Matcher matcher) {
        String m = matcher.group(1);
        // if space or comment
        if (m == null || matcher.group(2) != null) {
            return;
        }
        Token token;
        if (matcher.group(3) != null) {
            token = new NumToken(lineNumber, m, Integer.valueOf(m));
        } else if (matcher.group(4) != null) {
            token = new StrToken(lineNumber, m, toStringLiteral(m));
        } else {
            token = new IdToken(lineNumber, m, m);
        }
        queue.add(token);
    }

    private String toStringLiteral(String m) {
        StringBuffer sb = new StringBuffer();
        int len = m.length() - 1;
        for (int i = 1; i < len; i++) {
            char c = m.charAt(i);
            if (c == '\\' && i + 1 < len) {
                int ch = m.charAt(i + 1);
                switch (ch) {
                    case '"': c = '"'; break;
                    case 'n': c = '\n'; break;
                    case '\\': c = '\\'; break;
                }
            }
            sb.append(c);
        }
        return sb.toString();
    }
}
