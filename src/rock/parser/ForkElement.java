package rock.parser;

import rock.Lexer;
import rock.RockException;
import rock.ast.ASTree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ForkElement implements Element {

    protected List<Parser> parsers;

    public ForkElement(Parser... parsers) {
        this.parsers = new ArrayList<>(Arrays.asList(parsers));
    }

    @Override
    public void parse(Lexer lexer, List<ASTree> res) throws RockException {
        Parser p = choose(lexer);
        if (p == null) {
            throw new RockException(lexer.peek(0).toString());
        }
        res.add(p.parse(lexer));
    }

    @Override
    public boolean match(Lexer lexer) throws RockException {
        return choose(lexer) != null;
    }

    protected Parser choose(Lexer lexer) throws RockException {
        for (Parser p : parsers) {
            if (p.match(lexer)) {
                return p;
            }
        }
        return null;
    }

    protected void insert(Parser p) {
        parsers.add(0, p);
    }
}
