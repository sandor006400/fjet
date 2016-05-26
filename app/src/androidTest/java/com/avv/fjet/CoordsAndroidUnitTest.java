package com.avv.fjet;

import android.support.test.runner.AndroidJUnit4;
import android.test.InstrumentationTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import org.avv.fjet.core.board.HexCoords;
import org.avv.fjet.core.board.SquareCoords;
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
public class CoordsAndroidUnitTest extends InstrumentationTestCase {

    // region - Constants

    // endregion - Constants

    // region - Fields

    private HexCoords hexCoords;
    private SquareCoords squareCoords;

    // endregion - Fields

    // region - Methods

    @Before
    public void createCoords(){
        hexCoords = new HexCoords(3, 4);
        squareCoords = new SquareCoords(3, 4);
    }

    @Test
    public void hexCoords_jsonSerialization(){
        String jsonCoords = hexCoords.toJsonString();
        HexCoords newCoords = new HexCoords(jsonCoords);

        assertThat(hexCoords, is(newCoords));
    }

    @Test
    public void squareCoords_jsonSerialization(){
        String jsonCoords = squareCoords.toJsonString();
        SquareCoords newCoords = new SquareCoords(jsonCoords);

        assertThat(squareCoords, is(newCoords));
    }

    // endregion - Methods

    // region - Inner and Anonymous Classes

    // endregion - Inner and Anonymous Classes

}
