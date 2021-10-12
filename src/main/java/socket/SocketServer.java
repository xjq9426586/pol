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
import java.util.Date;


public class SocketServer extends Thread {

    private static final int SERVER_PORT = 8125;
    private int count = 0; // 连接客户端数
    private ServerSocket ss = null; // 服务端socket

    public SocketServer() {
        try {
            if (null == ss) {
                this.ss = new ServerSocket(SERVER_PORT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        System.out.println("-------监听线程留样冰箱启动----------");
        try {
            while (true) {
                Socket client = ss.accept();
                count += 1;
                Thread c_thread = new Thread(new SocketThread(client, count));
                c_thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class SocketThread extends Thread {
        private Socket client = null;
        private int count = 0; // 连接客户端数
        private PrintWriter writer;

        public SocketThread(Socket client, int count) {
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
                    // 获取当前链接对象的输入流
                    din = client.getInputStream();

                    int headLen = din.read(head, 0, 30);
                    System.out.println("=========" + count + "==========");
                    String str = StringUtil.byteToHexStr(head, headLen, false);
                    System.out.println(str);
                    String date = DateUtil.getSysDateStr();
                    String hexDate = StringUtil.encode(date);
                    dout.write(StringUtil.hexStrToByteArr(hexDate));
                    //writer.println(dout);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (dout != null) dout.close();
                    if (client != null) client.close();
                    if (din != null) din.close();
                    //writer.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public static void main(String[] args) {
        SocketServer tcp = new SocketServer();
        tcp.start();
    }
}
