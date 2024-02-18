package hello.servlet.web.frontcontroller.v5;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v3.ControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;
import hello.servlet.web.frontcontroller.v4.controller.MemberFormControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberListControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberSaveControllerV4;
import hello.servlet.web.frontcontroller.v5.adapter.ControllerV3HandlerAdapter;
import hello.servlet.web.frontcontroller.v5.adapter.ControllerV4HandlerAdapter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "frontControllerServletV5", urlPatterns = "/front-controller/v5/*")
public class FrontControllerServletV5 extends HttpServlet {
    private Map<String, Object> handerMappingMap = new HashMap<>();
    private final List<MyHandlerAdapter> handlerAdapters = new ArrayList<>();

    public FrontControllerServletV5() {
        initHandlerMappingMap();

        initHandlerAdapters();
    }

    private void initHandlerAdapters() {
        handlerAdapters.add(new ControllerV3HandlerAdapter());
        handlerAdapters.add(new ControllerV4HandlerAdapter());
    }

    private void initHandlerMappingMap() {
        // V3
        handerMappingMap.put("/front-controller/v5/v3/members/new-form", new MemberFormControllerV3());
        handerMappingMap.put("/front-controller/v5/v3/members/save", new MemberSaveControllerV3());
        handerMappingMap.put("/front-controller/v5/v3/members", new MemberListControllerV3());
        // V4
        handerMappingMap.put("/front-controller/v5/v4/members/new-form", new MemberFormControllerV4());
        handerMappingMap.put("/front-controller/v5/v4/members/save", new MemberSaveControllerV4());
        handerMappingMap.put("/front-controller/v5/v4/members", new MemberListControllerV4());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 핸들러 조회. (가입인지, 저장인지, 조회인지 URI에 따라서)
        Object handler = getHandler(request);
        // 맞는 핸들러가 없으면 404 에러 반환.
        if (handler == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // 핸들러 어댑터 조회. 조회한 핸들러에 맞는 게 V3 어댑터인지, V4 어댑터인지..
        MyHandlerAdapter adapter = getHandlerAdapter(handler);

        // 컨트롤러 로직 처리 후 모델뷰 반환. (뷰 이름과 뷰에 그려질 model 정보를 가지고 있다.)
        ModelView mv = adapter.handle(request, response, handler);

        // 최종적으로 뷰 가져오기. 뷰는 물리적 뷰 경로를 가지고 있다.
        MyView myView = viewResolver(mv.getViewName());

        // 이제 뷰에 들어갈 model을 render함수에 전달해서 진짜 뷰를 그린다.
        // (V3는 request에 데이터를 저장하지 않고 model 맵에 저장하기 때문에 model이 꼭 필요하다.)
        myView.render(mv.getModel(),request, response);
    }

    /**
     * 핸들러를 처리할 수 있는 핸들러 어댑터 조회
     * @param handler
     * @return
     */
    private MyHandlerAdapter getHandlerAdapter(Object handler) {
        // 핸들러 어댑터중에 파라미터로 온 핸들러를 처리할 수 있는 어댑터를 반환한다.
        for (MyHandlerAdapter adapter : handlerAdapters) {
            if (adapter.supports(handler)) {
                return adapter;
            }
        }
        throw new IllegalArgumentException("Cannot find Handler Adapter");
    }

    /**
     * 핸들러 조회
     * @param request
     * @return
     */
    private Object getHandler(HttpServletRequest request) {
        // service 코드는 기존 ControllerV3의 코드랑 비슷하다.
        // /front-controller/v1/members <- 딱 URI 부분을 얻을 수 있다.
        String requestURI = request.getRequestURI();

        // 다형성을 이용해 컨트롤러 추출. (회원가입/저장/목록 불러오기 중에서 찾아서..)
        return handerMappingMap.get(requestURI);
    }

    /**
     * view 리턴
     * @param viewName
     * @return
     */
    private MyView viewResolver(String viewName) {
        MyView view = new MyView("/WEB-INF/views/" + viewName + ".jsp");
        return view;
    }
}
