package com.malasang.wheatnight;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import java.util.ArrayList;


public class WheatActivity extends Activity {
    List<String> result = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wheat);
        Utils utils = new Utils();
        String fileName = "/mnt/sdcard/msg_0.log";
        String log = "the first message:" + System.currentTimeMillis();
        utils.writeFileSdcard(fileName, log);

        Log.e(Constants.TAG, "Has write to file!");
        String fileContent = utils.readFileSdcard(fileName);
        Log.e(Constants.TAG, fileContent);

        result = new ArrayList<String>();
        result = utils.calMsgTimeCost(fileName);

        // create button listener
        View sample_button = this.findViewById(R.id.sample_button);
        sample_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (R.id.sample_button == v.getId()) {
                    // add toast
//                    Toast.makeText(WheatActivity.this,"click on button A",Toast.LENGTH_LONG).show();

                    // transfer to new activity
//                    Intent i = new Intent(WheatActivity.this, ButtonAActivity.class);
//                    startActivity(i);

                    // alert dialog
                    //result    0: all the num of message received
                    //          1: average time cost
                    //          2. max time cost
                    //          3. min time cost
                    //          4. detail time cost
                    new AlertDialog.Builder(WheatActivity.this)
                            .setTitle("Message Test Result")
                            .setMessage("Send message 10\nReceived message 8\n" +
                                    Environment.getExternalStorageDirectory().toString() +
                                    "\nTotal message received:" + result.get(0) +
                                    "\nAverage cost(ms):" + result.get(1) +
                                    "\nMax cost(ms):" + result.get(2) +
                                    "\nMin cost(ms):" + result.get(3))
                            .setPositiveButton("Yes", null)
                            .setNegativeButton("No", null)
                            .show();
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.wheat, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
