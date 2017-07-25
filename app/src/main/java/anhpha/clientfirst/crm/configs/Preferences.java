package anhpha.clientfirst.crm.configs;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

/**
 * Created by Nguyen on 4/20/2015.
 */
public class Preferences {
    private static final String SHARED_PREFERENCES_NAME = "LIXIDO_OUTLET_PARTNER";
    SharedPreferences mPreferences;

    public Preferences(Context context) {
        mPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public void putBooleanValue(String KEY, boolean value) {
        mPreferences.edit().putBoolean(KEY, value).commit();
    }

    public boolean getBooleanValue(String KEY, boolean defValue) {
        return mPreferences.getBoolean(KEY, defValue);
    }

    public void putStringValue(String KEY, String value) {
        mPreferences.edit().putString(KEY, value).commit();
    }

    public String getStringValue(String KEY, String defValue) {
        return mPreferences.getString(KEY, defValue);
    }

    public void putIntValue(String KEY, int value) {
        mPreferences.edit().putInt(KEY, value).commit();
    }

    public int getIntValue(String KEY, int defValue) {
        return mPreferences.getInt(KEY, defValue);
    }

    public void putLongValue(String KEY, long value) {
        mPreferences.edit().putLong(KEY, value).commit();
    }

    public long getLongValue(String KEY, long defValue) {
        return mPreferences.getLong(KEY, defValue);
    }

    public void putFloatValue(String KEY, float value) {
        mPreferences.edit().putFloat(KEY, value).commit();
    }

    public float getFloatValue(String KEY, float defValue) {
        return mPreferences.getFloat(KEY, defValue);
    }

    public void putStringSet(String KEY, Set<String> set) {
        mPreferences.edit().putStringSet(KEY, set).commit();
    }

    public Set<String> getStringSet(String KEY, Set<String> defValue) {
        return mPreferences.getStringSet(KEY, defValue);
    }

    public void removeValue(String KEY) {
        mPreferences.edit().remove(KEY).commit();
    }

    public void clear() {
        mPreferences.edit().clear().commit();
    }
}
