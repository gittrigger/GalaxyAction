package com.havenskys.galaxy;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.URI;
import java.net.UnknownHostException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ProtocolException;
import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.RedirectHandler;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.LangUtils;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.PreviewCallback;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.provider.MediaStore.MediaColumns;
import android.provider.MediaStore.Images.ImageColumns;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView.OnEditorActionListener;

public class Art extends Activity implements OnFocusChangeListener, OnClickListener, OnTouchListener, SurfaceHolder.Callback {
	private String G = "Galaxy";
	private LinearLayout[] mArt;
	private ScrollView mOll;
	//private Context mContext;
	private SharedPreferences mReg;
	private Editor mRegedit;
	private int[] mOnList;
	private int mOnCount = 0;
	private LinearLayout mLumn;private ImageView mEview;
	
	public void onCreate(Bundle indata){
		super.onCreate(indata);
		getWindow().setFormat(PixelFormat.TRANSLUCENT);
		setContentView(R.layout.page);
		
		mReg = getSharedPreferences("Preferences", MODE_WORLD_WRITEABLE);
		mRegedit = mReg.edit();
		if( !mReg.contains("webaddress_verified") ){
			mRegedit.putString("webaddress", "");
			mRegedit.putBoolean("webaddress_verified", false);
			mRegedit.putString("weblogin", "");
			mRegedit.putBoolean("weblogin_verified", false);
			mRegedit.putString("webpassword", "");
			mRegedit.putBoolean("webpassword_verified", false);
			mRegedit.commit();
		}
		//mNe = (LinearLayout) findViewById(R.id.ne);
		//mNe.setVisibility(View.GONE);
		//mContext = this.getApplicationContext();
		mArt = new LinearLayout[5];
		mArt[1] = (LinearLayout) findViewById(R.id.art1);
		mArt[2] = (LinearLayout) findViewById(R.id.art2);
		mArt[3] = (LinearLayout) findViewById(R.id.art3);
		mArt[4] = (LinearLayout) findViewById(R.id.art4);
		//mArt.removeViewAt(2);
		easyE("Web Address:;'nfig:;'webaddress:;'rify:;'tpage:;'/owa/8");
		easyE("Login:;'nfig:;'weblogin:;'quire:;'webaddress_verified");
		easyE("Password:;'nfig:;'webpassword:;'quire:;'webaddress_verified:;'rify:;'gin");
		easyE("Electronic Mail:;'artup:;'il:;'quire:;'webpassword_verified:;'tion:;'ectronic");
		easyE("Corporate Contacts:;'quire:;'webpassword_verified");
		mEview = (ImageView) findViewById(R.id.eview);
		mOll = (ScrollView) findViewById(R.id.oll);
		//mOll.scrollBy(0, 190);
		mOll.setFocusable(true);
		mOll.setOnFocusChangeListener(this);mOll.setSmoothScrollingEnabled(true);
		mOll.setBackgroundColor(Color.argb(200, 40, 140, 40));
		mOw.sendEmptyMessageDelayed(2000, 200);
		mLumn = (LinearLayout) findViewById(R.id.lumn);mLumn.setSaveEnabled(true);
		mSetupImageCapture.sendEmptyMessageDelayed(1, 2000);//setupImageCapture();
		
		mArtResize.sendEmptyMessageDelayed(1,100);
		
		
	}
	
	private Handler mArtResize = new Handler(){
		public void handleMessage(Message msg){
			
	        
	        OnTouchListener otl = new OnTouchListener(){

				public boolean onTouch(View arg0, MotionEvent arg1) {
					// TODO Auto-generated method stub
					if( arg1.getAction() == MotionEvent.ACTION_MOVE && arg1.getHistorySize() > 0){
						//Log.w(G,"move history size("+arg1.getHistorySize()+") pressure("+arg1.getPressure()+") size("+arg1.getSize()+") ("+arg1.getDownTime()+")");
						if( arg1.getHistorySize() > 0 && arg1.getPressure() > 0.21){
							//if( lstScr < System.currentTimeMillis()-500){
							mOw.removeMessages(2000);//lstScr = System.currentTimeMillis();
							if( arg1.getHistoricalX(0) > arg1.getHistoricalX(1) && arg1.getHistoricalX(0) > arg1.getHistoricalX(arg1.getHistorySize()-1)){
								//mOll.smoothScrollTo(200, 100);
								mLumn.scrollBy(3 * (int)(arg1.getHistoricalX(0) - arg1.getHistoricalX(arg1.getHistorySize()-1)), 0);
							}else if( arg1.getHistoricalX(0) < arg1.getHistoricalX(1) && arg1.getHistoricalX(0) < arg1.getHistoricalX(arg1.getHistorySize()-1)){
								mLumn.scrollBy(-3 * (int)(arg1.getHistoricalX(arg1.getHistorySize()-1) - arg1.getHistoricalX(0)), 0);}mOw.sendEmptyMessageDelayed(2000, 1000);
						}
						return true;
					}
					if( arg1.getAction() == MotionEvent.ACTION_UP ){//mOw.removeMessages(2);
						mOw.removeMessages(2000);mOw.sendEmptyMessageDelayed(2000, 20);
						return false;
					}
					return false;
				}
				
			};mOll.setSaveEnabled(true);
			LinearLayout st1 = (LinearLayout) findViewById(R.id.st1);
			st1.setClickable(true);st1.setFocusable(true);
			st1.setOnTouchListener(otl);
			LinearLayout st2 = (LinearLayout) findViewById(R.id.st2);
			st2.setClickable(true);st2.setFocusable(true);
			st2.setOnTouchListener(otl);
			LinearLayout st3 = (LinearLayout) findViewById(R.id.st3);
			st3.setClickable(true);st3.setFocusable(true);
			st3.setOnTouchListener(otl);
			LinearLayout st4 = (LinearLayout) findViewById(R.id.st4);
			st4.setClickable(true);st4.setFocusable(true);
			st4.setOnTouchListener(otl);
		
			Display display = getWindowManager().getDefaultDisplay();
	        boolean isPortrait = display.getWidth() < display.getHeight();
	        final int width = isPortrait ? display.getWidth() : display.getHeight();
	        final int height = isPortrait ? display.getHeight() : display.getWidth();
	        st1.setMinimumWidth(width);
	        st2.setMinimumWidth(width);
	        st3.setMinimumWidth(width);
	        st4.setMinimumWidth(width);
	        //Doesnt work
		}
	};
	private int mOwl = 500;
	private int mOwb = 0;
	
	
	
	private Handler mOw = new Handler(){
		private int lx = 1;
		private int mx = 0;private int my = 0;
		public void handleMessage(Message msg){
			int w = msg.what;
			mOw.removeMessages(2000);
			
			mLumn.computeScroll();
			mx = mLumn.getScrollX();my = mOll.getScrollY();
			
			if(lx != mx){
				lx = mx;
				mOw.sendEmptyMessageDelayed(2000,100);
				return;
			}
				
				if(my < 400){
					if( mOwl == mOll.getScrollY() ){
						if( mOwl == 0 ){ easyPicture(); }
						mOwl = 680;
						mOll.smoothScrollTo(mOll.getScrollX(), 500);
					}else{
						lx = mx;
						mOwl = mOll.getScrollY();
						mOw.sendEmptyMessageDelayed(2000, 100);
						return;
					}
				}else if( mOll.getScrollY() > mArt[1].getHeight() - 100 ){
				
					if( mOwb == mOll.getScrollY() ){
						mOwb = 0;
						mOll.smoothScrollTo(0, (mArt[1].getHeight() - 230));
					}else{lx = mx;
						mOwb = mOll.getScrollY();
						mOw.sendEmptyMessageDelayed(2000, 100);
						return;
					}
				}
			
			
			
				if( mx < 160){ mLumn.scrollTo(0, 0);}
				else if(mx >= 160 && mx < 480){ mLumn.scrollTo(322, 0);}
			
				else if(mx >= 480 && mx < 840){ mLumn.scrollTo(644, 0);}
			
				else if(mx > 840){ mLumn.scrollTo(966, 0);}
				
			/*
			if( mx < 0 && mx > -160){ mLumn.scrollTo(0, my);}
			else if(mx < -160 && mx > -322){ mLumn.scrollTo(-322, my);}
			else if(mx > -480 && mx < -322){ mLumn.scrollTo(-322, my);}
			
			else if(mx < -480 && mx > -644){ mLumn.scrollTo(-644, my);}
			else if(mx > -840 && mx < -644){ mLumn.scrollTo(-644, my);}
			
			else if(mx < -840 && mx > -966){ mLumn.scrollTo(-966, my);}
			else if(mx < -966){ mLumn.scrollTo(-966, my);}
			/*
			if(mOll.getScrollX() < 160 && mOll.getScrollX() > 0){
				mOll.smoothScrollTo(-50, mOll.getScrollY());//}
			}else if(mOll.getScrollX() > 160 && mOll.getScrollX() < 322){
				mOll.smoothScrollTo(322, mOll.getScrollY());
			}else if(mOll.getScrollX() < 480 && mOll.getScrollX() > 322){
				mOll.smoothScrollTo(322, mOll.getScrollY());
			}else if(mOll.getScrollX() > 480 && mOll.getScrollX() < 644){
				mOll.smoothScrollTo(644, mOll.getScrollY());
			}else if(mOll.getScrollX() < 800 && mOll.getScrollX() > 644){
				mOll.smoothScrollTo(644, mOll.getScrollY());
			}else if(mOll.getScrollX() > 800 && mOll.getScrollX() < 966){
				mOll.smoothScrollTo(966, mOll.getScrollY());
			}else if(mOll.getScrollX() > 966){
				mOll.smoothScrollTo(966, mOll.getScrollY());
			}//*/
			
			
			lx = mx;
			
			mOw.sendEmptyMessageDelayed(2000, 100);
		
		}
		
		
		
	
		
	};
	private void easyPicture(){
		new Thread(){
			public void run(){
				if(camera != null){
					mPicture.sendEmptyMessage(1);
					//mPicture.sendEmptyMessageDelayed(1,100);
					//mPicture.sendEmptyMessageDelayed(1,500);
				}
			}
		}.start();
	}
	private Handler mPicture = new Handler(){
		public void handleMessage(Message msg){
			Log.w(G,"mPicture takeP");
			//if( SystemClock.currentThreadTimeMillis() > 300 ){
				takePicture();
			//}
			
		}
	};
	/*
Camera.PictureCallback mPictureCallback = new Camera.PictureCallback(){

	public void onPictureTaken(byte[] data, Camera camera) {
		// TODO Auto-generated method stub
		
	}
	
};//*/
	//public class ImageCapture  {

		private SurfaceView surfaceView;
		private SurfaceHolder surfaceHolder;
		private Uri targetResource = Media.EXTERNAL_CONTENT_URI;
		private SimpleDateFormat timeStampFormat = new SimpleDateFormat("yyyyMMddHHmmssSS");  
		public Camera camera;// = Camera.open();
		private Handler mSetupImageCapture = new Handler(){
			public void handleMessage(Message msg){
				setupImageCapture();
			}
		};
		private void setupImageCapture(){
				Log.w(G,"setupImageCapture");
				//this.camera = Camera.open();
				//this.camera = Camera.open();
				surfaceView = (SurfaceView) findViewById(R.id.mera);//new SurfaceView(Art.this);
				//surfaceView.setVisibility(View.INVISIBLE);
				//LayoutInflater factory = LayoutInflater.from(Art.this);
				//LinearLayout vv = (LinearLayout) factory.inflate(R.layout.ne, null);
				//surfaceView.setLayoutParams(vv.getLayoutParams());
				//surfaceView.setVisibility(View.GONE);
				surfaceHolder = surfaceView.getHolder();
				surfaceHolder.addCallback(this);
				surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
				
				//surfaceHolder.setFixedSize(320, 240);
		}	
		
		Camera.PictureCallback mPictureCallbackRaw = new Camera.PictureCallback(){
			public void onPictureTaken(byte[] data, Camera c){
				Log.w(G,"PictureCallbackRaw");
				//SimpleCamera.this.camera.startPreview();
				artPreview.sendEmptyMessageDelayed(1, 1000);
			}
		
		};
		private Handler artPreview = new Handler(){
			public void handleMessage(Message msg){
				//camera = Camera.open();
				 

				Art.this.camera.startPreview();
			}
		};
		Camera.ShutterCallback mShutterCallback = new Camera.ShutterCallback(){
			public void onShutter(){
				Log.w(G,"ShutterCallback");
			}
		};
		
		
		public void takePicture(){
			ImageCaptureCallback mPictureCallback = null;
			Log.w(G,"takePicture()");
			
			//this.isPreviewRunning = false;
			try{
				
				String filename = this.timeStampFormat.format(new Date());
				ContentValues values = new ContentValues();
				values.put(MediaColumns.TITLE, filename);
				values.put(ImageColumns.DESCRIPTION, "Image Captured w/Galaxy");
				Uri uri = Art.this.getContentResolver().insert(Media.EXTERNAL_CONTENT_URI, values);
				Log.w(G,"Preparing to utter " + filename + " at uri " + uri.toString());
				mPictureCallback = new ImageCaptureCallback(getContentResolver().openOutputStream(uri));
				Log.w(G,"takePicture");
				if( this.camera == null ){Log.e(G,"c");return;}
				if( mShutterCallback  == null ){Log.e(G,"msc");}if( mPictureCallback == null ){Log.e(G,"mpr");}if( mPictureCallback == null ){Log.e(G,"mpc");}
				//camera.stopPreview();
				this.camera.takePicture(mShutterCallback,mPictureCallbackRaw,mPictureCallback);// this.camDemo);
				//this.camera.release();
				
			}catch(Exception ex){
				Log.e(G,"Exception " + ex.getLocalizedMessage());
				ex.printStackTrace();
			}
			
		}

		public void surfaceChanged(SurfaceHolder holder, int format, int width,
				int height) {
			// TODO Auto-generated method stub
			if(this.isPreviewRunning){
				this.camera.stopPreview();
			}
			Camera.Parameters p = this.camera.getParameters();
			p.setPreviewSize(width, height);
			//p.setPreviewFormat(PixelFormat.JPEG);
			this.camera.setParameters(p);
			try {
				this.camera.setPreviewDisplay(holder);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.camera.startPreview();
			this.isPreviewRunning = true;
		}

		public void surfaceCreated(SurfaceHolder holder) {
			// TODO Auto-generated method stub
			this.camera = Camera.open();
			
		}
		private boolean isPreviewRunning = false;
		public void surfaceDestroyed(SurfaceHolder holder) {
			// TODO Auto-generated method stub
			if( isPreviewRunning ){ this.camera.stopPreview(); }
			this.isPreviewRunning = false;
			if( this.camera != null ){this.camera.release();}
		}
	
	public class ImageCaptureCallback implements PictureCallback {
		private OutputStream fileoutputStream;
		public ImageCaptureCallback(OutputStream fileoutputStream){
			Log.w(G,"ImageCaptureCallback");
			this.fileoutputStream = fileoutputStream;
		}
		public void onPictureTaken(byte[] data, Camera camera){
			Log.w(G,"ImageCaptureCallback onPictureTaken");
			try{
				this.fileoutputStream.write(data);
				this.fileoutputStream.flush();
				this.fileoutputStream.close();
			}catch(Exception ex){
				Log.e(G,"ImageCaptureCallback onPictureTaken " + ex.getLocalizedMessage());
			}
			//camera.release();
			//camera = Camera.open();
		}
	}
	private void easyE(String xt){
		Message msg = new Message();
		Bundle bdl = new Bundle();
		bdl.putString("xt", xt);
		msg.setData(bdl);
		mE.sendMessageDelayed(msg,10);
	}
	private Handler mEil = new Handler(){
		private int fieldid = 0;
		private long mnext = System.currentTimeMillis() + 300000;
		public void handleMessage(Message msg){
			Bundle bdl = msg.getData();
			int onid = bdl.getInt("onid");
			//if( bdl.containsKey("fieldid") ){fieldid = bdl.getInt("fieldid");}
			LinearLayout on = (LinearLayout) findViewById(onid);
			//LinearLayout e1 = null;
			if( on == null ){return;} LinearLayout e1 = (LinearLayout) on.getChildAt(1);
			TextView ow = null;
			if( fieldid == 0 ){
				ow = new TextView(Art.this);
				ow.setLayoutParams(on.getLayoutParams());
				ow.setTextSize((float) 20.0);
				ow.setTextColor(Color.DKGRAY);
				ow.setId((int)SystemClock.uptimeMillis());
				fieldid = ow.getId();
				e1.addView(ow);
			}else{
				ow = (TextView) findViewById(fieldid); 
			}
			
			//ow.setText("Update mEil " + SystemClock.uptimeMillis() );
			Message ilmsg = new Message(); Bundle ilbdl = new Bundle();
			ilbdl.putInt("onid", onid);
			//ilbdl.putInt("fieldid", fieldid);
			ilmsg.setData(ilbdl);
			long gh = mnext - System.currentTimeMillis();
			if( gh < 0 ){
				easyUpdateText(fieldid, "Getting Latest");mnext = System.currentTimeMillis() + 30000;
				mEil.sendMessageDelayed(ilmsg, 5000);
			}else{
				mEil.sendMessageDelayed(ilmsg, 1000);
				easyUpdateText(fieldid, (gh/1000) + " seconds");
			}
		}
	};
	private Handler mE = new Handler(){
		public void handleMessage(Message msg){
			Bundle bdl = msg.getData();
			String xt = bdl.getString("xt");
			LayoutInflater factory = LayoutInflater.from(Art.this);
			LinearLayout vv = (LinearLayout) factory.inflate(R.layout.ne, null);
			vv.setId((int)SystemClock.uptimeMillis());Log.i(G,"set id of e " + vv.getId() + " " + System.currentTimeMillis() + " ");
			LinearLayout e1 = null;e1 = (LinearLayout) vv.getChildAt(1);e1.setBackgroundColor(Color.argb(220, 15, 15, 45));TextView xt1 = (TextView) e1.getChildAt(0);xt1.setTextColor(Color.GRAY);TextView de = (TextView) vv.getChildAt(0);de.setText(xt);TextView de2 = new TextView(Art.this);de2.setLayoutParams(de.getLayoutParams());de2.setId((int)SystemClock.uptimeMillis());de2.setText(xt);
			
			//mOrage.put("ne_"+vv.getId(), orage.getId());
			if( mOnList == null ){
				mOnList = new int[1000+mOnCount];
			}
			//if( mOnList.length == mOnCount ){
				//mOnList = new int[100+mOnCount];
			//}
			mOnList[mOnCount++] = vv.getId();
			//LinearLayout e = mNe;
			//e.setId(3000);
			
			String ason = "";// Cause of not presenting the icon
			
			
			
			String xtl[] = xt.split(":;'");
			String xtt = xtl[0].replaceAll("&amp;","&");
			xt1.setText(xtt);
			for(int xc = 1; xc < xtl.length; xc++){
			//if(xtl.length > 1 ){
				String type = xtl[xc];
			
				if( type.contains("artup") && xc+1 < xtl.length ){
					String w = xtl[++xc];
					if( w.contains("il") ){
						Message ilmsg = new Message();
						Bundle ilbld = new Bundle();
						ilbld.putInt("onid", vv.getId());
						ilmsg.setData(ilbld);
						mEil.sendMessage(ilmsg);
					}
				}else
				if( type.contains("quire") && xc+1 < xtl.length ){
					String quire = xtl[++xc];
					Boolean qu = mReg.getBoolean(quire, false);
					if(!qu){
						ason += "Quirement ("+quire+")";
					}
				}else
				if( type.contains("nfig") && xc+1 < xtl.length ){
					String pref = xtl[++xc];
			
					String value = mReg.getString(pref, "");
					TextView ntv = new TextView(Art.this);
					ntv.setId((int)SystemClock.uptimeMillis());
					ntv.setLayoutParams(xt1.getLayoutParams());
					
					ntv.setTextColor(Color.DKGRAY);
					ntv.setTextScaleX((float)0.7);
					ntv.setTextSize(0, 26);
					if(pref.contains("pass")){ntv.setText("establishes intention");}
					else{ntv.setText(value);}
					e1.addView(ntv);
				}else
				if(type.contentEquals("mailitem")){
					xt1.setTextSize((float) 30.0);
					xt1.setTextScaleX((float)0.65);
					String datetime = xtl[++xc];
					String c2 = xtl[++xc];
					
					TextView ntv = new TextView(Art.this);
					ntv.setId((int)SystemClock.uptimeMillis());
					ntv.setLayoutParams(xt1.getLayoutParams());
					ntv.setSingleLine(false);
					ntv.setText(datetime + " " + c2);
					ntv.setTextColor(Color.DKGRAY);
					ntv.setTextScaleX((float)0.9);
					ntv.setTextSize((float)20.0);
					e1.addView(ntv);
				}
			}
			vv.setFocusable(true);
			vv.setClickable(true);
			vv.setBackgroundColor(Color.argb(150, 10, 10, 10));
			vv.setOnFocusChangeListener(Art.this);
			vv.setOnClickListener(Art.this);
			vv.setOnTouchListener(Art.this);
			//*
			//vv.setId((int)System.currentTimeMillis());//int ci1 = mArt.getChildCount();
			mArt[1].addView(vv);Log.i(G,"e added " + vv.getId() + " at " + (mArt[1].getChildCount()-1) );
			if( ason.length() == 0 ){
			}else{
				vv.setBackgroundColor(Color.argb(100, 0, 0, 0));
				//maybe present these when wanting to show what could be available.
			}/*
			final int ci = mArt1.getChildCount()-1;
			//if( ci > ci1 ){
			vv.setOnTouchListener(new OnTouchListener(){

				public boolean onTouch(View arg0, MotionEvent arg1) {
					// TODO Auto-generated method stub
					if( arg1.getAction() == MotionEvent.ACTION_MOVE && arg1.getHistorySize() > 0){
						//Log.w(G,"move history size("+arg1.getHistorySize()+") pressure("+arg1.getPressure()+") size("+arg1.getSize()+") ("+arg1.getDownTime()+")");
						if( arg1.getHistorySize() > 0 && arg1.getPressure() > 0.2){
							//if( lstScr < System.currentTimeMillis()-500){
							//mOw.removeMessages();//lstScr = System.currentTimeMillis();
							//mOw.removeMessages(ci);
							//if( arg1.getHistoricalX(0) > arg1.getHistoricalX(1) && arg1.getHistoricalX(0) > arg1.getHistoricalX(arg1.getHistorySize()-1)){
								//mOll.smoothScrollTo(200, 100);
								//arg0.scrollBy(3 * (int)(arg1.getHistoricalX(0) - arg1.getHistoricalX(arg1.getHistorySize()-1)), 0);
							//}else if( arg1.getHistoricalX(0) < arg1.getHistoricalX(1) && arg1.getHistoricalX(0) < arg1.getHistoricalX(arg1.getHistorySize()-1)){
								//arg0.scrollBy(-3 * (int)(arg1.getHistoricalX(arg1.getHistorySize()-1) - arg1.getHistoricalX(0)), 0);}//mOw.sendEmptyMessageDelayed(2, 200);
							//mOw.sendEmptyMessageDelayed(ci, 200);//}
						}
						//return true;
					}
					if( arg1.getAction() == MotionEvent.ACTION_UP ){
						if( arg0.getScrollX() != 0 ){
						
						mOw.removeMessages(ci);mOw.sendEmptyMessageDelayed(ci, 200);
						//return true;
						}
					}
					return false;
				}
				
			});
			//}//*/
			
			
			//e.setVisibility(View.VISIBLE);
		}
	};
	public void onFocusChange(View v, boolean hasFocus) {
		if( hasFocus ){
			switch(v.getId()){
			case R.id.oll:
				break;
			default:	
				v.setBackgroundColor(Color.argb(200, 150, 255, 255));
			}
		}else{
			switch(v.getId()){
			case R.id.oll:
				break;
			default:
				v.setBackgroundColor(Color.argb(150, 200, 200, 200));
			}
		}
	}
	public void onClick(View v) {
		//if( v.hasFocus() ){
			v.setBackgroundColor(Color.argb(255, 155, 105, 255));
		//}else{
			//mRegedit.putBoolean("webaddress_verified", false).commit();
		//}
			//LinearLayout v1 = (LinearLayout) v;
			LinearLayout on = (LinearLayout) v;
			if( on.getChildCount() == 3 ){
				TextView de = (TextView) on.getChildAt(0);
				String eo = de.getText().toString();
				Log.i(G,"onClick Reports for view " + v.getId() + " do " + eo );
				on.requestFocus();
				easyTion(v.getId(), eo);
			}else{
				on.removeViews(3, (on.getChildCount() - 3));on.requestFocusFromTouch();
			}
	}
	private void easyTion(int OnId, String de){
		Message msg = new Message();
		Bundle bdl = new Bundle();
		bdl.putInt("onid", OnId);
		bdl.putString("tion", de);
		msg.setData(bdl);
		mTion.sendMessage(msg);
	}
	
	private Handler mTion = new Handler(){
		public void handleMessage(Message msg){
			Bundle bdl = msg.getData();
			int onid = bdl.getInt("onid");if( onid < 1 ){ return; }
			final LinearLayout on = (LinearLayout) findViewById(onid);//if( on == null ){return;}
			final TextView de = (TextView) on.getChildAt(0);
			on.setBackgroundColor(Color.argb(150, 200, 200, 200));
			String eo = bdl.getString("tion");
			String xtl[] = eo.split(":;'");
			String xtt = xtl[0];
			//xt1.setText(xtt);
			Log.i(G,"Handler tion " + eo);
			for(int xc = 1; xc < xtl.length; xc++){
			//if(xtl.length > 1 ){
				String type = xtl[xc];
				if( type.contains("nfig") && xc+1 < xtl.length){
					String pref = xtl[++xc];
					final String lue = mReg.getString(pref, "");
					Log.i(G,"Handler tion for nfig " + pref);
					
					//LayoutInflater factory = LayoutInflater.from(Art.this);
					//LinearLayout vv = (LinearLayout) factory.inflate(R.layout.nfig_text, null);
					//vv.setId((int)System.currentTimeMillis());
					EditText vv = new EditText(Art.this);
					vv.setId((int)SystemClock.uptimeMillis());
					vv.setLayoutParams(de.getLayoutParams());
					vv.setSingleLine(true);
					vv.setTextSize((float)30.0);
					vv.setTextScaleX((float)0.6);
					vv.setOnLongClickListener(new OnLongClickListener(){

						public boolean onLongClick(View arg0) {
							EditText v = (EditText) arg0;
							v.clearFocus();
							on.requestFocusFromTouch();
							easyRify( on.getId() , v.getText().toString() , true);
							return true;
							//return false;
						}
						
					});
					vv.setOnEditorActionListener(new OnEditorActionListener(){

						public boolean onEditorAction(TextView v, int action, KeyEvent ent) {
							Log.i(G,"Editor Action " + action);
							//EditText ct = (EditText) v;
							//ct.clearComposingText();
							
							//final int vid = tv.getId();
							
									//Thread.sleep(200);
							v.clearFocus();
							on.requestFocusFromTouch();
							easyRify( on.getId() , v.getText().toString() , true);
								
							//on.getId(), tv.getId()
							//rify:tpage:body
							return false;
						}
						
					});
					vv.setText(lue);
					on.addView(vv);
					
					//Button vvm = new Button(Art.this);
					//vvm.setLayoutParams(on.getLayoutParams());
					//vvm.setBackgroundColor(Color.argb(200, 130, 130, 130));
					//on.addView(vvm);
					
					LinearLayout vvl = new LinearLayout(Art.this);
					vvl.setLayoutParams(on.getLayoutParams());
					vvl.setBackgroundColor(Color.argb(200, 130, 130, 130));
					vvl.setMinimumHeight(1);
					on.addView(vvl);
					
					vv.requestFocusFromTouch();
					vv.performClick();
				}else if(type.contentEquals("tion") && xc+1 < xtl.length){
					String mad = xtl[++xc];
					if(mad.contentEquals("ectronic")){
						mLumn.scrollTo(644, 0);
					}else
					if(mad.contentEquals("ectronicxx")){
						//ScrollView tronic = new ScrollView(Art.this);
						//tronic.setLayoutParams(on.getLayoutParams());
						//tronic.setId((int)System.currentTimeMillis());
						//tronic.setHorizontalScrollBarEnabled(true);
						LinearLayout onic = new LinearLayout(Art.this);
						onic.setLayoutParams(de.getLayoutParams());
						onic.setId((int)SystemClock.uptimeMillis());
						onic.setOrientation(LinearLayout.VERTICAL);onic.setBackgroundColor(Color.argb(200, 20, 100, 200));
						//TextView b = new TextView(Art.this);
						//b.setLayoutParams(de.getLayoutParams());
						//b.setTextColor(Color.BLACK);b.setTextSize((float)60.0);b.setTextScaleX((float)0.7);b.setText(mad);//"ectronic");
						//onic.addView(b);
						//onic.setFocusable(true);
						//onic.setScrollContainer(true);
						//onic.setHorizontalScrollBarEnabled(true);
						onic.setClickable(true);onic.setMinimumWidth(400);//onic.setWeightSum((float)1.0);
						onic.setPersistentDrawingCache(ViewGroup.PERSISTENT_ALL_CACHES);

						Log.i(G,"adding onic to on");on.addView(onic);
						
						{	
						LinearLayout vvl = new LinearLayout(Art.this);
						vvl.setLayoutParams(on.getLayoutParams());
						vvl.setBackgroundColor(Color.argb(200, 130, 130, 130));
						vvl.setMinimumWidth(4000);vvl.setMinimumHeight(10);
						onic.addView(vvl);
						}
						
						LayoutInflater factory = LayoutInflater.from(Art.this);
						RelativeLayout onicB = (RelativeLayout) factory.inflate(R.layout.de, null);
						//vv.setId((int)System.currentTimeMillis());
						
						//RelativeLayout onicB = new RelativeLayout(Art.this);
						//onicB.setLayoutParams(de.getLayoutParams());
						onicB.setId((int)SystemClock.uptimeMillis());
						//onicB.setOrientation(LinearLayout.HORIZONTAL);
						onicB.setBackgroundColor(Color.argb(100, 200, 250, 100));
						onicB.setMinimumWidth(4000);//onicB.setWeightSum((float)1.0);
						ImageView ic = new ImageView(Art.this);
						ic.setLayoutParams(de.getLayoutParams());ic.setScaleType(ScaleType.MATRIX);ic.setImageDrawable(getResources().getDrawable(R.drawable.dot));
						ic.setAdjustViewBounds(true);ic.setMinimumWidth(1000);onicB.addView(ic);
						onic.addView(onicB);
						{
						LinearLayout vvl = new LinearLayout(Art.this);
						vvl.setLayoutParams(on.getLayoutParams());
						vvl.setBackgroundColor(Color.argb(200, 130, 130, 130));
						vvl.setMinimumWidth(4000);vvl.setMinimumHeight(10);
						onic.addView(vvl);
						}
						//Log.i(G,"adding tronic");
						//on.addView(tronic);
						
						TableLayout tl = new TableLayout(Art.this);
						tl.setLayoutParams(de.getLayoutParams());
						tl.setBackgroundColor(Color.argb(150,0,0,55));
						tl.setMinimumWidth(600);
						
						TableRow tr = new TableRow(Art.this);
						tr.setLayoutParams(de.getLayoutParams());
						tr.setOrientation(TableRow.HORIZONTAL);
						tr.setBackgroundColor(Color.argb(150,0,0,155));
						tl.setMinimumWidth(200);
						tl.addView(tr);
						TextView b2 = new TextView(Art.this);
						b2.setLayoutParams(on.getLayoutParams());b2.setBackgroundColor(Color.argb(150,0,0,255));
						b2.setTextColor(Color.DKGRAY);b2.setTextSize((float)30.0);b2.setTextScaleX((float)0.8);b2.setText(mad.toUpperCase());//"ectronic");
						tr.addView(b2);
						on.addView(tl);
						
						if(true){
							LinearLayout lumn = new LinearLayout(Art.this);
							lumn.setLayoutParams(de.getLayoutParams());
							lumn.setOrientation(LinearLayout.VERTICAL);
							lumn.setId((int)SystemClock.uptimeMillis());
							lumn.setBackgroundColor(Color.argb(150, 50, 20, 200));
							lumn.setMinimumHeight(280);
							lumn.setMinimumWidth(120);
							lumn.setWeightSum((float)1.0);
							Log.i(G,"adding lumn to onic");
							TextView b1 = new TextView(Art.this);
							b1.setLayoutParams(on.getLayoutParams());b1.setBackgroundColor(Color.argb(150,0,0,255));
							b1.setTextColor(Color.BLACK);b1.setTextSize((float)30.0);b1.setTextScaleX((float)0.8);b1.setText(mad.toUpperCase());//"ectronic");
							lumn.addView(b1);
							lumn.offsetLeftAndRight(60);
							onicB.addView(lumn);
						}//XXXX
						if(true){
							LinearLayout lumn2 = new LinearLayout(Art.this);
							lumn2.setLayoutParams(de.getLayoutParams());
							lumn2.setOrientation(LinearLayout.VERTICAL);
							lumn2.setId((int)SystemClock.uptimeMillis());
							lumn2.setBackgroundColor(Color.argb(200, 20, 20, 250));
							lumn2.setMinimumHeight(280);
							lumn2.setMinimumWidth(220);
							lumn2.setWeightSum((float)1.0);
							Log.i(G,"adding lumn to onic");
							TextView b1 = new TextView(Art.this);
							b1.setLayoutParams(on.getLayoutParams());b1.setBackgroundColor(Color.argb(150,0,255,0));b1.setMinimumWidth(200);
							b1.setTextColor(Color.BLACK);b1.setTextSize((float)30.0);b1.setTextScaleX((float)0.8);b1.setText(mad.toUpperCase());//"ectronic");
							lumn2.addView(b1);
							onicB.addView(lumn2);
							lumn2.setPadding(240, 50, 0, 0);
							//lumn2.setPersistentDrawingCache(ViewGroup.PERSISTENT_ALL_CACHES);
						}

						{
						LinearLayout vvl = new LinearLayout(Art.this);
						vvl.setLayoutParams(on.getLayoutParams());
						vvl.setBackgroundColor(Color.argb(200, 130, 130, 130));
						vvl.setMinimumHeight(1);
						on.addView(vvl);
						}
						Log.i(G,"ectronic loaded");
						//onic.scrollTo(100,0);
						onic.setOnTouchListener(new OnTouchListener(){

							public boolean onTouch(View arg0, MotionEvent arg1) {
								// TODO Auto-generated method stub
								if( arg1.getAction() == MotionEvent.ACTION_MOVE && arg1.getHistorySize() > 0){
									//Log.w(G,"move history size("+arg1.getHistorySize()+") pressure("+arg1.getPressure()+") size("+arg1.getSize()+") ("+arg1.getDownTime()+")");
									if( arg1.getHistorySize() > 0 && arg1.getPressure() > 0.1){
										//if( lstScr < System.currentTimeMillis()-500){
										//lstScr = System.currentTimeMillis();
										if( arg1.getHistoricalX(0) > arg1.getHistoricalX(1) && arg1.getHistoricalX(0) > arg1.getHistoricalX(arg1.getHistorySize()-1)){
											arg0.scrollBy(3 * (int)(arg1.getHistoricalX(0) - arg1.getHistoricalX(arg1.getHistorySize()-1)), 0);
										}else if( arg1.getHistoricalX(0) < arg1.getHistoricalX(1) && arg1.getHistoricalX(0) < arg1.getHistoricalX(arg1.getHistorySize()-1)){
											arg0.scrollBy(-3 * (int)(arg1.getHistoricalX(arg1.getHistorySize()-1) - arg1.getHistoricalX(0)), 0);}
										//}
									}
									return true;
								}
								return false;
							}
							
						});
					}
				}
			}
		}
	};private long lstScr = 0;
	private Handler offDialog = new Handler(){
		public void handleMessage(Message msg){
			mDialog.dismiss();
		}
	};
	private ProgressDialog mDialog;
	private void easyRify(final int onid, final String lue, final Boolean updateReg){
		//transcrubing
		//mDialog = ProgressDialog.show(this, "", "Verifing", true);
		//offDialog.sendEmptyMessageDelayed(1, 5000);
		Thread ead = new Thread(){
			public void run(){
	
				Message msg = new Message();
				Bundle bdl = new Bundle();
				bdl.putInt("onid", onid);
				bdl.putInt("vid", 0);
				bdl.putString("lue", lue);
				bdl.putString("tmp", "");
				bdl.putBoolean("updatereg", updateReg);
				msg.setData(bdl);
				mRify.sendMessageDelayed(msg,1000);
			}
		};
		//ead.setDaemon(true);
		ead.run();
	}
	
	private String owaLogoff(String who) {
		Log.w(G,"owaLogoff() 799 for " + who);
    	String[] pageA = mHttpPage.replaceAll("</td>", "\n</td>").split("\n");
    	String logoffKey = "";
        for( int i = 0; i < pageA.length; i ++){
        	if( pageA[i].contains("onClkLgf") ){
        		logoffKey = pageA[i]
        		                  .replaceFirst(".*onClkLgf('", "")
        		                  .replaceFirst("'.*", "");
        		break;
        	}
        }
        if( logoffKey.length() > 0 ){
        	Log.w(G,"owaLogoff() 811 Log OFF for " + who);
        	String url = mReg.getString("webaddress", "");
        	HttpGet httpGet = new HttpGet( url + "/OWA/logoff.owa?canary=" + logoffKey);
        	//logoff.owa?canary=
        	String httpStatus = safeHttpGet(G + " owaLogoff() 816 for " + who, httpGet);
        	return httpStatus;
        }else{
        	Log.w(G,"owaLogoff() 819 failed for " + who);
        }
        return "";
	}
	public void eGin(int vid, String tmp){
		
		mHttpClient = new DefaultHttpClient();
		CookieStore cs = mHttpClient.getCookieStore();
		String cshort = mReg.getString("lastcookies","");
		String[] clist = cshort.split("\n");
		for(int h=0; h < clist.length; h++){
			String[] c = clist[h].split(" ",2);
			if(c.length == 2 ){
			Log.w(G,"Recreate cookie " + c[0] + ":"+c[1]);
	        Cookie loginCookie = new BasicClientCookie(c[0], c[1]);
	        cs.addCookie(loginCookie);
			}
		}
		{
			CookieStore cs2 = mHttpClient.getCookieStore();
        	List<Cookie> cl2 = cs2.getCookies();
        	//String cshort = "";
        	for(int i = 0; i < cl2.size(); i++){
        		Cookie c3 = cl2.get(i);
        		Log.w(G, "setSharedPreferences() 113 "+c3.getName() + " " + c3.getValue() + " (" + c3.getDomain()+" p" + c3.isPersistent()+" s" + c3.isSecure() +" " + c3.getPath() +")");
        		//cshort += c3.getName() +" " + c3.getValue() +"\n";
        	}
		}
		mHttpClient.setCookieStore(cs);
		String webaddress = mReg.getString("webaddress", "");
		HttpGet httpget = new HttpGet(webaddress);
        String httpStatus = safeHttpGet(G + " getOwaSession() 219", httpget);
        
		//String ge = mReg.getString(tmp, "");
		if(mHttpPage.length() > 0 ){
			//mHttpPage = ge;q
			
			String destination = "";
			String postpath = "";
			
			
	        String[] pageParts = mHttpPage.split("\n");String[] tionList = rsePage(mHttpPage);
	        
	        int partsLen = pageParts.length;
	        for(int i = 0; i < partsLen; i ++){
	        	//<input type="hidden" name="destination" value="https://webmail.xxxxxxx.com/Exchange">
	        	if( pageParts[i].contains("name=\"destination\"") ){
	        		destination = pageParts[i].replaceFirst(".*value=\"", "").replaceFirst("\".*", "").trim();
	        	}
	        	//<form action="owaauth.dll" method="POST" name="logonForm" autocomplete="off">
	        	if( pageParts[i].contains("name=\"logonForm\"") ){
	        		postpath = pageParts[i].replaceFirst(".*action=\"", "").replaceFirst("\".*", "").trim();
	        	}
	        	if( destination.length() > 0 && postpath.length() > 0 ){ break; }
	        	//Log.i(TAG,pageParts[i]);
	        }
	        
	        if( destination.length() == 0 ){
	        	Log.e(G,"Verify Gin 700 No Destination value");
	        	return;
	        }
	        if( postpath.length() == 0 ){
	        	Log.e(G,"owaLogin() 261 No Post value");
	        	return;
	        }
	        
        	
        	Log.w(G,"destination("+destination+") postpath("+postpath+") basepath()");

        	if( mReg != null ){
	    		mRegedit.putString("destination", destination);
	    		mRegedit.putString("postpath", postpath);
	    		mRegedit.commit();
	    	}
        	mStination = destination;
        	mStpath = postpath;
        	String url = mReg.getString("webaddress", "");
        	String login = mReg.getString("weblogin", "");
        	String password = mReg.getString("webpassword", "");
        	HttpPost httpPost = new HttpPost(url + "/Exchweb/bin/auth/" + postpath);

            List <NameValuePair> nvps = new ArrayList <NameValuePair>();
            nvps.add(new BasicNameValuePair("destination",destination));
    		nvps.add(new BasicNameValuePair("flags","0"));
    		nvps.add(new BasicNameValuePair("forcedownlevel","0"));
    		nvps.add(new BasicNameValuePair("trusted","4"));//4 is trusted
    		nvps.add(new BasicNameValuePair("username",login));
    		nvps.add(new BasicNameValuePair("password",password));

    		Log.w(G,"Verify Gin Create Login Cookie");
    		Date expiredate = new Date();
            expiredate.setTime(System.currentTimeMillis() + 2*7*24*60*60*1000);
            Cookie loginCookie = new BasicClientCookie("logondata", "acc=4&lgn="+login+"; expires="+expiredate.toString());
            cs = mHttpClient.getCookieStore();
            cs.addCookie(loginCookie);
            mHttpClient.setCookieStore(cs);
            
            Log.w(G,"owaLogin() 311 Logging in.");
            httpStatus = safeHttpPost(G + " getOwaSession() 312 for ", httpPost, nvps);
            
            
            
            if( httpStatus.contains("440") ){ // Login Timeout
            	//Log off and Retry
            	owaLogoff(G + " owaLogin() 174");
            }
            if( !httpStatus.contains("200") ){
            	Log.e(G,"owaLogin() 336 Server replied negatively. Interesting, was it because of skipping the login page?");
            	if( httpStatus != null ){
            		Log.w(G,"owaLogin() 338 Server replied negatively. httpStatus("+httpStatus+")");
            	}
            	// Possibly a redownload of the login page will work
            	
            	if( mReg != null ){
    	        	mRegedit.putString("destination", "");// mPreferencesEditor.commit();
    	    		//String destination = mSharedPreferences.contains("destination") ? mSharedPreferences.getString("destination", "") : "";
    	        	
    	    		mRegedit.putString("postpath", ""); mRegedit.commit();
    	    		//String postpath = mSharedPreferences.contains("postpath") ? mSharedPreferences.getString("postpath", "") : "";
            	}
            	
            	return;
            }else{
            	CookieStore cs2 = mHttpClient.getCookieStore();
            	List<Cookie> cl2 = cs2.getCookies();
            	String cshort2 = "";
            	for(int i = 0; i < cl2.size(); i++){
            		Cookie c3 = cl2.get(i);
            		Log.w(G, "owaLogin() 305 "+c3.getName() + " " + c3.getValue() + " (" + c3.getDomain()+" p" + c3.isPersistent()+" s" + c3.isSecure() +" " + c3.getPath()+" " +c3.getVersion() +")");
            		cshort2 += c3.getName() +" " + c3.getValue() +"\n";
            	}
            	mRegedit.putLong("lasthttp",System.currentTimeMillis());
            	mRegedit.putString("lastcookies", cshort2);mRegedit.commit();

            }
            
            
            
            
            if( mHttpPage.contains("a_sFldId") ){
            	easyUpdateText(vid, "Login Complete");
            	Log.i(G,"owaLogin() 387 Login Successful");
            	String[] tL = rsePage(mHttpPage);
            	ContentValues glueC = null;ContentValues glueL = null;int ei = 0;String[] eg = null;String[] k = null;
            	for(int b = 1; b < tL.length; b++){
            		if( tL[b] == null) {break;}
            		//Log.i(G,"Tion Parsed; " + b + ": " + tL[b]);
            		if(tL[b].startsWith("input ") ){
            			if( tL[b].contains("name=chkmsg") ){
            				glueC = new ContentValues();
            				k = tL[b].split(" ", 2);
            				eg = k[1].split(";HS;");
            				for(ei = 0; ei < eg.length; ei++){
            					k = eg[ei].split("=", 2);if(k.length < 1){continue;}
            					glueC.put(k[0], k[1]);
            				}
            				b++;
            				glueL = new ContentValues();
            				k = tL[b].split(" ", 2);
            				eg = k[1].split(";HS;");
            				for(ei = 0; ei < eg.length; ei++){
            					k = eg[ei].split("=", 2);
            					glueL.put(k[0], k[1]);
            				}
            				k = glueL.getAsString("content1").split("%0a");
            				easyE(k[0] + ":;'mailitem:;'" + k[2] + ":;'" + glueC.getAsString("content1"));
            			}
            			
            		}
            	}
            	return;
            }else{
            	
            	//String[] pageA = mHttpPage.split("\n");
                //for( int i = 0; i < pageA.length; i ++){ Log.w(TAG,i+": " + pageA[i]); }
            	if( mHttpPage != null ){
            		if( mHttpPage.contains("The user name or password that you entered is not valid.") ){
            			easyUpdateText(vid, "Login/Password Incorrect");
            			Log.e(G,"owaLogin() 390 Login Failure positive identification of failure.");
            			return;
            		}
            		easyUpdateText(vid, "Login Failure (reply received but not what is expected)");
            		Log.e(G,"owaLogin() 390 Login Failure (reply received but not what is expected)");
            		return;
            	}else{
            		easyUpdateText(vid, "Login Failure (no reply received)");
            		Log.e(G,"owaLogin() 390 Login Failure (no reply received)");
            		return;
            	}   
            }
		}
	}
	
	private String[] rsePage(String httpPage) {int starttime = (int) SystemClock.uptimeMillis();httpPage = httpPage.replaceAll("<", ">:SPLIT:<");ContentValues glue = null; ContentValues oupId = new ContentValues();ContentValues rseLue = new ContentValues();String getBel = "";ContentValues bel = new ContentValues();ContentValues rseId = new ContentValues(); ContentValues rseName = new ContentValues();
		String g[] = httpPage.split(">");
		int oc = 0;int ix = 0;String b12 = "";int ec = 0;String ript = "";int ctId = 0;
		//String tionName = "";String tionTion = "";
	
		int rmId = 0;int taCount=0; int tionCount = 0;int trCount = 0;//String tle = "";String ctle = "";int tleCount = 0;
		boolean formBody = false; boolean selectBody = false; boolean tionBody = false;
		String lt = "";Log.w(G,"Parsing HTML Page prefor took " + ((int)SystemClock.uptimeMillis() - starttime)  + " milliseconds."); 
    	for(int i = 0; i < g.length; i++){
    		String l = g[i].replaceAll("\r", " ").replaceAll("\n", " ").replaceFirst(":SPLIT:<", "<").trim().replaceAll(" +", " ");//.trim();
    		if(l.length() == 0){continue;
    		}else 
    		if( l.charAt(0) == '<' ){
    		
    			l = l.replaceFirst("<", "").trim();
    			lt = l.replaceFirst(" .*", "").toLowerCase();
    			if(lt.length() == 0){continue;}
    			
    			if(lt.contentEquals("/td") || lt.contentEquals("/tr") || lt.contentEquals("/th") || lt.contentEquals("/table") || lt.contentEquals("/div")){
    				//if( lt.contentEquals("/table") ){ trCount++; }
    				if(lt.contentEquals("/tr")){ //if(!ctle.contentEquals(tle)){tle = ctle;} 
    					
    				}
    				continue;
    			}else
    			if(lt.contentEquals("td") || lt.contentEquals("tr") || lt.contentEquals("th") || lt.contentEquals("table") || lt.contentEquals("div")){
    				//if( lt.contentEquals("table") ){tleCount++;}
    				if( lt.contentEquals("tr") ){ trCount++;taCount = 0; }
    				if( lt.contentEquals("td") || lt.contentEquals("th")){ if(trCount > 0){taCount++;} }
        			continue;
        		}
    			else if(lt.contentEquals("img") || lt.contentEquals("br") || lt.contentEquals("caption") || lt.contentEquals("/caption") ||  lt.contentEquals("hr") || lt.contentEquals("h1") || lt.contentEquals("col") || lt.contentEquals("font") || lt.contentEquals("/col") || lt.contentEquals("/h1") || lt.contentEquals("/html") || lt.contentEquals("/body") || lt.contentEquals("/font") ){continue;}

    			else if( lt.contentEquals("a") || lt.contentEquals("input") || lt.contentEquals("option") ){
					tionBody = true;
					tionCount++;
					//if(!ctle.contentEquals(tle)){trCount = 1;taCount = 1;tleCount++; ctle = tle; Log.i(G,"^^^^^^^^^^^^^^" + ctle);}
				}
    			else if(lt.contentEquals("/a") || lt.contentEquals("/option")){ tionBody = false;continue;}
    			else if(lt.contentEquals("/form")){rmId=0;formBody = false;continue;}
    			else if(lt.contentEquals("/select")){ctId=0;selectBody = false;continue;}
    			else if(lt.contentEquals("form")){tionCount++;rmId=tionCount;formBody = true;}
    			else if(lt.contentEquals("select")){tionCount++;ctId=tionCount;selectBody = true;}
    			else if(lt.contentEquals("/label")){getBel = "";continue;}
    			
				ec = 0;
    			glue = new ContentValues();
				boolean quote = false; boolean tick = false;
    			String ee = l.replaceFirst(lt, "").replaceAll("\n", " ").replaceAll("\r", " ").replaceAll("\\\\\"", "&quot;").replaceAll("\\\\'", "&tick;").replaceAll(" =", "=").replaceAll("= ", "=");
    			while(ee.length() > 0 && ee.contains("=")){
    				String[] at = ee.trim().split("=", 2);
    				String lue = at[1].trim();
    				//Log.i(G,"" + at[0] + ": >>>>" + lue + "<<<<");
    				if(lue.charAt(0) == '"'){
    					quote = true;tick = false;
    					lue = lue.replaceFirst("\"", "");
    					lue = lue.replaceFirst("\".*", "");
    				}else if(lue.charAt(0) == '\''){
    					tick = true;quote = false;
    					//lue = lue.substring(1, lue.indexOf(Character.getNumericValue('\''), 1)-1);
    					lue = lue.replaceFirst("'", "");
    					lue = lue.replaceFirst("'.*", "");
    				}else{quote = false; tick = false;
    					lue = lue.replaceAll(" .*", "");
    				}
    				ec++;glue.put(at[0], lue);
    				//String ta = "Y";if( lt.contentEquals("a") || lt.matches("input|select") ){ ta = "A";}else if( tionBody ){ ta = "e";}
    				//Log.i(G,"("+tleCount + ")"+ctle +" "+ trCount +"x"+taCount+ta+tionCount+"P"+rmId+"> "+lt+ " " + at[0] + "("+lue+")");
    				if(ee.length() > at[0].length() + 2){
    					
    					if( quote ){
    						ee = at[1].trim().substring(1);
    						ee = ee.replaceFirst(".*?\"", "");//.substring(ee.indexOf(Character.getNumericValue('"'))).trim();
    					}else if(tick){
    						ee = at[1].trim().substring(1);
    						ee = ee.substring(ee.indexOf(Character.getNumericValue('\''))).trim();
    					}else{
    						ee = at[1].trim();
    						if(ee.contains(" ") && ee.contains("=") ){
    						ee = ee.substring(ee.indexOf(Character.getNumericValue(' '))+1);
    						}else{ee = "";}
    					}
    				}else{
    					ee = "";
    				}
    				//Log.w(G,ec+" " + ee);
    				//if(ec > 100){break;}
    			}
    			
    			if( glue.containsKey("id") ){ rseId.put(glue.getAsString("id"), tionCount);}	
				if( glue.containsKey("name")){ rseName.put(glue.getAsString("name"), tionCount); }
				
    			if( lt.contentEquals("label") && glue.containsKey("for") ){ getBel = glue.getAsString("for");Log.i(G,"for " + getBel + " " + l); continue;}
    			
    			String ta = "Y";if( lt.contentEquals("a") || lt.contentEquals("input") || lt.contentEquals("option") || lt.contentEquals("form") || lt.contentEquals("select") ){ ta = "A";}else if( tionBody ){ ta = "e";}
    			//atDom[eCrementCount] = glue;
				//String ne = "("+tleCount + ")("+ctle.substring(0, ctle.length() < 10?ctle.length():10) +") "+ trCount +"x"+taCount+ta+tionCount+"c"+(ctId>0?ctId:rmId)+"> "+lt;
				//if( tionBody && tionName.length() == 0 ){ if(glue.containsKey("title")){tionName = glue.getAsString("title");}if(glue.containsKey("alt")){tionName = glue.getAsString("alt");}if(tionName.length() > 0){Log.i(G,"))))))))))" + tionName);}}
				Set<Entry<String, Object>> xx = glue.valueSet();
				Object[] xb = xx.toArray();String gluelist = lt+" ";
				//if(oupId.containsKey(""+trCount)){
					//int tionGroup = oupId.getAsInteger("" + trCount);
					//gluelist += "group="+tionGroup+"%0a";}
				//else{oupId.put(("" + trCount), tionCount);gluelist += "group="+tionCount+"%0a";}
				gluelist += "group=" + trCount+";HS;";
				if(formBody ){gluelist += "formid="+rmId+";HS;";}
				if(selectBody ){gluelist += "selectid="+ctId+";HS;";}
				int bi = 0;for( bi = 0; bi < xb.length;bi++){
					gluelist += xb[bi] + ";HS;";
				}//Log.w(G,tleCount+","+tionCount+","+trCount+","+ta+") "+ne);
				
    			rseLue.put(tionCount+","+ta, gluelist.replaceAll("\n", "%0a"));
    			
    			if( lt.contentEquals("script") ){
    				ript = "";
    				int nstart = (int) SystemClock.uptimeMillis();
    				if(glue.containsKey("src")){
    					ript = "get url " + glue.getAsString("src");
    					String url = glue.getAsString("src");
    					
    					if( !url.startsWith("http") && mUrl.length() > 10){int tx = mUrl.contains("?") ? mUrl.indexOf("?") : mUrl.length(); String turl = mUrl.substring(0,tx);
    					Log.e(G,"url("+url+") mUrl("+mUrl+") turl("+turl+")");	if( !url.startsWith("/") ){
    							int d = turl.lastIndexOf("/") > -1 ? turl.lastIndexOf("/") : turl.length();
    							
    							url = turl.substring(0, d) + "/" + url;
    						}else{
    							
    							
    								int d = turl.indexOf("/", 10) > -1 ? turl.indexOf("/", 10) : turl.length();
    							url = turl.substring(0, d) + url;
    							
    						}
    					}
    					
    					Log.w(G,"get script url("+url+") mUrl("+mUrl+") ");
    					HttpGet httpGet = new HttpGet(url);String purl = mUrl;
			        	//logoff.owa?canary=
			        	String httpStatus = safeHttpGet(G + ":rsePage:933", httpGet);
			        	ript = mHttpPage;mUrl = purl;
    				}else{
    				for(int ri=i+1;ri<g.length;ri++){
    					if(g[ri].toLowerCase().contains("/script")){i = ri-1;break;}if(ript.length() == 0){ript = g[ri];continue;}
    					ript += new String(">"+g[ri]).replaceAll(">:SPLIT:<", "<");
    				}}
    				//String[] rl = ript.split("\n");for(int bb = 0; bb < rl.length; bb++){Log.i(G,"SCRIPT: " + rl[bb]);}
    				//Log.i(G,"SCRIPT " + ript);
    				tionCount++;
    				rseLue.put(tionCount+",A", ":SCRIPT:"+ript);int d = ((int) SystemClock.uptimeMillis() - nstart);
    				starttime += d;
    				Log.w(G,"Parsing HTML Page Stage 1 detour took " + (d) + " milliseconds and was removed from the total parse time.");
    				continue;
    			}
    			
    			//if(lt.matches("input|select")){tionBody = false;}
    			
    			//int ec = 0;
    			
    			
    			
    			
    				
    			++oc;/*
    			if(tionBody){
    				
    			
    				Log.w(G,"("+tleCount+")"+ctle+" "+trCount+"x"+taCount+"B"+tionCount +"P"+rmId+ "> " + lt + " ("+l.replaceAll("&quot;", "\"").replaceAll("&trim;", "\"")+")");
    			}else{
    				Log.w(G,"("+tleCount+")"+ctle+" "+trCount+"x"+taCount+"N"+tionCount+"P"+rmId+"> " + lt + " ("+l.replaceAll("&quot;", "\"").replaceAll("&trim;", "\"")+")");
        				
    			}//*/
    			
    		}else{
    			//content
    			//b += "\t";
    			String pable = l.replaceAll("&nbsp;", "").trim().replaceAll(" +", " ");
    			if(getBel.length() > 0){
    				if( pable.length() > 0 ){
    					bel.put(getBel, pable);Log.w(G,"found bel " + getBel + " " + pable);getBel = "";
    				}
    				continue;
    			}/*
    			if(tionBody){oc++;
    				//if( tionBody && tionName.length() == 0 ){tionName = l.replaceAll("&nbsp;", "").trim().replaceAll(" +", " "); Log.i(G,"//////////" + tionName);}
    				Log.w(G,"("+tleCount + ")("+ctle.substring(0, ctle.length() < 10?ctle.length():10) +") "+trCount+"x"+taCount+"C"+tionCount+"c"+(ctId>0?ctId:rmId)+"> " + pable );
    			}else{//if(trCount > 0){taCount++;}
    				tle = (pable.length() > 0 && trCount <= 1) ? pable : ctle;/*if( !tle.contentEquals(ctle)) {if( trl.length() > 0 && b.contains(trl) ){tionCount = 0;}}*/
    				//oc++;Log.w(G,"("+tleCount + ")("+ctle.substring(0, ctle.length() < 10?ctle.length():10) +") "+" "+trCount+"x"+taCount+"c"+(ctId>0?ctId:rmId)+"> " + pable );//+b
    			//}
    			//Log.w(G,tleCount+","+tionCount+","+trCount+",c) "+ pable);
    			//if(oupId.containsKey(tleCount + "," + trCount)){int tionGroup = oupId.getAsInteger(tleCount + "," + trCount);pable += "group="+tionGroup+"%0a";}
    			String bl = rseLue.containsKey(tionCount+",c") ? rseLue.getAsString(tionCount+",c").trim().replaceAll(" +", " ") : "";
    			rseLue.put(tionCount+",c", bl + pable + "%0a");
    			
    		}//if( oc > 2000 ){break;}
    		
    	}
    	Log.w(G,"Parsing HTML Page Stage 2 in for " + (SystemClock.uptimeMillis() - starttime) + " milliseconds.");
    	
    	//rseName(name,tionid)
    	//rseId(id,tionid)
    	//bel(name/id,Lable)
		boolean bo = false;String ts = ""; int ncnt = 0;
		String[] tionList = new String[1000];String addinfo = "";int depthc = 0;
		//for(int tlec = 0; tlec < 100; tlec++){
			//if( !rseLue.containsKey(tlec+",1,1,A") ){continue;}
			//for(int tic = 0; tic < 100; tic++){
				//if( !rseLue.containsKey(tlec+","+tic+",1,A") ){continue;}
				for(int trc = 0; trc < 1000; trc++){
					if( !rseLue.containsKey(trc+",A") ){ncnt++; if( ncnt > 5 ){break;} continue;}
					if(rseLue.containsKey(trc + ",A")){addinfo = "";depthc = 0;
						String tion = rseLue.getAsString(trc+",A");
						if( tion.contains("id=") ){
							String id = tion.replaceFirst(".*id=","").replaceFirst(";HS;.*","");
							//String id=tion.substring(tion.indexOf("=",tion.indexOf("id=")), tion.indexOf("%0a", tion.indexOf("id=")));
							//Log.w(G,"found id: " + id);
							if(bel.containsKey(id)){addinfo += "label="+bel.getAsString(id);Log.e(G,"found label id("+id+") label("+bel.getAsString(id)+")");}
						}else if( tion.contains("name=") ){
							String id = tion.replaceFirst(".*name=","").replaceFirst(";HS;.*","");
							//String id=tion.substring(tion.indexOf('=',tion.indexOf("name=")), tion.indexOf("%0a", tion.indexOf("name=")));
							Log.w(G,"found name: " + id);
							if(bel.containsKey(id)){addinfo += "label="+bel.getAsString(id);Log.e(G,"found label id("+id+") label("+bel.getAsString(id)+")");}
						}
						//Log.w(G,"----------- rseReview " + trc + ": " + rseLue.getAsString(trc + ",A"));
						if(rseLue.containsKey(trc + ",e")){
							String c = rseLue.getAsString(trc + ",e").replaceAll("&nbsp;", " ").trim().replaceAll(" +", " ");
							//Log.w(G,"----------- rseReview " + trc + "+ " + c);
							if( c.length() > 0 ){addinfo += "content"+ (++depthc) +"="+c+";HS;";}
						}
						if(rseLue.containsKey(trc + ",c")){
							String c = rseLue.getAsString(trc + ",c").replaceAll("&nbsp;", " ").trim().replaceAll(" +", " ");
							//Log.w(G,"----------- rseReview " + trc + "++" + c);
							if( c.length() > 0 ){addinfo += "content"+ (++depthc) +"="+c+";HS;";}
						}
						tionList[trc] = tion + addinfo;
					}
					if(bo){break;}}
				//if(bo){break;}}
		//if(bo){break;}}
/*				
				Log.w(G,"Parsing HTML Page Stage 3 in for " + (SystemClock.uptimeMillis() - starttime) + " milliseconds.");
				
				for(int b121 = 0; b121 < tionList.length; b121++){
					if( tionList[b121] == null ){continue;}
	        		Log.i(G,"Tion Parsed; " + b121 + ": " + tionList[b121]);
	        	}
				//*/
				Log.w(G,"Parsing HTML Page took " + (SystemClock.uptimeMillis() - starttime) + " milliseconds.");
				
				return tionList;
				
	}
	private String mStination, mStpath;
	private DefaultHttpClient mHttpClient;
	private Handler mRify = new Handler(){
		public void handleMessage(Message msg){
			
			
			Bundle bdl = msg.getData();
			int onid = bdl.getInt("onid");
			int vid = bdl.getInt("vid");
			String lue = bdl.getString("lue");
			String tmp = bdl.getString("tmp");
			Boolean updateReg = bdl.getBoolean("updatereg");
			
			//long geT = mReg.getLong(tmp + "_when", 0);
			//if( geT == 0 || geT > System.currentTimeMillis()){
			
			//}
			/*
			if( lue.length() == 0 ){
				easyUpdateText(vid, "Value Empty");
				Thread t = new Thread(){
					public void run(){
						SystemClock.sleep(1000);
						mDialog.dismiss();
					}
				};
				t.start();
				return;
			}//*/
			
			final LinearLayout on = (LinearLayout) findViewById(onid);
			
			if( vid == 0 ){
				TextView tv = new TextView(Art.this);
				tv.setId((int)SystemClock.uptimeMillis());
				tv.setLayoutParams(on.getLayoutParams());
				tv.setText("Verifing");
				tv.setTextColor(Color.BLACK);
				//tv.setGravity(Gravity.CENTER);
				tv.setTextSize((float)20.0);
				tv.setFocusable(true);
				tv.setBackgroundColor(Color.argb(80, 240, 240, 240));
				tv.setPadding(5, 1, 1, 1);
				//tv.setFreezesText(true);
				on.addView(tv);
				vid = tv.getId();
				
				LinearLayout vvl = new LinearLayout(Art.this);
				vvl.setLayoutParams(on.getLayoutParams());
				vvl.setBackgroundColor(Color.argb(200, 130, 130, 130));
				vvl.setMinimumHeight(1);
				on.addView(vvl);
			}
			
			TextView de = (TextView) on.getChildAt(0);
			String eo = de.getText().toString();
			
			String xtl[] = eo.split(":;'");
			String xtt = xtl[0];
			//xt1.setText(xtt);
			Log.i(G,"Handler rify " + eo);
			//if(xtl.length > 1 ){
			String pref = "";
			for(int xc = 1; xc < xtl.length; xc++){
				String type = xtl[xc];
				
				if( type.contains("nfig") && xc+1 < xtl.length){
					//easyUpdateText(tv.getId(), "Verifing");
					pref = xtl[++xc];
					//String lue = mReg.getString(pref, "");
					Log.i(G,"Handler tion for nfig " + pref);
					
				}else
				if( type.contains("rify") && xc+1 < xtl.length ){
					
					String rify = xtl[++xc];
					
					if( rify.contains("gin") ){
						//*/
						if( tmp.length() == 0 ){
							tmp = "tmp_" + System.currentTimeMillis();
							final int vid2 = vid;
							final String tmp2 = tmp;
							mRegedit.putLong(tmp+"_start", System.currentTimeMillis());mRegedit.commit();
							new Thread(){
								public void run(){
									String url = mReg.getString("webaddress", "");
									//String ge = getPage(url,vid2,"mRify 427");
									eGin(vid2, tmp2);
									mRegedit.putLong(tmp2 + "_when", SystemClock.uptimeMillis());
									//mRegedit.putString(tmp2, ge);
									mRegedit.commit();
								}
							}.start();
						}//*/
						long geT = mReg.getLong(tmp + "_when", 0);
						if( geT > 0 ){
							mRegedit.remove(tmp + "_when");
							mRegedit.remove(tmp + "_start");
							//mRegedit.remove(tmp);
							mRegedit.commit();
							tmp = "";
							
						}else{
							long s = mReg.getLong(tmp+"_start", 0);
							if(s > 0){
								long d = (System.currentTimeMillis() - s)/1000;easyUpdateText(vid, d + " seconds.", Color.DKGRAY);
								//TextView gb = (TextView) findViewById(vid);
								
								//gb.setText("Time pass " + d + " seconds.");
							}
						}
						//final int vid2 = vid;
						//final String tmp2 = tmp;
						//new Thread(){
							//public void run(){
								//eGin(vid2, tmp2);
							//}
						//}.start();
						
					}else
					if( rify.contains("tpage") && xc+1 < xtl.length){
						String gex = xtl[++xc];
						
						/*String ge = getPage(lue,vid,"mRify 320");
						
						if( ge.length() > 0 ){
						if( ge.contains(gex) ){
						}
						}
						//*/
						
						if( tmp.length() == 0 ){
							tmp = "tmp_" + System.currentTimeMillis();
							final String lue2 = lue;
							final int vid2 = vid;
							final String tmp2 = tmp;
							mRegedit.putLong(tmp+"_start", System.currentTimeMillis());mRegedit.commit();
							new Thread(){
								public void run(){
									mHttpClient = new DefaultHttpClient();
									//String url = mReg.getString("webaddress", "");String url = mReg.getString("webaddress", "");
						        	HttpGet httpGet = new HttpGet(lue2);
						        	//logoff.owa?canary=
						        	String httpStatus = safeHttpGet(G + ":mRify:1068", httpGet);
									//String ge = getPage(lue2,vid2,"mRify 427");mHttpPage = ge; 
									String[] tionList = rsePage(mHttpPage);
									mRegedit.putLong(tmp2 + "_when", SystemClock.uptimeMillis());
									mRegedit.putString(tmp2, mHttpPage);mRegedit.commit();
								}
							}.start();
						}
						long geT = mReg.getLong(tmp + "_when", 0);
						if( geT > 0 ){
							String ge = mReg.getString(tmp, "");
							if(ge.length() > 0 ){
								mRegedit.remove(tmp + "_when");
								mRegedit.remove(tmp + "_start");
								mRegedit.remove(tmp);
								mRegedit.commit();
								tmp = "";
								if( !ge.contains(gex) ){
									//mDialog.dismiss();
									easyUpdateText(vid, "Missing Marker");
								}
							}
						}else{
							long s = mReg.getLong(tmp+"_start", 0);
							if(s > 0){
								long d = (System.currentTimeMillis() - s)/1000;
								TextView gb = (TextView) findViewById(vid);
								
								gb.setText("Time pass " + d + " seconds.");
							}
						}
						
						
					}
					
					if( tmp.length() > 0 ){
						Message msg1 = new Message();
						Bundle bdl1 = new Bundle();
						bdl1.putInt("onid", onid);
						bdl1.putInt("vid", vid);
						bdl1.putString("lue", lue);
						bdl1.putString("tmp", tmp);
						bdl1.putBoolean("updatereg", updateReg);
						msg1.setData(bdl1);
						mRify.sendMessageDelayed(msg1,1000);
						return;
					}
					if( updateReg ){
						easyUpdateText(vid, "Verified and Saved", Color.GREEN);
						mRegedit.putBoolean(pref+"_verified", true);
						mRegedit.putString(pref, lue);
						mRegedit.commit();
					}else{
						easyUpdateText(vid, "Verified", Color.GREEN);
					}
					
				}
			}
			/*
			if( tmp.length() == 0 ){
			Thread t = new Thread(){
				public void run(){
					SystemClock.sleep(1000);
					mDialog.dismiss();
				}
			};
			t.start();
			}//*/
			
		}
	};
	private String mUrl = "";
	private String mHttpPage = "";
	private HttpResponse mHttpResponse;
	private HttpEntity mHttpEntity;
	private List<Cookie> mHttpCookie;
	   public String safeHttpPost(String who, HttpPost httpPost, List<NameValuePair> nvps) {
	    	Log.w(G,"safeHttpPost() 972 getURI("+httpPost.getURI()+") for " + who);
	        //HttpParams params = httpclient.getParams();
	        //params.setParameter("Cookies", "logondata=acc=1&lgn=DDDD0/uuuu; expires="+expiredate.toString());
	        //httpclient.set
	    	
	    	String responseCode = ""; mHttpPage = "";
	        try {
	        	Log.w(G,"safeHttpPost() 979 UrlEncodeFormValues for " + who);
				httpPost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
				Log.w(G,"safeHttpPost() 981 httpclient.execute() for " + who);
				mHttpClient.setRedirectHandler(new RedirectHandler(){

					public URI getLocationURI(HttpResponse arg0,
							HttpContext arg1) throws ProtocolException {
						// TODO Auto-generated method stub
						if( arg0.containsHeader("Location")){
						String url = arg0.getFirstHeader("Location").getValue();
						Log.w(G,"getLocationURI url("+url+") mUrl("+mUrl+") " + arg0.getStatusLine().getReasonPhrase() + ": " + arg1.toString());
						mUrl = url;
						URI uri = URI.create(url);
						return uri;
						}else{
							return null;
						}
					}

					public boolean isRedirectRequested(HttpResponse arg0,
							HttpContext arg1) {Log.w(G,"isRedirectRequested " + arg0.getStatusLine().getReasonPhrase() + ": " + arg1.toString() + " ");
							if( arg0.containsHeader("Location") ){
								String url = arg0.getFirstHeader("Location").getValue();
								Log.w(G,"isRedirectRequested url(" + url+") ");
								return true;
							}
						// TODO Auto-generated method stub
						return false;
					}					
				});

mUrl = httpPost.getURI().toString();
		        mHttpResponse = mHttpClient.execute(httpPost);

		        if( mHttpResponse != null ){
			        Log.w(G,"safeHttpPost() " + mHttpResponse.getStatusLine());
			        
			        Log.w(G,"safeHttpPost() response.getEntity()");
			        mHttpEntity = mHttpResponse.getEntity();
		
			        if (mHttpEntity != null) {
				        //byte[] bytes = ;
				        mHttpPage = new String(EntityUtils.toByteArray(mHttpEntity));
				        Log.w(G,"safeHttpPost() 993 Downloaded " + mHttpPage.length() + " bytes. for " + who);
				        
				        mHttpCookie = mHttpClient.getCookieStore().getCookies();
				        //
				        // Print Cookies
				        if ( !mHttpCookie.isEmpty() ) { for (int i = 0; i < mHttpCookie.size(); i++) { Log.w(G,"safeHttpPost() Cookie: " + mHttpCookie.get(i).toString()); } }
		
				        //
				        // Print Headers
				        Header[] h = mHttpResponse.getAllHeaders(); for( int i = 0; i < h.length; i++){ Log.w(G,"safeHttpPost() Header: " + h[i].getName() + ": " + h[i].getValue()); }
				        //mUrl = httpPost.getURI().toString();
				        Log.w(G,"safeHttpGet mUrl " + mUrl);
				        // Clear memory
				        mHttpEntity.consumeContent();
			        }
			        
			        // Get response code string
			        responseCode = mHttpResponse.getStatusLine().toString();
		        }
			} catch (UnsupportedEncodingException e) {
				Log.w(G,"safeHttpPost() 1012 UnsupportedEncodingException for " + who);
				e.printStackTrace();
				responseCode = "HTTPERRORTHROWN " + e.getLocalizedMessage();
			} catch (ClientProtocolException e) {
				Log.w(G,"safeHttpPost() 1015 ClientProtocolException for " + who);
				e.printStackTrace();
				responseCode = "HTTPERRORTHROWN " + e.getLocalizedMessage();
			} catch (IOException e) {
				Log.w(G,"safeHttpPost() 1018 IOException for " + who);
				e.printStackTrace();
				responseCode = "HTTPERRORTHROWN " + e.getLocalizedMessage();
			} catch (IllegalStateException e) {
				Log.w(G,"safeHttpPost() 1021 IllegalState Exception for " + who);
				e.printStackTrace();
			}

			return responseCode;
			
	    }
	    


		public String safeHttpGet(String who, HttpGet httpget) {
			
			Log.w(G,"safeHttpGet() 1033 getURI("+httpget.getURI()+") for " + who);
			if( httpget.getURI().toString() == "" ){
				Log.e(G,"safeHttpGet 1035 Blocked empty request for " + who);
				return "";
			}
			
			String responseCode = ""; mHttpPage = "";
			
			try {
				mHttpClient.setRedirectHandler(new RedirectHandler(){

					public URI getLocationURI(HttpResponse arg0,
							HttpContext arg1) throws ProtocolException {
						// TODO Auto-generated method stub
						if( arg0.containsHeader("Location")){
						String url = arg0.getFirstHeader("Location").getValue();
						Log.w(G,"getLocationURI url("+url+") mUrl("+mUrl+") " + arg0.getStatusLine().getReasonPhrase() + ": " + arg1.toString());
						mUrl = url;
						URI uri = URI.create(url);
						return uri;
						}else{
							return null;
						}
					}

					public boolean isRedirectRequested(HttpResponse arg0,
							HttpContext arg1) {Log.w(G,"isRedirectRequested " + arg0.getStatusLine().getReasonPhrase() + ": " + arg1.toString() + " ");
							if( arg0.containsHeader("Location") ){
								String url = arg0.getFirstHeader("Location").getValue();
								Log.w(G,"isRedirectRequested url(" + url+") ");
								return true;
							}
						// TODO Auto-generated method stub
						return false;
					}
					
				});

				mUrl = httpget.getURI().toString();
				Log.w(G,"safeHttpGet() 1044 httpclient.execute() mUrl("+mUrl+") for " + who);
				mHttpResponse = mHttpClient.execute(httpget);
				
				if( mHttpResponse != null ){
			        Log.w(G,"safeHttpGet() 1048 " + mHttpResponse.getStatusLine() + " " + " for " + who);
			        
			        Log.w(G,"safeHttpGet() 1050 response.getEntity() for " + who);
			        mHttpEntity = mHttpResponse.getEntity();
		
			        if (mHttpEntity != null) {
				        //byte[] bytes = ;
				        mHttpPage = new String(EntityUtils.toByteArray(mHttpEntity));
				        Log.w(G,"safeHttpGet() 1056 Downloaded " + mHttpPage.length() + " bytes. for " + who);
				        
				        mHttpCookie = mHttpClient.getCookieStore().getCookies();
				        //
				        // Print Cookies
				        //if ( !mHttpCookie.isEmpty() ) { for (int i = 0; i < mHttpCookie.size(); i++) { Log.w(TAG,"safeHttpGet() Cookie: " + mHttpCookie.get(i).toString()); } }
				        
				        //
				        // Print Headers
			        	//Header[] h = mHttpResponse.getAllHeaders(); for( int i = 0; i < h.length; i++){ Log.w(TAG,"safeHttpGet() Header: " + h[i].getName() + ": " + h[i].getValue()); }
				        //mUrl = httpget.getURI().toString();
				        Log.w(G,"safeHttpGet mUrl " + mUrl);
				        mHttpEntity.consumeContent();
					}
				}
				
		        responseCode = mHttpResponse.getStatusLine().toString();
				
			} catch (ClientProtocolException e) {
				Log.w(G,"safeHttpGet() 1121 ClientProtocolException for " + who);
				Log.w(G,"safeHttpGet() 1122 IO Exception Message " + e.getLocalizedMessage());
				e.printStackTrace();
				responseCode = "HTTPERRORTHROWN " + e.getLocalizedMessage();
			} catch (NullPointerException e) {
				Log.w(G,"safeHttpGet() 1126 NullPointer Exception for " + who);
				Log.w(G,"safeHttpGet() 1127 IO Exception Message " + e.getLocalizedMessage());
				e.printStackTrace();
			} catch (IOException e) {
				Log.w(G,"safeHttpGet() 1130 IO Exception for " + who);
				//if( e.getLocalizedMessage().contains("Host is unresolved") ){ SystemClock.sleep(1880); }
				Log.w(G,"safeHttpGet() 1132 IO Exception Message " + e.getLocalizedMessage());
				StackTraceElement[] err = e.getStackTrace();
				for(int i = 0; i < err.length; i++){
					Log.w(G,"safeHttpGet() 1135 IO Exception Message " + i + " class(" + err[i].getClassName() + ") file(" + err[i].getFileName() + ") line(" + err[i].getLineNumber() + ") method(" + err[i].getMethodName() + ")");
				}
				responseCode = "HTTPERRORTHROWN " + e.getLocalizedMessage();
			} catch (IllegalStateException e) {
				Log.w(G,"safeHttpGet() 1139 IllegalState Exception for " + who);
				Log.w(G,"safeHttpGet() 1140 IO Exception Message " + e.getLocalizedMessage());
				e.printStackTrace();
				//if( responseCode == "" ){
					//responseCode = "440"; //440 simulates a timeout condition and recreates the client.
				//}
			}
			
			return responseCode;
		}
	
	private long mEUT = 0;
	private void easyUpdateText(int vid, String xt){
		easyUpdateText(vid,xt,0);
	}
	private void easyUpdateText(final int vid, final String xt, final int lor){
		
					
				Message msg = new Message(); Bundle bdl = new Bundle();
				bdl.putInt("vid", vid);
				bdl.putString("xt", xt);
				bdl.putInt("color", lor);
				msg.setData(bdl);
				
				
				if( mEUT > System.currentTimeMillis() - 490 ){
					mEUT = mEUT+500;
					mUpdateText.sendMessageDelayed(msg,(mEUT - System.currentTimeMillis()));
					Log.i(G,"Update Text " + xt + " in " + (mEUT - System.currentTimeMillis()) + " ms.");
				}else{
					mEUT = System.currentTimeMillis();
					mUpdateText.sendMessageDelayed(msg,10);
					Log.i(G,"Update Text " + xt + " in 10 ms");
				}
			
		
	}
	private Handler mUpdateText = new Handler(){
		public void handleMessage(Message msg){
			Bundle bdl = msg.getData();
			int vid = bdl.getInt("vid");
			String xt = bdl.getString("xt");
			int lor = bdl.getInt("color");
			TextView v = (TextView) findViewById(vid);if(v == null){Log.e(G,"empty mUpdateText view vid("+vid+") xt("+xt+")");return;}
			if( lor > 0 ){ v.setTextColor(lor); }
			v.setText(xt);
			
		}
	};
	
	public class HTTPRequestHelper {
		private static final int POST_TYPE = 1;
		private static final int GET_TYPE = 2;
		private static final String CONTENT_TYPE = "Content-Type";
		public static final String MIME_FORM_ENCODED = "application/x-www-form-urlencoded";
		public static final String MIME_TEXT_PLAIN = "text/plain";
		
		private final ResponseHandler<String> responseHandler;
		
		public HTTPRequestHelper(ResponseHandler<String> responseHandler) {
			this.responseHandler = responseHandler;
		}
		
		public void performGet(String url, String user, String pass, final Map<String, String> additionalHeaders){
			performRequest(null, url, user, pass, additionalHeaders, null, HTTPRequestHelper.GET_TYPE );
		}
		
		public void performPost(String contentType, String url, String user, String pass, Map<String, String> additionalHeaders, Map<String, String> params){
			performRequest(contentType, url, user, pass, additionalHeaders, params, HTTPRequestHelper.POST_TYPE);
		}
		
		public void performPost(String url, String user, String pass, Map<String, String> additionalHeaders, Map<String, String> params){
			performRequest(HTTPRequestHelper.CONTENT_TYPE, url, user, pass, additionalHeaders, params, HTTPRequestHelper.POST_TYPE);
		}
		public void performRequest(String contentType, String url, String user, String pass, Map<String, String> headers, Map<String, String> params, int requestType	){
			
			DefaultHttpClient client = new DefaultHttpClient();
			
			if( (user != null) && (pass != null) ){
				client.getCredentialsProvider().setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(user, pass));
			}
			
			final Map<String, String> sendHeaders = new HashMap<String, String>();
			if( (headers != null) && (headers.size() > 0)){
				sendHeaders.putAll(headers);
			}
			if( requestType == HTTPRequestHelper.POST_TYPE){
				sendHeaders.put(HTTPRequestHelper.CONTENT_TYPE, contentType);
			}
			if( sendHeaders.size() > 0){
				client.addRequestInterceptor(new HttpRequestInterceptor(){
					public void process( HttpRequest request, HttpContext context) throws HttpException, IOException {
						for( String key: sendHeaders.keySet()){
							if(!request.containsHeader(key)){
								request.addHeader(key, sendHeaders.get(key));
							}
						}
					}

				});
			}
			
			if( requestType == HTTPRequestHelper.POST_TYPE){
				HttpPost method = new HttpPost(url);
				List<NameValuePair> nvps = null;
				if((params != null) && (params.size() > 0)){
					nvps = new ArrayList<NameValuePair>();
					for( String key : params.keySet() ){
						nvps.add(new BasicNameValuePair(key,params.get(key)));
					}
				}
				if( nvps != null){
					try{
						method.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
					}catch(UnsupportedEncodingException e){
						
					}
				}
				execute(client, method);
			}else
			if(requestType == HTTPRequestHelper.GET_TYPE){
				HttpGet method = new HttpGet(url);
				execute(client, method);
			}
			
		}
		
		private void execute( HttpClient client, HttpRequestBase method ){
			BasicHttpResponse errorResponse = new BasicHttpResponse(new ProtocolVersion("HTTP_ERROR", 1, 1), 500, "ERROR");
			try {
				client.execute(method, this.responseHandler);
			} catch (Exception e){
				errorResponse.setReasonPhrase(e.getMessage());
				try {
					this.responseHandler.handleResponse(errorResponse);
				}catch(Exception ex){
					
				}
			}
		}
		
		public ResponseHandler<String> getResponseHandlerInstance(final Handler handler){
			
			final ResponseHandler<String> responseHandler = new ResponseHandler<String>(){
				public String handleResponse(final HttpResponse response){
					Message message = handler.obtainMessage();
					Bundle bundle = new Bundle();
					StatusLine status = response.getStatusLine();
					HttpEntity entity = response.getEntity();
					String result = null;
					if( entity != null){
						try {
							result = new String(EntityUtils.toByteArray(entity));
							//result = StringUtils.inputStreamToString(entity.getContent());
							bundle.putString("RESPONSE", result);
							message.setData(bundle);
							handler.sendMessage(message);
						} catch (IOException e){
							bundle.putString("RESPONSE", "Error - " + e.getMessage());
							message.setData(bundle);
							handler.sendMessage(message);
							
						}
					}else{
						bundle.putString("RESPONSE", "Error - " + response.getStatusLine().getReasonPhrase());
						message.setData(bundle);
						handler.sendMessage(message);
					}
					return result;
				}
			};
			return responseHandler;
		}

	}

	public String getPage(String gourl, int vid, String who){
		
		//w(TAG,"getPage() get ConnectivityManager");
    	ConnectivityManager cnnm = (ConnectivityManager) Art.this.getSystemService(Art.this.CONNECTIVITY_SERVICE);
    	//w(TAG,"getPage() get NetworkInfo");
    	NetworkInfo ninfo = cnnm.getActiveNetworkInfo();
    	//w(TAG,"getPage() got NetworkInfo state("+ninfo.getState().ordinal()+") name("+ninfo.getState().name()+")");
    	//android.os.Process.getElapsedCpuTime()
    	
		String httpPage = "";
		//String gourl = baseurl;
		Socket socket = null;
		SSLSocket sslsocket = null;
		BufferedReader br = null;
		BufferedWriter bw = null;
		int loopcnt = 0;
		String hostname = "";
		try {
			while(gourl.length() > 0 ){
				
				//mPreferencesEditor.putLong("lastfeedactive", System.currentTimeMillis()).commit();
				loopcnt ++;
				if( loopcnt > 8 ){
					easyUpdateText(vid, "HTTP Redirect Failure");
					Log.e(G,"getPage() Looped 8 times, really?! this many forwards?");
					break;
				}
				boolean secure = gourl.contains("https:") ? true : false;
				hostname = gourl.replaceFirst(".*?://", "").replaceFirst("/.*", "");
				int port = secure ? 443 : 80;
				if( hostname.contains(":") ){
					String[] p = hostname.split(":");
					hostname = p[0];
					port = Integer.parseInt(p[1]);
				}
				String docpath = gourl.replaceFirst(".*?://", "").replaceFirst(hostname,"").replaceFirst(".*?/", "/");
				if( docpath.length() == 0 ){docpath = "/";}
				Log.w(G,"getPage() hostname("+hostname+") path("+docpath+") gourl("+gourl+")");
				gourl = "";
				
				if( !secure ){
					sslsocket = null;
					easyUpdateText(vid, "Creating Non-secure Connection");
					Log.w(G,"getPage() Connecting to hostname("+hostname+") port("+port+")");
					socket = new Socket(hostname,port);
					
					//socket = new SecureSocket();
					//SecureSocket s = null;
					
					if( socket.isConnected() ){
						Log.i(G,"getPage() Connecting to hostname("+hostname+") CONNECTED");
					}else{
						int loopcnt2 = 0;
						while( !socket.isConnected() ){
							//easyUpdateText(vid, "Connection Slowness " + (loopcnt2 + 1));
							Log.e(G,"getPage() Not connected to hostname("+hostname+")");
							loopcnt2++;
							if( loopcnt2 > 100 ){
								easyUpdateText(vid, "Connection Timeout");
								Log.e(G,"getPage() Not connected to hostname("+hostname+") TIMEOUT REACHED");
								break;
							}
							SystemClock.sleep(30);
						}
					}
					
					Log.w(G,"getPage() Creating Writable to hostname("+hostname+") port("+port+")");
					bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
					Log.w(G,"getPage() Creating Readable to hostname("+hostname+") port("+port+")");
					br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				}else{
					socket = null;
					easyUpdateText(vid, "Creating Secure Connection");
					Log.w(G,"getPage() Connecting Securely to hostname("+hostname+") port("+port+")");
					
					SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
					Log.w(G,"getPage() creating socket");
					sslsocket = (SSLSocket) factory.createSocket(hostname,443);
					Log.w(G,"getPage() creating socket session");
					SSLSession session = sslsocket.getSession();
					Log.w(G,"getPage() creating socket cert");
					X509Certificate cert;
					try { cert = (X509Certificate) session.getPeerCertificates()[0]; }
					catch(SSLPeerUnverifiedException e){
						easyUpdateText(vid, "Security Interruption " + e.getLocalizedMessage());
						Log.e(G,"getPage() Connecting to hostname("+hostname+") port(443) failed CERTIFICATE UNVERIFIED");
						break;
					}
					Log.w(G,"getPage() looking for connection");
					if( sslsocket.isConnected() ){
						Log.i(G,"getPage() Connecting to hostname("+hostname+") CONNECTED");
					}else{
						int loopcnt2 = 0;
						while( !sslsocket.isConnected() ){
							//easyUpdateText(vid, "Connection Slowness " + (loopcnt2 + 1));
							//Log.e(G,"getPage() Not connected to hostname("+hostname+")");
							loopcnt2++;
							if( loopcnt2 > 200 ){
								easyUpdateText(vid, "Connection Timeout");
								Log.e(G,"getPage() Not connected to hostname("+hostname+") TIMEOUT REACHED");
								break;
							}
							SystemClock.sleep(30);
						}
					}
											
					Log.w(G,"getPage() Creating Writable to hostname("+hostname+") port("+port+")");
					bw = new BufferedWriter(new OutputStreamWriter(sslsocket.getOutputStream()));
					Log.w(G,"getPage() Creating Readable to hostname("+hostname+") port("+port+")");
					br = new BufferedReader(new InputStreamReader(sslsocket.getInputStream()));
				}
				
				easyUpdateText(vid, "Handshake Success");
				//mRegedit.putLong("lastfeedactive", System.currentTimeMillis()).commit();
				Log.w(G,"getPage() Requesting document("+docpath+") hostname("+hostname+") port("+port+")");
				bw.write("GET " + docpath + " HTTP/1.0\r\n");
				bw.write("Host: " + hostname + "\r\n");
				bw.write("User-Agent: Android\r\n");
				//bw.write("Range: bytes=0-"+(1024 * DOWNLOAD_LIMIT)+"\r\n");
				//bw.write("TE: deflate\r\n");
				bw.write("\r\n");
				bw.flush();
				//http://www.w3.org/Protocols/rfc2616/rfc2616-sec5.html#sec5
				String status = "";
				String line = "";
				try {
					if( !secure ){
						if( br.ready() ){
							Log.w(G,"getPage() Ready to be read");
						}else{
							int loopcnt2 = 0;
							while( !br.ready() ){
								//easyUpdateText(vid, "Connection Non-responsive " + (loopcnt2 + 1));
								Log.e(G,"getPage() NOT Ready to be read");
								loopcnt2++;
								if( loopcnt2 > 200 ){
									easyUpdateText(vid, "Connection Read Timeout");
									Log.e(G,"getPage() NOT Ready to be read TIMEOUT REACHED WAITING");
									line = br.readLine();
									Log.e(G,"getPage() NOT Ready to be read TIMEOUT REACHED WAITING line("+line+")");
									break;
								}
								SystemClock.sleep(30);
							}
						}
					}else{
						// br.ready() doesn't work from the sslsocket source
					}
					int linecnt = 0;
					Log.w(G,"getPage() Reading reply");
					
					for(line = br.readLine(); line != null; line = br.readLine()){
						if( line.length() == 0 ){
							Log.w(G,"getPage() End of header Reached");
							break;
						}
						linecnt++;
						//Log.i(G,"getPage() received("+line+")");
						if( line.regionMatches(true, 0, "Location:", 0, 9) ){
							gourl = line.replaceFirst(".*?:", "").trim();
							Log.w(G,"getPage() FOUND FORWARD URL("+gourl+") ");
							easyUpdateText(vid, "Location Forwarded");
						}else
						if( line.regionMatches(true, 0, "Cookie:", 0, 8) ){
							String cookie = line.replaceFirst(".*?:", "").trim();
							Log.w(G,"getPage() FOUND COOKIE URL("+cookie+") ");
							//easyUpdateText(vid, "Location Forwarded");
						}else{
							Log.w(G,"getPage() HeaderLine " + line);
						}

					}
					if( gourl.length() > 0 ){ continue; }
					if( line == null ){
						Log.w(G,"getPage() End of read");
					}
					if( linecnt > 0 ){
						easyUpdateText(vid, "Target Acquired");
						//mPreferencesEditor.putLong("lastfeedactive", System.currentTimeMillis()).commit();
						//mPreferencesEditor.putLong("lowmemory", 0).commit();
					}
					if( line != null ){
						int zerocnt = 0;
						for(line = br.readLine(); line != null; line = br.readLine()){
							if( line.length() == 0 ){
								zerocnt++;
								if( zerocnt > 50 ){
									easyUpdateText(vid, "Communication blunder");
									Log.e(G,"getPage() host("+hostname+") 50 empty lines received, moving on.");
									break;
								}
								continue;
							}
							zerocnt = 0;
							linecnt++;
							//i(TAG,"getPage() host("+hostname+") line("+line+")");
							httpPage += line;
							//if( httpPage.length() > 1024 * DOWNLOAD_LIMIT ){
								//w(TAG,"getPage() downloaded "+DOWNLOAD_LIMIT+"K from the site, moving on.");
								//break;
							//}
						}
					}
					Log.w(G,"getPage() Downloaded("+httpPage.length()+" bytes)");
					easyUpdateText(vid, "Successful Acquisition");
					
					/*/
					
					if( br.ready() ){
						
					}
					while(br.ready()){
						line = br.readLine();
						if( line == null ){
							w(TAG,"getPage() End of read Reached");
							break;
						} else if( line.length() == 0 ){
							w(TAG,"getPage() End of header Reached");
							break;
						}
						i(TAG,"getPage() feed("+longname+") received("+line+")");
						if( line.regionMatches(true, 0, "Location:", 0, 9) ){
							gourl = line.replaceFirst(".*?:", "").trim();
							w(TAG,"getPage() feed("+longname+") FOUND FORWARD URL("+gourl+") ");
						}
					}
					
					
					
					mPreferencesEditor.putLong("lastfeedactive", System.currentTimeMillis()).commit();
					while(br.ready()){
						line = br.readLine();
						if( line == null ){
							w(TAG,"getPage() End of read Reached");
							break;
						} else if( line.length() == 0 ){
							w(TAG,"getPage() End of header Reached");
							break;
						}
					}
					//*/
				}catch (IOException e1) {
					String msg = null;
					msg = e1.getLocalizedMessage() != null ? e1.getLocalizedMessage() : e1.getMessage();
					if( msg == null ){
						msg = e1.getCause().getLocalizedMessage();
						if( msg == null ){ msg = ""; }
					}
					easyUpdateText(vid, "System Failure " + msg);
					Log.e(G,"getPage() IOException while reading from web server " + msg);
					e1.printStackTrace();
				}
				
				if( !secure ){
					socket.close();
				}else{
					sslsocket.close();
				}
			}
		} catch (UnknownHostException e1) {
			easyUpdateText(vid, "DNS Lookup Failure " + hostname);
			Log.e(G,"getPage() unknownHostException");
			e1.printStackTrace();
		} catch (IOException e1) {
			easyUpdateText(vid, "System Failure " + e1.getLocalizedMessage());
			Log.e(G,"getPage() IOException");
			e1.printStackTrace();
		}
		
		return httpPage;
	}
	public boolean onTouch(View v, MotionEvent event) {
		if( event.getAction() == MotionEvent.ACTION_DOWN ){
			v.setBackgroundColor(Color.argb(255, 60, 210, 255));
			
		//}else if( event.getAction() == MotionEvent.ACTION_UP ){
			//v.setBackgroundColor(Color.argb(150, 240, 240, 240));
		}else if( event.getAction() == MotionEvent.ACTION_CANCEL ){
			//v.setBackgroundColor(Color.argb(255, 240, 40, 40));
			v.setBackgroundColor(Color.argb(150, 200, 200, 200));
		}
		return false;
	}

	@Override
	protected void onPause() {
		Log.i(G,"onPause()");
		super.onPause();
	}

	@Override
	protected void onResume() {
		Log.i(G,"onResume()");
		super.onResume();
	}

	@Override
	protected void onStart() {
		Log.i(G,"onStart()");
		super.onStart();
	}
	
}
