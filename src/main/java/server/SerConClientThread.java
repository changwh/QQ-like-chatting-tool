package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;

public class SerConClientThread extends Thread {
    Socket socekt;

    public SerConClientThread(Socket socekt) {
        this.socekt=socekt;
    }

    // TODO: 2018/12/22 iam是什么
    public void notifyOther(String iam) {
        HashMap hm=ManageClientThread.hm;
        Iterator it=hm.keySet().iterator();

        while (it.hasNext()) {
            Message message=new Message();
            message.setCon(iam);
            message.setMesType(Message.MessageType.message_ret_onLineFriend);
            //取出在线人的id
            String onLineUserId=it.next().toString();
            System.out.println(onLineUserId);
            try {
                ObjectOutputStream oos=new ObjectOutputStream(ManageClientThread.getClientThread(onLineUserId).socekt.getOutputStream());
                message.setReceiver(onLineUserId);
                oos.writeObject(message);
            } catch (Exception e) {
                e.printStackTrace();
                // TODO: handle exception
            }
        }
    }

    public void run() {
        while (true) {
            try {
                ObjectInputStream ois=new ObjectInputStream(socekt.getInputStream());
                Message m=(Message)ois.readObject();

                if(Message.MessageType.message_comm_mes.equals(m.getMesType())) {               // 普通信息
                    //一会完成转发.
                    //取得接收人的通信线程
                    SerConClientThread sc=ManageClientThread.getClientThread(m.getReceiver());
                    ObjectOutputStream oos=new ObjectOutputStream(sc.socekt.getOutputStream());
                    oos.writeObject(m);
                }else if(Message.MessageType.message_get_onLineFriend.equals(m.getMesType())) { // 获取在线好友
                    System.out.println(m.getSender()+"请求在线好友列表");
                    //把在服务器的好友给该客户端返回.
                    List<String> userid=ManageClientThread.getAllOnLineUserid();

                    ObjectMapper mapper=new ObjectMapper();

                    Message result=new Message();
                    result.setReceiver(m.getSender());
                    result.setMesType(Message.MessageType.message_ret_onLineFriend);
                    result.setCon(mapper.writeValueAsString(userid));
                    ObjectOutputStream oos=new ObjectOutputStream(socekt.getOutputStream());
                    oos.writeObject(result);
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
