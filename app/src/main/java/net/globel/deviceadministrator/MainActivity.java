package net.globel.deviceadministrator;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final int ACTIVATION_REQUEST = 11;

    private Context context;
    private ComponentName deviceAdminComponent;
    private DevicePolicyManager devicePolicyManager;

    private Switch adminSwitch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        adminSwitch = (Switch) findViewById(R.id.deviceAdminswitch);

        initDeviceAdminReceiver();

        adminSwitch.setChecked(isAdminActive());

        switchOperate();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ACTIVATION_REQUEST:
                if (resultCode == Activity.RESULT_OK) {
                    Log.i(TAG, "Administration enabled!");
                    adminSwitch.setChecked(true);
                } else {
                    Log.i(TAG, "Administration enable FAILED!");
                    adminSwitch.setChecked(false);
                }
                return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (adminSwitch!=null){
            if (devicePolicyManager!=null && deviceAdminComponent !=null){
                adminSwitch.setChecked(isAdminActive());
            }
        }
    }

    private void initDeviceAdminReceiver() {
        devicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        deviceAdminComponent = new ComponentName(context, DeviceAdministratorReceiver.class);
    }


    private void switchOperate() {
        adminSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                if (isChecked){
                        enableAdmin();
                }else {
                        disableAdmin();
                }
                Log.d(TAG, "onCheckedChanged to: " + isChecked);
            }
        });
    }


    private void enableAdmin(){

        Intent intent = new Intent(
                DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,
                deviceAdminComponent);
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                "Your It adminstrator asks the permission");
        startActivityForResult(intent, ACTIVATION_REQUEST);

    }

    private void disableAdmin(){
        devicePolicyManager.removeActiveAdmin(deviceAdminComponent);
    }


    private boolean isAdminActive() {
        return devicePolicyManager.isAdminActive(deviceAdminComponent);
    }
}
