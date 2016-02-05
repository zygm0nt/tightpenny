package com.ftang.tightpenny

import android.content.Context
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.DialogFragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.text.Spannable
import android.text.SpannableString
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ExpandableListView
import android.widget.ListView
import com.ftang.tightpenny.dialog.NoticeDialogFragment
import com.ftang.tightpenny.model.AggregateSpendingEntry
import com.ftang.tightpenny.model.Category
import com.ftang.tightpenny.model.SpendingEntryRepository
import net.danlew.android.joda.JodaTimeAndroid
import org.joda.time.DateTime
import uk.co.chrisjenx.calligraphy.CalligraphyConfig
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import java.math.BigDecimal
import java.util.*

/**
 * A lot about ListView is from here: http://www.vogella.com/tutorials/AndroidListView/article.html
 */
class MainActivity : AppCompatActivity(), NoticeDialogFragment.NoticeDialogListener {

    val entryRepository = SpendingEntryRepository()
    var listAdapter: ExpandableAggregateSpendingEntryAdapter? = null

    private val LOG_TAG = "Main_Activity"

    override protected fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        JodaTimeAndroid.init(this)
        CalligraphyConfig.initDefault(CalligraphyConfig.Builder()
                .setFontAttrId(R.attr.fontPath)
                .build()
        )

        setContentView(R.layout.activity_main)

        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        stylingActionBar()

        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { view ->
            showNoticeDialog()
        }

        listAdapter = ExpandableAggregateSpendingEntryAdapter(this, fetchSpendings().toArrayList())
        val listview = findViewById(R.id.summaryView) as ExpandableListView
        listview.setAdapter(listAdapter)
    }

    private fun fetchSpendings(): List<AggregateSpendingEntry> {
        val now = DateTime.now()
        return entryRepository.getMonthSpendings(now.year, now.monthOfYear)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_cleardb -> entryRepository.clearDb()

            else ->  return super.onOptionsItemSelected(item)
        }
        return true
    }

    fun  showNoticeDialog() {
        val dialog = NoticeDialogFragment()
        val fragmentManager = supportFragmentManager
        if (fragmentManager != null) {
            dialog.show(fragmentManager, "NoticeDialogFragment")
        }
    }

    override fun onDialogPositiveClick(dialog: DialogFragment, category: Category, amount: BigDecimal) {
        val entry = entryRepository.addNewSpending(category, amount)
        listAdapter!!.addSpending(entry)
    }

    override fun onDialogNegativeClick(dialog: DialogFragment) {
    }

    private fun stylingActionBar() {
        val s = SpannableString("TightPenny");
        s.setSpan(TypefaceSpan(this, "Wolf_in_the_City.ttf"), 0, s.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Update the action bar title with the TypefaceSpan instance
        val actionBar = getSupportActionBar()
        actionBar.title = s
    }
}
