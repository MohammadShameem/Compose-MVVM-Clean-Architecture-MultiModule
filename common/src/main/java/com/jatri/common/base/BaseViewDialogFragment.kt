package com.jatri.common.base

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar


abstract class BaseViewDialogFragment<V : ViewBinding> : DialogFragment(){
    private var _binding: V? = null
    protected val  binding get() = _binding!!
    protected abstract fun viewBindingLayout(): V
    protected abstract fun initializeView(savedInstanceState: Bundle?)


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = viewBindingLayout()
        return AlertDialog.Builder(requireContext()).setView(_binding?.root).create()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        initializeView(savedInstanceState)
        return binding.root
    }


    protected fun showMessage(message:String?){
        Snackbar.make(requireActivity().findViewById(android.R.id.content),""+message, Snackbar.LENGTH_LONG).show()
    }

    protected fun showProgressBar(isLoading: Boolean,view: View){
        if (isLoading) {
            view.visibility = View.VISIBLE
            requireActivity().window?.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        } else {
            view.visibility = View.GONE
            requireActivity().window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }
    }

    protected fun showToastMessage(message:String){
        Toast.makeText(requireContext(),message, Toast.LENGTH_SHORT).show()
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}