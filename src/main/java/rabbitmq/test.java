package rabbitmq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;

/**
 * @Auther: xujunqian
 * @Date: 2019/11/20 0020 17:39
 * @Description:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/beans.xml")
public class test {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Test
    public void test(){

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("id", "1");
        map.put("name", "呵呵呵呵");
        //根据key发送到对应的队列
        rabbitTemplate.convertAndSend("test_key", map);
    }

}
