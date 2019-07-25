package com.company.project.common.core;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.internal.operators.observable.ObservableObserveOn;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.concurrent.CompletableFuture;

/**
 * @author xuzefan  2019/7/11 11:30
 */

@ControllerAdvice(basePackages = "com.company.project")
public class CustomResponseBody implements ResponseBodyAdvice{
    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if ( o instanceof Result) {
            return o;
        }
        return ResultGenerator.genSuccessResult(o);
    }
}
