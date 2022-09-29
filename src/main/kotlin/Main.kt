data class Note(
    val nId: Int = 0,
    val title: String,
    val text: String,
    val delet: Boolean = false
)

data class Comment(
    val cId: Int = 0,
    val message: String,
    val delet: Boolean = false
)

data class Notes<A, B>(var first: A, var second: B)


object WallService {
    private var userNotes = mutableListOf<Notes<Note, Array<Comment>>>()
    private var counterNid: Int = 0
    private var counterCid: Int = 0


    fun add(note: Note): Int {
        val postWithId = note.copy(nId = counterNid)
        userNotes.add(Notes(postWithId, emptyArray()))
        counterNid += 1

        return postWithId.nId
    }


    fun createComment(nId: Int, comment: Comment): Int {
        var count = 0
        for (notes in userNotes) {
            if (notes.first.nId == nId) {
                val commentWithId = comment.copy(cId = counterCid)
                count = 1
                notes.second += commentWithId
                counterCid += 1

            }
        }
        if (count == 0) {
            throw NoteNotFoundException("Note not found")
        }
        return counterCid - 1
    }

    fun deleteNote(nId: Int): Boolean {
        var check = false
        for (notes in userNotes) {
            if (notes.first.nId == nId && !notes.first.delet) {
                val n = notes.first.copy(delet = true)
                val c = notes.second
                var cc = emptyArray<Comment>()
                for ((_, existing) in c.withIndex()) {
                    cc += existing.copy(delet = true)
                }
                check = true
                userNotes.remove(notes)
                return userNotes.add(Notes(n, cc))
            }
        }
        if (check == false) {
            throw NoteNotFoundException("Note not found")
        }
        return check
    }

    fun deleteCom(cId: Int): Boolean {
        for (notes in userNotes) {
            var c = notes.second
            for ((index, existing) in c.withIndex()) {
                if (existing.cId == cId) {
                    c[index] = existing.copy(delet = true)
                    return true
                }
            }
        }
        return false
    }

    fun editNote(
        nId: Int,
        title: String,
        text: String
        ): Boolean {
        var check = false
        for (notes in userNotes) {
            if (notes.first.nId == nId && !notes.first.delet) {
                val c = notes.second
                val n = notes.first.copy(
                    title = title,
                    text = text
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
            for ((index, existing) in c.withIndex()) {
                if (existing.cId == cId && existing.delet) {
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
            if (!notes.first.delet)
                aNotes += notes.first
        }
        return aNotes
    }

    fun getById(vararg nId: Int): Array<Note> {
        var aNotes = emptyArray<Note>()
        var check = 0
        for (notes in userNotes) {
            for ((_, existing) in nId.withIndex()) {
                if (notes.first.nId == existing && !notes.first.delet) {
                    aNotes += notes.first
                    check +=1
                }
            }
        }
        if (check == 0) {
            throw NoteNotFoundException("Note not found")
        }
        return aNotes
    }

    fun getComments(vararg cId: Int): Array<Comment> {
        var aCom = emptyArray<Comment>()
        for (notes in userNotes) {
            val com = notes.second
            for ((_, existing1) in cId.withIndex()) {
                for ((index, existing2) in com?.withIndex()!!) {
                    if (existing2.cId == existing1 && existing2.delet) {
                        throw AccessToCommentDenied("Access to comment denied")
                    } else if (existing2.cId == existing1) {
                        aCom += existing2
                    }
                }
            }
        }
        return aCom
    }

    fun restoreComment(cId: Int): Boolean {
        var check = 0
        for (notes in userNotes) {
            val c = notes.second
            for ((index, existing) in c?.withIndex()!!) {
                if (existing.cId == cId && existing.delet) {
                    c[index] = existing.copy(delet = false)
                    check +=1
                    return true
                } else if (existing.cId == cId && !existing.delet) {
                    throw AccessToCommentDenied("Access to comment denied. Сomment not deleted")
                }
            }
        }

        return false
    }

    fun clear() {
        userNotes.clear()
        counterNid = 0
        counterCid = 0
    }
}

fun main(args: Array<String>) {
    val note1 = Note(0, "Заголовок1", "Текст заметки1", false)
    val note2 = Note(0, "Заголовок2", "Текст заметки2", false)
    val com1 = Comment(0, " Комментарий1", false)
    val com2 = Comment(0, " Комментарий2", false)
    WallService.add(note1)
    WallService.add(note2)
    WallService.createComment(1, com1)
    WallService.createComment(1, com2)
    val ar = WallService.getNotes()
    println(ar[0])
    println(ar[1])

}