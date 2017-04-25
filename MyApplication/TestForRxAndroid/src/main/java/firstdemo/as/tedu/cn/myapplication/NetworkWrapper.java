package firstdemo.as.tedu.cn.myapplication;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by pc on 2017/3/4.
 */

public class NetworkWrapper {
    private static final String[] mFamousUsers =
            {"SpikeKing", "JakeWharton", "rock3r", "Takhion", "dextorer", "Mariuxtheone"};
    //获取用户信息
    public static void getUserInfo(final UserListAdapter adapter){
        GithubService githubService =
                ServiceFactory.createServiceFrom(GithubService.class,GithubService.ENDPOINT);
        Observable.fromArray(mFamousUsers)
                .flatMap(githubService::getUserData)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(adapter::addItem);
    }

    public static void getRepoInfo(final String username,RepoListAdapter adapter){
        GithubService githubService =
                ServiceFactory.createServiceFrom(GithubService.class,GithubService.ENDPOINT);
        githubService.getRepoData(username)
                .flatMap(Observable::fromArray)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(adapter::addRepo);
    }
}
