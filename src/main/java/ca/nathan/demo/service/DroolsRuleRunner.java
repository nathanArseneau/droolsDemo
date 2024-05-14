package ca.nathan.demo.service;

import ca.nathan.demo.model.Facts;
import ca.nathan.demo.model.Results;
import ca.nathan.demo.model.Rule;
import lombok.extern.log4j.Log4j2;
import org.drools.core.event.DebugAgendaEventListener;
import org.drools.core.event.DebugRuleRuntimeEventListener;
import org.drools.core.facttemplates.Fact;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.io.Resource;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.utils.KieHelper;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class DroolsRuleRunner {
    private final KieContainer kContainer;

    public DroolsRuleRunner(KieContainer kContainer) {
        this.kContainer = kContainer;
    }

    public Results buildRecommendation(Facts facts) {
        var results = new Results();
        KieSession kieSession = kContainer.newKieSession();
        kieSession.addEventListener( new DebugAgendaEventListener() );
        kieSession.addEventListener( new DebugRuleRuntimeEventListener() );
        kieSession.setGlobal("results", results);
        kieSession.insert(facts);
        kieSession.fireAllRules();
        kieSession.dispose();
        return results;
    }

    public void update(String drl) {
        KieServices kieServices = KieServices.Factory.get();
        KieHelper kieHelper = new KieHelper();
        Resource resource1 = kieServices.getResources().newByteArrayResource(drl.getBytes());
        kieHelper.addResource(resource1, ResourceType.DRL);

//            KieBase kieBase = kieHelper.build();
//            kieServices.newKieBuilder(kieHelper.getKieContainer().getReleaseId())
//            KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
//            kieFileSystem.write(tempFile.getAbsolutePath());
//            KieBuilder kieBuilder = kieServices.newKieBuilder(tempFile.getAbsoluteFile());
//            kieBuilder.buildAll();
//            KieModule kieModule = kieBuilder.getKieModule();
        kContainer.updateToVersion(kieHelper.getKieContainer().getReleaseId());
    }

    public void validate(Rule rule) {
        var drl = rule.getRule();
        KieServices kieServices = KieServices.Factory.get();
        KieHelper kieHelper = new KieHelper();
        Resource resource1 = kieServices.getResources().newByteArrayResource(drl.getBytes());
        kieHelper.addResource(resource1, ResourceType.DRL);

        KieBase kieBase = kieHelper.build();
    }

    public Results execute(String drl, Facts facts) {
        KieServices kieServices = KieServices.Factory.get();
        KieHelper kieHelper = new KieHelper();
        Resource resource1 = kieServices.getResources().newByteArrayResource(drl.getBytes());
        kieHelper.addResource(resource1, ResourceType.DRL);
        var results = new Results();
        KieBase kieBase = kieHelper.build();
        KieSession kieSession = kieBase.newKieSession();
        kieSession.addEventListener( new DebugAgendaEventListener() );
        kieSession.addEventListener( new DebugRuleRuntimeEventListener() );
        kieSession.setGlobal("results", results);
        kieSession.insert(facts);
        kieSession.fireAllRules();
        kieSession.dispose();
        return results;
    }
}
