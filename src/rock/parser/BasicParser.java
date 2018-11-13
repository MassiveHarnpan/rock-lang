package rock.parser;

import rock.Lexer;
import rock.RockException;
import rock.ast.ASTLeaf;
import rock.ast.ASTList;
import rock.ast.ASTree;
import rock.token.*;

public class BasicParser extends Parser {

    protected static Parser string = str();
    protected static Parser number = num();
    protected static Parser name = name();
    protected static Parser fOperator = id("*", "/", "%");
    protected static Parser tOperator = id("+", "-");
    protected static Parser bOperator = id("==", "!=", ">", "<", ">=", "<=");
    protected static Parser factor = fork(number, string, name);
    protected static Parser term = repeat(factor, fOperator, true);
    protected static Parser expr = fork().or(binary(ASTList.class, term, tOperator)).or(term);
    protected static Parser numExpr = repeat(term, tOperator);
    protected static Parser boolExpr = repeat(numExpr, bOperator);
    protected static Parser condition = boolExpr;
    protected static Parser expression = fork(numExpr, boolExpr);
    protected static Parser assignment = binary(name, id("="), expression);
    protected static Parser eol = id(Token.EOL);
    protected static Parser body = repeat(fork(assignment, expression), eol);
    protected static Parser whileStmt = seq(id("while"), condition, id("{"), eol)
            .then(body).then(eol).then(id("}")).then(eol);
    protected static Parser ifStmt = seq(id("if"), condition, id("{"), eol)
            .then(body).then(eol).then(id("}")).then(id("else")).then(id("{")).then(eol).then(body).then(eol).then(id("}")).then(eol);


    protected static Parser statement = fork().or(whileStmt).or(ifStmt).or(assignment).or(expression);


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
