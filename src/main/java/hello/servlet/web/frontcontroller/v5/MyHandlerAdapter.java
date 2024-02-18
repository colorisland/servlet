package hello.servlet.web.frontcontroller.v5;

import hello.servlet.web.frontcontroller.ModelView;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface MyHandlerAdapter {
    /**
     * 지원가능한 핸들러인지 판별하기.
     * @param handler
     * @return
     */
    boolean supports(Object handler);

    /**
     * 비즈니스 로직을 수행하고 리턴타입을 ModelView 에 맞춰서 반환한다. (V3)
     * @param request
     * @param response
     * @param handler
     * @return ModelView
     */
    ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException;
}
