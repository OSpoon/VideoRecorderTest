package com.n22.pages;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.gson.Gson;
import com.n22.adapter.LocalPagerAdapter;
import com.n22.bean.Policy;
import com.n22.bean.RecordInfo;
import com.n22.uploading.OSSUtils;
import com.n22.util.BeanUtilImpl;
import com.n22.util.DialogUtils;
import com.n22.util.FileUtils;
import com.n22.util.OssHelper;
import com.n22.videorecordertest.MainActivity;
import com.n22.videorecordertest.PolicyPreviewActivity;
import com.n22.videorecordertest.R;
import com.n22.zxing.activity.CaptureActivity;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LocalFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LocalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LocalFragment extends Fragment implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "LocalFragment";
    public static final int REQUEST_CODE = 0x999;
    public static final int PREVIEW_CODE = 0x996;
    private OnFragmentInteractionListener mListener;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView mRecyclerView;
    private LocalPagerAdapter localPagerAdapter;

    int page = 0;
    int ONE_PAGE_SIZE = 10;

    private static final String ARG_PARAM = "param";

    private String mParam;

    public static LocalFragment newInstance() {
        LocalFragment fragment = new LocalFragment();
        return fragment;
    }

    public static LocalFragment newInstance(String param) {
        LocalFragment fragment = new LocalFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM, param);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam = getArguments().getString(ARG_PARAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the operation_layout for this fragment
        return inflater.inflate(R.layout.fragment_local, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.test_recycler_view);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        init();
        setlistener();
    }

    private void init() {
        localPagerAdapter = new LocalPagerAdapter(mRecyclerView, R.layout.fragment_item);
        localPagerAdapter.openLoadAnimation();
        localPagerAdapter.setEnableLoadMore(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(localPagerAdapter);
        localPagerAdapter.setOnLoadMoreListener(this);
        onRefresh();
    }

    private void setlistener() {
        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {

            }

            @Override
            public void onItemChildClick(final BaseQuickAdapter adapter, View view, int position) {
                final RecordInfo itemsBean = (RecordInfo) adapter.getData().get(position);
                if (view.getId() == R.id.iv_scan) {
                    CaptureActivity.start(LocalFragment.this, REQUEST_CODE, itemsBean.getId());
                } else if (view.getId() == R.id.iv_delete) {
                    DialogUtils.getDialog(getActivity(), "确认删除此影像件吗?").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            boolean deleteFile = FileUtils.deleteFile(itemsBean.getVideotapePath());
//                            if (deleteFile) {//文件删除成功
                                if (itemsBean.getPolicy() != null) {//保单信息不为空
                                    int deletePolicy = DataSupport.delete(Policy.class, itemsBean.getPolicy().getBaseObjId());//删除保单信息
                                    if (deletePolicy == 1) {//保单信息删除成功
                                        int deleteRecord = DataSupport.delete(RecordInfo.class, Long.parseLong(itemsBean.getId()));//删除影像件信息
                                        if (deleteRecord == 1) {//影像件信息删除成功
                                            adapter.getData().remove(itemsBean);//移除列表
                                            adapter.notifyDataSetChanged();//刷新数据
                                            Toast.makeText(getActivity(), "删除完成", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                } else {
                                    int deleteRecord = DataSupport.delete(RecordInfo.class, Long.parseLong(itemsBean.getId()));
                                    if (deleteRecord == 1) {
                                        adapter.getData().remove(itemsBean);//移除列表
                                        adapter.notifyDataSetChanged();//刷新数据
                                        Toast.makeText(getActivity(), "删除完成", Toast.LENGTH_SHORT).show();
                                    }
                                }
//                            }
                        }
                    }).create().show();
                } else if (view.getId() == R.id.iv_upload) {
                    PolicyPreviewActivity.start(getActivity(), itemsBean.getId());
                }
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                localPagerAdapter.setEnableLoadMore(false);//关闭加载更多
                page = 0;
                localPagerAdapter.setNewData(DataSupport.order("id desc").limit(ONE_PAGE_SIZE).find(RecordInfo.class));
                mSwipeRefreshLayout.setRefreshing(false);
                localPagerAdapter.setEnableLoadMore(true);//开启加载更多
            }
        }, 200);
    }

    @Override
    public void onLoadMoreRequested() {
        mRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setEnabled(false);//关闭下拉刷新
                List<RecordInfo> recordInfos = DataSupport.order("id desc").limit(ONE_PAGE_SIZE).offset((page + 1) * ONE_PAGE_SIZE).find(RecordInfo.class);
                if (recordInfos != null) {//存在数据
                    localPagerAdapter.addData(recordInfos);//成功获取数据
                    if (recordInfos.size() == ONE_PAGE_SIZE) {//数据满足
                        page++;
                        localPagerAdapter.loadMoreComplete();//加载完成
                    } else if (recordInfos.size() < ONE_PAGE_SIZE) {//本次数据总数小于指定每页数
                        localPagerAdapter.loadMoreEnd();//没有更多数据
                    }
                } else {
                    localPagerAdapter.loadMoreFail();//加载失败
                }
                mSwipeRefreshLayout.setEnabled(true);//开启下拉刷新
            }
        }, 200);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**
         * 处理二维码扫描结果
         */
        if (requestCode == LocalFragment.REQUEST_CODE && resultCode == getActivity().RESULT_OK) {
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
                    DialogUtils.getDialog(getActivity(), "二维码解析成功是否确认关联此影像件?").setPositiveButton("确定", new DialogInterface.OnClickListener() {
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
                                        PolicyPreviewActivity.start(LocalFragment.this, PREVIEW_CODE, recordInfo.getId());
//                                        onRefresh();
                                    } else {
                                        Toast.makeText(getActivity(), "保单信息关联失败", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }
                    }).create().show();
                } catch (Exception e) {
                    Toast.makeText(getActivity(), "请确认二维码是否正确:", Toast.LENGTH_SHORT).show();
                }
            }
        } else if (requestCode == LocalFragment.PREVIEW_CODE && resultCode == getActivity().RESULT_OK) {
            onRefresh();
        }
    }
}
