package cn.xmo.listener;

import cn.xmo.com.utils.MailUtil;

import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * @Author: Elves
 * @Description:
 * @Date: Created in 16:26 2019/8/12
 * @Modified By:
 */
public class EmailMessageListener implements MessageListener {
    @Override
    public void onMessage(Message message) {
        MapMessage mm  = (MapMessage) message;
        try {
            String to = mm.getStringProperty("to");
            String subject = mm.getStringProperty("subject");
            String content = mm.getStringProperty("content");
            MailUtil.sendMsg(to, subject, content);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
