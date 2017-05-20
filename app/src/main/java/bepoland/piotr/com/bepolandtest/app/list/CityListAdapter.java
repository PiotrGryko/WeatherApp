package bepoland.piotr.com.bepolandtest.app.list;

import android.databinding.DataBindingUtil;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import bepoland.piotr.com.bepolandtest.BR;
import bepoland.piotr.com.bepolandtest.R;
import bepoland.piotr.com.bepolandtest.app.model.ModelCity;
import bepoland.piotr.com.bepolandtest.databinding.RowCityBinding;

/**
 * Created by piotr on 12/05/17.
 */

public class CityListAdapter extends RecyclerView.Adapter<CityListAdapter.CityViewHolder> {

    public interface OnCityClickListener {
        void onCityClick(ModelCity city, ImageView image);
    }

    private ModelCity[] data;
    private OnCityClickListener onItemClickListener;

    public CityListAdapter(ModelCity[] data)
    {
        this.data=data;
    }

    public void setData(ModelCity[] data)
    {
        this.data=data;
    }

    public void setOnItemClickListener(OnCityClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public CityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_city, parent,false);
        return new CityViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final CityViewHolder holder, final int position) {
        holder.viewDataBinding.setVariable(BR.city, data[position]);
        holder.viewDataBinding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) onItemClickListener.onCityClick(data[position],holder.viewDataBinding.ivProfile);
            }
        });
        ViewCompat.setTransitionName(holder.viewDataBinding.ivProfile, String.valueOf(position) + "_image");

    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    // inner class to hold a reference to each item of RecyclerView
    public static class CityViewHolder extends RecyclerView.ViewHolder {
        private RowCityBinding viewDataBinding;

        public CityViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            viewDataBinding = DataBindingUtil.bind(itemLayoutView);
        }
    }
}
