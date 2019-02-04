package rock.parser;

import rock.ast.*;
import rock.exception.ParseException;
import rock.lexer.Lexer;
import rock.parser.element.Element;
import rock.parser.element.ForkElement;
import rock.parser.element.TokenElement;
import rock.token.Token;
import rock.token.TokenType;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class RockParser extends Parser {
    public RockParser() {
        super(ASTList.FACTORY, "rock");

        Element name = new TokenElement(TokenType.NAME);
        Element number = new TokenElement(TokenType.NUMBER);
        Element string = new TokenElement(TokenType.STRING);
        Element comment = new TokenElement(TokenType.COMMENT);

        Element boolOps = sign(">", "<", ">=", "<=", "==", "!=", "&", "|");
        Element termOps = sign("+", "-");
        Element factorOps = sign("*", "/", "^", "..");

        Element primary = new ForkElement()
                .or(name)
                .or(number)
                .or(string);

        ForkElement valuable = new ForkElement();

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

        Parser assign = new Parser(AssignStmt.FACTORY, "assign").then(settable).sep("=").then(valuable).asAST();

        valuable.or(assign).or(expr);

        this.fork()
                .then(assign).or()
                .then(valuable)
                .merge();

    }

    @Override
    protected boolean doParse(Lexer lexer, List<ASTree> res) throws ParseException {
        return super.doParse(lexer, res) && lexer.peek(0) == Token.EOF;
    }

    public static void main(String[] args) {
        List<String> data = new ArrayList<>();

        data.add("90 + 1");
        data.add("95.56");
        data.add("13 > 9");
        data.add("13 >= 9");
        data.add("13 < 9");
        data.add("13 <= 9");
        data.add("57 * 9 -2");
        data.add("1 - 3*5");
        data.add("1 - 3*5==     90");
        data.add("name = 89 +8 * 5.5");
        data.add("result = 89 +8 * 5.5 >= 95");
        data.add("result = -89 +8 * -5.5 >= 95");
        data.add("result = value.toString()");
        data.add("result = value.doSth(sth1, sth2)");
        data.add("result = value.doSth(45, 777777)");
        data.add("result = var1.innerData[1].toString()");
        data.add("result = var1.innerData[\"invokable\"].method(arg1, arg2)");
        data.add("result = pro =  var1.innerData[\"invokable\"].method(arg1, arg2)");

        Parser parser = new RockParser();


        data.forEach(s -> {
            Lexer lexer = new Lexer(new StringReader(s));
            try {
                System.out.println(s + "        " + parser.parse(lexer));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });
    }
}
