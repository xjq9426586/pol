package callBack;

public class Ricky implements Student {
	 String name="Ricky";
	 
    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
    public void resolveQuestion(Callback callback) {
    	
        // 回调，告诉老师作业写了多久
        callback.tellAnswer(3);
    }
 
}
