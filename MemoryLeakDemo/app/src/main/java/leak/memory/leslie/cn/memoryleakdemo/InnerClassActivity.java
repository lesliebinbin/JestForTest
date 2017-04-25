package leak.memory.leslie.cn.memoryleakdemo;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by pc on 2017/4/7.
 */

public class InnerClassActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inner_class);


        /**
         * 内部类对象对外部类对象有一个隐式的强引用
         * java中的引用四种引用类型
         * 强引用类型 User u = new User();//显示的强引用,强可及对象,GC是不会回收的
         * 软引用类型 SoftReference srf = new SoftReference(u);软可及,GC当内存不足
         * 时,才会
         * u=null;
         * 软引用
         * 虚引用
         */
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                //模拟耗时操作
//                SystemClock.sleep(100000);
//            }
//        }).start();
        UserUtils.setUser(new User());
    }

    static class User{
        String username;

    }


}
