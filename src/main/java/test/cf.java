package test;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;


public class cf {
	static boolean f = true;
	public static void main(String[] args) {
//		 student s = null;
//		 for (int i = 0; i < 10; i++) {
//			 s = new student();
//			System.out.println(s.hashCode());
//		}
//		Logger log = LoggerFactory.getLogger(cf.class);
//		String s = "农药残留#蔬菜类#<5.0#%抑制率#合格#GB/T 5009.199-2003#STD-9000#2018-10-08 10:50:05#1f198cfe90259b37#50.0%#123s #|";
//		try {
//			Integer.parseInt(s);
//		} catch (Exception e) {
//			log.error(e.getMessage(), e);
//		}
//		
//		String[] sp = s.split("#");
//		System.out.println(sp[sp.length-2]);
		int p = 2;
		int s = 2;
		List<Integer> i = new ArrayList<Integer>();
		i.add(1);
		i.add(2);
		i.add(3);
		i.add(4);
		System.out.println(StringUtils.join(i,","));
		System.out.println(i.subList((p-1)*s, p*s).toString());
//		Unit u = new Unit();
//		u.setId(1);
//		System.out.println(u);
		
	}
	public static int byteArrayToInt(byte[] bytes) {  
	    int value = 0;  
	    // 由高位到低位  
	    for (int i = 0; i < 4; i++) {  
	        int shift = (4 - 1 - i) * 8;  
	        value += (bytes[i] & 0x000000FF) << shift;// 往高位游  
	    }  
	    return value;  
	}  
}
