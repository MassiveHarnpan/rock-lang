package rock.util;

import rock.RockVirtualMachine;

public class Logger {

    private static RockVirtualMachine rvm;

    public static void setRVM(RockVirtualMachine rvm) {
        Logger.rvm = rvm;
    }

    private static boolean show = false;

    public static void log(Object msg) {
        if (show) {
            if (rvm == null) {
                System.out.println(msg);
            } else {
                rvm.onLog(msg.toString());
            }
        }
    }

    public static void setShow(boolean show) {
        Logger.show = show;
    }
}
