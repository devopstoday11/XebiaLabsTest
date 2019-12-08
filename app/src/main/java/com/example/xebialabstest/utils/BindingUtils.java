package com.example.xebialabstest.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.example.xebialabstest.MyApplication;


public final class BindingUtils {

    private BindingUtils() {
        // This class is not publicly instantiable
    }


    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView imageView, String url) {
        Context context = imageView.getContext();
        Glide.with(context).load(url).centerCrop().into(imageView);
    }

    @BindingAdapter("loadImage")
    public static void setImage(final ImageView imageView, String url) {

        Glide.with(MyApplication.getInstance()).load(url).asBitmap().centerCrop().into(new BitmapImageViewTarget(imageView) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(MyApplication.getInstance().getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                imageView.setImageDrawable(circularBitmapDrawable);

            }
        });

    }


    @BindingAdapter("roundImage")
    public static void setRoundImage(final ImageView imageView, String url) {
        final Context context = imageView.getContext();

        Glide.with(context).load(url).asBitmap().listener(new RequestListener<String, Bitmap>() {
            @Override
            public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {

                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                // circularBitmapDrawable.setCornerRadius(15.0f);
                imageView.setImageDrawable(circularBitmapDrawable);
                return false;
            }
        }).into(imageView);
    }

    @BindingAdapter("roundTrailerImage")
    public static void setRoundTrailerImage(final ImageView imageView, String url) {
        final Context context = imageView.getContext();
        Glide.with(context).load(url).asBitmap().listener(new RequestListener<String, Bitmap>() {
            @Override
            public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {

                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                // circularBitmapDrawable.setCornerRadius(15.0f);
                imageView.setImageDrawable(circularBitmapDrawable);
                return false;
            }
        }).into(imageView);
    }


}
