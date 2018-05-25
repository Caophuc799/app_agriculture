package vn.edu.hcmut.agricultureapp.HomeActivity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import vn.edu.hcmut.agricultureapp.R;

/**
 * Created by rongc on 5/20/2018.
 */

public class FragmentMenuNews extends Fragment {

    Button btnChannuoi;
    Button btnTrongtrot;
    Button btnThuysan;
    Button btnLamnghiep;
    String Strtrongtrot="http://www.khuyennongvn.gov.vn/ky-thuat-trong-trot_t113c105";
    String Strchannuoi = "http://www.khuyennongvn.gov.vn/ky-thuat-chan-nuoi_t113c107";
    String Strthuysan = "http://www.khuyennongvn.gov.vn/ky-thuat-thuy-san_t113c104";
    String Strlamnghiep="http://www.khuyennongvn.gov.vn/ky-thuat-lam-nghiep_t113c106";


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_menu_news,container,false);
        // code here

        addControlls(view);
        addEvents();

        return view;
    }






    private void addEvents() {

    }

    private void addControlls(final View view) {
        btnChannuoi = view.findViewById(R.id.btnChannuoi);
        btnTrongtrot = view.findViewById(R.id.btnTrongtrot);
        btnThuysan = view.findViewById(R.id.btnThuysan);
        btnLamnghiep = view.findViewById(R.id.btnLamnghiep);

        btnChannuoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentNews fragmentNews = new FragmentNews();
                FragmentManager fragmentManager= getActivity().getFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                // fragmentTransaction.replace(R.id.frmSearch,fragmentSearch,"fragSearch");
//        fragmentTransaction.replace(R.id.frmContent,fragmentMenu,"fragMenu");
                Bundle bundle = new Bundle();
                bundle.putString("link", Strchannuoi);
                fragmentNews.setArguments(bundle);
                fragmentTransaction.add(R.id.frmContent,fragmentNews,"fragNews");
                fragmentTransaction.commit();
            }
        });

        btnTrongtrot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentNews fragmentNews = new FragmentNews();
                FragmentManager fragmentManager= getActivity().getFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                // fragmentTransaction.replace(R.id.frmSearch,fragmentSearch,"fragSearch");
//        fragmentTransaction.replace(R.id.frmContent,fragmentMenu,"fragMenu");
                Bundle bundle = new Bundle();
                bundle.putString("link", Strtrongtrot);
                fragmentNews.setArguments(bundle);
                fragmentTransaction.add(R.id.frmContent,fragmentNews,"fragNews");
                fragmentTransaction.commit();
            }
        });

        btnThuysan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentNews fragmentNews = new FragmentNews();
                FragmentManager fragmentManager= getActivity().getFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                // fragmentTransaction.replace(R.id.frmSearch,fragmentSearch,"fragSearch");
//        fragmentTransaction.replace(R.id.frmContent,fragmentMenu,"fragMenu");
                Bundle bundle = new Bundle();
                bundle.putString("link", Strthuysan);
                fragmentNews.setArguments(bundle);
                fragmentTransaction.add(R.id.frmContent,fragmentNews,"fragNews");
                fragmentTransaction.commit();
            }
        });

        btnLamnghiep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentNews fragmentNews = new FragmentNews();
                FragmentManager fragmentManager= getActivity().getFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                // fragmentTransaction.replace(R.id.frmSearch,fragmentSearch,"fragSearch");
//        fragmentTransaction.replace(R.id.frmContent,fragmentMenu,"fragMenu");
                Bundle bundle = new Bundle();
                bundle.putString("link", Strlamnghiep);
                fragmentNews.setArguments(bundle);
                fragmentTransaction.add(R.id.frmContent,fragmentNews,"fragNews");
                fragmentTransaction.commit();
            }
        });



    }
}
