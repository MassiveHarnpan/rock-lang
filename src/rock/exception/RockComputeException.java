package rock.exception;

import rock.data.Rock;

public class RockComputeException extends RockException {

    public static String toStr(Rock former, String op, Rock later) {
        return "Cannot compute: " + former + " " + op + " " + later;
    }

    private String op;
    private Rock former;
    private Rock later;

    public RockComputeException(Rock former, String op, Rock later) {
        super(toStr(former, op, later));
        this.former = former;
        this.op = op;
        this.later = later;
    }

    public String getOp() {
        return op;
    }

    public Rock getFormer() {
        return former;
    }

    public Rock getLater() {
        return later;
    }
}
