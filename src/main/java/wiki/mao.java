package wiki;

public class mao {
	public static void main(String[] args) {
		int a[]={3,7,5,8,1};
		for (int i : maopao(a)) {
			System.out.println(i);
		}
		
	}
	
	
	
	public static int[] maopao(int a[]){
		for(int i=0;i<a.length;i++){
			for(int j=0;j<a.length-1;j++){
				if(a[j]==a[j+1]){
					int temp=a[j];
					a[j]=a[j+1];
					a[j+1]=temp;
				}
			}
		}
		return a;
	}
}
