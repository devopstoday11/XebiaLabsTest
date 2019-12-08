package com.example.xebialabstest.ui.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.xebialabstest.BR;
import com.example.xebialabstest.R;
import com.example.xebialabstest.base.BaseFragment;
import com.example.xebialabstest.data.api.apiresponse.Medium;
import com.example.xebialabstest.data.api.apiresponse.NewsListing;
import com.example.xebialabstest.data.api.apiresponse.Result;
import com.example.xebialabstest.data.api.network.CustomException;
import com.example.xebialabstest.databinding.FragmentHomeBinding;
import com.example.xebialabstest.interfaces.ActionCallBack;
import com.example.xebialabstest.model.NewsDetailsModel;
import com.example.xebialabstest.ui.adapters.NewsAdapter;
import com.example.xebialabstest.ui.viewmodels.NewsViewModel;
import com.example.xebialabstest.utils.NetworkUtils;
import com.example.xebialabstest.utils.SnackbarUtils;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class HomeFragment extends BaseFragment<FragmentHomeBinding, NewsViewModel> {

    private NewsViewModel viewModel;
    private boolean isRefreshing;
    private boolean isLoading;
    private boolean stopPaging;
    private NewsAdapter adapter;


    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public NewsViewModel getViewModel()
    {
        viewModel=ViewModelProviders.of(this).get(NewsViewModel.class);
        return viewModel;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        settingRecyclerView();
        subscribeToApiResponse();
        callNewsListingApi();

    }

    private void initViews() {
        binding=getViewDataBinding();
        binding.srlMovies.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefreshing = true;
                callNewsListingApi();

            }
        });
    }

    private void subscribeToApiResponse() {
            getViewModel().getApiHanlder().isSuccessful().observe(this, new Observer<NewsListing>() {
            @Override
            public void onChanged(NewsListing newsListing) {
               mActivity. hideProgressBar();
                isLoading = false;
                binding.srlMovies.setRefreshing(false);

                if (newsListing != null && newsListing.getNumResults() > 0 && newsListing.getResults() != null && newsListing.getResults().size() > 0) {
                    // record fetched from api
                    if (adapter != null) {
                        if (isRefreshing)
                            adapter.getNewsList().clear();
                        adapter.setNewsList(newsListing.getResults());
                        if (newsListing.getNumResults() == 0)
                            stopPaging = true;

                    }
                } else if (newsListing != null && newsListing.getResults() != null && newsListing.getResults().size() == 0) {
                    //no more record
                    showSnackBar(getString(R.string.s_no_record_found));
                } else {
                    //todo show general message
                    showSnackBar(getString(R.string.s_something_went_wrong));
                }
                if (newsListing != null && newsListing.getNumResults() == 0)
                    stopPaging = true;

            }
        });
        getViewModel().getApiHanlder().isFailed().observe(this, new Observer<CustomException>() {
            @Override
            public void onChanged(CustomException e) {

                //todo handle different error codes here

               mActivity. hideProgressBar();
                resetValues();
                if (e != null) {
                    if (e.getApiError() != null) {
                        // api error
                    } else {
                        // api fail
                    }
                    SnackbarUtils.showSnackbar(binding.getRoot(), getString(R.string.s_something_went_wrong));

                }
            }
        });
    }

    private void resetValues() {
        isRefreshing = false;


        isLoading = false;
        binding.srlMovies.setRefreshing(false);

    }

    private void callNewsListingApi() {
        if (NetworkUtils.isNetworkConnected(mActivity)) {
            if (!isLoading) {
                isLoading = true;
                mActivity.showProgressBar();
                getViewModel().callApi();


            }
        } else {
            isLoading = false;
            isRefreshing = false;
            binding.srlMovies.setRefreshing(false);
            showSnackBar(getString(R.string.s_plz_check_internet_string));
        }
    }

    /*
   setup the recycler view
    */
    private void settingRecyclerView() {
        adapter = new NewsAdapter(new ArrayList<Result>(), new ActionCallBack() {
            @Override
            public void onAction(Object action, int pos, int type, String name) {

                Result result=(Result)action;
                NewsDetailsModel newsDetailsModel=new NewsDetailsModel();

                newsDetailsModel.setTittle(result.getTitle());
                newsDetailsModel.setAuthor(result.getByline());
                newsDetailsModel.setDate(result.getPublishedDate());
                newsDetailsModel.setDesc(result.getAbstract());
                if(result.getMedia()!=null && result.getMedia().size()>0) {
                    Medium mediaList = result.getMedia().get(0);
                    if (mediaList.getType() != null && mediaList.getType().equals("image") && mediaList.getMediaMetadata() != null && mediaList.getMediaMetadata().size() > 0)

                        if(mediaList.getMediaMetadata().size()>2)
                        newsDetailsModel.setImage(mediaList.getMediaMetadata().get(2).getUrl());
                        else if(mediaList.getMediaMetadata().size()>1)
                            newsDetailsModel.setImage(mediaList.getMediaMetadata().get(1).getUrl());
                        else
                            newsDetailsModel.setImage(mediaList.getMediaMetadata().get(0).getUrl());

                }

                mActivity.addFragmentWithoutAnimation(R.id.fl_container, NewsDetailsFragment.newInstance(newsDetailsModel),true);





            }
        });
        binding.rvNews.setAdapter(adapter);
    }

    private void showSnackBar(String message) {
        Snackbar snackbar = Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_SHORT);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(mActivity, R.color.colorPrimaryDark));
        TextView textView = snackbar.getView().findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(mActivity, android.R.color.white));
        snackbar.show();
    }
    /*
  here we can check for pagination condition
   */
    private void setPaging() {
        binding.rvNews.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!stopPaging && dy > 0) {
                    LinearLayoutManager layoutManager = (LinearLayoutManager) binding.rvNews.getLayoutManager();
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                    if (!stopPaging && !isLoading) {
                        if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                                && firstVisibleItemPosition >= 0) {

                            //todo handle pagination here
                            //isPaging=true;

                        }
                    }

                }

            }
        });
    }
}
