package main.java.com.mbj.services;

import java1.algorithm.LRUAlgoCacheImpl;
import main.java.com.mbj.dao.DaoFileImpl;
import main.java.com.mbj.dao.IDao;
import main.java.com.mbj.dm.DataModel;
import main.java.com.mbj.memory.CacheUnit;


public class CacheUnitService<T> {
    private IDao<Long, DataModel<T>> iDao;
    private CacheUnit<T> cacheUnit;
    private Integer cacheCapacity;
    private String chosedAlgorithm;
    private int swaps = 0;
    private int requests = 0;
    private int dataModelsCounter;

    public CacheUnitService() {
        chosedAlgorithm = "LRU";
        cacheCapacity = 10;
        iDao = new DaoFileImpl<>("src\\main\\resources\\datasourse.json");
        cacheUnit = new CacheUnit<>(new LRUAlgoCacheImpl<Long, DataModel<T>>(cacheCapacity));
    }

    public boolean delete(DataModel<T>[] dataModels) {
        try {
            requests++;
            Long ids[] = new Long[dataModels.length];

            for (int i = 0; i < dataModels.length; i++) {
                iDao.delete(dataModels[i]);
            }
            for (int i = 0; i < ids.length; i++) {
                ids[i] = dataModels[i].getDataModelId();
                dataModelsCounter++;
            }
            cacheUnit.removeDataModels(ids);
        }catch(Exception ex) {
            return false;
        }

        return true;
    }

    public DataModel<T>[] get(DataModel<T>[] dataModels) {
        requests++;
        dataModelsCounter += dataModels.length;
        Long[] ids = new Long[dataModels.length];
        for (int i = 0; i < ids.length; i++) {
            ids[i] = dataModels[i].getDataModelId();
            dataModelsCounter++;
        }
        DataModel<T>[] tempDM = cacheUnit.getDataModels(ids);
        for (int i = 0; i < tempDM.length; i++) {
            if (tempDM[i] == null) {
                tempDM[i] = iDao.find(dataModels[i].getDataModelId());
            }
        }
        return tempDM;
    }

    public boolean update(DataModel<T>[] dataModels) {
        requests++;
        Long[] ids = new Long[dataModels.length];
        for (int i = 0; i < ids.length; i++) {
            ids[i] = dataModels[i].getDataModelId();
        }
        DataModel<T>[] tempDM = cacheUnit.getDataModels(ids);
        for (int i = 0; i < tempDM.length; i++) {
            if (tempDM[i] == null) {
                tempDM[i] = cacheUnit.getDataModels().putElement(dataModels[i].getDataModelId(), dataModels[i]);
                swaps++;
            }
        }
        return true;
    }

    public void saveCacheToIDao(){
        DataModel<T>[] allDataModels = cacheUnit.getAllDataModels();
        for (DataModel<T> dataModel:allDataModels){
            try {
                iDao.save(dataModel);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String getChosedAlgorithm() {
        return chosedAlgorithm;
    }

    public void setChosedAlgorithm(String chosedAlgorithm) {
        this.chosedAlgorithm = chosedAlgorithm;
    }

    public Integer getCacheCapacity() {
        return cacheCapacity;
    }

    public void setCacheCapacity(Integer cacheCapacity) {
        this.cacheCapacity = cacheCapacity;
    }

    public String showStatisticsString() { //to handleRequest
        String stats = chosedAlgorithm + "," + cacheCapacity + "," + swaps + "," + requests + "," + dataModelsCounter;
        return stats;
    }

}

