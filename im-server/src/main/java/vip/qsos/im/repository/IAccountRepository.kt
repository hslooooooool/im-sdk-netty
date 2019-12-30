package vip.qsos.im.repository

import vip.qsos.im.model.db.TableChatAccount

/**
 * @author : 华清松
 * 聊天账号存储
 */
interface IAccountRepository {
    fun assign(): String
    fun findByAccount(account: String): TableChatAccount?
    fun list(used: Boolean?): List<TableChatAccount>
}