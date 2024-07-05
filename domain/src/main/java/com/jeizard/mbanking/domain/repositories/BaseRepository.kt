package com.jeizard.mbanking.domain.repositories

interface BaseRepository<Entity> {
    suspend fun insert(item: Entity)
    suspend fun update(item: Entity)
    suspend fun delete(item: Entity)
    suspend fun deleteAll()
    fun getAll(): List<Entity>

    interface OnDataChangedListener<Entity> {
        fun onChanged(items: List<Entity>)
    }

    fun addListener(listener: OnDataChangedListener<Entity>)
    fun removeListener(listener: OnDataChangedListener<Entity>)
}
