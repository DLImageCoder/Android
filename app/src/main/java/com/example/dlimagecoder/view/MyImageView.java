package com.example.dlimagecoder.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.example.dlimagecoder.R;

/**
 * Created by lenovo on 2017/8/24.
 */

public class MyImageView extends android.support.v7.widget.AppCompatImageView {

    private float mHeight;

    public MyImageView(Context context) {
        this(context,null);
    }

    public MyImageView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array=context.obtainStyledAttributes(attrs, R.styleable.MyImageView,defStyleAttr,0);
        for (int i=0;i<array.length();i++){
            int attr=array.getIndex(i);//取出每一个属性的ID
            if (attr== R.styleable.MyImageView_mHeight){
                mHeight =array.getFloat(i,1);
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width=MeasureSpec.getSize(widthMeasureSpec);

        setMeasuredDimension(width, (int) (width* mHeight));
    }
}
