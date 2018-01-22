package hackaton.bayern.vor5prung;

import android.app.Dialog;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

import hackaton.bayern.vor5prung.ui.camera.GraphicOverlay;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int RC_BARCODE_CAPTURE = 9001;
    private static final String TAG = "BarcodeMain";
    EditText editTextUser;
    EditText editTextPass;
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.read_barcode).setOnClickListener(this);

        editTextUser = (EditText) findViewById(R.id.edittext_username);
        editTextPass = (EditText) findViewById(R.id.edittext_password);
        loginButton = (Button) findViewById(R.id.button_login);
        loginButton.setOnClickListener(this);

    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.read_barcode) {
            // launch barcode activity.
            Intent intent = new Intent(this, BarcodeCaptureActivity.class);
            intent.putExtra(BarcodeCaptureActivity.AutoFocus, true);
            intent.putExtra(BarcodeCaptureActivity.UseFlash, false);

            startActivityForResult(intent, RC_BARCODE_CAPTURE);
        }
        else if(v.getId() == R.id.button_login){
            Intent intent = new Intent(this, AudiDriver.class);
            intent.putExtra("user", editTextUser.getText().toString());
            startActivity(intent);
        }

    }

    /**
     * Called when an activity you launched exits, giving you the requestCode
     * you started it with, the resultCode it returned, and any additional
     * data from it.  The <var>resultCode</var> will be
     * {@link #RESULT_CANCELED} if the activity explicitly returned that,
     * didn't return any result, or crashed during its operation.
     * <p/>
     * <p>You will receive this call immediately before onResume() when your
     * activity is re-starting.
     * <p/>
     *
     * @param requestCode The integer request code originally supplied to
     *                    startActivityForResult(), allowing you to identify who this
     *                    result came from.
     * @param resultCode  The integer result code returned by the child activity
     *                    through its setResult().
     * @param data        An Intent, which can return result data to the caller
     *                    (various data can be attached to Intent "extras").
     * @see #startActivityForResult
     * @see #createPendingResult
     * @see #setResult(int)
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_BARCODE_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);

                    Log.d(TAG, "Barcode read: " + barcode.displayValue);

                    final Dialog dialog = new Dialog(this);
                    dialog.setContentView(R.layout.dialog_register_fan);


                    dialog.show();
                } else {

                    Log.d(TAG, "No barcode captured, intent data is null");
                }
            } else {
                //statusMessage.setText(String.format(getString(R.string.barcode_error),
                //        CommonStatusCodes.getStatusCodeString(resultCode)));
                Log.d(TAG, "Error reading the barcode");
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public static class BarcodeGraphic extends GraphicOverlay.Graphic {
        private int mId;

        private static final int COLOR_CHOICES[] = {
                Color.BLUE
        };

        private static int mCurrentColorIndex = 0;

        private Paint mRectPaint;
        private Paint mTextPaint;
        private volatile Barcode mBarcode;

        BarcodeGraphic(GraphicOverlay overlay) {
            super(overlay);

            mCurrentColorIndex = (mCurrentColorIndex + 1) % COLOR_CHOICES.length;
            final int selectedColor = COLOR_CHOICES[mCurrentColorIndex];

            mRectPaint = new Paint();
            mRectPaint.setColor(selectedColor);
            mRectPaint.setStyle(Paint.Style.STROKE);
            mRectPaint.setStrokeWidth(4.0f);

            mTextPaint = new Paint();
            mTextPaint.setColor(selectedColor);
            mTextPaint.setTextSize(36.0f);
        }

        public int getId() {
            return mId;
        }

        public void setId(int id) {
            this.mId = id;
        }

        public Barcode getBarcode() {
            return mBarcode;
        }

        /**
         * Updates the barcode instance from the detection of the most recent frame.  Invalidates the
         * relevant portions of the overlay to trigger a redraw.
         */
        void updateItem(Barcode barcode) {
            mBarcode = barcode;
            postInvalidate();
        }

        /**
         * Draws the barcode annotations for position, size, and raw value on the supplied canvas.
         */
        @Override
        public void draw(Canvas canvas) {
            Barcode barcode = mBarcode;
            if (barcode == null) {
                return;
            }

            // Draws the bounding box around the barcode.
            RectF rect = new RectF(barcode.getBoundingBox());
            rect.left = translateX(rect.left);
            rect.top = translateY(rect.top);
            rect.right = translateX(rect.right);
            rect.bottom = translateY(rect.bottom);
            canvas.drawRect(rect, mRectPaint);

            // Draws a label at the bottom of the barcode indicate the barcode value that was detected.
            canvas.drawText(barcode.rawValue, rect.left, rect.bottom, mTextPaint);
        }
    }
}
