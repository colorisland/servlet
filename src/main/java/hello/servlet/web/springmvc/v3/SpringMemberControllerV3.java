package hello.servlet.web.springmvc.v3;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/springmvc/v3/members")
public class SpringMemberControllerV3 {
    MemberRepository memberRepository = MemberRepository.getInstance();

    /**
     * 회원 폼
     * @return
     */
    @GetMapping("/new-form")
//    @RequestMapping(value = "/new-form", method = RequestMethod.GET)
    public String newForm() {
        return "new-form";
    }

    /**
     * 회원가입
     * @return
     */
    @PostMapping("/save")
    public String save(@RequestParam("username") String username, @RequestParam("age") int age,Model model) {

        // 회원 저장.
        Member member = new Member(username, age);
        memberRepository.save(member);

        // Model 설정 후 jsp 경로 문자열 리턴.
        model.addAttribute("member", member);

        return "save-result";
    }

    /**
     * 회원 리스트 조회
     * @return
     */
    @GetMapping
    public String members(Model model) {
        List<Member> members = memberRepository.findAll();

        model.addAttribute("members", members);
        return "members";
    }
}
