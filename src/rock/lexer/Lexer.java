package rock.lexer;

import rock.token.Token;
import rock.token.TokenType;

import java.io.*;
import java.util.*;

public class Lexer {

    private LineNumberReader reader;

    public Lexer(Reader reader) {
        if (reader instanceof LineNumberReader) {
            this.reader = (LineNumberReader) reader;
        } else {
            this.reader = new LineNumberReader(reader);
        }
        registerLexer(new CommentLexer());
        registerLexer(new StringLexer());
        registerLexer(new DecimalLexer());
        registerLexer(new IntegerLexer());
        registerLexer(new IdLexer());
        registerLexer(new NameLexer());
    }


    private List<String> lines = new ArrayList<>();
    private String line;
    private int lineNumber = 0;
    private int lineOffset = 0;

    public int getLineNumber() {
        return lineNumber;
    }

    public int getLineOffset() {
        return lineOffset;
    }

    public String literalFrom(int startLineNumber, int startLineOffset) {
        return null;
    }
    public boolean hasNext() {return false;}
    public char next() {return 0;}
    public boolean read(String s) {return false;}
    public int readToLineEnd(String s) {return 0;}
    public String match(String... options) {return null;}








    private List<Token> parsedTokens = new ArrayList<>();

    private boolean readLine() {
        try {
            String line = reader.readLine();
            if (line == null) {
                return false;
            }
            int index = 0;
            int prev = 0;
            while (prev != (index = nextToken(reader.getLineNumber(), line, index))) {
                prev = index;
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private int skipSpace(String s, int start) {
        int index = start;
        while (index < s.length() && Character.isSpaceChar(s.charAt(index))) {
            index++;
        }
        return index;
    }

    private List<ILexer> lexers = new LinkedList<>();

    public void registerLexer(ILexer lexer) {
        lexers.add(lexer);
    }

    private int nextToken(int lineNumber, String line, int start) {
        int index = skipSpace(line, start);
        if (index >= line.length()) {
            return start;
        }

        Token token = null;
        for (ILexer lexer : lexers) {
            token = lexer.read(lineNumber, line, index);
            if (token != null) {
                if (token.type() != TokenType.COMMENT) {
                    parsedTokens.add(token);
                }
                //System.out.println(token);
                return index + token.literal().length();
            }
        }


        return start;
    }

    private int pointer = 0;

    public int pointer() {
        return pointer;
    }

    public int recovery(int pointer) {
        return this.pointer = pointer;
    }

    public Token read() {
        if (pointer < parsedTokens.size()) {
            return parsedTokens.get(pointer++);
        } else {
            if (parseUntil(pointer)) {
                return parsedTokens.get(pointer++);
            } else {
                return Token.EOF;
            }
        }
    }

    public Token peek(int offset) {
        int index = pointer + offset;
        if (index < parsedTokens.size()) {
            return parsedTokens.get(index);
        } else {
            if (parseUntil(index)) {
                return parsedTokens.get(index);
            } else {
                return Token.EOF;
            }
        }
    }

    private boolean parseUntil(int index) {
        while (index >= parsedTokens.size() && readLine());
        return index < parsedTokens.size();
    }


    public static void main(String[] args) {


        try (InputStreamReader reader = new InputStreamReader(new FileInputStream(new File("test/Sample.roc")), "UTF-8")) {
            Lexer lexer = new Lexer(reader);
            Token token;
            do {
                token = lexer.read();
                System.out.println(token.literal());
            } while (token != Token.EOF);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
