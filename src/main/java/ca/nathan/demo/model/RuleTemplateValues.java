package ca.nathan.demo.model;

import lombok.Data;

import java.util.HashMap;
import java.util.List;

@Data
public class RuleTemplateValues {
    List<HashMap<String, String>> rules;
}
