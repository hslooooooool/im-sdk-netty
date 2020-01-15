package vip.qsos.im.repository.db

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import vip.qsos.im.model.db.TableChatSessionOfGroup

@Repository
interface TableChatGroupRepository : JpaRepository<TableChatSessionOfGroup, Long> {

    fun findBySessionId(sessionId: Long): TableChatSessionOfGroup?
    fun findByName(name: String): List<TableChatSessionOfGroup>
    fun findByNameLike(name: String): List<TableChatSessionOfGroup>

}