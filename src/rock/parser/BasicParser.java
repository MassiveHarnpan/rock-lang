package rock.parser;

import rock.Lexer;
import rock.data.Proxy;
import rock.exception.RockException;
import rock.ast.*;
import rock.token.*;

import java.util.ArrayList;
import java.util.List;

public class BasicParser extends NonTerminalParser {

    

    public BasicParser() {
        super(Program.class);
        Parser string = str().named("string");
        Parser number = num().named("number");
        Parser name = name().named("name");
        Parser eos = sep(";", Token.EOL).named("eos");

        Parser fOperator = id("*", "/", "%").named("fOperator");
        Parser tOperator = id("+", "-").named("tOperator");
        Parser comparator = id("==", "!=", ">", "<", ">=", "<=").named("comparator");

        SeqParser program = seq(Program.class);
        ForkParser progStmt = fork();
        ASTParser block = ast();
        ForkParser stmt = fork();
        ASTParser expr = ast();
        ASTParser arguments = ast();
        ASTParser dot = ast();
        ASTParser index = ast();
        ForkParser postfix = fork();
        ForkParser basic = fork();
        ASTParser primary = ast();
        ForkParser factor = fork();
        ASTParser asgnStmt = ast();
        OptionParser paramList = option();
        ASTParser simple = ast();
        ASTParser funcDef = ast();
        ASTParser closure = ast();
        ASTParser lambda = ast();
        ASTParser ifStmt = ast();
        ASTParser whileStmt = ast();
        ForkParser classStmt = fork();
        ASTParser classBlock = ast();
        ASTParser classDef = ast();
        ASTParser array = ast();



        arguments.of(seq(Arguments.class).then(sep("("), maybe(seq(stmt, repeat(seq(sep(","), stmt)))), sep(")"))).named("arguments");
        index.of(seq(Index.class).then(sep("["), factor, sep("]"))).named("index");
        dot.of(seq(Dot.class).then(sep("."), name)).named("dot");
        postfix.or(arguments, dot, index).named("postfix");
        basic.or(seq(sep("("), stmt, sep(")")), number, name, string, array).named("basic");
        primary.of(seq(Primary.class).then(basic, repeat(postfix))).named("primary");
        factor.or(ast(seq(Negative.class).then(sep("-"), primary)), primary).named("factor");
        Parser term = ast(seq(Expr.class).then(factor, repeat(seq(fOperator, factor)))).named("term");
        Parser comp = ast(seq(Expr.class).then(term, repeat(seq(tOperator, term)))).named("comp");
        expr.of(seq(Expr.class).then(comp, repeat(seq(comparator, comp)))).named("expr");




        program.then(maybe(progStmt), repeat(seq(eos, maybe(progStmt)))).named("program");
        progStmt.or(funcDef, classDef, stmt).named("progStmt");
        block.of(seq(Block.class).then(sep("{"), maybe(stmt), repeat(seq(eos, maybe(stmt))), sep("}"))).named("block");
        stmt.or(ifStmt, whileStmt, asgnStmt, closure, lambda, simple, expr).named("stmt");
        asgnStmt.of(seq(AssignStmt.class).then(primary, sep("="), stmt)).named("asgnStmt");

        paramList.of(ast(seq(ASTList.class).then(name, repeat(seq(sep(","), name))))).named("paramList");

        funcDef.of(seq(FuncDef.class).then(sep("def"), name, sep("("), paramList, sep(")"), block)).named("funcDef");


        closure.of(seq(Closure.class).then(sep("fun"), sep("("), paramList, sep(")"), block)).named("closure");
        lambda.of(seq(Lambda.class).then(fork(seq(sep("("), paramList, sep(")")), ast(seq(name))), sep("->"), fork(block, stmt))).named("lambda");

        simple.of(seq(Simple.class).then(sep("#"), primary, ast(repeat(stmt))).named("simple"));


        ifStmt.of(seq(IfStmt.class).then(sep("if"), stmt, block, maybe(seq(sep("else"), block)))).named("ifStmt");
        whileStmt.of(seq(WhileStmt.class).then(sep("while"), stmt, block)).named("whileStmt");


        classStmt.or(funcDef, asgnStmt).named("classStmt");
        classBlock.of(seq(Block.class).then(sep("{"), maybe(classStmt), repeat(seq(eos, maybe(classStmt))), sep("}"))).named("classBlock");
        classDef.of(seq(ClassDef.class).then(sep("class"), name, maybe(seq(sep("extends"), name)), classBlock)).named("classDef");

        array.of(seq(Array.class).then(sep("["), maybe(seq(expr, repeat(seq(sep(","), expr)))), sep("]"))).named("array");

        this.program = program;
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
        return program.parse(lexer);
    }

//    @Override
//    public boolean match(Lexer lexer) throws RockException {
//        return program.match(lexer);
//    }
}
