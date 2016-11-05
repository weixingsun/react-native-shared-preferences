package in.sriraman.sharedpreferences;

import com.facebook.react.bridge.*;
import com.facebook.react.bridge.Callback;
import in.sriraman.sharedpreferences.SharedDataProvider;
import in.sriraman.sharedpreferences.SharedHandler;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class RNSharedPreferencesModule extends ReactContextBaseJavaModule {

    public void onCreate(Bundle savedInstanceState) {
    }

    public RNSharedPreferencesModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return "SharedPreferences";
    }

    @ReactMethod
    public void deleteItem(String key) {
        SharedHandler.init(getReactApplicationContext());
        SharedDataProvider.deleteSharedValue(key);
    }

    @ReactMethod
    public void setItem(String key, String value) {
        SharedHandler.init(getReactApplicationContext());
        SharedDataProvider.putSharedValue(key,value);
    }

    @ReactMethod
    public void getItem(String key, Callback successCallback){
      SharedHandler.init(getReactApplicationContext());
      String value = SharedDataProvider.getSharedValue(key);
      successCallback.invoke(value);

    }

    @ReactMethod
    public void getItems(ReadableArray keys, Callback successCallback){
        String[] keysArray= new String[keys.size()];
        for (int i=0;i<keys.size();i++){
            keysArray[i]=keys.getString(i);
        }
        String[] [] values = SharedDataProvider.getMultiSharedValues(keysArray);
        WritableNativeArray data = new WritableNativeArray();
        for(int i=0;i<keys.size();i++){
            data.pushString(values[i][1]);
        }
      	successCallback.invoke(data);
    }

    @ReactMethod
    public void getAll(Callback successCallback){
        String[][] values = SharedDataProvider.getAllSharedValues();
        WritableNativeArray data = new WritableNativeArray();
        for(int i=0; i<values.length; i++){
            WritableArray arr = new WritableNativeArray();
            arr.pushString(values[i][0]);
            arr.pushString(values[i][1]);
            data.pushArray(arr);
        }
        successCallback.invoke(data);
    }
		
/*
	@ReactMethod
	public void multiGet(String[] keys, Callback successCallback){

		SharedHandler.init(getReactApplicationContext());
		String[][] value = SharedDataProvider.getMultiSharedValues(keys);
		successCallback.invoke(value);

	}	

	@ReactMethod
	public void getAllKeys(Callback successCallback){

		SharedHandler.init(getReactApplicationContext());
		Object value = SharedDataProvider.getAllKeys();
		successCallback.invoke(value.toString());

	}	

*/

    @ReactMethod
    public void clear(){
      SharedHandler.init(getReactApplicationContext());
      SharedDataProvider.clear();
    }

}
