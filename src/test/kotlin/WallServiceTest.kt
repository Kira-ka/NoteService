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

        assertEquals(4, result1)
        assertEquals(5, result2)
    }


    @Test
    fun createComment() {
        val note1 = Note(0, "Заголовок1", "Текст заметки1", false)
        val note2 = Note(0, "Заголовок2", "Текст заметки2", false)
        val com1 = Comment(0, " Комментарий1", false)
        val com2 = Comment(0, " Комментарий2", false)

        WallService.add(note1)
        WallService.add(note2)


        val result1 = WallService.createComment(2, com1)
        val result2 = WallService.createComment(2, com2)

        assertEquals(1, result1)
        assertEquals(2, result2)

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
}