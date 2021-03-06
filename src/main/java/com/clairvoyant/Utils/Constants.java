package com.clairvoyant.Utils;

/**
 * Created by Clairvoyant on 04-11-2015.
 */
public class Constants {

    // Base Url
    public final static String BASE_URL = "http://android-geeks.in/restaurant/v1/";
    public final static String SUFFIX_URL = "";
    public final static String URL_TAIL = ".json";

    //Coludinary
	public final static String CLOUDINARY_NAME = "restra";
	public final static String CLOUDINARY_API_KEY = "751941358289462";
	public final static String CLOUDINARY_API_SECRET = "ODdV0-zB_QrSm_qGDZI-kb_FmcA";

    //PARAMETER
    public final static String PARAMETER_AUTH = "X-Authorization";

//    PARAMETER APP
    public final static String PARAMETER_IMAGE_ID = "image_id";
    public final static String PARAMETER_AREA_FILE = "areas";

    //PARAMETER INTENT
    public final static String PARAMETER_SEARCH_IS_LIST_ALL = "isListAll";

    // Fonts
    public final static String FONT_CIRCULAR = "fonts/Circular_Air-Book.ttf";



    //Common
    public final static int TIMEOUT = 25000;
    public final static String SHARED_PREF_FILE_NAME = "porter_file";
    public final static String ERROR_MESSAGE = "error_message";
    public final static String PARAMETER_BUNDLE = "bundle";
    public final static int INTENT_REQUEST_CODE = 1;

    //API
    public final static String API_GET_RESTAURANT = "restaurant";
    public final static String API_GET_SINGLE_RESTAURANT = "restaurant/";  //restaurant/:id (id is restaurant id)
    public final static String API_GET_RESTAURANT_BY_AREA_ID = "restaurant/area/";  //restaurant/area/:id (id is area id)
    public final static String API_GET_MENU = "menu/";  //menu/:id (id is restaurant id)
    public final static String API_GET_TAG = "tag/";    //tag/:id (id is restaurant id)
    public final static String API_GET_REVIEW = "review/";   //review/:id (id is restaurant id)
    public final static String API_GET_RESTAURANT_IMAGE = "image/";   //get restaurant image
    public final static String API_GET_MENU_IMAGE = "menu_image/";  //menu_image/:id (id is restaurant id)
    public final static String API_GET_AREA = "area";
}
