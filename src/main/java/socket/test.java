package socket;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;





public class test extends Thread {
	
	private static final int SERVER_PORT = 55533; 
	private int count = 0; // ���ӿͻ�����
	private ServerSocket ss = null; // �����socket
	
	public test(){
		try {
			if(null==ss){
				this.ss = new ServerSocket(SERVER_PORT);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		System.out.println("-------�����߳�������������----------");
		try {
			while(true){
				Socket client = ss.accept();
				count += 1;
				Thread c_thread = new Thread(new SocketThread(client, count));
				c_thread.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	class SocketThread extends Thread{
		private Socket client = null;
		private int count = 0; // ���ӿͻ�����
		private PrintWriter writer;
		
		public SocketThread(Socket client, int count){  
	        this.client = client;
	        this.count = count;
	    }
		public void run() { 
			byte[] head = new byte[512];
			boolean flag = true;
			InputStream din = null;
			OutputStream dout = null;
			 
			try {
				dout = new DataOutputStream(client.getOutputStream());  
		        
				while (flag) {
					// ��ȡ��ǰ���Ӷ����������  
					din = client.getInputStream();
					
					int headLen=din.read(head);
					System.out.println("=========" + count + "==========");
					String str = printHexString(head,headLen);
					System.out.println(str);
					String opt = str.substring(8, 10);
					System.out.println(opt);
					if(opt.equals("02")){
						String date=getSysTime();
						//String hexDate=StringUtil.encode(date);
						String str1 = "574B200002"+date+"0000AA";
						byte[] hexDate = hexStringToBytes(str1);
						String str11 = printHexString(hexDate,hexDate.length);
						
						System.out.println( str11+ " "+hexDate.length);
						//writer.println(hexDate);
					
						dout.write(hexDate);
						dout.flush();
					
					}else{
						String str1_hand = "574B0800";
						String str1_end  = "0000AA";
						
						System.out.println("d i=");
						byte[] hexDate = hexStringToBytes(str1_hand+opt+ str1_end);
						String str11 = printHexString(hexDate,hexDate.length);
						
						System.out.println( str11+ " "+hexDate.length);
						//writer.println(hexDate);
							dout.write(hexDate);
							dout.flush();
						
					}
				}
			} catch (Exception e) {  
				e.printStackTrace();
			} finally {
				try {
					if(dout!=null)dout.close();
					if(client!=null)client.close();
					if(din!=null)din.close();
					writer.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		}
	}
	
	public static void main(String[] args) {
		test tcp=new test();
		tcp.start();
	}
	public   String printHexString( byte[] b,int len) {
	
		String a = "";
		  for (int i = 0; i < len; i++) { 
		    String hex = Integer.toHexString(b[i] & 0xFF); 
		    if (hex.length() == 1) { 
		      hex = '0' + hex; 
		    }
		   
		    a = a+hex;
		  } 		  
		       return a;
		}
	public static byte[] hexStringToBytes(String hex) {
		  if( (hex) == "" || hex.length() %2> 0 ) {  
			  System.out.println("hex2Bytes: invalid HEX string:" + hex );  
	            return null;  
	        }  
	        int len = hex.length() / 2;  
	        byte[] ret = new byte[ len ];  
	        int k = 0;  
	        for (int i = 0; i < len; i++) {  
	            int c = hex.charAt(k++);  
	            if( c>='0'&& c<='9' )  
	                c = c-'0';  
	            else if( c>='a'&& c<='f' )  
	                c = c-'a'+ 10;  
	            else if( c>='A'&& c<='F' )  
	                c = c-'A'+ 10;  
	            else {  
	            	System.out.println("hex2Bytes: invalid HEX string:" + hex );  
	                return null;  
	            }  
	            ret[i]= (byte)(c<<4);  
	            c = hex.charAt(k++);  
	            if( c>='0'&& c<='9' )  
	                c = c-'0';  
	            else if( c>='a'&& c<='f' )  
	                c = c-'a'+ 10;  
	            else if( c>='A'&& c<='F' )  
	                c = c-'A'+ 10;  
	            else {  
	            	System.out.println("hex2Bytes: invalid HEX string:" + hex );  
	                return null;  
	            }  
	            ret[i]+= (byte)c;  
	            System.out.println("hex2Bytes: " + ret[i] +i );  
	            
	        }  
	        return ret;  
		
    }
    private static int charToByte(char c) {
    	int d = 0;
    	if ((c >= '0') && (c <= '9'))  
            d=(int)(c - '0');  
        if ((c >= 'A') && (c <= 'F'))  
            d=(int)(c - 'A' + 10);  
        if ((c >= 'a') && (c <= 'f'))  
            d=(int)(c - 'a' + 10);  
        return d;
    	/*
    	int a;
    	a = c-'0';
    	if(a >9)
    		a -=7;
    	System.out.println("a="+a);
        return (byte)a;
        */
    }
    
    public static String byteArrayToStr(byte[] byteArray) {
        if (byteArray == null) {
            return null;
        }
        String str = new String(byteArray);
        return str;
    }
    public static byte[] strToByteArray(String str) {
        if (str == null) {
            return null;
        }
        byte[] byteArray = str.getBytes();
        return byteArray;
    }
    
    public static String  getSysTime(){
    	String  datetime = "";
		
		Calendar c = Calendar.getInstance();//���Զ�ÿ��ʱ���򵥶��޸�   

		int year = c.get(Calendar.YEAR);  

		 int month = c.get(Calendar.MONTH);   

		int date = c.get(Calendar.DATE);    

		int hour = c.get(Calendar.HOUR_OF_DAY);   

		int minute = c.get(Calendar.MINUTE);   

		int second = c.get(Calendar.SECOND);    
		String info = String.format("%08d%08d%08d%08d%08d%08d",year , month, date , hour,minute,second);
		System.out.println(info);
		return info;
    	
    }
}
