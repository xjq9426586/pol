package callBack;

public class bo implements Student {
    String name = "bo";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void resolveQuestion(Callback callback) {
        callback.tellAnswer(5);

    }

}
