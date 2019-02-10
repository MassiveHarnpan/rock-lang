package rock.vsiualmachine;

import java.util.ArrayList;
import java.util.List;

import static rock.vsiualmachine.Instruction.*;

import static rock.util.ByteArrayUtil.*;

public class RockVisualMachine {

    // To store data
    private byte[] heap = new byte[1024];
    // To store operands
    private byte[] stack = new byte[1024];
    // To store program
    private byte[] program = new byte[1024];
    // Thr Program Counter
    private int pc;
    // Thr Stack Pointer
    private int sp;
    // Instruction Register
    private int ir;


    public void run() {
        while (pc >= 0 || pc >= program.length) {
            byte instructionHead = program[pc++];
            int param = 0;
            Instruction instruction = Instruction.values()[instructionHead];
            if (instruction.param) {
                param = readInt(heap, pc);
                pc += 4;
            }

        }
    }



    public static void main(String[] args) {
        testDouble();
    }

    public static void testInt() {
        RockVisualMachine rvm = new RockVisualMachine();

        List<Integer> data = new ArrayList<>();
        data.add(0);
        data.add(1);
        data.add(2);
        data.add(3);
        data.add(9512);
        data.add(-1);
        data.add(-95);
        data.add(Integer.MAX_VALUE);
        data.add(Integer.MAX_VALUE - 1);
        data.add(Integer.MAX_VALUE + 1);
        data.add(Integer.MIN_VALUE);

        for (Integer integer : data) {
            int i = integer;
            System.out.println("----------------");
            System.out.println("Encode: " + i);
            writeInt(rvm.stack, 0, i);
            System.out.println("Bytes:  " + bytesToString(rvm.stack, 4));
            int result = readInt(rvm.stack, 0);
            System.out.println("Decode: " + result);
            writeInt(rvm.stack, 0, result);
            System.out.println("Bytes:  " + bytesToString(rvm.stack, 4));
        }
    }


    public static void testDouble() {
        RockVisualMachine rvm = new RockVisualMachine();

        List<Double> data = new ArrayList<>();
        data.add(0.0);
        data.add(-0.0);
        data.add(1.0);
        data.add(-1.0);
        data.add(0.123);
        data.add(-0.123);
        data.add(-1456.0);
        data.add(1456.0);
        data.add(3.1415926535);
        data.add(Math.PI);
        data.add(Math.E);
        data.add(Double.MAX_VALUE);
        data.add(Double.MIN_VALUE);
        data.add(Double.NaN);
        data.add(Double.POSITIVE_INFINITY);
        data.add(Double.NEGATIVE_INFINITY);

        for (Double value : data) {
            Double.toString(9.0);
            double d = value;
            System.out.println("----------------");
            System.out.println("Encode: " + d);
            writeDouble(rvm.stack, 0, d);
            System.out.println("Bytes:  " + bytesToString(rvm.stack, 8));
            double result = readDouble(rvm.stack, 0);
            System.out.println("Decode: " + result);
            writeDouble(rvm.stack, 0, result);
            System.out.println("Bytes:  " + bytesToString(rvm.stack, 8));
        }
    }

    public static void testInstruction() {

    }
}
