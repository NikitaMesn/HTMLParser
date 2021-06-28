package app;


import java.util.*;
import org.json.simple.JSONObject;
import java.io.FileWriter;
import java.io.IOException;

public class JsonUtils {

    public static void saveToJsonDB(String key, Map<String, Long> map) {
        JSONObject obj = new JSONObject();
        FileWriter fileWriter = null;
        obj.put(key, map);

        try  {
            fileWriter = new FileWriter("jsondb.db", true);
            fileWriter.write(obj.toJSONString());

        } catch (IOException e) {
            ExceptionUtils.writeExceptionToFile(e);
            //e.printStackTrace();
        } finally {
            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                ExceptionUtils.writeExceptionToFile(e);
                //e.printStackTrace();
            }
        }
    }
}
