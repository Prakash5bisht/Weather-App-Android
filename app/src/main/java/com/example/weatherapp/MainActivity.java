package com.example.weatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

   private Button button;
   private EditText region;
   private TextView weather;
   private ImageView icon, temperature, humidity, wind, pressure;
   private TextView tText, hText, wText, pText;
   private RequestQueue requestQueue;

   Bitmap bitmap;

    // https://api.openweathermap.org/data/2.5/weather?q=India&appid=faf232c878d1d2af292b604ab7f3199c

    String baseUrl ="https://api.openweathermap.org/data/2.5/weather?q=";
    String API = "&appid=faf232c878d1d2af292b604ab7f3199c";
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        region = findViewById(R.id.region);
        weather = findViewById(R.id.weather);

        icon = findViewById(R.id.icon);

        temperature = findViewById(R.id.temperature);
        humidity = findViewById(R.id.humidity);
        wind = findViewById(R.id.wind);
        pressure = findViewById(R.id.pressure);

        tText = findViewById(R.id.tempText);
        hText = findViewById(R.id.humText);
        wText = findViewById(R.id.windText);
        pText = findViewById(R.id.pressText);

        requestQueue = Volley.newRequestQueue(this);


        button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // checking network connection
                    ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

                    if (networkInfo != null && networkInfo.isConnectedOrConnecting()){

                        if (region.getText().toString().matches("")) {
                            Toast toast = Toast.makeText(getApplicationContext(), ":( No input", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.TOP, 0, 10);
                            toast.show();
                        }

                        else {

                            String result = baseUrl + region.getText().toString() + API;

                            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, result, null, new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        String info = response.getString("weather");

                                        JSONArray array = new JSONArray(info);

                                        for (int i = 0; i < array.length(); i++) {
                                            JSONObject obj = array.getJSONObject(i);

                                            String ans = obj.getString("main");

                                            weather.setText(ans);

                                            switch (ans) {
                                                case "Thunderstorm":
                                                    icon.setImageResource(R.drawable.thunderstorm48);
                                                    break;

                                                case "Drizzle":
                                                    icon.setImageResource(R.drawable.lightrain48);
                                                    break;

                                                case "Rain":
                                                    icon.setImageResource(R.drawable.rainy40);
                                                    break;

                                                case "Snow":
                                                    icon.setImageResource(R.drawable.snow48);
                                                    break;

                                                case "Tornado":
                                                    icon.setImageResource(R.drawable.tornado16);
                                                    break;

                                                case "Mist":
                                                    icon.setImageResource(R.drawable.mist40);
                                                    break;

                                                case "Dust":
                                                    icon.setImageResource(R.drawable.dust40);
                                                    break;

                                                case "sand":
                                                    icon.setImageResource(R.drawable.dust40);
                                                    break;

                                                case "Haze":
                                                    icon.setImageResource(R.drawable.haze48);
                                                    break;

                                                case "Fog":
                                                    icon.setImageResource(R.drawable.fog40);
                                                    break;

                                                case "Squall":
                                                    icon.setImageResource(R.drawable.mist40);
                                                    break;

                                                case "Ash":
                                                    icon.setImageResource(R.drawable.volcano40);
                                                    break;

                                                case "Smoke":
                                                    icon.setImageResource(R.drawable.dust40);
                                                    break;

                                                case "Clear":
                                                    icon.setImageResource(R.drawable.sun);
                                                    break;

                                                case "Clouds":
                                                    icon.setImageResource(R.drawable.clouds64);
                                                    break;


                                            }




                                            temperature.setImageResource(R.drawable.temperature48);
                                            humidity.setImageResource(R.drawable.wet50);
                                            wind.setImageResource(R.drawable.wind64);
                                            pressure.setImageResource(R.drawable.pressure60);

                                            JSONObject tph = response.getJSONObject("main");
                                            String one = tph.getString("temp");
                                            String two = tph.getString("pressure");
                                            String three = tph.getString("humidity");



                                            float flag = Float.parseFloat(one);
                                            float celsius = flag - 273;
                                            String round = String.format("%.2f", celsius);


                                            tText.setText(round + " °C");
                                            pText.setText(two + " hpa");
                                            hText.setText(three + "%");



                                            JSONObject windspeed = response.getJSONObject("wind");
                                            String four = windspeed.getString("speed");



                                            wText.setText(four + " m/s");

                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            });
                            requestQueue.add(request);


                        }

                }

                    else{
                        Toast alert = Toast.makeText(getApplicationContext(),":( no connection available", Toast.LENGTH_SHORT);
                        alert.setGravity(Gravity.TOP,0,10);
                        alert.show();
                    }
                }
            });





    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {

        super.onSaveInstanceState(outState);

        outState.putString("savedweather",weather.getText().toString());
        outState.putString("savedTemp",tText.getText().toString());
        outState.putString("savedpressure",pText.getText().toString());
        outState.putString("savedHumidity",hText.getText().toString());
        outState.putString("savedWind",wText.getText().toString());




    }



    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        weather.setText(savedInstanceState.getString("savedweather"));


        temperature.setImageResource(R.drawable.temperature48);
        humidity.setImageResource(R.drawable.wet50);
        wind.setImageResource(R.drawable.wind64);
        pressure.setImageResource(R.drawable.pressure60);


        tText.setText(savedInstanceState.getString("savedTemp"));
        pText.setText(savedInstanceState.getString("savedpressure"));
        hText.setText(savedInstanceState.getString("savedHumidity"));
        wText.setText(savedInstanceState.getString("savedWind"));




    }
}
