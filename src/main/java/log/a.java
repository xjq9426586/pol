package log;

public class a implements Ia {
    private String s;

    @Override
    public void sys() {
        String s = "农药残留#蔬菜类#<5.0#%抑制率#合格#GB/T 5009.199-2003#STD-9000#2018-10-08 10:50:05#1f198cfe90259b37#50.0%#123s #|";
        Integer.parseInt(s);
        s = "123";
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

}
