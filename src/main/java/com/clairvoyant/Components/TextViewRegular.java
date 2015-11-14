package com.clairvoyant.Components;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import com.clairvoyant.Utils.Constants;

public class TextViewRegular extends TextView {

	public TextViewRegular(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
		init();
	}

	public TextViewRegular(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public TextViewRegular(Context context) {
		super(context);
		init();
	}

	private void init() {
		if (!isInEditMode()) {
			Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
                    Constants.FONT_CIRCULAR);
			setTypeface(tf);
		}
	}

	@Override
	public void setError(CharSequence error) {
		if (error != null) {
			notifyUser(this);
		}
		super.setError(error);
	}

	private void notifyUser(TextView textView) {
		Animation shake = new TranslateAnimation(0, 7, 0, 0);
		shake.setInterpolator(new CycleInterpolator(5));
		shake.setDuration(500);
		textView.startAnimation(shake);
	}
}