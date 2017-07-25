package anhpha.clientfirst.crm.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.Normalizer;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.model.MDistrict;
import anhpha.clientfirst.crm.model.MContract;
import anhpha.clientfirst.crm.model.MProvince;
import anhpha.clientfirst.crm.model.MWard;
import anhpha.clientfirst.crm.sweet.SweetAlert.SweetAlertDialog;
import anhpha.clientfirst.crm.topsnackbar.TSnackbar;

/**
 * Created by Nguyen on 4/24/2015.
 */
public class Utils {

    public static final String TAG = Utils.class.getSimpleName();
    public static NumberFormat formatter = new DecimalFormat("###,###,###,###,###,###,###.##");


    private static SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy");
    private static SimpleDateFormat sdf3 = new SimpleDateFormat("dd/MM/yyyy");
    private static SimpleDateFormat sdf2 = new SimpleDateFormat("MM/dd/yyyy hh:mm");
    private static SimpleDateFormat sdf4 = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aa");
    /**
     * PROGRESS DIALOG
     */

    private static ProgressDialog progressDialog;
    private static String mCost;

    public static Calendar tryParseDateDMY(String s) {
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(sdf3.parse(s));
            return calendar;
        } catch (Exception ex) {
            LogUtils.e(TAG, "tryParseDateDMY", ex);
        }
        return null;
    }
    public static String removeAccent(String s) {

        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("");
    }
    public static Calendar tryParseDate(String s) {
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(sdf1.parse(s));
            return calendar;
        } catch (Exception ex) {
            LogUtils.e(TAG, "tryParseDate", ex);
        }
        return null;
    }

    public static Calendar tryParseDateTime(String s) {
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(sdf2.parse(s));
            return calendar;
        } catch (Exception ex) {
            LogUtils.e(TAG, "tryParseDateTime", ex);
        }
        return null;
    }

    public static EditText setEditInvoice(final EditText etCost) {
        etCost.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() != 0) {
                    try {
                        etCost.removeTextChangedListener(this);
                        etCost.setText(mCost);
                        etCost.setSelection(mCost.length());
                        etCost.addTextChangedListener(this);
                    } catch (Exception e) {

                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                double value = -1;
                try {
                    value = Utils.tryParseDouble(s.toString());
                } catch (Exception ex) {
                    value = -1;
                }

                if (value >= 0) {
                    mCost = Utils.formatCurrency(value);
                }
                // etCost.setText(mCost);
            }
        });
        return etCost;
    }

    public static Calendar tryParseTime(String s) {
        Calendar calendar = Calendar.getInstance();
        try {
            String[] splits = s.split(":");
            calendar.set(0, 0, 0, Integer.parseInt(splits[0]), Integer.parseInt(splits[1]));
            return calendar;
        } catch (Exception ex) {
            LogUtils.e(TAG, "tryParseTime", ex);
        }
        return null;
    }


    public static void promptUserEnableGPS(Context context) {
        Intent gpsOptionsIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        Toast.makeText(context, R.string.require_active_gps, Toast.LENGTH_SHORT).show();
        context.startActivity(gpsOptionsIntent);
    }

    public static int tryParse(String s) {
        try {
            s = s.replace(",", "");
            s = s.replace("%", "");
            s = s.replace(" ", "");
            return formatter.getNumberInstance(Locale.US).parse(s).intValue() == 0 ? Integer.parseInt(s) : formatter.getNumberInstance(Locale.US).parse(s).intValue();
        } catch (Exception ex) {
            return 0;
        }
    }

    public static double tryParseDouble(String s) {
        try {
            s = s.replace(",", "");
            s = s.replace("%", "");
            s = s.replace(" ", "");

            return formatter.getNumberInstance(Locale.US).parse(s).doubleValue() == 0 ? Double.parseDouble(s) : formatter.getNumberInstance(Locale.US).parse(s).doubleValue();


        } catch (Exception ex) {
            return 0;
        }
    }


    public static String formatterNumber(Number number) {
        return formatter.getNumberInstance(Locale.US).format(number);
    }

    public static void setListViewHeightBasedOnChildren(ListView listView, boolean isEqual) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;

        View view = listAdapter.getView(0, null, listView);
        if (view != null) {
            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight = view.getMeasuredHeight() * listAdapter.getCount();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        if (totalHeight == 0) {
            params.height = 0;
        } else {
            params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        }
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View view = listAdapter.getView(i, null, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    public static void setEnableViewGroup(ViewGroup viewGroup, boolean value) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View view = viewGroup.getChildAt(i);
            view.setEnabled(value);
        }
    }

    public static String getRequestString(String url, HashMap<String, String> params) {

        if (params == null || params.size() == 0) {
            return url;
        }

        StringBuilder sb = new StringBuilder();

        sb.append(url);
        sb.append("?");

        for (Map.Entry<String, String> entry : params.entrySet()) {
            sb.append(entry.getKey() + "=" + entry.getValue());
            sb.append("&");
        }

        return sb.substring(0, sb.length() - 1);
    }


    /**
     * HIDE KEYBOARD
     */

    public static void hideSoftKeyboard(Activity activity) {
        try {
            if (activity == null) {
                return;
            }
            InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            View focusedView = activity.getCurrentFocus();
            if (focusedView != null) {
                inputManager.hideSoftInputFromWindow(
                        focusedView.getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }

        } catch (Exception ex) {
            LogUtils.e(TAG, "hideSoftKeyboard", ex);
        }
        // make sure the keyboard goes away when the user selects something outside the view (cancelled outside)
        try {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            // not the search view but the current focus at this point
            imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);

        } catch (Exception e) {
        }
        // try hide the keyboard

    }

    public static void hideSoftKeyboardWhenClickOnScreen(View viewGroup, final Activity activity) {

        if (!(viewGroup instanceof EditText)) {
            viewGroup.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(activity);
                    return false;
                }
            });
        }

        if (viewGroup instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) viewGroup).getChildCount(); i++) {
                View innerView = ((ViewGroup) viewGroup).getChildAt(i);
                hideSoftKeyboardWhenClickOnScreen(innerView, activity);
            }
        }
    }

    /**
     * EXPANDABLE VIEW
     */

    public static void expand(final View v) {
        v.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final int targetHeight = v.getMeasuredHeight();

        v.getLayoutParams().height = 0;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? ViewGroup.LayoutParams.WRAP_CONTENT
                        : (int) (targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        a.setDuration(((int) (targetHeight / v.getContext().getResources().getDisplayMetrics().density)) * 4);
        v.startAnimation(a);
    }

    public static void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    v.setVisibility(View.GONE);
                } else {
                    v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        a.setDuration(((int) (initialHeight / v.getContext().getResources().getDisplayMetrics().density)) * 4);
        v.startAnimation(a);
    }

    public static Class<?> getClass(String className) {

        Class<?> c = null;
        if (className != null) {
            try {
                c = Class.forName(className);
            } catch (ClassNotFoundException e) {
                LogUtils.e(TAG, "getClass", e);
            }
        }
        return c;
    }

    public static void fixMapFragmentInsideScrollView(final ScrollView mainScrollView, ImageView transparentImageView) {
        transparentImageView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        mainScrollView.requestDisallowInterceptTouchEvent(true);
                        // Disable touch on transparent view
                        return false;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        mainScrollView.requestDisallowInterceptTouchEvent(false);
                        return true;

                    case MotionEvent.ACTION_MOVE:
                        mainScrollView.requestDisallowInterceptTouchEvent(true);
                        return false;

                    default:
                        return true;
                }
            }
        });
    }

    public static void setEditPrice(final EditText etCost) {
        etCost.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() != 0) {
                    etCost.removeTextChangedListener(this);
                    etCost.setText(mCost);
                    etCost.setSelection(mCost.length());
                    etCost.addTextChangedListener(this);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                double value = -1;
                try {
                    value = Double.parseDouble(s.toString().replace(",", ""));
                } catch (Exception ex) {
                    value = -1;
                }

                if (value >= 0) {
                    mCost = Utils.formatCurrency(value);
                }
            }
        });
    }

    public static String formatCurrency(double value) {
        try {
            String pattern = "###,###,###,###,###,###";
            DecimalFormat decimalFormat = new DecimalFormat(pattern);
            //String a = NumberFormat.getNumberInstance(java.util.Locale.US).format(value);
            return NumberFormat.getNumberInstance(java.util.Locale.US).format(Utils.tryParseDouble(String.format("%.0f", (double) value)));
            // return decimalFormat.getNumberInstance(Locale.US).format(value);
        } catch (Exception e) {
            return "";
        }
    }

    public static String formatPercent(double value) {
        try {
            String pattern = "###,###,###,###,###,###.##";
            DecimalFormat decimalFormat = new DecimalFormat(pattern);
           // return NumberFormat.getNumberInstance(java.util.Locale.US).format(value);
            return String.format("%.2f", (double) value);
            // return decimalFormat.getNumberInstance(Locale.US).format(value);
        } catch (Exception e) {
            return "";
        }
    }

    public static boolean isEmpty(String value) {
        if (value == null) {
            return true;
        } else if (value.isEmpty()) {
            return true;
        }
        return false;
    }

    public static String trim(String value) {
        if (value == null) {
            return "";
        }
        return value.trim();
    }

    public static void openBrowser(Context context, String url) {
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "http://" + url;
        }
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        context.startActivity(browserIntent);
    }

    public static int dpToPx(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

    public static Bitmap rotateBitmap(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    public static int getWidth(Context mContext) {
        int width = 0;
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        if (Build.VERSION.SDK_INT > 12) {
            Point size = new Point();
            display.getSize(size);
            width = size.x;
        } else {
            width = display.getWidth();  // Deprecated
        }
        LogUtils.d("width", "", width + "");
        return width;
    }

    public static int getHeight(Context mContext) {
        int height = 0;
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        if (Build.VERSION.SDK_INT > 12) {
            Point size = new Point();
            display.getSize(size);
            height = size.y;
        } else {
            height = display.getHeight();  // Deprecated
        }
        LogUtils.d("height", "", height + "");
        return height;
    }

    public static void showError(View coordinatorLayout, int string) {
        TSnackbar snackbar = TSnackbar
                .make(coordinatorLayout, string, TSnackbar.LENGTH_LONG);
        // Changing message text color
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(Color.RED);
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.show();

    }

    public static void showDialogSuccess(final Context context, int string) {
        SweetAlertDialog pDialog = new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText(context.getString(string));
        pDialog.setCancelable(false);
        pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sDialog) {
                ((Activity) context).finish();
            }
        });
        pDialog.show();
    }


    public static void showDialogSuccess(final Context context, String string) {
        SweetAlertDialog pDialog = new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText((string));
        pDialog.setCancelable(false);
        pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sDialog) {
                ((Activity) context).finish();
            }
        });

        pDialog.show();

    }
    public static void showSuccess(View coordinatorLayout, int string) {
        TSnackbar snackbar = TSnackbar
                .make(coordinatorLayout, string, TSnackbar.LENGTH_LONG);
        // Changing message text color
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(Color.GREEN);
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.BLACK);
        snackbar.show();

    }

    public static String getFromDate() {
        try {

            Calendar c = Calendar.getInstance();
            SimpleDateFormat dateformat = new SimpleDateFormat("MM/dd/yyyy 00:00");
            String datetime = dateformat.format(c.getTime());
            return datetime;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";

    }
    public static String getDate() {
        try {

            Calendar c = Calendar.getInstance();
            SimpleDateFormat dateformat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aa");
            String datetime = dateformat.format(c.getTime());
            return datetime;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";

    }
    public static String getToDateMonth() {
        try {
            Date today = new Date();

            DateFormat to_sdf = new SimpleDateFormat("MM/dd/yyyy 23:59");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(today);

            calendar.add(Calendar.MONTH, 1);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            calendar.add(Calendar.DATE, -1);

            Date toDate = calendar.getTime();

            String to_Date = to_sdf.format(toDate);
            return to_Date;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";

    }

    public static String getFromDateMonth() {
        try {
            DateFormat from_sdf = new SimpleDateFormat("MM/dd/yyyy 00:00");

            Date today = new Date();

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(today);

            calendar.add(Calendar.MONTH, 1);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            calendar.add(Calendar.DATE, -1);

            Date toDate = calendar.getTime();
            calendar.add(Calendar.MONTH, 0);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            calendar.add(Calendar.DATE, 0);
            Date fromDate = calendar.getTime();

            String from_Date = from_sdf.format(fromDate);

            return from_Date;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";

    }

    public static String getToDate() {
        try {
            Calendar c = Calendar.getInstance();
            SimpleDateFormat dateformat = new SimpleDateFormat("MM/dd/yyyy 23:59");
            String datetime = dateformat.format(c.getTime());
            return datetime;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String formatTime(String activity_date) {
        if (activity_date.isEmpty())
            return "-";
        java.util.Date now = new java.util.Date();
        java.util.Date date = new java.util.Date();
        java.util.Date date_begin = new java.util.Date();
        Calendar calendar = Calendar.getInstance();
        DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aa", Locale.ENGLISH);
        DateFormat formatter2 = new SimpleDateFormat("MM/dd/yyyy 00:00:00", Locale.ENGLISH);
        try {
            date = formatter.parse(activity_date);
            date_begin = formatter.parse(formatter2.format(now) + " AM");
            calendar.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {

            long diff = now.getTime() - date.getTime();
            long diff2 = now.getTime() - date_begin.getTime();

            if (diff <= diff2) {
                try {
                    SimpleDateFormat dateformat = new SimpleDateFormat("HH:mm");
                    String datetime = dateformat.format(formatter.parse(activity_date));
                    return datetime;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
                    String datetime = dateformat.format(formatter.parse(activity_date));
                    return datetime;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    public static List<MProvince> getProvince(Context context) throws UnsupportedEncodingException {


        List<MProvince> objList = new ArrayList<>();
        AssetManager assetManager = context.getAssets();
        InputStream is = null;

        try {
            is = assetManager.open("data/csv_location_province.csv");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        BufferedReader reader = null;
        reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
        String line = "";
        StringTokenizer st = null;
        MProvince obj;
        try {

            while ((line = reader.readLine()) != null) {
                st = new StringTokenizer(line + ", ", ",");
                obj = new MProvince();
                obj.setProvince_id(ConvertToInt(st.nextToken()));
                obj.setProvince_code((st.nextToken()));
                obj.setCountry_id(ConvertToInt(st.nextToken()));
                obj.setProvince_status(ConvertToInt(st.nextToken()));
                obj.setStatus_id(ConvertToInt(st.nextToken()));
                obj.setCreate_date(ConertToString(st.nextToken()));
                obj.setModify_date(st.nextToken());
                obj.setProvince_name(st.nextToken());
                obj.setType(st.nextToken());
                obj.setLocation(st.nextToken());
                obj.setCountry_code(st.nextToken());
                objList.add(obj);

            }
            reader.close();
            is.close();

        } catch (IOException e) {

            e.printStackTrace();
        }

        return objList;

    }

    private static String ConertToString(String s) {
        return s;
    }

    public static List<MWard> getWard(Context context) throws UnsupportedEncodingException {


        List<MWard> objList = new ArrayList<>();
        AssetManager assetManager = context.getAssets();
        InputStream is = null;

        try {
            is = assetManager.open("data/csv_location_ward.csv");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        BufferedReader reader = null;
        reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));

        String line = "";
        StringTokenizer st = null;
        MWard obj;
        try {

            while ((line = reader.readLine()) != null) {
                st = new StringTokenizer(line + ", ", ",");
                 obj = new MWard();
                obj.setWard_id(ConvertToInt(st.nextToken()));
                obj.setWard_code(st.nextToken());
                obj.setWard_name(st.nextToken());
                obj.setType(st.nextToken());
                obj.setLocation((st.nextToken()));
                obj.setDistrict_code(st.nextToken());
//
                objList.add(obj);


            }
            reader.close();
            is.close();
        } catch (IOException e) {

            e.printStackTrace();
        }

        return objList;

    }

    public static List<MDistrict> getDistrict(Context context) throws UnsupportedEncodingException {


        List<MDistrict> objList = new ArrayList<>();
        AssetManager assetManager = context.getAssets();
        InputStream is = null;

        try {
            is = assetManager.open("data/csv_location_district.csv");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        BufferedReader reader = null;
        reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));

        String line = "";
        StringTokenizer st = null;
        MDistrict obj;
        try {

            while ((line = reader.readLine()) != null) {
                st = new StringTokenizer(line + ", ", ",");
                obj = new MDistrict();
                obj.setDistrict_id(ConvertToInt(st.nextToken()));
                obj.setDistrict_code(st.nextToken());
                obj.setDistrict_name(st.nextToken());
                obj.setDistrict_description(st.nextToken());
                obj.setStatus_id(ConvertToInt(st.nextToken()));
                obj.setCreate_date(st.nextToken());
                obj.setProvince_id(ConvertToInt(st.nextToken()));
                obj.setModify_date(st.nextToken());
                obj.setSort_order(ConvertToInt(st.nextToken()));
                obj.setType(st.nextToken());
                obj.setLocation(st.nextToken());
                obj.setProvince_code(st.nextToken());

                objList.add(obj);
            }
            reader.close();
            is.close();
        } catch (IOException e) {

            e.printStackTrace();
        }

        return objList;

    }

    public static int ConvertToInt(String s) {
        try {
            return Integer.parseInt(s);
        } catch (Exception e) {
        }
        return 0;
    }

    public static MContract getPriceContract(MContract activityItem, Context mContent) {
        MContract MContract = new MContract();
        int factor = activityItem.getNumber_in_group() > 1 ? activityItem.getNumber_in_group() : 0;
        int num_group = factor == 0 ? 0 : (int) (activityItem.getNumber() / factor);
        int num = (int) (activityItem.getNumber() - (num_group * factor));
        MContract.setNumber((double) num);
        MContract.setIs_price_tax(activityItem.is_price_tax());
        MContract.setTax(activityItem.getTax());
        MContract.setContract_name(activityItem.getContract_name());
        MContract.setNumber_group((double) num_group);
        MContract.setPrice(activityItem.getPrice());
        MContract.setPrice_group(activityItem.getPrice_group());
        MContract.setNumber_in_group(factor);
        MContract.setNote(activityItem.getNote());
        MContract.setPrice_number(num * activityItem.getPrice());
        MContract.setPrice_group_number(num_group * activityItem.getPrice_group());
        MContract.setAmount_price(MContract.getPrice_number() + MContract.getPrice_group_number());
        MContract.setContract_unit_name(num + " " + (activityItem.getContract_unit_name().isEmpty() ? "" : activityItem.getContract_unit_name()));
        MContract.setContract_unit_group_name(num_group + " " + (activityItem.getContract_unit_group_name().isEmpty() ? "" : activityItem.getContract_unit_group_name()));
        return MContract;
    }

    public static String convertTime(String s, Context activity) {

        if (s.isEmpty())
            return "-";
        java.util.Date now = new java.util.Date();
        java.util.Date date = new java.util.Date();

        Calendar calendar = Calendar.getInstance();
        DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aa", Locale.ENGLISH);

        try {
            date = formatter.parse(s);
            calendar.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {

            long diff = now.getTime() - date.getTime();

            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            long diffDays = diff / (24 * 60 * 60 * 1000);

            if (diffDays > 0) {
                return (+diffDays
                        + activity.getString(R.string.day)
                        + diffHours
                        + activity.getString(R.string.hours)
                        + diffMinutes
                        + activity.getString(R.string.minite)
                        + activity.getString(R.string.before));
            } else if (diffHours > 0) {
                return (
                        +diffHours
                                + activity.getString(R.string.hours)
                                + diffMinutes
                                + activity.getString(R.string.minite)
                                + activity.getString(R.string.before));
            } else if (diffMinutes > 0) {
                return (
                        +diffMinutes
                                + activity.getString(R.string.minite)
                                + activity.getString(R.string.before));
            } else if (diffMinutes == 0 && diffHours == 0 && diffDays == 0 && diffSeconds == 0) {
                return ("-");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "-";


    }

    public static boolean convertTime(String s) {

        if (s.isEmpty())
            return false;
        java.util.Date now = new java.util.Date();
        java.util.Date date = new java.util.Date();

        Calendar calendar = Calendar.getInstance();
        DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aa", Locale.ENGLISH);

        try {
            date = formatter.parse(s);
            calendar.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {

            long diff = now.getTime() - date.getTime();

            if (diff > 0) {
                return false;
            } else return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;


    }

    public static java.util.Date convertDateTime(String s) {

        if (s.isEmpty())
            return new java.util.Date();

        java.util.Date date = new java.util.Date();

        Calendar calendar = Calendar.getInstance();
        DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aa", Locale.ENGLISH);

        try {
            date = formatter.parse(s);
            calendar.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;

    }
    public static java.util.Date convertDateTime(String s, String fm) {

        if (s.isEmpty())
            return new java.util.Date();
        ;
        java.util.Date date = new java.util.Date();
        Calendar calendar = Calendar.getInstance();
        DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm", Locale.ENGLISH);

        try {
            date = formatter.parse(s);
            calendar.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;

    }
    public static String convertDistance(int distance) {

        try {
            int m = distance;

            if (m == -1) {
                return "-";
            }
            if (m == 0) {
                return "-";
            }
            if (m < 1000)
                return m + " m";
            else {
                double k = ((double) m) / 1000;
                String rating = String.format("%.2f", k);
                return (rating + " km");
            }
        } catch (Exception e) {
            return "";
        }
    }

    public static String deAccent(String str) {
        String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("");
    }

    public static int updateEvent(Activity curActivity, String title, String addInfo, long startDate, int id) {
        try {
        int iNumRowsUpdated = 0;

        ContentValues eventValues = new ContentValues();

        eventValues.put("_id", id); // id, We need to choose from
        // our mobile for primary
        // its 1
        eventValues.put("title", title);
        eventValues.put("description", addInfo);
        long endDate = startDate + 1000 * 60 * 60; // For next 1hr
        eventValues.put("dtstart", startDate);
        eventValues.put("dtend", endDate);
        Uri eventsUri = Uri.parse("content://com.android.calendar/events");
        Uri eventUri = ContentUris.withAppendedId(eventsUri, id);

        iNumRowsUpdated = curActivity.getContentResolver().update(eventUri, eventValues, null,
                null);
        Log.i("", "Updated " + iNumRowsUpdated + " calendar entry.");

        return iNumRowsUpdated;
        }catch (Exception e){
            return  0;
        }
    }

    public static int deleteEvent(int entryID, Context context) {
        try {
            int iNumRowsDeleted = 0;

            Uri eventsUri = Uri.parse("content://com.android.calendar/events");
            Uri eventUri = ContentUris.withAppendedId(eventsUri, entryID);
            iNumRowsDeleted = context.getContentResolver().delete(eventUri, null, null);
            Log.i("", "Deleted " + iNumRowsDeleted + " calendar entry.");

            return iNumRowsDeleted;
        }catch (Exception e){
            return  0;
        }
    }

    public static double addEvent(Activity curActivity, String title, String addInfo, long startDate, int id) {
        /***************** Event: note(without alert) *******************/
        try {
            String eventUriString = "content://com.android.calendar/events";
            ContentValues eventValues = new ContentValues();

            eventValues.put("calendar_id", 1); // id, We need to choose from
            // our mobile for primary
            // its 1

            eventValues.put("title", title);
            eventValues.put("organizer", id);
            eventValues.put("description", addInfo);
            long endDate = startDate + 1000 * 60 * 60; // For next 1hr
            eventValues.put("dtstart", startDate);
            eventValues.put("dtend", endDate);

            // values.put("allDay", 1); //If it is bithday alarm or such
            // kind (which should remind me for whole day) 0 for false, 1
            // for true
            eventValues.put("eventStatus", 1); // This information is
            // sufficient for most
            // entries tentative (0),
            // confirmed (1) or canceled
            // (2):
            eventValues.put("eventTimezone", "UTC/GMT +7:00");

            eventValues.put("hasAlarm", 1); // 0 for false, 1 for true

            Uri eventUri = curActivity.getApplicationContext().getContentResolver().insert(Uri.parse(eventUriString), eventValues);
            long eventID = Long.parseLong(eventUri.getLastPathSegment());

            /***************** Event: Reminder(with alert) Adding reminder to event *******************/

            String reminderUriString = "content://com.android.calendar/reminders";

            ContentValues reminderValues = new ContentValues();

            reminderValues.put("event_id", eventID);
            reminderValues.put("minutes", 5); // Default value of the
            // system. Minutes is a
            // integer
            reminderValues.put("method", 1); // Alert Methods: Default(0),
            // Alert(1), Email(2),
            // SMS(3)

            Uri reminderUri = curActivity.getApplicationContext().getContentResolver().insert(Uri.parse(reminderUriString), reminderValues);


            return eventID;
        }catch (Exception e){
            return  0 ;
        }

    }

}

