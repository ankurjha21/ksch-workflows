package ksch.registration;

import ksch.ApplicationFrame;
import lombok.extern.java.Log;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.annotation.mount.MountPath;

@MountPath("/registration/register-patient")
@AuthorizeInstantiation({"NURSE", "CLERK"})
@Log
public class RegisterPatientActivity extends ApplicationFrame {

    private String patientSearchTerm = null;

    public RegisterPatientActivity(PageParameters pageParameters) {
        super(pageParameters);

        WebMarkupContainer patientListContainer = new WebMarkupContainer("patientList");

        add(patientListContainer);
        patientListContainer.setVisible(false);

        PropertyModel<String> patientSearchTermModel = new PropertyModel<>(this, "patientSearchTerm");

        Form<?> patientSearchForm = new Form<Void>("patientSearchForm") {

            @Override
            protected void onSubmit() {
                String patientSearchTerm = patientSearchTermModel.getObject();

                patientListContainer.setVisible(true);
                log.info(patientSearchTerm);

                patientSearchTermModel.setObject(null);
            }
        };

        patientSearchForm.add(new TextField("patientSearchTerm", patientSearchTermModel));

        add(patientSearchForm);
    }
}
