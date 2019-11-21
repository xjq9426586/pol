package test;

import com.jeedev.msdp.utlis.DateUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class test3 {
	public static void main(String[] args) throws Exception {
//		String s = "hello";
//		char[] temp = s.toCharArray();
//		StringBuffer sb = new StringBuffer(s);
////		for (int i = temp.length-1; i >= 0; i--) {
////			sb.append(temp[i]);
////		}
//		System.out.println(sb.reverse());
		String date = "2018-11";
		System.out.println(DateUtil.addYear(date,-1));
	}
}
