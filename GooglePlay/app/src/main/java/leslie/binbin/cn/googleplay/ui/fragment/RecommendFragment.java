package leslie.binbin.cn.googleplay.ui.fragment;


import android.graphics.Color;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import leslie.binbin.cn.googleplay.http.protocol.RecommendProtocol;
import leslie.binbin.cn.googleplay.ui.view.LoadingPage;
import leslie.binbin.cn.googleplay.ui.view.fly.ShakeListener;
import leslie.binbin.cn.googleplay.ui.view.fly.StellarMap;
import leslie.binbin.cn.googleplay.utils.UIUtils;

public class RecommendFragment extends BaseFragment {

    private ArrayList<String> mData;

    @Override
    public View onCreateSuccessView() {
        StellarMap stellar = new StellarMap(UIUtils.getContext());
        stellar.setAdapter(new RecommendAdapter());
        //随机的方式,将空间划分为9行6列的格子,然后再格子中随机方式
        stellar.setRegularity(6,9);
        int padding = UIUtils.dip2px(10);
        //设置内边距
        stellar.setPadding(padding,padding,padding,padding);

        //设置默认页面,第一组数据
        stellar.setGroup(0,true);

        ShakeListener shake = new ShakeListener(UIUtils.getContext());
        shake.setOnShakeListener(()->{
            stellar.zoomIn();//调到下一页数据
        });

        return stellar;
    }

    @Override
    public LoadingPage.ResultState onLoad() {
        RecommendProtocol protocol = new RecommendProtocol();
        mData = protocol.getData(0);
        return check(mData);
    }

    class RecommendAdapter implements StellarMap.Adapter{

        //返回组的个数
        @Override
        public int getGroupCount() {
            return 2;
        }

        //返回个数
        @Override
        public int getCount(int group) {
            int count = mData.size()/getGroupCount();
            if(group==getGroupCount()-1){
                //最后一页,将除不尽,将余下来的数量追加到最后一页,保持数据完整
                count+=mData.size()%getGroupCount();
            }
            return count;
        }

        //初始化布局的方法
        @Override
        public View getView(int group, int position, View convertView) {
            //因为position每组都会从0开始计数,所以需要将其他几组的个数加起来
            //才能确定当前组的获取数据的角标位置
            position+=(group)*getCount(group-1);
            String keyword = mData.get(position);
            TextView view = new TextView(UIUtils.getContext());
            view.setText(keyword);

            //随机大小

            Random random = new Random();
            int size = 16+random.nextInt(10);
            view.setTextSize(TypedValue.COMPLEX_UNIT_SP,size);
            //随机颜色
            //r g b 0-255

            int r = 30+random.nextInt(200);
            int g = 30+random.nextInt(200);
            int b = 30+random.nextInt(200);

            view.setTextColor(Color.rgb(r,g,b));
            view.setOnClickListener((textView)->{
                Toast.makeText(UIUtils.getContext(),keyword, Toast.LENGTH_SHORT).show();
            });

            return view;
        }

        //返回下一组的id
        @Override
        public int getNextGroupOnZoom(int group, boolean isZoomIn) {


            if(isZoomIn){
                //往下划加载上一页
                if(group>0){
                    group--;
                }else{
                    //调到最后一页
                    group = getGroupCount()-1;
                }
            }else{
                //往上划加载下一页
                if(group<getGroupCount()-1){
                    group++;
                }else{
                    group=0;
                }
            }
            return group;
        }
    }
}
