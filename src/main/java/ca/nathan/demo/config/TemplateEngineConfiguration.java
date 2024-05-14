package ca.nathan.demo.config;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import java.io.File;

@Configuration
public class TemplateEngineConfiguration {
    public static final String template = "drl.mustache";

    @SneakyThrows
    @Bean
    Mustache templateEngine(){
        File file = ResourceUtils.getFile("classpath:" + template);
        String templateStr = file.toString();
        MustacheFactory mf = new DefaultMustacheFactory();
        return mf.compile(templateStr);
    }
}
