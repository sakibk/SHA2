package com.example.hp.catalog;

        import  android.app.ProgressDialog;
        import android.content.Intent;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.util.Log;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.ListAdapter;
        import android.widget.ListView;
        import android.widget.SimpleAdapter;
        import android.widget.Toast;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.util.ArrayList;
        import java.util.HashMap;


        import data.HttpHandler;
        import data.PropertyList;

public class PropertyActivity extends AppCompatActivity {


    private String TAG = PropertyActivity.class.getSimpleName();

    private ListView lv;
    private ProgressDialog pDialog;
    private static String url = "http://192.168.1.5:61422/api/propertylist";

    ArrayList<HashMap<String,String>> propList;
    ArrayList<PropertyList> pl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property);

        propList = new ArrayList<>();
        pl = new ArrayList<>();

        lv = (ListView) findViewById(R.id.list);

        new GetProperties().execute();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent i = new Intent(view.getContext(), DetailsActivity.class);
                HashMap<String, String> p = propList.get(position);
                for (String key : p.keySet()) {
                    i.putExtra(key, p.get(key));
                }
                startActivity(i);
            }
        });

        /*
        * ImageButton scroll = (ImageButton) findViewById(R.id.imageButton);
        scroll.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intentS = new Intent(view.getContext(),PropertyActivity.class);
                startActivityForResult(intentS,0);
            }
        });
        *

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?>adapter,View v, int position){
                ItemClicked item = adapter.getItemAtPosition(position);

                Intent intent = new Intent(PropertyActivity.this,DetailsActivity.class);
                //based on item add info to intent
                startActivity(intent);
            }
        });*/
    }
    private class GetProperties extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(PropertyActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONArray properties = new JSONArray(jsonStr);

                    // looping through All Contacts
                    for (int i = 0; i < properties.length(); i++) {
                        JSONObject c = properties.getJSONObject(i);

                        String id = Integer.toString(c.getInt("id"));
                        String title = c.getString("title");
                        String odate = c.getString("occupancyDate").substring(0, 10);
                        String address = c.getString("location");
                        String price = Double.toString(c.getDouble("price"))+" KM";


                        // tmp hash map for single contact
                        HashMap<String, String> prop = new HashMap<>();

                        // adding each child node to HashMap key => value
                        prop.put("id", id);
                        prop.put("title", title);
                        prop.put("occupancyDate", odate);
                        prop.put("location", address);
                        prop.put("price",price);

                        Log.d("AAAAAA",Integer.toString(propList.size()));
                        // adding contact to contact list
                        propList.add(prop);
                    }
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
            /**
             * Updating parsed JSON data into ListView
             * */
            ListAdapter adapter = new SimpleAdapter(
                    PropertyActivity.this, propList,
                    R.layout.list_item, new String[]{"title", "location","price",
                    "occupancyDate"}, new int[]{R.id.title1,
                    R.id.address, R.id.price, R.id.ocDate});

            lv.setAdapter(adapter);
        }

    }
}
