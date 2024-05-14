package ca.nathan.demo.model;

import lombok.Data;

import java.util.List;

@Data
public class ExecuteRulePayload {
    List<Rule> rules;
    Facts facts;

    @Data
    public static class Rule {
        String code;
        String spel;
    }
}
