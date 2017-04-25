package firstdemo.as.tedu.cn.myapplication;

import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * Created by pc on 2017/3/12.
 */

public class Friend implements Serializable,Comparable<Friend>{
    private String name;

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    private String pinyin;

    public Friend(String name) {
        this.name = name;
        setPinyin(PinYinUtil.getPinyin(name));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(@NonNull Friend another) {
        return getPinyin().compareTo(another.getPinyin());
    }
}
