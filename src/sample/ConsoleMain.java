package sample;

import rock.Lexer;
import rock.RockException;
import rock.ast.ASTree;
import rock.parser.BasicParser;
import rock.parser.Parser;

import java.io.*;

public class ConsoleMain {

    public static void main(String[] args) throws IOException, RockException {

        Lexer lexer;
        Parser parser = new BasicParser();

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
        StringBuffer program = new StringBuffer();
        String prev = null, line;
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if ("/exit".equals(line)) {
                break;
            }
            if (!line.isEmpty()) {
                lexer = new Lexer(new StringReader(line));
                ASTree ast = parser.parse(lexer);
                System.out.println(ast);
            }
        }
    }

}
