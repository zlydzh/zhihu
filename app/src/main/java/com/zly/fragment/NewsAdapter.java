package com.zly.fragment;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.zly.Util.Latest;
import com.zly.activity.R;
import java.util.List;

public class NewsAdapter extends BaseAdapter {
    private Context mContext;
    List<Latest.StoriesEntity> mList;

    public NewsAdapter(Context conext, List<Latest.StoriesEntity> list) {
        mContext = conext;
        mList = list;
    }

    public void addList(List<Latest.StoriesEntity> items) {
        this.mList.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (null == convertView) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.news_item_list, parent, false);
            viewHolder.tvTitle = (TextView)convertView.findViewById(R.id.tv_item);
            viewHolder.ivTitle = (ImageView)convertView.findViewById(R.id.iv_item);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Latest.StoriesEntity entity = mList.get(position);
        if (entity != null) {
            viewHolder.tvTitle.setText(entity.getTitle());
        Glide.with(mContext)
                .load(entity.getImages().get(0))
                .into(viewHolder.ivTitle);
        }
        return convertView;
    }

    public static class ViewHolder {
        public TextView tvTitle;
        public ImageView ivTitle;
    }
}
