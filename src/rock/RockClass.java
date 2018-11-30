package rock;

public class RockClass {

    private String name;
    private Evaluator body;

    public RockClass(String name, Evaluator body) {
        this.name = name;
        this.body = body;
    }

    public String name() {
        return name;
    }

    public Evaluator body() {
        return body;
    }

    public Object create(Environment env, Object... args) {
        return null;
    }

}
