package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import rock.*;
import rock.ast.ASTree;
import rock.exception.RockException;
import rock.parser.BasicParser;
import rock.parser.Parser;
import rock.runtime.NativeEvaluator;
import rock.runtime.NativeFunction;

import java.io.StringReader;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private TextArea tarCode;
    @FXML
    private TextArea tarParser;
    @FXML
    private TextArea tarConsole;


    private Lexer lexer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            //Function println = new NativeFunction("println", System.out, System.out.getClass().getDeclaredMethod("println", Object.class));
            //runtime.put(println.name(), println);
            //Function print = new NativeFunction("print", System.out, System.out.getClass().getDeclaredMethod("print", Object.class));
            //runtime.put(print.name(), print);
            Function currentTimeMillis = new NativeFunction("currentTimeMillis", System.class.getDeclaredMethod("currentTimeMillis"));
            runtime.put("currentTimeMillis", currentTimeMillis);

            runtime.put("print", new Function("print", new String[]{"msg"}, new NativeEvaluator() {
                @Override
                public Object eval(Environment env) throws RockException {
                    tarConsole.appendText(String.valueOf(env.get("msg")));
                    return null;
                }
            }, runtime));
            runtime.put("println", new Function("print", new String[] {"msg"}, new NativeEvaluator() {
                @Override
                public Object eval(Environment env) throws RockException {
                    tarConsole.appendText("\n" + String.valueOf(env.get("msg")));
                    return null;
                }
            }, runtime));
            runtime.put("ln", new Function("print", new String[] {}, new NativeEvaluator() {
                @Override
                public Object eval(Environment env) throws RockException {
                    tarConsole.appendText("\n");
                    return null;
                }
            }, runtime));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    private Parser parser = new BasicParser();
    private Environment runtime = new Environment();
    @FXML
    public void run(){
        System.out.println("Code runs");
        lexer = new Lexer(new StringReader(tarCode.getText()));
        clearConsole();
        try {
            ASTree ast = parser.parse(lexer);
            if (ast == null) {
                tarParser.setText("#Failed\n");
            } else {
                ast = ast.simplify();
                tarParser.setText("#Succeed\n");
                tarParser.appendText(ast.toString()/*.replace("\n", "#EOF")*/+'\n');
                outputParseResult(ast, 0);
            }
            tarParser.appendText("\n------------------------\n");
            String rst = String.valueOf(ast.eval(new Environment(runtime)));
            tarConsole.appendText("\n=> " + rst);
        } catch (RockException e) {
            e.printStackTrace();
            tarParser.appendText("\n" + e.getMessage());
        }
    }

    private void outputParseResult(ASTree ast, int indent) {
        StringBuffer sb = new StringBuffer();
        for (int i = 1; i < indent; i++) {
            sb.append("| ");
        }
        sb.append("|-  ");
        if (ast.isLeaf()) {
            sb.append(ast.toString().replace("\n", "#EOL")).append('\n');
            tarParser.appendText(sb.toString());
            return;
        }
        for (int i = 0; i < ast.childCount(); i++) {
            outputParseResult(ast.child(i), indent + 1);
        }
    }

    @FXML
    public void stop() {
        System.out.println("Code stops");
    }

    @FXML
    public void clearCode() {
        tarCode.setText("");
    }

    @FXML
    public void clearConsole() {
        tarParser.setText("");
    }

    @FXML
    public void onKey(KeyEvent event) {
        if (event.isControlDown()) {
            switch (event.getCode()) {
                case SPACE: run(); break;
                case C: stop(); break;
            }
        }
    }

}
