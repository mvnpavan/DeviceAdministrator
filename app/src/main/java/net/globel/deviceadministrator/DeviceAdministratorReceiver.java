package net.globel.deviceadministrator;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by mvnpavan on 11/02/17.
 */

public class DeviceAdministratorReceiver extends DeviceAdminReceiver {

    private static final String TAG = "DeviceAdminReceiver";

    @Override
    public void onEnabled(Context context, Intent intent) {
        super.onEnabled(context, intent);
        Toast.makeText(context, "Admin Enabled!" , Toast.LENGTH_SHORT).show();

        Log.d(TAG , "Admin Enabled!");
    }

    @Override
    public void onDisabled(Context context, Intent intent) {
        super.onDisabled(context, intent);
        Toast.makeText(context, "Admin Disabled!" , Toast.LENGTH_SHORT).show();

        Log.d(TAG , "Admin Disabled!");
    }
}
