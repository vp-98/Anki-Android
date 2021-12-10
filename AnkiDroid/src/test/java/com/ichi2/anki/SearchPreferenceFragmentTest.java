/****************************************************************************************
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
 * this program. If not, see <http://www.gnu.org/licenses/>.                            *
 ****************************************************************************************/

package com.ichi2.anki;


import org.junit.Test;
import org.junit.runner.RunWith;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.mock;

import androidx.appcompat.widget.SearchView;
import androidx.test.ext.junit.runners.AndroidJUnit4;

@RunWith(AndroidJUnit4.class)
public class SearchPreferenceFragmentTest extends RobolectricTest {

    /**
     * testGenerateMap
     *  This tests to see if the map has correct key and value pairs using some sample option choices.
     */
    @Test
    public void testGenerateMap() {
        SearchPreferenceFragment searchFrag = new SearchPreferenceFragment(mock(SearchView.class));
        searchFrag.generateMap();
        // Check to see if the correct mapping exists
        String[] options1 = {"AnkiWeb account", "Fetch media on sync", "Automatic synchronization"};
        String[] options2 = {"Force full sync", "Custom sync server", "Max number of backups"};
        String[] options3 = {"Swipe up", "Enable gestures", "Volume up"};

        for (String option : options1) {
            assertEquals(searchFrag.mFragmentMap.get(option), "General Preferences");
        }
        for (String option : options2) {
            assertEquals(searchFrag.mFragmentMap.get(option), "Advanced Preferences");
        }
        for (String option : options3) {
            assertEquals(searchFrag.mFragmentMap.get(option), "Gesture Preferences");
        }
    }


    /**
     * testView
     *  Test to see if the list view is correctly added for the the search view.
     */
    @Test
    public void testView() {
        SearchPreferenceFragment searchFrag = new SearchPreferenceFragment(mock(SearchView.class));
        assertNotEquals(searchFrag.getView().findViewById(R.id.search_result_listview), null);
    }

}
