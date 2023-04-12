package com.cheesecake.todo.ui.createtodo

import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cheesecake.todo.R
import com.cheesecake.todo.databinding.FragmentCreateToDoBinding
import com.cheesecake.todo.ui.base.BaseFragment
import com.cheesecake.todo.ui.createtodo.model.CreateToDoModel
import com.cheesecake.todo.ui.createtodo.presenter.StatusResponse


class ToDoFragment : BaseFragment<FragmentCreateToDoBinding>() {
    override val bindingInflater: (LayoutInflater) -> FragmentCreateToDoBinding =
        FragmentCreateToDoBinding::inflate

    private lateinit var title: String
    private lateinit var description: String
    private val presenter : CreateTodoPresenter by lazy {
        CreateTodoPresenter(requireContext())
    }
    private var status = "" // take init value from previous fragment
    private val person = "person"
    private val team = "team"


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPersonalOrTeam()
        hasBackButtonOrNot()
        setActionBarTitle()
        createToDo()


    }

    private fun getData() {
        title = binding.titleField.text.toString()
        description = binding.descriptionField.text.toString()
    }


    private fun createToDo() {

        binding.create.setOnClickListener {
            showDialog()
            getData()

            if (checkEmptyField(title, description)) {
                checkPersonalOrTeam().let {

                    presenter.checkToken()?.let { token ->
                        makeRequest(token)

                    }


                }
            }


        }
    }

    private fun makeRequest(token: String) {
        when (status) {
            person -> {
                showDialog()
                presenter.toDoForPersonal(
                    title, description, token,
                    object : StatusResponse {
                        override fun onSuccess(toDoModel: CreateToDoModel) {
                            Log.d("TAG", "onSuccess: ${toDoModel.value}")
                            showDialog()
                        }
                        override fun onError(error: String) {
                        }
                    }
                )
            }
            team -> {
                presenter.toDoForTeam(
                    title, description, token,
                    object : StatusResponse {
                        override fun onSuccess(toDoModel: CreateToDoModel) {
                            Log.d("TAG", "onSuccess: ${toDoModel.value}")
                           requireActivity().runOnUiThread {
                               showDialog()

                           }

                        }
                        override fun onError(error: String) {
                        }
                    }
                )
            }
            else -> {
                Toast.makeText(
                    requireActivity(),
                    "Please Chose status ",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
    }

    private fun hasBackButtonOrNot() {
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    private fun setActionBarTitle() {
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.create_task)
    }

    private fun checkEmptyField(title: String, description: String): Boolean {
        when {
            title.trim().isEmpty() -> {
                Toast.makeText(requireContext(), "Please fill title field", Toast.LENGTH_SHORT)
                    .show()
                return false
            }

            description.trim().isEmpty() -> {
                Toast.makeText(
                    requireContext(),
                    "Please fill description field",
                    Toast.LENGTH_SHORT
                )
                    .show()
                return false
            }

        }
        return true

    }

    private fun checkChip(
        checkPersonal: Boolean, checkTeam: Boolean,
        colorPersonal: Int, colorTeam: Int, status: String
    ): String {
        binding.apply {
            chipPersonalTodo.apply {
                isCheckable = checkPersonal
                setChipBackgroundColorResource(colorPersonal)
            }
            chipTeamTodo.apply {
                isCheckable = checkTeam
                setChipBackgroundColorResource(colorTeam)
            }
        }
        return status
    }

    private fun checkPersonalOrTeam(): String {
        binding.apply {
            chipPersonalTodo.setOnClickListener {
                when {
                    binding.chipPersonalTodo.isCheckable -> status = checkChip(
                        checkPersonal = false,
                        checkTeam = true,
                        colorPersonal = R.color.white,
                        colorTeam = R.color.blue,
                        status = team
                    )

                    !binding.chipPersonalTodo.isCheckable -> status = checkChip(
                        checkPersonal = true,
                        checkTeam = false,
                        colorPersonal = R.color.blue,
                        colorTeam = R.color.white,
                        status = person
                    )


                }
            }
            chipTeamTodo.setOnClickListener {
                when {
                    binding.chipTeamTodo.isCheckable -> status = checkChip(
                        checkPersonal = true,
                        checkTeam = false,
                        colorPersonal = R.color.blue,
                        colorTeam = R.color.white,
                        status = person
                    )
                    !binding.chipTeamTodo.isCheckable ->
                        status = checkChip(
                            checkPersonal = false,
                            checkTeam = true,
                            colorPersonal = R.color.white,
                            colorTeam = R.color.blue,
                            status = team
                        )

                }
            }
        }

        return status
    }

    private fun showDialog(){
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.confirmationdialog);
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false)
        dialog.window?.attributes?.windowAnimations = R.style.animationDialog;

       val cancel = dialog.findViewById<TextView>(R.id.cancel);

        cancel.setOnClickListener{
                dialog.dismiss();
        }

        dialog.show();

    }





}