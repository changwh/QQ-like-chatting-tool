package client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DialogPanel extends JFrame implements ActionListener {

    private String ownerId, friendId;

    public DialogPanel(String ownerId, String friendId) {
        this.ownerId = ownerId;
        this.friendId = friendId;


    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
