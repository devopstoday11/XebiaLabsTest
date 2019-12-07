package com.example.xebialabstest.ui.activities;

import android.os.Bundle;
import android.view.Gravity;
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
import com.example.xebialabstest.base.BaseActivity;
import com.example.xebialabstest.data.api.apiresponse.NewsListing;
import com.example.xebialabstest.data.api.apiresponse.Result;
import com.example.xebialabstest.data.api.network.CustomException;
import com.example.xebialabstest.databinding.ActivityMainBinding;
import com.example.xebialabstest.interfaces.ActionCallBack;
import com.example.xebialabstest.ui.adapters.MainActivityAdapter;
import com.example.xebialabstest.ui.viewmodels.MainActivityViewModel;
import com.example.xebialabstest.utils.NetworkUtils;
import com.example.xebialabstest.utils.SnackbarUtils;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;



public class MainActivity extends BaseActivity<ActivityMainBinding, MainActivityViewModel> implements View.OnClickListener {

    private ActivityMainBinding binding;
    private boolean isRefreshing;
    private boolean isLoading;
    private boolean stopPaging;
    private MainActivityAdapter adapter;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public void retryApi(int requestCode) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        settingRecyclerView();
        settingClickListner();
        subscribeToApiResponse();
        callNewsListingApi();
        //setPaging();  // for pagination condition

    }


    private void subscribeToApiResponse() {
        getViewModel().getApiHanlder().isSuccessful().observe(this, new Observer<NewsListing>() {
            @Override
            public void onChanged(NewsListing newsListing) {
                hideProgressBar();
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

                hideProgressBar();
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
        if (NetworkUtils.isNetworkConnected(this)) {
            if (!isLoading) {
                isLoading = true;
                showProgressBar();
                getViewModel().callApi();


            }
        } else {
            isLoading = false;
            isRefreshing = false;
            binding.srlMovies.setRefreshing(false);
            showSnackBar(getString(R.string.s_plz_check_internet_string));
        }
    }

    private void showSnackBar(String message) {
        Snackbar snackbar = Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_SHORT);
       /* snackbar.setAction(getString(R.string.s_retry), new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });*/
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
        TextView textView = snackbar.getView().findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(this, android.R.color.white));
        snackbar.show();
    }

    private void openCloseDrawer() {
        if (binding.drawerLayout.isDrawerOpen(Gravity.LEFT))
            binding.drawerLayout.closeDrawer(Gravity.LEFT);
        else
            binding.drawerLayout.openDrawer(Gravity.LEFT);
    }


    /**
     * handling click event s
     */
    private void settingClickListner() {
        binding.customToolBar.setViewModel(getViewModel());
        binding.customToolBar.ivMenu.setOnClickListener(this);
        binding.customToolBar.ivMore.setOnClickListener(this);
        binding.customToolBar.ivSearch.setOnClickListener(this);

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


    /**
     * initializing views
     */
    private void initViews() {
        binding = getViewDataBinding();
        MainActivityViewModel viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        setViewModel(viewModel);
        binding.setLifecycleOwner(this);
        binding.setViewModel(viewModel);
        //int[] colors={R.color.colorAccent,,R.color.colorPrimaryDark};
        //binding.srlMovies.setColorSchemeColors(colors);
        binding.srlMovies.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefreshing = true;
                callNewsListingApi();

            }
        });
    }


    /*
    setup the recycler view
     */
    private void settingRecyclerView() {
        adapter = new MainActivityAdapter(new ArrayList<Result>(), new ActionCallBack() {
            @Override
            public void onAction(Object action, int pos, int type, String name) {
                //todo handle click event


            }
        });
        binding.rvNews.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(Gravity.LEFT))
            openCloseDrawer();
        else
            super.onBackPressed();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_search:

                //handle search
                break;
            case R.id.iv_more:
                //todo handle event
                break;
            case R.id.iv_menu:
                openCloseDrawer();
                break;

        }

    }
}
