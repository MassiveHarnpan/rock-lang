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

        Parser fOperator = id("*", "/", "%").named("fOperator");
        Parser tOperator = id("+", "-").named("tOperator");
        Parser bOperator = id("==", "!=", ">", "<", ">=", "<=").named("bOperator");

        Parser factor = fork(number, string, name).named("factor");
        Parser term = ast(seq(Expr.class).then(factor, repeat(seq(fOperator, factor)))).named("term");
        Parser expr = ast(seq(Expr.class).then(term, repeat(seq(tOperator, term)))).named("expr");
        Parser expression = ast(seq(Expr.class).then(expr, repeat(seq(bOperator, expr)))).named("expression");
        Parser assignStmt = ast(seq(AssignStmt.class).then(name, sep("="), expression)).named("assignStmt");
        SeqParser stmt = seq(ASTList.class);
        Parser eol = sep(Token.EOL).named("eol");
        Parser block = ast(seq(Block.class).then(sep("{"), eol, repeat(stmt), sep("}"))).named("block");
        Parser whileStmt = ast(seq(WhileStmt.class).then(sep("while"),
                expression,
                block)).named("whileStmt");
        Parser ifStmt = ast(seq(IfStmt.class).then(sep("if"),
                expression,
                block,
                option(seq(sep("else"),
                        block)
                ))).named("ifStmt");


        stmt.then(option(fork(whileStmt, ifStmt, assignStmt, expression)), eol).named("stmt");
        Parser statement = ast(stmt).named("statement");


        program = repeat(ASTList.class, statement);
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
