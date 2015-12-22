package com.ftang.tightpenny

import android.app.AlertDialog
import android.app.Dialog
import android.app.FragmentManager
import android.content.DialogInterface
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.app.DialogFragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import com.ftang.tightpenny.dialog.FireMissilesDialogFragment
import com.ftang.tightpenny.dialog.NoticeDialogFragment
import net.danlew.android.joda.JodaTimeAndroid

class MainActivity : AppCompatActivity(), NoticeDialogFragment.NoticeDialogListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        JodaTimeAndroid.init(this)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { view ->
            //onCreateDialog().show()
            showNoticeDialog()
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

    private fun createAlertDialog(): AlertDialog {
        val builder = AlertDialog.Builder(this);

        // 2. Chain together various setter methods to set the dialog characteristics
        builder.setMessage(R.string.dialog_message)
                .setTitle(R.string.dialog_title)

        builder.setPositiveButton(R.string.ok, DialogInterface.OnClickListener() { dialog, id ->
        })
        builder.setNegativeButton(R.string.cancel, DialogInterface.OnClickListener() { dialog, id ->

        })
        // 3. Get the AlertDialog from create()
        val dialog = builder.create();

        return dialog
    }

    fun onCreateDialog(): Dialog {
        val builder = AlertDialog.Builder(this)
        // Get the layout inflater
        val inflater = this.getLayoutInflater()

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.new_spending_dialog, null))
                // Add action buttons
                .setPositiveButton(R.string.signin, DialogInterface.OnClickListener() { dialog, id ->

                })
                .setNegativeButton(R.string.cancel, DialogInterface.OnClickListener() { dialog, id ->
                    dialog.cancel()
                });
        return builder.create()
    }

    fun  showNoticeDialog() {
        val dialog = NoticeDialogFragment()
        val fragmentManager = supportFragmentManager
        if (fragmentManager != null) {
            dialog.show(fragmentManager, "NoticeDialogFragment")
        }
    }

    override fun onDialogPositiveClick(dialog: DialogFragment?) {
        throw UnsupportedOperationException()
    }

    override fun onDialogNegativeClick(dialog: DialogFragment?) {
        throw UnsupportedOperationException()
    }

}
