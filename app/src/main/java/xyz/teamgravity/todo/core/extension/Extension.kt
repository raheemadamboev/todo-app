package xyz.teamgravity.todo.core.extension

/**
 * To turn sealed class statement to an expression so compiler won't compile code if all branches in when took care
 */
val <T> T.exhaustive: T
    get() = this