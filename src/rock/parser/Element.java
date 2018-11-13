package rock.parser;

import rock.Lexer;
import rock.RockException;
import rock.ast.ASTree;

import java.util.List;

public interface Element {

    void parse(Lexer lexer, List<ASTree> res) throws RockException;
    boolean match(Lexer lexer) throws RockException;

}
