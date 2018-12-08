package rock.parser;

import rock.exception.RockException;
import rock.ast.ASTLeaf;
import rock.ast.ASTree;
import rock.Lexer;
import rock.token.Token;
import rock.util.Logger;

import java.lang.reflect.Constructor;
import java.util.*;

public class TerminalParser extends Parser {

    private Class<? extends ASTLeaf> as;
    private Class<? extends Token> clazz;
    private Set values = new HashSet();

    public TerminalParser(Class<? extends ASTLeaf> as, Class<? extends Token> clazz, Object... values) {
        this.as = as;
        this.clazz = clazz;
        this.values.addAll(Arrays.asList(values));
    }

    @Override
    public boolean doParse(Lexer lexer, List<ASTree> res) throws RockException {
        ASTree ast = parse(lexer);
        if (ast != null) {
            res.add(ast);
            return true;
        }
        return false;
    }

    @Override
    public ASTree parse(Lexer lexer) throws RockException {
        Token token = lexer.read();
        Logger.log("expect: " + clazz.getName() + " " + values.toString().replace("\n", "#EOL"));
        Logger.log("find: " + token.getClass().getName() + " " + token.literal().replace("\n", "#EOL"));
        if (!clazz.isInstance(token)) {
            return null;
        }
        if (values.isEmpty() || values.contains(token.value())) {
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

    protected ASTLeaf create(Token token) throws RockException {
        try {
            Constructor<? extends ASTLeaf> constructor = as.getConstructor(Token.class);
            ASTLeaf asl = constructor.newInstance(token);
            return asl;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RockException(e);
        }
    }




}
