package rock.ast;

import rock.token.Token;

public interface ASTLeafFactory {

    ASTLeaf create(Token token);

}
