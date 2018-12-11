package sample;

import rock.Lexer;
import rock.ast.ASTree;
import rock.data.NestedEnvironment;
import rock.data.Rock;
import rock.exception.RockException;
import rock.parser.BasicParser;
import rock.parser.Parser;
import rock.runtime.BasicRuntimeEnvironment;

import java.io.*;

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
        String rst = (r == null) ? "<>" : r.toString();
        System.out.println("=>  " + rst);
        return true;
    }

    static NestedEnvironment runtime = new NestedEnvironment();

    public static void init() {
        try {
            reader = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        runtime = new BasicRuntimeEnvironment();
    }

}
