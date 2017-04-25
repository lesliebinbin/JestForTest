package firstdemo.as.tedu.cn.myapplication;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by pc on 2017/3/4.
 */

public interface GithubService {

    String ENDPOINT = "https://api.github.com";

    //获取个人信息
    @GET("/users/{user}")
    Observable<UserListAdapter.GitHubUser> getUserData(@Path("user") String user);

    // 获取库, 获取的是数组
    @GET("/users/{user}/repos")
    Observable<RepoListAdapter.GitHubRepo[]> getRepoData(@Path("user") String user);

}
