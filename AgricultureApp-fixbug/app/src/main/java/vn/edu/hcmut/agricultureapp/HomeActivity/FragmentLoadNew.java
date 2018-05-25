package vn.edu.hcmut.agricultureapp.HomeActivity;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;

import vn.edu.hcmut.agricultureapp.Article.Article;
import vn.edu.hcmut.agricultureapp.Article.ArticleAdapter;
import vn.edu.hcmut.agricultureapp.R;

public class FragmentLoadNew extends Fragment {



    WebView mWebview;
    WebView htmlWebView;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_one_new,container,false);
        // code here
        addControlls(view);
        addEvents();

        return view;
    }






    private void addEvents() {

    }

    private void addControlls(View view) {

        Bundle bundle = this.getArguments();
        String link = "http://www.khuyennongvn.gov.vn/vi-VN/khoa-hoc-cong-nghe/khcn-trong-nuoc/ky-thuat-trong-va-cham-soc-cay-thanh-long-phan-2_t114c40n16940";
        if (bundle != null) {
            link = bundle.getString("link");
            Log.e("keylink",link);
//            Toast.makeText(getActivity(),link,Toast.LENGTH_SHORT).show();
        }
        htmlWebView = (WebView)view.findViewById(R.id.webView);
        htmlWebView.setWebViewClient(new CustomWebViewClient());
        WebSettings webSetting = htmlWebView.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setDisplayZoomControls(true);
        webSetting.setBuiltInZoomControls(true);

        webSetting.setLoadWithOverviewMode(true);
        webSetting.setUseWideViewPort(true);


        new DownloadTask().execute( link);


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
                    Elements sub = document.select("div.tnnddemo_wrap");
                    content=sub.html();
                    Log.e("Database",content.toString());



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

