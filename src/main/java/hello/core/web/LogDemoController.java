package hello.core.web;


import hello.core.common.MyLogger;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class LogDemoController {

    private final LogDemoService logDemoService;
    // request scope는 유저의 요청이 들어올 때 비로소 컨테이너에 빈으로 생성되기 때문에
    // 처음에 컨테이너가 생성될 때는 주입할 수가 없어 가져올 수도 없다.
    // 따라서 유저의 요청이 들어올 때까지 기다렸다가 호출해야 하는데, 이를 위해 처음에 컨테이너가 생성될 때는
    // Provider를 주입하고, 이후에 사용하고 싶을 때 Provider를 이용해서 컨테이너로부터 해당 빈을 가져오면 된다.
    // 이 시점에는 이미 유저의 요청이 들어오고 난 후 컨테이너에 빈이 생성되었을 것이고,
    // Provider를 통해 이 시점에 컨테이너로부터 빈 객체를 요구하면 이미 존재하기 때문에 성공적으로 가져올 수 있다.
    // 이를 지연 로딩 방식이라고 한다. 왜냐? 처음에 싱글톤 빈에 주입을 할 수 없으니 기다렸다가 나중에 요청하기 때문

    // 또는 @Scope(proxyMode = ScopedProxyMode.TARGET_CLASS) 설정을 통해서
    // 처음에 주입할 떄 CGLIB을 통해 생성된 가짜 프록시 객체를 주입할 수 있음
    private final ObjectProvider<MyLogger> myloggerProvider;

    @RequestMapping("log-demo")
    @ResponseBody
    public String logDemo(HttpServletRequest request) {
        MyLogger myLogger = myloggerProvider.getObject();
        String requestURL = request.getRequestURL().toString();
        myLogger.setRequestURL(requestURL);

        myLogger.log("controller test");
        logDemoService.logic("testId");

        return "OK";
    }
}
