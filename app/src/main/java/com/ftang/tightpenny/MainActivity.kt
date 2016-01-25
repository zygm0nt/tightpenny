package com.ftang.tightpenny

import android.content.Context
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.DialogFragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
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

    override protected fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        JodaTimeAndroid.init(this)
        CalligraphyConfig.initDefault(CalligraphyConfig.Builder()
                //.setDefaultFontPath("fonts/Wolf_in_the_City.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        )

        setContentView(R.layout.activity_main)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { view ->
            showNoticeDialog()
        }

        listAdapter = ExpandableAggregateSpendingEntryAdapter(this, fetchSpendings().toArrayList())
        val listview = findViewById(R.id.summaryView) as ExpandableListView
        listview.setAdapter(listAdapter)

        //listview.onItemLongClickListener = S(listview, listAdapter!!)
        listview.setOnChildClickListener (
                fun (expandableListView: AdapterView<*>, view: View, groupPosition: Int, childPosition: Int, id: Long): Boolean {
                    view.animate().setDuration(2000).alpha(0.0f)
                            .withEndAction({
                                fun run(): Unit {
                                    val entry = expandableListView.getItemAtPosition(groupPosition) as AggregateSpendingEntry
                                    entry.entries.removeAt(childPosition)
                                    listAdapter!!.notifyDataSetChanged();
                                    view.setAlpha(1.0f);
                                }
                            })
                    return false
                }
        )
            /*parent: AdapterView<*>?, view: View?, position: Int, id: Long ->

                val packedPosition = listview.getExpandableListPosition(position)

                val itemType = ExpandableListView.getPackedPositionType(packedPosition)
                val groupPosition = ExpandableListView.getPackedPositionGroup(packedPosition)
                val childPosition = ExpandableListView.getPackedPositionChild(packedPosition)

                if (itemType == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
                    //onChildLongClick(groupPosition, childPosition);
                    view!!.animate().setDuration(2000).alpha(0.0f)
                            .withEndAction({
                                fun run(): Unit {
                                    val entry = parent!!.getItemAtPosition(groupPosition) as AggregateSpendingEntry
                                    entry.entries.removeAt(childPosition)
                                    listAdapter!!.notifyDataSetChanged();
                                    view.setAlpha(1.0f);
                                }
                            })

                }

                return false*/

        //listview.setOnItemLongClickListener(AdapterView.OnItemLongClickListener() {

            /*fun onItemLongClick(parent: AdapterView<?>, view: View, position: Int , id: Long): Boolean {

                long packedPosition = m_expandableListView.getExpandableListPosition(position);

                int itemType = ExpandableListView.getPackedPositionType(packedPosition);
                int groupPosition = ExpandableListView.getPackedPositionGroup(packedPosition);
                int childPosition = ExpandableListView.getPackedPositionChild(packedPosition);


                *//*  if group item clicked *//*
                if (itemType == ExpandableListView.PACKED_POSITION_TYPE_GROUP) {
                    //  ...
                    onGroupLongClick(groupPosition);
                }

                *//*  if child item clicked *//*
                else if (itemType == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
                    //  ...
                    onChildLongClick(groupPosition, childPosition);
                }


                return false;
            }*/
        listview.onItemClickListener = AdapterView.OnItemClickListener() {
            parent: AdapterView<*>?, view: View?, position: Int, id: Long ->

                val item = parent!!.getItemAtPosition(position) as AggregateSpendingEntry
                view!!.animate().setDuration(2000).alpha(0.0f)
                        .withEndAction({
                            fun run(): Unit {
                                //values.remove(item);
                                listAdapter!!.notifyDataSetChanged();
                                view.setAlpha(1.0f);
                            }
                        })
        }
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
}
