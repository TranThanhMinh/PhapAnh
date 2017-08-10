package anhpha.clientfirst.crm.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.activity.LoginActivity;
import anhpha.clientfirst.crm.configs.Constants;
import anhpha.clientfirst.crm.configs.Preferences;
import anhpha.clientfirst.crm.model.Errors;
import anhpha.clientfirst.crm.model.MErrors;
import anhpha.clientfirst.crm.sweet.SweetAlert.SweetAlertDialog;

/**
 * Created by Window7 on 3/15/2017.
 */
public class TokenUtils {

    public static void checkToken(final Context context, MErrors response) {
        if(response.getID() == 4) {
            SweetAlertDialog pDialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText(context.getString(R.string.token_fail));
            pDialog.setCancelable(false);
            pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sDialog) {
                    Preferences prefs = new Preferences(context);
                    prefs.putBooleanValue(Constants.USER_LOGGED_IN, true);
                    prefs.putIntValue(Constants.USER_ID, 0);

                    Intent intent = new Intent(context, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    ((Activity) context).finish();
                }
            });
            pDialog.show();
        }

    }
}
