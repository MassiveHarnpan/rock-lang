package rock.ast;

import rock.token.Token;

public class ASTLeaf extends ASTree {
    @Override
    public boolean isLeaf() {
        return true;
    }

    @Override
    public ASTree child(int i) {
        return null;
    }

    @Override
    public int childCount() {
        return 0;
    }

    private Token token;

    public ASTLeaf(Token token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return token.literal();
    }
}
