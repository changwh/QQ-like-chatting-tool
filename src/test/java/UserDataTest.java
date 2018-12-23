import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class UserDataTest {
    @Test
    public void test() throws IOException {
        FileReader fr;
        fr = new FileReader("data/user_data.txt");
        BufferedReader br = new BufferedReader(fr);

        int userNum = 0;
        String str;
        StringTokenizer st;
        Map<String, String> userData = new HashMap<String, String>();
        while ((str = br.readLine()) != null) {
            System.out.println(str);
            st = new StringTokenizer(str, ",");
            userData.put(st.nextToken(), st.nextToken());
            userNum++;
        }
        System.out.println(userNum);
        System.out.println(userData);
    }
}
