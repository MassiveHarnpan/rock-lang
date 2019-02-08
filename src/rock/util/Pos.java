package rock.util;

import java.util.Objects;

public class Pos {
    public final String name;
    public final int lineNumber;
    public final int lineOffset;

    public Pos(String name, int lineNumber, int lineOffset) {
        this.name = name;
        this.lineNumber = lineNumber;
        this.lineOffset = lineOffset;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Pos)) {
            return false;
        }
        Pos other = (Pos) obj;
        return
                Objects.equals(this.name, other.name)
                && this.lineNumber == other.lineNumber
                && this.lineOffset == other.lineOffset;
    }

    @Override
    public String toString() {
        return "(" + name + ":" + lineNumber + ":" + lineOffset + ")";
    }
}