package com.lemon.c.nms.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by C on 2016/8/31.
 */
public class JsonUtils {

    /**
     * 将json格式的字符串解析成Map对象 <li>
     * json格式：{"name":"admin","retries":"3fff","testname"
     * :"ddd","testretries":"fffffffff"}
     */
    public static HashMap<String, String> toHashMap(String info) throws Exception
    {
        HashMap<String, String> data = new HashMap<String, String>();
        if(info.length()==0)
            return data;
        JSONObject jsonObject  = new JSONObject(info);
        Iterator it = jsonObject.keys();
        while (it.hasNext())
        {
            String key = String.valueOf(it.next());
            String value = (String) jsonObject.get(key);
            data.put(key, value);
        }
        return data;
    }

    public static List toList(String info, int chartType) {
        List<Float> valueList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject("{" + info + "}");
            JSONArray jsonArray ;
            if (chartType == 1) {
                jsonArray = jsonObject.getJSONArray("snr");
            } else if (chartType == 2) {
                jsonArray = jsonObject.getJSONArray("ber");
            } else {
                jsonArray = jsonObject.getJSONArray("npa");
            }
            for (int i = 0; i < jsonArray.length(); i++) {
                String date = null;
                String value = null;
                JSONArray item = jsonArray.getJSONArray(i);
//                date = item.get(0).toString();
                value = item.get(1).toString();
                valueList.add(Float.parseFloat(value));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return valueList;
    }
}
