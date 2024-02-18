package hello.servlet.web.springmvc.v1;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SpringMemberSaveControllerV1 {
    MemberRepository memberRepository = MemberRepository.getInstance();

    @RequestMapping("/springmvc/v1/members/save")
    public ModelAndView process(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));

        // 회원 저장.
        Member member = new Member(username, age);
        memberRepository.save(member);

        // ModelAndView에 뷰이름 설정 후 모델 저장.
        ModelAndView mv = new ModelAndView("save-result");
        // ModelAndView는 모델을 넣는 편리한 메소드가 있다.
        mv.addObject("member", member);
        return mv;
    }
}
