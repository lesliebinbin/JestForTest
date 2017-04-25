package firstdemo.as.tedu.cn.myapplication;

import android.text.TextUtils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * Created by pc on 2017/3/12.
 */

public class PinYinUtil {
    /**
     * 获取汉字的拼音,会消耗一定的资源,所以不应该频繁调用
     * @param chinese
     * @return
     */
    public static String getPinyin(String chinese){
        if(TextUtils.isEmpty(chinese)) return null;

        //用来设置转化的拼音的大小写,或者声调

        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.UPPERCASE);//设置转化的拼音是大写字母
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);//设置转化的声音是不带声调的




        //1.由于只能对单个汉字进行转换,所以需要将字符串转化为字符数组,然后对每个字符转化,最后拼接起来
        char[] charArray = chinese.toCharArray();
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<charArray.length;i++){
            //2.过滤空格
            if(Character.isWhitespace(charArray[i])) continue;

            //3.需要判断是否是汉字
            //汉字占两个字节,一个字节是-128~127,那么汉字肯定大于127
            if(charArray[i]>127){
                //可能是汉字
                try {
                    //由于有多音字的存在,
                    String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(charArray[i],format);
                    if(pinyinArray!=null) {
                        sb.append(pinyinArray[0]);//此处即使有多音字,那么也只能取第一个拼音
                    }
                    //说明没有找到对应的拼音,汉字有问题,或者可能不是汉子,则忽略
                } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
                    badHanyuPinyinOutputFormatCombination.printStackTrace();
                    //说明转换失败,不是汉字,那么就忽略
                }
            }else{
                //肯定不是汉字,应该是键盘上能够直接输入的字符,这些字符能够排序,但是不能够获取拼音
                //所以直接拼接
                sb.append(charArray[i]);
            }
        }

        return sb.toString();
    }
}
