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
    File currentDir = new File("");
    String externalImagePath = "file:" + File.separator + File.separator + File.separator
        + currentDir.getAbsoluteFile().getParent() +
        File.separator + "images" + File.separator + "public" + File.separator;

    registry
        .addResourceHandler("/api/images/public/**")
        .addResourceLocations(externalImagePath);
  }

}
