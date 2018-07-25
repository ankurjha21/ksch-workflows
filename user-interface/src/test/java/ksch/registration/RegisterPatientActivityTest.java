package ksch.registration;

import ksch.WebPageTest;
import ksch.registration.RegisterPatientActivity;
import org.apache.wicket.util.tester.FormTester;
import org.junit.Test;

public class RegisterPatientActivityTest extends WebPageTest {

    @Test
    public void should_render_registration_landing_page() {
        login("user", "pwd");

        tester.startPage(RegisterPatientActivity.class);
        tester.assertRenderedPage(RegisterPatientActivity.class);

        FormTester formTester = tester.newFormTester("patientSearchForm", false);
        formTester.submit();

        System.out.println(tester.getLastRenderedPage().getMarkup());
    }
}
