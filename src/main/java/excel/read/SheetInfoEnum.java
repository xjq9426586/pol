package excel.read;

/**
 * @Auther: xujunqian
 * @Date: 2019/10/17 0017 17:27
 * @Description:
 */
public enum SheetInfoEnum {
    YSZK("应收帐款明细表", 5, 9,
            new String[]{"xm", "hs", "je", "bl", "bj", "bz"}),
    CWFY("财务费用明细表", 2, 3,
            new String[]{"xm", "jr"});

    private String type;
    private int startIndex;
    private int endIndex;
    private String[] names;

    SheetInfoEnum(String type, int startIndex, int endIndex, String[] names) {
        this.type = type;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.names = names;
    }

    public String getType() {
        return type;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }

    public String[] getNames() {
        return names;
    }

    public static void main(String[] args) {
        SheetInfoEnum[] entities = SheetInfoEnum.values();
        for (SheetInfoEnum entity : entities) {
            System.out.println(entity.type);
        }
    }
}
