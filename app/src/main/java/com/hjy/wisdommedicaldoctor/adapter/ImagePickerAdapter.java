package com.hjy.wisdommedicaldoctor.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hjy.wisdommedicaldoctor.R;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by QKun on 2018/7/5.
 */
public class ImagePickerAdapter extends RecyclerView.Adapter<ImagePickerAdapter.SelectedPicViewHolder> implements View.OnClickListener,View.OnLongClickListener{
    private Context mContext;
    private int maxImgCount;
    private boolean isAdded;   //是否额外添加了最后一个图片
    private List<ImageItem> mData;

    public ImagePickerAdapter(Context context, List<ImageItem> data, int maxImgCount) {
        this.mContext = context;
        this.maxImgCount = maxImgCount;
        setImages(data);
    }


    @Override
    public SelectedPicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_image_packer, parent, false);
        view.setOnClickListener(this);
        return new SelectedPicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SelectedPicViewHolder holder, int position) {
        holder.itemView.setTag(position);
        holder.bind(position);
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public List<ImageItem> getImages() {
        //由于图片未选满时，最后一张显示添加图片，因此这个方法返回真正的已选图片
        if (isAdded) return new ArrayList<>(mData.subList(0, mData.size() - 1));
        else return mData;
    }


    public void setImages(List<ImageItem> data) {
        mData = new ArrayList<>(data);
        if (getItemCount() < maxImgCount) {
            mData.add(new ImageItem());
            isAdded = true;
        } else {
            isAdded = false;
        }
        notifyDataSetChanged();
    }



    public class SelectedPicViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_img;

        public SelectedPicViewHolder(View itemView) {
            super(itemView);
            iv_img = itemView.findViewById(R.id.iv_img);
        }

        public void bind(int position) {
            //根据条目位置设置图片
            ImageItem item = mData.get(position);
            if (isAdded && position == getItemCount() - 1) {
                iv_img.setImageResource(R.drawable.selector_image_add);
            } else {
                ImagePicker.getInstance().getImageLoader().displayImage((Activity) mContext, item.path, iv_img, 0, 0);
            }
        }
    }


    private OnItemClickListener mItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    private OnItemLongClickLister mItemLongClickListener;

    public interface OnItemLongClickLister {
        void onItemLongClick(int position);
    }

    public void setItemLongClickListener(OnItemLongClickLister itemLongClickListener) {
        mItemLongClickListener = itemLongClickListener;
    }

    @Override
    public void onClick(View v) {
        if (mItemClickListener != null) {
            mItemClickListener.onItemClick((Integer) v.getTag());
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (mItemLongClickListener!=null){
            mItemLongClickListener.onItemLongClick((Integer) v.getTag());
        }
        return false;
    }


}
