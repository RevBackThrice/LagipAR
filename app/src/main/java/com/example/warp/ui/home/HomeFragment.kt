package com.example.warp.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.warp.databinding.FragmentModelsBinding

class HomeFragment : Fragment() {
    private var binding: FragmentModelsBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentModelsBinding.inflate(inflater, container, false)
        val root = binding!!.root

        // Set up the button click listeners
        binding!!.buttonModelOne.setOnClickListener { v -> openComparatorWithModel("neco_arc.glb") }
        binding!!.buttonModelTwo.setOnClickListener { v -> openComparatorWithModel("Gangsa.glb") }
        binding!!.buttonModelThree.setOnClickListener { v -> openComparatorWithModel("Pateteg.glb") }
        binding!!.buttonModelFour.setOnClickListener { v -> openComparatorWithModel("Tongatong.glb") }
        binding!!.buttonModelFive.setOnClickListener { v -> openComparatorWithModel("Flute_1.glb") }
        binding!!.buttonModelSix.setOnClickListener { v -> openComparatorWithModel("Unknown1.glb") }

        return root
    }

    private fun openComparatorWithModel(modelName: String) {
        val intent = Intent(context, Comparator::class.java)
        intent.putExtra("MODEL_NAME", modelName)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}