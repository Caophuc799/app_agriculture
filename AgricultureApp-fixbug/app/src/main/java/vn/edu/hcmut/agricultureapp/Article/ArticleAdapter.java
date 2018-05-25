package vn.edu.hcmut.agricultureapp.Article;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import vn.edu.hcmut.agricultureapp.HomeActivity.FragmentLoadNew;
import vn.edu.hcmut.agricultureapp.R;

/**
 * Created by rongc on 5/20/2018.
 */
//
public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleHolder> {
    private Activity activity;
    private ArrayList<Article> listAticle;

    public ArticleAdapter(Activity activity, ArrayList<Article> listAticle) {
        this.activity = activity;
        this.listAticle = listAticle;
    }

    @Override
    public ArticleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_item,parent,false);
        return new ArticleHolder(view);
    }

    @Override
    public void onBindViewHolder(final ArticleHolder holder, int position) {
        final Article article = listAticle.get(position);
        holder.tvTitle.setText(article.getTitle());
        holder.tvDescription.setText(article.getDecription());
        Glide.with(activity)
                .load(article.getThumnail())
                .asBitmap()
                .atMost()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .animate(android.R.anim.fade_in)
                .approximate()
                .into(holder.imgThumnal);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //activity.startActivity(new Intent(activity,DetailArticleActivity.class).putExtra("Article",article));
//                Toast.makeText(activity,article.getTitle().toString(),Toast.LENGTH_SHORT).show();
//                Log.e("image",article.getTitle().toString());
                FragmentLoadNew fragmentLoadNew = new FragmentLoadNew();
                FragmentManager fragmentManager= activity.getFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                // fragmentTransaction.replace(R.id.frmSearch,fragmentSearch,"fragSearch");
//        fragmentTransaction.replace(R.id.frmContent,fragmentMenu,"fragMenu");
                Bundle bundle = new Bundle();
                bundle.putString("link", article.getUrl());
                fragmentLoadNew.setArguments(bundle);
                fragmentTransaction.replace(R.id.frmContent,fragmentLoadNew,"fragNews");
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listAticle.size();
    }

    class ArticleHolder extends RecyclerView.ViewHolder{

        private ImageView imgThumnal;
        private TextView tvTitle,tvDescription;
        public ArticleHolder(View itemView) {
            super(itemView);
            imgThumnal = (ImageView) itemView.findViewById(R.id.img_thumbnail);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvDescription = (TextView) itemView.findViewById(R.id.tv_Description);
        }
    }

}
//public class ArticleAdapter extends BaseAdapter {
//
//    private Context context;
//    private int layout;
//    private List<Article> lvArticle;
//
//    public ArticleAdapter(Context context, int layout, List<Article> lvArticle) {
//            this.context = context;
//            this.layout = layout;
//            this.lvArticle = lvArticle;
//    }
//
//    public ArticleAdapter(Activity activity, ArrayList<Article> articles) {
//        this.lvArticle=articles;
//    }
//
//
//    @Override
//    public int getCount() {
//        return lvArticle.size();
//    }
//
//    @Override
//    public Object getItem(int i) {
//        return lvArticle.get(i);
//    }
//
//    @Override
//    public long getItemId(int i) {
//        return 0;
//    }
//
//    @Override
//    public View getView(int i, View view, ViewGroup viewGroup) {
//        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        view = inflater.inflate(layout,null);
//
//        ImageView imageProduct = view.findViewById(R.id.imgArticle);
//        TextView txtTitle = view.findViewById(R.id.txtTitleArticle);
//        TextView txtDescription = view.findViewById(R.id.txtDeScription);
//
//
//
//        Article article = lvArticle.get(i);
//
//
//        txtTitle.setText(article.getTitle());
//
//        txtDescription.setText(article.getDecription());
//
//
//        Glide.with(view.getContext())
//        .load(article.getThumnail())
//        .into(imageProduct);
//
//        return view;
//    }
//}
