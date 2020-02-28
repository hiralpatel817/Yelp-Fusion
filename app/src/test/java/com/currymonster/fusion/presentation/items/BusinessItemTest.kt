package com.currymonster.fusion.presentation.items

import com.currymonster.fusion.R
import com.currymonster.fusion.Util
import com.currymonster.fusion.presentation.fragment.home.HomeViewModel
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import kotlin.test.assertEquals


class BusinessItemTest {

    private lateinit var viewModel: HomeViewModel

    @Before
    fun initMocks() {
        MockitoAnnotations.initMocks(this)
        viewModel = Mockito.mock(HomeViewModel::class.java)
    }

    @Test
    fun testRatingSwitch() {
        val item = BusinessItem(
            viewModel,
            Util.randomBusinessGenerator()
        )

        val drawableFourHalf = item.getRatingDrawable(4.5)
        assertEquals(R.drawable.stars_4_5, drawableFourHalf)

        val drawableThreeHalf = item.getRatingDrawable(3.5)
        assertEquals(R.drawable.stars_3_5, drawableThreeHalf)

        val drawableFive = item.getRatingDrawable(5.0)
        assertEquals(R.drawable.stars_5, drawableFive)

        val drawableEmpty = item.getRatingDrawable(3.2)
        assertEquals(0, drawableEmpty)
    }
}