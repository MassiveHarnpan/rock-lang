package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import rock.Lexer;
import rock.ast.ASTree;
import rock.data.Environment;
import rock.data.NestedEnvironment;
import rock.data.Rock;
import rock.data.internal.RockFunction;
import rock.exception.RockException;
import rock.parser.BasicParser;
import rock.parser.Parser;
import rock.runtime.BasicRuntimeEnvironment;
import rock.runtime.NativeEvaluator;

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
        //Function println = new NativeFunction("println", System.out, System.out.getClass().getDeclaredMethod("println", Object.class));
        //runtime.put(println.name(), println);
        //Function print = new NativeFunction("print", System.out, System.out.getClass().getDeclaredMethod("print", Object.class));
        //runtime.put(print.name(), print);
        runtime = new BasicRuntimeEnvironment();

        try {
            runtime.set("print", new RockFunction("print", new String[]{"msg"}, new NativeEvaluator() {
                @Override
                public Rock eval(Environment env) throws RockException {
                    tarConsole.appendText(String.valueOf(env.get("msg")));
                    return null;
                }
            }, runtime));
            runtime.set("println", new RockFunction("print", new String[]{"msg"}, new NativeEvaluator() {
                @Override
                public Rock eval(Environment env) throws RockException {
                    tarConsole.appendText("\n" + String.valueOf(env.get("msg")));
                    return null;
                }
            }, runtime));
            runtime.set("ln", new RockFunction("print", new String[]{}, new NativeEvaluator() {
                @Override
                public Rock eval(Environment env) throws RockException {
                    tarConsole.appendText("\n");
                    return null;
                }
            }, runtime));
        } catch (RockException e) {
            e.printStackTrace();
        }
    }

    private Parser parser = new BasicParser();
    private Environment runtime;
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
            Rock r = ast.eval(new NestedEnvironment(runtime));
            String rst = String.valueOf(r == null ? "<>" : r.toString());
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
                case Q: stop(); break;
            }
        }
    }

}
