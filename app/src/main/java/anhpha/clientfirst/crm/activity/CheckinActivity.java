package anhpha.clientfirst.crm.activity;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.adapter.PhotosAdapter;
import anhpha.clientfirst.crm.configs.Constants;
import anhpha.clientfirst.crm.configs.Preferences;
import anhpha.clientfirst.crm.cropper.Crop;
import anhpha.clientfirst.crm.model.MAPIResponse;
import anhpha.clientfirst.crm.model.MCheckin;
import anhpha.clientfirst.crm.model.MClient;
import anhpha.clientfirst.crm.model.MPhoto;
import anhpha.clientfirst.crm.service_api.ServiceAPI;
import anhpha.clientfirst.crm.utils.DynamicBox;
import anhpha.clientfirst.crm.utils.LogUtils;
import anhpha.clientfirst.crm.utils.TokenUtils;
import anhpha.clientfirst.crm.utils.Utils;
import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckinActivity extends BaseAppCompatActivity implements Callback<MAPIResponse<MCheckin>>, View.OnClickListener  {
    @Bind(R.id.rvActivities)
    RecyclerView rvActivities;
    @Bind(R.id.etContent)
    EditText etContent;
    @Bind(R.id.tvClientName)
    TextView tvClientName;
    @Bind(R.id.tvAddress)
    TextView tvAddress;
    @Bind(R.id.include)
    Toolbar toolbar;
    @Bind(R.id.ivCamera)
    ImageView ivCamera;

    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    MCheckin mCheckin = new MCheckin();
    MClient mClient = new MClient();

    Preferences preferences;
    protected List<MPhoto> photos;
    protected PhotosAdapter photosAdapter;
    private String filePath = null;
    private Uri mImageCaptureUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        box = new DynamicBox(this, R.layout.activity_checkin);
        ButterKnife.bind(this);
        preferences = new Preferences(mContext);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.meet);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        Intent intent = getIntent();
        mCheckin = (MCheckin) intent.getSerializableExtra("mCheckin");
        mClient = (MClient) intent.getSerializableExtra("mClient");
        checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
        rvActivities.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        llm.setSmoothScrollbarEnabled(true);
        rvActivities.setLayoutManager(llm);
        rvActivities.setAdapter(photosAdapter);

        tvClientName.setText(mClient.getClient_name());
        tvAddress.setText(mClient.getAddress());
        if(mClient.getAddress()!=null && !mClient.getAddress().isEmpty()){
            tvAddress.setVisibility(View.VISIBLE);
        }
        if(mCheckin == null)
            mCheckin = new MCheckin();
            ivCamera.setOnClickListener(this);
        if(mCheckin.getUser_checkin_id() > 0){
            etContent.setText(mCheckin.getContent_checkin());
            etContent.setFocusable(false);
            ivCamera.setVisibility(View.GONE);
            GetRetrofit().create(ServiceAPI.class)
                    .getUserCheckin(preferences.getStringValue(Constants.TOKEN, "")
                            , preferences.getIntValue(Constants.USER_ID, 0)
                            , preferences.getIntValue(Constants.PARTNER_ID, 0)
                            , mCheckin.getUser_checkin_id())
                    .enqueue(new Callback<MAPIResponse<MCheckin>>() {
                        @Override
                        public void onResponse(Call<MAPIResponse<MCheckin>> call, Response<MAPIResponse<MCheckin>> response) {
                            TokenUtils.checkToken(mContext,response.body().getErrors());
                            mCheckin= response.body().getResult();
                            photos = mCheckin.getPhotos();
                            photosAdapter.setPhotoList(photos);
                            photosAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onFailure(Call<MAPIResponse<MCheckin>> call, Throwable t) {

                        }
                    });
        }

        photos = new ArrayList<>();

        photosAdapter = new PhotosAdapter(this, photos, new PhotosAdapter.IPhotoCallback() {
            @Override
            public void select(int position) {
                String photo= photos.get(position).url + photos.get(position).name;
                if(photos.get(position).local != null) {
                    photo = photos.get(position).local ;
                    LogUtils.d(photo,"local","photo");
                }
                startActivity(new Intent(mContext, ViewImageActivity.class).putExtra("url",photo));

            }
        });
        rvActivities.setAdapter(photosAdapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_done, menu);
        if(mCheckin.getUser_checkin_id()>0){
            for (int i = 0; i < menu.size(); i++) {
                if(menu.getItem(i).getItemId() == R.id.done)
                    menu.getItem(i).setVisible(false);
            }
        }
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.done:
                mCheckin.setClient_id(mClient.getClient_id());
                mCheckin.setUser_id(preferences.getIntValue(Constants.USER_ID, 0));
                mCheckin.setContent_checkin(etContent.getText().toString());
                mCheckin.setLatitude(mLastLocation.getLatitude());
                mCheckin.setLongitude(mLastLocation.getLongitude());
                GetRetrofit().create(ServiceAPI.class)
                        .setUserCheckin(preferences.getStringValue(Constants.TOKEN, "")
                                , preferences.getIntValue(Constants.USER_ID, 0)
                                , preferences.getIntValue(Constants.PARTNER_ID, 0)
                                , mClient.getClient_id()
                                , mCheckin)
                        .enqueue(this);

                return true;

            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void uploadImage(final List<MPhoto> photos ){
        if(photos.size() == 0){
            Utils.showDialogSuccess(mContext, R.string.checkin_client_done);
        }

        for (final MPhoto p : photos){
            if(p.local != null) {
            File file = new File(p.local);
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part imagenPerfil = null;
            imagenPerfil = MultipartBody.Part.createFormData("image", file.getName(), requestBody);

            GetRetrofit().create(ServiceAPI.class)
                    .upLoadPhoto(preferences.getStringValue(Constants.TOKEN, "")
                            , preferences.getIntValue(Constants.USER_ID, 0)
                            , preferences.getIntValue(Constants.PARTNER_ID, 0)
                            , "photo"
                            , "a"
                            , "ci"
                            , mCheckin.getUser_checkin_id()
                            , imagenPerfil)
                    .enqueue(new Callback<MAPIResponse<MPhoto>>() {
                        @Override
                        public void onResponse(Call<MAPIResponse<MPhoto>> call, Response<MAPIResponse<MPhoto>> response) {
                            LogUtils.api(TAG, call, (response.body()));
                            TokenUtils.checkToken(mContext,response.body().getErrors());
                            photos.remove(p);
                            uploadImage(photos);
                        }

                        @Override
                        public void onFailure(Call<MAPIResponse<MPhoto>> call, Throwable t) {
                            LogUtils.d(TAG, "getUserActivities ", t.toString());
                            photos.remove(p);
                            uploadImage(photos);
                        }
                    });
            box.showLoadingLayout();
            LogUtils.d(TAG, "getUserActivities ", "start");
            break;
            }else {
                photos.remove(p);
                uploadImage(photos);
                break;
            }
        }
    }
    @Override
    public void onResponse(Call<MAPIResponse<MCheckin>> call, Response<MAPIResponse<MCheckin>> response) {
        LogUtils.api(TAG, call, (response.body()));
        box.hideAll();
        TokenUtils.checkToken(mContext,response.body().getErrors());
        mCheckin = response.body().getResult();
        if(!response.body().isHasErrors()){
            uploadImage(photos);
        }

    }

    @Override
    public void onFailure(Call<MAPIResponse<MCheckin>> call, Throwable t) {
        LogUtils.d(TAG, "getUserActivities ", t.toString());
        box.hideAll();
        Utils.showError(coordinatorLayout, R.string.checkin_client_fail);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.ivCamera:
                CharSequence[] charSequences = new CharSequence[2];
                charSequences[0] = getString(R.string.camera);
                charSequences[1] = getString(R.string.library);
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle(getResources().getString(R.string.choose_image));
                builder.setCancelable(true);
                builder.setItems(charSequences, new  DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case 0:
                                openCamera();
                                break;
                            case 1:
                                openPictures();
                                break;
                            default:
                                break;
                        }
                    }
                });
                builder.show();
                break;
            default:
                break;
        }
    }

    private void openPictures() {
        Crop.pickImage(this);
    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
                "tmp_avatar_" + String.valueOf(System.currentTimeMillis()) + ".jpg"));

        intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);

        try {
            intent.putExtra("return-data", true);

            startActivityForResult(intent, Constants.PICK_FROM_CAMERA);
        } catch (ActivityNotFoundException e) {
            LogUtils.e(TAG, "openCamera", e);
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Crop.REQUEST_PICK && resultCode == RESULT_OK) {
            if (data != null) {
                beginCrop(data.getData());
            }
        } else if (requestCode == Crop.REQUEST_CROP) {
        if (data != null) {

            filePath = data.getStringExtra("file").toString();
            MPhoto p = new MPhoto();
            p.local = filePath;
            photos.add(p);
            photosAdapter.setPhotoList(photos);
            photosAdapter.notifyDataSetChanged();
        }
    } else if (requestCode == Constants.PICK_FROM_CAMERA) {
            if (mImageCaptureUri != null) {
                beginCrop(mImageCaptureUri);
            }
    }
    }

    private void beginCrop(Uri source) {
        Intent intent = new Intent(getApplication(), CropperImage.class);
        intent.putExtra("source", source.toString());
        startActivityForResult(intent, Crop.REQUEST_CROP);
    }
}
