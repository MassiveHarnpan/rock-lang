package rock;

import rock.ast.ASTree;
import rock.data.Environment;
import rock.data.NestedEnvironment;
import rock.data.Rock;
import rock.data.internal.RockFunction;
import rock.data.internal.RockNil;
import rock.data.internal.RockString;
import rock.exception.RockException;
import rock.parser.BasicParser;
import rock.parser.Parser;
import rock.runtime.BasicRuntimeEnvironment;
import rock.runtime.NativeEvaluator;
import rock.token.Token;
import rock.util.Convert;
import rock.util.Logger;

import java.io.Reader;
import java.io.StringReader;
import java.util.regex.Pattern;

public abstract class RockVirtualMachine {

    private Parser program = new BasicParser();
    private Environment runtime = new BasicRuntimeEnvironment(this);
    private Environment rootEnv = new NestedEnvironment(runtime);


    public Environment getRootEnv() {
        return rootEnv;
    }

    public Environment getRuntime() {
        return runtime;
    }

    public Rock eval(Reader reader) {
        Lexer lexer = new Lexer(reader);
        try {
            ASTree result = program.parse(lexer);
            return checkCompleted(result, lexer);
        } catch (RockException e) {
            onException(e);
            return null;
        }
    }

    public void init() {
        Logger.setRVM(this);
        try {
            runtime.set("show_log", new RockFunction("show_log", new String[] {"opt"}, new NativeEvaluator() {
                @Override
                public Rock eval(Environment env) throws RockException {
                    Rock rock = env.get("opt");
                    Logger.setShow(Convert.toBoolean(rock));
                    return RockNil.INSTANCE;
                }
            }, runtime));
        } catch (RockException e) {
            e.printStackTrace();
        }
    }

    private StringBuffer sb = new StringBuffer();
    private String prev = null;
    private Pattern emptyPattern = Pattern.compile("\\s*");

    public Rock evalLine(String line) {
        Lexer lexer;
        ASTree ast;
        if (prev == null) {
            Logger.log("prev = null");
            if (emptyPattern.matcher(line).matches()) {
                Logger.log("line = empty");
                return RockNil.INSTANCE;
            }
            Logger.log("line != empty");
            lexer = new Lexer(new StringReader(line));
            try {
                ast = program.parse(lexer);
            } catch (RockException e) {
                return null;
            }
            try {
                if (ast == null || lexer.peek(0) != Token.EOF) {
                    prev = line;
                    sb.delete(0, sb.length());
                    sb.append(line);
                    return null;
                }
            } catch (RockException e) {
                onException(e);
                return null;
            }
            return checkCompleted(ast, lexer);
        }
        Logger.log("prev != null");
        if (!emptyPattern.matcher(line).matches()) {
            Logger.log("line != empty");
            prev = line;
            sb.append('\n').append(line);
            return null;
        }


        Logger.log("line = empty");
        prev = null;
        lexer = new Lexer(new StringReader(sb.toString()));
        sb.delete(0, sb.length());

        try {
            ast = program.parse(lexer);
            return checkCompleted(ast, lexer);
        } catch (RockException e) {
            onException(e);
            return null;
        }
    }

    protected Rock checkCompleted(ASTree ast, Lexer lexer) {
        try {
            if (lexer.peek(0) != Token.EOF) {
                onException(new RockException("uncompleted program"));
            }
            if (ast == null) {
                return null;
            }
            return ast.simplify().eval(rootEnv);
        } catch (RockException e) {
            onException(e);
            return null;
        }
    }



    public abstract void onException(RockException e);
    public abstract void onLog(String msg);
    public abstract void onOutput(String msg);
    public abstract String onInput();

}
