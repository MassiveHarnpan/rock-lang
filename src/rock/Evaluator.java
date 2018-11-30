package rock;

public interface Evaluator {
    Object eval(Environment env) throws RockException;
}
