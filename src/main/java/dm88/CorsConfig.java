package dm88;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration 
public class CorsConfig implements WebMvcConfigurer { 
// Global Cors configuration  IMPORTANT UNCOMMENT ONLY IF IT IS NEEDED
@Override
public void addCorsMappings(CorsRegistry registry) {
	
	/* registry.addMapping("/api/**");
	.allowedOrigins("*")
	.allowedMethods("PUT", "DELETE","GET","POST")
	.allowedHeaders("content-type", "header2", "header3")
	.exposedHeaders("content-type", "header2")
	.allowCredentials(false).maxAge(3600);	*/
	
	
	
}

} 







