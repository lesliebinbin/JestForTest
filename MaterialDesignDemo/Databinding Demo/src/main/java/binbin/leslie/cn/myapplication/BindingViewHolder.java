package binbin.leslie.cn.myapplication;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

/**
 * Created by pc on 2017/3/29.
 */

public class BindingViewHolder<T extends ViewDataBinding>
    extends RecyclerView.ViewHolder
{
    private T mBinding;

    public BindingViewHolder(T binding) {
        super(binding.getRoot());
        mBinding = binding;
    }

    public T getBinding(){
        return mBinding;
    }
}
