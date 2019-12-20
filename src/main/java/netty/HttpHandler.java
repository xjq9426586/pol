package netty;

import com.alibaba.fastjson.JSONObject;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.AsciiString;
import io.netty.util.CharsetUtil;

import java.lang.reflect.InvocationTargetException;

public class HttpHandler extends SimpleChannelInboundHandler<FullHttpRequest> { // 1

    private AsciiString contentType = HttpHeaderValues.APPLICATION_JSON;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {


        httpResponse(ctx, msg, route(msg.uri()));
        
        
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelReadComplete");
        super.channelReadComplete(ctx);
        ctx.flush(); // 4
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("exceptionCaught");
        if(null != cause) cause.printStackTrace();
        if(null != ctx) ctx.close();
    }
    
    private void httpResponse(ChannelHandlerContext ctx,FullHttpRequest req,JSONObject json) {
    	 boolean keepAlive = HttpUtil.isKeepAlive(req);
    	DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                HttpResponseStatus.OK,
                Unpooled.wrappedBuffer(json.toJSONString().getBytes())); // 2

        HttpHeaders heads = response.headers();
        heads.add(HttpHeaderNames.CONTENT_TYPE, contentType + "; charset=UTF-8");
        heads.add(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes()); // 3
        heads.add(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        if (!keepAlive) {
            ctx.write(response).addListener(ChannelFutureListener.CLOSE);
        } else {
            ctx.write(response);
        }
    }
    private JSONObject route(String uri) throws InvocationTargetException, IllegalAccessException {
//    	JSONObject json = new JSONObject();
//    	if("/user".equals(uri)) {
//    	      json.put("user", "123");
//    	}
//    	if("/message".equals(uri)) {
//    		json.put("message", "123");
//    	}
        Dispatcher dispatcher = Dispatcher.getDispatcher();
        JSONObject json = (JSONObject)dispatcher.dispatcherRoute(uri);
        if(json == null){
            json = new JSONObject();
        }
    	return json;
    }

    private JSONObject parseJsonRequest(FullHttpRequest req) {
    	 ByteBuf jsonBuf = req.content();
    	 String jsonStr = jsonBuf.toString(CharsetUtil.UTF_8);
    	 
		return JSONObject.parseObject(jsonStr);
    }
}
