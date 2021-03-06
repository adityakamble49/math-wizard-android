package com.adityakamble49.mathwizard

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import com.adityakamble49.mathwizard.classifier.MathSymbolClassifier
import com.adityakamble49.mathwizard.utils.CustomFileUtils
import com.google.android.gms.tasks.OnSuccessListener
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Main Activity
 *
 * @author Aditya Kamble
 * @since 12/20/2019
 */
class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    private val mathSymbolClassifier = MathSymbolClassifier(this)
    private lateinit var RESULT_MAP: Map<String, String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Setup Actionbar
        setSupportActionBar(main_toolbar)

        // Setup Draw View
        draw_view?.setStrokeWidth(70.0f)
        draw_view?.setColor(Color.WHITE)
        draw_view?.setBackgroundColor(Color.BLACK)

        // Setup Clear Canvas FAB
        fab_reset_canvas.setOnClickListener {
            draw_view?.clearCanvas()
            tv_math_symbol_predicted.setText(R.string.draw_math_symbol)
        }

        // Setup Result Map
        RESULT_MAP =
            CustomFileUtils.jsonToMap(CustomFileUtils.loadJSONFromAsset(this))

        draw_view?.setOnTouchListener { _, event ->
            draw_view?.onTouchEvent(event)
            if (event.action == MotionEvent.ACTION_UP) {
                classifyDrawing()
            }
            true
        }

        // Setup Math Symbol Classifier
        mathSymbolClassifier.initializeInterpreterTask().addOnFailureListener {
            Log.e(TAG, "Math Symbol Classifier Initialization Failed !")
        }
    }

    private fun classifyDrawing() {
        val drawnImageBitmap = draw_view?.getBitmap()

        if (drawnImageBitmap != null && mathSymbolClassifier.isInitialized) {
            mathSymbolClassifier.classifyInBackground(drawnImageBitmap)
                .addOnSuccessListener { resultText ->
                    tv_math_symbol_predicted?.text = RESULT_MAP[resultText]
                }
                .addOnFailureListener { e ->
                    tv_math_symbol_predicted?.text = "Classification Error"
                    Log.e(TAG, "Error classifying drawing.", e)
                }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_about -> startActivity(Intent(this, AboutActivity::class.java))
        }
        return true
    }

    override fun onDestroy() {
        mathSymbolClassifier.closeClassifier()
        super.onDestroy()
    }
}
