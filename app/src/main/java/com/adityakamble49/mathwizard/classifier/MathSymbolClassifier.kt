package com.adityakamble49.mathwizard.classifier

import android.content.Context
import android.graphics.Bitmap
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks.call
import org.tensorflow.lite.Interpreter
import java.util.concurrent.Callable
import java.util.concurrent.Executors

class MathSymbolClassifier(private val context: Context) {

    companion object {
        private const val TAG = "MathSymbolClassifier"
        private const val MODEL_FILE = "math_symbols_model.tflite"
        private const val FLOAT_TYPE_SIZE = 4
        private const val PIXEL_SIZE = 1
        private const val OUTPUT_CLASSES_COUNT = 82
    }

    private var intrepreter: Interpreter? = null
    val isInitialized = false

    private val executorService = Executors.newCachedThreadPool()

    private var imageWidth = 0
    private var imageHeight = 0
    private var modelInputSize = 0

    fun initializeInterpreterTask(): Task<Void> {
        return call(executorService, Callable<Void> {
            initializeInterpreter()
            null
        })
    }

    private fun initializeInterpreter() {

    }

    fun classify(imageBitmap: Bitmap) {

    }
}