import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.cheesecake.todo.data.models.TodoItem
import com.cheesecake.todo.data.models.TodoState
import com.cheesecake.todo.databinding.FragmentViewAllTodoItemsBinding
import com.cheesecake.todo.ui.base.BaseFragment

class ViewAllTodoItemsFragment : BaseFragment<FragmentViewAllTodoItemsBinding>() {

    override val bindingInflater: (LayoutInflater) -> FragmentViewAllTodoItemsBinding =
        FragmentViewAllTodoItemsBinding::inflate

    val todosList =
        listOf(
            TodoItem(",rfwea", "far", "arf", "afr", TodoState.TODO, "far"),
            TodoItem(",rfwea", "far", "arf", "afr", TodoState.TODO, "far"),
            listOf<TodoItem>(
                TodoItem(",rfwea", "far", "arf", null, TodoState.TODO, "far"),
                TodoItem(",rfwea", "far", "arf", null, TodoState.TODO, "far"),
            ), listOf<TodoItem>(
                TodoItem(",rfwea", "far", "arf", "afr", TodoState.TODO, "far"),
                TodoItem(",rfwea", "far", "arf", "afr", TodoState.TODO, "far"),
            )
        )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //binding.recyclerViewAllTodos.adapter = SearchTodosAdapter(todosList[2] as List<TodoItem>)
    }

}