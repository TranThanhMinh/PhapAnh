package anhpha.clientfirst.crm.activity;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.adapter.adapter_Photo_history_order;
import anhpha.clientfirst.crm.configs.Constants;
import anhpha.clientfirst.crm.configs.Preferences;
import anhpha.clientfirst.crm.cropper.CropImage.CropImage;
import anhpha.clientfirst.crm.cropper.CropImage.CropImage_View;
import anhpha.clientfirst.crm.cropper.CropImage.FileUtils;
import anhpha.clientfirst.crm.interfaces.Url;
import anhpha.clientfirst.crm.model.Photo;
import anhpha.clientfirst.crm.model.Result_status_contract;
import anhpha.clientfirst.crm.model.Result_upload_photo;
import anhpha.clientfirst.crm.model.Status;
import anhpha.clientfirst.crm.service_api.ServiceAPI;
import anhpha.clientfirst.crm.sweet.SweetAlert.SweetAlertDialog;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static anhpha.clientfirst.crm.adapter.adapter_History_orders.lvImage;

;

/**
 * Created by MinhTran on 7/13/2017.
 */

public class Edit_status_contract_activity extends BaseAppCompatActivity implements View.OnClickListener, adapter_Photo_history_order.funcDelete_lvImage{
    private RecyclerView lvPhoto;
    private EditText editNote;
    private TextView tvStatus,tvName,tvAddress;
    private View popupView;
    private PopupWindow popupWindow;
    private ImageView imSelect_upload_photo, imBack;
    private Uri selectedImage;
    private Retrofit retrofit, retrofit_photo;
    private RelativeLayout relNotifi;
    private Bundle b;
    private boolean result;
    private Toolbar toolbar;
    private Preferences preferences;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_status_contract);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        preferences = new Preferences(mContext);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.srtHistory_order);
            toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        }

        relNotifi = (RelativeLayout) findViewById(R.id.relNotifi);
        lvPhoto = (RecyclerView) findViewById(R.id.lvPhoto);
        editNote = (EditText) findViewById(R.id.editNote);
        tvStatus = (TextView) findViewById(R.id.tvStatus);
        tvName = (TextView) findViewById(R.id.tvName);
        tvAddress = (TextView) findViewById(R.id.tvAddress);
        imSelect_upload_photo = (ImageView) findViewById(R.id.imSelect_upload_photo);
        imSelect_upload_photo.setOnClickListener(this);
        // imBack.setOnClickListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL
                , false);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        lvPhoto.setHasFixedSize(true);
        lvPhoto.setLayoutManager(layoutManager);

        b = getIntent().getExtras();
        tvName.setText(b.getString("client_name"));
        tvAddress.setText(b.getString("address"));
        editNote.setText(b.getString("note"));
        tvStatus.setText(b.getString("status"));
        retrofit = func_Connect();
        retrofit_photo = func_Upload_photo();
        getLoad_photo();
        //  funcPopupWindow_upload_photo();
    }

    //load list photo
    public void getLoad_photo() {
        adapter_Photo_history_order adapter = new adapter_Photo_history_order(this, lvImage, this);
        lvPhoto.setAdapter(adapter);
    }
    //sự kiện click
    @Override
    public void onClick(View view) {
        if (view == imSelect_upload_photo) {
            CropImage.activity(null).setGuidelines(CropImage_View.Guidelines.ON)
                    .start(Edit_status_contract_activity.this);
        }

        if (view == imBack) {
            finish();

        }
    }

    public Retrofit func_Connect() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Url.URL_exchange)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

    public Retrofit func_Upload_photo() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Url.URL_utils)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

    //edit status contract
    public void funcUpdateStatus_contract() {
        ServiceAPI history_orders = retrofit.create(ServiceAPI.class);
        Status resquet_client = new Status();
        resquet_client.setValue(editNote.getText().toString());

        Call<Result_status_contract> call = history_orders.getStatus_contract(b.getInt("object_id"),  preferences.getStringValue(Constants.TOKEN, ""), b.getInt("client_id"), b.getInt("StatusId"), preferences.getIntValue(Constants.USER_ID, 0), preferences.getIntValue(Constants.PARTNER_ID, 0), resquet_client);
        call.enqueue(new Callback<Result_status_contract>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(Call<Result_status_contract> call, Response<Result_status_contract> response) {
                if (response.body().getStatus().getId() > 0) {
                    if (lvImage.size() > 0) {
                    boolean b = funcUpload_photo(response.body().getStatus().getId(), lvImage.size() - 1);
                    if (b == false) {
                        funcNotification(2);
                        relNotifi.setVisibility(View.INVISIBLE);
                    } else {
                        funcNotification(1);
                        relNotifi.setVisibility(View.INVISIBLE);
                    }
                }else {
                        funcNotification(2);
                        relNotifi.setVisibility(View.INVISIBLE);
                    }

                } else {
                    funcNotification(1);
                    relNotifi.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<Result_status_contract> call, Throwable t) {
                funcNotification(1);
                relNotifi.setVisibility(View.INVISIBLE);
            }
        });
    }

    //upload photo đệ quy
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public boolean funcUpload_photo(final int id, final int i) {
        if (lvImage.get(i).getUrl().contains("http")) {
            if (i == 0)
                result = false;
            else funcUpload_photo(id, i - 1);
        } else {
            ServiceAPI history_orders = retrofit_photo.create(ServiceAPI.class);
            File file = FileUtils.getFile(this, lvImage.get(i).getFilePart());
                if(file != null) {
                    RequestBody requestFile =
                            RequestBody.create(
                                    MediaType.parse(getContentResolver().getType(lvImage.get(i).getFilePart())),
                                    file
                            );
                    // create RequestBody instance from file
                    MultipartBody.Part filePart = MultipartBody.Part.createFormData("photo", file.getName(), requestFile);
                    Call<Result_upload_photo> call = history_orders.getUpload_photo("avarta", id, preferences.getStringValue(Constants.TOKEN, ""),preferences.getIntValue(Constants.USER_ID, 0), "i",  preferences.getIntValue(Constants.PARTNER_ID, 0), "od", filePart);
                    call.enqueue(new Callback<Result_upload_photo>() {
                        @Override
                        public void onResponse(Call<Result_upload_photo> call, Response<Result_upload_photo> response) {
                            if (i == 0)
                                result = false;
                            else funcUpload_photo(id, i - 1);
                        }

                        @Override
                        public void onFailure(Call<Result_upload_photo> call, Throwable t) {
                            result = true;
                        }
                    });
                }
                else result = false;

        }

        return result;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // handle result of CropImageActivity
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                String a;
//                ((ImageView) findViewById(R.id.quick_start_cropped_image)).setImageURI(result.getUri());
                if(result.getUri().toString().contains("file")){
                    a = result.getUri().toString().replace("file://","");
                    selectedImage =getImageContentUri(this,new File(Uri.parse(a).toString()));
                    Log.d("uri",getImageContentUri(this,new File(Uri.parse(a).toString()))+"");
                }
                Photo photo = new Photo();
                photo.setCode("");
                photo.setHeight(0);
                photo.setWidth(0);
                photo.setObjectId(0);
                photo.setOrderNo(0);
                photo.setPhotoId(0);
                photo.setType(0);
                photo.setName("");
                photo.setUrl("");
                photo.setFilePart(selectedImage);
                lvImage.add(photo);
                getLoad_photo();

                Log.d("result.getUri",result.getUri().toString());
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(this, "Lỗi file: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        }
    }



    public void funcNotification(int im) {

        if(im == 1) {
            new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText(getResources().getString(R.string.srtNotifi))
                    .setContentText(getResources().getString(R.string.srtNotifi_false))
                    .setConfirmText("Đồng ý")
                    .show();
        }
        else {
            new SweetAlertDialog(this,SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText(getResources().getString(R.string.srtNotifi))
                    .setContentText(getResources().getString(R.string.srtNotifi_done))
                    .setConfirmText("Đồng ý")
                    .show();
        }
    }
    //xóa photo chưa được gửi lên.
    @Override
    public void Delete_photo_off(final int pos) {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_delete_photo);
        Button btDelete = (Button) dialog.findViewById(R.id.btDelete);
        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lvImage.remove(pos);
                getLoad_photo();
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    //xóa photo đã oline.
    @Override
    public void Delete_photo_onl(final int pos) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_delete_photo);
        Button btDelete = (Button) dialog.findViewById(R.id.btDelete);
        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Photo photo = lvImage.get(pos);
                ServiceAPI history_orders = retrofit_photo.create(ServiceAPI.class);
                Call<Result_upload_photo> call = history_orders.getDelete_photo(photo.getName(), "avarta", photo.getPhotoId(), preferences.getStringValue(Constants.TOKEN, ""), preferences.getIntValue(Constants.USER_ID, 0), "a", preferences.getIntValue(Constants.PARTNER_ID, 0), "od", photo.getObjectId());
                call.enqueue(new Callback<Result_upload_photo>() {
                    public void onResponse(Call<Result_upload_photo> call, Response<Result_upload_photo> response) {
                        if (response.body().getHasErrors() == false) {
                            lvImage.remove(pos);
                            getLoad_photo();
                            dialog.dismiss();
                            Toast.makeText(Edit_status_contract_activity.this, R.string.srtDone, Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(Edit_status_contract_activity.this, R.string.srtFalse, Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onFailure(Call<Result_upload_photo> call, Throwable t) {
                        Log.d("Update_staus2222", call.toString());
                    }
                });

            }
        });
        dialog.show();

    }
    public static Uri getImageContentUri(Context context, File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[] { MediaStore.Images.Media._ID },
                MediaStore.Images.Media.DATA + "=? ",
                new String[] { filePath }, null);
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
            cursor.close();
            return Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return context.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_edit_history_order, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.actionDone) {
            relNotifi.setVisibility(View.VISIBLE);
            funcUpdateStatus_contract();
        }
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
