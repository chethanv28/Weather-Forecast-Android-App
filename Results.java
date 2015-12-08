package com.webtech.chethan.cvwebtechhw9;

import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ImageView;
import android.view.View;
import android.content.Intent;

import com.facebook.FacebookException;
import com.facebook.FacebookSdk;

import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.net.HttpURLConnection;
import org.json.JSONArray;
import org.json.JSONException;
import com.facebook.CallbackManager;
import com.facebook.login.LoginResult;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.facebook.share.Sharer;

import com.facebook.FacebookCallback;
import org.json.JSONObject;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class Results extends  FragmentActivity implements OnClickListener {


    CallbackManager callbackManager;
    ShareDialog shareDialog;

    String JSONARRAY = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        Intent intent = getIntent();
        FacebookSdk.sdkInitialize(getApplicationContext());

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);
        shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result loginResult) {
                // App code
                Toast.makeText(getApplicationContext(), "Facebook Post Successful",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {
                // App code
                Toast.makeText(getApplicationContext(), "Post Cancelled",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });




        String jsonArray = intent.getStringExtra("JSONDATA");
        JSONARRAY = jsonArray;
        String Street = intent.getStringExtra("Street");
        String City = intent.getStringExtra("City");
        String State = intent.getStringExtra("State");
        String Unit = intent.getStringExtra("Unit");

        try {
            JSONObject Obj = new JSONObject(getIntent().getStringExtra("JSONDATA"));
            String tempvalue =  Obj.getString("temperature");
            TextView temperature = (TextView) findViewById(R.id.temperature);
            temperature.setText(tempvalue);

            String iconvalue =  Obj.getString("iconvalue");
            ImageView icon = (ImageView) findViewById(R.id.icon);


            if(iconvalue.equals("clear-day"))
                icon.setImageResource(R.drawable.clear);

            else if(iconvalue.equals("clear-night"))
                icon.setImageResource(R.drawable.clear_night);

            else if(iconvalue.equals("rain"))
                icon.setImageResource(R.drawable.rain);

            else if(iconvalue.equals("snow"))
                icon.setImageResource(R.drawable.snow);

            else if(iconvalue.equals("sleet"))
                icon.setImageResource(R.drawable.sleet);

            else  if(iconvalue.equals("wind"))
                icon.setImageResource(R.drawable.wind);

            else if(iconvalue.equals("fog"))
                icon.setImageResource(R.drawable.fog);

            else if(iconvalue.equals("cloudy"))
                icon.setImageResource(R.drawable.cloudy);

            else if(iconvalue.equals("partly-cloudy-day"))
                icon.setImageResource(R.drawable.cloud_day);

            else if(iconvalue.equals("partly-cloudy-night"))
                icon.setImageResource(R.drawable.cloud_night);

            String low = Obj.getString("mintemp");
            String high = Obj.getString("maxtemp");
            TextView lowhigh = (TextView)findViewById(R.id.lowhigh);
            lowhigh.setText("L:"+low+"\u00b0"+" | H:"+high+"\u00b0");
            TextView unit = (TextView)findViewById(R.id.unit);
            String tempunit = Obj.getString("tempUnits");
            if(tempunit.equals("&#8451"))
                tempunit = "C";
            else tempunit = "F";
            unit.setText("\u00b0"+tempunit);

            TextView summary = (TextView)findViewById(R.id.summary);
            String summaryText = Obj.getString("summary")+" in "+City+", " +State;
            summary.setText(summaryText);

            TextView precvalue = (TextView)findViewById(R.id.precvalue);
            String precvaluetext = Obj.getString("precipitationvalue");
            precvalue.setText(precvaluetext);

            TextView rain = (TextView)findViewById(R.id.rainvalue);
            String rainvaluetext = Obj.getString("precipitationprob");
            rain.setText(rainvaluetext);

            TextView wind = (TextView)findViewById(R.id.wspeedvalue);
            String windvaluetext = Obj.getString("WindSpeed");
            wind.setText(windvaluetext);

            TextView dew = (TextView)findViewById(R.id.dewvalue);
            String dewvaluetext = Obj.getString("DewPoint");
            dewvaluetext = dewvaluetext.replace(dewvaluetext.substring(dewvaluetext.length() - 6, dewvaluetext.length()), "");
            dewvaluetext = dewvaluetext+"\u00b0"+tempunit;
            dew.setText(dewvaluetext);

            TextView humid = (TextView)findViewById(R.id.humidvalue);
            String humidvaluetext = Obj.getString("humidity");
            humid.setText(humidvaluetext);

            TextView visible = (TextView)findViewById(R.id.visiblevalue);
            String visiblevaluetext = Obj.getString("visibility");
            visible.setText(visiblevaluetext);

            TextView sunrise = (TextView)findViewById(R.id.sunrisevalue);
            String sunrisevaluetext = Obj.getString("sunrise");
            sunrise.setText(sunrisevaluetext);

            TextView sunset = (TextView)findViewById(R.id.sunsetvalue);
            String sunsetvaluetext = Obj.getString("sunset");
            sunset.setText(sunsetvaluetext);

        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }
        Button btnabout = (Button)findViewById(R.id.details);
        btnabout.setOnClickListener(this);
        Button btnmap = (Button)findViewById(R.id.map);
        btnmap.setOnClickListener(this);
        ImageButton btnfb = (ImageButton)findViewById(R.id.fb);
        btnfb.setOnClickListener(this);
    }

    public void facebookAPIs()
    {
        Uri link=  Uri.parse("http://cs-server.usc.edu:45678/hw/hw8/images/clear.png");
        if (ShareDialog.canShow(ShareLinkContent.class)) {
            ShareLinkContent linkContent = new ShareLinkContent.Builder()
                    .setImageUrl(link)
            .setContentTitle("Current Weather in Los Angeles, CA")
                    .setContentDescription("Clear 56dF")
                    .setContentUrl(Uri.parse("forecat.io"))
                    .build();

            shareDialog.show(linkContent);
        }
    }
    public void onClick(View v) {

        Intent i = new Intent(Results.this, DetailsActivity.class);
        Intent j = new Intent(Results.this, MapActivity.class);
        i.putExtra("JSONDATA", JSONARRAY);
        switch (v.getId())
        {
            case R.id.details: startActivity(i);//startActivity(new Intent("com.webtech.chethan.cvwebtechhw9.DetailsActivity"));
                break;
            case R.id.fb: facebookAPIs();
                break;
            case R.id.map: startActivity(j);
                break;
            default:
                break;
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_results, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }




}
