package com.ftang.tightpenny.model

import junit.framework.TestCase

public class JUnit3StringTest : TestCase() {

    protected override fun setUp() {
        // set up the test case
    }

    protected override fun tearDown() {
        // tear down the test case
    }

    fun testCategoryParsing() {
        assertEquals(Category.EatingOut, Category.valueOf("EatingOut"))
        assertEquals(Category.EatingOut, Category.valueOf(3))
    }
}

