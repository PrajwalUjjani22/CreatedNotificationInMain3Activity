package com.xyzTechnologies.helloworldapp;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    MainActivity mainActivity;
    @Test
    public void addition_isCorrect() {
        mainActivity = new MainActivity();
        assertEquals(4, mainActivity.addition(2,2));
    }
}