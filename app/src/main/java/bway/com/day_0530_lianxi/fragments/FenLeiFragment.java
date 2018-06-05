package bway.com.day_0530_lianxi.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import bway.com.day_0530_lianxi.MyListView;
import bway.com.day_0530_lianxi.R;
import bway.com.day_0530_lianxi.adapter.GoodsAdapter;
import bway.com.day_0530_lianxi.adapter.MyPageAdapter;
import bway.com.day_0530_lianxi.app.MyApp;
import bway.com.day_0530_lianxi.bean.GoodsBean;
import bway.com.day_0530_lianxi.bean.LunBoBeam;
import bway.com.day_0530_lianxi.https.HttpConfig;
import bway.com.day_0530_lianxi.https.HttpUtils;

public class FenLeiFragment extends Fragment {

    private static final String TAG = "fen*********";
    private View view;
    private ViewPager viewpager;
    private LinearLayout line;
    private List<ImageView> img=new ArrayList<>();
    private List<ImageView> point=new ArrayList<>();
    private List<GoodsBean.DataBean> list=new ArrayList<>();
    private MyPageAdapter mypage;
    private PullToRefreshScrollView pull;
    private int page=1;
    private MyListView listview;
    private  GoodsAdapter ga;

    private Handler handler=new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            viewpager.setCurrentItem(viewpager.getCurrentItem()+1);
            for(int i=0;i<point.size();i++){

                if(i==viewpager.getCurrentItem()%point.size()){
                    point.get(i).setSelected(true);
                }else{
                    point.get(i).setSelected(false);
                }
            }

            handler.sendEmptyMessageDelayed(0,2000);
        }
    };



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fenleifragment, null);

        //初始化页面
        initViews();

//创建适配器
         ga = new GoodsAdapter(getContext(), list);
          listview.setAdapter(ga);
        return view;
    }
//懒汉式加载
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if(isVisibleToUser){
            getLunBo();
            if(line!=null&&line.getChildCount()>0){
                line.removeAllViews();
                img.clear();
                point.clear();
            }
        }
    }

    //轮播图
    public void getLunBo(){

        HttpUtils httpUtils = HttpUtils.getHttpUtils();
        httpUtils.get(HttpConfig.LunBo);
        httpUtils.setHttpListener(new HttpUtils.HttpListener() {
            @Override
            public void getSuccess(String json) {

                Gson gson = new Gson();
                LunBoBeam lun = gson.fromJson(json, LunBoBeam.class);
                List<LunBoBeam.DataBean> data = lun.getData();
                Log.d(TAG, "getSuccess: "+data.size());
                for(int i=0;i<data.size();i++){
                    String icon = data.get(i).getIcon();
                    ImageView imageView = new ImageView(getActivity());
                    ImageLoader.getInstance().displayImage(icon,imageView, MyApp.getOptions());

                    img.add(imageView);
                    //添加小圆点

                    ImageView im = new ImageView(getActivity());
                    im.setImageResource(R.drawable.selector);
                    im.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    point.add(im);
                    im.setPadding(15,0,15,0);
                    line.addView(im,i);
                }

                //默认显示 第一个
                 point.get(0).setSelected(true);
                //创建适配器
                 mypage = new MyPageAdapter(img);
                viewpager.setAdapter(mypage);
                getDateGoods();
                //发送handrr
                handler.sendEmptyMessageDelayed(0,2000);
            }

            @Override
            public void getError(String error) {

            }
        });

    }



    //商品展示
    public void getDateGoods(){

        HttpUtils httpUtils = HttpUtils.getHttpUtils();

        httpUtils.get(HttpConfig.ShangPin);
        httpUtils.setHttpListener(new HttpUtils.HttpListener() {
            @Override
            public void getSuccess(String json) {

                Gson gson = new Gson();
                GoodsBean goods = gson.fromJson(json, GoodsBean.class);
               if(page==1){
                  list.clear();
               }

              list.addAll(goods.getData());
               //刷新适配器
                ga.notifyDataSetChanged();
                pull.onRefreshComplete();

            }

            @Override
            public void getError(String error) {

            }
        });
    }
    //初始化页面
    private void initViews() {

        viewpager = view.findViewById(R.id.fenlei_viewpager);
        line = view.findViewById(R.id.fenlei_line);
         listview=view.findViewById(R.id.shangpin_listview);
         pull=view.findViewById(R.id.shangpin_pull);

          pull.setMode(PullToRefreshBase.Mode.BOTH);
          pull.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
              @Override
              public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                  page=1;
                  getDateGoods();
              }

              @Override
              public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {

                  page++;
                  getDateGoods();
              }
          });
    }


}
