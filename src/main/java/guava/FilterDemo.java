package guava;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

public class FilterDemo {
	   public static void main(String[] args) {
	       List<String> list= Lists.newArrayList("moon","dad","refer","son");
	       Collection<String> palindromeList= Collections2.filter(list, Predicates.containsPattern("o"));
	       List<String> list1=new ArrayList<String>(palindromeList);
	       System.out.println(list);
	       System.out.println(list1);
	   }
	}
