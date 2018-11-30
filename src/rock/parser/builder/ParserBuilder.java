package rock.parser.builder;

import rock.parser.Parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ParserBuilder {

    private Stack<List<String>> patterns = new Stack<>();

    public ParserBuilder() {
        this.patterns.add(new ArrayList<>());
    }

    public Parser build() {
        return null;
    }

    public ParserBuilder then(String name) {
        patterns.peek().add(name);
        return this;
    }


}
