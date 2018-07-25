package ksch.registration;

import ksch.ApplicationFrame;
import lombok.extern.java.Log;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.leanhis.patientmanagement.Gender;
import org.leanhis.patientmanagement.Patient;
import org.leanhis.patientmanagement.PatientService;
import org.wicketstuff.annotation.mount.MountPath;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static java.time.temporal.ChronoUnit.YEARS;

@MountPath("/registration/register-patient")
@AuthorizeInstantiation({"NURSE", "CLERK"})
@Log
public class RegisterPatientActivity extends ApplicationFrame {

    @SpringBean
    private PatientService patientService;

    private String patientSearchTerm = null;

    public RegisterPatientActivity(PageParameters pageParameters) {
        super(pageParameters);

        WebMarkupContainer patientListContainer = new WebMarkupContainer("patientList");
        WebMarkupContainer noSearchResultsMessageContainer = new WebMarkupContainer("noSearchResultsMessage");

        add(patientListContainer);
        add(noSearchResultsMessageContainer);

        patientListContainer.setVisible(false);
        noSearchResultsMessageContainer.setVisible(false);

        PropertyModel<String> patientSearchTermModel = new PropertyModel<>(this, "patientSearchTerm");

        Form<?> patientSearchForm = new Form<Void>("patientSearchForm") {

            @Override
            protected void onSubmit() {
                String patientSearchTerm = patientSearchTermModel.getObject();

                List<Patient> matchingPatients = patientService.findBy(patientSearchTerm);


                if (matchingPatients.size() > 0) {
                    ListView lv = new ListView<Patient>("patients", matchingPatients) {
                        @Override
                        protected void populateItem(ListItem<Patient> item) {
                            Patient patient = item.getModelObject();

                            item.add(new Label("medicalRecordNumber", patient.getMedicalRecordNumber()));
                            item.add(new Label("name", patient.getName()));
                            item.add(new Label("gender", patient.getGender().toString().toLowerCase()));
                            item.add(new Label("age", patientService.getAgeInYears(patient)));
                        }
                    };

                    patientListContainer.removeAll(); // required to be able to re-submit search
                    patientListContainer.add(lv);
                    patientListContainer.setVisible(true);
                } else {
                    noSearchResultsMessageContainer.setVisible(true);
                }

                patientSearchTermModel.setObject(null);
            }
        };

        patientSearchForm.add(new TextField("patientSearchTerm", patientSearchTermModel));
        add(patientSearchForm);
    }

    private void createDummyPatients() {
        Patient patient1 = new Patient() {
            @Override
            public UUID getId() {
                return UUID.randomUUID();
            }

            @Override
            public String getMedicalRecordNumber() {
                return "KSA-18-1001";
            }

            @Override
            public String getName() {
                return "John Doe";
            }

            @Override
            public LocalDate getDateOfBirth() {
                return LocalDate.now().minus(25, YEARS);
            }

            @Override
            public Gender getGender() {
                return Gender.MALE;
            }
        };

        Patient patient2 = new Patient() {
            @Override
            public UUID getId() {
                return UUID.randomUUID();
            }

            @Override
            public String getMedicalRecordNumber() {
                return "KSA-18-1002";
            }

            @Override
            public String getName() {
                return "Jane Doe";
            }

            @Override
            public LocalDate getDateOfBirth() {
                return LocalDate.now().minus(20, YEARS);
            }

            @Override
            public Gender getGender() {
                return Gender.FEMALE;
            }
        };

        patientService.create(patient1);
        patientService.create(patient2);
    }
}
