package ca.nathan.demo.service;

import ca.nathan.demo.model.ExecuteRulePayload;
import ca.nathan.demo.model.RuleTemplateValues;
import com.github.mustachejava.Mustache;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class RuleTemplateService {
    private final Mustache templateEngine;

    public RuleTemplateService(Mustache templateEngine) {
        this.templateEngine = templateEngine;
    }

    @SneakyThrows
    public String createDrl(ExecuteRulePayload executeRulePayload) {
        StringWriter writer = new StringWriter();
        RuleTemplateValues ruleTemplateValues = new RuleTemplateValues();

        List<HashMap<String, String>> rules = new ArrayList<>();
        for (ExecuteRulePayload.Rule r : executeRulePayload.getRules()) {
            HashMap<String, String> rule = new HashMap<>();
            rule.put("code", r.getCode());
            rule.put("spel", r.getSpel().replace("or", "||").replace("and", "&&"));
            rules.add(rule);
        }

        ruleTemplateValues.setRules(rules);
        templateEngine.execute(writer, ruleTemplateValues).flush();
        return writer.toString();
    }
}
