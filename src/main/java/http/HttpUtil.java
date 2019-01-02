package http;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;


/**
 * Created by Administrator on 2016/5/22.
 */
public class HttpUtil {
    public static byte[] get(String urlString) {
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            //设置请求方法
            urlConnection.setRequestMethod("GET");
            //设置超时时间
            urlConnection.setConnectTimeout(5000);
            urlConnection.setReadTimeout(3000);

            //获取响应的状态码
            int responseCode = urlConnection.getResponseCode();
            if (responseCode == 200) {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                InputStream in = urlConnection.getInputStream();
                byte[] buffer = new byte[4 * 1024];
                int len = -1;
                while((len = in.read(buffer)) != -1) {
                    bos.write(buffer, 0, len);
                }
                close(in);
                byte[] result = bos.toByteArray();
                close(bos);
                return result;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        return null;
    }

    private static void close(Closeable stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			//param = new String(param.getBytes("utf-8"));
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！"+e);
			e.printStackTrace();
		}
		//使用finally块来关闭输出流、输入流
		finally{
			try{
				if(out!=null){
					out.close();
				}
				if(in!=null){
					in.close();
				}
			}
			catch(IOException ex){
				ex.printStackTrace();
			}
		}
		return result;
	}

  
    public static void main(String[] args) {
    	String s=new String (HttpUtil.get("http://news-at.zhihu.com/api/4/news/latest"));
    	System.out.println(s);
    	//sendPost("http://192.168.31.227:8001/AQS_CDC/view/androidLogin.do","user.usrID=xmyd&user.usrPwd=123456");
//    	System.out.println(sendPost("http://127.0.0.1:8083/AQS_CDC/rec/receiveHttpData.do","key=8a1d30b31c388092561979effec61e95df12725a"));
//    	String s=new String (HttpUtil.get("http://127.0.0.1:8083/AQS_CDC/ext/supplier.do"));
//    	System.out.println(s);
	}
}