package ru.netology.nmedia.db

object PostsTable {

    val DDL = """
        CREATE TABLE ${Columns.TABLE} (
            ${Columns.ID} INTEGER PRIMARY KEY AUTOINCREMENT,
            ${Columns.AUTHOR} TEXT NOT NULL,
            ${Columns.CONTENT} TEXT NOT NULL,
            ${Columns.PUBLISHED} TEXT NOT NULL,
            ${Columns.LIKED_BY_ME} BOOLEAN NOT NULL DEFAULT 0,
            ${Columns.LIKES} INTEGER NOT NULL DEFAULT 0,
            ${Columns.COUNT_SHARE} INTEGER NOT NULL DEFAULT 0,
            ${Columns.VIDEO} TEXT DEFAULT NULL
        );
        """.trimIndent()


    object Columns {
        const val TABLE = "posts"
        const val ID = "id"
        const val AUTHOR = "author"
        const val CONTENT = "content"
        const val PUBLISHED = "published"
        const val LIKED_BY_ME = "likedByMe"
        const val LIKES = "likes"
        const val COUNT_SHARE = "countShare"
        const val VIDEO = "video"
        val ALL_COLUMNS = arrayOf(
            ID,
            AUTHOR,
            CONTENT,
            PUBLISHED,
            LIKED_BY_ME,
            LIKES,
            COUNT_SHARE,
            VIDEO
        )
    }
}