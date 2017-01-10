package com.example.hp.catalog;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.widget.Adapter;
import android.widget.DialerFilter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.util.HashMap;

import data.HttpHandler;

public class DetailsActivity extends AppCompatActivity {


    private String TAG = DetailsActivity.class.getSimpleName();
    private ListView lv;
    private ProgressDialog pDialog;
    private static String url = "http://192.168.1.5:61422/api/rentalproperty";
    private String urladd;

    private HashMap<String, String> prop ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        String id = getIntent().getExtras().getString("id");
        Log.d("BBBBBB", id);
        urladd = "/"+ id;
        new GetProperty().execute();
    }

    private class GetProperty extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(DetailsActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url+urladd);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject c = new JSONObject(jsonStr);

                        String id = Integer.toString(c.getInt("id"));
                        String title = c.getString("title");
                        String odate = c.getString("occupancyDate").substring(0, 10);
                        String address = c.getString("locationAddress");
                        String price = Double.toString(c.getDouble("price"))+" KM";
                        String desc = c.getString("description");
                        String rate = c.getString("ratingA");
                        String actn = c.getString("accomodationTypeName");
                        String ltn = c.getString("leaseTypeName");
                        String fnm = c.getString("landlordFirstName");
                        String lnm = c.getString("landlordLastName");
                        String tel = c.getString("landlordContactNumber");
                        String img = c.getString("one");


                        // tmp hash map for single contact
                        prop = new HashMap<>();

                        // adding each child node to HashMap key => value
                        prop.put("id", id);
                        prop.put("title", title);
                        prop.put("occupancyDate", odate);
                        prop.put("location", address);
                        prop.put("price",price);
                        prop.put("description",desc);
                        prop.put("ratingA",rate);
                        prop.put("accomodationTypeName",actn);
                        prop.put("leasetypeName",ltn);
                        prop.put("landlordFirstName",fnm);
                        prop.put("landlordLastName",lnm);
                        prop.put("PhoneNumber",tel);
                        prop.put("img",img);
            } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();

            byte[] backToBytes = Base64.decode(prop.get("img"),0);
            Bitmap bmp = BitmapFactory.decodeByteArray(backToBytes, 0, backToBytes.length);
            ImageView image = (ImageView) findViewById(R.id.pic1);

            image.setImageBitmap(Bitmap.createScaledBitmap(bmp, image.getWidth(),
                    image.getHeight(), false));


            TextView tit = (TextView) findViewById(R.id.title2);
            tit.setText(prop.get("title"));

            TextView tit1 = (TextView) findViewById(R.id.address1);
            tit1.setText(prop.get("location"));

            TextView tit2 = (TextView) findViewById(R.id.price1);
            tit2.setText(prop.get("price"));

            TextView tit3 = (TextView) findViewById(R.id.ocDate1);
            tit3.setText(prop.get("occupancyDate"));

            /*("ratingA",rate);
                        prop.put("accomodationTypeName",actn);
                        prop.put("leasetypeName",ltn);
                        prop.put("landlordFirstName",fnm);
                        prop.put("landlordLastName",lnm);
            } catch (final JSONException e) {*/

            TextView tit4 = (TextView) findViewById(R.id.rate);
            tit4.setText(prop.get("ratingA"));

            TextView tit5 = (TextView) findViewById(R.id.atname);
            tit5.setText(prop.get("accomodationTypeName"));

            TextView tit6 = (TextView) findViewById(R.id.ltname);
            tit6.setText(prop.get("leaseTypeName"));

            TextView tit7 = (TextView) findViewById(R.id.lname);
            tit7.setText(prop.get("landlordLastName")+" "+prop.get("landlordFirstName"));

            TextView dit = (TextView) findViewById(R.id.tel);
            dit.setText(prop.get("PhoneNumber"));

            TextView tit9 = (TextView) findViewById(R.id.desc1);
            tit9.setText(prop.get("description"));


        }

    }
}
