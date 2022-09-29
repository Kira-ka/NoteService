import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class WallServiceTest {

    @Before
    fun clearBeforeTest() {
        WallService.clear()
    }

    @Test
    fun add() {
        val note1 = Note(0, "Заголовок1", "Текст заметки1", false)
        val note2 = Note(0, "Заголовок2", "Текст заметки2", false)


        val result1 = WallService.add(note1)
        val result2 = WallService.add(note2)

        assertEquals(0, result1)
        assertEquals(1, result2)
    }


    @Test
    fun createComment() {
        val note1 = Note(0, "Заголовок1", "Текст заметки1", false)
        val note2 = Note(0, "Заголовок2", "Текст заметки2", false)
        val com1 = Comment(0, " Комментарий1", false)
        val com2 = Comment(0, " Комментарий2", false)

        WallService.add(note1)
        WallService.add(note2)


        val result1 = WallService.createComment(0, com1)
        val result2 = WallService.createComment(1, com2)

        assertEquals(0, result1)
        assertEquals(1, result2)

    }

    @Test(expected = NoteNotFoundException::class)
    fun createCommentThrow() {
        val note1 = Note(0, "Заголовок1", "Текст заметки1", false)
        val note2 = Note(0, "Заголовок2", "Текст заметки2", false)
        val com1 = Comment(0, " Комментарий1", false)
        val com2 = Comment(0, " Комментарий2", false)
        WallService.add(note1)
        WallService.add(note2)
        WallService.createComment(0, com1)
        WallService.createComment(25, com2)
    }

    @Test
    fun deleteNote() {
        val note1 = Note(0, "Заголовок1", "Текст заметки1", false)
        val note2 = Note(0, "Заголовок2", "Текст заметки2", false)
        val com1 = Comment(0, " Комментарий1", false)
        val com2 = Comment(0, " Комментарий2", false)

        WallService.add(note1)
        WallService.add(note2)
        WallService.createComment(0, com1)
        WallService.createComment(0, com2)


        val result = WallService.deleteNote(1)

        assertEquals(true, result)
    }

    @Test(expected = NoteNotFoundException::class)
    fun deleteNoteThrow() {
        val note1 = Note(0, "Заголовок1", "Текст заметки1", false)
        val note2 = Note(0, "Заголовок2", "Текст заметки2", false)
        val com1 = Comment(0, " Комментарий1", false)
        val com2 = Comment(0, " Комментарий2", false)

        WallService.add(note1)
        WallService.add(note2)
        WallService.createComment(0, com1)

        WallService.deleteNote(33)
    }

    @Test
    fun deleteCom() {
        val note1 = Note(0, "Заголовок1", "Текст заметки1", false)
        val note2 = Note(0, "Заголовок2", "Текст заметки2", false)
        val com1 = Comment(0, " Комментарий1", false)
        val com2 = Comment(0, " Комментарий2", false)

        WallService.add(note1)
        WallService.add(note2)
        WallService.createComment(1, com1)
        WallService.createComment(1, com2)

        val result = WallService.deleteCom(0)

        assertEquals(true, result)
    }

    @Test
    fun editNote() {
        val note1 = Note(0, "Заголовок1", "Текст заметки1", false)
        val note2 = Note(0, "Заголовок2", "Текст заметки2", false)
        val com1 = Comment(0, " Комментарий1", false)
        val com2 = Comment(0, " Комментарий2", false)

        WallService.add(note1)
        WallService.add(note2)
        WallService.createComment(1, com1)
        WallService.createComment(1, com2)

        val result = WallService.editNote(0, "Редактированный Заголовок1", "Редактированный Текст заметки1")

        assertEquals(true, result)
    }

    @Test(expected = NoteNotFoundException::class)
    fun editNoteThrow() {
        val note1 = Note(0, "Заголовок1", "Текст заметки1", false)
        val note2 = Note(0, "Заголовок2", "Текст заметки2", false)
        val com1 = Comment(0, " Комментарий1", false)
        val com2 = Comment(0, " Комментарий2", false)

        WallService.add(note1)
        WallService.add(note2)
        WallService.createComment(1, com1)
        WallService.createComment(1, com2)

        val result = WallService.editNote(55, "Редактированный Заголовок1", "Редактированный Текст заметки1")

        assertEquals(true, result)
    }

    @Test
    fun editComment() {
        val note1 = Note(0, "Заголовок1", "Текст заметки1", false)
        val note2 = Note(0, "Заголовок2", "Текст заметки2", false)
        val com1 = Comment(0, " Комментарий1", false)
        val com2 = Comment(0, " Комментарий2", false)
        val com3 = Comment(0, " Комментарий3", false)

        WallService.add(note1)
        WallService.add(note2)
        WallService.createComment(1, com1)
        WallService.createComment(1, com2)
        WallService.createComment(0, com3)

        val result = WallService.editComment(0, "Редактированный Комментарий3",)

        assertEquals(true, result)
    }

    @Test(expected = AccessToCommentDenied::class)
    fun editCommentThrow() {
        val note1 = Note(0, "Заголовок1", "Текст заметки1", false)
        val note2 = Note(0, "Заголовок2", "Текст заметки2", false)
        val com1 = Comment(0, " Комментарий1", false)
        val com2 = Comment(0, " Комментарий2", false)
        val com3 = Comment(0, " Комментарий3", true)

        WallService.add(note1)
        WallService.add(note2)
        WallService.createComment(1, com1)
        WallService.createComment(1, com2)
        WallService.createComment(0, com3)

        val result = WallService.editComment(2, "Редактированный Комментарий3",)

        assertEquals(true, result)
    }

    @Test
    fun getNotes() {
        val note1 = Note(0, "Заголовок1", "Текст заметки1", false)
        val note2 = Note(1, "Заголовок2", "Текст заметки2", false)
        val com1 = Comment(0, " Комментарий1", false)
        val com2 = Comment(0, " Комментарий2", false)
        val com3 = Comment(0, " Комментарий3", false)

        WallService.add(note1)
        WallService.add(note2)
        WallService.createComment(1, com1)
        WallService.createComment(1, com2)
        WallService.createComment(0, com3)

        val result = WallService.getNotes()
        val ar = arrayOf(note1, note2)

        assertArrayEquals(ar, result)
    }

    @Test
    fun getById() {
        val note1 = Note(0, "Заголовок1", "Текст заметки1", false)
        val note2 = Note(1, "Заголовок2", "Текст заметки2", false)
        val com1 = Comment(0, " Комментарий1", false)
        val com2 = Comment(0, " Комментарий2", false)
        val com3 = Comment(0, " Комментарий3", false)

        WallService.add(note1)
        WallService.add(note2)
        WallService.createComment(1, com1)
        WallService.createComment(1, com2)
        WallService.createComment(0, com3)

        val result = WallService.getById(0)
        val ar = arrayOf(note1)

        assertArrayEquals(ar, result)
    }

    @Test
    fun getByIdVar() {
        val note1 = Note(0, "Заголовок1", "Текст заметки1", false)
        val note2 = Note(1, "Заголовок2", "Текст заметки2", false)
        val com1 = Comment(0, " Комментарий1", false)
        val com2 = Comment(0, " Комментарий2", false)
        val com3 = Comment(0, " Комментарий3", false)

        WallService.add(note1)
        WallService.add(note2)
        WallService.createComment(1, com1)
        WallService.createComment(1, com2)
        WallService.createComment(0, com3)

        val result = WallService.getById(0,1)
        val ar = arrayOf(note1, note2)

        assertArrayEquals(ar, result)
    }

    @Test(expected = NoteNotFoundException::class)
    fun getByIdThrow() {
        val note1 = Note(0, "Заголовок1", "Текст заметки1", false)
        val note2 = Note(1, "Заголовок2", "Текст заметки2", false)
        val com1 = Comment(0, " Комментарий1", false)
        val com2 = Comment(0, " Комментарий2", false)
        val com3 = Comment(0, " Комментарий3", false)

        WallService.add(note1)
        WallService.add(note2)
        WallService.createComment(1, com1)
        WallService.createComment(1, com2)
        WallService.createComment(0, com3)

        val result = WallService.getById(55)
        val ar = arrayOf(note1)

        assertArrayEquals(ar, result)
    }


    @Test
    fun getComments() {
        val note1 = Note(0, "Заголовок1", "Текст заметки1", false)
        val note2 = Note(1, "Заголовок2", "Текст заметки2", false)
        val com1 = Comment(0, " Комментарий1", false)
        val com2 = Comment(0, " Комментарий2", false)
        val com3 = Comment(0, " Комментарий3", true)

        WallService.add(note1)
        WallService.add(note2)
        WallService.createComment(1, com1)
        WallService.createComment(1, com2)
        WallService.createComment(0, com3)

        val result = WallService.getComments(0)
        val ar = arrayOf(com1)

        assertArrayEquals(ar, result)
    }

    @Test
    fun getCommentsVar() {
        val note1 = Note(0, "Заголовок1", "Текст заметки1", false)
        val note2 = Note(1, "Заголовок2", "Текст заметки2", false)
        val com1 = Comment(0, " Комментарий1", false)
        val com2 = Comment(1, " Комментарий2", false)
        val com3 = Comment(0, " Комментарий3", true)

        WallService.add(note1)
        WallService.add(note2)
        WallService.createComment(1, com1)
        WallService.createComment(1, com2)
        WallService.createComment(0, com3)

        val result = WallService.getComments(0,1)
        val ar = arrayOf(com1, com2)

        assertArrayEquals(ar, result)
    }

    @Test(expected = AccessToCommentDenied::class)
    fun getCommentsThrow() {
        val note1 = Note(0, "Заголовок1", "Текст заметки1", false)
        val note2 = Note(1, "Заголовок2", "Текст заметки2", false)
        val com1 = Comment(0, " Комментарий1", false)
        val com2 = Comment(1, " Комментарий2", false)
        val com3 = Comment(0, " Комментарий3", true)

        WallService.add(note1)
        WallService.add(note2)
        WallService.createComment(1, com1)
        WallService.createComment(1, com2)
        WallService.createComment(0, com3)

        val result = WallService.getComments(0,1,2)
    }

    @Test
    fun restoreComment() {
        val note1 = Note(0, "Заголовок1", "Текст заметки1", false)
        val note2 = Note(1, "Заголовок2", "Текст заметки2", false)
        val com1 = Comment(0, " Комментарий1", false)
        val com2 = Comment(0, " Комментарий2", false)
        val com3 = Comment(0, " Комментарий3", true)

        WallService.add(note1)
        WallService.add(note2)
        WallService.createComment(1, com1)
        WallService.createComment(1, com2)
        WallService.createComment(0, com3)

        val result = WallService.restoreComment(2)

        assertEquals(true, result)
    }

    @Test(expected = AccessToCommentDenied::class)
    fun restoreCommentThrow() {
        val note1 = Note(0, "Заголовок1", "Текст заметки1", false)
        val note2 = Note(1, "Заголовок2", "Текст заметки2", false)
        val com1 = Comment(0, " Комментарий1", false)
        val com2 = Comment(0, " Комментарий2", false)
        val com3 = Comment(0, " Комментарий3", true)

        WallService.add(note1)
        WallService.add(note2)
        WallService.createComment(1, com1)
        WallService.createComment(1, com2)
        WallService.createComment(0, com3)

        val result = WallService.restoreComment(0)

        assertEquals(true, result)
    }
}
