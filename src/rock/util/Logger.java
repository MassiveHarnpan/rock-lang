package rock.util;

public class Logger {


    private static boolean show = true;

    public static void log(Object msg) {
        if (show) {
            System.out.println(msg);
        }
    }

    public static void setShow(boolean show) {
        Logger.show = show;
    }
}
