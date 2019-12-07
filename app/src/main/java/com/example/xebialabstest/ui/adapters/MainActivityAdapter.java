package com.example.xebialabstest.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xebialabstest.base.BaseViewHolder;
import com.example.xebialabstest.data.api.apiresponse.Result;
import com.example.xebialabstest.databinding.RowLatestNewsBinding;
import com.example.xebialabstest.interfaces.ActionCallBack;

import java.util.List;


public class MainActivityAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Result> newsList;
    private ActionCallBack callBack;


    //constructor
    public MainActivityAdapter(List<Result> newsList, ActionCallBack callBack) {
        this.newsList = newsList;
        this.callBack = callBack;
    }

    public List<Result> getNewsList() {
        return newsList;
    }


    //notifying the adapter
    public void setNewsList(List<Result> newsList) {
        int start=this.newsList.size();
        this.newsList.addAll(newsList);
        if(start!=0)
            notifyItemRangeInserted(start,newsList.size());
        else notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RowLatestNewsBinding binding = RowLatestNewsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MainViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        populateData(holder, position);
    }

    /*
    here we are populating the list data in individual rows of recycler view
     */
    private void populateData(RecyclerView.ViewHolder viewHolder, int position) {
        final MainViewHolder holder = ((MainViewHolder) viewHolder);
        holder.binding.setViewModel(newsList.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callBack != null)
                    callBack.onAction(newsList.get(holder.getAdapterPosition()), holder.getAdapterPosition(), 0, null);
            }
        });
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return newsList != null ? newsList.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    //view holder class
    static class MainViewHolder extends BaseViewHolder {
        private RowLatestNewsBinding binding;

        public MainViewHolder(RowLatestNewsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
