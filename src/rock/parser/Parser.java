package rock.parser;

import rock.ast.ASTListFactory;
import rock.exception.ParseException;
import rock.lexer.Lexer;
import rock.ast.ASTList;
import rock.ast.ASTree;
import rock.exception.RockException;
import rock.parser.element.*;
import rock.token.Token;
import rock.token.TokenType;

import java.util.ArrayList;
import java.util.List;

public class Parser extends NonTerminalElement {

    private SequenceElement sequence = new SequenceElement();
    private String name;
    private boolean asAst = false;


    public Parser(ASTListFactory factory, String name) {
        super(factory);
        this.name = name == null ? getClass().getSimpleName() : name;
    }

    public Parser(ASTListFactory factory) {
        this(factory, null);
    }

    public Parser() {
        this(ASTList.FACTORY, null);
    }

    public SequenceElement getSequence() {
        return sequence;
    }

    private boolean forking = false;

    private void add(Element e) {
        if (forking) {
            sub.then(e);
        } else {
            sequence.then(e);
        }
    }

    public Parser then(Element e) {
        add(e);
        return this;
    }

    public Parser repeat(Element element, Element splitter, boolean atLeastOnce) {
        RepeatElement repeat = new RepeatElement(element, splitter);
        repeat.setAtLeastOneElement(atLeastOnce);
        add(repeat);
        return this;
    }

    public Parser repeat(Element element, boolean atLeastOnce) {
        RepeatElement repeat = new RepeatElement(element, null);
        repeat.setAtLeastOneElement(atLeastOnce);
        add(repeat);
        return this;
    }

    private ForkElement forks;
    private SequenceElement sub;

    public Parser fork() {
        forking = true;
        forks = new ForkElement();
        sub = new SequenceElement();
        return this;
    }

    public Parser or() {
        forks.or(sub);
        sub = new SequenceElement();
        return this;
    }

    public Parser merge() {
        forking = false;
        forks.or(sub);
        add(forks);
        forks = null;
        return this;
    }



    public Parser sep(String value) {
        add(new SepElement(value));
        return this;
    }

    public Parser name() {
        add(new TokenElement(TokenType.NAME));
        return this;
    }

    public Parser number() {
        add(new TokenElement(TokenType.NUMBER));
        return this;
    }

    public Parser string() {
        add(new TokenElement(TokenType.STRING));
        return this;
    }

    public Parser comment() {
        add(new TokenElement(TokenType.COMMENT));
        return this;
    }

    public Parser asAST() {
        asAst = true;
        return this;
    }

    public static MaybeElement maybe(Element element) {
        return new MaybeElement(element);
    }

    public static SkipElement skip(Element element) {
        return new SkipElement(element);
    }


    @Override
    protected boolean doParse(Lexer lexer, List<ASTree> res) throws ParseException {
        if (asAst) {
            List<ASTree> list = new ArrayList<>();
            if (sequence.parse(lexer, list)) {
                ASTList ast = getFactory().create(list.toArray(new ASTree[list.size()]));
                res.add(ast);
                return true;
            } else {
                return false;
            }
        } else {
            return sequence.parse(lexer, res);
        }
    }



    public static TokenElement sign(String... values) {
        return new TokenElement(TokenType.IDENTIFIER, values);
    }

    public static TokenElement split(String... values) {
        return new SepElement(values);
    }
}
