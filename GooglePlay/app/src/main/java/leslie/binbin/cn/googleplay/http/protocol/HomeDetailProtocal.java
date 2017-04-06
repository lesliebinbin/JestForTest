package leslie.binbin.cn.googleplay.http.protocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import leslie.binbin.cn.googleplay.domain.AppInfo;

/**
 * 首页详情页面网络访问
 */

public class HomeDetailProtocal extends BaseProtocol<AppInfo> {

    public String packageName;

    public HomeDetailProtocal(String packageName){
        this.packageName = packageName;
    }

    @Override
    public AppInfo parseData(String result) {
        try {
            JSONObject jo = new JSONObject(result);

            AppInfo info = new AppInfo();
            info.des = jo.getString("des");
            info.downloadUrl = jo.getString("downloadUrl");
            info.iconUrl = jo.getString("iconUrl");
            info.id = jo.getString("id");
            info.name = jo.getString("name");
            info.packageName = jo.getString("packageName");
            info.size = jo.getLong("size");
            info.stars = (float) jo.getDouble("stars");

            info.author = jo.getString("author");
            info.date = jo.getString("date");
            info.downloadNum = jo.getString("downloadNum");
            info.version = jo.getString("version");

            JSONArray ja = jo.getJSONArray("safe");

            //解析安全信息
            ArrayList<AppInfo.SafeInfo> safe = new ArrayList<>();

            for(int i = 0;i<ja.length();i++){
                JSONObject jo1=ja.getJSONObject(i);

                AppInfo.SafeInfo safeInto= new AppInfo.SafeInfo();
                safeInto.safeDes = jo1.getString("safeDes");
                safeInto.safeUrl = jo1.getString("safeUrl");
                safeInto.safeDesUrl = jo1.getString("safeDesUrl");
                safe.add(safeInto);
            }

            info.safe = safe;

            //解析截图信息
            JSONArray ja1 = jo.getJSONArray("screen");
            ArrayList<String> screen = new ArrayList<>();

            for(int i=0;i<ja1.length();i++){
                String pic = ja1.getString(i);

                screen.add(pic);
            }

            info.screen = screen;

            return info;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public String getParams() {
        return "&packageName="+packageName;
    }

    @Override
    public String getKey() {
        return "detail";
    }
}
