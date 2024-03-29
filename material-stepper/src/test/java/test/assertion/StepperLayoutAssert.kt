package test.assertion

import android.support.annotation.IdRes
import android.view.View
import android.widget.HorizontalScrollView
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2
import com.toybeth.dokto.stepper.R
import com.toybeth.dokto.stepper.StepperLayout
import com.toybeth.dokto.stepper.internal.util.AnimationUtil
import org.assertj.android.api.Assertions
import org.assertj.android.api.view.ViewAssert
import org.assertj.android.api.widget.AbstractLinearLayoutAssert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull

/**
 * @author Piotr Zawadzki
 */
class StepperLayoutAssert constructor(actual: StepperLayout) : AbstractLinearLayoutAssert<StepperLayoutAssert, StepperLayout>(actual, StepperLayoutAssert::class.java) {

    companion object {

        fun assertThat(actual: StepperLayout): StepperLayoutAssert {
            return StepperLayoutAssert(actual)
        }

    }

    fun hasHorizontalProgressBarShown(): StepperLayoutAssert {
        hasNotNullChildView(R.id.ms_stepProgressBar).isVisible
        return this
    }

    fun hasHorizontalProgressBarHidden(): StepperLayoutAssert {
        hasNotNullChildView(R.id.ms_stepProgressBar).isGone
        return this
    }

    fun hasDottedProgressBarShown(): StepperLayoutAssert {
        hasNotNullChildView(R.id.ms_stepDottedProgressBar).isVisible
        return this
    }

    fun hasDottedProgressBarHidden(): StepperLayoutAssert {
        hasNotNullChildView(R.id.ms_stepDottedProgressBar).isGone
        return this
    }

    fun hasTabsShown(): StepperLayoutAssert {
        hasNotNullChildView(R.id.ms_stepTabsContainer).isVisible
        return this
    }

    fun hasTabsHidden(): StepperLayoutAssert {
        hasNotNullChildView(R.id.ms_stepTabsContainer).isGone
        return this
    }

    fun hasBottomNavigationShown(): StepperLayoutAssert {
        hasNotNullChildView(R.id.ms_bottomNavigation).isVisible
        return this
    }

    fun hasBottomNavigationHidden(): StepperLayoutAssert {
        hasNotNullChildView(R.id.ms_bottomNavigation).isGone
        return this
    }

    fun hasBackButtonShown(): StepperLayoutAssert {
        hasNotNullChildView(R.id.ms_stepPrevButton).isVisible
        return this
    }

    fun hasBackButtonHidden(): StepperLayoutAssert {
        hasNotNullChildView(R.id.ms_stepPrevButton).isGone
        return this
    }

    fun hasNextButtonShown(): StepperLayoutAssert {
        hasNotNullChildView(R.id.ms_stepNextButton).isVisible
        return this
    }

    fun hasNextButtonHidden(): StepperLayoutAssert {
        hasNotNullChildView(R.id.ms_stepNextButton).isGone
        return this
    }

    fun hasCompleteButtonShown(): StepperLayoutAssert {
        hasNotNullChildView(R.id.ms_stepCompleteButton).isVisible
        return this
    }

    fun hasCompleteButtonHidden(): StepperLayoutAssert {
        hasNotNullChildView(R.id.ms_stepCompleteButton).isGone
        return this
    }

    fun hasTabNavigationEnabled(enabled: Boolean): StepperLayoutAssert {
        assertEquals("Incorrect tab navigation state", enabled, actual.isTabNavigationEnabled)
        return this
    }

    fun hasProgressMessageShown(message: String): StepperLayoutAssert {
        Assertions.assertThat(getProgressMessageView())
                .isNotNull
                .isVisible
                .hasAlpha(AnimationUtil.ALPHA_OPAQUE)
                .hasText(message)
        return this
    }

    fun hasProgressMessageHidden(): StepperLayoutAssert {
        Assertions.assertThat(getProgressMessageView())
                .isNotNull
                .isVisible
                .hasAlpha(AnimationUtil.ALPHA_INVISIBLE)
        return this
    }

    fun hasTabsScrollingContainerHidden(): StepperLayoutAssert {
        Assertions.assertThat(getTabsScrollingContainer())
                .isNotNull
                .isVisible
                .hasAlpha(AnimationUtil.ALPHA_INVISIBLE)
        return this
    }

    fun hasTabsScrollingContainerShown(): StepperLayoutAssert {
        Assertions.assertThat(getTabsScrollingContainer())
                .isNotNull
                .isVisible
                .hasAlpha(AnimationUtil.ALPHA_OPAQUE)
        return this
    }

    fun hasContentOverlayShown(): StepperLayoutAssert {
        Assertions.assertThat(getContentOverlay())
                .isNotNull
                .isVisible
                .hasAlpha(AnimationUtil.ALPHA_OPAQUE)
        return this
    }

    fun hasContentOverlayHidden(): StepperLayoutAssert {
        Assertions.assertThat(getContentOverlay())
                .isNotNull
                .isVisible
                .hasAlpha(AnimationUtil.ALPHA_INVISIBLE)
        return this
    }

    fun pagerHasAlpha(alpha: Float): StepperLayoutAssert {
        val pager: ViewPager2 = actual.findViewById(R.id.ms_stepPager)
        assertNotNull(pager)
        assertEquals(alpha, pager.alpha, 0.001f)
        return this
    }

    private fun getTabsScrollingContainer(): View? = actual.findViewById<HorizontalScrollView?>(R.id.ms_stepTabsScrollView)

    private fun getContentOverlay(): View? = actual.findViewById<HorizontalScrollView>(R.id.ms_stepPagerOverlay)

    private fun getProgressMessageView(): TextView = actual.findViewById(R.id.ms_stepTabsProgressMessage)

    private fun hasNotNullChildView(@IdRes childId: Int): ViewAssert {
        val child: View = actual.findViewById(childId)
        return Assertions.assertThat(child).isNotNull
    }
}
