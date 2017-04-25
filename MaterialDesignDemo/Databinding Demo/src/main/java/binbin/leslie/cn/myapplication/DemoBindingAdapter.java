package binbin.leslie.cn.myapplication;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by pc on 2017/3/29.
 */

public class DemoBindingAdapter {
    @BindingAdapter({"app:imageUrl","app:placeholder"})
    public static void loadImageFromUrl(ImageView view, String url, Drawable drawable) {
        Glide.with(view.getContext())
                .load(url)
                .placeholder(drawable)
                .into(view);
    }
}
