package rock.runtime;

import rock.buildin.NativeMethods;
import rock.data.Environment;
import rock.data.NestedEnvironment;
import rock.data.internal.RockBoolean;
import rock.data.internal.RockClass;
import rock.data.internal.RockFunction;
import rock.data.internal.RockNil;
import rock.runtime.classes.ClassObject;

public class RuntimeEnviroumentFactory {


    public static Environment create() {
        NestedEnvironment global = new NestedEnvironment();

        global.set("true", RockBoolean.TRUE);
        global.set("false", RockBoolean.FALSE);
        global.set("nil", RockNil.INSTANCE);

        global.set("print", new RockFunction("print", new String[]{"msg"}, NativeMethods.PRINT, global));
        global.set("time", new RockFunction("time", new String[0], NativeMethods.CURRENT_TIME_MILLIS, global));

        global.set("Object", ClassObject.create());

        return global;
    }

}
