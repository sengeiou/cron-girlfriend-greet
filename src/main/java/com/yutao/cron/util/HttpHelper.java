package com.yutao.cron.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

/**
 * @author :RETURN
 * @date :2021/10/14 21:22
 */
public class HttpHelper {

    private static final OkHttpClient CLIENT = new OkHttpClient();

    private static Request request(String url, String method, HashMap<String, String> rBodyMap){
        RequestBody requestBody = null;
        if (Objects.nonNull(rBodyMap)){
            FormBody.Builder builder = new FormBody.Builder();
            rBodyMap.forEach(builder::add);
            requestBody = builder.build();
        }

        return new Request.Builder()
                .url(url)
                .method(method,requestBody)
                .build();
    }

    /**
     * okHttp3中的构造request时，传递method的参数，必须为大写
     */
    public static RequestProcess get(String url){
       return new RequestProcess(request(url,"GET",null));
    }

    public static RequestProcess post(String url, HashMap<String, String> rBodyMap){
        return new RequestProcess(request(url, "POST", rBodyMap));
    }

    @RequiredArgsConstructor
    @Slf4j
    public static class RequestProcess{

        private boolean isAsync;
        private RequestState state = RequestState.WAIT_REQUEST;
        private final Request request;
        private Response response;
        private Call call;
        private CallbackOnResponse cResponse;
        private CallbackOnFailure cFailure;

        private void call(){
            if (state != RequestState.WAIT_REQUEST){
                throw new RuntimeException("请求状态错误");
            }
            state = RequestState.PREP_REQUEST;
            call = CLIENT.newCall(request);
        }

        public RequestProcess async(){
            if (state != RequestState.PREP_REQUEST){
                throw new RuntimeException("请求状态错误");
            }

            isAsync = true;
            return this;
        }

        private RequestProcess asyncExecute(){
            final CallbackOnResponse r = this.cResponse;
            final CallbackOnFailure f = this.cFailure;

            call.enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    if (f == null){
                        return;
                    }
                    f.onFailure(call,e);
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    if (r == null){
                        return;
                    }
                    RequestProcess.this.response = response;
                    r.onResponse(call,response);
                }
            });
            state = RequestState.WAIT_RESPONSE;
            return this;
        }

        public RequestProcess onFailure(CallbackOnFailure onFailure){
            this.cFailure = onFailure;
            return this;
        }

        public RequestProcess onResponse(CallbackOnResponse onResponse){
            this.cResponse = onResponse;
            return this;
        }

        public RequestProcess execute(){
            call();
            if (state != RequestState.PREP_REQUEST){
                throw new RuntimeException("请求状态错误");
            }
            state = RequestState.REQUESTING;

            if (isAsync){
                return asyncExecute();
            }

            try {
                response = call.execute();
                state = RequestState.WAIT_RESPONSE;
            } catch (IOException e) {
                log.warn("http请求错误:{}",e.getMessage());
                e.printStackTrace();
            }
            return this;
        }

        public Response getResponse(){
            if (state != RequestState.WAIT_RESPONSE){
                throw new RuntimeException("请求状态错误");
            }

            return response;
        }

        public JSONObject getJson(){
            if (state != RequestState.WAIT_RESPONSE){
                throw new RuntimeException("请求状态错误");
            }

            try {
                return JSON.parseObject(Objects.requireNonNull(response.body()).string());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return new JSONObject();
        }
    }

    private enum RequestState{
        //等待请求
        WAIT_REQUEST(0),
        //准备请求
        PREP_REQUEST(1),
        //请求中
        REQUESTING(2),
        //等待响应
        WAIT_RESPONSE(3);

        RequestState(int state) {
        }
    }

    @FunctionalInterface
    private interface CallbackOnFailure{
        void onFailure(@NotNull Call call, @NotNull IOException e);
    }

    @FunctionalInterface
    private interface CallbackOnResponse{
        void onResponse(@NotNull Call call, @NotNull Response response) throws IOException;
    }
}
