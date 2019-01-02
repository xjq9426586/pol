package test;

import java.util.Map;

import com.google.common.collect.Maps;

import net.sf.json.JSONObject;

public class test {
	public static void main(String[] args) {
//		Object s[] = {"ss","adc"};
//		Object value[]={123,null};
//		Map<String,Object> o=new HashMap<String, Object>();
//		for (Object str : s) {
//			
//			for (Object object : value) {
//				if(!o.containsValue(object)&&!o.containsKey(str)){
//					o.put(str.toString(), object);
//				}
//				
//			}
//		}
//		System.out.println(o);
		String str = "ww,lll;,op";
		String[] sp = str.split(",");
		Map<String, Object> map=Maps.newHashMap();
		map.put("w", new String[]{"1","3"});
		System.out.println(JSONObject.fromObject(map));
		
		
		String s="123#mg%kg";
		System.out.println(s.split("#")[1]);
		System.out.println("15:02".indexOf("15:02"));
		int n = 6;
		 n |= n >>> 1;
		 System.out.println(n);
//		List<String> list = new ArrayList<String>();
//		list.add("1");
//		list.add("2");
//		for (String item : list) {
//		if ("2".equals(item)) {
//		list.remove(item);
//		}
//		}
//		System.err.println(list);
		new test().new b().p();
	}
	abstract class a{
		protected int age=12;
		public abstract void p();
	}
	class b extends a{

		@Override
		public void p() {
			System.out.println(this.age);
		}
		
	}
}
