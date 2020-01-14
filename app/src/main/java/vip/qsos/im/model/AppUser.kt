package vip.qsos.im.model

import io.swagger.annotations.ApiModelProperty
import vip.qsos.im.model.db.TableUser

/**
 * @author : 华清松
 * APP用户
 */
data class AppUser(
        @ApiModelProperty(value = "用户ID")
        var id: Long,
        @ApiModelProperty(value = "用户名称")
        var name: String = "",
        @ApiModelProperty(value = "关联的消息账号")
        var imAccount: String = "",
        @ApiModelProperty(value = "用户头像")
        var avatar: String? = null
) {
    companion object {
        fun getBo(table: TableUser): AppUser {
            return AppUser(table.userId, table.name, table.imAccount, table.avatar)
        }
    }
}