package board.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				/* board 패키지 내에 있는 RequestMapping으로 할당된 모든 URL을 선택 */
				.apis(RequestHandlerSelectors.basePackage("board"))
				/* 특정 경로가 아닌 모든 URL을 선택 */
				.paths(PathSelectors.any())
				.build();
	}
}
