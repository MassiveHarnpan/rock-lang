package rock.ast;

import rock.data.Environment;
import rock.data.Rock;
import rock.data.internal.RockDecimal;
import rock.data.internal.RockInteger;
import rock.data.internal.RockString;
import rock.exception.RockException;
import rock.token.Token;
import rock.util.IndentationPrinter;
import rock.util.Logger;

public class ASTLeaf extends ASTree {

    public static final ASTLeafFactory FACTORY = t -> new ASTLeaf(t);

    @Override
    public boolean isLeaf() {
        return true;
    }

    @Override
    public Token token() {
        return token;
    }

    @Override
    public ASTree child(int i) {
        return null;
    }

    @Override
    public int childCount() {
        return 0;
    }

    private Rock cache = null;

    @Override
    public Rock eval(Environment env, Rock base) throws RockException {
        Object value = token.value();
        Logger.log(value + " instanceof "+value.getClass().getSimpleName());
        if (cache != null) {
            return cache;
        }
        switch (token.type()) {
            case INTEGER: cache = new RockInteger(token.getInt()); return cache;
            case DECIMAL: cache = new RockDecimal(token.getDec()); return cache;
            case STRING: cache = new RockString(token.getStr()); return cache;
        }
        throw new RockException("cannot get value of " + token);
    }

    @Override
    public Rock set(Environment env, Rock base, Rock value) throws RockException {
        throw new RockException("cannot set to a literal value: " + token);
    }


    private Token token;

    public ASTLeaf(Token token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return token.literal().replace("\n", "#EOL");
    }

    @Override
    public void write(IndentationPrinter printer) {
        printer.print(token.toString());
    }
}
