package wiki;

import java.util.HashMap;
import java.util.Map;

/**
 * 有10个阶梯，每次走1个或者2个阶梯，有几种走法
 * @author dell
 *
 */
public class dp {
	//递归不行
	public static int getclimWay(int n,HashMap<Integer, Integer> map){
		if(n==0){
			return 0;
		}
		if(n==1){
			return 1;
		}
		if(n==2){
			return 2;
		}
		else{
			int value=getclimWay(n-1,map)+getclimWay(n-2,map);
			return value;
		}
		
		
	}
	//最优
	public static int getclimWay(int n){
		int a=1;
		int b=2;
		int temp=0;
		if(n==0){
			return 0;
		}
		if(n==1){
			return a;
		}
		if(n==2){
			return b;
		}
		for (int i=3;i<=n;i++){
			temp=a+b;
			a=b;
			b=temp;
		}
		return temp;
	}

	public static void main(String[] args) {
		HashMap<Integer, Integer> map=new HashMap<>();
		 long startTime=System.currentTimeMillis();
		System.out.println(getclimWay(50));
		long endTime=System.currentTimeMillis();
		float excTime=(float)(endTime-startTime)/1000;
	       System.out.println("执行时间："+excTime+"s");
	}
}
