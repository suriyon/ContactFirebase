package suriyon.cs.ubru.contactrealtimedemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bnvContact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        matchView();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ShowContactFragment()).commit();
        bnvContact.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()){
                    case R.id.menu_home : fragment = new ShowContactFragment();break;
                    case R.id.menu_add_contact : fragment = new AddContactFragment();break;
                    case R.id.menu_search : fragment = new SearchContactFragment();break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
                return true;
            }
        });
    }

    private void matchView() {
        bnvContact = findViewById(R.id.bnv_contact);
    }
}