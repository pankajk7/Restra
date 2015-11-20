package com.clairvoyant.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.clairvoyant.Utils.BackgroundNetwork;
import com.clairvoyant.Utils.Constants;
import com.clairvoyant.entities.Image;
import com.clairvoyant.restra.R;
import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ImagePagerAdapter extends PagerAdapter {
 
    Context context;
    LayoutInflater layoutInflater;

    ArrayList<Image> arrayList;
 
    public ImagePagerAdapter(Context context, ArrayList<Image> arrayList) {
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.arrayList = arrayList;
    }
 
    @Override
    public int getCount() {
        if(arrayList != null){
            return arrayList.size();
        }
        return 0;
    }
 
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }
 
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = layoutInflater.inflate(R.layout.image_viewpager_layout, container, false);
 
        ImageView imageView = (ImageView) itemView.findViewById(R.id.viewPagerItem_image1);
        TextView textView = (TextView)itemView.findViewById(R.id.viewPagerItem_textview_page);

        getImages(arrayList.get(position).getImage_id(),imageView);
        textView.setText(position + 1 + "/" + arrayList.size());

        RadioGroup radioGroup = (RadioGroup) itemView.findViewById(R.id.radiogroup_imageViewPager);

//        for (int row = 0; row < getCount(); row++) {
            LinearLayout ll = new LinearLayout(context);
            ll.setOrientation(LinearLayout.HORIZONTAL);

        RadioGroup.LayoutParams rprms;

        RadioButton rdbtn[] = new RadioButton[getCount()];
        for (int i = 0; i < getCount(); i++) {
            rdbtn[i] = new RadioButton(context);
            rdbtn[i].setId((getCount() * 2) + i);
//                rdbtn.setEnabled(false);
//                ll.addView(rdbtn);
            rprms= new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);
//            radioGroup.addView(ll);
            radioGroup.addView(rdbtn[i], rprms);
            }

            rdbtn[position].setChecked(true);
//        }

        container.addView(itemView);

        return itemView;
    }
 
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

    private void getImages(final String id, final ImageView imageView) {

        new BackgroundNetwork(context) {
            String url = "";

            protected Void doInBackground(Void... params) {
                Map<String, String> config = new HashMap<String, String>();
                config.put("cloud_name", Constants.CLOUDINARY_NAME);
                config.put("api_key", Constants.CLOUDINARY_API_KEY);
                config.put("api_secret", Constants.CLOUDINARY_API_SECRET);

                try {
                    Cloudinary cloudinary = new Cloudinary(config);
//                    Transformation transformation = new Transformation();
//                    transformation.width(300);
//                    transformation.height(300);
//                    transformation.crop("fit");
                    url = cloudinary.url().generate(id);
                    // url = cloudinary.url()
                    // .generate(id);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            };

            protected void onPostExecute(Void result) {
                super.onPostExecute(result);
                if (!url.equalsIgnoreCase("") || url != null) {
                    Picasso.with(context).load(url)
                            .placeholder(R.drawable.image_uploading)
                            .error(R.drawable.image_not_found).into(imageView);
                }

            };
        }.execute();
    }
}