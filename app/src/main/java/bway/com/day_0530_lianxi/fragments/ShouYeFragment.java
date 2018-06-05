package bway.com.day_0530_lianxi.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

import bway.com.day_0530_lianxi.R;
import bway.com.day_0530_lianxi.adapter.MyBase;
import bway.com.day_0530_lianxi.bean.NewsBean;
import bway.com.day_0530_lianxi.https.HttpConfig;
import bway.com.day_0530_lianxi.https.HttpUtils;

public class ShouYeFragment extends Fragment {

    private View view;
    private PullToRefreshListView listview;
    private List<NewsBean.DataBean> list=new ArrayList<>();
    private MyBase myBase;
    private int page=1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.shouyefragment, null);

        //初始化页面
        initViews();
//创建适配器
        myBase = new MyBase(getContext(), list);
        listview.setAdapter(myBase);

        return view;
    }

 //懒汉式加载


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if(isVisibleToUser){
            getDataNet();
        }
    }

    //加载数据
    public void getDataNet(){

        HttpUtils httpUtils = HttpUtils.getHttpUtils();

        httpUtils.get(HttpConfig.URL1);

        httpUtils.setHttpListener(new HttpUtils.HttpListener() {
            @Override
            public void getSuccess(String json) {

                Gson gson = new Gson();
                NewsBean newsBean = gson.fromJson(json, NewsBean.class);

               if(page==1){
                   list.clear();
               }

                list.addAll(newsBean.getData());
               myBase.notifyDataSetChanged();
               listview.onRefreshComplete();

            }

            @Override
            public void getError(String error) {

            }
        });
    }



    //初始化页面
    private void initViews() {

        listview = view.findViewById(R.id.fen_listview);
        listview.setMode(PullToRefreshBase.Mode.BOTH);
        listview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page=1;
                getDataNet();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

                page++;
                getDataNet();
            }
        });
    }


}
