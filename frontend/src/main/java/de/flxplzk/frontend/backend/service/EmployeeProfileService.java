package de.flxplzk.frontend.backend.service;

import com.google.common.collect.Lists;
import de.flxplzk.frontend.backend.domain.EmployeeProfile;
import de.flxplzk.frontend.backend.repository.EmployeeProfileRepository;

import java.util.ArrayList;
import java.util.List;

public class EmployeeProfileService implements Subscribable<EmployeeProfile>{

    private final EmployeeProfileRepository repository;
    private List<ServiceListener> listeners = new ArrayList<>();

    public EmployeeProfileService(EmployeeProfileRepository repository) {
        this.repository = repository;
    }

    public List<EmployeeProfile> findAll() {
        return Lists.newArrayList(this.repository.findAll());
    }

    public void save(EmployeeProfile profile) {
        this.repository.save(profile);
        notifyChange(profile);
    }

    @Override
    public Registration addListener(ServiceListener<EmployeeProfile> listener) {
        this.listeners.add(listener);
        return new Registration() {
            @Override
            public void remove() {
                listeners.remove(listener);
            }
        };
    }

    @SuppressWarnings("unchecked")
    private void notifyChange(EmployeeProfile employeeProfile) {
        ChangeEvent<EmployeeProfile> event = new ChangeEvent<>(employeeProfile);
        this.listeners.forEach(serviceListener -> serviceListener.change(event));
    }
}
