package vip.qsos.im.model.form

import com.fasterxml.jackson.annotation.JsonIgnore
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import vip.qsos.im.lib.server.model.Message
import vip.qsos.im.model.type.ChatType
import java.io.Serializable
import javax.validation.constraints.NotNull

/**
 * @author : 华清松
 * 发送的公告消息实体
 */
@ApiModel(description = "发送的公告消息实体")
data class SendNoticeForm constructor(
        @ApiModelProperty(value = "消息类型，如0:文本、1:文件等，默认：0")
        @NotNull(message = "消息类型不能为空")
        var action: String = "0",
        @ApiModelProperty(value = "消息内容", required = true)
        @NotNull(message = "消息内容不能为空")
        var content: String,
        @ApiModelProperty(value = "消息发送者账号", required = true)
        @NotNull(message = "发送者账号不能为空")
        var sender: String,
        @ApiModelProperty(value = "消息接收者集合账号", required = true)
        @NotNull(message = "接收者账号不能为空")
        var receiver: List<String>
) : ISendForm, Serializable {

    companion object {
        private const val serialVersionUID = 1L
    }

    @JsonIgnore
    override var chatType: ChatType = ChatType.NOTICE

    @JsonIgnore
    fun getMessageList(): List<Message> {
        return receiver.map {
            Message(
                    action = this.action,
                    title = "公告",
                    content = this.content,
                    sender = this.sender,
                    receiver = it,
                    extra = this.chatType.name,
                    format = Message.Format.JSON.value
            )
        }

    }

}