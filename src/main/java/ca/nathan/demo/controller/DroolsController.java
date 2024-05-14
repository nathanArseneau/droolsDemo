package ca.nathan.demo.controller;

import ca.nathan.demo.model.ExecuteRulePayload;
import ca.nathan.demo.model.Facts;
import ca.nathan.demo.model.Results;
import ca.nathan.demo.model.Rule;
import ca.nathan.demo.service.DroolsRuleRunner;
import ca.nathan.demo.service.RuleTemplateService;
import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DroolsController {

    @Autowired
    DroolsRuleRunner runner;

    @Autowired
    RuleTemplateService ruleTemplateService;

    @PostMapping("/")
    Results recommendation(@RequestBody Facts facts) {
        return runner.buildRecommendation(facts);
    }

    @PostMapping("/validate")
    void validate(@RequestBody Rule rule) {
        System.out.println(rule.getRule());
        runner.validate(rule);
    }

    @PostMapping("/execute")
    Results loadAndRun(@RequestBody ExecuteRulePayload executeRulePayload) {
        var drlStr = ruleTemplateService.createDrl(executeRulePayload);
        return runner.execute(drlStr, executeRulePayload.getFacts());
    }

    @PostMapping("/update")
    void update(@RequestBody ExecuteRulePayload executeRulePayload) {
        var drlStr = ruleTemplateService.createDrl(executeRulePayload);
        runner.update(drlStr);
    }
}
