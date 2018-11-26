package rock.parser;

import rock.Lexer;
import rock.RockException;
import rock.ast.ASTLeaf;
import rock.ast.ASTree;
import rock.token.Token;

import java.util.ArrayList;
import java.util.List;

public class SeparateParser extends TerminalParser {


    public SeparateParser(Class<? extends Token> clazz, Object... values) {
        super(ASTLeaf.class, clazz, values);
    }

    @Override
    public boolean doParse(Lexer lexer, List<ASTree> res) throws RockException {
        List<ASTree> list = new ArrayList<>();
        return super.doParse(lexer, list);
    }
}
