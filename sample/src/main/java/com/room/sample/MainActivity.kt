package com.room.sample

import android.os.Bundle
import android.text.InputType
import android.view.Gravity
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RelativeLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.room.sample.adapter.PlayerInfoListAdapter
import com.room.sample.databinding.ActivityMainBinding
import com.room.sample.db.PlayerInfoRepo
import com.room.sample.model.PlayerInfo
import com.room.sample.utils.ViewModelProviderFactory
import com.room.sample.utils.logcatD
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var viewModel: PlayerInfoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        initViewModel()
        initViews()
    }

    private fun initViews() {
        activityMainBinding.recyclerView.run {
            layoutManager = LinearLayoutManager(this@MainActivity)
            val playerInfoListAdapter = PlayerInfoListAdapter()
            adapter = playerInfoListAdapter

            val itemDecoration =
                DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL)
            addItemDecoration(itemDecoration)
        }

        viewModel.getDataSet().observe(this) { infoList ->
            for (info in infoList) {
                logcatD("item => $info")
            }

            val adapter = activityMainBinding.recyclerView.adapter as PlayerInfoListAdapter
            adapter.refreshDataList(infoList)
        }

        activityMainBinding.btnInsert
            .setOnClickListener {
                val (firstname, lastname) = generatePlayerNames()
                val playerInfo =
                    PlayerInfo(
                        firstName = firstname,
                        lastName = lastname,
                        age = Random().nextInt(100)
                    )
                viewModel.addPlayerInfo(playerInfo)
            }

        val adapter = activityMainBinding.recyclerView.adapter as PlayerInfoListAdapter
        adapter.addOnItemClickCallback { info ->

            val editText = EditText(this)
            editText.setBackgroundResource(android.R.color.transparent)
            editText.layoutParams = RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            editText.gravity = Gravity.CENTER
            editText.inputType = InputType.TYPE_CLASS_NUMBER

            editText.setText(info.age.toString())

            val dialog = AlertDialog.Builder(this)
                .setTitle("Modify Player Age")
                .setView(editText)
                .setPositiveButton("OK") { _, _ ->
                    val newInfo = PlayerInfo(
                        info.id,
                        info.firstName,
                        info.lastName,
                        editText.text.toString().toInt()
                    )
                    viewModel.updatePlayerInfo(newInfo)
                }
                .setNeutralButton("Delete") { _, _ ->
                    viewModel.deleteSelectPlayInfo(info)
                }
                .setNegativeButton("NO") { _, _ ->

                }
                .create()

            dialog.show()
        }

        activityMainBinding.btnQueryByAge
            .setOnClickListener {
                viewModel.queryPlayerByAge(60)
            }
    }

    private fun generatePlayerNames(): Array<String> {
        val uuid = UUID.randomUUID().toString()
        val names = uuid.split("-")
        return arrayOf(names[0], names[0])
    }

    private fun initViewModel() {
        val repository = PlayerInfoRepo(application)
        viewModel = ViewModelProvider(this, ViewModelProviderFactory(repository))
            .get(PlayerInfoViewModel::class.java)
    }
}