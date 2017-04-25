package leak.memory.leslie.cn.memoryleakdemo;

/**
 * Created by pc on 2017/4/7.
 */

public class UserUtils {

    private static InnerClassActivity.User sUser;
    public static void setUser(InnerClassActivity.User user){
        sUser = user;
    }

}
