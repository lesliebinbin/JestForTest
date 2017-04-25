package firstdemo.as.tedu.cn.myapplication;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by pc on 2017/3/16.
 */

public interface GitHubService {
    @GET("orgs/{orgName}/repos")
    Call<List<RepositoryBean>> queryOrgRepos(@Path("orgName") String orgName);

    Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create()).build();
}
