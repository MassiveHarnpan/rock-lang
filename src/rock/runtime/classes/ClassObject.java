package rock.runtime.classes;

import rock.data.Environment;
import rock.data.Evaluator;
import rock.data.EvaluatorAdapter;
import rock.data.Rock;
import rock.data.internal.*;
import rock.exception.RockException;
import rock.util.Logger;

public class ClassObject extends RockClass {

    public static ClassObject create() {
        return new ClassObject();
    }

    private ClassObject() {
        super("Object", null, null, null);
    }

    @Override
    public void initObject(RockObject ro) throws RockException {
        Environment env = ro.env();
        env.set("toString", new RockFunction("toString", new String[0], new EvaluatorAdapter() {
            @Override
            public Rock eval(Environment env, Rock base) throws RockException {
                return new RockString(ro.asString());
            }
        }, env));
    }
}
