package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    ServerSocket ss;

    public static void main(String args[]) {
        new Server();
    }

    public Server() {
        try {
            System.out.println("私聊服务器已运行,等待任意一个客户端连接,请先确定groupChat_Server是否运行。");

            ss=new ServerSocket(6666);

            while (true) {
                Socket s=ss.accept();

                ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
                User user=(User)ois.readObject();
                System.out.println("服务器接收到用户id:"+user.getUserId()+"  密码:"+user.getPasswd());
                Message message=new Message();

                ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());

                if(s!=null) {
                    System.out.println("成功登录主面板");

                    //返回一个成功登陆的信息报
                    message.setMesType(Message.MessageType.message_login_succeed);
                    oos.writeObject(message);

                    //单开一个线程，让该线程与该客户端保持通讯.
                    SerConClientThread scct=new SerConClientThread(s);
                    ManageClientThread.addClientThread(user.getUserId(), scct);
                    //启动与该客户端通信的线程.
                    scct.start();

                    //并通知其它在线用户.
                    scct.notifyOther(user.getUserId());
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}


