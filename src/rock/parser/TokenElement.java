package rock.parser;

import rock.Lexer;
import rock.RockException;
import rock.ast.ASTLeaf;
import rock.ast.ASTList;
import rock.ast.ASTree;
import rock.token.Token;
import rock.token.TokenType;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TokenElement implements Element {

    protected Class<ASTLeaf> clazz;
    protected TokenType type;
    protected Set<Object> values = new HashSet<>();

    public TokenElement(Class<ASTLeaf> clazz, TokenType type, Object... values) {
        this.clazz = clazz == null? ASTLeaf.class : clazz;
        this.type = type;
        this.values.addAll(Arrays.asList(values));
    }

    @Override
    public void parse(Lexer lexer, List<ASTree> res) throws RockException {
        Token token = lexer.read();
        ASTLeaf asl = Factory.makeLeaf(clazz, token);
        if (asl != null) {
            res.add(asl);
        }
    }

    @Override
    public boolean match(Lexer lexer) throws RockException {
        Token token = lexer.peek(0);
        if (token.type() != type) {
            return false;
        }
        if (values.isEmpty()) {
            return true;
        } else {
            return values.contains(token.value());
        }
    }
}
