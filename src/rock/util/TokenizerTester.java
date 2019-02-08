package rock.util;

import rock.lexer.Tokenizer;
import rock.token.Token;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

public class TokenizerTester {

    public static void test(List<String> data, Tokenizer tokenizer) {
        data.forEach(s -> {
            System.out.println("--------------------------------");
            LineReader reader = null;
            try {
                reader = new LineReader(new StringReader(s));
            } catch (IOException e) {
                e.printStackTrace();
            }
            //System.out.println("Tokenizing: ");
            System.out.println(s.replaceAll("\n", "#EOL#"));

            Token result = tokenizer.next(reader);
            if (result == null) {
                //System.out.println("Tokenization failed!");
            } else {
                //System.out.println("Tokenization succeeded:");
                System.out.println(result.toString().replaceAll("\n", "#EOL#"));
            }
        });
    }

}
