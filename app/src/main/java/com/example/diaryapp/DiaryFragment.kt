package com.example.diaryapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 * Use the [DiaryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DiaryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_diary, container, false)
    }

    override fun onViewCreated(view:View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView  = view.findViewById<RecyclerView>(R.id.rv_diary)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val db = DiaryDatabase.getDatabase(requireContext())

        viewLifecycleOwner.lifecycleScope.launch {
            val diaries = db.diaryDao().getAll()
            recyclerView.adapter = DiaryAdapter(diaries)
        }
        //ボタンを取得する
        val btnWrite = view.findViewById<ImageButton>(R.id.btn_write)

        //ボタンを押すとWriteFragmentに変更
        btnWrite.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, WriteFragment())
                .addToBackStack(null)
                .commit()
        }
    }

}