package com.quadcopter.ui;

import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.Size;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class CameraPreviewSurface extends SurfaceView implements SurfaceHolder.Callback {
    SurfaceHolder mHolder;
    Camera mCamera;
    PreviewCallback mPreviewCallback;

    public CameraPreviewSurface(Context context, AttributeSet attrs)
    {
    	super(context, attrs);
    	
    	// Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.
    	mHolder = getHolder();
        mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }
    
    public CameraPreviewSurface(Context context, PreviewCallback previewCallback) 
    {
        this(context);
        mPreviewCallback = previewCallback;
    }
    
    public CameraPreviewSurface(Context context) {
        super(context);

        // Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.
        mHolder = getHolder();
        mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    public void surfaceCreated(SurfaceHolder holder) {
        // The Surface has been created, acquire the camera and tell it where
        // to draw.
        mCamera = Camera.open();
        try {
            mCamera.setDisplayOrientation(90);
        	mCamera.setPreviewDisplay(holder);
        } catch (IOException exception) {
            mCamera.release();
            mCamera = null;
            // TODO: add more exception handling logic here
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        // Surface will be destroyed when we return, so stop the preview.
        // Because the CameraDevice object is not a shared resource, it's very
        // important to release it when the activity is paused.
        mCamera.stopPreview();
        mCamera.setPreviewCallback(null);
        mCamera.release();
        mCamera = null;
    }


    private Size getOptimalPreviewSize(List<Size> sizes, int w, int h) {
        final double ASPECT_TOLERANCE = 0.05;
        double targetRatio = (double) w / h;
        if (sizes == null) return null;

        Size optimalSize = null;
        double minDiff = Double.MAX_VALUE;

        int targetHeight = h;

        // Try to find an size match aspect ratio and size
        for (Size size : sizes) {
            double ratio = (double) size.width / size.height;
            if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE) continue;
            if (Math.abs(size.height - targetHeight) < minDiff) {
                optimalSize = size;
                minDiff = Math.abs(size.height - targetHeight);
            }
        }

        // Cannot find the one match the aspect ratio, ignore the requirement
        if (optimalSize == null) {
            minDiff = Double.MAX_VALUE;
            for (Size size : sizes) {
                if (Math.abs(size.height - targetHeight) < minDiff) {
                    optimalSize = size;
                    minDiff = Math.abs(size.height - targetHeight);
                }
            }
        }
        return optimalSize;
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
    	if (mCamera!=null)
    	{
		    // Now that the size is known, set up the camera parameters and begin
		    // the preview.
		    Camera.Parameters parameters = mCamera.getParameters();
		
		    List<Size> sizes = parameters.getSupportedPreviewSizes();
		    Size optimalSize = getOptimalPreviewSize(sizes, w, h);
//        parameters.setPreviewSize(sizes.get(sizes.size()-1).width
//        		,sizes.get(sizes.size()-1).height);
		    parameters.setPreviewSize(optimalSize.width
		    		,optimalSize.height);
		    parameters.setPictureFormat(ImageFormat.NV21); 
		    parameters.setPictureSize(240, 480);
		    parameters.setPreviewFrameRate(1);
		    parameters.set("orientation", "landscape");
		    parameters.set("rotation", 90);
		    
		    mCamera.setParameters(parameters);
		    mCamera.startPreview();
		    
		    mCamera.setPreviewCallback(mPreviewCallback);
    	}
    }
    
    public void setPreviewCallback(PreviewCallback callback)
    {
    	
    	mPreviewCallback = callback;
    	if (mCamera!=null)
    		mCamera.setPreviewCallback(callback);
    }

}
