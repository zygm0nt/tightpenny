package com.ftang.tightpenny

import android.app.AlertDialog
import android.app.Dialog
import android.app.FragmentManager
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.app.DialogFragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import com.ftang.tightpenny.dialog.FireMissilesDialogFragment
import com.ftang.tightpenny.dialog.NoticeDialogFragment
import com.ftang.tightpenny.model.SpendingEntryRepository
import net.danlew.android.joda.JodaTimeAndroid
import java.math.BigDecimal
import java.util.*

class MainActivity : AppCompatActivity(), NoticeDialogFragment.NoticeDialogListener {

    val entryRepository = SpendingEntryRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        JodaTimeAndroid.init(this)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { view ->
            showNoticeDialog()
        }

        val listview = findViewById(R.id.summaryView) as ListView

        val adapter = MySimpleArrayAdapter(this, entryRepository.getMonthSpendings(2015, 23));
        listview.adapter = adapter

        listview.onItemClickListener = AdapterView.OnItemClickListener() {
            parent: AdapterView<*>?, view: View?, position: Int, id: Long ->

                val item = parent!!.getItemAtPosition(position) as String
                view!!.animate().setDuration(2000).alpha(0.0f)
                        .withEndAction({
                            fun run(): Unit {
                                //values.remove(item);
                                adapter.notifyDataSetChanged();
                                view.setAlpha(1.0f);
                            }
                        })
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    fun  showNoticeDialog() {
        val dialog = NoticeDialogFragment()
        val fragmentManager = supportFragmentManager
        if (fragmentManager != null) {
            dialog.show(fragmentManager, "NoticeDialogFragment")
        }
    }

    override fun onDialogPositiveClick(dialog: DialogFragment, category: String, amount: BigDecimal) {
        Log.i("TightPenny", "Got $amount in $category")
    }

    override fun onDialogNegativeClick(dialog: DialogFragment) {
    }

    class StableArrayAdapter(context: Context, textViewResourceId: Int, objects: List<String>) :
            ArrayAdapter<String>(context, textViewResourceId, objects) {

        val mIdMap = HashMap<String, Int>()

        init {
            for (i in 0 .. objects.size - 1) {
                mIdMap.put(objects.get(i), i)
            }
        }

        override fun getItemId(position: Int): Long {
            val item = getItem(position)
            return mIdMap.get(item)!!.toLong()
        }

        override fun hasStableIds(): Boolean {
            return true;
        }

    }
}
