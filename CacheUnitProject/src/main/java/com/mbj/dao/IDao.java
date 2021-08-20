package main.java.com.mbj.dao;


/**
 * interface that manages main memory, insertion, deletion and bargain
 * @param <ID> id type of element in memory
 * @param <T> value type of element in memory
 */
public interface IDao<ID, T> {


    /**
     * delete an entity from the main memory
     * @param entity entity to delete
     * @throws IOException throw exception when i/o failed
     */
    void delete(T entity);


    /**
     * find the data of the given id from the main memory
     * @param id id to find
     * @return the data from main memory
     */
    T find(ID id);

    /**
     * Keeps a new entity in the main memory
     * @param entity new entity to save
     * @throws IOException throw exception when i/o failed
     */
    void save(T entity);
}
