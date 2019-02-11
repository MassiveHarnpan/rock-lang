package rock.vsiualmachine;

import rock.util.ByteArrayUtil;

import java.io.PrintStream;
import java.util.*;

public class Memory {


    private class Fragment {
        int start;
        int end;
        boolean allocated;

        public Fragment(int start, int end, boolean allocated) {
            this.start = start;
            this.end = end;
            this.allocated = allocated;
        }

        int size() {
            return end - start;
        }
    }

    private byte[] data;

    public Memory(int bytes) {
        this.data = new byte[bytes];
        fragments.add(new Fragment(0, bytes, false));
    }


    private LinkedList<Fragment> fragments = new LinkedList<>();


    public int malloc(int size) {
        ListIterator<Fragment> itr = fragments.listIterator();
        while (itr.hasNext()) {
            Fragment fragment = itr.next();
            if (fragment.allocated || fragment.size() < size) {
                continue;
            }
            if (fragment.size() == size) {
                fragment.allocated = true;
            } else {
                int end = fragment.end;
                fragment.end = fragment.start + size;
                Fragment rest = new Fragment(fragment.end, end, fragment.allocated);
                fragment.allocated = true;
                itr.add(rest);
            }
            return fragment.start;
        }
        return -1;
    }

    public boolean free(int address) {
        ListIterator<Fragment> itr = fragments.listIterator();
        while (itr.hasNext()) {
            Fragment fragment = itr.next();
            if (fragment.start > address) {
                break;
            } else if (fragment.start < address){
                continue;
            }
            if (!fragment.allocated) {
                break;
            }
            fragment.allocated = false;
            return true;
        }
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
            if (random.nextBoolean()) {
                System.out.println("malloc: " + size);
                address = memory.malloc(size);
                System.out.println("get: " + address);
            } else {
                address = memory.fragments.get(random.nextInt(memory.fragments.size())).start;
                System.out.println("free: " + address);
                memory.free(address);
            }
            memory.print(System.out, 32);
            Thread.sleep(1000);
        } while (address >= 0 && address < memory.data.length);
    }

    public void print(PrintStream stream, int len) {

        for (int i = 0; i < len * 2 + 4; i++) {
            stream.print('-');
        }
        stream.println();

        Iterator<Fragment> itr = fragments.iterator();
        Fragment fragment = itr.next();
        int address = 0;
        while (address < data.length && fragment != null) {
            if (address % len == 0) {
                stream.print(format(address, 4));
                stream.print(' ');
            }

            stream.print(fragment.allocated ? '■' : '□');

            if (++address % len == 0 && fragment != null) {
                stream.println();
            }
            if (address >= fragment.end) {
                fragment = itr.hasNext() ? itr.next() : null;
            }
        }

        for (int i = 0; i < len * 2 + 4; i++) {
            stream.print('-');
        }
        stream.println();
    }

    public static String format(int integer, int len) {
        String s = String.valueOf(integer);
        StringBuilder builder = new StringBuilder();
        builder.append(s).reverse();
        for (int i = s.length(); i < len; i++) {
            builder.append(' ');
        }
        return builder.reverse().toString();
    }

}
