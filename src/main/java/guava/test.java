package guava;

import com.google.common.base.Objects;
import com.google.common.base.Optional;

public class test {
	
	public static void main(String[] args) {
		String data = "酱油氨基酸态氮#酱油(1204)#22.6#mg/kg#合格#GB2760-2011#A #2014-06-16 15:49:00#513877caa4f1055d#material-20";
		String text[] = data.split("#");
		System.out.println(text[7]);
	}
	
	
	class person{
		private int id;
		private String name;
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		
	}
	
}
