package test;

public class test3 {
	public static void main(String[] args) throws Exception {
		String s = "hello";
		char[] temp = s.toCharArray();
		StringBuffer sb = new StringBuffer(s);
//		for (int i = temp.length-1; i >= 0; i--) {
//			sb.append(temp[i]);
//		}
		System.out.println(sb.reverse());
	}
}
