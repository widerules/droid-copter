package com.quadcopter.ui;

import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.Size;
import android.view.Surface;
import android.view.SurfaceHolder;

public class CameraPreview {
    SurfaceHolder mHolder;
    Camera mCamera;
    PreviewCallback mPreviewCallback;

    public CameraPreview(Context context, PreviewCallback previewCallback) 
    {
        this(context);
        mPreviewCallback = previewCallback;
    }
    
    public CameraPreview(Context context) {

        // Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.
        mHolder = previewSurfaceHolder;
        //mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        
    }
    
    public void startPreview()
    {
        // The Surface has been created, acquire the camera and tell it where
        // to draw.
        mCamera = Camera.open();
        try {
           mCamera.setPreviewDisplay(mHolder);
        } catch (IOException exception) {
            mCamera.release();
            mCamera = null;
            // TODO: add more exception handling logic here
        }
        
        
        // Now that the size is known, set up the camera parameters and begin
        // the preview.
        Camera.Parameters parameters = mCamera.getParameters();

        List<Size> sizes = parameters.getSupportedPreviewSizes();
        
        parameters.setPreviewSize(sizes.get(sizes.size()-1).width
        		,sizes.get(sizes.size()-1).height);
        
        parameters.setPictureFormat(ImageFormat.NV21); 
        parameters.setPictureSize(240, 480);
        parameters.setPreviewFrameRate(1);
        parameters.set("orientation", "landscape");
        parameters.set("rotation", 90);
        
        mCamera.setParameters(parameters);
        mCamera.startPreview();
        
        mCamera.setPreviewCallback(mPreviewCallback);
    }

    public void stopPrievew() {
        // Surface will be destroyed when we return, so stop the preview.
        // Because the CameraDevice object is not a shared resource, it's very
        // important to release it when the activity is paused.
        mCamera.stopPreview();
        mCamera.release();
        mCamera = null;
    }
    
    private SurfaceHolder previewSurfaceHolder = new SurfaceHolder() {
//   	 private static final String LOG_TAG = "SurfaceHolder";
//        private int mSaveCount;
//        final ReentrantLock mSurfaceLock = new ReentrantLock();
//        final Surface mSurface = new Surface();
        
        public boolean isCreating() {
            return false;//return mIsCreating;
        }

        public void addCallback(Callback callback) {
            //
        }

        public void removeCallback(Callback callback) {
            //
        }
        
        public void setFixedSize(int width, int height) {
            //
        }

        public void setSizeFromLayout() {
            //
        }

        public void setFormat(int format) {
            //
        }

        public void setType(int type) {
            //
        }

        public void setKeepScreenOn(boolean screenOn) {
            //
        }
        
        public Canvas lockCanvas() {
            return internalLockCanvas(null);
        }

        public Canvas lockCanvas(Rect dirty) {
            return internalLockCanvas(dirty);
        }

        private final Canvas internalLockCanvas(Rect dirty) {
                    
            return null;
        }

        public void unlockCanvasAndPost(Canvas canvas) {
           //
        }

        public Surface getSurface() {
            return null;
        }

        public Rect getSurfaceFrame() {
            return null;
        }
   };
}
