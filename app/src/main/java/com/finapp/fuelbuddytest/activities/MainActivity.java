package com.finapp.fuelbuddytest.activities;

import com.appolica.interactiveinfowindow.InfoWindow;
import com.appolica.interactiveinfowindow.InfoWindowManager;
import com.appolica.interactiveinfowindow.fragment.MapInfoWindowFragment;
import com.finapp.fuelbuddytest.R;
import com.finapp.fuelbuddytest.fragments.CustomInfoWindowFragment;
import com.finapp.fuelbuddytest.fragments.FragmentListPetrole;
import com.finapp.fuelbuddytest.ui.Petrole;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnTouchListener, InfoWindowManager.WindowShowListener,
        GoogleMap.OnMarkerClickListener, View.OnClickListener {

    private static final String TAG = "MainActivity";
    private static final int DISTANCE = 0;
    private static final int COST = 1;

    GoogleMap map;
    private Marker marker;
    private InfoWindowManager infoWindowManager;
    private InfoWindow customWindow;

    private FrameLayout rootView;
    private ImageButton profileBtn;
    private ImageButton settingsBtn;
    private ImageButton pinBtn;
    private ImageButton addBtn;
    private EditText searchEdit;
    private ImageButton dragBtn;
    private LinearLayout bottomContent;
    private FrameLayout.LayoutParams LParams;

    private static ViewPager viewPager;
    private static TabLayout tabLayout;
    private ArrayList<Petrole> allPetroles = new ArrayList<>();


    //test attributes for Petrole object
    private String petroleTitle = "Автозаправка Shell";
    private String petroleCost = "35,5 ₽";
    private String petroleTime = "час назад";
    private String petroleIcon = "logo_shell";
    private String petroleAdress = "ул. Садовническая, 57";
    private double petroleLatitude = 55.736316;
    private double petroleLongitude = 37.650791;
    private double petroleDistance = 0.2;

    private String petroleTitle2 = "Газпром";
    private String petroleCost2 = "30,5 ₽";
    private String petroleTime2 = "3 часа назад";
    private String petroleIcon2 = "logo_gasprom";
    private String petroleAdress2 = "ул. Карла-Маркса, 112";
    private double petroleLatitude2 = 55.650056;
    private double petroleLongitude2 = 37.323780;
    private double petroleDistance2 = 1.3;

    private String petroleTitle3 = "Газпром";
    private String petroleCost3 = "27,5 ₽";
    private String petroleTime3 = "3 часа назад";
    private String petroleIcon3 = "logo_gasprom";
    private String petroleAdress3 = "ул. Первомайская, 33";
    private double petroleLatitude3 = 55.793201;
    private double petroleLongitude3 = 37.782071;
    private double petroleDistance3 = 5.3;

    private String petroleTitle4 = "Газпром";
    private String petroleCost4 = "35,5 ₽";
    private String petroleTime4 = "3 часа назад";
    private String petroleIcon4 = "logo_gasprom";
    private String petroleAdress4 = "Шоссе энтузиастов, 51";
    private double petroleLatitude4 = 55.773077;
    private double petroleLongitude4 = 37.823859;
    private double petroleDistance4 = 12;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.finapp.fuelbuddytest.R.layout.activity_main);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/helveticaneuecyr-roman.otf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );


        final MapInfoWindowFragment mapInfoWindowFragment =
                (MapInfoWindowFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        infoWindowManager = mapInfoWindowFragment.infoWindowManager();

        mapInfoWindowFragment.getMapAsync(this);

        infoWindowManager.setWindowShowListener(MainActivity.this);
        infoWindowManager.getContainerSpec().setBackground(null);

        rootView = (FrameLayout)findViewById(R.id.root_view);

        profileBtn = (ImageButton)findViewById(R.id.profile_btn);
        profileBtn.setOnClickListener(this);
        settingsBtn = (ImageButton)findViewById(R.id.settings_btn);
        settingsBtn.setOnClickListener(this);
        pinBtn = (ImageButton)findViewById(R.id.pin_btn);
        pinBtn.setOnClickListener(this);
        addBtn = (ImageButton)findViewById(R.id.add_btn);
        addBtn.setOnClickListener(this);
        searchEdit = (EditText)findViewById(R.id.search_edit);
        dragBtn = (ImageButton)findViewById(R.id.drag_btn);
        bottomContent = (LinearLayout)findViewById(R.id.bottom_content);
        dragBtn.setOnTouchListener(this);
        LParams = (FrameLayout.LayoutParams)bottomContent.getLayoutParams();
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        addPetrolesToArrayList();

        //set array petroles for viewpager
        Bundle bundle1 = new Bundle();
        bundle1.putParcelableArrayList("petroles", allPetroles);
        Bundle bundle2 = new Bundle();
        bundle2.putParcelableArrayList("petroles", allPetroles);

        //set type of fragment
        bundle1.putInt("type", DISTANCE);
        Fragment fragment1 = new FragmentListPetrole();
        fragment1.setArguments(bundle1);

        //set type of fragment
        bundle2.putInt("type", COST);
        Fragment fragment2 = new FragmentListPetrole();
        fragment2.setArguments(bundle2
        );

        adapter.addFragment(fragment1, getString(R.string.by_distance));
        adapter.addFragment(fragment2, getString(R.string.by_cost));
        viewPager.setAdapter(adapter);

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
    }

    public void addPetrolesToArrayList() {
        Petrole petrole1 = new Petrole(petroleTitle, petroleCost, petroleTime, petroleIcon, petroleAdress, petroleDistance, petroleLatitude, petroleLongitude);
        Petrole petrole2 = new Petrole(petroleTitle2, petroleCost2, petroleTime2, petroleIcon2, petroleAdress2, petroleDistance2, petroleLatitude2, petroleLongitude2);
        Petrole petrole3 = new Petrole(petroleTitle3, petroleCost3, petroleTime3, petroleIcon3, petroleAdress3, petroleDistance3, petroleLatitude3, petroleLongitude3);
        Petrole petrole4 = new Petrole(petroleTitle4, petroleCost4, petroleTime4, petroleIcon4, petroleAdress4, petroleDistance4, petroleLatitude4, petroleLongitude4);
        allPetroles.add(petrole1);
        allPetroles.add(petrole2);
        allPetroles.add(petrole3);
        allPetroles.add(petrole4);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        // Add a marker in Moscow, Russia, and move the camera.
        LatLng moscow = new LatLng(55.75, 37.621);
        map.setMinZoomPreference(10);
        map.setMaxZoomPreference(16);
        googleMap.setMinZoomPreference(10);
        CameraUpdate cameraPosition = CameraUpdateFactory.newLatLngZoom(moscow, 12);
        map.moveCamera(cameraPosition);
        map.animateCamera(cameraPosition);
        googleMap.setOnMarkerClickListener(MainActivity.this);

    }

    @Override
    public void onWindowShowStarted(@NonNull InfoWindow infoWindow) {

    }

    @Override
    public void onWindowShown(@NonNull InfoWindow infoWindow) {

    }

    @Override
    public void onWindowHideStarted(@NonNull InfoWindow infoWindow) {

    }

    @Override
    public void onWindowHidden(@NonNull InfoWindow infoWindow) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.profile_btn:
                Toast.makeText(this, "Profile Button pressed", Toast.LENGTH_SHORT).show();
                break;
            case R.id.settings_btn:
                Toast.makeText(this, "Settings Button pressed", Toast.LENGTH_SHORT).show();
                break;
            case R.id.pin_btn:
                Toast.makeText(this, "Pin Button pressed", Toast.LENGTH_SHORT).show();
                break;
            case R.id.add_btn:
                Toast.makeText(this, "Add Button pressed", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }


    // Adapter for the viewpager using FragmentPagerAdapter
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    public boolean onTouch(View view, MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_UP:

                break;

            case MotionEvent.ACTION_MOVE:
                LParams.bottomMargin -= event.getY();
                if (convertPixelsToDp(LParams.bottomMargin, MainActivity.this) < -150) {
                    LParams.bottomMargin = (int)convertDpToPixel(-190, MainActivity.this);
                }
                else if (convertPixelsToDp(LParams.bottomMargin, MainActivity.this) > -50) {
                    LParams.bottomMargin = 0;
                }
                bottomContent.setLayoutParams(LParams);
                break;
        }
        rootView.invalidate();
        return true;
    }

    public void setMarker(int posObjectInPetroleArray) {
        Petrole markerPetrole = allPetroles.get(posObjectInPetroleArray);
        LatLng markerPosition = new LatLng(markerPetrole.getLatitude(), markerPetrole.getLongtitude());
        MarkerOptions options = new MarkerOptions().position(markerPosition);

        Marker marker = map.addMarker(new MarkerOptions().position(markerPosition));

        final int offsetX = 0;
        final int offsetY = 0;

        final InfoWindow.MarkerSpecification markerSpec = new InfoWindow.MarkerSpecification(offsetX, offsetY);

        CustomInfoWindowFragment customInfoWindowFragment = new CustomInfoWindowFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("petrole", markerPetrole);
        customInfoWindowFragment.setArguments(bundle);

        customWindow = new InfoWindow(marker, markerSpec, customInfoWindowFragment);


        if(marker == null){
            marker = map.addMarker(options);
        }
        else {
            marker.setPosition(markerPosition);
        }
        marker.setVisible(false);

        InfoWindow infoWindow = null;
        infoWindow = customWindow;

        if (infoWindow != null) {
            infoWindowManager.toggle(infoWindow, true);
        }

        map.moveCamera(CameraUpdateFactory.newLatLng(markerPosition));
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return true;
    }

    public static float convertPixelsToDp(float px, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }

    public static float convertDpToPixel(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }

    public void print(String text, float value) {
        Log.d(text, String.valueOf(value));
    }

    public void print(String text) {
        Log.d(TAG, text);
    }
}
