package com.rappi.emovie.ui.views

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.rappi.emovie.R
import com.rappi.emovie.databinding.FragmentNoDataBinding
import com.rappi.emovie.domain.model.Movie

class NoDataDialogFragment(private val text: String, private val onClickListener : () -> Unit) : DialogFragment() {

    private lateinit var binding: FragmentNoDataBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreate(savedInstanceState)
        binding = FragmentNoDataBinding.inflate(layoutInflater)
        val builder = AlertDialog.Builder(requireContext())
        builder.setView(binding.root)
        setView()
        return builder.create()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun setView(){
        binding.ok.setOnClickListener { onClickListener() }
        binding.text.text = text
    }

}