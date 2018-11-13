package rock.parser;


import rock.RockException;
import rock.ast.ASTLeaf;
import rock.ast.ASTList;
import rock.ast.ASTree;
import rock.Lexer;
import rock.token.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Parser {

    private static int i = 0;


    protected List<Element> elements = new ArrayList<>();

    public Parser(Element... elements) {
        this.elements.addAll(Arrays.asList(elements));
    }



    public ASTree parse(Lexer lexer) throws RockException {
        i ++ ;
        StringBuffer ind = new StringBuffer();
        for (int j = 0; j < i; j++) {
            ind.append(" |");
        }
        String indent = ind.toString();
        System.out.println(indent+"Parsing "+getClass().getSimpleName());
        int check = lexer.pointer();

        List<ASTree> results = new ArrayList<>();
        for (Element element : elements) {
            if (element.match(lexer)) {
                element.parse(lexer, results);
            } else {
                System.out.println(indent+"parse failed");
                lexer.recovery(check);
                return null;
            }
        }
        ASTree[] asts = new ASTree[results.size()];
        ASTree ast = Factory.makeList(ASTList.class, results.toArray(asts));
        System.out.println(indent+"parse succeeded: "+ast);
        i--;
        return ast;
    }

    public boolean match(Lexer lexer) throws RockException {
        return elements.isEmpty() ? true : elements.get(0).match(lexer);
    }



    protected Element fork(Parser... parsers) {
        return new ForkElement(parsers);
    }
    protected Element ast(Parser parser) {
        return new TreeElement(parser);
    }
    protected Element num() {
        return new TokenElement(ASTLeaf.class, TokenType.NUMBER);
    }
    protected Element str() {
        return new TokenElement(ASTLeaf.class, TokenType.STRING);
    }
    protected Element id(Object... values) {
        return new TokenElement(ASTLeaf.class, TokenType.IDENTIFIER, values);
    }
    protected Element skip(Object... values) {
        return new SkipElement(values);
    }
    protected Element end() {
        return new EndElement();
    }



    protected Parser then(Element element) {
        elements.add(element);
        return this;
    }



}
