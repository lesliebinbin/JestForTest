package leslie.binbin.cn.googleplay.http.protocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import leslie.binbin.cn.googleplay.domain.SubjectInfo;

/**
 * 专题的网络请求
 */

public class SubjectProtocol extends BaseProtocol<ArrayList<SubjectInfo>> {
    @Override
    public ArrayList<SubjectInfo> parseData(String result) {
        try {
            JSONArray ja = new JSONArray(result);
            ArrayList<SubjectInfo> list = new ArrayList<>();
            for(int i=0;i<ja.length();i++){
                JSONObject jo = ja.getJSONObject(i);
                SubjectInfo info = new SubjectInfo();
                info.des = jo.getString("des");
                info.url = jo.getString("url");
                list.add(info);
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
        return "subject";
    }
}
