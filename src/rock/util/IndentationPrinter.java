package rock.util;

import rock.data.internal.RockString;

import java.util.List;
import java.util.Objects;

public class IndentationPrinter {

    private StringBuilder builder;
    private String indentation;
    private int level;

    public IndentationPrinter() {
        this("    ", 0);
    }

    public IndentationPrinter(int level) {
        this("    ", level);
    }

    public IndentationPrinter(String indentation) {
        this(indentation, 0);
    }

    public IndentationPrinter(String indentation, int level) {
        builder = new StringBuilder();
        this.indentation = indentation;
        this.level = level;
    }


    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int levelUp() {
        return ++level;
    }

    public int leveldown() {
        return --level;
    }



    public IndentationPrinter print(Object obj) {
        String s = Objects.toString(obj);
        String[] ss = s.split("\r?\n");
        for (int i = 0; i < ss.length; i++) {
            builder.append(ss[i]);
            if (i < ss.length - 1) {
                newLine();
                printIndentation();
            }
        }
        return this;
    }

    public IndentationPrinter newLine() {
        builder.append('\n');
        return this;
    }

    public IndentationPrinter ln() {
        newLine();
        printIndentation();
        return this;
    }

    // 与System.out.println()不同，这个是先另起一行，然后打印内容
    public IndentationPrinter println(Object obj) {
        newLine();
        printIndentation();
        print(Objects.toString(obj));
        return this;
    }

    private void printIndentation() {
        for (int i = 0; i < level; i++) {
            print(indentation);
        }
    }

    @Override
    public String toString() {
        return builder.toString();
    }
}
