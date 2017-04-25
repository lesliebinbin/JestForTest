package firstdemo.as.tedu.cn.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import firstdemo.as.tedu.cn.myapplication.util.AnimationUtil;

public class MainActivity extends Activity {

    @Bind(R.id.rl_level1)
    RelativeLayout rl_level1;
    @Bind(R.id.rl_level2)
    RelativeLayout rl_level2;
    @Bind(R.id.rl_level3)
    RelativeLayout rl_level3;
    boolean isLevel3Displayed = true;
    boolean isLevel2Displayed = true;
    boolean isLevel1Displayed = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //keyCode事件码
        if(keyCode==KeyEvent.KEYCODE_MENU){

            if(AnimationUtil.runningAnimationCount>0){
                return true;
            }

            //如果按下的是菜单按钮
            //隐藏三级菜单
            if(isLevel1Displayed){
                long delay = 0;
                if(isLevel3Displayed){

                    AnimationUtil.rotateOutAnim(rl_level3,0);
                    isLevel3Displayed=!isLevel3Displayed;
                    delay+=200;
                }
                //隐藏二级菜单
                if(isLevel2Displayed){
                    AnimationUtil.rotateOutAnim(rl_level2,delay);
                    isLevel2Displayed=!isLevel2Displayed;
                    delay+=200;
                }
                //隐藏一级菜单
                AnimationUtil.rotateOutAnim(rl_level1,delay);
            }else{
                //顺次转进来
                AnimationUtil.rotateInAnim(rl_level1,0);
                AnimationUtil.rotateInAnim(rl_level2,200);
                AnimationUtil.rotateInAnim(rl_level3,400);
                isLevel2Displayed = true;
                isLevel3Displayed = true;
            }
            isLevel1Displayed=!isLevel1Displayed;
            return true;//消费了当前事件
        }
        return super.onKeyDown(keyCode, event);
    }

    @OnClick({R.id.ib_menu,R.id.ib_home})
    public void doClick(View view){
        if(AnimationUtil.runningAnimationCount>0){
            //当前动画正在执行,取消当前事件
            return;
        }

        switch (view.getId()){
            case R.id.ib_menu:
                if(isLevel3Displayed){
                    AnimationUtil.rotateOutAnim(rl_level3,0);
                }else{
                    AnimationUtil.rotateInAnim(rl_level3,0);
                }
                isLevel3Displayed = !isLevel3Displayed;
                break;
            case R.id.ib_home:
                long delay = 0;
                if(isLevel2Displayed){
                    //如果当前三级菜单已经显示,先转出去
                    if(isLevel3Displayed){
                        AnimationUtil.rotateOutAnim(rl_level3,0);
                        isLevel3Displayed = !isLevel3Displayed;
                        delay+=200;
                    }
                    AnimationUtil.rotateOutAnim(rl_level2,delay);
                }else{
                    AnimationUtil.rotateInAnim(rl_level2,delay);
                }
                isLevel2Displayed = !isLevel2Displayed;
                break;
        }
    }
}
