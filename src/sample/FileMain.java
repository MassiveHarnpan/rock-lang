package sample;

import rock.Lexer;
import rock.RockException;
import rock.ast.ASTree;
import rock.parser.BasicParser;
import rock.parser.Parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class FileMain {

    public static void main(String[] args) throws FileNotFoundException, RockException {

        Lexer lexer;
        Parser parser = new BasicParser();


        File program = new File("program.txt");
        if (program.exists()) {
            lexer = new Lexer(new FileReader(program));
            ASTree ast = parser.parse(lexer);
            System.out.println(ast);
            return;
        } else {
            System.out.println("File "+program+" not exist");
        }

    }


}
