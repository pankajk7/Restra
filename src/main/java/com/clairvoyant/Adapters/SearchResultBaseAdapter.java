package com.clairvoyant.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.clairvoyant.Utils.BackgroundNetwork;
import com.clairvoyant.Utils.Constants;
import com.clairvoyant.entities.Restaurant;
import com.clairvoyant.restra.R;
import com.clairvoyant.restra.RestraApplication;
import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchResultBaseAdapter extends BaseAdapter {

	Context context;
	LayoutInflater inflater;

	ArrayList<Restaurant> arrayList;

	public SearchResultBaseAdapter(Context context,
								   ArrayList<Restaurant> arrayList) {
		this.context = context;
		this.arrayList = arrayList;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		if (arrayList != null) {
			return arrayList.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		return arrayList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder = new ViewHolder();
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.search_item_layout,
					parent, false);
			holder.nameTextView = (TextView) convertView
					.findViewById(R.id.searchResultItem_textview_name);
			holder.addressTextView = (TextView) convertView
					.findViewById(R.id.searchResultItem_textview_address);
			holder.priceRangeTextView = (TextView) convertView
					.findViewById(R.id.searchResultItem_textview_price);
			holder.areaTextView = (TextView) convertView
					.findViewById(R.id.searchResultItem_textview_area);

			holder.imageView = (ImageView) convertView
					.findViewById(R.id.imageView_searchItem);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.nameTextView.setText(arrayList.get(position).getName());
		holder.addressTextView.setText(arrayList.get(position).getAddress());
		holder.priceRangeTextView.setText(arrayList.get(position).getPhone());
		holder.areaTextView.setText(arrayList.get(position).getArea());
		holder.imageView.setImageResource(R.drawable.ic_launcher);

//		if(position==0){
			getImages("Breaking_Bad_title_card_nxixvd",holder.imageView);
//		}

		//getting only first image to show
//		getImages(arrayList.get(position).getImages()[0].getCloudinary_image_id(), holder.imageView);
		return convertView;
	}

	private void showImages(ImageView imageView){
//		RestraApplication.getInstance().getImageLoader().get();
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
					Transformation transformation = new Transformation();
					transformation.width(300);
					transformation.height(300);
					transformation.crop("fit");
					url = cloudinary.url().transformation(transformation)
							.generate(id);
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
							.placeholder(R.drawable.ic_launcher)
							.error(R.drawable.ic_launcher).into(imageView);
				}

			};
		}.execute();
	}

	public static class ViewHolder {
		TextView nameTextView;
		TextView addressTextView;
		TextView areaTextView;
		TextView priceRangeTextView;
		ImageView imageView;
	}

}