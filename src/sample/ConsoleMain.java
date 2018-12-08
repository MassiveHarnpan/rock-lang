package sample;

import rock.Lexer;
import rock.data.*;
import rock.exception.RockException;
import rock.ast.ASTree;
import rock.parser.BasicParser;
import rock.parser.Parser;
import rock.runtime.NativeEvaluator;
import rock.runtime.NativeFunction;
import rock.util.Logger;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class ConsoleMain {

    static BufferedReader reader;
    static Parser parser = new BasicParser();

    public static void main(String[] args) throws IOException, RockException {
        init();

        Lexer lexer;

        StringBuffer program = new StringBuffer();
        String prev = null;
        String line;
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if ("/exit".equals(line)) {
                break;
            }
            if (prev == null) {
                if (run(line)) {
                    prev = null;
                    continue;
                }
                prev = line;
                program.delete(0, program.length());
                program.append(line);
                continue;
            }
            if (line.isEmpty()) {
                run(program.toString());
                prev = null;
                program.delete(0, program.length());
                continue;
            }
            program.append("\n" + line);
            prev = line;
        }
    }

    static boolean run(String s) throws RockException {
        Lexer lexer = new Lexer(new StringReader(s));
        ASTree ast = parser.parse(lexer);
        if (ast == null) {
            return false;
        }
        Rock r = ast.eval(runtime);
        String rst;
        if (r == null) {
            rst = "<>";
        } else {
            rst = String.valueOf(r instanceof  RockLiteral ? ((RockLiteral) r).get() : r);
        }
        System.out.println("=>  " + rst);
        return true;
    }

    static Environment runtime = new Environment();

    public static void init() {
        try {
            reader = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));

            Function currentTimeMillis = new NativeFunction("currentTimeMillis", System.class.getDeclaredMethod("currentTimeMillis"));
            runtime.set("currentTimeMillis", currentTimeMillis);

            runtime.set("print", new Function("print", new String[]{"msg"}, new NativeEvaluator() {
                @Override
                public Rock eval(Environment env) throws RockException {
                    System.out.println(String.valueOf(env.get("msg")));
                    return null;
                }
            }, runtime));
            runtime.set("println", new Function("println", new String[] {"msg"}, new NativeEvaluator() {
                @Override
                public Rock eval(Environment env) throws RockException {
                    System.out.println("\n" + String.valueOf(env.get("msg")));
                    return null;
                }
            }, runtime));
            runtime.set("ln", new Function("ln", new String[] {}, new NativeEvaluator() {
                @Override
                public Rock eval(Environment env) throws RockException {
                    System.out.println("\n");
                    return null;
                }
            }, runtime));
            runtime.set("show_log", new Function("show_log", new String[] {"stat"}, new NativeEvaluator() {
                @Override
                public Rock eval(Environment env) throws RockException {
                    Rock r = env.get("stat");
                    boolean show = false;
                    if (r == null || Integer.valueOf(0).equals(r.get())) {
                        show = false;
                    } else {
                        show = true;
                    }
                    Logger.setShow(show);
                    return null;
                }
            }, runtime));
            runtime.set("exit", new Function("exit", new String[0], new NativeEvaluator() {
                @Override
                public Rock eval(Environment env) throws RockException {
                    System.exit(0);
                    return null;
                }
            }, runtime));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

}
