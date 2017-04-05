package leslie.binbin.cn.googleplay.http.protocol;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import leslie.binbin.cn.googleplay.http.HttpHelper;
import leslie.binbin.cn.googleplay.utils.IOUtils;
import leslie.binbin.cn.googleplay.utils.LogUtils;
import leslie.binbin.cn.googleplay.utils.StringUtils;
import leslie.binbin.cn.googleplay.utils.UIUtils;

/**
 * Created by pc on 2017/4/4.
 */

public abstract class BaseProtocol<T> {

    //index表示的是从哪个位置开始返回20条数据,用于分页
    public T getData(int index) {
        //先判断是否有缓存,有的话就加载缓存
        String result = getCache(index);
        if(StringUtils.isEmpty(result)){//如果没有缓存或者缓存失效
            //请求服务器
            result = getDataFromServer(index);
        }

        //开始解析
        if(result!=null){
            T data = parseData(result);
            return data;
        }
        return null;
    }

    //解析数据,子类必须实现
    public abstract T parseData(String result);



    //从网络获取数据
    //index表示的是从哪个位置开始返回20条数据,用于分页
    public String getDataFromServer(int index) {
        HttpHelper.HttpResult httpResult = HttpHelper.get(HttpHelper.URL
                + getKey() + "?index=" + index + getParams());

        if (httpResult != null) {
            String result = httpResult.getString();
            LogUtils.e("访问结果:" + result);

            //写缓存
            if(!StringUtils.isEmpty(result)){
                setCache(index,result);
            }

            return result;
        }
        return null;
    }

    //获取网络链接参数,子类必须实现
    public abstract String getParams();

    //获取网络链接关键字,子类必须实现
    public abstract String getKey();

    //写缓存
    public void setCache(int index, String json) {
        //以url为文件名,以json为文件内容,缓存在本地
        File cacheDir = UIUtils.getContext().getCacheDir();//本应用的缓存文件夹
        //生成缓存文件
        File cacheFile = new File(cacheDir, getKey() + "?index=" + index + getParams());

        FileWriter writer = null;
        try {
            writer = new FileWriter(cacheFile);

            //缓存失效截止时间
            long deadline = System.currentTimeMillis()+30*60*1000;
            writer.write(deadline+"\n");//在第一行写入缓存时间,换行

            writer.write(json);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.close(writer);
        }

    }

    //读缓存
    public String getCache(int index){
        //以url为文件名,以json为文件内容,缓存在本地
        File cacheDir = UIUtils.getContext().getCacheDir();//本应用的缓存文件夹
        //生成缓存文件
        File cacheFile = new File(cacheDir, getKey() + "?index=" + index + getParams());
        //判断缓存是否存在
        if(cacheFile.exists()){
            //判断缓存是否有效
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new FileReader(cacheFile));
                String deadLine = reader.readLine();
                long deadTime = Long.parseLong(deadLine);

                if(System.currentTimeMillis()<deadTime){//当前时间小于截止时间,缓存有效
                    //缓存有效
                    String line;
                    StringBuffer sb = new StringBuffer();
                    while((line=reader.readLine())!=null){
                        sb.append(line);
                    }

                    return sb.toString();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                IOUtils.close(reader);
            }
        }
        return null;
    }



}
