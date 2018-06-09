package cn.edu.swpu.data_castle_new.util;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;

import java.io.IOException;
import java.util.Date;

public class SshUtil {
    public static String ip = "连接服务器的ip地址";
    public static String uploadPath = "/home/ubuntu/clf/testFile/";//上传文件的路径
    public static String username = "服务器的用户名";
    public static String password = "服务器的登录密码";
    public static boolean putFile(String localFilePath) throws IOException {
        Connection conn = new Connection(ip);//目标服务器地址
        conn.connect();
        boolean isAuthenticated = conn.authenticateWithPassword(username, password);
        if (isAuthenticated == false){
            throw new IOException("Authentication failed.");
        }

        SCPClient client = new SCPClient(conn);
//        client.get("目标服务器文件路径", "本服务器用来存放文件路径");//get方法用来将目标服务器的文件下载到本地服务器
        client.put(localFilePath,uploadPath+DateTimeUtil.convertDateToString(new Date()));//put方法用来将本地文件上传到目标服务器

        ch.ethz.ssh2.Session session = conn.openSession();
        conn.close();
        session.close();
        return true;
    }

//    public static void main(String[] args) {
//        try {
//            boolean re = putFile("/home/hk/IdeaProjects/zuoye5.zip");
//            System.out.println("re:"+re);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

}
