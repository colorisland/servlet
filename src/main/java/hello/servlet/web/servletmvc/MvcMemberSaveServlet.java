package hello.servlet.web.servletmvc;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "mvcMemberSaveServlet", urlPatterns = "/servlet-mvc/members/save")
public class MvcMemberSaveServlet extends HttpServlet {
    MemberRepository memberRepository = MemberRepository.getInstance();
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));

        // 회원 저장.
        Member member = new Member(username, age);
        memberRepository.save(member);

        // Model 에 데이터를 보관한다.
        request.setAttribute("member", member);

        // 회원 저장 jsp
        String viewPath = "/WEB-INF/views/save-result.jsp";
        // 컨트롤러에서 view 로 이동하는 기능.
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
        // view로 진짜 이동.
        dispatcher.forward(request, response);
    }
}
