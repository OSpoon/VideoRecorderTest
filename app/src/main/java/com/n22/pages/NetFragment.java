package com.n22.pages;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.n22.adapter.NetPagerAdapter;
import com.n22.bean.RecordInfo;
import com.n22.videorecordertest.PolicyPreviewActivity;
import com.n22.videorecordertest.R;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NetFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NetFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NetFragment extends Fragment implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "NetFragment";
    private OnFragmentInteractionListener mListener;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView mRecyclerView;
    private NetPagerAdapter localPagerAdapter;

    int page = 0;
    int ONE_PAGE_SIZE = 10;

    private static final String ARG_PARAM = "param";

    private String mParam;

    public static NetFragment newInstance() {
        NetFragment fragment = new NetFragment();
        return fragment;
    }

    public static NetFragment newInstance(String param) {
        NetFragment fragment = new NetFragment();
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
        localPagerAdapter = new NetPagerAdapter(mRecyclerView, R.layout.fragment_item);
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
                if (view.getId() == R.id.iv_preview) {
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
}
