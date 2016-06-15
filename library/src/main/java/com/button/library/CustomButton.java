package com.button.library;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.widget.Button;


/**
 * Button(圆角Button带点击效果,正常Button带点击效果)
 *
 * @author gqiu
 */
public class CustomButton extends Button {

    private StateListDrawable selector;
    private int radius;
    private int strokeWidth = 2;

    public CustomButton(Context context) {
        super(context);

    }

    public CustomButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setAttributeSet(context, attrs);
    }

    public CustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setAttributeSet(context, attrs);
    }


    private void setAttributeSet(Context context, AttributeSet attrs) {

        if (!isInEditMode()) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.customButton);
            int normalSolid = a.getColor(R.styleable.customButton_normalSolid, Color.TRANSPARENT);
            int pressedSolid = a.getColor(R.styleable.customButton_pressedSolid, Color.TRANSPARENT);
            int strokeColor = a.getColor(R.styleable.customButton_stroke, Color.TRANSPARENT);
            radius = a.getDimensionPixelSize(R.styleable.customButton_roundButtonRadius, 0);
            int leftTopRadius = a.getDimensionPixelSize(R.styleable.customButton_roundButtonLeftTopRadius, 0);
            int leftBottomRadius = a.getDimensionPixelSize(R.styleable.customButton_roundButtonLeftBottomRadius, 0);
            int rightTopRadius = a.getDimensionPixelSize(R.styleable.customButton_roundButtonRightTopRadius, 0);
            int rightBottomRadius = a.getDimensionPixelSize(R.styleable.customButton_roundButtonRightBottomRadius, 0);
            Drawable normalDrawable = a.getDrawable(R.styleable.customButton_normalDrawable);
            Drawable pressedDrawable = a.getDrawable(R.styleable.customButton_pressedDrawable);
            boolean isSelected = a.getBoolean(R.styleable.customButton_isSelected, false);
            int normalTextColor = a.getColor(R.styleable.customButton_normalTextColor, 0);
            int selectedTextColor = a.getColor(R.styleable.customButton_selectedTextColor, 0);
            strokeWidth = a.getDimensionPixelSize(R.styleable.customButton_strokeWidth, 2);
            int normalStrokeColor = a.getColor(R.styleable.customButton_normalStroke, Color.TRANSPARENT);
            int pressedStokeColor = a.getColor(R.styleable.customButton_pressedStroke, Color.TRANSPARENT);

            a.recycle();


            selector = new StateListDrawable();
            if (normalDrawable != null && pressedDrawable != null) {

                if (isSelected) {
                    selector.addState(new int[]{android.R.attr.state_selected}, pressedDrawable);
                } else {
                    selector.addState(new int[]{android.R.attr.state_pressed}, pressedDrawable);
                }

                selector.addState(new int[]{}, normalDrawable);
                setBackgroundDrawable(selector);
            } else {
                GradientDrawable normalGD = new GradientDrawable();
                normalGD.setColor(normalSolid);


                if (radius != 0) {
                    normalGD.setCornerRadius(radius);
                } else if (leftTopRadius != 0 || leftBottomRadius != 0 || rightTopRadius != 0 || rightBottomRadius != 0) {
                    normalGD.setCornerRadii(new float[]{leftTopRadius, leftTopRadius, rightTopRadius, rightTopRadius, rightBottomRadius, rightBottomRadius, leftBottomRadius, leftBottomRadius});

                }

                if (normalStrokeColor != Color.TRANSPARENT) {
                    normalGD.setStroke(strokeWidth, normalStrokeColor);
                } else {
                    normalGD.setStroke(strokeWidth, strokeColor);
                }

                if (pressedSolid != Color.TRANSPARENT || pressedStokeColor != Color.TRANSPARENT) {
                    GradientDrawable pressedGD = new GradientDrawable();
                    pressedGD.setColor(pressedSolid);
                    if (radius != 0) {
                        pressedGD.setCornerRadius(radius);
                    } else if (leftTopRadius != 0 || leftBottomRadius != 0 || rightTopRadius != 0 || rightBottomRadius != 0) {
                        pressedGD.setCornerRadii(new float[]{leftTopRadius, leftTopRadius, rightTopRadius, rightTopRadius, rightBottomRadius, rightBottomRadius, leftBottomRadius, leftBottomRadius});
                    }

                    if (pressedStokeColor != Color.TRANSPARENT) {
                        pressedGD.setStroke(strokeWidth, pressedStokeColor);
                    } else {
                        pressedGD.setStroke(strokeWidth, strokeColor);
                    }


                    if (isSelected) {
                        selector.addState(new int[]{android.R.attr.state_selected}, pressedGD);
                    } else {
                        selector.addState(new int[]{android.R.attr.state_pressed}, pressedGD);
                    }
                }


                selector.addState(new int[]{}, normalGD);
                setBackgroundDrawable(selector);


                if (normalTextColor != 0 && selectedTextColor != 0) {
                    //设置state_selected状态时，和正常状态时文字的颜色
                    int[][] states = new int[3][1];
                    states[0] = new int[]{android.R.attr.state_selected};
                    states[1] = new int[]{android.R.attr.state_pressed};
                    states[2] = new int[]{};
                    ColorStateList textColorSelect = new ColorStateList(states, new int[]{selectedTextColor, selectedTextColor, normalTextColor});
                    setTextColor(textColorSelect);
                }
            }
        }
    }

    /**
     * 设置Button背景
     */
    public void setBackGround(int normalSolid, int pressedSolid, int normalStroke, int pressedStroke, int roundButtonRadius, boolean isEnableSelected) {
        normalSolid = getResources().getColor(normalSolid);
        pressedSolid = getResources().getColor(pressedSolid);
        normalStroke = getResources().getColor(normalStroke);
        pressedStroke = getResources().getColor(pressedStroke);

        selector = new StateListDrawable();
        GradientDrawable normalGD = new GradientDrawable();
        normalGD.setColor(normalSolid);
        GradientDrawable pressedGD = new GradientDrawable();
        pressedGD.setColor(pressedSolid);

        if (roundButtonRadius < 0) {
            normalGD.setCornerRadius(radius);
            pressedGD.setCornerRadius(radius);
        } else {
            radius = roundButtonRadius;
            normalGD.setCornerRadius(radius);
            pressedGD.setCornerRadius(radius);
        }


        if (normalStroke != 0 && pressedStroke != 0) {
            normalGD.setStroke(strokeWidth, normalStroke);
            pressedGD.setStroke(strokeWidth, pressedStroke);
        }


        if (isEnableSelected) {
            selector.addState(new int[]{android.R.attr.state_selected}, pressedGD);
            selector.addState(new int[]{android.R.attr.state_pressed}, pressedGD);
        } else {
            selector.addState(new int[]{android.R.attr.state_pressed}, pressedGD);
        }

        selector.addState(new int[]{}, normalGD);
        setBackgroundDrawable(selector);
    }


    /**
     * 设置Button背景
     */
    public void setBackGround(int normalSolid, int pressedSolid, int stroke, int roundButtonRadius, boolean isEnableSelected) {
        normalSolid = getResources().getColor(normalSolid);
        pressedSolid = getResources().getColor(pressedSolid);
        stroke = getResources().getColor(stroke);

        selector = new StateListDrawable();
        GradientDrawable normalGD = new GradientDrawable();
        normalGD.setColor(normalSolid);
        GradientDrawable pressedGD = new GradientDrawable();
        pressedGD.setColor(pressedSolid);

        if (roundButtonRadius < 0) {
            normalGD.setCornerRadius(radius);
            pressedGD.setCornerRadius(radius);
        } else {
            radius = roundButtonRadius;
            normalGD.setCornerRadius(radius);
            pressedGD.setCornerRadius(radius);
        }


        if (stroke != 0) {
            normalGD.setStroke(strokeWidth, stroke);
            pressedGD.setStroke(strokeWidth, stroke);
        }


        if (isEnableSelected) {
            selector.addState(new int[]{android.R.attr.state_selected}, pressedGD);
            selector.addState(new int[]{android.R.attr.state_pressed}, pressedGD);
        } else {
            selector.addState(new int[]{android.R.attr.state_pressed}, pressedGD);
        }

        selector.addState(new int[]{}, normalGD);
        setBackgroundDrawable(selector);
    }

    /**
     * 设置Button背景
     */
    public void setBackGround(int normalSolid, int pressedSolid, int roundButtonRadius, boolean isEnableSelected) {
        normalSolid = getResources().getColor(normalSolid);
        pressedSolid = getResources().getColor(pressedSolid);

        selector = new StateListDrawable();
        GradientDrawable normalGD = new GradientDrawable();
        normalGD.setColor(normalSolid);
        GradientDrawable pressedGD = new GradientDrawable();
        pressedGD.setColor(pressedSolid);

        if (roundButtonRadius < 0) {
            normalGD.setCornerRadius(radius);
            pressedGD.setCornerRadius(radius);
        } else {
            radius = roundButtonRadius;
            normalGD.setCornerRadius(radius);
            pressedGD.setCornerRadius(radius);
        }


        if (isEnableSelected) {
            selector.addState(new int[]{android.R.attr.state_selected}, pressedGD);
            selector.addState(new int[]{android.R.attr.state_pressed}, pressedGD);
        } else {
            selector.addState(new int[]{android.R.attr.state_pressed}, pressedGD);
        }

        selector.addState(new int[]{}, normalGD);
        setBackgroundDrawable(selector);
    }


    /**
     * 设置Button文字颜色
     */
    public void setTextColor(int normalTextColor, int selectedTextColor) {
        normalTextColor = getResources().getColor(normalTextColor);
        selectedTextColor = getResources().getColor(selectedTextColor);

        int[][] states = new int[3][1];
        states[0] = new int[]{android.R.attr.state_selected};
        states[1] = new int[]{android.R.attr.state_pressed};
        states[2] = new int[]{};
        ColorStateList textColorSelect = new ColorStateList(states, new int[]{selectedTextColor, selectedTextColor, normalTextColor});
        setTextColor(textColorSelect);
    }


    @Override
    public void setEnabled(boolean enabled) {
        if (enabled) {
            setBackgroundDrawable(selector);
        } else {
            if (radius != 0) {
                GradientDrawable tempBackGround = new GradientDrawable();
                tempBackGround.setColor(getResources().getColor(R.color.gray_4));
                tempBackGround.setCornerRadius(radius);
                setBackgroundDrawable(tempBackGround);
            } else {
                setBackgroundColor(getResources().getColor(R.color.gray_4));
            }

        }

        super.setEnabled(enabled);
    }

    public void setEnabled(boolean enabled,int color) {
        if (enabled) {
            setBackgroundDrawable(selector);
        } else {
            if (radius != 0) {
                GradientDrawable tempBackGround = new GradientDrawable();
                tempBackGround.setColor(getResources().getColor(color));
                tempBackGround.setCornerRadius(radius);
                setBackgroundDrawable(tempBackGround);
            } else {
                setBackgroundColor(getResources().getColor(color));
            }

        }

        super.setEnabled(enabled);
    }
}