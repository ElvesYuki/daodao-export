package cn.xmo.web.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.*;

/**
 * @Author: Elves
 * @Description:
 * @Date: Created in 16:03 2019/8/12
 * @Modified By:
 */
@Component
public class EmailProducer {
    @Autowired
    private JmsTemplate jmsTemplate;
    @Autowired
    private Destination destination;

    public void sendMsg(String to, String subject, String content){
        jmsTemplate.send(destination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                MapMessage mapMessage = session.createMapMessage();
                mapMessage.setStringProperty("to", to);
                mapMessage.setStringProperty("subject", subject);
                mapMessage.setStringProperty("content", content);
                return mapMessage;
            }
        });
    }
}
