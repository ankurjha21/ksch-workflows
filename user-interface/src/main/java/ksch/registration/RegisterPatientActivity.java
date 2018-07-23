package ksch.registration;

import ksch.ApplicationFrame;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.annotation.mount.MountPath;

@MountPath("/registration/register-patient")
@AuthorizeInstantiation({"NURSE", "CLERK"})
public class RegisterPatientActivity extends ApplicationFrame {

    public RegisterPatientActivity(PageParameters pageParameters) {
        super(pageParameters);

        WebMarkupContainer patientListContainer = new WebMarkupContainer("patientList");


        add(patientListContainer);

        patientListContainer.setVisible(true);

    }
}
