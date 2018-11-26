package rock.parser;

import rock.Lexer;
import rock.RockException;
import rock.ast.*;
import rock.token.*;

import java.util.ArrayList;
import java.util.List;

public class BasicParser extends NonTerminalParser {

    

    public BasicParser() {
        super(ASTList.class);
        Parser string = str();
        Parser number = num();
        Parser name = name();

        Parser fOperator = id("*", "/", "%");
        Parser tOperator = id("+", "-");
        Parser bOperator = id("==", "!=", ">", "<", ">=", "<=");

        Parser factor = fork(number, string, name);
        Parser term = ast(seq(Expr.class).then(factor, repeat(seq(fOperator, factor))));
        Parser expr = ast(seq(Expr.class).then(term, repeat(seq(tOperator, term))));
        Parser expression = ast(seq(Expr.class).then(expr, repeat(seq(bOperator, expr))));
        Parser assignStmt = seq(AssignStmt.class).then(name).then(sep("=")).then(expression);
        SeqParser stmt = seq(ASTList.class);
        Parser eol = sep(Token.EOL);
        Parser block = ast(seq(Block.class).then(sep("{"), eol, repeat(stmt), sep("}")));
        Parser whileStmt = seq(WhileStmt.class).then(sep("while"),
                expression,
                block);
        Parser ifStmt = seq(IfStmt.class).then(sep("if"),
                expression,
                block,
                option(
                        seq(sep("else"),
                                block)
                ));


        stmt.then(option(fork(whileStmt, ifStmt, assignStmt, expression)), eol);


        program = repeat(ASTList.class, stmt);
    }

    private Parser program;

//    static {
//        ((ForkParser)factor).or(term);
//    }

    @Override
    public boolean doParse(Lexer lexer, List<ASTree> res) throws RockException {
        return program.parse(lexer, res);
    }

    public ASTree parse(Lexer lexer) throws RockException {
        List<ASTree> res = new ArrayList<>();
        if (program.parse(lexer, res)) {
            return create(res.toArray(new ASTree[res.size()]));
        }
        return null;
    }

//    @Override
//    public boolean match(Lexer lexer) throws RockException {
//        return program.match(lexer);
//    }
}
