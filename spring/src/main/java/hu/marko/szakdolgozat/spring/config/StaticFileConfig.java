package hu.marko.szakdolgozat.spring.config;

import java.io.File;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class StaticFileConfig implements WebMvcConfigurer {

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    String externalImagePath = "file:" + File.separator + File.separator + File.separator
        + System.getProperty("java.class.path").split(File.pathSeparator)[0].split("spring")[0] +
        "images" + File.separator + "public" + File.separator;

    registry
        .addResourceHandler("/api/images/public/**")
        .addResourceLocations(externalImagePath);
  }

}
