package rock.parser;


import rock.RockException;
import rock.ast.ASTLeaf;
import rock.ast.ASTList;
import rock.ast.ASTree;
import rock.Lexer;
import rock.token.*;

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

    public static ForkParser fork(Parser... parsers) {
        return new ForkParser(ASTList.class, parsers);
    }






    public static RepeatParser repeat(Class<ASTList> as, Parser element, Parser operator, boolean needSeparator) {
        return new RepeatParser(as, element, operator, needSeparator);
    }

    public static RepeatParser repeat(Class<ASTList> as, Parser element, Parser operator) {
        return new RepeatParser(as, element, operator);
    }

    public static RepeatParser repeat(Parser element, Parser operator) {
        return new RepeatParser(ASTList.class, element, operator);
    }

    public static RepeatParser repeat(Parser element, Parser operator,boolean needSeparator) {
        return new RepeatParser(ASTList.class, element, operator, needSeparator);
    }






    public static SeqParser seq(Class<ASTList> as) {
        return new SeqParser(as);
    }

    public static SeqParser seq(Parser... parsers) {
        return new SeqParser(ASTList.class, parsers);
    }







    public static BinaryParser binary(Class<ASTList> as, Parser left, Parser operator, Parser right) {
        return new BinaryParser(as, left, operator, right);
    }

    public static BinaryParser binary(Class<ASTList> as, Parser element, Parser operator) {
        return new BinaryParser(as, element, operator, element);
    }

    public static BinaryParser binary(Parser element, Parser operator) {
        return new BinaryParser(ASTList.class, element, operator, element);
    }

    public static BinaryParser binary(Parser left, Parser operator, Parser right) {
        return new BinaryParser(ASTList.class, left, operator, right);
    }








    public static TerminalParser terminal(Class<ASTLeaf> as, Class<? extends Token> clazz, Object... values) {
        return new TerminalParser(as, clazz, values);
    }

    public static TerminalParser id(Object... values) {
        return new TerminalParser(ASTLeaf.class, IdToken.class, values);
    }

    public static TerminalParser num() {
        return new TerminalParser(ASTLeaf.class, NumToken.class);
    }

    public static TerminalParser str() {
        return new TerminalParser(ASTLeaf.class, StrToken.class);
    }

    public static TerminalParser name() {
        return new TerminalParser(ASTLeaf.class, NameToken.class);
    }









}
