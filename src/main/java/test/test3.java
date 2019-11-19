package test;

import org.apache.commons.lang3.StringUtils;

public class test3 {
	public static void main(String[] args) throws Exception {
//		String s = "hello";
//		char[] temp = s.toCharArray();
//		StringBuffer sb = new StringBuffer(s);
////		for (int i = temp.length-1; i >= 0; i--) {
////			sb.append(temp[i]);
////		}
//		System.out.println(sb.reverse());

		String str = "2018";
		String strs = "2019-2018";
		System.out.println(StringUtils.replace(strs, str, "p"));
		System.out.println(strs.replace(str,""));
	}
}
