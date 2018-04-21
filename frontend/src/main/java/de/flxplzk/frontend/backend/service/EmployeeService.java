package de.flxplzk.frontend.backend.service;

import com.google.common.collect.Lists;
import de.flxplzk.frontend.backend.domain.Employee;
import de.flxplzk.frontend.backend.repository.EmployeeRepository;

import java.util.ArrayList;
import java.util.List;

public class EmployeeService implements Subscribable<Employee>{

    private final EmployeeRepository repository;
    private final List<ServiceListener> serviceListeners = new ArrayList<>();

    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    public void save(Employee model) {
        this.repository.save(model);
        notifyChange(model);
    }

    public List<Employee> findAll() {
        return Lists.newArrayList(this.repository.findAll());
    }

    public void delete(Employee employee) {
        this.repository.delete(employee);
        notifyChange(employee);
    }

    @SuppressWarnings("unchecked")
    private void notifyChange(Employee employee) {
        ChangeEvent<Employee> event = new ChangeEvent<>(employee);
        this.serviceListeners.forEach(serviceListener -> serviceListener.change(event));
    }

    public List<Employee> findByFilter(String criteria) {
        return this.repository.findByFirstNameContainingOrLastNameContaining(criteria, criteria);
    }

    @Override
    public Registration addListener(ServiceListener<Employee> listener) {
        this.serviceListeners.add(listener);
        return () -> serviceListeners.remove(listener);
    }
}
