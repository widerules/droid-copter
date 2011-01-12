package com.quadcopter.ui;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.quadcopter.R;
import com.quadcopter.webserver.IWebServerService;

public class QuadCopterActivity extends Activity implements OnClickListener, PreviewCallback{
	public static String TAG = "QuadCopterActivity";
	
	private IWebServerService mWebService;
	
//	CameraPreview mCamPreview;
	
	boolean startServerOnBind = false;
	
    private ServiceConnection mWebServiceConnection = new ServiceConnection() {
		
		@Override
		public void onServiceDisconnected(ComponentName name) {
			// This is called when the connection with the service has been
            // unexpectedly disconnected -- that is, its process crashed.
			mWebService = null;
			//mCamPreview.mWebService = null;
		}
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			mWebService = IWebServerService.Stub.asInterface(service);
			try {
				//if the web server needs to be started then start it
				if (mWebService.isRunning())
				{
					((Button)findViewById(R.id.start_server)).setText("Stop");
				}else if (startServerOnBind)
				{
					mWebService.start();
			        //show ip address - works for LAN
			        TextView lblIpAddress = (TextView)findViewById(R.id.ip_address);
			        Context c = QuadCopterActivity.this.getApplicationContext(); 
			        String ip = getIPAddress(c);
			        
			        lblIpAddress.setText(ip+":"+mWebService.getPort());
				}
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
//                WindowManager.LayoutParams.FLAG_FULLSCREEN );
        
        setContentView(R.layout.main);
        
        ((CameraPreviewSurface)findViewById(R.id.camera_preview_surface)).setPreviewCallback(this);
        
        Button startServer = ((Button)findViewById(R.id.start_server));
        startServer.setOnClickListener(this);
        startServer.setClickable(true);
        
		//Startup the WebServerService 
        Intent serviceIntent = new Intent();
        serviceIntent.setAction(IWebServerService.class.getName());
        bindService(serviceIntent, mWebServiceConnection, Context.BIND_AUTO_CREATE);
        
        TextView lblIpAddress = (TextView)findViewById(R.id.ip_address);
        lblIpAddress.setText("unavaliable");
    }
	
    public void onClick(View v) {
		Button btn = (Button) v;
		if (btn.getText().equals("Start"))
		{
			if (mWebService==null)
			{
				//Startup the WebServerService 
				startServerOnBind = true;
		        Intent serviceIntent = new Intent();
		        serviceIntent.setAction(IWebServerService.class.getName());
		        bindService(serviceIntent, mWebServiceConnection, Context.BIND_AUTO_CREATE);
			} else
			{
				try {
					mWebService.start();
					
			        //show ip address - works for LAN
			        TextView lblIpAddress = (TextView)findViewById(R.id.ip_address);
			        Context c = QuadCopterActivity.this.getApplicationContext(); 
			        String ip = getIPAddress(c);
			        
			        lblIpAddress.setText(ip+":"+mWebService.getPort());
			        
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			btn.setText("Stop");
		} else
		{
			if (mWebService!=null)
			{
				try {
					mWebService.stop();
					
			        TextView lblIpAddress = (TextView)findViewById(R.id.ip_address);
			        lblIpAddress.setText("unavaliable");
			        
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			btn.setText("Start");
		}
	}

	@Override
	public void onPreviewFrame(byte[] data, Camera camera) {
		if (mWebService!=null)
		{
			try {
				Camera.Parameters params = camera.getParameters();
				
				//The image is in the NV21 image format and
				//must be converted to JPEG.
				YuvImage converter = new YuvImage(data, 
						ImageFormat.NV21,
						params.getPreviewSize().width,
						params.getPreviewSize().height,null);
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				converter.compressToJpeg(new Rect(0, 0, converter.getWidth(),
						converter.getHeight()), 
						80, baos);
				
				//Pass the JPEG image to the WebServer
				mWebService.setWebCamByteArray(baos.toByteArray());
				
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static String getIPAddress(Context context) 
	{
		WifiManager wifiManager = (WifiManager) context
		.getSystemService(Context.WIFI_SERVICE);
		android.net.wifi.WifiInfo info = wifiManager.getConnectionInfo();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);

		byte[] intByte;
		try 
		{
			dos.writeInt(info.getIpAddress());
			dos.flush();
			intByte = bos.toByteArray();
		} catch (IOException e) {
			Log.e(TAG, "Problem converting IP address");
			return "unknown";
		}

		// Reverse int bytes...
		byte[] addressBytes = new byte[intByte.length];
		for (int i = 0; i < intByte.length; i++) 
		{
			addressBytes[i] = intByte[(intByte.length - 1) - i];
		}

		InetAddress address = null;
		try 
		{
			address = InetAddress.getByAddress(addressBytes);
		} catch (UnknownHostException e) 
		{
			Log.e(TAG, "Problem determing IP address");
			return "unknown";
		}
		return address.getHostAddress();
	}
}