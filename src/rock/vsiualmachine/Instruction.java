package rock.vsiualmachine;

public enum Instruction {

    I_LOAD(0x00, "iload", true),
    I_SAVE(0x01, "isave", true),
    I_ADD(0x02, "iadd", false);





    public final byte code;
    public final String name;
    public final boolean param;

    Instruction(byte code, String name, boolean param) {
        this.code = code;
        this.name = name;
        this.param = param;
    }

    Instruction(int code, String name, boolean param) {
        this((byte) code, name, param);
    }
}
