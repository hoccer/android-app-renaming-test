package com.hoccer.importTest;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    public static final String HOCCER_XO_PACKAGE_NAME = "com.hoccer.xo.release";

    private enum HoccerXoStatus {
        CAN_EXPORT,
        CANNOT_EXPORT,
        NOT_INSTALLED
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Button button = (Button)findViewById(R.id.btn_import);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tryToImportCredentials();
            }
        });
    }

    private void tryToImportCredentials() {
        HoccerXoStatus xoStatus = HoccerXoStatus.NOT_INSTALLED;

        try {
            PackageInfo info = getPackageManager().getPackageInfo(HOCCER_XO_PACKAGE_NAME, 0);
            xoStatus = info.versionCode >= 89 ? HoccerXoStatus.CAN_EXPORT : HoccerXoStatus.CANNOT_EXPORT;
        } catch (PackageManager.NameNotFoundException e) {
            // Hoccer XO is not installed, case already handled above
        }

        switch (xoStatus) {
            case CAN_EXPORT:
                importCredentialsFromHoccerXO();
                break;
            case CANNOT_EXPORT:
                Log.i("GREEN", "Hoccer XO needs to be updated");
                break;
            case NOT_INSTALLED:
                Log.i("GREEN", "Hoccer XO is not installed");
                break;
        }
    }

    private void importCredentialsFromHoccerXO() {
        Intent intent = new Intent();
        ComponentName component = new ComponentName(HOCCER_XO_PACKAGE_NAME, "com.hoccer.xo.android.activity.DataExportActivity");
        intent.setComponent(component);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                String credentialsJson = data.getStringExtra("credentialsJson");
                Log.i("GREEN", "got credentials: " + credentialsJson);
            } else {
                Log.i("GREEN", "did not get credentials");
            }
        }
    }
}
