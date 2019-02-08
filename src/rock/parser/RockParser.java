package rock.parser;

import rock.ast.*;
import rock.data.Environment;
import rock.data.Evaluator;
import rock.data.NestedEnvironment;
import rock.data.Rock;
import rock.data.internal.RockFunction;
import rock.data.internal.RockNil;
import rock.exception.ParseException;
import rock.exception.RockException;
import rock.lexer.Lexer;
import rock.parser.element.Element;
import rock.parser.element.ForkElement;
import rock.parser.element.SequenceElement;
import rock.parser.element.TokenElement;
import rock.token.Token;
import rock.token.TokenType;
import rock.util.LineReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class RockParser extends Parser {

    private Parser program;

    public RockParser() {
        super(Program.FACTORY, "rock");

        Element name = new TokenElement(Name.FACTORY, TokenType.NAME);
        Element number =  new ForkElement().or(new TokenElement(TokenType.INTEGER)).or(new TokenElement(TokenType.DECIMAL));
        Element string = new TokenElement(TokenType.STRING);
        Parser lambda = new Parser(Lambda.FACTORY);

        Element boolOps = sign(">", "<", ">=", "<=", "==", "!=", "&", "|");
        Element termOps = sign("+", "-");
        Element factorOps = sign("*", "/", "^");

        Element primary = new ForkElement()
                .or(lambda)
                .or(name)
                .or(number)
                .or(string);

        ForkElement valuable = new ForkElement();
        ForkElement flow = new ForkElement();
        ForkElement stmt = new ForkElement();

        Parser arguments = new Parser(Arguments.FACTORY, "arguments").sep("(").repeat(valuable, split(","), false).sep(")").asAST();
        Parser dot = new Parser(Dot.FACTORY, "dot").sep(".").then(name).asAST();
        Parser index = new Parser(Index.FACTORY, "index").sep("[").then(valuable).sep("]").asAST();

        ForkElement postfix = new ForkElement().or(dot).or(index).or(arguments);
        ForkElement settablePostfix = new ForkElement().or(dot).or(index);

        Parser settable = new Parser(Settable.FACTORY, "settable").fork()
                .then(primary).repeat(settablePostfix, true).or()
                .then(name)
                .merge().asAST();

        Parser gettable = new Parser(Gettable.FACTORY, "gettable").then(primary).repeat(postfix, false).asAST();


        Parser negative = new Parser(Negative.FACTORY, "negative").sep("-").then(gettable).asAST();
        Element factor = new ForkElement().or(negative).or(gettable);


        Parser term = new Parser(Expr.FACTORY, "term").repeat(factor, factorOps, true).asAST();
        Parser bool = new Parser(Expr.FACTORY, "bool").repeat(term, termOps, true).asAST();
        Parser expr = new Parser(Expr.FACTORY, "expr").repeat(bool, boolOps, true).asAST();

        Parser block = new Parser(Block.FACTORY).sep("{").repeat(stmt, false).sep("}").asAST();


        Parser assign = new Parser(AssignStmt.FACTORY, "assign").then(settable).sep("=").then(valuable).asAST();

        SequenceElement valuableStmt = new SequenceElement(valuable, split(";"));

        Parser ifStmt = new Parser(IfStmt.FACTORY).sep("if").sep("(").then(valuable).sep(")")
                .then(block).then(maybe(new SequenceElement(split("else"), block))).asAST();

        Parser whileStmt = new Parser(WhileStmt.FACTORY).sep("while").sep("(").then(valuable).sep(")").then(block).asAST();

        valuable.or(assign).or(expr);
        flow.or(ifStmt).or(whileStmt);
        stmt.or(flow).or(valuableStmt);

        Parser params = new Parser(ASTList.FACTORY).repeat(name, false).asAST();
        Parser funcDef = new Parser(FuncDef.FACTORY).sep("def").then(name).sep("(").then(params).sep(")").then(block).asAST();
        lambda.sep("(").then(params).sep(")").sep("->").then(block).asAST();

        ForkElement progStmt = new ForkElement()
                .or(funcDef)
                .or(stmt);

        this.program = new Parser(Program.FACTORY, "program").repeat(new ForkElement().or(progStmt).or(split(";")), false).asAST();

    }

    @Override
    public ASTree parse(Lexer lexer) throws ParseException {
        return program.parse(lexer);
    }

    @Override
    public boolean parse(Lexer lexer, List<ASTree> res) throws ParseException {
        return program.parse(lexer, res);
    }

    @Override
    protected boolean doParse(Lexer lexer, List<ASTree> res) throws ParseException {
        return program.doParse(lexer, res) && lexer.peek(0) == Token.EOF;
    }

    public static void main(String[] args) {
//        List<String> data = new ArrayList<>();
//
//        data.add("90 + 1");
//        data.add("95.56");
//        data.add("13 > 9");
//        data.add("13 >= 9");
//        data.add("13 < 9");
//        data.add("13 <= 9");
//        data.add("57 * 9 -2");
//        data.add("1 - 3*5");
//        data.add("1 - 3*5==     90");
//        data.add("name = 89 +8 * 5.5");
//        data.add("result = 89 +8 * 5.5 >= 95");
//        data.add("result = -89 +8 * -5.5 >= 95");
//        data.add("result = value.write()");
//        data.add("result = value.doSth(sth1, sth2)");
//        data.add("result = value.doSth(45, 777777)");
//        data.add("result = var1.innerData[1].write()");
//        data.add("result = var1.innerData[\"invokable\"].method(arg1, arg2)");
//        data.add("result = pro =  var1.innerData[\"invokable\"].method(arg1, arg2)");
//
//        Parser parser = new RockParser();
//
//
//        data.forEach(s -> {
//            Lexer lexer = new Lexer(new StringReader(s));
//            try {
//                System.out.println(s + "        " + parser.parse(lexer));
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//        });

        File file = new File("test/test2.roc");
        try (InputStreamReader reader = new InputStreamReader(new FileInputStream(file), "UTF-8")) {
            LineReader lr = new LineReader(file.getName(), reader);
            Lexer lexer = new Lexer(lr);

            RockParser parser = new RockParser();
            ASTree ast = parser.parse(lexer);
            System.out.println(ast);
            NestedEnvironment env = new NestedEnvironment();
            env.set("print", new RockFunction("print", new String[]{"obj"}, new Evaluator() {
                @Override
                public Rock eval(Environment env, Rock base) throws RockException {
                    System.out.println(env.get("obj").asString());
                    return RockNil.INSTANCE;
                }

                @Override
                public Rock set(Environment env, Rock base, Rock value) throws RockException {
                    return RockNil.INSTANCE;
                }
            }, env));
            System.out.println(ast.eval(env, null));
            //System.out.println(parser.parse(lexer).eval(new NestedEnvironment(), null));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
