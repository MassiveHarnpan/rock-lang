package rock.ast;

import rock.Enviroument;
import rock.token.NumToken;

public class BinaryExpr extends ASTList {

    public BinaryExpr(ASTree... children) {
        super(children);
    }

    public ASTree left() {
        return child(0);
    }

    public String operator() {
        return ((ASTLeaf) child(1)).token().literal();
    }

    public ASTree right() {
        return child(2);
    }

    @Override
    public Object eval(Enviroument env) {
        Object leftVal = left().eval(env);
        Object rightVal = right().eval(env);
        return operate(leftVal, operator(), rightVal);
    }

    public static Object operate(Object leftVal, String operator, Object rightVal) {
        switch (operator) {
            case "+": return ((Number) leftVal).intValue() + ((Number) rightVal).intValue();
            case "-": return ((Number) leftVal).intValue() - ((Number) rightVal).intValue();
            case "*": return ((Number) leftVal).intValue() * ((Number) rightVal).intValue();
            case "/": return ((Number) leftVal).intValue() / ((Number) rightVal).intValue();
            case "%": return ((Number) leftVal).intValue() % ((Number) rightVal).intValue();
            case "==": return ((Number) leftVal).intValue() == ((Number) rightVal).intValue() ? 1 : 0;
            case "!=": return ((Number) leftVal).intValue() != ((Number) rightVal).intValue() ? 1 : 0;
            case ">": return ((Number) leftVal).intValue() > ((Number) rightVal).intValue() ? 1 : 0;
            case "<": return ((Number) leftVal).intValue() < ((Number) rightVal).intValue() ? 1 : 0;
            case ">=": return ((Number) leftVal).intValue() >= ((Number) rightVal).intValue() ? 1 : 0;
            case "<=": return ((Number) leftVal).intValue() <= ((Number) rightVal).intValue() ? 1 : 0;
        }
        return null;
    }
}
