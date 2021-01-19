package xyz.teamgravity.todo.helper.extensions

import androidx.appcompat.widget.SearchView

/**
 * Text change function to get only text live change from search view
 */
inline fun SearchView.onQueryTextChanged(crossinline listener: (String) -> Unit) {
    this.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            return true
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            listener(newText.orEmpty())
            return true
        }
    })
}

/**
 * To turn sealed class statement to an expression so compiler won't compile code if all branches in when took care
 */
val <T> T.exhaustive: T
    get() = this