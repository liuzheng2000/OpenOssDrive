package edu.sdut.Netty;

import edu.sdut.Entity.QrCodeLogin.CodeMap;
import edu.sdut.Entity.QrCodeLogin.QrCodeState;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.util.HashMap;
import java.util.Map;

import static edu.sdut.Entity.QrCodeLogin.QrCodeState.QrCodeStatePass;

/**
 * @author qingyun
 */
public class MyWebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {





    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("与客户端建立连接，通道开启！");
        //添加到channelGroup通道组
        MyChannelHandlerPool.channelGroup.add(ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("与客户端断开连接，通道关闭！");
        //添加到channelGroup 通道组
        MyChannelHandlerPool.channelGroup.remove(ctx.channel());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //首次连接是FullHttpRequest，处理参数 by zhengkai.blog.csdn.net
        if (null != msg && msg instanceof FullHttpRequest) {
            FullHttpRequest request = (FullHttpRequest) msg;
            String uri = request.uri();

            //处理url 参数
//            Map paramMap=getUrlParams(uri);
            getUrlParams(uri);

//            System.out.println("接收到的参数是："+JSON.toJSONString(paramMap));
            //如果url包含参数，需要处理
            if(uri.contains("?")){
                String newUri=uri.substring(0,uri.indexOf("?"));
                System.out.println(newUri);
                request.setUri(newUri);
            }
        }else if(msg instanceof TextWebSocketFrame){
            //正常的TEXT消息类型
            TextWebSocketFrame frame=(TextWebSocketFrame)msg;
            System.out.println("客户端收到服务器数据：" +frame.text());
            sendAllMessage(frame.text());
        }
        super.channelRead(ctx, msg);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {

    }

    private void sendAllMessage(String message){
        //收到信息后，群发给所有channel
        MyChannelHandlerPool.channelGroup.writeAndFlush( new TextWebSocketFrame(message));
    }

//    private static Map getUrlParams(String url){
//        Map<String,String> map = new HashMap<>();
//        url = url.replace("?",";");
//        if (!url.contains(";")){
//            return map;
//        }
//        if (url.split(";").length > 0){
//            String[] arr = url.split(";")[1].split("&");
//            for (String s : arr){
//                String key = s.split("=")[0];
//                String value = s.split("=")[1];
//                map.put(key,value);
//            }
//            return  map;
//
//        }else{
//            return map;
//        }
//    }

    /**
     * 处理URL参数，设置等待状态
     * @param url
     * @return
     */
    private static Map getUrlParams(String url){
        Map<String,String> map = new HashMap<>();
        url = url.replace("?",";");
        if (!url.contains(";")){
            return map;
        }

        //处理Url的参数
        if (url.split(";").length > 0){
            String[] arr = url.split(";")[1].split("&");
            for (String s : arr){
                String key = s.split("=")[0];
                String value = s.split("=")[1];
                map.put(key,value);
                //判断是否在等待状态 是则通过（此处相当于已经扫码）
                //此处需要使用 ==
                // 使用equals则出现对象不相等
                if (CodeMap.get(value)==(QrCodeState.QrCodeStateWait)){
                    //设置Pass扫码状态
                    CodeMap.replace(value,QrCodeStatePass);
                }
            }
            return  map;

        }else{
            return map;
        }
    }
}
