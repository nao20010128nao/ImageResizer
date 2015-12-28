package com.nao20010128nao.Image.Resizer;
import android.app.*;
import android.os.*;
import android.graphics.*;
import java.io.*;
import android.content.*;
import android.util.*;
import android.widget.*;
import android.net.*;
import java.net.*;

public class _PlayBanner extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		new Thread(){
			public void run(){
				OutputStream os=null;
				Bitmap resized=Bitmap.createBitmap(1024,500,Bitmap.Config.ARGB_8888);
				File f=new File(Environment.getExternalStorageDirectory(),"ImageResizer");
				if(!f.exists())f.mkdirs();
				f=new File(f,System.currentTimeMillis()+".png");
				try {
					Log.d("dbg", "save:" + resized.compress(Bitmap.CompressFormat.PNG, 100, os = new FileOutputStream(f)));
				} catch (Throwable e) {
					finish();
				}finally{
					try {
						os.flush();
						os.close();
					} catch (Throwable ez) {}
					resized.recycle();
				}
				finish();
			}
		}.start();
		TextView tv;
		setContentView(tv=new TextView(this));
		tv.setText("Please wait...");
	}
	public InputStream tryOpen(String uri) throws IOException {
		Log.d("dbg", "tryOpen:" + uri);
		if (uri.startsWith("content://")) {
			return getContentResolver().openInputStream(Uri.parse(uri));
		} else if (uri.startsWith("/")) {
			return new FileInputStream(uri);
		} else {
			return URI.create(uri).toURL().openConnection().getInputStream();
		}
	}

	@Override
	public void finish() {
		// TODO: Implement this method
		runOnUiThread(new Runnable(){
				public void run(){
					_PlayBanner.super.finish();
				}
			});
	}
}
