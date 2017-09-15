package com.example.administrator.loginreviewhero;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.request.animation.ViewPropertyAnimation;

public class Util {
    Util(){

    }

    public static boolean validateValueAccount(String username, String password, Context context) {
        boolean isEmpty = false;
        if (TextUtils.equals(username, "")) {
            isEmpty = true;
            Toast.makeText(context, "Please input Username", Toast.LENGTH_LONG).show();
            return isEmpty;
        } else if (TextUtils.equals(password, "")) {
            isEmpty = true;
            Toast.makeText(context, "Please input Password", Toast.LENGTH_LONG).show();
            return isEmpty;
        }
        return false;
    }

    public static boolean validateSignUp (String insertUsername, String insertPassword,
                                          String insertName, String insertEmail, String insertImage,
                                          String insertTel, Context context) {
        boolean isEmpty = false;
        if (insertUsername.equals("")) {
            isEmpty = true;
            Toast.makeText(context, "Please input Username", Toast.LENGTH_LONG).show();
            return isEmpty;
        } else if (insertPassword.equals("")) {
            isEmpty = true;
            Toast.makeText(context, "Please input Password", Toast.LENGTH_LONG).show();
            return isEmpty;
        } else if (insertName.equals("")) {
            isEmpty = true;
            Toast.makeText(context, "Please input Name", Toast.LENGTH_LONG).show();
            return isEmpty;
        } else if (insertEmail.equals("")) {
            isEmpty = true;
            Toast.makeText(context, "Please input Email", Toast.LENGTH_LONG).show();
            return isEmpty;
        } else if (insertImage.equals("")) {
            isEmpty = true;
            Toast.makeText(context, "Please input Image", Toast.LENGTH_LONG).show();
            return isEmpty;
        } else if (insertTel.equals("")) {
            isEmpty = true;
            Toast.makeText(context, "Please input Tel", Toast.LENGTH_LONG).show();
            return isEmpty;
        }
        return false;
    }

    public static void setDownloadImageView(Context context, String url, ImageView imgView) {
        ViewPropertyAnimation.Animator animationObject = new ViewPropertyAnimation.Animator() {
            @Override
            public void animate(View view) {
                view.setAlpha(0f);
                ObjectAnimator fadeAnim = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f);
                fadeAnim.setDuration(2500);
                fadeAnim.start();
            }
        };

        Glide.with(context)
                .load(url)
//                .transform(new CircleTransform(context))
                .placeholder(R.drawable.bg_loading)
                .animate(animationObject)
                .into(imgView);
    }
    public static void setLoadImageProfile(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .transform(new CircleTransform(context))
                .placeholder(R.drawable.logo_hero)
                .into(imageView);
    }


    public static class CircleTransform extends BitmapTransformation {
        public CircleTransform(Context context) {
            super(context);
        }

        @Override
        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            return circleCrop(pool, toTransform);
        }

        private static Bitmap circleCrop(BitmapPool pool, Bitmap source) {
            if (source == null) return null;

            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;

            // TODO this could be acquired from the pool too
            Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);

            Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
            if (result == null) {
                result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
            }

            Canvas canvas = new Canvas(result);
            Paint paint = new Paint();
            paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
            paint.setAntiAlias(true);
            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);
            return result;
        }

        @Override
        public String getId() {
            return getClass().getName();
        }
    }
}
