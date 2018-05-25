package vn.edu.hcmut.agricultureapp.HomeActivity;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import vn.edu.hcmut.agricultureapp.R;

public class FragmentSearch extends Fragment {
    EditText etxtSearch;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_search,container,false);
        // code here
        addControlls(view);

        return view;
    }

    private void addControlls(View view) {
        etxtSearch=view.findViewById(R.id.etxtSearch);
        etxtSearch.setCursorVisible(false);
        etxtSearch.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_search_black_22dp, 0, 0, 0);
    }
}
