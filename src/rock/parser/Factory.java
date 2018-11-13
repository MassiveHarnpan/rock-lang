package rock.parser;

import rock.RockException;
import rock.ast.ASTLeaf;
import rock.ast.ASTList;
import rock.ast.ASTree;
import rock.token.Token;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class Factory {

    public static ASTLeaf makeLeaf(Class<ASTLeaf> clazz, Token token) throws RockException {
        Constructor<ASTLeaf> constructor;
        try {
            constructor = clazz.getConstructor(Token.class);
        } catch (NoSuchMethodException e) {
            throw new RockException(e);
        }
        try {
            constructor.newInstance(token);
        } catch (Exception e) {
            throw new RockException(e);
        }
        return null;
    }

    public static ASTList makeList(Class<ASTList> clazz, ASTree... trees) throws RockException {
        Constructor<ASTList> constructor;
        try {
            constructor = clazz.getConstructor(ASTree[].class);
        } catch (NoSuchMethodException e) {
            throw new RockException(e);
        }
        try {
            constructor.newInstance((Object) trees);
        } catch (Exception e) {
            throw new RockException(e);
        }
        return null;
    }

}
