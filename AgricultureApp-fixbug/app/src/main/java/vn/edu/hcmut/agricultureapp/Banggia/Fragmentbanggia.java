package vn.edu.hcmut.agricultureapp.Banggia;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

import vn.edu.hcmut.agricultureapp.R;

/**
 * Created by rongc on 5/20/2018.
 */

public class Fragmentbanggia extends Fragment {
    WebView htmlWebView;
    private  String linkBanggia = "http://giaca.nsvl.com.vn/";
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_banggia,container,false);
        // code here
//        Bundle bundle = getArguments();
//        if(bundle!=null) {
//            MY_URL = bundle.getString("link");
//        }
        addControlls(view);
        addEvents();

        return view;
    }

    private void addEvents() {

    }

    private void addControlls(View view) {
        htmlWebView = (WebView)view.findViewById(R.id.webView);
        htmlWebView.setWebViewClient(new CustomWebViewClient());
        WebSettings webSetting = htmlWebView.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setDisplayZoomControls(true);
        webSetting.setBuiltInZoomControls(true);

        webSetting.setLoadWithOverviewMode(true);
        webSetting.setUseWideViewPort(true);


        new DownloadTask().execute(linkBanggia);


    }
    private class CustomWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    private void updateWebView(String result) {

        Log.e("soi", result);
        htmlWebView.getSettings().setJavaScriptEnabled(true);
        htmlWebView.loadData(result, "text/html; charset=utf-8", "utf-8");
    }
    private class DownloadTask extends AsyncTask<String, Void, String> {

        private static final String TAG = "DownloadTask";

        @Override
        protected String doInBackground(String... strings) {
            Document document = null;
            String content="";
//
            try {
                document = (Document) Jsoup.connect(strings[0]).get();
                if (document != null) {
                    //Lấy  html có thẻ như sau: div#latest-news > div.row > div.col-md-6 hoặc chỉ cần dùng  div.col-md-6
                    Elements sub = document.select("div.DQ_Box_Middle");
                    content=sub.html();



//                    for (Element element : sub) {
//                        Banggia article = new Banggia();
//
//                        listBangia.add(article);
//                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return content;
        }

        @Override
        protected void onPostExecute(String banggias) {
            super.onPostExecute(banggias);
            updateWebView(banggias);

            //Setup data recyclerView

        }
    }
}