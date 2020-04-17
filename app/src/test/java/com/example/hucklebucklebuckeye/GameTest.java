package com.example.hucklebucklebuckeye;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class GameTest {

    private Game mGame;
    private String mMessage;
    private int mStepCount;
    private int mCurrentColor;
    private boolean isWon;
    private int mCurrentTime;
    private Coordinates Lat;
    private Coordinates Long;
    private static Coordinates destinationLocation;

    @Before
    public void setUp() throws Exception {
        mMessage = "Hello there!";
        mStepCount = 13;
        mCurrentColor = 27;
        mCurrentTime = 12;
        isWon = true;
        Locations locations = new Locations();
        destinationLocation = locations.setLocation();
        mGame = new Game(destinationLocation);
        mGame.setMessage(mMessage);
        mGame.setStepCount(mStepCount);
        mGame.setCurrentColor(mCurrentColor);
        mGame.setTime(mCurrentTime);
        mGame.updateWin();
    }

    @Test
    public void checksMessage() {
        assertThat(mGame.getMessage(), is(mMessage));
    }

    @Test
    public void checksStepCount() {
        assertThat(mGame.getStepCount(), is(mStepCount));
    }

    @Test
    public void checkCurrentColor() {
        assertThat(mGame.getCurrentColor(), is(mCurrentColor));
    }

    @Test
    public void checkCurrentTime() {
        assertThat(mGame.getTime(), is(mCurrentTime));
    }

    @Test
    public void checkStatus() {
        assertThat(mGame.status(), is(isWon));
    }


}