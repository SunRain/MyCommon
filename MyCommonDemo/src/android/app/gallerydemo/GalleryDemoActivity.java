package android.app.gallerydemo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.nbtstatx.mydemos.R;
import android.os.Bundle;
import android.view.Gravity;

import java.util.List;

import wd.android.common.ui.view.GalleryFlow;
import wd.android.util.util.BitmapUtil;
import wd.android.util.util.ObjectUtil;

public class GalleryDemoActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.gallerydemo_main);

        Integer[] images = {R.drawable.gallery_flow_a,
                R.drawable.gallery_flow_b, R.drawable.gallery_flow_c};
        List<Bitmap> list = initImages(images);

        ImageAdapter adapter = new ImageAdapter(this, list);

        GalleryFlow galleryFlow = (GalleryFlow) findViewById(R.id.Gallery01);
        galleryFlow.setAdapter(adapter);
        galleryFlow.setSpacing(50);
        galleryFlow.setFadingEdgeLength(0);
        galleryFlow.setGravity(Gravity.CENTER_VERTICAL);
        galleryFlow.setMaxZoom(100);
    }

    public List<Bitmap> initImages(Integer[] images) {
        List<Bitmap> list = ObjectUtil.newArrayList();
        for (int imageId : images) {
            Bitmap srcBitmap = BitmapUtil.decodeBitmap(this, imageId);
            Bitmap bitmap = BitmapUtil.createBitmapWithReflection(srcBitmap,
                    srcBitmap.getHeight() / 2, 4);
            list.add(bitmap);

            // ImageView imageView = new ImageView(this);
            // imageView.setImageBitmap(bitmap);
            // imageView.setLayoutParams(new GalleryFlow.LayoutParams(110,
            // 184));
            // // imageView.setScaleType(ScaleType.MATRIX);
            // mImages[index++] = imageView;
        }
        return list;
    }
}