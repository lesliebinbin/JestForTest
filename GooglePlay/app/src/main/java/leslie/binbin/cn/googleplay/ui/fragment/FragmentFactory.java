package leslie.binbin.cn.googleplay.ui.fragment;

import java.util.HashMap;

/**
 * 生产Fragment的工厂
 * @author Leslie
 * @date    2017-04-02
 */

public class FragmentFactory {

    private static HashMap<Integer,BaseFragment> mFragmentsMap =
            new HashMap<>();

    public static BaseFragment createFragment(int pos){
        //先从集合中取如果没有才创建对象,可以提高性能
        BaseFragment fragment = mFragmentsMap.get(pos);

        if(fragment==null) {
            switch (pos) {
                case 0:
                    fragment = new HomeFragment();
                    break;
                case 1:
                    fragment = new AppFragment();
                    break;
                case 2:
                    fragment = new GameFragment();
                    break;
                case 3:
                    fragment = new SubjectFragment();
                    break;
                case 4:
                    fragment = new RecommendFragment();
                    break;
                case 5:
                    fragment = new CategoryFragment();
                    break;
                case 6:
                    fragment = new HotFragment();
                    break;
            }
            mFragmentsMap.put(pos,fragment);//将fragment保存在集合中
        }


        return fragment;
    }
}
