package rock.exception;

public class LexerException extends Exception {

    public static String location(String msg, int lineNumber, int offsetNumber) {
        return msg + " at (" + lineNumber + ":" + offsetNumber + ")";
    }



    private int lineNumber;
    private int offsetNumber;
    private String msg;

    public LexerException(String msg, int lineNumber, int offsetNumber) {
        super(location(msg, lineNumber, offsetNumber));
        this.lineNumber = lineNumber;
        this.offsetNumber = offsetNumber;
        this.msg = msg;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public int getOffsetNumber() {
        return offsetNumber;
    }

    public String getMsg() {
        return msg;
    }
}
