package callBack;

import org.junit.Test;

public class test {
	 @Test
	    public void testCallback() {
	        Student student = new Ricky();
	        Teacher teacher = new Teacher(student);
	 
	        teacher.askQuestion();
	 
	    }
}
