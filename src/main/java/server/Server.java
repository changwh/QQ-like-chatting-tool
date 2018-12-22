package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    ServerSocket ss;

    public static void main(String args[])
    {
        new Server();
    }

    public Server()
    {
        try {
            System.out.println("˽�ķ�����������,�ȴ�����һ���ͻ�������,����ȷ��groupChat_Server�Ƿ����С�");

            ss=new ServerSocket(6666);

            while (true)
            {
                Socket s=ss.accept();

                ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
                User user=(User)ois.readObject();
                System.out.println("���������յ��û�id:"+user.getUserId()+"  ����:"+user.getPasswd());
                Message message=new Message();

                ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());

                if(s!=null)
                {
                    System.out.println("�ɹ���¼�����");

                    //����һ���ɹ���½����Ϣ��
                    message.setMesType(Message.MessageType.message_login_succeed);
                    oos.writeObject(message);

                    //����һ���̣߳��ø��߳���ÿͻ��˱���ͨѶ.
                    SerConClientThread scct=new SerConClientThread(s);
                    ManageClientThread.addClientThread(user.getUserId(), scct);
                    //������ÿͻ���ͨ�ŵ��߳�.
                    scct.start();

                    //��֪ͨ���������û�.
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


