package socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;


public class StringUtil {
	
	//该方法将数据转为16进制显示  
	public static String byteToHexStr(byte[] bArray,int dataLen, boolean format) {  
		StringBuffer strb = new StringBuffer(bArray.length);  
		String str;  
		for (int i = 0; i < dataLen; i++) {  
			str = Integer.toHexString(0xFF & bArray[i]).trim();  
			if (str.length() < 2){  
				str = "0" + str;  
			}  
			if (format){  
				str += " ";  
			}  
			strb.append(str);  
		}  
		str = strb.toString().toUpperCase().trim();  
		return str;  
    }
    
	public static byte[] hexStrToByteArr (String str) {
    	byte[] bArr = str.getBytes();
    	int bArrLen = bArr.length;
    	
    	byte[] rBArr = new byte[bArrLen/2];
    	
    	for (int i = 0; i < bArrLen; i = i + 2) {
    		String strTmp = new String(bArr, i, 2);
    		rBArr[i / 2] = (byte) Integer.parseInt(strTmp, 16);
    	}
    	return rBArr;
    }
	
	/**
	 * 将16进制的字符串转换为对应的ASCII字符
	 * @param hex {String}
	 * @return {String}
	 */
	public static String convertHexToString(String hex){
		hex = hex.replaceAll(" ", "");
		StringBuilder sb = new StringBuilder();
        
		// 将16进制的字符串两两成对的取转换
		for( int i=0; i<hex.length()-1; i+=2 ){
			// 获取成对的16进制字符 
			String output = hex.substring(i, (i + 2));
			// 转换为10进制
			int decimal = Integer.parseInt(output, 16);
			// 转换十进制成字符串 
			sb.append((char)decimal);
		}
    
		return sb.toString();
	}
	

	/**
	 * 将字符串中连着的多个空格转换为只有一个空格；
	 * 并可通过replaceSpecToChar参数设置将空格最终替换为另一个字符
	 * @param str {String}
	 * @param replaceSpecToChar {String}
	 * @return
	 */
	public static String replaceCharMoreToOne (String str, char c, String replaceSpecToChar) {
		
		if(str == null) {
			return null;
		}
		
		char[] cdn = str.toCharArray();
		boolean isSpec = false;
		StringBuffer sb = new StringBuffer();
		
		for (int i=0, len=cdn.length; i<len; i++) {
			char _c = cdn[i];
			// 判断是否是空格,
			if (_c == c) {
				if  (!isSpec) { // 并且是第一次出现
					sb.append(replaceSpecToChar==null?_c:replaceSpecToChar);
					isSpec = true;
				} else {
					continue;
				}
			} else {
				sb.append(_c);
				isSpec = false;
			}
		}
			
		return sb.toString();
	}
		
	public static String replaceStartAndEndStr (String str, String oldstr, String newstr) {
		
		if (str != null) {
			// str = str.replace(oldstr, newstr);
			StringBuffer sb = new StringBuffer(str);
			
			if (sb.indexOf(oldstr) == 0) { // 替换开头
				sb.replace(0, oldstr.length(), newstr);
			}
			
			int len = sb.length();
			if (len > 1) {
				int lastIndex = sb.lastIndexOf(oldstr);
				if (lastIndex == len-1) {
					sb.deleteCharAt(lastIndex).append(newstr);
				}
				str = sb.toString();
			} else if (len == 1 && str.equals(oldstr)) {
				str = newstr;
			} else {
				str = sb.toString();
			}
		}
		
		return str;
	}

 	/**
 	 * 变量编码转换
 	 * @param p 要转码的字符串
 	 * @param c 转码方式
 	 * @return
 	 */
 	public static String changeURLCode(String p, String c) {
 		try {
			p = URLDecoder.decode(p, c);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
 		return p;
 	}
 	
 	//判断字符串是否为空
	public static boolean isBlank(Object str){
          return str==null || "".equals(str.toString().trim());
	}
	
 	
 	public static String getUTF8Code(String str) throws UnsupportedEncodingException{
		if(!isBlank(str) && java.nio.charset.Charset.forName("ISO-8859-1").newEncoder().canEncode(str)){
			 return new String(str.getBytes("ISO-8859-1"),"UTF-8");
		}
       return str;
	}
 	
 	
 	public static void main(String[] args) {
//		System.out.println(convertHexToString("0A 01 0A 11 0D"));
//		System.out.println(StringUtil.hexStrToByteArr((new SimpleDateFormat("yyyyMMddHHmmss")).format(new Date())));
// 		System.out.println(TCPSocketThreadAlpa.WISHT10_TCP_THREE_WAY_HANDSHAKE.replace(" ", "").substring(0,8) + encode("20140211170500") + TCPSocketThreadAlpa.WISHT10_TCP_THREE_WAY_HANDSHAKE.replace(" ", "").substring(8,10));
// 		System.out.println(TCPSocketThreadAlpa.WISHT10_TCP_THREE_WAY_HANDSHAKE.replace(" ", "").substring(0,8) + encode("20140211170500") + TCPSocketThreadAlpa.WISHT10_TCP_THREE_WAY_HANDSHAKE.replace(" ", "").substring(8,10));
// 		System.out.println(TCPSocketThreadAlpa.WISHT10_TCP_THREE_WAY_HANDSHAKE.replace(" ", "").substring(8,10));
// 		32 30 31 34 30 34 30 34 31 36 32 34 32 36
 		System.out.println(encode("高官厚爵"));
 		System.out.println(hexStringToString("50 4F 53 54 20 2F 61 6E 64 72 6F 69 64 2F 65 71 75 69 70 6D 65 6E 74 2F 75 70 6C 6F 61 64 47 70 73 2E 64 6F 20 48 54 54 50 2F 31 2E 31 0D 0A 43 6F 6E 74 65 6E 74 2D 4C 65 6E 67 74 68 3A 20 32 37 37 37 0D 0A 43 6F 6E 74 65 6E 74 2D 54 79 70 65 3A 20 74 65 78 74 2F 70 6C 61 69 6E 3B 20 63 68 61 72 73 65 74 3D 75 74 66 2D 38 0D 0A 48 6F 73 74 3A 20 36 31 2E 31 33 33 2E 31 39 35 2E 31 39 38 3A 39 30 38 31 0D 0A 43 6F 6E 6E 65 63 74 69 6F 6E 3A 20 4B 65 65 70 2D 41 6C 69 76 65 0D 0A 43 6F 6F 6B 69 65 32 3A 20 24 56 65 72 73 69 6F 6E 3D 31 0D 0A 41 63 63 65 70 74 2D 45 6E 63 6F 64 69 6E 67 3A 20 67 7A 69 70 0D 0A 43 6F 6F 6B 69 65 3A 20 4A 53 45 53 53 49 4F 4E 49 44 3D 33 30 43 33 31 34 32 41 31 38 37 34 34 35 35 41 36 30 30 30 45 37 41 37 35 44 42 36 44 34 34 39 0D 0A 0D 0A"));
 		String date=DateUtil.getSysDateContinuous();
		String hexDate=StringUtil.encode(date);
 		System.out.println(hexDate);
	}
 	
 	private static String hexString="0123456789ABCDEF"; 
 	
 	/* 
 	* 将字符串编码成16进制数字,适用于所有字符（包括中文） 
 	*/ 
 	public static String encode(String str) { 
	 	//根据默认编码获取字节数组 
	 	byte[] bytes=str.getBytes(); 
	 	StringBuilder sb=new StringBuilder(bytes.length*2); 
	 	//将字节数组中每个字节拆解成2位16进制整数 
	 	for(int i=0;i<bytes.length;i++) { 
		 	sb.append(hexString.charAt((bytes[i]&0xf0)>>4)); 
		 	sb.append(hexString.charAt((bytes[i]&0x0f)>>0)); 
	 	} 
	 	return sb.toString(); 
 	} 
	
 	
 	/**
 	 * 字符串转码
 	 * @param str 要转码的字符串
 	 * @param code 转码前字符集
 	 * @param coded 目标字符集
 	 * @return
 	 */
 	public static String changeCode(String str,String code,String coded){
 		if(isBlank(str)){
 			return null;
 		}
 		String target=null;
 		try {
			target=new String(str.getBytes(code),coded);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
 		return target;
 	}
 	
 	/**
 	 * 项目中特定常量字段，由数字状态转换成字符串
 	 * @return
 	 */
 	public static Object convertConstValue(Integer intValue,Object[] valueResult){
 		if(isBlank(intValue)||isBlank(valueResult)||valueResult.length<=intValue){
 			return "";
 		}
 		return valueResult[intValue];
 		
 	}
 	
 	
 	public static String printParameter(HttpServletRequest request){

		Map<String, String[]>parame=request.getParameterMap();
		StringBuffer sb= new StringBuffer();
		sb.append("{\n");
		for (Entry<String, String[]> entry : parame.entrySet()) {
			sb.append(" \n");
			sb.append("\""+entry.getKey()+"\""+":[");
			for(String v:entry.getValue()){
				sb.append("\""+v+"\"");
			}
			sb.append("],");
		}
		sb.append("\n}");
		return sb.toString();
 	}
 	
 	 public static String hexStringToString(String s) {  
         if (s == null || s.equals("")) {  
             return null;  
         }  
         s = s.replace(" ", "");  
         byte[] baKeyword = new byte[s.length() / 2];  
         for (int i = 0; i < baKeyword.length; i++) {  
             try {  
                 baKeyword[i] = (byte) (0xff & Integer.parseInt(  
                         s.substring(i * 2, i * 2 + 2), 16));  
             } catch (Exception e) {  
                 e.printStackTrace();  
             }  
         }  
         try {  
             s = new String(baKeyword, "utf-8");  
             new String();  
         } catch (Exception e1) {  
             e1.printStackTrace();  
         }  
         return s;  
     }  
 	 public static String convertStreamToString(InputStream is) throws IOException {
		 BufferedReader in = new BufferedReader(new InputStreamReader(is));
		    StringBuffer buffer = new StringBuffer();
		    String line = "";
		    while ((line = in.readLine()) != null){
		      buffer.append(line);
		    }
		    return buffer.toString();
	 }
 	 
 	public static String bytesToHexString(byte[] src){
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }
 	public static String process(InputStream in, String charset) {
 	    byte[] buf = new byte[1024];
 	    StringBuffer sb = new StringBuffer();
 	    int len = 0;
 	    try {
 	        while ((len=in.read(buf)) != -1) {
 	            sb.append(new String(buf, 0, len, charset));
 	        }
 	    } catch (IOException e) {
 	        e.printStackTrace();
 	    }
 	    return sb.toString();
 	}
 	
}
