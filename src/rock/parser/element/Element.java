package rock.parser.element;

import rock.ast.ASTree;
import rock.exception.ParseException;
import rock.exception.RockException;
import rock.lexer.Lexer;

import java.util.List;

public abstract class Element {

    public abstract ASTree parse(Lexer lexer) throws ParseException;

    public boolean parse(Lexer lexer, List<ASTree> res) throws ParseException {
        int check = lexer.pointer();
        int back = res.size();
        boolean result = doParse(lexer, res);
        if (!result) {
            lexer.recovery(check);
            while (res.size() > back) {
                res.remove(back);
            }
        }
        return result;
    }
    protected abstract boolean doParse(Lexer lexer, List<ASTree> res) throws ParseException;
}
