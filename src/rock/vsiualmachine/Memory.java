package rock.vsiualmachine;

import rock.util.ByteArrayUtil;

import java.io.PrintStream;
import java.io.Writer;
import java.lang.reflect.Method;
import java.util.*;

import static rock.util.ByteArrayUtil.*;

public class Memory {


    private class Fragment {
        int address;
        int size;
        int end;

        public Fragment(int address, int size) {
            this.address = address;
            this.size = size;
            this.end = address + size - 1;
        }

        public boolean isInRange(int a) {
            return a >= address && a < address + size;
        }

    }

    private byte[] data;

    public Memory(int bytes) {
        this.data = new byte[bytes];
        fragments.add(new Fragment(0, bytes));
    }


    private List<Fragment> fragments = new ArrayList<>();

    public int malloc(int size) {
        if (fragments.isEmpty()) {
            return -1;
        }
        for (int i = 0; i < fragments.size(); i++) {
            Fragment fragment = fragments.get(i);
            if (fragment.size >= size) {
                int restSize = fragment.size - size;
                if (restSize > 0) {
                    Fragment rest = new Fragment(fragment.address + size, restSize);
                    fragments.set(i, rest);
                } else {
                    fragments.remove(i);
                }
                return fragment.address;
            }
        }
        return -1;
    }


    public boolean free(int address, int size) {
        // TODO
        return false;
    }

    public void writeInt(int index, int value) {
        ByteArrayUtil.writeInt(data, index, value);
    }

    public int readInt(int index) {
        return ByteArrayUtil.readInt(data, index);
    }

    public void writeDouble(int index, double value) {
        ByteArrayUtil.writeDouble(data, index, value);
    }

    public double readDouble(int index) {
        return ByteArrayUtil.readDouble(data, index);
    }


    public static void main(String[] args) throws InterruptedException {
        Memory memory = new Memory(16 * 16);
        int address;
        Random random = new Random();
        do {
            int size = random.nextInt(7) + 1;
            System.out.println("malloc: " + size);
            address = memory.malloc(size);
            System.out.println("get: " + address);
            memory.print(System.out, 32);
            Thread.sleep(1000);
        } while (address >= 0 && address < memory.data.length);
    }

    public void print(PrintStream stream, int len) {
        Iterator<Fragment> itr = fragments.iterator();

        for (int i = 0; i < len * 2; i++) {
            stream.print('-');
        }
        stream.println();

        int address = 0;
        Fragment fragment = null;
        while (address < data.length) {
            for (int i = 0; i < len && address < data.length; i++, address++) {
                if (fragment == null || address >= fragment.end) {
                    fragment = itr.hasNext() ? itr.next() : null;
                }
                //System.out.println(fragment != null ? fragment.address +":" + fragment.size : "Alloced");
                stream.print((fragment != null && fragment.isInRange(address)) ? '□' : '■');
            }
            stream.println();
        }

        for (int i = 0; i < len * 2; i++) {
            stream.print('-');
        }
        stream.println();
    }

}
