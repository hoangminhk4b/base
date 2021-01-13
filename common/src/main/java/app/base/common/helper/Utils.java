package app.base.common.helper;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class Utils {
  public static TrustManager[] trustAllCerts = new TrustManager[]{
            new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[]{};
                }
            }
    };;

  public static void showSoftKeyboard(Activity activity, EditText editText) {
    InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
  }

  public static void hideSoftKeyboard(Activity activity) {
    if (activity == null) {
                return;
            }
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            View v = activity.getCurrentFocus();
            if (imm != null && v != null) {
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
  }

  public static int checkNetwork(Context activity, String text) {
    ConnectivityManager manager = (ConnectivityManager) activity.getSystemService(activity.CONNECTIVITY_SERVICE);

            boolean is3g = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                    .isConnectedOrConnecting();

            boolean isWifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                    .isConnectedOrConnecting();

            if (isWifi) return ConnectivityManager.TYPE_WIFI;
            if (is3g) return ConnectivityManager.TYPE_MOBILE;

            if (!is3g && !isWifi) {
                Toast.makeText(activity, text, Toast.LENGTH_LONG).show();
                return -1;
            }

            return -1;
  }

  /**
   * create dialog waiting when click button send api
   * @param context
   * @return
   */
  public static Dialog getDialogWaiting(Context context) {
    Dialog dialogLoad=new Dialog(context);
            dialogLoad.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialogLoad.setCanceledOnTouchOutside(false);
            dialogLoad.setCancelable(false);
            return dialogLoad;
  }

  /**
   * Using read file json from assets to list with POJO (GSON support)
   * @param context
   * @param filename
   * @param type
   * @param <T>
   * @return
   * @throws IOException
   */
  public static <T> List<T> addDataArrayFromAssetFileJson(Context context, String filename,
      Class<T[]> type) throws Exception {
    //Read file Json from Asset
            InputStream is = context.getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer, "UTF-8");
            //Return list data with Generic T
            List<T> data = new ArrayList<>();
            Gson gson = new Gson();
            T[] arrayClass = gson.fromJson(json, type);
            for (T element : arrayClass) {
                data.add(element);
            }
            return data;
  }

  public static SSLSocketFactory getSSLSocketFactory() {
     // Install the all-trusting trust manager
            SSLContext sslContext = null;
            try {
                sslContext = SSLContext.getInstance("SSL");
                sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (KeyManagementException e) {
                e.printStackTrace();
            }

            SSLSocketFactory sslSocketFactory = null;
            // Create an ssl socket factory with our all-trusting manager
    if(sslContext!=null) sslSocketFactory = sslContext.getSocketFactory();
            return sslSocketFactory;
  }

  public static void hideKeyword(View view, final Activity activity) {
     // Set up touch listener for non-text box views to hide keyboard.
            if (!(view instanceof EditText)) {
                view.setOnTouchListener(new View.OnTouchListener() {
                    public boolean onTouch(View v, MotionEvent event) {
                        hideSoftKeyboard(activity);
                        return false;
                    }
                });
            }

            //If a layout container, iterate over children and seed recursion.
            if (view instanceof ViewGroup) {
                for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                    View innerView = ((ViewGroup) view).getChildAt(i);
                    hideKeyword(innerView,activity);
                }
            }
  }
}
