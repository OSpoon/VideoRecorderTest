package com.n22.videorecordertest;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialcamera.MaterialCamera;
import com.google.gson.Gson;
import com.n22.bean.Policy;
import com.n22.bean.RecordInfo;
import com.n22.util.DateUtil;
import com.n22.util.DialogUtils;
import com.n22.util.ImageUtils;
import com.n22.util.NetWorkUtils;
import com.n22.zxing.activity.CaptureActivity;
import com.zsmarter.doubleinputsdk.bean.DoubleInoutSDKKey;
import com.zsmarter.doubleinputsdk.bean.DoubleInput;
import com.zsmarter.doubleinputsdk.bean.VideoRecorderOptions;
import com.zsmarter.doubleinputsdk.bean.WaterMarkOption;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private static final int CAMERA_RQ = 0x998;
    private static final int REQUEST_CODE = 0x997;
    private static final long ERROR = -1;
    //第三方双录sdk
//    private String appid = "RDkzMDk0QzEtQUFFNC00MzY5LTg5MDMtQzhBRDBFNTM2MDlE";//测试用appid
//    private String appkey = "MEQyNTNBMEQtMDlCMi00MTJFLUI0RDctREY4QjgzQUE0QjY1";//测试用appkey
//    private String accessKey = "QkJERDUxMDQtQTg4NC00QzIwLUIxOEQtNUI5NUVDRENGMTc3";//测试用accessKey

    private String appid = "RTI4ODM3NjgtREZGOC00OENBLTg0QUEtRjk5RTE3N0QwMUE2";//测试用appid
    private String appkey = "MURBOENDMDAtMTM4NS00RjZGLUFFNkUtODVFN0NBMkE3OEU4";//测试用appkey
    private String accessKey = "QzVCRTU5Q0EtMTIwMC00QzU5LUE4RjQtN0RGQjFBQTlDRTA1";//测试用accessKey

    private DoubleInoutSDKKey sdkKey;
    private DoubleInput doubileInput;
    private String videopath;
    private String picturepath;
    private String audiopath;

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initMaterialCamera();
        initDoubleInoutSDK();
    }

    private void initDoubleInoutSDK() {
        sdkKey = new DoubleInoutSDKKey(appid, appkey, accessKey);
        doubileInput = new DoubleInput(this);
    }

    private void initView() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        findViewById(R.id.title_center).setVisibility(View.VISIBLE);
        findViewById(R.id.list_layout).setOnClickListener(this);
        findViewById(R.id.new_layout).setOnClickListener(this);
        findViewById(R.id.new_layout2).setOnClickListener(this);
        findViewById(R.id.new_layout3).setOnClickListener(this);
        findViewById(R.id.new_layout4).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.list_layout:
                startActivity(new Intent(this, TabPageActivity.class));
                break;
            case R.id.new_layout:
                isPass(new OnPassListener() {
                    @Override
                    public void onPass() {
                        materialCamera.start(CAMERA_RQ); // Starts the camera activity, the result will be sent back to the current Activitys
                    }
                });
                break;
            case R.id.new_layout2:
                isPass(new OnPassListener() {
                    @Override
                    public void onPass() {
                        recordVideo();
                    }
                });
                break;
            case R.id.new_layout3:
                boolean audio = startAudio();
                if (audio) {
                    initProgress(MainActivity.this);
                    showPressDialog();
                }
                break;
            case R.id.new_layout4:
                startWater();
                break;
        }
    }

    MaterialCamera materialCamera;

    private void initMaterialCamera() {
        materialCamera = new MaterialCamera(this)                               // Constructor takes an Activity
                .allowRetry(true)                                  // 回放时是否可见“重试”
                .autoSubmit(false)                                 // 是否允许用户在录制后播放视频？。这会影响到下一节中讨论的其他事情。
                .saveDir(Environment.getExternalStorageDirectory().getPath() + "/VideoRecorderTest/")                               // 文件夹录制的视频被保存到
                .primaryColorAttr(R.attr.colorPrimary)             // 用于摄像机的主题颜色，默认为colorprimary构造函数中的活动
                .showPortraitWarning(true)                         // 如果用户按纵向定位记录，是否显示警告
                .defaultToFrontFacing(false)                       // 相机是否会首先显示前置摄像头
                .retryExits(false)                                 // 如果为真，则回放屏幕中的“重试”按钮将退出摄像机，而不是回到记录器。
                .restartTimerOnRetry(false)                        // 如果为真，则当用户在回放中点击“重试”时，倒计时计时器将重置为0。
                .continueTimerInPlayback(false)                    // 如果是真的，倒计时计时器将在回放期间继续下去，而不是暂停。
                .videoEncodingBitRate(1024000)                     // 设置视频录制的自定义比特率。
                .audioEncodingBitRate(50000)                       // 设置音频记录的自定义比特率。
                .videoFrameRate(30)                                // 设置用于视频记录的自定义帧速率（fps）。
                .qualityProfile(MaterialCamera.QUALITY_HIGH)       // 设置质量配置文件，手动设置比特率或帧速率与其他设置将覆盖个人质量配置文件设置。
                .videoPreferredHeight(720)                         // 为录制的视频输出设置一个首选高度。
                .videoPreferredAspect(4f / 3f)                     // 为录制的视频输出设置一个优先的长宽比。
                .maxAllowedFileSize(1024 * 1024 * 5)               // 设置最大文件大小为5，记录文件是否达到这个值停止。记住，FAT文件系统有4GB文件大小限制。
                .iconRecord(R.drawable.mcam_action_capture)        // 设置用于开始录制的按钮的自定义图标。
                .iconStop(R.drawable.mcam_action_stop)             // 设置用于停止录制的按钮的自定义图标。
                .iconFrontCamera(R.drawable.mcam_camera_front)     // 设置用于切换到前置摄像机的按钮的自定义图标。
                .iconRearCamera(R.drawable.mcam_camera_rear)       // 设置用于切换到后置摄像机的按钮的自定义图标。
                .iconPlay(R.drawable.evp_action_play)              // 设置用于开始播放的自定义图标。
                .iconPause(R.drawable.evp_action_pause)            // 设置用于暂停播放的自定义图标。
                .iconRestart(R.drawable.evp_action_restart)        // 设置用于重新启动播放的自定义图标。
                .labelRetry(R.string.mcam_retry)                   // Sets a custom button label for the button used to retry recording, when available
                .labelConfirm(R.string.mcam_use_video)             // Sets a custom button label for the button used to confirm/submit a recording
//                .autoRecordWithDelaySec(5)                         // 摄像机将在5秒倒数后自动开始录制。这将禁用前端和背面摄像机之间的切换。
//                .autoRecordWithDelayMs(5000)                       // 与上面一样，用毫秒表示，而不是秒。
                .audioDisabled(false);                             // 设置为真实录制视频没有任何音频。
    }

    public boolean startAudio() {
        if (NetWorkUtils.isConnected()) {
            String yyyy_mm_dd_hh_mm_ss = DateUtil.getCurrentTime("yyyy_MM_dd_HH_mm_ss");
            audiopath = "VideoRecorderTest/audio/" + "audio_" + yyyy_mm_dd_hh_mm_ss;
            doubileInput.startAudio("VideoRecorderTest/audio", "audio_" + yyyy_mm_dd_hh_mm_ss, sdkKey);
            return true;
        }
        return false;
    }

    public void stopAudio() {
        doubileInput.stopAudio();
    }

    public void recordVideo() {
        if (NetWorkUtils.isConnected()) {
            videopath = "VideoRecorderTest/video/" + "video_" + DateUtil.getCurrentTime("yyyy_MM_dd_HH_mm_ss");
            VideoRecorderOptions options = new VideoRecorderOptions(videopath);
            options.setVideoMaxTime(60);//录制最大时长
            options.setmWidth(1280);
            options.setmHeight(720);
//            options.setFrame(18);
            options.setFrameRate(30);
            options.setVideoEncodingBitRate(1024);
            options.setAudioEncodingBitRate(256000);
            options.setAudioSampleRate(32000);
            options.setWaterPaddingWidth(60);
            options.setWaterPaddingHeight(60);
            options.setDistinguishability(720);
            options.setHaveWaterMark(true);
            Drawable drawable = getResources().getDrawable(R.mipmap.icon_watermark);
            options.setWaterMarkPicture(ImageUtils.drawable2Bitmap(drawable));

            doubileInput.recordVideo(options, sdkKey);
        }
    }

    public void startWater() {
        String yyyy_mm_dd_hh_mm_ss = DateUtil.getCurrentTime("yyyy_MM_dd_HH_mm_ss");
        picturepath = "VideoRecorderTest/picture/" + "picture_" + yyyy_mm_dd_hh_mm_ss;
        WaterMarkOption option = new WaterMarkOption("VideoRecorderTest/picture", "picture_" + yyyy_mm_dd_hh_mm_ss);
        option.setAlpha(255);
        option.setType(WaterMarkOption.TYPE_MARK_STRING);//设置水印类型
        option.setPosition(WaterMarkOption.POSITION_RIGHT_BOTTOM);//设置水印位置
        option.setStringMarkSize(40);//设置水印字体大小
        option.setPenColor("#4cd964");//设置水印颜色
        doubileInput.stakeWarterMarkPicture(option, sdkKey);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //图片添加水印
        if (requestCode == DoubleInput.DOUBLEINPUT_WARTERMARKER_CAMERA_START) {
            //拍照完成回调
            doubileInput.waterMarkActivityForResult();
            picturepath = Environment.getExternalStorageDirectory().getPath() + "/" + picturepath + ".png";
            DialogUtils.getDialog(MainActivity.this, picturepath).create().show();
        } else
            //sdk视频录制完成
            if (resultCode == DoubleInput.DOUBLEINPUT_VIDEO_SUCCESS) {
                //视频完成回调
                final RecordInfo recordInfo = new RecordInfo();
                recordInfo.setAuthor("abc");
                long millis = System.currentTimeMillis();
                recordInfo.setUpdateTime(new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(new Date(millis)));
                recordInfo.setUpdate(millis);
                recordInfo.setVideotapePath(Environment.getExternalStorageDirectory().getPath() + "/" + videopath + ".mp4");
                videopath = null;
                recordInfo.save();
                DialogUtils.getDialog(this, "影像件录制完成,编号:" + recordInfo.getId() + "\n是否开始关联保单信息?\n取消后可在任务列表查看并操作").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        CaptureActivity.start(MainActivity.this, REQUEST_CODE, recordInfo.getId());
                    }
                }).create().show();
            } else
                //自定义相机录制结束
                // Received recording or error from MaterialCamera
                if (requestCode == CAMERA_RQ) {
                    if (resultCode == RESULT_OK) {
                        final RecordInfo recordInfo = new RecordInfo();
                        recordInfo.setAuthor("abc");
                        long millis = System.currentTimeMillis();
                        recordInfo.setUpdateTime(new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(new Date(millis)));
                        recordInfo.setUpdate(millis);
                        recordInfo.setVideotapePath(data.getData().getPath());
                        recordInfo.save();
                        final File file = new File(data.getData().getPath());
                        DialogUtils.getDialog(this, "影像件录制完成,编号:" + recordInfo.getId() + "\n是否开始关联保单信息?\n取消后可在任务列表查看并操作").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                CaptureActivity.start(MainActivity.this, REQUEST_CODE, recordInfo.getId());
                            }
                        }).create().show();
                    } else if (data != null) {
                        Exception e = (Exception) data.getSerializableExtra(MaterialCamera.ERROR_EXTRA);
                        if (e != null) {
                            e.printStackTrace();
                            Toast.makeText(this, "Error: " + e.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                } else
                /**处理二维码扫描结果*/
                    if (requestCode == MainActivity.REQUEST_CODE && resultCode == RESULT_OK) {
                        //处理扫描结果（在界面上显示）
                        if (null != data) {
                            Bundle bundle = data.getExtras();
                            if (bundle == null) {
                                return;
                            }
                            try {
                                String msg = bundle.getString(CaptureActivity.SCAN_MSG);
                                final String local_id = bundle.getString(CaptureActivity.LOCAL_ID);
                                final Policy policy = new Gson().fromJson(msg, Policy.class);
                                DialogUtils.getDialog(MainActivity.this, "二维码解析成功\n是否确认关联此影像件?").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (!TextUtils.isEmpty(local_id)) {
                                            boolean save = policy.save();
                                            if (save) {
                                                RecordInfo recordInfo = DataSupport.find(RecordInfo.class, Long.parseLong(local_id));
                                                recordInfo.setPolicy(policy);
                                                save = recordInfo.save();
                                                if (save) {
                                                    //数据库ID
                                                    PolicyPreviewActivity.start(MainActivity.this, recordInfo.getId());
                                                    Toast.makeText(MainActivity.this, "保单信息关联成功", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(MainActivity.this, "保单信息关联失败", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        }
                                    }
                                }).create().show();
                            } catch (Exception e) {
                                Toast.makeText(MainActivity.this, "请确认二维码是否正确:", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
    }

    /**
     * 初始化进度条
     */
    public void initProgress(Context aContext) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(aContext);
        }
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setTitle("温馨提示");
        mProgressDialog.setMessage("正在录音,如需结束请点击关闭...");
        mProgressDialog.setButton(DialogInterface.BUTTON_POSITIVE, "关闭", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                stopAudio();
                audiopath = Environment.getExternalStorageDirectory().getPath() + "/" + audiopath + ".mp3";
                DialogUtils.getDialog(MainActivity.this, audiopath).create().show();
            }
        });
    }

    public void showPressDialog() {
        if (mProgressDialog == null) {
            return;
        }
        mProgressDialog.show();
    }


    public void dismissProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    private void isPass(OnPassListener onPassListener) {
        long availableSize = getAvailableExternalMemorySize();
        if (availableSize <= 1024L * 1024 * 1024 * 1) {
            showWarn();
        } else {
            if (onPassListener != null)
                onPassListener.onPass();
        }
    }

    private void showWarn() {
        AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setMessage("您的手机存储空间小于1G，请清理存储后重试");
        dialog.setButton(Dialog.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.show();
    }

    /**
     * 获取SDCARD剩余存储空间
     *
     * @return
     */
    public static long getAvailableExternalMemorySize() {
        if (externalMemoryAvailable()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long availableBlocks = stat.getAvailableBlocks();
            return availableBlocks * blockSize;
        } else {
            return ERROR;
        }
    }

    /**
     * SDCARD是否存
     */
    public static boolean externalMemoryAvailable() {
        return android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED);
    }

    interface OnPassListener {
        void onPass();
    }
}

