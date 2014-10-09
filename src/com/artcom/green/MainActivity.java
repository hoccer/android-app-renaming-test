package com.artcom.green;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Button button = (Button)findViewById(R.id.btn_import);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                ComponentName component = new ComponentName("com.hoccer.xo.release", "com.hoccer.xo.android.activity.DataExportActivity");
                intent.setComponent(component);
                startActivityForResult(intent, 0);
            }
        });
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
