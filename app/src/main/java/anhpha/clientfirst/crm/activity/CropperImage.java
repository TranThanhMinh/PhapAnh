package anhpha.clientfirst.crm.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import anhpha.clientfirst.crm.cropper.CropImageView;
import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.utils.LogUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Random;

public class CropperImage extends BaseAppCompatActivity {

    public static final String TAG = CropperImage.class.getSimpleName();
    public static final int REQUEST_CROP = 6709;
    public static final int REQUEST_PICK = 9162;
    public static final int RESULT_ERROR = 404;
    // Static final constants
    private static final int DEFAULT_ASPECT_RATIO_VALUES = 10;
    private static final int ROTATE_NINETY_DEGREES = -90;
    private static final String ASPECT_RATIO_X = "ASPECT_RATIO_X";
    private static final String ASPECT_RATIO_Y = "ASPECT_RATIO_Y";
    private static final int ON_TOUCH = 1;
    Bitmap croppedImage;

    // Instance variables
    private int mAspectRatioX = DEFAULT_ASPECT_RATIO_VALUES;
    private int mAspectRatioY = DEFAULT_ASPECT_RATIO_VALUES;

    // Saves the state upon rotating the screen/restarting the activity
    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt(ASPECT_RATIO_X, mAspectRatioX);
        bundle.putInt(ASPECT_RATIO_Y, mAspectRatioY);
    }

    // Restores the state upon rotating the screen/restarting the activity
    @Override
    public void onRestoreInstanceState(Bundle bundle) {
        super.onRestoreInstanceState(bundle);
        mAspectRatioX = bundle.getInt(ASPECT_RATIO_X);
        mAspectRatioY = bundle.getInt(ASPECT_RATIO_Y);
    }

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cropper_image);

        // Sets fonts for all
        ViewGroup root = (ViewGroup) findViewById(R.id.mylayout);
        Intent intent = getIntent();
        Uri uri = Uri.parse(intent.getStringExtra("source"));
        // Initialize components of the app
        final CropImageView cropImageView = (CropImageView) findViewById(R.id.CropImageView);

        cropImageView.setImageUri(uri);

        //Sets the rotate button
        final Button rotateButton = (Button) findViewById(R.id.Button_rotate);
        rotateButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                cropImageView.rotateImage(ROTATE_NINETY_DEGREES);
            }
        });


        // Sets initial aspect ratio to 10/10, for demonstration purposes
        cropImageView.setAspectRatio(DEFAULT_ASPECT_RATIO_VALUES, DEFAULT_ASPECT_RATIO_VALUES);

        cropImageView.setFixedAspectRatio(false);
        final Button cropButton = (Button) findViewById(R.id.Button_crop);
        cropButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                croppedImage = cropImageView.getCroppedImage();

                Intent resultIntent = new Intent();
                resultIntent.putExtra("file", saveBitmap("image" + new Random().nextInt(255), croppedImage).getPath());
                setResult(REQUEST_CROP, resultIntent);
                finish();

                //ImageView croppedImageView = (ImageView) findViewById(R.id.croppedImageView);
                //croppedImageView.setImageBitmap(croppedImage);
            }
        });

    }

    private File saveBitmap(String filename, Bitmap bitmap) {

        OutputStream outStream = null;

        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), filename + ".jpg");
        if (file.exists()) {
            file.delete();
            file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), filename + ".jpg");
            LogUtils.d(TAG, "file exist", "" + file + ",Bitmap= " + filename);
        }
        try {
            LogUtils.d(TAG, "file", "" + Environment.getExternalStorageDirectory());
            // make a new bitmap from your file
            outStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, outStream);
            outStream.flush();
            outStream.close();

        } catch (Exception ex) {
            LogUtils.e(TAG, "", ex);
        }
        LogUtils.d(TAG, "Done", "" + file.getPath());
        return file;

    }


    /*
     * Sets the font on all TextViews in the ViewGroup. Searches recursively for
     * all inner ViewGroups as well. Just add a check for any other views you
     * want to set as well (EditText, etc.)
     */
    public void setFont(ViewGroup group, Typeface font) {
        int count = group.getChildCount();
        View v;
        for (int i = 0; i < count; i++) {
            v = group.getChildAt(i);
            if (v instanceof TextView || v instanceof EditText || v instanceof Button) {
                ((TextView) v).setTypeface(font);
            } else if (v instanceof ViewGroup)
                setFont((ViewGroup) v, font);
        }
    }
}
