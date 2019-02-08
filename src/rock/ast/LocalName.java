package rock.ast;

import rock.token.Token;

public class LocalName extends Name {

    public static final ASTLeafFactory FACTORY = token -> new LocalName(token);

    public LocalName(Token token) {
        super(token);
    }


}
