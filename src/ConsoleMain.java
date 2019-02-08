import rock.ast.ASTree;
import rock.buildin.NativeMethods;
import rock.data.*;
import rock.data.internal.RockBoolean;
import rock.data.internal.RockFunction;
import rock.data.internal.RockNil;
import rock.exception.ParseException;
import rock.exception.RockException;
import rock.lexer.Lexer;
import rock.parser.RockParser;
import rock.token.Token;
import rock.util.LineReader;

import java.io.*;

public class ConsoleMain {

    public static void main(String[] args) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(System.in, "UTF-8"))) {
            LineReader lr = new LineReader("console");
            Lexer lexer = new Lexer(lr);

            RockParser parser = new RockParser();
            ASTree ast = null;
            NestedEnvironment global = new NestedEnvironment();

            global.set("print", new RockFunction("print", new String[]{"msg"}, NativeMethods.PRINT, global));
            global.set("time", new RockFunction("time", new String[0], NativeMethods.CURRENT_TIME_MILLIS, global));
            global.set("true", RockBoolean.TRUE);
            global.set("false", RockBoolean.FALSE);
            global.set("nil", RockNil.INSTANCE);


            String line;
            System.out.print(">>> ");
            while ((line = in.readLine()) != null && !"/exit".equals(line)) {
                lexer.concat(line);
                try {
                    ast = parser.parse(lexer);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (lexer.peek() != Token.EOF) {
                    System.out.println("Cannot parse: " + lexer.peek() + " at " + lexer.peek().getPos());
                    lexer.abandon();
                }
                Rock rock = null;
                try {
                    rock = (ast == null) ? RockNil.INSTANCE : ast.eval(global, null);
                } catch (RockException e) {
                    e.printStackTrace();
                }
                System.out.println();
                System.out.print("<-< ");
                System.out.println(rock);

                System.out.print(">>> ");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
