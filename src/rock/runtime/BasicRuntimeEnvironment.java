package rock.runtime;

import rock.data.*;
import rock.data.internal.*;
import rock.exception.RockException;
import rock.util.Logger;

public class BasicRuntimeEnvironment extends NestedEnvironment {

    public final RockClass BASIC_ROCK_OBJECT_CLASS;



    public BasicRuntimeEnvironment() {

        BASIC_ROCK_OBJECT_CLASS = new RockClass("Object", null, new NativeEvaluator() {
            @Override
            public Rock eval(Environment env) throws RockException {
                env.set("toString", new RockFunction("toString", new String[0], null, null) {
                    @Override
                    public Rock invoke(Rock... args) throws RockException {
                        return new RockString(env.toString());
                    }
                });
                return null;
            }
        }, this);

        this.set(BASIC_ROCK_OBJECT_CLASS.name(), BASIC_ROCK_OBJECT_CLASS);

        this.set("false", RockInteger.FALSE);
        this.set("true", RockInteger.TRUE);
        this.set("nil", RockNil.INSTANCE);


        RockFunction currentTimeMillis = null;
        try {
            currentTimeMillis = new NativeFunction("currentTimeMillis", System.class.getDeclaredMethod("currentTimeMillis"));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        this.set("currentTimeMillis", currentTimeMillis);
        this.set("print", new RockFunction("print", new String[]{"msg"}, new NativeEvaluator() {
            @Override
            public Rock eval(Environment env) throws RockException {
                System.out.println(String.valueOf(env.get("msg")));
                return null;
            }
        }, this));
        this.set("println", new RockFunction("println", new String[] {"msg"}, new NativeEvaluator() {
            @Override
            public Rock eval(Environment env) throws RockException {
                System.out.println("\n" + String.valueOf(env.get("msg")));
                return null;
            }
        }, this));
        this.set("ln", new RockFunction("ln", new String[] {}, new NativeEvaluator() {
            @Override
            public Rock eval(Environment env) throws RockException {
                System.out.println("\n");
                return null;
            }
        }, this));
        this.set("show_log", new RockFunction("show_log", new String[] {"stat"}, new NativeEvaluator() {
            @Override
            public Rock eval(Environment env) throws RockException {
                Rock r = env.get("stat");
                boolean show = !(r == null || RockInteger.FALSE.equals(r));
                Logger.setShow(show);
                return null;
            }
        }, this));
        this.set("exit", new RockFunction("exit", new String[0], new NativeEvaluator() {
            @Override
            public Rock eval(Environment env) throws RockException {
                System.exit(0);
                return null;
            }
        }, this));
    }
}
