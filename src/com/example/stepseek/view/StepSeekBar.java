package com.example.stepseek.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import com.example.stepseek.R;

public class StepSeekBar extends View implements OnTouchListener {
	private float nowX;
	private OnChangedListener listener;
	private int split = 2;
	private float lineWidth;
	private float lineHeight;
	private int lineColor;
	private float normalPointR;
	private int normalPointColor;
	private float movePointR;
	private int movePointColor;
	private float maxPadding;
	private float textMarginTop;
	private float textSize;
	private int textColor;
	private String[] splitDes;
	private String[] defaultSplitDes;

	public StepSeekBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray array = context.obtainStyledAttributes(attrs,
				R.styleable.StepSeekBar);
		lineWidth = array.getDimension(R.styleable.StepSeekBar_line_width, 720);
		lineHeight = array
				.getDimension(R.styleable.StepSeekBar_line_height, 10);
		lineColor = array
				.getColor(R.styleable.StepSeekBar_line_color, 0XEED5D2);
		normalPointR = array.getDimension(
				R.styleable.StepSeekBar_normal_point_r, 20);
		normalPointColor = array.getColor(
				R.styleable.StepSeekBar_normal_point_color, 0XEEC591);
		movePointR = array.getDimension(R.styleable.StepSeekBar_move_point_r,
				30);
		movePointColor = array.getColor(
				R.styleable.StepSeekBar_move_point_color, 0XEE9A00);
		textMarginTop = array.getDimension(
				R.styleable.StepSeekBar_text_marginTop, 0);
		textSize = array.getDimensionPixelSize(
				R.styleable.StepSeekBar_text_size, 20);
		textColor = array
				.getColor(R.styleable.StepSeekBar_text_color, 0X000000);
		split = array.getInt(R.styleable.StepSeekBar_split, 3);
		splitDes = array.getString(R.styleable.StepSeekBar_text_splitDes)
				.split("#");
		array.recycle();
		init();
	}

	public void moveTo(int index) {
		nowX = lineWidth / split * index;
	}

	private void init() {
		maxPadding = Math.max(normalPointR, movePointR);
		maxPadding = Math.max(maxPadding, lineHeight);
		maxPadding = 50;
		setOnTouchListener(this);
	}

	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int measuredHeight = measureHeight(heightMeasureSpec);
		int measuredWidth = measureWidth(widthMeasureSpec);
		setMeasuredDimension(measuredWidth, measuredHeight);
	}

	private int measureWidth(int widthMeasureSpec) {

		return (int) (lineWidth + maxPadding * 2);
	}

	private int measureHeight(int heightMeasureSpec) {

		return (int) (maxPadding * 4 + textMarginTop);
	}

	public String getSplitText(int index) {
		if (splitDes != null && splitDes.length == split + 1) {
			return splitDes[index];
		}
		return defaultSplitDes[index];
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		// »­±³¾°Ïß
		paint.setColor(lineColor);
		paint.setStrokeWidth(lineHeight);
		paint.setStyle(Style.FILL);
		canvas.drawLine(maxPadding, maxPadding, maxPadding + lineWidth,
				maxPadding, paint);
		// »­·Ö¸îµã
		paint.setStyle(Paint.Style.FILL);
		float perWidth = lineWidth / split;
		String str = "";
		float w = 0;
		for (int i = 0; i <= split; i++) {
			paint.setColor(normalPointColor);
			canvas.drawCircle(perWidth * i + maxPadding, maxPadding,
					normalPointR, paint);
			// Ð´×Ö
			if (splitDes != null && splitDes.length == split + 1) {
				str = splitDes[i];
			} else {
				if (defaultSplitDes == null) {
					defaultSplitDes = new String[split + 1];
				}
				str = "²âÊÔ" + (i + 1);
				defaultSplitDes[i] = str;
			}
			w = paint.measureText(str);
			paint.setTextSize(textSize);
			paint.setColor(textColor);
			if (i == 0) {
				canvas.drawText(str, 0, maxPadding * 2f + textMarginTop, paint);
			} else if (i == split) {
				canvas.drawText(str, getWidth() - w, maxPadding * 2f
						+ textMarginTop, paint);
			} else {
				canvas.drawText(str, perWidth * i + maxPadding - w / 2,
						maxPadding * 2f + textMarginTop, paint);
			}
		}
		// »­ÍÏ¶¯ µÄÔ²µã
		paint.setColor(movePointColor);
		paint.setStyle(Paint.Style.FILL);
		if (nowX < maxPadding + movePointR) {
			canvas.drawCircle(maxPadding, maxPadding, movePointR, paint);
		} else if (nowX > maxPadding + lineWidth + movePointR) {
			canvas.drawCircle(maxPadding + lineWidth, maxPadding, movePointR,
					paint);
		} else {
			canvas.drawCircle(nowX + maxPadding, maxPadding, movePointR, paint);
		}

	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if (event.getX() > lineWidth + maxPadding
					|| event.getY() > maxPadding * 2) {
				return false;
			} else {
				nowX = event.getX();
			}
			break;
		case MotionEvent.ACTION_MOVE:
			nowX = event.getX();
			break;
		case MotionEvent.ACTION_UP:
			float perWidth = lineWidth / (split * 2);
			int index = (int) ((event.getX() - maxPadding) / perWidth);
			index = index % 2 == 0 ? index / 2 : index / 2 + 1;
			if (index < 0) {
				index = 0;
			}
			if (index > split) {
				index = split;
			}
			nowX = index * perWidth * 2;
			if (event.getX() > lineWidth) {
				nowX = lineWidth;
			}

			if (listener != null) {
				listener.OnChanged(StepSeekBar.this, index);
			}
		}
		invalidate();
		return true;

	}

	public void setOnChangedListener(OnChangedListener listener) {
		this.listener = listener;
	}

	public interface OnChangedListener {
		public void OnChanged(StepSeekBar stepSeekBar, int index);
	}
}
