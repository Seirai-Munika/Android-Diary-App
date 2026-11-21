package com.example.diaryapp

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import java.util.Calendar

/**
 * A simple [Fragment] subclass.
 * Use the [WriteFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WriteFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_write, container, false)
    }

    override fun onViewCreated(view:View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etDate = view.findViewById<EditText>(R.id.et_date)
        val etTitle  = view.findViewById<EditText>(R.id.et_title)
        val etContent = view.findViewById<EditText>(R.id.et_content)
        val btnSave = view.findViewById<Button>(R.id.btn_save)
        val btnBack = view.findViewById<Button>(R.id.btn_back)
        val image1 = view.findViewById<ImageButton>(R.id.imageButton_red)
        val image2 = view.findViewById<ImageButton>(R.id.imageButton_blue)
        val image3 = view.findViewById<ImageButton>(R.id.imageButton_green)
        val image4 = view.findViewById<ImageButton>(R.id.imageButton_yellow)
        val image5 = view.findViewById<ImageButton>(R.id.imageButton_purple)

        //カレンダー
        val calendar = Calendar.getInstance()
        //日記記録
        val db = DiaryDatabase.getDatabase(requireContext())

        //日付欄　デフォルトで今日の日付表示
        etDate.setText(
            String.format(
                "%04d-%02d-%02d",
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DAY_OF_MONTH)
            )
        )

        //日付欄　タップでカレンダー表示
        etDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get((Calendar.YEAR))
            val month = calendar.get((Calendar.MONTH))
            val day = calendar.get((Calendar.DAY_OF_MONTH))

            //カレンダー表示
            val datePicker = DatePickerDialog(
                requireContext(),
                {_, selectedYear, selectedMonth, selectedDay ->
                    val date = String.format(
                        "%04d-%02d-%02d",
                        selectedYear,
                        selectedMonth + 1,
                        selectedDay
                    )
                    etDate.setText(date)
                },
                year,
                month,
                day
            )
            datePicker.show()
        }

        //選択した画像番号
        var selectedImageId = 1

        image1.setOnClickListener {
            selectedImageId = 1
            selectImage(1, image1, image2, image3, image4, image5)
        }

        image2.setOnClickListener {
            selectedImageId = 2
            selectImage(2, image1, image2, image3, image4, image5)
        }

        image3.setOnClickListener {
            selectedImageId = 3
            selectImage(3, image1, image2, image3, image4, image5)
        }

        image4.setOnClickListener {
            selectedImageId = 4
            selectImage(4, image1, image2, image3, image4, image5)
        }

        image5.setOnClickListener {
            selectedImageId = 5
            selectImage(5, image1, image2, image3, image4, image5)
        }

        selectImage(1, image1, image2, image3, image4, image5)

        //記録ボタンを押すとデータベースに記録
        btnSave.setOnClickListener {
            if (etTitle.text.toString().isBlank() || etContent.text.toString().isBlank()) {
               Toast.makeText(
                   requireContext(),
                   "タイトルと内容を入力してください",
                   Toast.LENGTH_SHORT
               ).show()
                return@setOnClickListener
            }

            val diary = Diary(
                title = etTitle.text.toString(),
                content = etContent.text.toString(),
                date = etDate.text.toString(),
                imageId = selectedImageId
            )
            viewLifecycleOwner.lifecycleScope.launch {
                db.diaryDao().insert(diary)

                Toast.makeText(
                    requireContext(),
                    "日記記録完了",
                    Toast.LENGTH_SHORT
                ).show()

                //フラグメント変更
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, DiaryFragment())
                    .addToBackStack(null)
                    .commit()
            }
        }

        //フラグメント変更
        btnBack.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, DiaryFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    //画像を選択すると枠線を付ける
    private fun selectImage(
        selectedId: Int,
        image1: ImageButton,
        image2: ImageButton,
        image3: ImageButton,
        image4: ImageButton,
        image5: ImageButton
    ) {
        val images = listOf(image1, image2, image3, image4, image5)

        for (i in images.indices) {
            if( i+ 1 == selectedId) {
                images[i].setBackgroundResource(R.drawable.selected_border)
            }else {
                images[i].background = null
            }
        }
    }
}