package vn.edu.hcmut.agricultureapp.HomeActivity;

import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import vn.edu.hcmut.agricultureapp.R;

public class FragmentWeather extends Fragment {
    RequestQueue requestQueue;
    Context context;
    LayoutInflater inflater;
    Button btnWeather;
    ImageView imgIcon;
    TextView txtNhietDo,txtWind,txtCloud,txtHumidity,txtUpdate,txtCity;
    ListView lvWeather;
    WeatherAdapter weatherAdapter;
    ArrayList<ThoiTiet> thoiTietArrayList;

    String title="saigon";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_weather,container,false);
        this.inflater=inflater;
        context=view.getContext();
        // code here
        addControlls(view);
        addEvents();

        return view;
    }

    private void addEvents() {
       btnWeather.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               final PopupMenu popupMenu=new PopupMenu(context,btnWeather);
               popupMenu.getMenuInflater().inflate(R.menu.pop_menu,popupMenu.getMenu());
               popupMenu.show();
               popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                   @Override
                   public boolean onMenuItemClick(MenuItem item) {
                       Toast.makeText(context,item.getTitle(),Toast.LENGTH_LONG).show();
                       title=item.getTitle().toString();
                       txtCity.setText(title+", Việt Nam");
                       ReadWeatherCurrent();
                       return false;
                   }
               });
           }
       });
    }

    private void addControlls(View view) {
        txtCity=view.findViewById(R.id.txtCity);
        lvWeather=view.findViewById(R.id.lvWeather);
        txtNhietDo=view.findViewById(R.id.txtNhietDo);
        txtUpdate=view.findViewById(R.id.txtUpdate);
        txtWind=view.findViewById(R.id.txtWind);
        txtCloud=view.findViewById(R.id.txtCloud);
        txtHumidity=view.findViewById(R.id.txtHumidity);
        imgIcon=view.findViewById(R.id.imgIcon);
        btnWeather=view.findViewById(R.id.btnWeather);
        requestQueue = Volley.newRequestQueue(view.getContext());

        thoiTietArrayList=new ArrayList<ThoiTiet>();
        weatherAdapter=new WeatherAdapter(view.getContext(),R.layout.item_weather,thoiTietArrayList);
        lvWeather.setAdapter(weatherAdapter);

        ReadWeatherCurrent();


    }
    public void ReadWeatherCurrent(){
        new MyAsyncTask().execute("se");
        new MyAsyncTask_Get7DaysData().execute("se");
    }
    public class MyAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {}

        @Override
        protected String doInBackground(String... params) {
            getData(title);
            return "Executed";
        }
        @Override
        protected void onPostExecute(String result) {
        }
        @Override
        protected void onProgressUpdate(Void... values) {}

        public void getData(final String se) {

            String HttpUrl= "http://api.openweathermap.org/data/2.5/weather?q="+se+"&appid=be8d3e323de722ff78208a7dbb2dcd6f";
            final StringRequest stringRequest = new StringRequest(Request.Method.GET, HttpUrl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String ServerResponse) {
                            try {
                                JSONObject jsonObject=new JSONObject(ServerResponse);
                                String day=jsonObject.getString("dt");
                                String name=jsonObject.getString("name");
                                long longday=Long.valueOf(day);
                                Date date = new Date(longday*1000L);
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE dd-MM-yyyy HH-mm-ss");
                                String daytime=simpleDateFormat.format(date);
                                txtUpdate.setText(daytime);

                                JSONArray jsonArrayWeather=jsonObject.getJSONArray("weather");
                                JSONObject jsonObjectWeather=jsonArrayWeather.getJSONObject(0);
                                String status=jsonObjectWeather.getString("main");
                                String icon=jsonObjectWeather.getString("icon");
                                String link= "http://openweathermap.org/img/w/"+icon+".png";
                                Picasso.with(context).load(link).into(imgIcon);

                                JSONObject jsonObjectMain = jsonObject.getJSONObject("main");
                                String nhietdo=jsonObjectMain.getString("temp");

                                String doam=jsonObjectMain.getString("humidity");
                                txtHumidity.setText(doam);

                                Double a =Double.valueOf(nhietdo)-273D;
                                String Nhietdo=String.valueOf(a.intValue());
                                txtNhietDo.setText(Nhietdo+"ºC");

                                JSONObject jsonObjectWind= jsonObject.getJSONObject("wind");
                                String gio = jsonObjectWind.getString("speed");
                                txtWind.setText(gio);

                                JSONObject jsonObjectClouds= jsonObject.getJSONObject("clouds");
                                String may = jsonObjectClouds.getString("all");
                                txtCloud.setText(may);
                                JSONObject jsonObjectSys= jsonObject.getJSONObject("sys");
                                String country = jsonObjectSys.getString("country");

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            Toast.makeText(context, volleyError.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {


            };
            // Adding the StringRequest object into requestQueue.
            requestQueue.add(stringRequest);

        }
    }

    public class MyAsyncTask_Get7DaysData extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {}

        @Override
        protected String doInBackground(String... params) {
            getData(title);
            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {

        }

        @Override
        protected void onProgressUpdate(Void... values) {}

        public void getData(final String se) {


//
            // Creating string request with post method.
            String HttpUrl= "http://api.openweathermap.org/data/2.5/forecast/daily?q="+se+"&appid=be8d3e323de722ff78208a7dbb2dcd6f";
            final StringRequest stringRequest = new StringRequest(Request.Method.GET, HttpUrl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String ServerResponse) {
                          //  Toast.makeText(context, ServerResponse, Toast.LENGTH_LONG).show();
                            try {
                                JSONObject jsonObject=new JSONObject(ServerResponse);
                                JSONArray jsonArrayWeatherList=jsonObject.getJSONArray("list");
                                for(int i=0;i<jsonArrayWeatherList.length();i++){
                                    JSONObject jsonObjectList=jsonArrayWeatherList.getJSONObject(i);
                                    String ngay =jsonObjectList.getString("dt");
                                    long longday=Long.valueOf(ngay);
                                    Date date = new Date(longday*1000L);
                                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE dd-MM-yyyy HH-mm-ss");
                                    String daytime=simpleDateFormat.format(date);///-----------
                                 //   Toast.makeText(context, "-1-", Toast.LENGTH_LONG).show();


                                    JSONObject jsonObjectTemp=jsonObjectList.getJSONObject("temp");
                                   // Toast.makeText(context, "-1.1-", Toast.LENGTH_LONG).show();

                                    String max=jsonObjectTemp.getString("max");
                                    String min=jsonObjectTemp.getString("min");
                                   // Toast.makeText(context, "-2-", Toast.LENGTH_LONG).show();


                                    Double a =Double.valueOf(max)-273D;
                                    Double b =Double.valueOf(min)-273D;

                                    String NhietdoMax=String.valueOf(a.intValue());
                                    String NhietdoMin=String.valueOf(b.intValue());
                                   // Toast.makeText(context, "-3-", Toast.LENGTH_LONG).show();


                                    JSONArray jsonArrayWeather=jsonObjectList.getJSONArray("weather");
                                    JSONObject jsonObjectWeather=jsonArrayWeather.getJSONObject(0);
                                    String icon=jsonObjectWeather.getString("icon");
                                    String status=jsonObjectWeather.getString("description");
                                  //  Toast.makeText(context, "-4-", Toast.LENGTH_LONG).show();


                                    thoiTietArrayList.add(new ThoiTiet(daytime,status,icon,NhietdoMax,NhietdoMin));
                                   // Toast.makeText(context, "---"+thoiTietArrayList.size(), Toast.LENGTH_LONG).show();



                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(context, "eeeeeeerrrrrrrrr"+thoiTietArrayList.size(), Toast.LENGTH_LONG).show();

                            }
                            weatherAdapter.notifyDataSetChanged();
                           // Toast.makeText(context, ""+thoiTietArrayList.size(), Toast.LENGTH_LONG).show();

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                        }
                    }) {


            };
            // Adding the StringRequest object into requestQueue.
            requestQueue.add(stringRequest);

        }
    }
}
