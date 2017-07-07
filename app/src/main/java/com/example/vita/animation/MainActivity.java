package com.example.vita.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.animation.ValueAnimatorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageView mImageView;
    private ImageView mImageViewStar;
    private RelativeLayout mRelativeLayout;
    private Context mContext;
    private Button mTraceButton;
    private Button mBujianButton;
    private Button mZuheButton;
    private int height;
    private int width;
    private  final  String TAG = "#########";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindView();
        mContext = this;

    }

    @Override
    protected void onStart() {
        super.onStart();
        mBujianButton.setOnClickListener(this);
        mTraceButton.setOnClickListener(this);
        mZuheButton.setOnClickListener(this);

    }

    private void bindView(){
        mImageView =(ImageView)findViewById(R.id.img_show);
        mTraceButton = (Button)findViewById(R.id.shuxing1);
        mBujianButton = (Button)findViewById(R.id.bujian);
        mRelativeLayout = (RelativeLayout)findViewById(R.id.layout_root);
        mZuheButton = (Button)findViewById(R.id.shuxing2);
        mImageViewStar =(ImageView)findViewById(R.id.star);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bujian:animation();break;
            case R.id.shuxing1:lineAmitator();break;
            case R.id.shuxing2:zuheAmitator();break;

        }
    }

    private  void  animation(){
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.anim_set);
        mImageView.startAnimation(animation);

    }

    private void viewMove(View view,int x,int y){
        int left = x-view.getWidth()/2;
        int top = y - view.getHeight();
        int right = left + view.getWidth();
        int bottom = top+view.getHeight();

        view.layout(left,top,right,bottom);
    }


    private void lineAmitator(){
        height = mRelativeLayout.getHeight();
        width = mRelativeLayout.getWidth();
        ValueAnimator xValue = ValueAnimator.ofInt(height,height/3*2,height/3,(mImageView.getHeight())/5*4);
        xValue.setDuration(3000);
        xValue.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int y = (Integer) animation.getAnimatedValue();
                int x = width/2;
                viewMove(mImageView,x,y);
            }
        });
        xValue.setInterpolator(new BounceInterpolator());
        xValue.start();
        Log.i(TAG, "start: ");

    }
    private  void  zuheAmitator(){
        height = mRelativeLayout.getHeight();
        width = mRelativeLayout.getWidth();
        ValueAnimator valueround = ValueAnimator.ofFloat(0f,(float) (2*(Math.PI)));
        ValueAnimator valuer = ValueAnimator.ofInt(0,360);
//        valueround.setDuration(3000);
        valueround.setRepeatCount(1);
        valueround.setRepeatMode(ValueAnimator.REVERSE);
        valueround.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float arg = (Float) animation.getAnimatedValue();
                int r = width/4;
                int x = width/2+(int) (r*Math.sin(arg));
                int y = height/2 +(int)(r*Math.cos(arg));
                viewMove(mImageViewStar,x,y);
            }
        });
//        valuer.setDuration(3000);
        valuer.setRepeatCount(1);
        valuer.setRepeatMode(ValueAnimator.REVERSE);
        valuer.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mImageViewStar.setRotation((int)animation.getAnimatedValue());
            }
        });

        ObjectAnimator obscalex = ObjectAnimator.ofFloat(mImageViewStar,"scaleX",1f,10f);
        ObjectAnimator obscaley = ObjectAnimator.ofFloat(mImageViewStar,"scaleY",1f,10f);
        ObjectAnimator obAlpha = ObjectAnimator.ofFloat(mImageViewStar,"alpha",1.0f,0.0f);


        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setInterpolator(new AnticipateOvershootInterpolator());

        animatorSet.play(valueround).with(valuer);
        animatorSet.play(obscalex).with(obscaley).with(obAlpha);
        animatorSet.play(valuer).before(obscalex);

        animatorSet.setDuration(3000);
        animatorSet.start();

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mImageViewStar.setAlpha(1.0f);
                mImageViewStar.setScaleX(1.0f);
                mImageViewStar.setScaleY(1.0f);
            }
        });

    }


}
