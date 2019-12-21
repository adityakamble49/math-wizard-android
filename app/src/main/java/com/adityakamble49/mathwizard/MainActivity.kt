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
            math_symbol_predicted.setText(R.string.draw_math_symbol)
        }

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
            mathSymbolClassifier.classify(drawnImageBitmap)
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
}
