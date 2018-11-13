package rock.parser;

public class Precedence {

    private int level;
    private boolean leftAssoc;

    public Precedence(int level, boolean leftAssoc) {
        this.level = level;
        this.leftAssoc = leftAssoc;
    }
}
