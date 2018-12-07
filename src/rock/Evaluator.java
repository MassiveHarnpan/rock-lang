package rock;

public interface Evaluator {
    Object eval(Environment env) throws RockException;
    Object eval(Environment env, Object base) throws RockException;
}
