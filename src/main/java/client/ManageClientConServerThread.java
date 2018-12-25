package client;

import java.util.HashMap;

public class ManageClientConServerThread {
    private static HashMap hm = new HashMap<String, ClientConServerThread>();

    //把创建好的ClientConServerThread放入到hm
    public static void addClientConServerThread(String userId, ClientConServerThread ccst) {
        hm.put(userId, ccst);
    }

    //可以通过qqId取得该线程
    public static ClientConServerThread getClientConServerThread(String userId) {
        return (ClientConServerThread) hm.get(userId);
    }
}
