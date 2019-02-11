package rock.lexer;

import rock.token.Token;
import rock.token.TokenType;
import rock.util.LineReader;

import java.io.*;
import java.util.*;

public class Lexer {

    private LineReader reader;

    public Lexer(LineReader reader) {
        this.reader = reader;

        registerLexer(new CommentTokenizer());
        registerLexer(new DecimalTokenizer());
        registerLexer(new IntegerTokenizer());
        registerLexer(new IdfTokenizer());
        registerLexer(new NameTokenizer());
        registerLexer(new StringTokenizer());
    }

    public Lexer concat(Reader reader) throws IOException {
        this.reader.concat(reader);
        this.reader.load();
        return this;
    }

    public Lexer concat(String s) throws IOException {
        return concat(new StringReader(s));
    }







    private List<Tokenizer> tokenizers = new LinkedList<>();

    public void registerLexer(Tokenizer lexer) {
        tokenizers.add(lexer);
    }

    private Token nextToken() {
        reader.skipSpace();
        for (Tokenizer tokenizer : tokenizers) {
            Token token = tokenizer.next(reader);
            if (token != null) {
                return token;
            }
        }
        return null;
    }

    private List<Token> parsedTokens = new ArrayList<>();
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
            if (readTokens(pointer)) {
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
            if (readTokens(index)) {
                return parsedTokens.get(index);
            } else {
                return Token.EOF;
            }
        }
    }

    public Token peek() {
        return peek(0);
    }

    private boolean readTokens(int index) {
        while (parsedTokens.size() <= index) {
            Token token = nextToken();
            if (token == null) {
                break;
            }
            if (token.type() != TokenType.COMMENT) {
                parsedTokens.add(token);
            }
        }
        return index < parsedTokens.size();
    }

    public List<Token> abandon() {
        reader.abandon(peek().getPos());
        List<Token> tokens = new ArrayList<>();
        while (pointer < parsedTokens.size()) {
            tokens.add(parsedTokens.remove(parsedTokens.size() - 1));
        }
        return tokens;
    }


    public static void main(String[] args) {
        File file = new File("test/test2.roc");

        try (InputStreamReader reader = new InputStreamReader(new FileInputStream(file), "UTF-8")) {
            LineReader lr = new LineReader(file.getName(), reader);
            Lexer lexer = new Lexer(lr);
            Token token;
            do {
                token = lexer.read();
                //System.out.println(token.literal().replaceAll("\n", "#EOL#"));
            } while (token != Token.EOF);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
