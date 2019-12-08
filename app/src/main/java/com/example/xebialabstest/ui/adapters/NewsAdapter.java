package com.example.xebialabstest.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xebialabstest.base.BaseViewHolder;
import com.example.xebialabstest.data.api.apiresponse.Medium;
import com.example.xebialabstest.data.api.apiresponse.Result;
import com.example.xebialabstest.databinding.RowLatestNewsBinding;
import com.example.xebialabstest.interfaces.ActionCallBack;

import java.util.List;


public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Result> newsList;
    private ActionCallBack callBack;


    //constructor
    public NewsAdapter(List<Result> newsList, ActionCallBack callBack) {
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
        if(newsList.get(position).getMedia()!=null && newsList.get(position).getMedia().size()>0){
            Medium mediaList = newsList.get(position).getMedia().get(0);
            if(mediaList.getType()!=null && mediaList.getType().equals("image") && mediaList.getMediaMetadata()!=null && mediaList.getMediaMetadata().size()>0)
                holder.binding.setImage(mediaList.getMediaMetadata().get(0).getUrl());
        }
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
