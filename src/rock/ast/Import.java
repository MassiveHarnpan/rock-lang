package rock.ast;

import rock.data.Environment;
import rock.data.Rock;
import rock.data.internal.RockNil;
import rock.exception.RockException;
import rock.lexer.Lexer;
import rock.parser.RockParser;
import rock.token.Token;
import rock.util.IndentationPrinter;
import rock.util.LineReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class Import extends ASTList {

    public static final ASTListFactory FACTORY = elements -> new Import(elements);

    public Import(ASTree... children) {
        super(children);
    }

    public String fileName() {
        return child(0).token().getStr();
    }


    @Override
    public Rock eval(Environment env, Rock base) throws RockException {
        String fileName = fileName();
        File file = new File(fileName);
        try (InputStreamReader reader = new InputStreamReader(new FileInputStream(file), "UTF-8")) {
            LineReader lr = new LineReader(file.getName(), reader);
            Lexer lexer = new Lexer(lr);
            RockParser parser = new RockParser();
            ASTree ast = parser.parse(lexer);
            if (ast == null || lexer.peek() != Token.EOF) {
                return RockNil.INSTANCE;
            }
            Rock result = ast.eval(env, null);
            return result == null ? RockNil.INSTANCE : result;
        } catch (Exception e) {
            throw new RockException(e);
        }
    }

    @Override
    public String toString() {
        return "import " + fileName();
    }

    @Override
    public void write(IndentationPrinter printer) {
        printer.print("import ").print(fileName());
    }
}
