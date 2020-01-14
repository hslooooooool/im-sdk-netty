package vip.qsos.im.api

import org.springframework.web.bind.annotation.RestController
import vip.qsos.im.component.MessageDataComponent
import vip.qsos.im.component.MessagePusher
import vip.qsos.im.lib.server.model.ImException
import vip.qsos.im.model.BaseResult
import vip.qsos.im.model.db.AbsTableChatMessage
import vip.qsos.im.model.db.TableChatSession
import vip.qsos.im.model.form.SendMessageInSingleForm
import vip.qsos.im.model.type.EnumSessionType
import vip.qsos.im.repository.db.TableChatSessionRepository
import javax.annotation.Resource

@RestController
class AppSessionOfSingleControllerImpl : AppSessionOfSingleApi {
    @Resource
    private lateinit var messagePusher: MessagePusher
    @Resource
    private lateinit var mMessageDataComponent: MessageDataComponent
    @Resource
    private lateinit var mTableChatSessionRepository: TableChatSessionRepository

    override fun sendMessage(sessionId: Long, contentType: Int, content: String, sender: String): BaseResult {
        return this.send(SendMessageInSingleForm(sessionId, contentType, content, sender))
    }

    private fun send(message: SendMessageInSingleForm): BaseResult {
        /**验证会话*/
        val sessionId = message.sessionId
        val mTableChatSession: TableChatSession
        try {
            mTableChatSession = mTableChatSessionRepository.findById(sessionId).get()
        } catch (e: Exception) {
            throw ImException("会话不存在")
        }

        /**识别会话类型*/
        val sMessage: AbsTableChatMessage?
        when (message.sessionType) {
            EnumSessionType.SINGLE -> {
                mTableChatSession.getAccountList().map {
                    // 仅给未离群的账号发送消息
                    if (!it.leave && it.account != message.sender) {
                        try {
                            val msg = message.getMessage(it.account)
                            messagePusher.push(msg)
                        } catch (ignore: Exception) {
                            // 忽略未接收的消息用户
                        }
                    }
                }
                sMessage = mMessageDataComponent.save(sessionId, message.sessionType, message.getMessage("${message.sessionId}"))
            }
            else -> {
                throw ImException("发送失败，此接口不支持此聊天类型的发送处理")
            }
        }
        return BaseResult.data(sMessage, "发送成功")
    }

}