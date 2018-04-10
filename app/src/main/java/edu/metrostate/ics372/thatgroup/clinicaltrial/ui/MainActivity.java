package edu.metrostate.ics372.thatgroup.clinicaltrial.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import edu.metrostate.ics372.thatgroup.clinicaltrial.models.entities.ClinicEntity;
import edu.metrostate.ics372.thatgroup.clinicaltrial.models.entities.ClinicalEntity;
import edu.metrostate.ics372.thatgroup.clinicaltrial.models.entities.TrialEntity;
import edu.metrostate.ics372.thatgroup.clinicaltrial.ui.fragments.clinic.FragmentClinic;
import edu.metrostate.ics372.thatgroup.clinicaltrial.ui.fragments.clinic.FragmentClinicList;
import edu.metrostate.ics372.thatgroup.clinicaltrial.ui.fragments.trial.FragmentAddTrial;
import edu.metrostate.ics372.thatgroup.clinicaltrial.ui.fragments.trial.FragmentTrial;
import edu.metrostate.ics372.thatgroup.clinicaltrial.ui.fragments.trial.FragmentTrialList;
import edu.metrostate.ics372.thatgroup.clinicaltrialclient.R;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String VISIBLE_FRAGMENT = "visible_fragment";
    private FragmentTrialList tListFrag;
    private FragmentClinicList cListFrag;
    private FragmentManager fragManager;
    private Fragment currBackStackFragment;
    private NavigationView navigationView;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        tListFrag = new FragmentTrialList();
        cListFrag = new FragmentClinicList();
        fragManager = getSupportFragmentManager();

        // Add trial list fragment if this is first creation
        if (savedInstanceState == null) {
            fragManager.beginTransaction().add(R.id.fragment_container,
                    tListFrag, VISIBLE_FRAGMENT).addToBackStack(null).commit();
        }
        initializeActivityView();
    }

    private void initializeActivityView() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);
        addListeners();
    }

    private void addListeners() {
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> showAddEntityForm());

        getSupportFragmentManager().addOnBackStackChangedListener(
                () -> {
                    currBackStackFragment = fragManager.findFragmentByTag(VISIBLE_FRAGMENT);

                    if (currBackStackFragment instanceof FragmentTrialList) {
                        setTitle(R.string.trial_list_lbl);
                        navigationView.getMenu().getItem(0).setChecked(true);
                        fab.show();
                    } else if (currBackStackFragment instanceof FragmentClinicList) {
                        setTitle(R.string.clinic_list_lbl);
                        navigationView.getMenu().getItem(1).setChecked(true);
                        fab.show();
                    } else if (currBackStackFragment instanceof FragmentTrial) {
                        setTitle(R.string.trial_detail_lbl);
                        fab.hide();
                    } else if (currBackStackFragment instanceof FragmentClinic) {
                        setTitle(R.string.clinic_detail_lbl);
                        fab.hide();
                    }
                });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_trials) {
            swapFragment(tListFrag);
        } else if (id == R.id.nav_clinics) {
            swapFragment(cListFrag);
        } else if (id == R.id.nav_patients) {

        } else if (id == R.id.nav_readings) {

        } else if (id == R.id.nav_import) {

        } else if (id == R.id.nav_export) {

        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void swapFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragment_container,
                        fragment, VISIBLE_FRAGMENT).commit();
    }

    public void show(ClinicalEntity entity) {
        Fragment fragmentToShow = null;
        if (entity instanceof TrialEntity) {
            fragmentToShow = FragmentTrial.forEntity(entity);
        } else if (entity instanceof ClinicEntity) {
            fragmentToShow = FragmentClinic.forEntity(entity);
//        } else if (entity instanceof PatientEntity) {
//            fragmentToShow = PatientEntity.forPatient(((PatientEntity) entity));
//            backStack = "patient";
//        } else if (entity instanceof ReadingEntity) {
//            fragmentToShow = ReadingEntity.forReading(((ReadingEntity) entity));
//            backStack = "reading";
        }
        swapFragment(fragmentToShow);
    }

    private void showAddEntityForm() {
        if (currBackStackFragment instanceof FragmentTrialList) {
            swapFragment(new FragmentAddTrial());
            setTitle("Add New Trial");
        }
        fab.hide();
    }

    public void showDatePicker(View view) {
        FragmentAddTrial frag = new FragmentAddTrial();
        //frag.show(getSupportFragmentManager(), null);
    }
}
