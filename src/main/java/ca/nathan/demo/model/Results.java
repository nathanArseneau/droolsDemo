package ca.nathan.demo.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Results {
    List<String> ruleCode = new ArrayList<>();
}
