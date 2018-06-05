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
import bway.com.day_0530_lianxi.bean.GoodsBean;

public class GoodsAdapter extends BaseAdapter {

    private Context context;
    private List<GoodsBean.DataBean> list;

    public GoodsAdapter(Context context, List<GoodsBean.DataBean> list) {
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
        ViewHolder holder;
        if(convertView==null){

            convertView=View.inflate(context, R.layout.goods_base,null);
             holder = new ViewHolder();
             holder.img=convertView.findViewById(R.id.goods_img);
            holder.tetx=convertView.findViewById(R.id.goods_text);
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder) convertView.getTag();
        }

        String pic = list.get(position).getBook_cover();
        ImageLoader.getInstance().displayImage(pic,holder.img, MyApp.getOptions());
        holder.tetx.setText(list.get(position).getBookname());
        return convertView;
    }


    class ViewHolder{

       ImageView img;
       TextView tetx;
    }
}
