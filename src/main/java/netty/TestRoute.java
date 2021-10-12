package netty;

import com.alibaba.fastjson.JSONObject;
import annotation.Route;

public class TestRoute {

    @Route(name = "/user")
    public JSONObject user() {
        JSONObject json = new JSONObject();
        json.put("user", "123");
        return json;
    }

    @Route(name = "/message")
    public JSONObject message() {
        JSONObject json = new JSONObject();
        json.put("message", "123");
        return json;
    }
}
