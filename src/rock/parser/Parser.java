package rock.parser;


import rock.RockException;
import rock.ast.*;
import rock.Lexer;
import rock.token.*;
import rock.util.Logger;

import java.util.List;

public abstract class Parser {

    private static int i = 0;

    public boolean parse(Lexer lexer, List<ASTree> res) throws RockException {
        if (i > 50) System.exit(-1);
        i ++ ;
        StringBuffer ind = new StringBuffer();
        for (int j = 0; j < i; j++) {
            ind.append(" |");
        }
        String indent = ind.toString();
        String name = getName();
        Logger.log(indent+"Parsing " + name);

        int check = lexer.pointer();
        int back = res.size();
        boolean result = doParse(lexer, res);
        if (!result) {
            Logger.log(indent+"parse "+name+" failed");
            lexer.recovery(check);
            while (res.size() > back) {
                res.remove(back);
            }
        } else {
            Logger.log(indent+"parse "+name+" succeeded: " + res);
        }
        i--;
        return result;
    }
    protected abstract boolean doParse(Lexer lexer, List<ASTree> res) throws RockException;
    public abstract ASTree parse(Lexer lexer) throws RockException;

    protected String name;

    public Parser named(String name) {
        this.name = name;
        return this;
    }

    public String getName() {
        return this.name == null ? getClass().getSimpleName() : this.name;
    }
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






    public static RepeatParser repeat(Class<? extends ASTList> as, Parser element) {
        return new RepeatParser(as, element);
    }

    public static RepeatParser repeat(Parser element) {
        return new RepeatParser(ASTList.class, element);
    }






    public static ASTParser ast(Parser parser) {
        return new ASTParser(ASTList.class, parser);
    }

    public static ASTParser ast() {
        return new ASTParser(ASTList.class);
    }










    public static SeqParser seq(Class<? extends ASTList> as) {
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




    public static SeparateParser sep(Class<? extends Token> clazz, Object... values) {
        return new SeparateParser(clazz, values);
    }

    public static SeparateParser sep(Object... values) {
        return new SeparateParser(IdToken.class, values);
    }




    public static OptionParser maybe(Parser parser) {
        return new OptionParser(ASTList.class, parser, false);
    }

    public static OptionParser option(Parser parser) {
        return new OptionParser(ASTList.class, parser, true);
    }

    public static OptionParser option() {
        return new OptionParser(ASTList.class, null, true);
    }












    public static TerminalParser terminal(Class<ASTLeaf> as, Class<? extends Token> clazz, Object... values) {
        return new TerminalParser(as, clazz, values);
    }

    public static TerminalParser id(Object... values) {
        return new TerminalParser(ASTLeaf.class, IdToken.class, values);
    }

    public static TerminalParser num() {
        return new TerminalParser(Primary.class, NumToken.class);
    }

    public static TerminalParser str() {
        return new TerminalParser(Primary.class, StrToken.class);
    }

    public static TerminalParser name() {
        return new TerminalParser(Name.class, NameToken.class);
    }









}
