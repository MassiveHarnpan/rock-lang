package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import rock.Lexer;
import rock.RockException;
import rock.Token;

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

    @FXML
    public void run() throws RockException {
        System.out.println("Code runs");
        lexer = new Lexer(new StringReader(tarCode.getText()));
        clearConsole();
        Token token;
        do {
            token = lexer.read();
            tarConsole.appendText("\n" +token.getLineNumber() + ": " + token.literal().replace("\n", "#EOL"));
        } while (token != Token.EOF);
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

}
