package com.example.study.asynctaskexample;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class BooksAsyncTask extends AsyncTask<String,Void,String>
{
    ProgressDialog pb;
    String url="https://www.googleapis.com/books/v1/volumes?q=";
    String bookname;
    Context context;
    public BooksAsyncTask(Context context, String bookname) {
        this.context = context;
        this.bookname = bookname;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pb=new ProgressDialog(context);
        pb.setCancelable(true);
        pb.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pb.setMessage("Please wait..");
        pb.show();
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            URL u=new URL(url+bookname);
            HttpsURLConnection huc= (HttpsURLConnection) u.openConnection();
            InputStream inputStream=huc.getInputStream();
            BufferedReader br=new BufferedReader(new InputStreamReader(inputStream));
            String line="";
            StringBuilder sb=new StringBuilder();
            while ((line=br.readLine())!=null)
            {
                sb.append(line);
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Toast.makeText(context, ""+s, Toast.LENGTH_SHORT).show();
        pb.dismiss();
        try {
            JSONObject root=new JSONObject(s);
            JSONArray items=root.getJSONArray("items");
            JSONObject obj=items.getJSONObject(0);
            JSONObject volumeInfo=obj.getJSONObject("volumeInfo");
            String title=volumeInfo.optString("title");
            JSONArray authors=volumeInfo.getJSONArray("authors");
            StringBuilder sb=new StringBuilder();
            for(int i=0;i<authors.length();i++)
            {
                sb.append(authors.get(i)+"\n");
            }
            Toast.makeText(context,
                    "Title:\n"+title+"\n"+"Authors:\n"+sb.toString(),
                    Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
