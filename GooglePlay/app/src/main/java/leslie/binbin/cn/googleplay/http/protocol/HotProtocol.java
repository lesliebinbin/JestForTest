package leslie.binbin.cn.googleplay.http.protocol;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * 排行网络访问
 */

public class HotProtocol extends BaseProtocol<ArrayList<String>> {
    @Override
    public ArrayList<String> parseData(String result) {
        try {
            JSONArray ja = new JSONArray(result);

            ArrayList<String> list = new ArrayList<>();

            for(int i=0;i<ja.length();i++){
                String keyword = ja.getString(i);
                list.add(keyword);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public String getParams() {
        return "";
    }

    @Override
    public String getKey() {
        return "hot";
    }
}
