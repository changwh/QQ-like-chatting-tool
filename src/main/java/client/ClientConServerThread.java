package client;

import util.Message;

import java.io.*;
import java.net.Socket;

public class ClientConServerThread extends Thread {

    private static Socket socket;

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public ClientConServerThread(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        while (true) {
            try {
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Message message = (Message) ois.readObject();

                if (message.getMesType() == Message.MessageType.message_comm_mes) {
                    DialogPanel dialogPanel = ManageChat.getQqChat(message.getReceiver() + " " + message.getSender());
                    dialogPanel.showMessage(message);
                } else if (message.getMesType() == Message.MessageType.message_ret_onLineFriend) {
                    String receiver = message.getReceiver();
                    FriendList friendList = ManageFriendList.getFriendList(receiver);
                    if (friendList != null) {
                        friendList.updateList(message);
                    }
                    // TODO: 2018/12/28 接受文件
//                } else if (message.getMesType() == Message.MessageType.message_file) {
//                    DataInputStream dis = new DataInputStream(socket.getInputStream());
//                    String fileName = dis.readUTF();
//                    long fileLength = dis.readLong();
//                    File directory = new File("D:\\FTCache");
//                    if (!directory.exists()) {
//                        directory.mkdir();
//                    }
//                    File file = new File(directory.getAbsolutePath() + File.separatorChar + fileName);
//                    FileOutputStream fos = new FileOutputStream(file);
//
//                    byte[] bytes = new byte[1024];
//                    int length = 0;
//                    while ((length = dis.read(bytes, 0, bytes.length)) != -1) {
//                        fos.write(bytes, 0, length);
//                        fos.flush();
//                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                return;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
