package rock.parser.builder;

public class Element {


    public static final int AT_MOST_ONE = 1;
    public static final int AT_LEAST_ONE = 2;
    public static final int NO_LIMIT = 3;

    private String name;
    private boolean ast;
    private int countMode;

}
