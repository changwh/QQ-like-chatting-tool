package server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class ManageClientThread
{
    public static HashMap<String,SerConClientThread> hm=new HashMap<String,SerConClientThread>();

    public static void addClientThread(String uid,SerConClientThread scct)
    {
        hm.put(uid,scct);
    }

    public static SerConClientThread getClientThread(String uid)
    {
        return hm.get(uid);
    }

    public static List<String> getAllOnLineUserid()
    {
        Iterator it=hm.keySet().iterator();
        List<String> useridList=new ArrayList<String>();
        while (it.hasNext())
        {
            useridList.add(it.next().toString());
        }
        return useridList;
    }
}
