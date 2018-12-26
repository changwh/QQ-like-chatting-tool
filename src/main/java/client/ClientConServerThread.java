package client;

import util.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientConServerThread extends Thread {

    private Socket socket;

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
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
