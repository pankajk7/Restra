package com.clairvoyant.Utils;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

public class AlertMessage {

	Context context;
	Toast toast;
	
	public AlertMessage(Context context) {
		this.context = context;
		toast = new Toast(context);
	}
	
	public void showTostMessage(String message){
		if (toast.getView() != null) {
			toast.setText(message);
		} else {
			toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
		}
		toast.show();
	}
	
	public void showTostMessage(int id){
		String message = context.getResources().getString(id);
		if (toast.getView() != null) {
			toast.setText(message);
		} else {
			toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
		}
		toast.show();
	}
}