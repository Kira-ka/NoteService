data class Note(
    val nId: Int = 0,
    val title: String,
    val text: String,
    val privacy: Int,
    val commentPrivacy: Int,
    val delet: Boolean = false
)

data class Comment(
    val cId: Int = 0,
    val message: String,
    val delet: Boolean = false
)

data class Notes<A, B>(var first: A, var second: B?)


object WallService {
    val userNotes = mutableListOf<Notes<Note, Array<Comment>>>()
    private var counterNid: Int = 0
    private var counterCid: Int = 0

    fun add(note: Note): Int {
        val postWithId = note.copy(nId = counterNid)
        userNotes.add(Notes(postWithId, null))
        counterNid += 1
        return postWithId.nId
    }

    fun createComment(nId: Int, comment: Comment): Int {
        var count = 0
        for (notes in userNotes) {
            if (notes.first.nId == nId) {
                val commentWithId = comment.copy(cId = counterCid)
                count = commentWithId.cId
                counterCid += 1
                notes.second = arrayOf(commentWithId)
            }
        }
        return count
    }

    fun deleteNote(nId: Int): Boolean {
        var check = false
        for (notes in userNotes) {
            if (notes.first.nId == nId) {
                val n = notes.first
                val nn = n.copy(delet = true)
                val c = notes.second
                val cc = emptyArray<Comment>()
                for ((_, existing) in c?.withIndex()!!)
                    arrayOf(existing.copy(delet = true))
                check = true
                userNotes.remove(notes)
                return userNotes.add(Notes(nn, cc))
            }
            if (check == false) {
                throw NoteNotFoundException("Note not found")
            }
        }
        return false
    }

    fun deleteCom(cId: Int): Boolean {
        for (notes in userNotes) {
            val c = notes.second
            for ((index, existing) in c?.withIndex()!!) {
                if (existing.cId == cId) {
                    val cc = existing.copy(delet = true)
                    c.set(index, cc)
                    return true
                }
            }
        }
        return false
    }

    fun editNote(
        nId: Int,
        title: String,
        text: String,
        privacy: Int,
        commentPrivacy: Int
    ): Boolean {
        var check = false
        for (notes in userNotes) {
            if (notes.first.nId == nId) {
                val c = notes.second
                val n = notes.first.copy(
                    title = title,
                    text = text,
                    privacy = privacy,
                    commentPrivacy = commentPrivacy
                )
                check = true
                userNotes.remove(notes)
                return userNotes.add(Notes(n, c))
            }
        }
        if (check == false) {
            throw NoteNotFoundException("Note not found")
        }
        return false
    }

    fun editComment(cId: Int, message: String): Boolean {
        var check = false
        for (notes in userNotes) {
            val c = notes.second
            for ((index, existing) in c?.withIndex()!!) {
                if (existing.cId == cId || existing.delet) {
                    throw AccessToCommentDenied("Access to comment denied")
                } else if (existing.cId == cId) {
                    check = true
                    c[index] = existing.copy(message = message)
                    return check
                }
            }
        }
        return check
    }

    fun getNotes(): Array<Note> {
        var aNotes = emptyArray<Note>()
            for (notes in userNotes) {
            val n = notes.first
                aNotes += n
        }
        return aNotes
    }


}

fun main(args: Array<String>) {

}