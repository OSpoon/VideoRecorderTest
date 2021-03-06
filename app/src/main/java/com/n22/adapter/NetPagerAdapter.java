package com.n22.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.n22.bean.NetPolicyVideoList;
import com.n22.bean.Policy;
import com.n22.bean.RecordInfo;
import com.n22.util.FileUtils;
import com.n22.util.encoder.Config;
import com.n22.videorecordertest.R;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by zhanxiaolin-n22 on 2017/7/22.
 */

public class NetPagerAdapter extends BaseQuickAdapter<NetPolicyVideoList.InsureImageUploadDetailBean.UpFileInfosBean, BaseViewHolder> {

    protected boolean isScrolling;

    public NetPagerAdapter(RecyclerView view, int layoutResId) {
        super(layoutResId);
        view.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                isScrolling = !(newState == RecyclerView.SCROLL_STATE_IDLE);
                if (!isScrolling) {
                    notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    protected void convert(BaseViewHolder helper, NetPolicyVideoList.InsureImageUploadDetailBean.UpFileInfosBean item) {
        TextView tv_info = helper.getView(R.id.tv_info);
        JCVideoPlayerStandard player = helper.getView(R.id.videoplayer);

        helper.getView(R.id.iv_scan).setVisibility(View.GONE);
        helper.getView(R.id.iv_delete).setVisibility(View.GONE);
        helper.getView(R.id.iv_upload).setVisibility(View.GONE);

        String info = "当前影像件\n编号:" + item.getFilename() + "\n";
        tv_info.setText(info);
        String video = "http://" + Config.HOST_IP + "/com.ifp.oms/imageQueryList/index/%7BmenuId%7DdownloadFile?filename=" + item.getFilename();
        player.setUp(item.getFilepath(), JCVideoPlayerStandard.SCREEN_LAYOUT_LIST, "N22");

        if (isScrolling) {
            JCVideoPlayer.releaseAllVideos();
        }
    }
}