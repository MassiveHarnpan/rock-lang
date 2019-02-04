package rock.util;


public class Logger {


    private static boolean show = false;

    public static void log(Object msg) {
        if (show) {
            System.out.println(msg);
        }
    }

    public static void setShow(boolean show) {
        Logger.show = show;
    }
}
