package com.vdlasov.materialbottomsheet

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var bs: BottomSheetBehavior<View>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bs = BottomSheetBehavior.from(vBSBehavior)
        bs.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                vBSSlide.text = slideOffset.toString()
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                vBSCurrentState.text = when (newState) {
                    BottomSheetBehavior.STATE_DRAGGING -> "The bottom sheet is dragging."
                    BottomSheetBehavior.STATE_SETTLING -> "The bottom sheet is settling."
                    BottomSheetBehavior.STATE_EXPANDED -> "The bottom sheet is expanded."
                    BottomSheetBehavior.STATE_COLLAPSED -> "The bottom sheet is collapsed."
                    BottomSheetBehavior.STATE_HIDDEN -> "The bottom sheet is hidden."
                    BottomSheetBehavior.STATE_HALF_EXPANDED -> "The bottom sheet is half-expanded (used when mFitToContents is false)."
                    else -> "none"
                }
            }
        })

        fun animateBS(changeHeight: () -> Unit) {
            // custom animation
            val transition = AutoTransition()
            transition.addTarget(vBSBehavior)
            transition.interpolator = FastOutSlowInInterpolator()
            transition.duration = 600L
            TransitionManager.beginDelayedTransition(vCoordinator, transition)

            // simple animation
            // comment above code, uncomment below line
            //TransitionManager.beginDelayedTransition(vCoordinator)
            changeHeight()
            TransitionManager.endTransitions(vBSBehavior)
        }

        bSmallHeight.setOnClickListener {
            animateBS {
                tvFirstPart.isVisible = !tvFirstPart.isVisible
            }
        }

        bMediumHeight.setOnClickListener {
            animateBS {
                tvSecondPart.isVisible = !tvSecondPart.isVisible
            }
        }

        bBigHeight.setOnClickListener {
            animateBS {
                tvThirdPart.isVisible = !tvThirdPart.isVisible
            }
        }
    }

}