package rock.ast;

import rock.Environment;
import rock.exception.RockException;
import rock.exception.UnsupportedOperationException;
import rock.util.Logger;

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
    public Object eval(Environment env) throws RockException {
        Object leftVal = left().eval(env);
        Object rightVal = right().eval(env);
        return operate(leftVal, operator(), rightVal);
    }

    public static Object operate(Object leftVal, String operator, Object rightVal) throws RockException{
        Logger.log("# left = " + leftVal);
        Logger.log("# right = " + rightVal);
        if (leftVal instanceof Number) {
            Number left = (Number) leftVal;
            if (rightVal instanceof Number) {
                Number right = (Number) rightVal;
                //region number ops
                switch (operator) {
                    case "+":
                        return left.intValue() + right.intValue();
                    case "-":
                        return left.intValue() - right.intValue();
                    case "*":
                        return left.intValue() * right.intValue();
                    case "/":
                        return left.intValue() / right.intValue();
                    case "%":
                        return left.intValue() % right.intValue();
                    case "==":
                        return left.intValue() == right.intValue() ? 1 : 0;
                    case "!=":
                        return left.intValue() != right.intValue() ? 1 : 0;
                    case ">":
                        return left.intValue() > right.intValue() ? 1 : 0;
                    case "<":
                        return left.intValue() < right.intValue() ? 1 : 0;
                    case ">=":
                        return left.intValue() >= right.intValue() ? 1 : 0;
                    case "<=":
                        return left.intValue() <= right.intValue() ? 1 : 0;
                }
                //endregion
            } else if (rightVal instanceof String) {
                String right = (String) rightVal;
                switch (operator) {
                    case "+": return left + right;
                    case "*": return repeat(right, left.intValue());
                }
            }
        } else {
            String left = leftVal.toString();
            if ("+".equals(operator)) {
                return left + rightVal;
            } else if ("-".equals(operator)){
                return remove(left, rightVal.toString());
            }
            if (rightVal instanceof Number) {
                Number right = (Number) rightVal;
                if ("*".equals(operator)) {
                    return repeat(left, right.intValue());
                }
            }
        }
        throw new UnsupportedOperationException(operator, leftVal.toString(), rightVal.toString());
    }

    public static String repeat(String s, int times) {
        StringBuffer sb = new StringBuffer();
        if (times < 0) {
            s = new StringBuffer().append(s).reverse().toString();
        }
        while (times-- > 0) {
            sb.append(s);
        }
        return sb.toString();
    }

    public static String remove(String s, String sub) {
        return s.replaceAll(sub, "");
    }
}
