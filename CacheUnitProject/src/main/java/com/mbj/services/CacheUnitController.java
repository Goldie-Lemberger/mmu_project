package main.java.com.mbj.services;

import main.java.com.mbj.dm.DataModel;

public class CacheUnitController<T> {
    CacheUnitService<T> service;

    public CacheUnitController() {
        service = new CacheUnitService<>();

    }

    public boolean delete(DataModel<T>[] dataModels) {
        return service.delete(dataModels);
    }

    public DataModel<T>[] get(DataModel<T>[] dataModels) {
        return service.get(dataModels);

    }

    public boolean update(DataModel<T>[] dataModels) {
        return service.update(dataModels);

    }

    public void saveCacheToIDao(){
        service.saveCacheToIDao();
    }

    public String getStats() {
        return service.showStatisticsString(); //controller -> service
    }

}
