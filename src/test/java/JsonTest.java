import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonTest {
    @Test
    public void test() throws IOException {
        List<String> list = new ArrayList<String>();
        list.add("test");
        list.add("test2");
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(mapper.writeValueAsString(list));
    }

}
