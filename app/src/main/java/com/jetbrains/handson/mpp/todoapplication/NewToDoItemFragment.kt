package com.jetbrains.handson.mpp.todoapplication

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.EditText
import androidx.fragment.app.DialogFragment

class NewToDoItemFragment: DialogFragment() {

//    declare an interface with two methods
    interface NewToDoDialogListener {

        fun onAddClick(dialog: DialogFragment, toDoItem: String)

        fun onCancelClick(dialog: DialogFragment)
    }

    private var newToDoDialogListener: NewToDoDialogListener? = null

//    It defines a method, newInstance(), in a companion object.
//    By doing this, the method can be accessed without having to create an instance of the NewToDoItemFragment class.
//    The newInstance() method does the following:
    companion object {

    //  It takes an Int parameter named title
    // It creates an instance of the NewToDoItemFragment and passes the title as part of its arguments
    // It returns the new instance of the NewToDoItemFragment
        fun newInstance(title: Int): NewToDoItemFragment {

            val newToDoDialogFragment = NewToDoItemFragment()
            val args = Bundle()
            args.putInt("dialog_title", title)
            newToDoDialogFragment.arguments = args

            return newToDoDialogFragment
        }
    }

//  override the onCreateDialog() method
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

//      try to retrieve the title argument passed
//      instantiate an AlertDialog builder and assign the retrieved title to the dialog’s title
        val title = arguments?.getInt("dialog_title")
        val builder = AlertDialog.Builder(activity)

        if (title != null) {

            builder.setTitle(title)
        }

//      use the LayoutInflater of the DialogFragment instance’s parent activity to inflate the layout
//      set the inflated view as the dialog’s view
        val dialogView = activity?.layoutInflater?.inflate(R.layout.new_todo_item_dialog, null)
        val toDoItem = dialogView?.findViewById<EditText>(R.id.ToDoItem)

//      set two buttons to the dialog: Add and Cancel

        builder.setView(dialogView).setPositiveButton(R.string.add) { _, _ ->

            if (toDoItem != null) {
                newToDoDialogListener?.onAddClick(this, toDoItem.text.toString())
            }
        }

            .setNegativeButton(android.R.string.cancel) { _, _ ->

                newToDoDialogListener?.onCancelClick(this)
            }

        return builder.create()
    }

//  try to assign the Context object passed to the newToDoDialogListener variable.
//  For this to work, the Activity object should implement the NewToDoDialogListener interface.
    override fun onAttach(context: Context) {

        super.onAttach(context)

        try {
            newToDoDialogListener = context as NewToDoDialogListener
        }

        catch (e: ClassCastException) {

            throw ClassCastException("$context must implement NewToDoDialogListener")
        }
    }
}