package main.java.com.mbj.memory;

import java1.algorithm.IAlgoCache;
import main.java.com.mbj.dm.DataModel;


/**
 * Manages RAM according to RAM management algorithms
 * @param <T> content type of RAM elements
 */
public class CacheUnit<T> {
    IAlgoCache<Long, DataModel<T>> algo;

    public CacheUnit(IAlgoCache<Long, DataModel<T>> algo) {
        this.algo = algo;
    }

    public IAlgoCache<Long, DataModel<T>> getDataModels() {
        return algo;
    }

    /**
     * get the data of the given ids from RAM
     * @param ids ids array to get data
     * @return the data from RAM
     */
    public DataModel<T>[] getDataModels(Long[] ids) {
        DataModel<T>[] dataModelsContents = new DataModel[ids.length];
        for (int i = 0; i < ids.length; i++) {
            dataModelsContents[i] = algo.getElement(ids[i]);
        }
        return dataModelsContents;
    }

    /**
     * insert the given elements to the RAM and exits according to the algorithm if there is no place in the RAM
     * @param datamodels elements to insert
     * @return the The elements extracted by the algorithm
     */
    public DataModel<T>[] putDataModels(DataModel<T>[] datamodels) {
        DataModel<T>[] removesData = new DataModel[datamodels.length];
        for (int i = 0; i < datamodels.length; i++) {
            removesData[i] = algo.putElement(datamodels[i].getDataModelId(), datamodels[i]);
        }
        return removesData;
    }
    /**
     * remove the given elements from the RAM
     * @param ids ids of elements to remove
     */
    public void removeDataModels(java.lang.Long[] ids) {
        for (Long id : ids) {
            algo.removeElement(id);
        }


    }

    public DataModel<T>[] getAllDataModels() {
        
        return algo.getAllElements().values().toArray(new DataModel[0]);
    }
}
