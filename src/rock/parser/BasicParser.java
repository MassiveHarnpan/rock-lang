package rock.parser;

import rock.Lexer;
import rock.RockException;
import rock.ast.*;
import rock.token.*;

import java.util.ArrayList;
import java.util.List;

public class BasicParser extends NonTerminalParser {

    

    public BasicParser() {
        super(Block.class);
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
                maybe(seq(sep("else"),
                        block)
                ))).named("ifStmt");

        Parser params = ast(seq(sep("("), option(seq(
                name,
                repeat(seq(sep(","), name))
        )), sep(")"))).named("params");

        Parser args = ast(seq(sep("("), option(seq(
                expression,
                repeat(seq(sep(","), expression))
        )), sep(")"))).named("args");

        Parser funcDef = ast(seq(FuncDef.class).then(sep("def"), name, params, block)).named("funcDef");
        Parser funcCall = ast(seq(FuncCall.class).then(name, args)).named("funcCall");



        stmt.then(maybe(fork(whileStmt, ifStmt, funcCall, assignStmt, expression, funcDef)), eol).named("stmt");
        Parser statement = ast(stmt).named("statement");






        program = repeat(Block.class, statement);
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
