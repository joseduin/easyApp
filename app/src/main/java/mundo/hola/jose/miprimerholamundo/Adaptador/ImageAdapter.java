package mundo.hola.jose.miprimerholamundo.Adaptador;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

import java.util.ArrayList;

import mundo.hola.jose.miprimerholamundo.modelo.Conversor;

/**
 * Created by Jose on 4/10/2016.
 */

public class ImageAdapter extends BaseAdapter {

    private ArrayList<Bitmap> imgs;
    private Context ctx;

    public ImageAdapter(Context c, ArrayList<Bitmap> img) {
        ctx = c;
        imgs = img;
    }

    @Override
    public int getCount() {
        return imgs.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView iView = new ImageView(ctx);
        iView.setImageBitmap(imgs.get(position));
        iView.setScaleType(ImageView.ScaleType.FIT_XY);
        iView.setLayoutParams(new Gallery.LayoutParams(parent.getHeight(), parent.getHeight()));    // Dimenciones del alto del padre -> Galeria

        return iView;
    }
}
