package com.example.challenge_1

import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow

import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

// --- Data Models --- It will be necessary distinguish from db/dto/domain --> in this example I'm only cosidering the domain model
sealed class Item {
    abstract val id: String
    abstract val title: String
    abstract val author: String
    abstract val isbn: String

    data class Book(
        override val isbn: String, override val author: String,
        override val id: String,
        override val title: String
    ) : Item()

    class ComicBook(
        override val id: String,
        override val isbn: String,
        override val title: String,
        override val author: String,
        val marvelUniverse: Boolean
    ) : Item()
}


enum class TypeOfBook {
    BOOK, COMIC_BOOK
}


//--Network Api ----
class LibraryApiService {
    fun fetchItems(): Result<List<Item>> {

        return Result.success(
            listOf(
                Item.Book("123", "Author1", "1", "Book1"),
                Item.ComicBook("456", "Author2", "2", "Comic1", true)
            )
        )
    }

}

//--Database ----

class LibraryDatabase {
    private val items = mutableListOf<Item>()
    fun fetchItems(filter: TypeOfBook): Result<List<Item>> =
        Result.success(items.filter {
            when (filter) {
                TypeOfBook.BOOK -> it is Item.Book
                TypeOfBook.COMIC_BOOK -> it is Item.ComicBook
            }

        })

    fun deleteItem(item: Item): Result<Unit> {
        return if (items.remove(item)) {
            Result.success(Unit)
        } else {
            Result.failure(Exception("Item not found"))
        }
    }

    fun saveItem(item: Item): Result<Unit> {
        return if (items.add(item)) {
            Result.success(Unit)
        } else {
            Result.failure(Exception("Item not added"))
        }
    }

    fun saveItems(items: List<Item>): Result<Unit> {
        return if (this.items.addAll(items)) {
            Result.success(Unit)
        } else {
            Result.failure(Exception("Items not added"))
        }
    }
}

// --- Data Sources ---
interface Library {

    fun fetchItems(filter: TypeOfBook): Flow<Result<List<Item>>>
    fun saveItems(items: List<Item>): Result<Unit>
    fun deleteItem(item: Item): Result<Unit>
    fun saveItem(item: Item): Result<Unit>

}


class LibraryImpl(
    private val libraryApi: LibraryApiService,
    private val libraryDatabase: LibraryDatabase
) : Library {
    @OptIn(FlowPreview::class)
    override fun fetchItems(filter: TypeOfBook): Flow<Result<List<Item>>> =
        flow { emit(libraryDatabase.fetchItems(filter)) }
            .flatMapConcat { databaseResult ->
                databaseResult.fold(
                    onSuccess = {
                        flow {
                            libraryApi.fetchItems()
                                .onSuccess { apiItems ->
                                    libraryDatabase.saveItems(apiItems)
                                        .onSuccess { emit(libraryDatabase.fetchItems(filter)) }
                                        .onFailure { emit(Result.failure(it)) }
                                }.onFailure { emit(Result.failure(it)) }
                        }
                    },
                    onFailure = { flowOf(Result.failure(it)) }
                )
            }


    override fun saveItems(items: List<Item>): Result<Unit> = libraryDatabase.saveItems(items)

    override fun saveItem(item: Item): Result<Unit> = libraryDatabase.saveItem(item)

    override fun deleteItem(item: Item): Result<Unit> = libraryDatabase.deleteItem(item)

}