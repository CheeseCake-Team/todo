import com.cheesecake.todo.databinding.FragmentSearchBinding
import com.cheesecake.todo.ui.base.BaseFragment
import com.cheesecake.todo.ui.home.TodoItemAdapter
import com.cheesecake.todo.ui.home.TodoItemClickListener
import com.google.android.material.search.SearchView


class SearchFragment() : BaseFragment<FragmentSearchBinding>(), SearchView.OnQueryTextListener {
    private var searchAdapter = TodoItemAdapter(object : TodoItemClickListener {
        override fun onTodoItemClick(todoItem: TodoItem) {
            class HomeFragment() : BaseFragment<FragmentHomeBinding>() {

            }
        })
        private val homeAdapter = HomeAdapter(object : TodoItemClickListener {
            override fun onTodoItemClick(todoItem: TodoItem) {

                @@ -33,44 +29,14 @@ class HomeFragment() : BaseFragment<FragmentHomeBinding>(), SearchView.OnQueryTe
                override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
                    super.onViewCreated(view, savedInstanceState)
                    setUp()
                    addCallbacks()



                }

                private fun setUp() {
                    homeAdapter.submitList(list)
                    addSearchListener()
                }

                private fun addSearchListener() {
                    binding.searchBar.setOnQueryTextListener(this)
                }

                private fun searchByQueryAndSetDataInAdapter(query: String?) {
                    query?.let {
                        binding.apply {
                            setDataOnAdapter(it)
                        }
                    }
                }

                private fun setDataOnAdapter(query: String) {
                    if (query.isNotEmpty()) {
                        val resultOfSearch = searchByTitleOrDescription(query)
                        searchAdapter.submitList(resultOfSearch)
                        binding.recyclerViewHome.adapter = searchAdapter
                    } else {

                        binding.recyclerViewHome.adapter = homeAdapter
                    }

                }

                private fun addCallbacks() {
                    binding.apply {
                        recyclerViewHome.adapter = homeAdapter
                    }
                }


                @@ -83,23 +49,7 @@ class HomeFragment() : BaseFragment<FragmentHomeBinding>(), SearchView.OnQueryTe
            }


            private fun searchByTitleOrDescription(searchWord: String): List<TodoItem> {
                return list2.filter {
                    it.title.lowercase().contains(searchWord.lowercase()) || it.description.lowercase()
                        .contains(searchWord.lowercase())
                }
            }


            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { searchByQueryAndSetDataInAdapter(it) }
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { searchByQueryAndSetDataInAdapter(it) }
                return true
            }

            private var list2 = listOf<TodoItem>(
                TodoItem(",", "ll", "", "", TodoState.TODO, ""),
                TodoItem(",", "", "", "", TodoState.TODO, ""),
                TodoItem(",", "", "mm", "", TodoState.TODO, ""),
                TodoItem(",", "", "", "", TodoState.TODO, "")
            )
            val todosList = listOf<TodoItem>(
                TodoItem(",", "", "", "", TodoState.TODO, ""),
                TodoItem(",", "", "", "", TodoState.TODO, ""),
                TodoItem(",", "", "", "", TodoState.TODO, ""),
                TodoItem(",", "", "", "", TodoState.TODO, "")
            )
            val todosList0 = listOf<TodoItem>(
                TodoItem(",", "", "", "", TodoState.TODO, ""),
                TodoItem(",", "", "", "", TodoState.TODO, ""),
                TodoItem(",", "", "", "", TodoState.TODO, ""),
                TodoItem(",", "", "", "", TodoState.TODO, "")
            )
            val list = listOf(
                DataItem.Header(10, 10, 20),
                DataItem.TagItem(Tag(0, "afdsafds", todosList)),
                DataItem.TagItem(Tag(1, "sadfsdafffff", todosList0))
            )
        }