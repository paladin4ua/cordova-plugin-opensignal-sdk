
package opensignal;

import org.apache.cordova.CordovaPlugin;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import com.opensignal.datacollection.OpenSignalNdcSdk;
import com.opensignal.datacollection.exceptions.SdkNotInitialisedException;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CordovaInterface;

import org.apache.cordova.PermissionHelper;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.READ_PHONE_STATE;

public class OpenSignalSDKPlugin extends CordovaPlugin {

  private static final int PERMISSIONS_REQUEST_CODE = 100;

  public void initialize(CordovaInterface cordova, CordovaWebView webView) {
    super.initialize(cordova, webView);

    Context context = cordova.getActivity().getApplicationContext();

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
      (context.checkSelfPermission(ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
        context.checkSelfPermission(READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED)) {

      PermissionHelper.requestPermissions(this, PERMISSIONS_REQUEST_CODE, new String[]{ACCESS_FINE_LOCATION, READ_PHONE_STATE});
    } else {
      initializeSdk();
    }
  }

  private void initializeSdk() {

    Context context = cordova.getActivity().getApplicationContext();

    try {
      OpenSignalNdcSdk.initialiseSdk(context, true);
    } catch (SdkNotInitialisedException e) {
      e.printStackTrace();
    }
  }


  public void onRequestPermissionResult(int requestCode, String[] permissions,
                                        int[] grantResults) throws JSONException
  {
    for(int r:grantResults)
    {
    }
    initializeSdk();
  }

  @Override
  public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
    Context context = cordova.getActivity().getApplicationContext();


    if (action.equals("startDataCollection")) {
      return true;
    }
    return false;
  }

}
