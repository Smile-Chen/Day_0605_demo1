package bway.com.day_0530_lianxi.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import bway.com.day_0530_lianxi.R;
import bway.com.day_0530_lianxi.app.MyApp;
import bway.com.day_0530_lianxi.bean.NewsBean;

public class MyBase extends BaseAdapter {

    private Context context;
    private List<NewsBean.DataBean> list;

    public MyBase(Context context, List<NewsBean.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        int type = getItemViewType(position);
        switch (type){

            case 0:
                ViewHolderLeft left;
                if(convertView==null){

                    convertView=View.inflate(context, R.layout.fen_base01,null);
                     left = new ViewHolderLeft();
                    left.img=convertView.findViewById(R.id.f1_img);
                    left.text=convertView.findViewById(R.id.f1_text);

                    convertView.setTag(left);
                }else{
                    left=(ViewHolderLeft) convertView.getTag();
                }

                String pic = list.get(position).getProfile_image();
                ImageLoader.getInstance().displayImage(pic,left.img, MyApp.getOptions());

                left.text.setText(list.get(position).getText());
                break;

           case 1:
               ViewHolderRight right;
               if(convertView==null){

                   convertView=View.inflate(context,R.layout.fen_base02,null);
                    right = new ViewHolderRight();
                   right.img=convertView.findViewById(R.id.f2_img);
                   right.text=convertView.findViewById(R.id.f2_text);
                   convertView.setTag(right);
               }else{
                   right=(ViewHolderRight) convertView.getTag();
               }


               String p = list.get(position).getProfile_image();
               ImageLoader.getInstance().displayImage(p,right.img,MyApp.getOptions());
               right.text.setText(list.get(position).getText());
               break;



        }
        return convertView;
    }

    @Override
    public int getItemViewType(int position) {

        String s = list.get(position).getType();
        position=position%2;
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }



    class ViewHolderLeft{
        ImageView img;
        TextView text;
    }


    class ViewHolderRight{
        ImageView img;
        TextView text;
    }

}
