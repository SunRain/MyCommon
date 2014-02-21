package android.app.gridgallerydemo2;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery.LayoutParams;
import android.widget.ImageView;

/**
 * @author 空山不空
 *         图片适配器，用来加载图片
 */
public class ImageAdapter extends BaseAdapter {
    //图片适配器
    // 定义Context
    private int ownposition;


    public int getOwnposition() {
        return ownposition;
    }

    public void setOwnposition(int ownposition) {
        this.ownposition = ownposition;
    }

    private Context mContext;

    // 定义整型数组 即图片源

    // 声明 ImageAdapter
    public ImageAdapter(Context c) {
        mContext = c;
    }

    // 获取图片的个数
    public int getCount() {
        return ImageSource.mThumbIds.length;
    }

    // 获取图片在库中的位置
    public Object getItem(int position) {
        ownposition = position;
        return position;
    }

    // 获取图片ID
    public long getItemId(int position) {
        ownposition = position;
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {


        ownposition = position;
        ImageView imageview = new ImageView(mContext);
        imageview.setBackgroundColor(0xFF000000);
        imageview.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageview.setLayoutParams(new GalleryExt.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        imageview.setImageResource(ImageSource.mThumbIds[position]);
        // imageview.setAdjustViewBounds(true);
        // imageview.setLayoutParams(new GridView.LayoutParams(320, 480));
        // imageview.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        return imageview;
    }
}
