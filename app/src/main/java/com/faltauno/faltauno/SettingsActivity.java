package com.faltauno.faltauno;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Chechu on 14/5/2017.
 */

public class SettingsActivity extends PreferenceActivity {
    private static LocalLocationManager localLocationManager = LocalLocationManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsFragment()).commit();
    }

    public static class SettingsFragment extends PreferenceFragment {

        private static final String[] LOCATION_PERMS={
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
        };

        private static final int INITIAL_REQUEST=1338;
        private static final int LOCATION_REQUEST=INITIAL_REQUEST+1;

        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preference_screen);

            Preference shareLocPref = findPreference("pref_key_location");
            shareLocPref.setDefaultValue(true);

            shareLocPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {

                    boolean wasActivated = (boolean) newValue;

                    if (wasActivated){

                        localLocationManager.showRequestPermission((SettingsActivity)getContext(), LOCATION_PERMS, LOCATION_REQUEST);

                        if(!localLocationManager.locationPermissionGranted){
                            wasActivated = false;
                        }

                        Toast toast = Toast.makeText(getActivity(),"on!",Toast.LENGTH_SHORT);
                        toast.show();
                    } else { //desactivado
                        localLocationManager.revokePermissions();
                    }
                    return true;
                }

            });
        }

        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

        }
    }


}