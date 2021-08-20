package com.hit.client;

import com.hit.view.CacheUnitView;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class CacheUnitClientObserver implements PropertyChangeListener {

    private CacheUnitClient client;

    public CacheUnitClientObserver() {
        client = new CacheUnitClient();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName().equals("Load a Request") || evt.getPropertyName().equals("Show Stats")) {
            CacheUnitView observable = (CacheUnitView)evt.getSource();
            String response = client.send((String)evt.getNewValue());
            observable.updateUIData(response);
        }

    }
}
