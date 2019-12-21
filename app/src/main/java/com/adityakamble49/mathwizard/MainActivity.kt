package com.adityakamble49.mathwizard

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Main Activity
 *
 * @author Aditya Kamble
 * @since 12/20/2019
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Setup Draw View
        draw_view?.setStrokeWidth(70.0f)
        draw_view?.setColor(Color.WHITE)
        draw_view?.setBackgroundColor(Color.BLACK)


        draw_view?.setOnTouchListener { _, event ->
            draw_view?.onTouchEvent(event)
            if (event.action == MotionEvent.ACTION_UP) {
                classifyDrawing()
            }
            true
        }
    }

    private fun classifyDrawing() {

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
