package runners;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(strict=true, plugin= {
		"pretty"}, features="src/test/resources/feature_files", glue="classpath:steps_definition", tags="@shoes")
public class RunCucumberTest {

}
