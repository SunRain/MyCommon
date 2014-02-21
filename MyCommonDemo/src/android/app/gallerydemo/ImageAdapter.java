package android.app.gallerydemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import java.util.List;

public class ImageAdapter extends BaseAdapter {

    int mGalleryItemBackground;
    private Context mContext;
    List<Bitmap> list;

    public ImageAdapter(Context c, List<Bitmap> list) {
        mContext = c;
        this.list = list;
    }

    public int getCount() {
        return Integer.MAX_VALUE;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == convertView) {
            convertView = new ImageView(mContext);
            convertView.setLayoutParams(new Gallery.LayoutParams(110, 184));
        }

        int p = position % list.size();
        ImageView imageView = (ImageView) convertView;
        imageView.setImageBitmap(list.get(p));
        imageView.setScaleType(ScaleType.FIT_XY);

        return imageView;
    }

}
