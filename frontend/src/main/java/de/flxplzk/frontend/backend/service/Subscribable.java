package de.flxplzk.frontend.backend.service;

public interface Subscribable<V> {

    Registration addListener(ServiceListener<V> listener);

    interface Registration {
        void remove();
    }

    interface ServiceListener<V> {

        void change(ChangeEvent<V> changeEvent);
    }

    class ChangeEvent<V> {

        private V changedEntity;

        public V getChangedEntity() {
            return changedEntity;
        }

        public ChangeEvent(V changedEntity) {
            this.changedEntity = changedEntity;
        }
    }
}
