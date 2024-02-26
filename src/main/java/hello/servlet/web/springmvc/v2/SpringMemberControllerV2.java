package hello.servlet.web.springmvc.v2;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/springmvc/v2/members")
public class SpringMemberControllerV2 {
    MemberRepository memberRepository = MemberRepository.getInstance();

    /**
     * 회원 폼
     * @return
     */
    @RequestMapping("/new-form")
    public ModelAndView newForm() {
        return new ModelAndView("new-form");
    }

    /**
     * 회원가입
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/save")
    public ModelAndView save(HttpServletRequest request, HttpServletResponse response) {
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

    /**
     * 회원 리스트 조회
     * @return
     */
    @RequestMapping
    public ModelAndView members() {
        List<Member> members = memberRepository.findAll();

        ModelAndView mv = new ModelAndView("members");
        mv.addObject("members", members);
        return mv;
    }
}
