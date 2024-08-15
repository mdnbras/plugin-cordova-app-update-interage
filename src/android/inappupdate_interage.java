package cordova.plugin.codeplay.in.app.update;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Context;
import android.content.IntentSender;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.Task;
import android.widget.Toast;

public class inappupdate_interage extends CordovaPlugin {


	CallbackContext _callbackContex;

	@Override
	public void initialize(CordovaInterface cordova, CordovaWebView webView) {
		super.initialize(cordova, webView);
		// your init code here
		
		Context testParameter = (cordova.getActivity()).getBaseContext();

		// Toast.makeText(testParameter, "AutoUpdate( Android ) iniciado...", Toast.LENGTH_SHORT).show();
	}

	@Override
	public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {


		_callbackContex=callbackContext;

		Context testParameter = (cordova.getActivity()).getBaseContext();

		// Creates instance of the manager.
		AppUpdateManager appUpdateManager = AppUpdateManagerFactory.create(testParameter);

		// Returns an intent object that you use to check for an update.
		Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();

		//Toast.makeText(testParameter, "Classe executada com sucesso", Toast.LENGTH_SHORT).show();

		if (action.equals("isUpdateAvailable"))
		{
			// Checks that the platform will allow the specified type of update.
			appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
				if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
						// For a flexible update, use AppUpdateType.FLEXIBLE
						&& appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE))
				{

					//Toast.makeText(testParameter, "Success - Will start update", Toast.LENGTH_SHORT).show();

					callbackContext.success("true");


					// Request the update.
				}
				else
				{
					callbackContext.success("false");
					//Toast.makeText(testParameter, "Else part executed - no update available", Toast.LENGTH_LONG).show();

				}
			});

			return true;
		}

		if (action.equals("update"))
		{
			String updateType = args.getString(0);


			Integer flxRimmediate=1;
			if(updateType!="" && updateType!="null" && updateType.equals("flexible"))
			{
				flxRimmediate=0;
			}

			if(flxRimmediate==1)
			{
				appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
					if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
							&& appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE))
					{

						try {

							//Toast.makeText(testParameter, "Success - Will start update", Toast.LENGTH_SHORT).show();

							appUpdateManager.startUpdateFlowForResult(
									appUpdateInfo,
									AppUpdateType.IMMEDIATE,
									cordova.getActivity(),
									123);
						} catch (IntentSender.SendIntentException e) {
							e.printStackTrace();
							String str=e.getMessage();
							callbackContext.error(str);
							//Toast.makeText(testParameter, "Error - "+str, Toast.LENGTH_SHORT).show();
						}
					}
					else
					{
						callbackContext.success("No update available");
						//Toast.makeText(testParameter, "Else part executed - no update available", Toast.LENGTH_SHORT).show();

					}
				});
			}
			else
			{
				appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
					if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
							&& appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE))
					{

						try {

							//Toast.makeText(testParameter, "Success - Will start update", Toast.LENGTH_SHORT).show();

							appUpdateManager.startUpdateFlowForResult(
									appUpdateInfo,
									AppUpdateType.FLEXIBLE,
									cordova.getActivity(),
									123);
						} catch (IntentSender.SendIntentException e) {
							e.printStackTrace();
							String str=e.getMessage();
							callbackContext.error(str);
							//Toast.makeText(testParameter, "Error - "+str, Toast.LENGTH_SHORT).show();
						}


						// Request the update.
					}
					else
					{
						callbackContext.success("No update available");
						//Toast.makeText(testParameter, "Else part executed - no update available", Toast.LENGTH_SHORT).show();

					}
				});
			}

		}

		return false;
	}

	private void coolMethod(String message, CallbackContext callbackContext) {
		if (message != null && message.length() > 0) {
			callbackContext.success(message);
		} else {
			callbackContext.error("Expected one non-empty string argument.");
		}
	}
}
