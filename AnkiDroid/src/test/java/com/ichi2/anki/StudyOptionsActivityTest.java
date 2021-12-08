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

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.ActionProvider;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;

import com.ichi2.anki.dialogs.customstudy.CustomStudyDialog;

import static android.os.Looper.getMainLooper;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.robolectric.Shadows.shadowOf;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.Robolectric;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import timber.log.Timber;

@RunWith(AndroidJUnit4.class)
public class StudyOptionsActivityTest extends RobolectricTest{
    private StudyOptionsActivity mMockStudyOptionsActivity = new StudyOptionsActivity();
    private StudyOptionsActivity mMockStudyOptionsActivity2 = new StudyOptionsActivity();

    @Override
    public void setUp() {
        mMockStudyOptionsActivity = new StudyOptionsActivity();
        mMockStudyOptionsActivity.mOptionsFragment = mock(StudyOptionsFragment.class);


                //new StudyOptionsActivity()
       // mMockStudyOptionsActivity2 = mock(StudyOptionsActivity.class);
        //mMockStudyOptionsActivity2.loadStudyOptionsFragment() = mock(StudyOptionsActivity.loadStudyOptionsFragment());


        super.setUp();

    }


    @Override
    @After
    public void tearDown() {

        super.tearDown();
    }

    @Test
    public void testUndo(){


        MenuItem item = mock(MenuItem.class);
        when(item.getItemId()).thenReturn(R.id.action_undo);

        Timber.i("test logging 1 "+String.valueOf(item.getItemId()));
        Timber.i("test logging 2 "+String.valueOf(R.id.action_undo));

        Timber.i("test logging 4 "+String.valueOf(item.getItemId()==R.id.action_undo));
        shadowOf(getMainLooper()).idle();

        boolean result = mMockStudyOptionsActivity.onOptionsItemSelected(item);
        assertEquals(true, result);
    }

    @Test
    public void testLoadingStudyOptions(){

        mMockStudyOptionsActivity2 = Robolectric.buildActivity(StudyOptionsActivity.class).create(null).start().resume().get();
        assertNotEquals(mMockStudyOptionsActivity2.mOptionsFragment, null);

    }
}
