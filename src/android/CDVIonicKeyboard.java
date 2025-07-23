package io.ionic.keyboard;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.PluginResult;
import org.apache.cordova.PluginResult.Status;
import org.json.JSONArray;
import org.json.JSONException;

import android.os.Handler;
import android.os.Looper;

public class CDVIonicKeyboard extends CordovaPlugin {
    private Keyboard implementation;

    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
    }

    public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {
        if ("hide".equals(action)) {
            cordova.getThreadPool().execute(
                () -> {
                   if (!implementation.hide()) {
                        callbackContext.error("Can't close keyboard, not currently focused");
                    } else {
                        callbackContext.success(); // Thread-safe.
                    }
                }
            );
            return true;
        }
        if ("show".equals(action)) {
            cordova.getThreadPool().execute(
                () ->
                    new Handler(Looper.getMainLooper())
                        .postDelayed(
                            () -> {
                                implementation.show();
                                callbackContext.success(); // Thread-safe.
                            },
                            350
                        )
            );
            return true;
        }
        if ("init".equals(action)) {
            cordova.getThreadPool().execute(
                () -> {
                    boolean resizeOnFullScreen = preferences.getBoolean("resizeOnFullScreen", false);
                    implementation = new Keyboard(cordova.getActivity(), resizeOnFullScreen);
                    implementation.setKeyboardEventListener(
                        (String event, int size) -> {
                            PluginResult result;
                            String msg;
                            switch (event) {
                                case Keyboard.EVENT_KB_WILL_SHOW:
                                case Keyboard.EVENT_KB_DID_SHOW:
                                    msg = "S" + Integer.toString(size);
                                    result = new PluginResult(PluginResult.Status.OK, msg);
                                    result.setKeepCallback(true);
                                    callbackContext.sendPluginResult(result);
                                    break;
                                case Keyboard.EVENT_KB_WILL_HIDE:
                                case Keyboard.EVENT_KB_DID_HIDE:
                                    msg = "H";
                                    result = new PluginResult(PluginResult.Status.OK, msg);
                                    result.setKeepCallback(true);
                                    callbackContext.sendPluginResult(result);
                                    break;
                            }
                        }
                    );

                    PluginResult dataResult = new PluginResult(PluginResult.Status.OK);
                    dataResult.setKeepCallback(true);
                    callbackContext.sendPluginResult(dataResult);
                }
            );
            return true;
        }
        return false;  // Returning false results in a "MethodNotFound" error.
    }

    @Override
    public void onDestroy() {
        implementation.setKeyboardEventListener(null);
    }

}
