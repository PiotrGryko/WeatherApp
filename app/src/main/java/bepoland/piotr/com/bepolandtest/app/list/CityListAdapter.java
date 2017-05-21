package bepoland.piotr.com.bepolandtest.app.list;

import android.databinding.DataBindingUtil;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Arrays;

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
        void onElementRemoved(ModelCity city);
    }

    private ArrayList<ModelCity> data;
    private OnCityClickListener onItemClickListener;

    public CityListAdapter(ModelCity[] data) {
        this.data = new ArrayList<ModelCity>(Arrays.asList(data));
    }

    public void setData(ModelCity[] data) {
        this.data = new ArrayList<ModelCity>(Arrays.asList(data));
    }

    public void setOnItemClickListener(OnCityClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public CityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_city, parent, false);
        return new CityViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final CityViewHolder holder, final int position) {
        holder.viewDataBinding.setVariable(BR.city, data.get(position));
        holder.viewDataBinding.btnRemove.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onElementRemoved(data.get(position));
                    data.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position,data.size());
                }
            }
        }); holder.viewDataBinding.getRoot().setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (onItemClickListener != null)
                    onItemClickListener.onCityClick(data.get(position), holder.viewDataBinding
                            .ivProfile);
            }
        });
        ViewCompat.setTransitionName(holder.viewDataBinding.ivProfile, String.valueOf(position) +
                "_image");
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    // inner class to hold a reference to each item of RecyclerView
    public static class CityViewHolder extends RecyclerView.ViewHolder {

        private RowCityBinding viewDataBinding;

        public CityViewHolder(View itemLayoutView) {
            super(itemLayoutView); viewDataBinding = DataBindingUtil.bind(itemLayoutView);
        }
    }
}
