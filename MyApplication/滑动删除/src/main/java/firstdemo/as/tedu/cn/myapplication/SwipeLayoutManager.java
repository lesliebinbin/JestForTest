package firstdemo.as.tedu.cn.myapplication;

/**
 * Created by pc on 2017/3/12.
 */

public class SwipeLayoutManager {
    private static SwipeLayoutManager mInstance = new SwipeLayoutManager();
    private SwipeLayout mCurrentLayout;//用来记录当前打开的SwipeLayout
    private SwipeLayoutManager(){

    }

    public static SwipeLayoutManager getInstance(){
        return mInstance;
    }

    public void setSwipeLayout(SwipeLayout currentLayout){
        mCurrentLayout = currentLayout;
    }

    /**
     * 清空当前所记录的已经打开的layout
     */
    public void clearCurrentLayout(){
        mCurrentLayout=null;
    }

    /**
     * 关闭当前已经打开的SwipeLayout
     */
    public void closeCurrentLayout(){
        if(mCurrentLayout!=null){
            mCurrentLayout.close();
        }
    }

    /**
     * 判断当前是否能够滑动,如果没有打开的,则可以滑动
     * 如果有打开的,则判断打开的layout和当前按下的layout是否是同一个
     *
     * @return
     */
    public boolean isShouldSwipe(SwipeLayout swipeLayout){
        if(mCurrentLayout==null){
            //说明当前木有打开的layout
            return true;
        }else{
            //说明有打开的
            return mCurrentLayout==swipeLayout;
        }
    }
}
