package com.n22.update.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Binder;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.TextUtils;
import android.widget.RemoteViews;

import com.n22.videorecordertest.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * download service
 *
 * @author spoon
 * @version 1.0.0
 * @created at 2016/9/4
 */
public class DownloadService extends Service {

	public static final String BUNDLE_KEY_DOWNLOAD_URL = "download_url";
	public static final String BUNDLE_KEY_TITLE = "title";
	private static final int NOTIFY_ID = 0;
	// 默认存放文件下载的路径
	public static String DEFAULT_SAVE_FILE_PATH = Environment.getExternalStorageDirectory() + File.separator
			+ "DownloadService" + File.separator + "download" + File.separator;

	private String saveApkPath = DEFAULT_SAVE_FILE_PATH;

	private Context mContext = this;
	private NotificationManager mNotificationManager;
	private ICallbackResult callback;
	private DownloadBinder binder;
	private Thread downLoadThread;
	private Notification mNotification;

	private int progress;
	private boolean canceled;
	private String downloadUrl;
	private String mTitle = "正在下载%s";
	private boolean serviceIsDestroy = false;

	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 0:
				// 下载完毕
				mNotificationManager.cancel(NOTIFY_ID);
				installApk();
				break;
			case 2:
				// 取消通知
				mNotificationManager.cancel(NOTIFY_ID);
				break;
			case 1:
				int rate = msg.arg1;
				if (rate < 100) {
					RemoteViews contentview = mNotification.contentView;
					contentview.setTextViewText(R.id.tv_download_state, mTitle + "(" + rate + "%" + ")");
					contentview.setProgressBar(R.id.pb_download, 100, rate, false);
				} else {
					// 下载完毕后变换通知形式
					mNotification.flags = Notification.FLAG_AUTO_CANCEL;
					mNotification.contentView = null;
					Intent intent = new Intent("com.spoon.xrclient");
					intent.addCategory("android.intent.category.DEFAULT");
					// 告知已完成
					intent.putExtra("completed", "yes");
					serviceIsDestroy = true;
					stopSelf();// 停掉服务自身
				}
				mNotificationManager.notify(NOTIFY_ID, mNotification);
				break;
			}
		}
	};

	@Override
	public IBinder onBind(Intent intent) {
		downloadUrl = intent.getStringExtra(BUNDLE_KEY_DOWNLOAD_URL);
		saveApkPath = saveApkPath + getSaveFileName(downloadUrl);
		mTitle = String.format(mTitle, intent.getStringExtra(BUNDLE_KEY_TITLE));
		return binder;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		binder = new DownloadBinder();
		mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		stopForeground(true);// 这个不确定是否有作用
	}

	private void startDownload() {
		canceled = false;
		downloadApk();
	}

	/**
	 * 创建通知
	 */
	private void setUpNotification() {
		int icon = R.mipmap.ic_launcher;
		CharSequence tickerText = "准备下载";
		long when = System.currentTimeMillis();
		mNotification = new Notification(icon, tickerText, when);

		// 放置在"正在运行"栏目中
		mNotification.flags = Notification.FLAG_ONGOING_EVENT;

		RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.download_notification_show);
		contentView.setTextViewText(R.id.tv_download_state, mTitle);
		// 指定个性化视图
		mNotification.contentView = contentView;

		Intent intent = new Intent(getApplication().getPackageName());
		intent.addCategory("android.intent.category.DEFAULT");
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

		// 指定内容意图
		mNotification.contentIntent = contentIntent;
		mNotificationManager.notify(NOTIFY_ID, mNotification);
	}

	private void downloadApk() {
		downLoadThread = new Thread(mdownApkRunnable);
		downLoadThread.start();
	}

	/**
	 * 安装apk
	 */
	private void installApk() {
		File apkfile = new File(saveApkPath);
		if (!apkfile.exists()) {
			return;
		}
		installAPK(mContext, apkfile);
	}

	private Runnable mdownApkRunnable = new Runnable() {
		@Override
		public void run() {
			File file = new File(DEFAULT_SAVE_FILE_PATH);
			if (!file.exists()) {
				file.mkdirs();
			}
			String apkFile = saveApkPath;
			File saveFile = new File(apkFile);
			try {
				downloadUpdateFile(downloadUrl, saveFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};

	public long downloadUpdateFile(String downloadUrl, File saveFile) throws Exception {
		int downloadCount = 0;
		int currentSize = 0;
		long totalSize = 0;
		int updateTotalSize = 0;

		HttpURLConnection httpConnection = null;
		InputStream is = null;
		FileOutputStream fos = null;

		try {
			URL url = new URL(downloadUrl);
			httpConnection = (HttpURLConnection) url.openConnection();
			httpConnection.setRequestProperty("User-Agent", "PacificHttpClient");
			if (currentSize > 0) {
				httpConnection.setRequestProperty("RANGE", "bytes=" + currentSize + "-");
			}
			httpConnection.setConnectTimeout(10000);
			httpConnection.setReadTimeout(20000);
			updateTotalSize = httpConnection.getContentLength();
			if (httpConnection.getResponseCode() == 404) {
				throw new Exception("fail!");
			}
			is = httpConnection.getInputStream();
			fos = new FileOutputStream(saveFile, false);
			byte buffer[] = new byte[1024];
			int readsize = 0;
			while ((readsize = is.read(buffer)) > 0) {
				fos.write(buffer, 0, readsize);
				totalSize += readsize;
				// 为了防止频繁的通知导致应用吃紧，百分比增加10才通知一次
				if ((downloadCount == 0) || (int) (totalSize * 100 / updateTotalSize) - 4 > downloadCount) {
					downloadCount += 4;
					// 更新进度
					Message msg = mHandler.obtainMessage();
					msg.what = 1;
					msg.arg1 = downloadCount;
					mHandler.sendMessage(msg);
					if (callback != null)
						callback.OnBackResult(progress);
				}
			}
			// 下载完成通知安装
			mHandler.sendEmptyMessage(0);
			// 下载完了，cancelled也要设置
			canceled = true;

		} finally {
			if (httpConnection != null) {
				httpConnection.disconnect();
			}
			if (is != null) {
				is.close();
			}
			if (fos != null) {
				fos.close();
			}
		}
		return totalSize;
	}

	private String getSaveFileName(String downloadUrl) {
		if (downloadUrl == null || TextUtils.isEmpty(downloadUrl)) {
			return "";
		}
		return downloadUrl.substring(downloadUrl.lastIndexOf("/"));
	}

	public class DownloadBinder extends Binder {
		public void start() {
			if (downLoadThread == null || !downLoadThread.isAlive()) {
				progress = 0;
				setUpNotification();
				new Thread() {
					public void run() {
						// 下载
						startDownload();
					}
				}.start();
			}
		}

		public void cancel() {
			canceled = true;
		}

		public int getProgress() {
			return progress;
		}

		public boolean isCanceled() {
			return canceled;
		}

		public boolean serviceIsDestroy() {
			return serviceIsDestroy;
		}

		public void cancelNotification() {
			mHandler.sendEmptyMessage(2);
		}

		public void addCallback(ICallbackResult callback) {
			DownloadService.this.callback = callback;
		}
	}

	public interface ICallbackResult {

		public void OnBackResult(Object s);
	}

	/**
	 * 安装APK
	 */
	private void installAPK(Context context, File file) {
		if (file == null || !file.exists())
			return;
		Intent intent = new Intent();
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
		context.startActivity(intent);
	}

	public String getSaveApkPath() {
		return saveApkPath;
	}

	public void setSaveApkPath(String saveApkPath) {
		this.saveApkPath = Environment.getExternalStorageDirectory() + File.separator + saveApkPath;
	}

	public static void openDownLoadService(Context context, String downurl, String tilte) {
		final ICallbackResult callback = new ICallbackResult() {

			@Override
			public void OnBackResult(Object s) {
			}
		};
		ServiceConnection conn = new ServiceConnection() {

			@Override
			public void onServiceDisconnected(ComponentName name) {
			}

			@Override
			public void onServiceConnected(ComponentName name, IBinder service) {
				DownloadBinder binder = (DownloadBinder) service;
				binder.addCallback(callback);
				binder.start();
			}
		};
		Intent intent = new Intent(context, DownloadService.class);
		intent.putExtra(DownloadService.BUNDLE_KEY_DOWNLOAD_URL, downurl);
		intent.putExtra(DownloadService.BUNDLE_KEY_TITLE, tilte);
		context.startService(intent);
		context.bindService(intent, conn, Context.BIND_AUTO_CREATE);
	}
}
