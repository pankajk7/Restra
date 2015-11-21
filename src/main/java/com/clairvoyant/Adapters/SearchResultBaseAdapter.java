package com.clairvoyant.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.clairvoyant.Activities.RestaurantInfoActivity;
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

public class SearchResultBaseAdapter extends RecyclerView.Adapter<SearchResultBaseAdapter.ViewHolder>  {

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

	// Provide a reference to the views for each data item
	// Complex data items may need more than one view per item, and
	// you provide access to all the views for a data item in a view holder
	public static class ViewHolder extends RecyclerView.ViewHolder {
		// each data item is just a string in this case
		LinearLayout linearLayout;
		TextView nameTextView;
		TextView addressTextView;
		TextView areaTextView;
		TextView priceRangeTextView;
		ImageView imageView;
		public ViewHolder(View v) {
			super(v);
			this.linearLayout = (LinearLayout)v.findViewById(R.id.layout_cardview_search);
			this.nameTextView = (TextView)v.findViewById(R.id.searchResultItem_textview_name);
			this.addressTextView = (TextView) v
					.findViewById(R.id.searchResultItem_textview_address);
			this.priceRangeTextView = (TextView) v
					.findViewById(R.id.searchResultItem_textview_price);
			this.areaTextView = (TextView) v
					.findViewById(R.id.searchResultItem_textview_area);
			this.imageView = (ImageView)v.findViewById(R.id.imageView_searchItem);
		}
	}

//	@Override
//	public int getCount() {
//		if (arrayList != null) {
//			return arrayList.size();
//		}
//		return 0;
//	}
//
//	@Override
//	public Object getItem(int position) {
//		return arrayList.get(position);
//	}
//
//	@Override
//	public long getItemId(int position) {
//		return position;
//	}

	@Override
	public int getItemCount() {
		if (arrayList != null) {
			return arrayList.size();
		}
		return 0;
	}

	// Create new views (invoked by the layout manager)
	@Override
	public SearchResultBaseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
												   int viewType) {
		// create a new view
		View v = LayoutInflater.from(parent.getContext())
				.inflate(R.layout.search_item_layout, parent, false);
		// set the view's size, margins, paddings and layout parameters


		ViewHolder holder = new ViewHolder(v);

		return holder;
	}

	// Replace the contents of a view (invoked by the layout manager)
	@Override
	public void onBindViewHolder(ViewHolder holder, final int position) {
		// - get element from your dataset at this position
		// - replace the contents of the view with that element
		holder.nameTextView.setText(arrayList.get(position).getName());
		holder.addressTextView.setText(arrayList.get(position).getAddress());
		holder.priceRangeTextView.setText(arrayList.get(position).getCost());
		holder.areaTextView.setText(arrayList.get(position).getArea());
		holder.imageView.setImageResource(R.drawable.ic_launcher);
		getImages(arrayList.get(position).getPrimary_image(), holder.imageView);

		holder.linearLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, RestaurantInfoActivity.class);
                intent.putExtra("obj", arrayList.get(position));
				context.startActivity(intent);
			}
		});
	}

//	@Override
//	public View getView(int position, View convertView, ViewGroup parent) {
//
//		ViewHolder holder = new ViewHolder();
//		if (convertView == null) {
//			convertView = inflater.inflate(R.layout.search_item_layout,
//					parent, false);
//			holder.nameTextView = (TextView) convertView
//					.findViewById(R.id.searchResultItem_textview_name);
//			holder.addressTextView = (TextView) convertView
//					.findViewById(R.id.searchResultItem_textview_address);
//			holder.priceRangeTextView = (TextView) convertView
//					.findViewById(R.id.searchResultItem_textview_price);
//			holder.areaTextView = (TextView) convertView
//					.findViewById(R.id.searchResultItem_textview_area);
//
//			holder.imageView = (ImageView) convertView
//					.findViewById(R.id.imageView_searchItem);
//			convertView.setTag(holder);
//		} else {
//			holder = (ViewHolder) convertView.getTag();
//		}
//
//		holder.nameTextView.setText(arrayList.get(position).getName());
//		holder.addressTextView.setText(arrayList.get(position).getAddress());
//		holder.priceRangeTextView.setText(arrayList.get(position).getPhone());
//		holder.areaTextView.setText(arrayList.get(position).getArea());
//		holder.imageView.setImageResource(R.drawable.ic_launcher);
//		getImages(arrayList.get(position).getPrimary_image(),holder.imageView);
//
//
//		//getting only first image to show
////		getImages(arrayList.get(position).getImages()[0].getCloudinary_image_id(), holder.imageView);
//		return convertView;
//	}


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
							.placeholder(R.drawable.image_uploading)
							.error(R.drawable.image_not_found).into(imageView);
				}

			};
		}.execute();
	}

//	public static class Holder {
//		TextView nameTextView;
//		TextView addressTextView;
//		TextView areaTextView;
//		TextView priceRangeTextView;
//		ImageView imageView;
//	}

}