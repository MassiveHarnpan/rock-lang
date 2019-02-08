package rock.buildin;

import rock.data.Environment;
import rock.data.Evaluator;
import rock.data.EvaluatorAdapter;
import rock.data.Rock;
import rock.data.internal.RockDecimal;
import rock.data.internal.RockFunction;
import rock.data.internal.RockInteger;
import rock.data.internal.RockNil;
import rock.exception.RockException;

public class NativeMethods {

    public static final Evaluator PRINT = new EvaluatorAdapter() {
        @Override
        public Rock eval(Environment env, Rock base) throws RockException {
            System.out.println(env.get("msg").asString());
            return RockNil.INSTANCE;
        }
    };

    public static final Evaluator CURRENT_TIME_MILLIS = new EvaluatorAdapter() {
        @Override
        public Rock eval(Environment env, Rock base) throws RockException {
            return new RockDecimal(System.currentTimeMillis());
        }
    };


}
