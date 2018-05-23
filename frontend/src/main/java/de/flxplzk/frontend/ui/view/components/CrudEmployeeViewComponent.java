package de.flxplzk.frontend.ui.view.components;

import com.vaadin.data.HasValue;
import com.vaadin.shared.Registration;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import de.flxplzk.frontend.backend.domain.Employee;
import de.flxplzk.frontend.backend.domain.EmployeeProfile;
import de.flxplzk.vaadin.common.AbstractViewComponent;
import de.flxplzk.vaadin.mvvm.*;

import java.util.ArrayList;
import java.util.List;

@View(model = "crudEmployeeViewModel")
public class CrudEmployeeViewComponent extends AbstractViewComponent
        implements HasValue.ValueChangeListener, Paramizeable<Employee> {

    private List<Registration> registrations = new ArrayList<>();

    @ItemBound(to = "param")
    private final HasValue<Employee> param = new Property<>(new Employee());

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

    @OnClick(method = "cancel")
    private final Button mCancelButton = new Button("Abrechen");

    private VerticalLayout rootLayout = new VerticalLayout(
            this.mFirstName,
            this.mLastName,
            this.mEmployeeDate,
            this.profileComboBox,
            this.mSaveButton,
            this.mCancelButton);

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
        this.mSaveButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
        this.mCancelButton.setSizeFull();
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

    @Override
    public void withParam(Employee param) {
        this.param.setValue(param);
    }
}
