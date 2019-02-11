package rock.buildin;

import rock.ast.ASTree;
import rock.data.Environment;
import rock.data.Evaluator;
import rock.data.EvaluatorAdapter;
import rock.data.Rock;
import rock.data.internal.RockDecimal;
import rock.data.internal.RockFunction;
import rock.data.internal.RockInteger;
import rock.data.internal.RockNil;
import rock.exception.RockException;
import rock.lexer.Lexer;
import rock.parser.RockParser;
import rock.runtime.RuntimeEnviroumentFactory;
import rock.util.LineReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

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

    public static final Evaluator IMPORT = new EvaluatorAdapter() {
        @Override
        public Rock eval(Environment env, Rock base) throws RockException {
            File file = new File(env.get("fileName").asString());
            try (InputStreamReader reader = new InputStreamReader(new FileInputStream(file), "UTF-8")) {
                LineReader lr = new LineReader(file.getName(), reader);
                Lexer lexer = new Lexer(lr);

                RockParser parser = new RockParser();
                ASTree ast = parser.parse(lexer);
                if (ast == null) {
                    return RockNil.INSTANCE;
                }
                Rock result = ast.eval(env, null);
                return result == null ? RockNil.INSTANCE : result;
            } catch (Exception e) {
                e.printStackTrace();
                return RockNil.INSTANCE;
            }
        }
    };


}
