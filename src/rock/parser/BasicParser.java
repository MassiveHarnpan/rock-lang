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
        Parser eos = sep(";", Token.EOL).named("eos");

        Parser fOperator = id("*", "/", "%").named("fOperator");
        Parser tOperator = id("+", "-").named("tOperator");
        Parser comparator = id("==", "!=", ">", "<", ">=", "<=").named("comparator");

        ASTParser program = ast();
        ForkParser progStmt = fork();
        ASTParser block = ast();
        ForkParser stmt = fork();
        ASTParser expr = ast();
        ForkParser primary = fork();
        ForkParser factor = fork();
        ASTParser asgnStmt = ast();
        OptionParser argList = option();
        OptionParser paramList = option();
        ASTParser funcCall = ast();
        ASTParser simple = ast();
        ASTParser funcDef = ast();
        ASTParser closure = ast();
        ASTParser ifStmt = ast();
        ASTParser whileStmt = ast();
        ForkParser classStmt = fork();
        ASTParser classBlock = ast();
        ASTParser classDef = ast();


        primary.or(seq(sep("("), stmt, sep(")")), funcCall, number, name, string).named("primary");
        factor.or(seq(Negtive.class).then(sep("-"), primary), primary).named("factor");
        Parser term = ast(seq(Expr.class).then(factor, repeat(seq(fOperator, factor)))).named("term");
        Parser comp = ast(seq(Expr.class).then(term, repeat(seq(tOperator, term)))).named("comp");
        expr.of(seq(Expr.class).then(comp, repeat(seq(comparator, comp)))).named("expr");




        program.of(seq(Block.class).then(maybe(progStmt), repeat(seq(eos, maybe(progStmt))))).named("program");
        progStmt.or(funcDef, classDef, stmt).named("progStmt");
        block.of(seq(Block.class).then(sep("{"), maybe(stmt), repeat(seq(eos, maybe(stmt))), sep("}"))).named("block");
        stmt.or(ifStmt, whileStmt, asgnStmt, closure, simple, expr).named("stmt");
        asgnStmt.of(seq(AssignStmt.class).then(name, sep("="), stmt)).named("asgnStmt");

        argList.of(ast(seq(ASTList.class).then(stmt, repeat(seq(sep(","), stmt))))).named("argList");
        paramList.of(ast(seq(ASTList.class).then(name, repeat(seq(sep(","), name))))).named("paramList");

        funcDef.of(seq(FuncDef.class).then(sep("def"), name, sep("("), paramList, sep(")"), block)).named("funcDef");
        funcCall.of(seq(FuncCall.class).then(name, sep("("), argList, sep(")"))).named("funcCall");

        closure.of(seq(Closure.class).then(sep("fun"), sep("("), paramList, sep(")"), block)).named("closure");

        simple.of(seq(FuncCall.class).then(sep("#"), name, ast(repeat(stmt))).named("simple"));


        ifStmt.of(seq(IfStmt.class).then(sep("if"), stmt, block, maybe(seq(sep("else"), block)))).named("ifStmt");
        whileStmt.of(seq(WhileStmt.class).then(sep("while"), stmt, block)).named("whileStmt");


        classStmt.or(funcDef, asgnStmt).named("classStmt");
        classBlock.of(seq(Block.class).then(sep("{"), maybe(classStmt), repeat(seq(eos, maybe(classStmt))), sep("}"))).named("classBlock");
        classDef.of(seq(ClassDef.class).then(sep("class"), name, classBlock)).named("classDef");

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
