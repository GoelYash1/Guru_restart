package com.axyz.upasthithguru.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.axyz.upasthithguru.R
import com.axyz.upasthithguru.databinding.ActivityMainBinding
import com.axyz.upasthithguru.other.OnboardingItem
import com.axyz.upasthithguru.other.OnboardingItemsAdapter

class OnboardingActivity : AppCompatActivity() {

    private lateinit var onboardingItemsAdapter: OnboardingItemsAdapter
    private lateinit var binding: ActivityMainBinding
    private lateinit var indicatorsContainer: LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setOnboardingItems()
        setupIndicators()
        setCurrentIndicator(0)
    }

    private fun setOnboardingItems(){
        onboardingItemsAdapter = OnboardingItemsAdapter(
            listOf(
                OnboardingItem(
                    onBgImage = R.drawable.onbg1,
                    onboardingImage = R.drawable.onclassroom,
                    title = "Easy Attendance Marking System",
                    description = "Mark the attendance of your class via bluetooth within just seconds"
                ),
                OnboardingItem(
                    onBgImage = R.drawable.onbg2,
                    onboardingImage = R.drawable.onreport,
                    title = "Track Your Students' Attendance",
                    description = "Keep track of your students' attendance with a trouble-free data system"
                ),
                OnboardingItem(
                    onBgImage = R.drawable.onbg3,
                    onboardingImage = R.drawable.onschedule,
                    title = "Manage Your Lectures' Schedule",
                    description = "Schedule and manage all your lectures effortlessly"
                )

            )
        )
        val onboardingViewPager:ViewPager2 = binding.onboardingViewPager
        onboardingViewPager.adapter = onboardingItemsAdapter
        onboardingViewPager.registerOnPageChangeCallback(object:
            ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)
            }
        })
        (onboardingViewPager.getChildAt(0) as RecyclerView).overScrollMode =
            RecyclerView.OVER_SCROLL_NEVER
        binding.imageNext.setOnClickListener {
            if(onboardingViewPager.currentItem+1<onboardingItemsAdapter.itemCount){
                onboardingViewPager.currentItem+=1
            }
            else{
                navigateToHomeActivity()
            }
        }
//        findViewById<TextView>(R.id.textSkip).setOnClickListener {
//            navigateToHomeActivity()
////        }
//        findViewById<MaterialButton>(R.id.btn_get_start).setOnClickListener {
//            navigateToHomeActivity()
//        }
    }
    private fun navigateToHomeActivity(){
        startActivity(Intent(applicationContext, EntryActivity::class.java))
//        finish()
    }

    private fun setupIndicators(){
        indicatorsContainer = binding.indicatorsContainer
        val indicators = arrayOfNulls<ImageView>(onboardingItemsAdapter.itemCount)
        val layoutParams: LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        layoutParams.setMargins(8,0,8,0)
        for(i in indicators.indices){
            indicators[i] = ImageView(applicationContext)
            indicators[i]?.let {
                it.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactive_background
                    )
                )
                it.layoutParams = layoutParams
                indicatorsContainer.addView(it)
            }
        }
    }

    private fun setCurrentIndicator(position: Int){
        val childCount = indicatorsContainer.childCount
        for(i in 0 until childCount){
            val imageView = indicatorsContainer.getChildAt(i) as ImageView
            if(i == position){
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext, R.drawable.indicator_active_background
                    )
                )
            }
            else{
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactive_background
                    )
                )
            }
        }
    }
}