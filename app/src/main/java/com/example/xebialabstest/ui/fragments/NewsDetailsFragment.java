package com.example.xebialabstest.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.example.xebialabstest.BR;
import com.example.xebialabstest.R;
import com.example.xebialabstest.base.BaseFragment;
import com.example.xebialabstest.databinding.FragmentDetailsPageBinding;
import com.example.xebialabstest.model.NewsDetailsModel;
import com.example.xebialabstest.ui.viewmodels.NewsViewModel;
import com.example.xebialabstest.utils.AppConstants;

public class NewsDetailsFragment  extends BaseFragment<FragmentDetailsPageBinding, NewsViewModel>
{
    private NewsDetailsModel detailsModel;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_details_page;
    }

    public static NewsDetailsFragment newInstance(NewsDetailsModel model) {
        NewsDetailsFragment fragment = new NewsDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(AppConstants.DETAILS_MODEL, model);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getData();
    }


    private void getData() {
        if(getArguments()!=null)
            detailsModel=(NewsDetailsModel) getArguments().getSerializable(AppConstants.DETAILS_MODEL);

    }

    @Override
    public NewsViewModel getViewModel() {
       return ViewModelProviders.of(this).get(NewsViewModel.class);

    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding=getViewDataBinding();
        binding.setLifecycleOwner(this);
        binding.setDataModel(detailsModel);
        binding.executePendingBindings();
    }


}
