package hello.servlet.web.frontcontroller;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

// 뷰이름(논리이름)과 뷰를 그릴때 필요한 모델을 담고있는 DTO
@Getter @Setter
public class ModelView {
    private String viewName;

    private Map<String, Object> model = new HashMap<>();

    public ModelView(String viewName) {
        this.viewName = viewName;
    }
}
