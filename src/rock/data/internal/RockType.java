package rock.data.internal;

public enum RockType {
    INT("integer"),
    DEC("decimal"),
    STR("string"),
    FUN("function"),
    CON("constructor"),
    OBJ("object"),
    CLS("class"),
    DIC("dictionary"),
    ARR("array"),
    NIL("nil");


    private String typeName;

    RockType(String typeName) {
        this.typeName = typeName;
    }
}
