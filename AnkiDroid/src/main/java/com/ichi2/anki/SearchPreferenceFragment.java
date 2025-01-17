/***************************************************************************************
 * Copyright (c) 2009 Nicolas Raoul <nicolas.raoul@gmail.com>                           *
 * Copyright (c) 2009 Edu Zamora <edu.zasu@gmail.com>                                   *
 * Copyright (c) 2010 Norbert Nagold <norbert.nagold@gmail.com>                         *
 * Copyright (c) 2012 Kostas Spyropoulos <inigo.aldana@gmail.com>                       *
 * Copyright (c) 2015 Timothy Rae <perceptualchaos2@gmail.com>                          *
 *                                                                                      *
 * This program is free software; you can redistribute it and/or modify it under        *
 * the terms of the GNU General Public License as published by the Free Software        *
 * Foundation; either version 3 of the License, or (at your option) any later           *
 * version.                                                                             *
 *                                                                                      *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY      *
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A      *
 * PARTICULAR PURPOSE. See the GNU General Public License for more details.             *
 *                                                                                      *
 * You should have received a copy of the GNU General Public License along with         *
 * this program.  If not, see <http://www.gnu.org/licenses/>.                           *
 ****************************************************************************************/

package com.ichi2.anki;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import timber.log.Timber;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SearchPreferenceFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    // Layout items
    private ArrayAdapter<String> mStringArrayAdapter;
    private SearchView mSearchView;
    private ListView mListView;
    protected HashMap<String, String> mFragmentMap;
    private FragmentManager mFragmentManager;


    /**
     * Constructor
     *  Instantiates the fragment and gives it a reference to the searchview object
     *   that will be used to search with.
     * @param searchView   The search bar that will be used
     */
    public SearchPreferenceFragment(SearchView searchView) {
        mSearchView = searchView; // Take searchView from parent's toolbar
    }


    /**
     * onCreate
     *  Performs the basic application startup logic. Happens only once for the entire life of the activity.
     * @param savedInstanceState   Bundle object passed by the parent activity
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        mFragmentManager = getParentFragmentManager();
    }


    /**
     * onCreateview
     *  Creates the fragment view and applies the layout to the fragment. The elements inside the fragment are
     *    also initialized and set.
     * @param inflater                inflater that will add the fragment's layout
     * @param container               container in which the fragment is located
     * @param savedInstanceState      Bundle object passed by the parent activity
     * @return                        Fragment view.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_preference, container, false);

        // Generate a list of searchable items
        generateMap();
        List<String> lines = new ArrayList<String>(mFragmentMap.keySet()); // Get keys as items to view in search

        // Layout items
        mListView = (ListView) view.findViewById(R.id.search_result_listview); // List view to hold results
        mStringArrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, lines);
        mListView.setAdapter(mStringArrayAdapter);
        mFragmentManager = getParentFragmentManager(); // Needed to switch between fragments

        // Setting up search view functionality with ArrayAdapter
        mSearchView.setQueryHint("Search");
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            /**
             * onQueryTextSubmit
             *  Takes the text that was typed in the search view after the user hits enter.
             * @param query   The text that the user typed
             * @return        False by default
             */
            @Override
            public boolean onQueryTextSubmit(String query) {
                String message = "SearchPreference :: onQueryTextSubmit = " + query;
                Timber.d(message);
                return false;
            }


            /**
             * onQueryTextChange
             *  Registers changes in the typed text in the search bar. Modifies the listview
             *   with filtered results that match the user's search query.
             * @param newText   Modified text
             * @return          False by default
             */
            @Override
            public boolean onQueryTextChange(String newText) {
                mStringArrayAdapter.getFilter().filter(newText);
                return false;
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            /**
             * onItemClick
             *  Determines which fragment needs to be created depending on which issue was selected.
             * @param parent     Listview adapter view that is being used.
             * @param view       Listview in use
             * @param position   position/index of the item that was selected
             * @param id         ID of the item that was selected
             */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Timber.d("Listview item onClick...");
                String option = mListView.getItemAtPosition(position).toString();
                String location = mFragmentMap.get(option);

                Fragment fragLocation = null;
                switch(location) {
                    case "General Preferences":
                        fragLocation = new Preferences.GeneralSettingsFragment();
                        break;
                    case "Reviewing Preferences":
                        fragLocation = new Preferences.ReviewingSettingsFragment();
                        break;
                    case "Appearance Preferences":
                        fragLocation = new Preferences.AppearanceSettingsFragment();
                        break;
                    case "Gesture Preferences":
                        fragLocation = new Preferences.GesturesSettingsFragment();
                        break;
                    case "Advanced Preferences":
                        fragLocation = new Preferences.AdvancedSettingsFragment();
                        break;
                    default:
                        // Frag remains as the search fragment
                        break;
                }
                if (fragLocation != null) {
                    mFragmentManager.beginTransaction().replace(R.id.settings_container, fragLocation).commit();
                }
            }
        });

        return view;
    }


    /**
     * generateMap
     *  Generates a hashmap that contains key value pairs showing where each search/preference options is located.
     */
    protected void generateMap() {
        String[] options = {"AnkiWeb account", "Fetch media on sync", "Automatic synchronization", "Display synchronization status",
                "Deck for new cards", "Language", "Share feature usage", "Paste clipboard images as PNG", "Error reporting mode",
                "Notify when", "Vibrate", "Blink light", "New card position", "Start of next day", "Learn ahead limit",
                "Timebox time limit", "New timezone handling", "Double tap time interval (milliseconds)", "App bar buttons",
                "Keep screen on", "Fullscreen mode", "Center align", "Show button time", "Card zoom", "Image zoom",
                "Answer button size", "Answer buttons position", "Show Top bar", "Show remaining", "Show ETA",
                "Show large answer buttons", "Stroke width", "Automatic display answer", "Timeout answer", "Time to show answer",
                "Time to show next question", "Day theme", "Night theme", "Choose an Image", "Default font", "Default font applicability",
                "Browser end editor font", "Card browser font scaling", "Display filenames in card browser", "Enable gestures",
                "Ignore touch gestures on links", "9-point touch", "Full screen navigation drawer", "Swipe sensitivity", "Swipe up",
                "Swipe down", "Swipe left", "Swipe right", "Double touch", "Touch top left", "Touch top right", "Touch top", "Touch left",
                "Touch middle center", "Touch right", "Touch bottom left", "Touch bottom", "Touch bottom right", "Volume up", "Volume down",
                "AnkiDroid directory", "Force full sync", "Custom sync server", "Max number of backups", "Disable card hardware render",
                "Safe display mode", "Type answer into the card", "Disable Single-Field Edit Mode", "Replace newlines with HTML",
                "Simple typed answer formatting", "Focus 'type in answer'", "Press back twice to go back/exit", "Allow all files in media imports",
                "Enable AnkiDroid API", "Third-party API apps", "Text to speech", "Lookup dictionary", "Reset languages", "Chess notation support",
                "eReader", "Double scrolling", "Advanced statistics", "HTML / Javascript Debugging", "About AnkiDroid"};
        mFragmentMap = new HashMap<String, String>();
        for (int i = 0; i < options.length; i++) {
            if ( i < 12) {
                // Map to General Preferences
                mFragmentMap.put(options[i], "General Preferences");
            } else if (i < 36) {
                // Map to Reviewing Preferences
                mFragmentMap.put(options[i], "Reviewing Preferences");
            } else if (i < 44) {
                // Map to Appearance Preferences
                mFragmentMap.put(options[i], "Appearance Preferences");
            } else if (i < 65) {
                // Map to Gesture Preferences
                mFragmentMap.put(options[i], "Gesture Preferences");
            } else {
                // Map to Advanced Preferences
                mFragmentMap.put(options[i], "Advanced Preferences");
            }
        }
    }
}
