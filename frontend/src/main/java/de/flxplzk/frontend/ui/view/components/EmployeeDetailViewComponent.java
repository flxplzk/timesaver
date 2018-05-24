package de.flxplzk.frontend.ui.view.components;

import com.vaadin.data.HasValue;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;
import de.flxplzk.frontend.backend.domain.Employee;
import de.flxplzk.frontend.backend.domain.TransactionSummary;
import de.flxplzk.vaadin.common.AbstractViewComponent;
import de.flxplzk.vaadin.mvvm.ItemBound;
import de.flxplzk.vaadin.mvvm.Property;
import de.flxplzk.vaadin.mvvm.View;
import de.flxplzk.vaadin.mvvm.ViewModelComposer;

@View(model = "crudEmployeeViewModel")
public class EmployeeDetailViewComponent extends AbstractViewComponent
    implements Paramizeable<Employee> {

    @ItemBound(to = "param")
    private final HasValue<Employee> param = new Property<>(new Employee());

    @ItemBound(to = "summaries")
    private final Grid<TransactionSummary> summaryGrid = new Grid<>();

    private final VerticalLayout rootComponent;

    private final CrudEmployeeViewComponent crudEmployeeViewComponent;

    public EmployeeDetailViewComponent(ViewModelComposer viewModelComposer) {
        super(viewModelComposer);
        this.crudEmployeeViewComponent = new CrudEmployeeViewComponent(viewModelComposer);
        this.rootComponent = new VerticalLayout(
                this.crudEmployeeViewComponent,
                this.summaryGrid
        );
        setCompositionRoot(rootComponent);

    }

    @Override
    public void withParam(Employee param) {
        this.crudEmployeeViewComponent.withParam(param);
    }
}
