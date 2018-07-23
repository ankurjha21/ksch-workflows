package ksch.registration;

import ksch.ApplicationFrame;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.annotation.mount.MountPath;

@MountPath("/registration/edit-patient")
@AuthorizeInstantiation({"NURSE", "CLERK"})
public class EditPatientDetailsActivity extends ApplicationFrame {

    public EditPatientDetailsActivity(PageParameters pageParameters) {
        super(pageParameters);
    }
}
