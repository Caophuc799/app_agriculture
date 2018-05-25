package vn.edu.hcmut.agricultureapp.HomeActivity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import vn.edu.hcmut.agricultureapp.Banggia.Fragmentbanggia;
import vn.edu.hcmut.agricultureapp.R;
import vn.edu.hcmut.agricultureapp.new_feed.QAActivity;

public class FragmentMenu extends Fragment {
    private FragmentManager fragmentManager;

    LinearLayout fragWeather,fragTraCuu,fragBangGia,fragQA;

    FragmentWeather fragmentWeather;
    Fragmentbanggia fragmentbanggia;
    FragmentMenuNews fragmentMenuNews;
    View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_menu,container,false);
        // code here
        addControlls(view);
        addEvents(view);

        return view;
    }
    public void loadWeather(){
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frmContent,fragmentWeather,"fragWeather");
        fragmentTransaction.commit();

    }
    public void loadBangGia(){
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.frmContent,fragmentbanggia,"fragBanggia");
        fragmentTransaction.commit();

    }
    public void loadTraCuu(){
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frmContent,fragmentMenuNews,"fragMenuNews");
        fragmentTransaction.commit();
    }
    public void loadQA(){

        startActivity(new Intent(view.getContext(), QAActivity.class));
    }
    private void addEvents(final View view) {
        fragWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(v.getContext(),"Weather",Toast.LENGTH_SHORT).show();
                GiaoTiep giaoTiep= (GiaoTiep) view.getContext();
                giaoTiep.uploadUIBottomNavigation(1);
                loadWeather();
            }
        });

        fragBangGia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(v.getContext(),"Weather",Toast.LENGTH_SHORT).show();
                GiaoTiep giaoTiep= (GiaoTiep) view.getContext();
                giaoTiep.uploadUIBottomNavigation(0);
                loadBangGia();
            }
        });
        fragTraCuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GiaoTiep giaoTiep= (GiaoTiep) view.getContext();
                giaoTiep.uploadUIBottomNavigation(0);
              loadTraCuu();
            }
        });
        fragQA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                GiaoTiep giaoTiep= (GiaoTiep) view.getContext();
//                giaoTiep.uploadUIBottomNavigation(2);
                loadQA();


            }
        });

    }

    private void addControlls(View view) {
        fragmentManager = getFragmentManager();
        fragTraCuu=view.findViewById(R.id.fragTraCuu);
        fragWeather=view.findViewById(R.id.fragWeather);
        fragBangGia=view.findViewById(R.id.fragBangGia);
        fragQA=view.findViewById(R.id.fragQA);
        fragmentWeather=new FragmentWeather();
        fragmentbanggia=new Fragmentbanggia();
        fragmentMenuNews=new FragmentMenuNews();
    }
}
