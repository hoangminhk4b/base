package app.base.common.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SharePreferencesLoader {
  private static SharedPreferences mPreferences;

  private static SharePreferencesLoader sharePreferencesLoader;

  private int PREFERENCES_MODE = android.app.Activity.MODE_PRIVATE;

  private String PREFERENCES_NAME = "";

  private Context mContext;

  private SharePreferencesLoader() {
  }

  public static synchronized SharePreferencesLoader getInstance() {
    if(sharePreferencesLoader==null) {
      sharePreferencesLoader = new SharePreferencesLoader();
    }
    return sharePreferencesLoader;
  }

  public SharePreferencesLoader setupContext(Context pContext) {
    this.mContext = pContext.getApplicationContext();
    this.PREFERENCES_NAME = mContext.getPackageName();
    return this;
  }

  public SharePreferencesLoader setupPreferencesName(final String name) {
    PREFERENCES_NAME = name;
    return this;
  }

  public SharePreferencesLoader setupPreferencesMode(final int mode) {
    PREFERENCES_MODE = mode;
    return this;
  }

  public SharedPreferences build() {
    mPreferences = mContext.getSharedPreferences(PREFERENCES_NAME, PREFERENCES_MODE);
    return mPreferences;
  }

  public Editor saveValueToSharedPreferences() {
    return mPreferences.edit();
  }

  public void remove(final String pKeyName) {
    if (pKeyName != null) {
      this.saveValueToSharedPreferences().remove(pKeyName).commit();
    }
  }

  public void saveValueToSharedPreferences(final String pKeyName, final String pValue) {
    saveValueToSharedPreferences().putString(pKeyName, pValue).commit();
  }

  public void saveValueToSharedPreferences(final String pKeyName, final int pValue) {
    saveValueToSharedPreferences().putInt(pKeyName, pValue).commit();
  }

  public void saveLongValueToSharedPreferences(final String pKeyName, final long pValue) {
    saveValueToSharedPreferences().putLong(pKeyName, pValue).commit();
  }

  public void saveValueToSharedPreferences(final String pKeyName, final boolean pValue) {
    saveValueToSharedPreferences().putBoolean(pKeyName, pValue).commit();
  }

  public void saveValueToSharedPreferences(final String pKeyName, final Object obj) {
    Gson gson= new Gson();
    String json = gson.toJson(obj);;
    if (json != null) {
      saveValueToSharedPreferences().putString(pKeyName, json).commit();
    }
  }

  public <T> Object getValueFromPreferences(final String pKeyName, final String pDefaultValue,
      T pClass) {
    Gson gson= new Gson();
    String json = mPreferences.getString(pKeyName, pDefaultValue);
    Object object=gson.fromJson(json, pClass.getClass());
    return object;
  }

  public <T> void saveValueList(final String pKeyName, final Type type, List<T> list) {
    Gson gson= new Gson();
    //   Type type = new TypeToken<List<T>>(){}.getType();
    String json = gson.toJson(list, type);
    if (json != null) {
      saveValueToSharedPreferences().putString(pKeyName, json).commit();
    }
  }

  public <T> ArrayList<T> getValueList(final String pKeyName, final Type type,
      final String pDefaultValue) {
    Gson gson= new Gson();
    String json = mPreferences.getString(pKeyName, pDefaultValue);
    //  Type type = new TypeToken<List<T>>(){}.getType();
    ArrayList<T> list = gson.fromJson(json, type);
    return list;
  }

  public String getValueFromPreferences(final String pKeyName, final String pDefaultValue) {
    return mPreferences.getString(pKeyName, pDefaultValue);
  }

  public int getValueFromPreferences(final String pKeyName, final int pDefaultValue) {
    return mPreferences.getInt(pKeyName, pDefaultValue);
  }

  public long getValueLongFromPreferences(final String pKeyName, final long pDefaultValue) {
    return mPreferences.getLong(pKeyName, pDefaultValue);
  }

  public void onDestroy() {
    mContext = null;
    mPreferences = null;
    sharePreferencesLoader = null;
  }

  public boolean getValueFromPreferences(final String pKeyName, final boolean pDefaultValue) {
    return mPreferences.getBoolean(pKeyName, pDefaultValue);
  }
}
