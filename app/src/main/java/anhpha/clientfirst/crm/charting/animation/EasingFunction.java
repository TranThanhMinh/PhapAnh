package anhpha.clientfirst.crm.charting.animation;

import android.animation.TimeInterpolator;
import android.annotation.SuppressLint;

/**
 * Interface for creating custom made easing functions. Uses the
 * TimeInterpolator interfaces provided by Android.
 */
@SuppressLint("NewApi")
public interface EasingFunction extends TimeInterpolator {

    @Override
    float getInterpolation(float input);
}
