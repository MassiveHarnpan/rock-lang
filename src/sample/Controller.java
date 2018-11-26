package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import rock.Lexer;
import rock.RockException;
import rock.ast.ASTree;
import rock.parser.BasicParser;
import rock.parser.Parser;

import java.io.StringReader;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private TextArea tarCode;
    @FXML
    private TextArea tarConsole;


    private Lexer lexer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    private Parser parser = new BasicParser();
    @FXML
    public void run(){
        System.out.println("Code runs");
        lexer = new Lexer(new StringReader(tarCode.getText()));
        clearConsole();
        try {
            ASTree ast = parser.parse(lexer);
            if (ast == null) {
                tarConsole.setText("#Failed\n");
            } else {
                tarConsole.setText("#Succeed\n");
                tarConsole.appendText(ast.toString().replace("\n", "#EOF")+'\n');
                outputParseResult(ast, 0);
            }
        } catch (RockException e) {
            e.printStackTrace();
            tarConsole.appendText("\n" + e.getMessage());
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
            tarConsole.appendText(sb.toString());
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
        tarConsole.setText("");
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
