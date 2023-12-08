package services;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.superhoodcleaning.R;

public class GridAdapter extends BaseAdapter {

    private final String[] staffName;
    private final String[] imgURL;
    Context context;
    View view;
    LayoutInflater inflater;

    public GridAdapter(String[] staffName, String[] imgURL, Context context) {
        this.staffName = staffName;
        this.imgURL = imgURL;
        this.context = context;
    }

    @Override
    public int getCount() {
        return staffName.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if (convertView == null) {
            view = new View(context);
            view = inflater.inflate(R.layout.grid_item_layout, parent, false);
            ImageView imageView = view.findViewById(R.id.ivAvatar);
            TextView textView = view.findViewById(R.id.tvStaffName);

            int radius = 50;
//            Glide.with(context).load(imgURL[position]).apply(RequestOptions.bitmapTransform(new RoundedCorners(radius))).into(imageView);
            Glide.with(context)
                    .load(imgURL[position])
                    .transform(new CircleCrop()) // This will make the image circular
                    .into(imageView);
            textView.setText(staffName[position]);
        }

//        Staff staff =
        return view;
    }
}
