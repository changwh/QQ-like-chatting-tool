package server;

public class Message {
    private MessageType mesType;

    private String sender;      // 发送者
    private String receiver;    // 接收者
    private String con;         // 信息内容
    private String sendTime;    // 发送时间

    /**
     * 消息类型
     */
    enum MessageType {
        message_login_succeed,              // 登录成功
        message_login_fail,                 // 登录失败
        message_comm_mes,                   // 普通信息
        message_get_onLineFriend,           // 获取在线好友
        message_ret_onLineFriend            // 返回在线好友
    }

    public MessageType getMesType() {
        return mesType;
    }

    public void setMesType(MessageType mesType) {
        this.mesType = mesType;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getCon() {
        return con;
    }

    public void setCon(String con) {
        this.con = con;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }
}
