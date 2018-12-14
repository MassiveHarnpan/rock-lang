package sample;

import rock.RockVirtualMachine;
import rock.data.Rock;
import rock.exception.RockException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class ConsoleMain extends RockVirtualMachine {

    static BufferedReader reader;
    static RockVirtualMachine rvm = new ConsoleMain();

    public static void main(String[] args) throws IOException, RockException {
        initialize();

        String line;



        rvm.onOutput(">>> ");
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if ("/exit".equals(line)) {
                break;
            }
            Rock r = rvm.evalLine(line);
            if (r == null) {
                rvm.onOutput("... ");
            } else {
                rvm.onOutput(">=> " + r + '\n');
                rvm.onOutput(">>> ");
            }
        }
    }

    public static void initialize() {
        rvm.init();
        try {
            reader = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onException(RockException e) {
        e.printStackTrace();
    }

    @Override
    public void onLog(String msg) {
        System.out.println(msg);
    }

    @Override
    public void onOutput(String msg) {
        System.out.print(msg);
    }

    @Override
    public String onInput() {
        try {
            return reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
