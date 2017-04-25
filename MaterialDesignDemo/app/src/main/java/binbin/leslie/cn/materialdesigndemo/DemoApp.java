package binbin.leslie.cn.materialdesigndemo;

import java.util.ArrayList;

/**
 * Created by pc on 2017/3/28.
 */

public class DemoApp {
//获取要显示的数据
    public static ArrayList<SampleModel> getSampleData(int size){
        ArrayList<SampleModel> sampleData = new ArrayList<>();
        for(int i=0;i<size;i++){
            //每一项数据后面都有相应的序号
            sampleData.add(new SampleModel("新的列表项<"+i+">"));
        }
        return sampleData;
    }
}
