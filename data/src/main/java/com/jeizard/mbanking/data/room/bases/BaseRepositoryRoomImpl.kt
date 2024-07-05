package com.jeizard.mbanking.data.room.bases

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.jeizard.mbanking.domain.repositories.BaseRepository
import com.jeizard.mbanking.data.mapper.Mapper

abstract class BaseRepositoryRoomImpl<DBEntity, DAO : BaseDao<DBEntity>, Entity>(
    private val dao: DAO,
    private val mapper: Mapper<DBEntity, Entity>
) : BaseRepository<Entity> {

    private var allItems: List<DBEntity> = emptyList()
    private val listeners: MutableSet<BaseRepository.OnDataChangedListener<Entity>> = HashSet()

    init {
        CoroutineScope(Dispatchers.IO).launch {
            initData()
        }
    }

    private suspend fun initData() {
        allItems = withContext(Dispatchers.IO) { dao.getAll() }
        withContext(Dispatchers.Main) {
            notifyChanges()
        }
    }

    override suspend fun insert(item: Entity) {
        withContext(Dispatchers.IO) {
            dao.insert(mapper.mapToDBEntity(item))
            allItems = dao.getAll()
        }
        withContext(Dispatchers.Main) {
            notifyChanges()
        }
    }

    override suspend fun update(item: Entity) {
        withContext(Dispatchers.IO) {
            dao.update(mapper.mapToDBEntity(item))
            allItems = dao.getAll()
        }
        withContext(Dispatchers.Main) {
            notifyChanges()
        }
    }

    override suspend fun delete(item: Entity) {
        withContext(Dispatchers.IO) {
            dao.delete(mapper.mapToDBEntity(item))
            allItems = dao.getAll()
        }
        withContext(Dispatchers.Main) {
            notifyChanges()
        }
    }

    override suspend fun deleteAll() {
        withContext(Dispatchers.IO) {
            dao.deleteAll()
            allItems = dao.getAll()
        }
        withContext(Dispatchers.Main) {
            notifyChanges()
        }
    }

    override fun getAll(): List<Entity> {
        return mapper.mapFromDBEntity(allItems)
    }

    override fun addListener(listener: BaseRepository.OnDataChangedListener<Entity>) {
        listeners.add(listener)
        listener.onChanged(mapper.mapFromDBEntity(allItems))
    }

    override fun removeListener(listener: BaseRepository.OnDataChangedListener<Entity>) {
        listeners.remove(listener)
    }

    private fun notifyChanges() {
        for (listener in listeners) {
            listener.onChanged(mapper.mapFromDBEntity(allItems))
        }
    }
}
