package rock.util;

import rock.ast.Array;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.*;

public class LineReader {

    private String name;
    private Queue<Reader> readers = new LinkedList<>();
    private List<String> lines = new ArrayList<>();
    private String line;
    private int lineNumber = 0;
    private int lineOffset = 0;
    private boolean eof = false; // 流结尾标记，若为真，则不能添加新的Reader

    public LineReader(String name, Reader... readers) throws IOException {
        this.name = name;
        this.readers.addAll(Arrays.asList(readers));
        load();
    }

    public LineReader(Reader... readers) throws IOException {
        this("anonymous_input", readers);
    }


    public void setEof(boolean eof) {
        this.eof = eof;
    }

    public boolean concat(Reader reader) {
        return eof ? false : readers.add(reader);
    }

    // 从流中载入所有代码
    public boolean load() throws IOException {
        int lineCount = lines.size();
        while (!readers.isEmpty()) {
            Reader reader = readers.poll();
            LineNumberReader lineNumberReader =
                    reader instanceof LineNumberReader
                            ? (LineNumberReader) reader
                            : new LineNumberReader(reader);
            String line;
            while ((line = lineNumberReader.readLine()) != null) {
                lines.add(line);
            }
        }
        reset(lineNumber, lineOffset);
        return lineCount < lines.size();
    }

    // 从指定位置开始，将已载入的内容之后的部分抛弃，包括当前内容也放弃
    // 并将指针移至指定位置
    public List<String> abandon(int lineNumber, int lineOffset) {
        List<String> strings = new ArrayList<>();
        if (!reset(lineNumber, lineOffset)) {
            return strings;
        }
        if (line == null) {
            return strings;
        }
        while (lineNumber + 1 < lines.size()) {
            strings.add(lines.remove(lineNumber + 1));
        }
        if (lineOffset >= line.length()) {
            return strings;
        }
        String former = line.substring(0, lineOffset);
        String later = line.substring(lineOffset);
        line = former;
        if (!former.isEmpty())  {
            lines.set(lineNumber, former);
        } else {
            lines.remove(lineNumber);
        }
        if (!later.isEmpty()) {
            strings.add(0, later);
        }
//
//        System.out.println(lines);
//        System.out.println(strings);
//        System.out.println(checkPoint());
        return strings;
    }

    public List<String> abandon(Pos pos) {
        return abandon(pos.lineNumber, pos.lineOffset);
    }







    public String getLine(int index) {
        return lines.get(index);
    }


    public int getLineNumber() {
        return lineNumber;
    }

    public int getLineOffset() {
        return lineOffset;
    }

    public String getName() {
        return name;
    }


    public Pos checkPoint() {
        return new Pos(name, lineNumber, lineOffset);
    }


    public boolean reset(Pos pos) {
        return reset(pos.lineNumber, pos.lineOffset);
    }

    public boolean reset(int lineNumber, int lineOffset) {
        if (lineNumber > lines.size() || (lineNumber == lines.size() && lineOffset > 0)) {
            return false;
        }
        String line = lineNumber < lines.size() ? lines.get(lineNumber) : null;
        if (line != null && lineOffset > line.length()) {
            return false;
        }
        this.lineNumber = lineNumber;
        this.lineOffset = lineOffset;
        this.line = lineNumber >= lines.size() ? null : lines.get(lineNumber);
        return true;
    }

    public boolean reset(int lineNumber) {
        return reset(lineNumber, 0);
    }

    public boolean reset() {
        return reset(0, 0);
    }


    /**
     * 截取子串
     * @param startLineNumber 起始行号（包含）
     * @param startLineOffset 起始偏移（包含）
     * @param endLineNumber 结束行号（包含)
     * @param endLineOffset 结束偏移（不包含）
     * @return 截取的子串
     */
    public String substring(int startLineNumber, int startLineOffset, int endLineNumber, int endLineOffset) {
        if (startLineNumber == endLineNumber) {
            return lines.get(lineNumber).substring(startLineOffset, endLineOffset);
        }
        StringBuilder builder = new StringBuilder();
        builder.append(lines.get(startLineNumber).substring(startLineOffset));
        for (int i = startLineNumber + 1; i <= endLineNumber - 1; i++) {
            builder.append(lines.get(i));
        }
        builder.append(lines.get(endLineNumber), 0, endLineOffset);
        return builder.toString();
    }

    public String substring(int startLineNumber, int startLineOffset) {
        return substring(startLineNumber, startLineOffset, lineNumber, lineOffset);
    }

    public String substring(Pos start, Pos end) {
        return substring(start.lineNumber, start.lineOffset, end.lineNumber, end.lineOffset);
    }

    public String substring(Pos start) {
        return substring(start.lineNumber, start.lineOffset, lineNumber, lineOffset);
    }

    public boolean hasMore() {
        return line != null && lineOffset < line.length() + 1;
    }

    public boolean noMore() {
        return !hasMore();
    }

    // 如果读到行尾，则返回一个换行符
    public char read() {
        if (lineOffset >= line.length()) {
            reset(lineNumber + 1);
            return '\n';
        }
        char ch = line.charAt(lineOffset++);
        return ch;
    }

    public char peek() {
        if (lineOffset == line.length()) {
            return '\n';
        }
        return line.charAt(lineOffset);
    }

    public void skip(char... chs) {
        if (!hasMore()) {
            return;
        }
        while (hasMore() && match(peek(), chs)) {
            read();
        }
    }

    public boolean match(char ch, char... chs) {
        for (int i = 0; i < chs.length; i++) {
            if (ch == chs[i]) {
                return true;
            }
        }
        return false;
    }

    public static final char[] SPACE = {' ', '\r', '\n', '\t'};

    public void skipSpace() {
        skip(SPACE);
    }

    public boolean read(char... chs) {
        int number = lineNumber;
        int offset = lineOffset;
        if (hasMore() && match(read(), chs)) {
            return true;
        }
        reset(number, offset);
        return false;
    }

    public boolean read(String s) {
        int number = lineNumber;
        int offset = lineOffset;
        for (int i = 0; i < s.length(); i++) {
            char expect = s.charAt(i);
            if (!hasMore() || read() != expect) {
                reset(number, offset);
                return false;
            }
        }
        return true;
    }


    @Override
    public String toString() {
        return name + " at (" + lineNumber + ":" + lineOffset + ") of " + lines.size();
    }
}
