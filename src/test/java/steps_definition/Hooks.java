package steps_definition;

import core.PageObjectBase;
import io.cucumber.java8.En;

public class Hooks implements En {
	public Hooks() {
		Before(0, () -> {
			PageObjectBase.initiateBrowser();
		});
				
		After(1, () -> {
			PageObjectBase.closeBrowser();
		});
	}
}
