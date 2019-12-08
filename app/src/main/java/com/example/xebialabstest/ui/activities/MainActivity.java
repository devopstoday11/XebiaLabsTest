package com.example.xebialabstest.ui.activities;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;

import com.example.xebialabstest.BR;
import com.example.xebialabstest.R;
import com.example.xebialabstest.base.BaseActivity;
import com.example.xebialabstest.databinding.ActivityMainBinding;
import com.example.xebialabstest.ui.fragments.HomeFragment;
import com.example.xebialabstest.ui.viewmodels.MainActivityViewModel;
import com.google.android.material.snackbar.Snackbar;


public class MainActivity extends BaseActivity<ActivityMainBinding, MainActivityViewModel> implements View.OnClickListener {

    private ActivityMainBinding binding;
    private MainActivityViewModel viewModel;

    @Override
    public MainActivityViewModel getViewModel() {
       viewModel=ViewModelProviders.of(this).get(MainActivityViewModel.class);;
       return viewModel;
    }

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
        settingClickListner();
        addFragmentWithoutAnimation(R.id.fl_container, HomeFragment.newInstance(),true);
    }
    private void showSnackBar(String message) {
        Snackbar snackbar = Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_SHORT);
       /* snackbar.setAction(getString(R.string.s_retry), new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });*/
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
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
        binding.customToolBar.ivMenu.setOnClickListener(this);
        binding.customToolBar.ivMore.setOnClickListener(this);
        binding.customToolBar.ivSearch.setOnClickListener(this);
    }
    /**
     * initializing views
     */
    private void initViews() {
        binding = getViewDataBinding();
        setViewModel(viewModel);
        binding.setLifecycleOwner(this);
        binding.setViewModel(viewModel);
    }



    @Override
    public void onBackPressed(){
        if (binding.drawerLayout.isDrawerOpen(Gravity.LEFT))
            openCloseDrawer();
        else if(getSupportFragmentManager().getBackStackEntryCount() <=1){
            finish();
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_search:
            case R.id.iv_more:
                showSnackBar(getString(R.string.s_under_dev));
                break;
                case R.id.iv_menu:
                openCloseDrawer();
                break;

        }

    }
}
