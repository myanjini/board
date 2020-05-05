package board.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Aspect
@Slf4j
public class LoggerAspect {
	
	// 호출된 메서드의 종류(Controller, Serivce, Mapper)를 구분하고 
	// 메서드의 정보를 로그로 출력
	@Around("execution(* board..controller.*Controller.*(..)) or execution(* board..service.*Impl.*(..)) or execution(* board..mapper.*Mapper.*(..)) ")
	public Object logPrin(ProceedingJoinPoint joinPoint) throws Throwable {
		String type = "";
		String name = joinPoint.getSignature().getDeclaringTypeName(); // 메서드가 포함된 클래스 이름
		if (name.indexOf("Controller") > -1) {
			type = "Controller";
		} else if (name.indexOf("Service") > -1) {
			type = "Serivce";
		} else if (name.indexOf("Mapper") > -1) {
			type = "Mapper";
		}
		
		log.debug(type + " : " + name + "." + joinPoint.getSignature().getName() + "()");
		//        ~~~~           ~~~~         ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		//        종류           패키지+클래스          메서드()
		
		return joinPoint.proceed();
	}
}
