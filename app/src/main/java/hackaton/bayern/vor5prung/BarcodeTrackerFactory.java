package hackaton.bayern.vor5prung;

import android.content.Context;

import com.google.android.gms.vision.MultiProcessor;
import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.barcode.Barcode;

import hackaton.bayern.vor5prung.ui.camera.GraphicOverlay;

/**
 * Created by edhuar on 19.01.18.
 */

public class BarcodeTrackerFactory implements MultiProcessor.Factory<Barcode> {
    private GraphicOverlay<MainActivity.BarcodeGraphic> mGraphicOverlay;
    private Context mContext;

    public BarcodeTrackerFactory(GraphicOverlay<MainActivity.BarcodeGraphic> mGraphicOverlay,
                                 Context mContext) {
        this.mGraphicOverlay = mGraphicOverlay;
        this.mContext = mContext;
    }

    @Override
    public Tracker<Barcode> create(Barcode barcode) {
        MainActivity.BarcodeGraphic graphic = new MainActivity.BarcodeGraphic(mGraphicOverlay);
        return new BarcodeGraphicTracker(mGraphicOverlay, graphic, mContext);
    }
}
