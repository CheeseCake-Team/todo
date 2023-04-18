//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import com.cheesecake.todo.data.models.TodoItem
//import com.cheesecake.todo.data.models.TodoState
//import com.cheesecake.todo.databinding.FragmentSearchBinding
//import com.cheesecake.todo.ui.base.BaseFragment
//
//
//import com.cheesecake.todo.ui.home.TodoItemAdapter
//import com.cheesecake.todo.ui.home.TodoItemClickListener
//
//class SearchFragment() : BaseFragment<FragmentSearchBinding>(),
//    android.widget.SearchView.OnQueryTextListener {
//    private var searchAdapter = TodoItemAdapter() {
//
//    }
//
//    override val bindingInflater: (LayoutInflater) -> FragmentSearchBinding =
//        FragmentSearchBinding::inflate
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        setUp()
//    }
//
//    private fun setUp() {
//
//        addSearchListener()
//    }
//
//    private fun addSearchListener() {
//        binding.searchBar.setOnQueryTextListener(this)
//    }
//
//    private fun searchByQueryAndSetDataInAdapter(query: String?) {
//        query?.let {
//            binding.apply {
//                setDataOnAdapter(it)
//            }
//        }
//    }
//
//    private fun setDataOnAdapter(query: String) {
//        if (query.isNotEmpty()) {
//            val resultOfSearch = searchByTitleOrDescription(query)
//            searchAdapter.submitList(resultOfSearch)
//            binding.recyclerViewHome.adapter = searchAdapter
//        }
//
//    }
//
//
//    private fun searchByTitleOrDescription(searchWord: String): List<TodoItem> {
//        return list2.filter {
//            it.title.lowercase().contains(searchWord.lowercase()) || it.description.lowercase()
//                .contains(searchWord.lowercase())
//        }
//    }
//
//
//    override fun onQueryTextChange(newText: String?): Boolean {
//        newText?.let { searchByQueryAndSetDataInAdapter(it) }
//        return true
//    }
//
//    override fun onQueryTextSubmit(query: String?): Boolean {
//        query?.let { searchByQueryAndSetDataInAdapter(it) }
//        return true
//    }
//
//    private var list2 = listOf<TodoItem>(
//        TodoItem(",", "ll", "", "", TodoState.TODO, ""),
//        TodoItem(",", "", "", "", TodoState.TODO, ""),
//        TodoItem(",", "", "mm", "", TodoState.TODO, ""),
//        TodoItem(",", "", "", "", TodoState.TODO, "")
//    )
//    val todosList = listOf<TodoItem>(
//        TodoItem(",", "", "", "", TodoState.TODO, ""),
//        TodoItem(",", "", "", "", TodoState.TODO, ""),
//        TodoItem(",", "", "", "", TodoState.TODO, ""),
//        TodoItem(",", "", "", "", TodoState.TODO, "")
//    )
//    val todosList0 = listOf<TodoItem>(
//        TodoItem(",", "", "", "", TodoState.TODO, ""),
//        TodoItem(",", "", "", "", TodoState.TODO, ""),
//        TodoItem(",", "", "", "", TodoState.TODO, ""),
//        TodoItem(",", "", "", "", TodoState.TODO, "")
//    )
//
//}