package com.monzo.androidtest.articles;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ArticlesActivityTest {
    @Rule
    public ActivityTestRule<ArticlesActivity> testRule = new ActivityTestRule<>(ArticlesActivity.class, true, false);

    @Test
    public void test() throws Exception {
        testRule.launchActivity(null);
    }
}