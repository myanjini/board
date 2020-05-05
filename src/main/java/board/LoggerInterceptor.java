package board;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.lang.Nullable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoggerInterceptor extends HandlerInterceptorAdapter {
	
	//	해당 컨트롤러 실행 전 로그를 출력
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		log.debug("=============== START ===============");
		log.debug(" Request URI : " + request.getRequestURI());
		return super.preHandle(request, response, handler);
	}

	//	해당 컨트롤러 실행 후 로그를 출력
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			@Nullable ModelAndView modelAndView) throws Exception {
		log.debug("===============  END  ===============");
		super.postHandle(request, response, handler, modelAndView);
	}
}
