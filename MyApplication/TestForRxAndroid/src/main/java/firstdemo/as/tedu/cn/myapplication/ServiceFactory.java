package firstdemo.as.tedu.cn.myapplication;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by pc on 2017/3/4.
 */

public class ServiceFactory <T>{
    public static  <T> T createServiceFrom(final Class<T> serviceClass,String endpoint){
        Retrofit adapter = new Retrofit.Builder()
                .baseUrl(endpoint)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//添加Rx适配器
                .addConverterFactory(GsonConverterFactory.create())//添加Gson转换器
                .build();
        return adapter.create(serviceClass);
    }
}
