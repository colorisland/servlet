package hello.servlet.basic.request;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * 1. 파라미터 전송 기능
 * 2. url : http://localhost:8080/request-param?username=hello&age=20
 */
@WebServlet(name = "requestParamServlet", urlPatterns = "/request-param")
public class RequestParamServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("[전체 파라미터 조회] - start");
        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> System.out.println(paramName+"="+request.getParameter(paramName)));
        System.out.println("[전체 파라미터 조회] - end");

        // ?username=hello&username=hello2 이런식으로 같은 키값으로 여러 파라미터를 보낼 수 있다.
        // 그냥 단일 조회하면 제일 먼저 입력한 값이 나오지만 (hello), 복수 파라미터 조회도 할 수 있다.
        System.out.println("[이름이 같은 복수 파라미터 조회]");
        String[] usernames = request.getParameterValues("username");
        for (var name : usernames) {
            System.out.println("username = " + name);
        }
        response.getWriter().write("OK");
    }
}
