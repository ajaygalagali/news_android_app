package com.astro.webnotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    static   ArrayList<String> arrayList = new ArrayList<String>();
    static  ArrayList<String> urlList = new ArrayList<String>();
    ArrayAdapter arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        DownloadTask downloadTask = new DownloadTask();
        downloadTask.execute("https://newsapi.org/v2/top-headlines?sources=google-news-in&apiKey=YOUR_API_KEY"); //Put your api key here


        arrayAdapter= new ArrayAdapter(this,android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,WebActivity.class);
                intent.putExtra("pos",position);
                startActivity(intent);
            }
        });
    }

    public class DownloadTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try {
                url =new URL(urls[0]);
                urlConnection = (HttpURLConnection)url.openConnection();
                InputStream inputStream =urlConnection.getInputStream();
                InputStreamReader reader =new InputStreamReader(inputStream);
                int data =reader.read();

                while(data!=-1){

                    char current =(char) data;
                    result+=current;
                    data = reader.read();

                }
//                Log.i("Result",result);
                return result;
            }catch(Exception e){
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(String s) {


            super.onPostExecute(s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                String articles = jsonObject.getString("articles");

                JSONArray jsonArray = new JSONArray(articles);

                for(int i=0;i<jsonArray.length();i++){
                    JSONObject part = jsonArray.getJSONObject(i);
//                    Log.i("Title",String.valueOf(part.getString("title")));
                    arrayList.add(String.valueOf(part.getString("title")));
//                    Log.i("URL",String.valueOf(part.getString("url")));
                    urlList.add(String.valueOf(part.getString("url")));
                    arrayAdapter.notifyDataSetChanged();
                }
//                Log.i("Articles ",articles);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


}
