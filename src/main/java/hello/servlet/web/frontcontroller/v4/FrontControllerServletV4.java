package hello.servlet.web.frontcontroller.v4;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v3.ControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;
import hello.servlet.web.frontcontroller.v4.controller.MemberFormControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberListControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberSaveControllerV4;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// * 부분에 어떤 url이 들어와도 그 전 경로가 같으면 무조건 이 서블릿을 통한다.
@WebServlet(name = "frontControllerServletV4", urlPatterns = "/front-controller/v4/*")
public class FrontControllerServletV4 extends HttpServlet {
    private Map<String, ControllerV4> controllerMap = new HashMap<>();

    public FrontControllerServletV4() {
        controllerMap.put("/front-controller/v4/members/new-form", new MemberFormControllerV4());
        controllerMap.put("/front-controller/v4/members/save", new MemberSaveControllerV4());
        controllerMap.put("/front-controller/v4/members", new MemberListControllerV4());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // /front-controller/v1/members <- 딱 URI 부분을 얻을 수 있다.
        String requestURI = request.getRequestURI();

        // 다형성을 이용해 컨트롤러 추출.
        ControllerV4 controller = controllerMap.get(requestURI);

        // 맞는 컨트롤러가 없으면 404 에러 반환.
        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // request로부터 paramMap생성.
        // 디테일한 로직을 메소드로 만들어서 코드 레벨을 맞춘다.
        Map<String, String> map = createParamMap(request);
        Map<String, Object> model = new HashMap<>();

        // 잘 조회됐으면 알맞은 컨트롤러 호출.
        String viewName = controller.process(map, model);

        // 물리 뷰네임 가져오기.
        MyView view = viewResolver(viewName);

        view.render(model,request,response);
    }

    private MyView viewResolver(String viewName) {
        MyView view = new MyView("/WEB-INF/views/" + viewName + ".jsp");
        return view;
    }

    private Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();
        request.getParameterNames().asIterator()
                .forEachRemaining(paramName->map.put(paramName, request.getParameter(paramName)));
        return map;
    }
}
