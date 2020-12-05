package eu.codeyard.simplenews.ui.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

public class AnimationUtils {

    private static final int FAST_ANIM_TIME = 150;
    private static final int NORMAL_ANIM_TIME = 500;

    private AnimationUtils() {}

    public static void animateContainerHeight(View targetView, int startHeight, int endHeight, AnimationListener animationListener) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(startHeight, endHeight);
        valueAnimator.addUpdateListener(valueAnimator1 -> {
            targetView.getLayoutParams().height = (int) valueAnimator1.getAnimatedValue();
            targetView.requestLayout();
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                if (animationListener != null) {
                    animationListener.onAnimationEnd();
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (animationListener != null) {
                    animationListener.onAnimationEnd();
                }
            }
        });
        valueAnimator.setDuration(NORMAL_ANIM_TIME);
        valueAnimator.start();
    }

    public static void swapImage(ImageView targetView, Drawable targetImage) {
        scaleDownAnimation(targetView, new AnimationListener() {
            @Override
            public void onAnimationEnd() {
                targetView.setImageDrawable(targetImage);
                scaleUpAnimation(targetView, null);
            }
        });
    }

    private static void scaleDownAnimation(ImageView targetView, AnimationListener animationListener) {
        AnimatorSet animatorSet = new AnimatorSet();

        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(targetView, View.SCALE_X, 1f, 0.4f);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(targetView, View.SCALE_Y, 1f, 0.4f);
        scaleYAnimator.setInterpolator(new AccelerateInterpolator());
        scaleXAnimator.setInterpolator(new AccelerateInterpolator());
        scaleXAnimator.setDuration(FAST_ANIM_TIME);
        scaleYAnimator.setDuration(FAST_ANIM_TIME);
        scaleXAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (animationListener != null) {
                    animationListener.onAnimationEnd();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                if (animationListener != null) {
                    animationListener.onAnimationEnd();
                }
            }
        });

        animatorSet.playTogether(scaleXAnimator, scaleYAnimator);
        animatorSet.start();
    }

    private static void scaleUpAnimation(ImageView targetView, AnimationListener animationListener) {
        AnimatorSet animatorSet = new AnimatorSet();

        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(targetView, View.SCALE_X, 0.4f, 1f);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(targetView, View.SCALE_Y, 0.4f, 1f);
        scaleYAnimator.setInterpolator(new DecelerateInterpolator());
        scaleXAnimator.setInterpolator(new DecelerateInterpolator());
        scaleXAnimator.setDuration(FAST_ANIM_TIME);
        scaleYAnimator.setDuration(FAST_ANIM_TIME);
        scaleXAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (animationListener != null) {
                    animationListener.onAnimationEnd();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                if (animationListener != null) {
                    animationListener.onAnimationEnd();
                }
            }
        });

        animatorSet.playTogether(scaleXAnimator, scaleYAnimator);
        animatorSet.start();
    }

    public interface AnimationListener {
        void onAnimationEnd();
    }
}
