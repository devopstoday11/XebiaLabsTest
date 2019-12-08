package com.example.xebialabstest.base;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.xebialabstest.R;
import com.example.xebialabstest.utils.NetworkUtils;


public abstract class BaseActivity<MyDataBinding extends ViewDataBinding,MyViewModel extends BaseViewModel>  extends AppCompatActivity implements BaseFragment.Callback{

    private MyDataBinding binding;
    private MyViewModel viewModel;
    private Dialog mProgressDialog;


    public abstract MyViewModel getViewModel();

    public  void setViewModel(MyViewModel viewModel){
        this.viewModel=viewModel;
    }

    @Override
    public void onFragmentAttached() {

    }

    @Override
    public void onFragmentDetached(String tag) {

    }

    /**
     * Override for set binding variable
     *
     * @return variable id
     */
    public abstract int getBindingVariable();

    public  abstract void  retryApi(int requestCode);

    @LayoutRes
    public abstract int getLayoutId();

    @Override
    public void onAttachFragment(@NonNull Fragment fragment) {
        super.onAttachFragment(fragment);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            performDataBinding();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    private void performDataBinding() throws NullPointerException{
        binding = DataBindingUtil.setContentView(this, getLayoutId());
        this.viewModel = viewModel == null ? getViewModel() : viewModel;
        if (binding == null)
            return;
        binding.setVariable(getBindingVariable(), viewModel);
        binding.executePendingBindings();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View view = getCurrentFocus();
        if (view != null && (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) && view instanceof EditText && !view.getClass().getName().startsWith("android.webkit.")) {
            int scrcoords[] = new int[2];
            view.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + view.getLeft() - scrcoords[0];
            float y = ev.getRawY() + view.getTop() - scrcoords[1];
            if (x < view.getLeft() || x > view.getRight() || y < view.getTop() || y > view.getBottom())
                ((InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow((this.getWindow().getDecorView().getApplicationWindowToken()), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * @param resourceID-->       layout id
     * @param fragment-->fragment object
     * @param addToBackStack-->   flag to onAddItem to back stack
     */
    public void addFragmentWithoutExitAnimation(@IdRes int resourceID, Fragment fragment, boolean addToBackStack) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_right, 0, R.anim.enter_from_right, 0);

        transaction.add(resourceID, fragment, fragment.getClass().getSimpleName());
        if (addToBackStack)
            transaction.addToBackStack(fragment.getClass().getSimpleName());
        transaction.commit();
    }

    /**
     * @param resourceID-->       layout id
     * @param fragment-->fragment object
     * @param addToBackStack-->   flag to onAddItem to back stack
     */
    public void addFragmentWithoutAnimation(@IdRes int resourceID, Fragment fragment, boolean addToBackStack) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        transaction.add(resourceID, fragment, fragment.getClass().getSimpleName());
        if (addToBackStack)
            transaction.addToBackStack(fragment.getClass().getSimpleName());
        transaction.commit();
    }

    /**
     * @param resourceID-->       layout id
     * @param fragment-->fragment object
     * @param addToBackStack-->   flag to onAddItem to back stack
     */
    public void replaceFragment(@IdRes int resourceID, Fragment fragment, boolean addToBackStack) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
       // transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right);
        transaction.replace(resourceID, fragment, fragment.getClass().getSimpleName());
        if (addToBackStack)
            transaction.addToBackStack(fragment.getClass().getSimpleName());
        transaction.commit();
    }

    public MyDataBinding getViewDataBinding() {
        return binding;
    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean hasPermission(String permission) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }
    public void showProgressBar() {
        if (!isFinishing()) {
            hideProgressBar();
            mProgressDialog = new Dialog(this);
            mProgressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            View view = LayoutInflater.from(this).inflate(R.layout.layout_progressbar, null);
            mProgressDialog.setContentView(view);

            view.setVisibility(View.VISIBLE);
            mProgressDialog.setCancelable(false);

            if (mProgressDialog.getWindow() != null) {
                mProgressDialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(this, android.R.color.transparent));
                mProgressDialog.getWindow().setDimAmount(0);
                mProgressDialog.getWindow().setGravity(Gravity.CENTER);
            }
            mProgressDialog.show();
        }
    }
    public void hideProgressBar() {
        if (!isFinishing() && mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }



    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    public boolean isNetworkConnected() {
        return NetworkUtils.isNetworkConnected(getApplicationContext());
    }



    public Fragment getCurrentFragment(int id) {
        return getSupportFragmentManager().findFragmentById(id);
    }
}
