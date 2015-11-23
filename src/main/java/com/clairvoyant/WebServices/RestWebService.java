package com.clairvoyant.WebServices;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.clairvoyant.Utils.Constants;
import com.clairvoyant.restra.R;
import com.clairvoyant.restra.RestraApplication;

import java.util.HashMap;
import java.util.Map;

public class RestWebService {

    Context context;
    ProgressDialog transparentProgressDialog;

    String baseURL;
    String urlSuffix;

	public RestWebService(Context context) {
		baseURL = Constants.BASE_URL;
		urlSuffix = Constants.SUFFIX_URL;
		this.context = context;
		transparentProgressDialog = new ProgressDialog(context);
	}


	private String getServiceURL(String resourceName, String extraParameters) {
		return baseURL + urlSuffix + resourceName + extraParameters;
	}

	public void onComplete() {
		if (transparentProgressDialog.isShowing()) {
			transparentProgressDialog.dismiss();
		}
	}


	public void onSuccess(String data) {

	}
	
	public void onError(VolleyError error){
		
	}

    public void serviceCall(String resourceName, boolean showLoading) {
        serviceCall(resourceName, "", showLoading);
    }

	public void serviceCall(String resourceName, String extraParameters, boolean showLoading) {
		String url = getServiceURL(resourceName, extraParameters);
		if(showLoading){
			if(!transparentProgressDialog.isShowing()){
				transparentProgressDialog.show();
			}
		}

		if (resourceName.equalsIgnoreCase(Constants.API_GET_RESTAURANT)) {
			getCall(url);
		} else if (resourceName.equalsIgnoreCase(Constants.API_GET_SINGLE_RESTAURANT)) {
			getCall(url);
		} else if (resourceName.equalsIgnoreCase(Constants.API_GET_MENU)) {
            getCall(url);
        } else if (resourceName.equalsIgnoreCase(Constants.API_GET_TAG)) {
            getCall(url);
        } else if (resourceName.equalsIgnoreCase(Constants.API_GET_REVIEW)) {
            getCall(url);
        } else if (resourceName.equalsIgnoreCase(Constants.API_GET_RESTAURANT_IMAGE)) {
			getCall(url);
		} else{ //if resourceName doesn't match
            if (transparentProgressDialog.isShowing()) {
                transparentProgressDialog.dismiss();
            }
        }

	}

	private void getCall(String url) {
        Log.d("URL=====>",url);
		StringRequest req = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

			@Override
			public void onResponse(String data) {
				onComplete();
				onSuccess(data);
			}

		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				onComplete();
				Log.d("Error", error.toString());
				if(error.getMessage().contains("java.net.UnknownHostException")){
					Toast.makeText(context, context.getResources().getString(R.string.error_network_connection), Toast.LENGTH_SHORT).show();
					return;
				}
				onError(error);
			}
		}) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put(Constants.PARAMETER_AUTH, context.getResources().getString(R.string.parameter_auth));
                return params;
            }
        };


		// add the request object to the queue to be executed
		RestraApplication.getInstance().getRequestQueue().add(req);
	}

}