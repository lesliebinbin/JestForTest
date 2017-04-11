package leslie.binbin.cn.myapplication;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void sort(){
        int num[] = {223,11,33,24,321,21,23,123};
        for(int i=0;i<num.length;i++){
            for(int j=0;j<num.length-i-1;j++){
                if(num[j]<num[j+1]){
                    int tmp = num[j];
                    num[j]=num[j+1];
                    num[j+1]=tmp;
                }
            }
        }

        System.out.println(Arrays.toString(num));
    }
}