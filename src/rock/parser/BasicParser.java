package rock.parser;

import rock.Lexer;
import rock.RockException;
import rock.ast.ASTLeaf;
import rock.ast.ASTList;
import rock.ast.ASTree;
import rock.token.IdToken;
import rock.token.NumToken;
import rock.token.StrToken;
import rock.token.Token;

public class BasicParser extends Parser {

    protected static Parser string = terminal(ASTLeaf.class, StrToken.class, null);
    protected static Parser number = terminal(ASTLeaf.class, NumToken.class, null);
    protected static Parser identifier = (terminal(ASTLeaf.class, IdToken.class, null));
    protected static Parser name = terminal(ASTLeaf.class, IdToken.class, null);
    protected static Parser fOperator = fork().or(terminal(ASTLeaf.class, IdToken.class, "*")).or(terminal(ASTLeaf.class, IdToken.class, "/")).or(terminal(ASTLeaf.class, IdToken.class, "%"));
    protected static Parser tOperator = fork().or(terminal(ASTLeaf.class, IdToken.class, "+")).or(terminal(ASTLeaf.class, IdToken.class, "-"));
    protected static Parser factor = fork().or(number).or(string).or(name);
    protected static Parser term = fork().or(binary(ASTList.class, factor, fOperator)).or(factor);
    protected static Parser expr = fork().or(binary(ASTList.class, term, tOperator)).or(term);
    protected static Parser expression = fork().or(binary(ASTList.class, expr, tOperator, term)).or(expr);
    protected static Parser condition = binary(ASTList.class, expression, fork().or(terminal(ASTLeaf.class, IdToken.class, "==")).or(terminal(ASTLeaf.class, IdToken.class, "!=")));
    protected static Parser assignment = binary(ASTList.class, terminal(ASTLeaf.class, IdToken.class, null), terminal(ASTLeaf.class, IdToken.class, "="), expression);
    protected static Parser statement = fork().or(assignment).or(condition).or(expression);
    protected static Parser program = repeat(ASTList.class, statement, terminal(ASTLeaf.class, IdToken.class, Token.EOL));

//    static {
//        ((ForkParser)factor).or(term);
//    }

    @Override
    public ASTree doParse(Lexer lexer) throws RockException {
        return program.parse(lexer);
    }

//    @Override
//    public boolean match(Lexer lexer) throws RockException {
//        return program.match(lexer);
//    }
}
