package com.example.warp.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.warp.R
import com.example.warp.databinding.FragmentModelcategoryBinding
import com.example.warp.models.ImageItem
import com.example.warp.models.SliderItem
import com.example.warp.adapters.ImageAdapter
import com.example.warp.adapters.SliderAdapter
import java.util.UUID

class DashboardFragment : Fragment() {

    private var _binding: FragmentModelcategoryBinding? = null
    private val binding get() = _binding!!

    private lateinit var seepager2: ViewPager2
    private lateinit var pageChangeListener: ViewPager2.OnPageChangeCallback
    private val params = LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.WRAP_CONTENT,
        LinearLayout.LayoutParams.WRAP_CONTENT
    ).apply {
        setMargins(8,  0,  8,  0)
    }

    private var viewPager2: ViewPager2? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentModelcategoryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        seepager2 = binding.otherCategoryViewPager
        viewPager2 = binding.suggestionViewPager

        val images = arrayListOf(
            ImageItem(
                UUID.randomUUID().toString(),
                R.drawable.cat1
            ),
            ImageItem(
                UUID.randomUUID().toString(),
                R.drawable.cat2
            ),
            ImageItem(
                UUID.randomUUID().toString(),
                R.drawable.cat3
            ),
            ImageItem(
                UUID.randomUUID().toString(),
                R.drawable.cat4
            ),
            ImageItem(
                UUID.randomUUID().toString(),
                R.drawable.cat5
            )
        )

        // Initialize your ImageAdapter with the images list
        val imageAdapter = ImageAdapter()
        imageAdapter.submitList(images)

        // Set the ImageAdapter to your first ViewPager2 instance
        seepager2.adapter = imageAdapter

        // Assuming you have a list of SliderItem objects for the second ViewPager2
        val sliderItems = listOf(
            SliderItem(R.drawable.image1),
            SliderItem(R.drawable.image2),
            SliderItem(R.drawable.image3)
        )// ... initialize your list of SliderItem objects here ...

        // Initialize your SliderAdapter with the sliderItems list and the second ViewPager2 instance
        val sliderAdapter = SliderAdapter(sliderItems, viewPager2!!)

        // Set the SliderAdapter to your second ViewPager2 instance
        viewPager2?.adapter = sliderAdapter

        val slideDotLL = binding.slideDotLL
        val dotsImage = Array(images.size) { ImageView(requireContext()) }

        dotsImage.forEach {
            it.setImageResource(
                R.drawable.non_active_dot
            )
            slideDotLL.addView(it, params)
        }
        //default Dot selected
        dotsImage[0].setImageResource(R.drawable.active_dot)
        pageChangeListener = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                dotsImage.mapIndexed { index, imageView ->
                    if (position == index) {
                        imageView.setImageResource(
                            R.drawable.active_dot
                        )
                    } else {
                        imageView.setImageResource(R.drawable.non_active_dot)
                    }
                }
                super.onPageSelected(position)
            }
        }
        seepager2.registerOnPageChangeCallback(pageChangeListener)

        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        seepager2.unregisterOnPageChangeCallback(pageChangeListener)
        _binding = null
    }
}
