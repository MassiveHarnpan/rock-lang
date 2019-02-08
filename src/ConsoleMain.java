import rock.ast.ASTree;
import rock.buildin.NativeMethods;
import rock.data.*;
import rock.data.internal.RockFunction;
import rock.data.internal.RockNil;
import rock.exception.RockException;
import rock.lexer.Lexer;
import rock.parser.RockParser;
import rock.util.LineReader;

import java.io.*;

public class ConsoleMain {

    public static void main(String[] args) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(System.in, "UTF-8"))) {
            LineReader lr = new LineReader("console");
            Lexer lexer = new Lexer(lr);

            RockParser parser = new RockParser();
            ASTree ast;
            NestedEnvironment global = new NestedEnvironment();

            global.set("print", new RockFunction("print", new String[]{"msg"}, NativeMethods.PRINT, global));
            global.set("time", new RockFunction("time", new String[0], NativeMethods.CURRENT_TIME_MILLIS, global));

            String line;
            while ((line = in.readLine()) != null && !"/exit".equals(line)) {
                lexer.concat(line);
                ast = parser.parse(lexer);
                System.out.println(ast.eval(global, null));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
