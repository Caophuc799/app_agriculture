package vn.edu.hcmut.agricultureapp.HomeActivity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import vn.edu.hcmut.agricultureapp.Article.Article;
import vn.edu.hcmut.agricultureapp.Article.ArticleAdapter;
import vn.edu.hcmut.agricultureapp.R;

public class FragmentNews extends Fragment {

    public String MY_URL = "http://www.khuyennongvn.gov.vn/ky-thuat-chan-nuoi_t113c107";

    private RecyclerView recycler;
//    private ListView lvArticle;
//    private ArrayList<Article> arrayArticle;
    private ArticleAdapter articleAdapter;





    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_fragment_news,container,false);
        // code here
        Bundle bundle = getArguments();
        if(bundle!=null) {
            MY_URL = bundle.getString("link");
        }
        addControlls(view);
        addEvents();

        return view;
    }






    private void addEvents() {

    }

    private void addControlls(View view) {
//        lvArticle = view.findViewById(R.id.lvArticle);
        recycler = view.findViewById(R.id.recyler_category);
        configRecyclerView();
//        arrayArticle= new ArrayList<Article>();
//        articleAdapter = new ArticleAdapter(getActivity(), R.layout.article_item,arrayArticle);
//        lvArticle.setAdapter(articleAdapter);
        new DownloadTask().execute(MY_URL);
    }
    private void configRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(),1);

        recycler.hasFixedSize();
        recycler.setLayoutManager(layoutManager);
    }
    //Download HTML bằng AsynTask
    private class DownloadTask extends AsyncTask<String, Void, ArrayList<Article>> {

        private static final String TAG = "DownloadTask";

        @Override
        protected ArrayList<Article> doInBackground(String... strings) {
            Document document = null;
            ArrayList<Article> listArticle = new ArrayList<>();
            try {
                document = (Document) Jsoup.connect(strings[0]).get();
                if (document != null) {
                    //Lấy  html có thẻ như sau: div#latest-news > div.row > div.col-md-6 hoặc chỉ cần dùng  div.col-md-6
                    Elements sub = document.select("div.xitem");
                    for (Element element : sub) {
                        Article article = new Article();
                        Element titleSubject = element.getElementsByTag("h2").first();
                        Element imgSubject = element.getElementsByTag("img").first();
                        Element linkSubject = element.getElementsByTag("a").first();
                        Element descrip = element.getElementsByClass("xsubject").first();
                        Log.e("dang",element.getAllElements().toString());
                        //Parse to model
                        if (titleSubject != null) {
                            String title = titleSubject.text();
                            article.setTitle(title);
                        }
                        if (imgSubject != null) {
                            String src = imgSubject.attr("src");
                            article.setThumnail("http://www.khuyennongvn.gov.vn"+src);
                        }
                        if (linkSubject != null) {
                            String link = linkSubject.attr("href");
                            article.setUrl("http://www.khuyennongvn.gov.vn/vi-VN"+link);
                        }
                        if (descrip != null) {
                            String des = descrip.text();
                            article.setDecription(des);
                        }
                        //Add to list
//                        Toast.makeText(getActivity(),article.getTitle().toString(),Toast.LENGTH_SHORT).show();
                        listArticle.add(article);
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return listArticle;
        }

        @Override
        protected void onPostExecute(ArrayList<Article> articles) {
            super.onPostExecute(articles);
            //Setup data recyclerView
            articleAdapter = new ArticleAdapter(getActivity(),articles);
            recycler.setAdapter(articleAdapter);
        }
    }
}

