package hello.servlet.basic.response;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * response
 */
@WebServlet(name = "responseHeaderServlet", urlPatterns = "/response-header")
public class ResponseHeaderServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // status-line
        response.setStatus(HttpServletResponse.SC_OK);

        content(response);
        cookie(response);
        redirect(response);

        // reponse-body
        response.getWriter().write("Ok");
    }

    private void content(HttpServletResponse response) {
        // response-headers
        response.setHeader("Content-Type","text/plain;charset=utf-8");
        response.setHeader("Cache-Control", "no-cache, no-store, mustrevalidate"); // 캐시 사용하지 않는 설정.
        response.setHeader("Pragma", "no-cache");
        response.setHeader("my-header","hello");
    }

    private void cookie(HttpServletResponse response) {
        // 쿠키는 헤더 추가하는 것처럼 똑같이 세팅할 수도 있다.
        //Set-Cookie: myCookie=good; Max-Age=600;
        //response.setHeader("Set-Cookie", "myCookie=good; Max-Age=600");
        Cookie cookie = new Cookie("myCookie", "delicious");
        cookie.setMaxAge(600); //600초
        response.addCookie(cookie);
    }

    private void redirect(HttpServletResponse response) throws IOException {
        //Status Code 302
        //Location: /basic/hello-form.html
        //response.setStatus(HttpServletResponse.SC_FOUND); //302
        //response.setHeader("Location", "/basic/hello-form.html");
        // sendRedirect 쓰면 코드가 더 짧아진다.
        response.sendRedirect("/basic/hello-form.html");
    }
}
