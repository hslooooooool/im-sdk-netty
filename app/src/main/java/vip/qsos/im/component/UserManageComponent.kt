package vip.qsos.im.component

import vip.qsos.im.model.AppException
import vip.qsos.im.model.AppUser
import vip.qsos.im.model.db.TableFriend
import vip.qsos.im.model.db.TableUser
import javax.transaction.Transactional

interface UserManageComponent {

    @Transactional
    fun register(name: String, password: String): TableUser

    fun login(name: String, password: String): TableUser
    fun findAll(): List<AppUser>
    fun findByName(name: String): AppUser?
    fun findById(userId: Long): AppUser
    fun findMine(userId: Long): TableUser
    fun findByNameLike(name: String): List<AppUser>
    fun findByImAccount(account: String): AppUser?
    /**分配账号*/
    @Throws(AppException::class)
    fun assignImAccount(user: TableUser): TableUser

    fun addFriend(sender: AppUser, receiver: AppUser): TableFriend
    fun findFriend(userId: Long, friendId: Long): TableFriend?

}