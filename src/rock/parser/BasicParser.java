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
        Parser string = str().named("string");
        Parser number = num().named("number");
        Parser name = name().named("name");
        Parser eol = sep(Token.EOL).named("eol");

        Parser fOperator = id("*", "/", "%").named("fOperator");
        Parser tOperator = id("+", "-").named("tOperator");
        Parser comparator = id("==", "!=", ">", "<", ">=", "<=").named("comparator");

        ASTParser func = ast();
        ASTParser expr = ast();
        Parser primary = fork(seq(sep("("), expr, sep(")")), func, number, name, string).named("primary");
        Parser factor = fork(seq(Negtive.class).then(sep("-"), primary), primary).named("factor");
        Parser term = ast(seq(Expr.class).then(factor, repeat(seq(fOperator, factor)))).named("term");
        Parser comp = ast(seq(Expr.class).then(term, repeat(seq(tOperator, term)))).named("comp");
        expr.of(seq(Expr.class).then(comp, repeat(seq(comparator, comp)))).named("expr");

        ASTParser args = ast();
        Parser simple = ast(seq(Simple.class).then(name, ast(repeat(expr))).named("simple"));

        ASTParser stmt = ast();

        Parser assign = ast(seq(AssignStmt.class).then(name, sep("="), stmt)).named("assign");

        Parser block = ast(seq(Block.class).then(sep("{"), maybe(stmt), repeat(seq(sep(";", Token.EOL), maybe(stmt))), sep("}"))).named("block");
        Parser whileStmt = ast(seq(WhileStmt.class).then(sep("while"),
                expr,
                block)).named("whileStmt");
        Parser ifStmt = ast(seq(IfStmt.class).then(sep("if"),
                expr,
                block,
                maybe(seq(sep("else"),
                        block)
                ))).named("ifStmt");



        // function
        Parser params = ast(seq(sep("("), option(seq(
                name,
                repeat(seq(sep(","), name))
        )), sep(")"))).named("params");

        args.of(seq(sep("("), option(seq(
                expr,
                repeat(seq(sep(","), expr))
        )), sep(")"))).named("args");

        Parser def = ast(seq(FuncDef.class).then(sep("def"), name, params, block)).named("def");
        func.of(seq(FuncCall.class).then(name, args)).named("func");


        stmt.of(fork(whileStmt, ifStmt, assign, expr)).named("stmt");






        program = repeat(Block.class, seq(maybe(fork(simple, def, stmt)), sep(";", Token.EOL))).named("program");
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
