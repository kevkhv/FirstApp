package ru.netology.nmedia.db

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import ru.netology.nmedia.dto.Post

class PostDaoImpl(private val db: SQLiteDatabase) : PostDao {


    override fun getAll(): List<Post> {
        val posts = mutableListOf<Post>()
        db.query(
            PostsTable.Columns.TABLE,
            PostsTable.Columns.ALL_COLUMNS,
            null, null, null, null,
            "${PostsTable.Columns.ID} DESC"
        ).use { cursor ->
            while (cursor.moveToNext()) {
                posts.add(createPost(cursor))
            }
        }
        return posts
    }

    override fun save(post: Post): Post {
        val values = ContentValues().apply {
            put(PostsTable.Columns.AUTHOR, post.author)
            put(PostsTable.Columns.CONTENT, post.content)
            put(PostsTable.Columns.PUBLISHED, post.published)
            put(PostsTable.Columns.LIKED_BY_ME, post.likedByMe)
            put(PostsTable.Columns.LIKES, post.likes)
            put(PostsTable.Columns.COUNT_SHARE, post.countShare)
            put(PostsTable.Columns.VIDEO, post.video)
        }
        val id = if (post.id != 0) {                                      // old post, Update
            db.update(
                PostsTable.Columns.TABLE,
                values,
                "${PostsTable.Columns.ID} = ?",
                arrayOf(post.id.toString())
            )
        post.id
        } else {                                                          // new post, Insert
            db.insert(PostsTable.Columns.TABLE, null, values)
        }

        db.query(
            PostsTable.Columns.TABLE,
            PostsTable.Columns.ALL_COLUMNS,
            "${PostsTable.Columns.ID} = ?",
            arrayOf(id.toString()),
            null, null, null
        ).use { cursor ->
            cursor.moveToNext()
            return createPost(cursor)
        }
    }


    override fun shareById(id: Int) {
        db.execSQL(
            """
           UPDATE ${PostsTable.Columns.TABLE} SET
               ${PostsTable.Columns.COUNT_SHARE} = ${PostsTable.Columns.COUNT_SHARE} + 1
           WHERE ${PostsTable.Columns.ID} = ?;
        """.trimIndent(),
            arrayOf(id)
        )
    }

    override fun likeById(id: Int) {
        db.execSQL(
            """
           UPDATE ${PostsTable.Columns.TABLE} SET
               ${PostsTable.Columns.LIKES} = ${PostsTable.Columns.LIKES} + CASE WHEN ${PostsTable.Columns.LIKED_BY_ME} THEN -1 ELSE 1 END,
               ${PostsTable.Columns.LIKED_BY_ME} = CASE WHEN ${PostsTable.Columns.LIKED_BY_ME} THEN 0 ELSE 1 END
           WHERE ${PostsTable.Columns.ID} = ?;
        """.trimIndent(),
            arrayOf(id)
        )
    }

    override fun removeById(id: Int) {
        db.delete(
            PostsTable.Columns.TABLE,
            "${PostsTable.Columns.ID} = ?",
            arrayOf(id.toString())
        )
    }

    private fun createPost(cursor: Cursor): Post {
        with(cursor) {
            return Post(
                id = getInt(getColumnIndexOrThrow(PostsTable.Columns.ID)),
                author = getString(getColumnIndexOrThrow(PostsTable.Columns.AUTHOR)),
                content = getString(getColumnIndexOrThrow(PostsTable.Columns.CONTENT)),
                published = getString(getColumnIndexOrThrow(PostsTable.Columns.PUBLISHED)),
                likedByMe = getInt(getColumnIndexOrThrow(PostsTable.Columns.LIKED_BY_ME)) != 0,
                likes = getInt(getColumnIndexOrThrow(PostsTable.Columns.LIKES)),
                countShare = getInt(getColumnIndexOrThrow(PostsTable.Columns.COUNT_SHARE)),
                video = getString(getColumnIndexOrThrow(PostsTable.Columns.VIDEO)),
            )
        }
    }
}