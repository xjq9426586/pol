package excel;

import com.jeedev.msdp.utlis.DateUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class test {
public static void main(String[] args) {
	List<String> list = new ArrayList<>();
	list.add("sss");
	System.out.println(list.indexOf("sss"));
	DateUtils.getBetweenDayNumber("2017-03-09", "2017-03-9");
	Employee e=new Employee();

	System.out.println(DateUtil.date(Long.parseLong("1573630912057")).toString());
}
}
