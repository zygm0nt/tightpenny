package com.ftang.tightpenny.dialog

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.text.SpannableStringBuilder
import android.widget.EditText
import android.widget.Spinner
import com.ftang.tightpenny.R
import java.math.BigDecimal

class NoticeDialogFragment : DialogFragment() {

    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    interface NoticeDialogListener {
        fun onDialogPositiveClick(dialog: DialogFragment, category: String, amount: BigDecimal)
        fun onDialogNegativeClick(dialog: DialogFragment)
    }

    // Use this instance of the interface to deliver action events
    internal var mListener: NoticeDialogListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(activity)

        val that = this
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(activity.layoutInflater.inflate(R.layout.new_spending_dialog, null))
                // Add action buttons
                .setPositiveButton(R.string.signin, { dialog, id ->
                    val dialogView = that.dialog
                    val categorySpinner = dialogView.findViewById(R.id.category) as Spinner
                    val amountET = dialogView.findViewById(R.id.amount) as EditText

                    val category = categorySpinner.getSelectedItem().toString()
                    val amount = BigDecimal((amountET.text as SpannableStringBuilder).toString())
                    mListener?.onDialogPositiveClick(that, category, amount)
                })
                .setNegativeButton(R.string.cancel, { dialog, id ->
                    dialog.cancel()
                    mListener?.onDialogNegativeClick(that)
                })
        return builder.create()
    }

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = activity as NoticeDialogListener?
        } catch (e: ClassCastException) {
            // The activity doesn't implement the interface, throw exception
            throw ClassCastException(activity!!.toString() + " must implement NoticeDialogListener")
        }

    }
}