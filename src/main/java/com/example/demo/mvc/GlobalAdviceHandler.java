package com.example.demo.mvc;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;


/**
 * @author xiaolong7713
 */
@ControllerAdvice(annotations = {WithApiResult.class})
public class GlobalAdviceHandler implements ResponseBodyAdvice<Object> {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResultDTO<Void> handleException(Exception ex) {
        System.out.println("程序异常：" + ex.toString());
        return new ResultDTO<>("100101", ex.getMessage());
    }

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        // TODO Auto-generated method stub
        boolean flag1 = AnnotationUtils.findAnnotation(returnType.getMethod(), WithApiResult.class) == null;
        boolean flag2 = AnnotationUtils.findAnnotation(returnType.getDeclaringClass(), WithApiResult.class) == null;
        System.out.println("flag1: " + flag1);
        System.out.println("flag2: " + flag2);
        return false;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        // Annotation[] as = returnType.getMethodAnnotations();
        boolean flag1 = returnType.hasParameterAnnotation(WithApiResult.class);
        boolean flag2 = returnType.hasMethodAnnotation(WithApiResult.class);
        AnnotatedElement annotatedElement = returnType.getAnnotatedElement();
        Annotation as = annotatedElement.getDeclaredAnnotation(WithApiResult.class);

        System.out.println(returnType.getContainingClass().getAnnotation(WithApiResult.class));

        Annotation[] aa = returnType.getExecutable().getDeclaredAnnotations();
        System.out.println(returnType.getClass().toString());
        Annotation b = returnType.getClass().getAnnotation(WithApiResult.class);

        if (body instanceof ResultDTO) {
            return body;
        }

        // 类注解
        Annotation withApiResultAnnotation = returnType.getContainingClass().getAnnotation(WithApiResult.class);
        if (withApiResultAnnotation != null) {
            return ResultDTO.success(body);
        }

        // 方法注解
        if (returnType.hasMethodAnnotation(WithApiResult.class)) {
            return ResultDTO.success(body);
        }
        return body;
    }
}
