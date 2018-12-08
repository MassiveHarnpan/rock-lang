package rock.parser;

import rock.Lexer;
import rock.exception.RockException;
import rock.ast.ASTList;
import rock.ast.ASTree;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

public abstract class NonTerminalParser extends Parser {

    protected List<ASTree> trees = new ArrayList<>();

    protected Class<? extends ASTList> as;

    public NonTerminalParser(Class<? extends ASTList> as) {
        this.as = as;
    }

    protected ASTList create(ASTree... children) throws RockException {
        //Logger.log(getName() + " is creating " + as.getName());
        try {
            Constructor<? extends ASTList> constructor = as.getConstructor(ASTree[].class);
            if (constructor != null) {
                //Logger.log("get constructor");
                ASTList asl = constructor.newInstance((Object) children);
                //Logger.log(getName() + " created " + asl.getClass().getSimpleName());
                //Logger.log(asl + " is instance of Expr = " + (asl instanceof Expr));
                return asl;
            }
            constructor = as.getConstructor();
            if (constructor != null) {
                ASTList asl = constructor.newInstance();
                asl.append(children);
                return asl;
            }
            throw new RockException("Unable to construct ASTList");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RockException(e.getMessage());
        }
    }

    @Override
    public ASTree parse(Lexer lexer) throws RockException {
        List<ASTree> res = new ArrayList<>();
        if (parse(lexer, res)) {
            return create(res.toArray(new ASTree[res.size()]));
        }
        return null;
    }
}
