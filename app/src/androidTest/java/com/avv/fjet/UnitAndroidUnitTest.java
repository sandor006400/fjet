package com.avv.fjet;

import android.support.test.runner.AndroidJUnit4;
import android.test.InstrumentationTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import org.avv.fjet.core.unit.Unit;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by Alexander Vorobiev on 26/05/16.
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class UnitAndroidUnitTest extends InstrumentationTestCase {

    // region - Constants

    // endregion - Constants

    // region - Fields

    private Unit unit;

    // endregion - Fields

    // region - Methods

    @Before
    public void createUnit(){
        unit = new Unit();
    }

    @Test
    public void unit_jsonSerialization(){
        String json = unit.toJsonString();
        Unit newUnit = new Unit(json);

        //assertThat(unit, is(newUnit));
        assertEquals(unit, newUnit);
    }

    // endregion - Methods

    // region - Inner and Anonymous Classes

    // endregion - Inner and Anonymous Classes

}
