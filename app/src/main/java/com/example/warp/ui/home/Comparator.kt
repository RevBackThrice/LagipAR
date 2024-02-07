package com.example.warp.ui.home

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.example.warp.R
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.assets.RenderableSource
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.TransformableNode

class Comparator : AppCompatActivity() {
    private lateinit var renderable: ModelRenderable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modelone)

        val modelName =
            intent.getStringExtra("MODEL_NAME") ?: "neco_arc.glb" //default model if none are found
        loadModel(modelName)

    }

    private fun loadModel(modelName: String) {
        val renderableSource = RenderableSource.builder()
            .setSource(
                this,
                Uri.parse(modelName),
                RenderableSource.SourceType.GLB
            )
            .setRecenterMode(RenderableSource.RecenterMode.ROOT)
            .build()

        var renderable: ModelRenderable? = null

        ModelRenderable.builder()
            .setSource(
                this,
                renderableSource // This is the model we built earlier
            )
            .build()
            .thenAccept { modelRenderable ->
                renderable = modelRenderable // Store in a variable to reference later
            }
            .exceptionally { throwable ->
                // Log the exception or show an error message to the user
                Log.e("Comparator", "Error loading model, something went wrong.", throwable)
                null
            }

        val fragment = supportFragmentManager.findFragmentById(R.id.ux_fragment) as ArFragment

        fragment.setOnTapArPlaneListener { hitResult, _, _ ->
            val anchorNode = AnchorNode(hitResult.createAnchor())
            anchorNode.setParent(fragment.arSceneView.scene)

            val transformableNode = TransformableNode(fragment.transformationSystem)
            transformableNode.renderable = renderable
            transformableNode.setParent(anchorNode)
            transformableNode.select()

            transformableNode.scaleController.minScale = 0.05f
            transformableNode.scaleController.maxScale = 1f

            transformableNode.setOnTapListener { _, _ ->
                val alertDialogBuilder = AlertDialog.Builder(this@Comparator)
                alertDialogBuilder.setTitle("Model Tapped")
                alertDialogBuilder.setMessage("The AR model has been successfully tapped.")

                alertDialogBuilder.setPositiveButton("OK") { dialog, _ ->
                    dialog.dismiss()
                }
                val alertDialog = alertDialogBuilder.create()
                alertDialog.show()
            }
        }
    }
}

