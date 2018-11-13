package rock.parser;

import rock.RockException;
import rock.ast.ASTLeaf;
import rock.ast.ASTList;
import rock.ast.ASTree;
import rock.Lexer;
import rock.token.Token;

import java.lang.reflect.Constructor;
import java.util.Objects;

import static rock.token.Token.EOL;

public class TerminalParser extends Parser {

    private Class<ASTLeaf> as;
    private Class<? extends Token> clazz;
    private Object value;

    public TerminalParser(Class<ASTLeaf> as, Class<? extends Token> clazz, Object value) {
        this.as = as;
        this.clazz = clazz;
        this.value = value;
    }

    @Override
    public ASTree doParse(Lexer lexer) throws RockException {
        Token token = lexer.read();
        //System.out.println("expect: "+clazz.getSimpleName()+" "+value);
        //System.out.println("get: "+token.getClass().getSimpleName()+" "+token.literal().replace(EOL, "#EOL"));
        if (!clazz.isInstance(token)) {
            return null;
        }
        if (value == null) {
            return create(token);
        }
        if (Objects.equals(value, token.value())) {
            return create(token);
        }
        return null;
    }

//    @Override
//    public boolean match(Lexer lexer) throws RockException {
//        if (clazz.isInstance(lexer.peek(0))) {
//            return true;
//        }
//        return false;
//    }

    public ASTLeaf create(Token token) throws RockException {
        try {
            Constructor<ASTLeaf> constructor = as.getConstructor(Token.class);
            ASTLeaf asl = constructor.newInstance(token);
            return asl;
        } catch (Exception e) {
            throw new RockException(e.getMessage());
        }
    }




}
