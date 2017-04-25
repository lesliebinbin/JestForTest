package firstdemo.as.tedu.cn.myapplication;

/**
 * Created by pc on 2017/3/16.
 */

public class RepositoryBean {
    String full_name;
    String html_url;

    int contributions;

    @Override
    public String toString() {
        return full_name + " (" + contributions + ")";
    }
}
