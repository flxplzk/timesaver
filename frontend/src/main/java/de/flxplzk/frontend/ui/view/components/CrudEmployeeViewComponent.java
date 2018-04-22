package de.flxplzk.frontend.ui.view.components;

import com.vaadin.data.HasValue;
import com.vaadin.shared.Registration;
import com.vaadin.ui.*;
import de.flxplzk.frontend.backend.domain.EmployeeProfile;
import de.flxplzk.vaadin.common.AbstractViewComponent;
import de.flxplzk.vaadin.mvvm.*;

import java.util.ArrayList;
import java.util.List;

@View(model = "crudEmployeeViewModel")
public class CrudEmployeeViewComponent extends AbstractViewComponent implements HasValue.ValueChangeListener {

    private List<Registration> registrations = new ArrayList<>();

    @ItemBound(to = "firstName")
    private final TextField mFirstName = new TextField("Vorname:");

    @ItemBound(to = "lastName")
    private final TextField mLastName = new TextField("Nachname:");

    @ItemBound(to = "employeeDate")
    private final DateField mEmployeeDate = new DateField("Einstellungsdatum:");

    @ItemBound(to = "selectedProfile")
    @ListingBound(to = "profiles")
    private final ComboBox<EmployeeProfile> profileComboBox = new ComboBox<>("Anstellungsart:");

    @OnClick(method = "save")
    private final Button mSaveButton = new Button("Speichern");
    private VerticalLayout rootLayout = new VerticalLayout(
            mFirstName,
            mLastName,
            mEmployeeDate,
            profileComboBox,
            mSaveButton);

    public CrudEmployeeViewComponent(ViewModelComposer viewModelComposer) {
        super(viewModelComposer);
        build();
    }

    private void build() {
        this.mSaveButton.setEnabled(false);
        setCompositionRoot(rootLayout);
        this.profileComboBox.setItemCaptionGenerator(EmployeeProfile::getProfileName);
        this.rootLayout.setMargin(true);
        this.rootLayout.setSizeFull();
        this.mEmployeeDate.setSizeFull();
        this.mFirstName.setSizeFull();
        this.mLastName.setSizeFull();
        this.profileComboBox.setSizeFull();
        this.mSaveButton.setSizeFull();
        this.registerListeners();
    }

    @SuppressWarnings("unchecked")
    private void registerListeners() {
        registrations.add(this.mFirstName.addValueChangeListener(this));
        registrations.add(this.mLastName.addValueChangeListener(this));
        registrations.add(this.mEmployeeDate.addValueChangeListener(this));
        registrations.add(this.profileComboBox.addValueChangeListener(this));
    }

    @Override
    public void valueChange(HasValue.ValueChangeEvent event) {
        if (this.profileComboBox.getValue() == null
                || this.mLastName.getValue().equals("")
                || this.mFirstName.getValue().equals("")) {
            this.mSaveButton.setEnabled(false);
        } else {
            this.mSaveButton.setEnabled(true);
        }
    }
}