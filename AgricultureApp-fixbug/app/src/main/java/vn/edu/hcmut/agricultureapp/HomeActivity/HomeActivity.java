package vn.edu.hcmut.agricultureapp.HomeActivity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import vn.edu.hcmut.agricultureapp.Banggia.Fragmentbanggia;
import vn.edu.hcmut.agricultureapp.BaseActivity;
import vn.edu.hcmut.agricultureapp.R;
import vn.edu.hcmut.agricultureapp.myAccount.EditInfoActivity;
import vn.edu.hcmut.agricultureapp.new_feed.QAActivity;

public class HomeActivity extends BaseActivity implements GiaoTiep {
    private FragmentManager fragmentManager;
    FragmentSearch fragmentSearch;
    FragmentMenu fragmentMenu;
    FragmentMenuNews fragmentMenuNews;
    Fragmentbanggia fragmentbanggia;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        addControlls();
        addEvents();

    }

    @Override
    public void onBackPressed() {
        uploadUIBottomNavigation(4);
        //super.onBackPressed();
    }

    private void addEvents() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_home:
                        bottomNavigationView.getMenu().findItem(R.id.action_home).setChecked(true);

                        //Toast.makeText(HomeActivity.this, "home=))", Toast.LENGTH_LONG).show();
                        FragmentTransaction fragmentTransaction1=fragmentManager.beginTransaction();
                        fragmentTransaction1.replace(R.id.frmContent,fragmentMenu,"fragMenu");
                        fragmentTransaction1.commit();
                        break;

                    case R.id.action_qa:
                        //bottomNavigationView.getMenu().findItem(R.id.action_home).setChecked(true);

                        startActivity(new Intent(HomeActivity.this, QAActivity.class));

                        //Toast.makeText(HomeActivity.this, "QA=))", Toast.LENGTH_LONG).show();
                        break;
                        //

                    case R.id.action_weather:
                        //Toast.makeText(HomeActivity.this, "weather=))", Toast.LENGTH_LONG).show();
                        bottomNavigationView.getMenu().findItem(R.id.action_weather).setChecked(true);

                        fragmentMenu.loadWeather();
                       break;

                    case R.id.action_account:
                        //bottomNavigationView.getMenu().findItem(R.id.action_account).setChecked(true);

                        startActivity(new Intent(HomeActivity.this, EditInfoActivity.class));

                        break;
                }
                return false;
            }
        });
        //bottomNavigationView.setSelectedItemId(R.id.action_weather);

    }

    private void addControlls() {
        fragmentManager = getFragmentManager();
        fragmentSearch=new FragmentSearch();
        fragmentMenu= new FragmentMenu();
        fragmentMenuNews = new FragmentMenuNews();
        fragmentbanggia = new Fragmentbanggia();
        bottomNavigationView=findViewById(R.id.bottom_home);

        FragmentTransaction fragmentTransaction1=fragmentManager.beginTransaction();
        fragmentTransaction1.replace(R.id.frmContent,fragmentMenu,"fragMenu");
        fragmentTransaction1.commit();
      //  Toast.makeText(HomeActivity.this, "1=))", Toast.LENGTH_LONG).show();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void uploadUIBottomNavigation(int check) {
        switch (check){
            case 0:
                //bottomNavigationView.setSelectedItemId(R.id.action_home);
                bottomNavigationView.getMenu().findItem(R.id.action_home).setChecked(true);
                break;
            case 1:
                bottomNavigationView.getMenu().findItem(R.id.action_weather).setChecked(true);
                break;
            case 2:
                bottomNavigationView.getMenu().findItem(R.id.action_qa).setChecked(true);
                break;
            case 3:
                bottomNavigationView.getMenu().findItem(R.id.action_account).setChecked(true);
                break;

            case 4:
                FragmentTransaction fragmentTransaction1=fragmentManager.beginTransaction();
                fragmentTransaction1.replace(R.id.frmContent,fragmentMenu,"fragMenu");
                fragmentTransaction1.commit();
                break;
        }
    }
}
