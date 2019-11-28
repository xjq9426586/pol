package xml;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Auther: xujunqian
 * @Date: 2019/11/26 0026 17:12
 * @Description: ftp工具类
 */
public class FtpUtil {
	private static final Logger logger = LoggerFactory.getLogger(FtpUtil.class);

	//TODO 从数据表取ip port username password
	public static FTPClient connect(String businessKey){
		FTPClient ftp = new FTPClient();
		return ftp;
	}
	/**
	 *
	 * @Author xujunqian
	 * @Description 连接ftp服务器
	 * @Date 2019/11/27 0027
	 * @Param [host, port, username, password]
	 *FTP服务器hostname,FTP服务器端口,FTP登录账号,FTP登录密码
	 * @return org.apache.commons.net.ftp.FTPClient
	**/
	public static FTPClient connect(String host, int port, String username, String password){
		FTPClient ftp = new FTPClient();
		try {
			ftp.connect(host, port);
			ftp.login(username, password);// 登录
			String str = ftp.getReplyString();//返回字符串
			int reply = ftp.getReplyCode();//返回码
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				logger.error("FTP " + str);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ftp;
	}

	/**
	 *
	 * @Author xujunqian
	 * @Description 断开连接
	 * @Date 2019/11/27 0027
	 * @Param [ftp]
	 * @return void
	**/
	public static void disconnect(FTPClient ftp){
		try {
			ftp.logout();
			ftp.disconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 *
	 * @Author xujunqian
	 * @Description 获取ftp文件文本
	 * @Date 2019/11/27 0027
	 * @Param [ftp, remotePath, fileName]
	 * FTPClient对象,FTP服务器上的相对路径,下载的文件名
	 * @return java.lang.String
	**/
	public static String getFileStr(FTPClient ftp, String remotePath, String fileName) {
		InputStream is = getFileStream(ftp, remotePath, fileName);
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		Stream<String> result = br.lines();
		return result.collect(Collectors.joining());
	}

	/**
	 *
	 * @Author xujunqian
	 * @Description 获取ftp文件流
	 * @Date 2019/11/27 0027
	 * @Param [ftp, remotePath, fileName]
	 * FTPClient对象,FTP服务器上的相对路径,下载的文件名
	 * @return java.lang.String
	 **/
	public static InputStream getFileStream(FTPClient ftp, String remotePath, String fileName){
		byte[] fileByte = getFileByte(ftp, remotePath, fileName);
		return new ByteArrayInputStream(fileByte);
	}

	/**
	 *
	 * @Author xujunqian
	 * @Description 下载文件
	 * @Date 2019/11/27 0027
	 * @Param [ftp, remotePath, fileName, localPath]
	 * FTPClient对象,FTP服务器上的相对路径,下载的文件名,下载路径
	 * @return boolean
	**/
	public static boolean downloadFile(FTPClient ftp, String remotePath, String fileName, String localPath) {
		boolean result = false;
		BufferedOutputStream bos = null;
		try {
			bos =  new BufferedOutputStream(new FileOutputStream(localPath + fileName));
			bos.write(getFileByte(ftp, remotePath, fileName));
			result = true;
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				bos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	/**
	 *
	 * @Author xujunqian
	 * @Description 获取ftp文件 byte
	 * @Date 2019/11/27 0027
	 * @Param [ftp, remotePath, fileName, localPath]
	 * FTPClient对象,FTP服务器上的相对路径,下载的文件名,下载路径
	 * @return boolean
	 **/
	public static byte[] getFileByte(FTPClient ftp, String remotePath, String fileName){
		ByteArrayOutputStream bos = null;
		try {
			String filePath = new String((remotePath + fileName).getBytes("gb2312"), StandardCharsets.ISO_8859_1);
			InputStream is = ftp.retrieveFileStream(filePath);
			BufferedInputStream bis = new BufferedInputStream(is);
			bos = new ByteArrayOutputStream();
			byte[] buf = new byte[1024];
			int length;
			while ((length = bis.read(buf)) != -1) {
				bos.write(buf, 0, length);
			}
			bos.close();
			ftp.completePendingCommand();
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("FTP " + ftp.getReplyString(), e);
	}
		return bos.toByteArray();
	}
	public static void main(String[] args) {
		FTPClient ftp = connect("127.0.0.1", 21, "user", "123456");
		InputStream is = getFileStream(ftp,  "", "we.xml");
		System.out.println(XmlUtil.xmlToMap(is));

		InputStream is2 = getFileStream(ftp,  "", "we.txt");
		System.out.println(TextUtil.textSplit(is2, "\n", "\\|"));

		System.out.println(getFileStr(ftp,  "logs/", "web_info.log"));

		downloadFile(ftp,  "logs/tansun-tcp-app-pc/", "tansun-tcp-app-pc-msdp.log", "C:\\");
		disconnect(ftp);
	}
}
