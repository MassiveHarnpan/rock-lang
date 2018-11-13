package rock.parser;


import rock.RockException;
import rock.ast.ASTLeaf;
import rock.ast.ASTList;
import rock.ast.ASTree;
import rock.Lexer;
import rock.token.IdToken;
import rock.token.NumToken;
import rock.token.StrToken;
import rock.token.Token;

public abstract class Parser {

    private static int i = 0;

    public ASTree parse(Lexer lexer) throws RockException {
        i ++ ;
        StringBuffer ind = new StringBuffer();
        for (int j = 0; j < i; j++) {
            ind.append(" |");
        }
        String indent = ind.toString();
        System.out.println(indent+"Parsing "+getClass().getSimpleName());
        int check = lexer.pointer();
        ASTree ast = doParse(lexer);
        if (ast == null) {
            System.out.println(indent+"parse failed");
            lexer.recovery(check);
        } else {
            System.out.println(indent+"parse succeeded: "+ast);
        }
        i--;
        return ast;
    }
    protected abstract ASTree doParse(Lexer lexer) throws RockException;
    //public abstract boolean match(Lexer lexer) throws RockException;


    public static ForkParser fork(Class<ASTList> as) {
        return new ForkParser(as);
    }

    public static ForkParser fork() {
        return new ForkParser(ASTList.class);
    }

    public static RepeatParser repeat(Class<ASTList> as, Parser element, Parser operator) {
        return new RepeatParser(as, element, operator);
    }

    public static SeqParser seq(Class<ASTList> as) {
        return new SeqParser(as);
    }

    public static BinaryParser binary(Class<ASTList> as, Parser left, Parser operator, Parser right) {
        return new BinaryParser(as, left, operator, right);
    }

    public static BinaryParser binary(Class<ASTList> as, Parser element, Parser operator) {
        return new BinaryParser(as, element, operator, element);
    }

    public static TerminalParser terminal(Class<ASTLeaf> as, Class<? extends Token> clazz, Object value) {
        return new TerminalParser(as, clazz, value);
    }









}
