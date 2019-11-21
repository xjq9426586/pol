package rabbitmq;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

import java.io.IOException;

/**
 * @Auther: xujunqian
 * @Date: 2019/11/20 0020 17:37
 * @Description:
 */
public class Receiver implements MessageListener {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public void onMessage(Message msg) {
        try {
            //msg就是rabbitmq传来的消息，需要的同学自己打印看一眼
            // 使用jackson解析
            JsonNode jsonData = MAPPER.readTree(msg.getBody());
            System.out.println("收到消息:" + jsonData);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

