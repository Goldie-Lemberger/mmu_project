package main.java.com.mbj.dao;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import main.java.com.mbj.dm.DataModel;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;



/**
 * implements the IDao interface, Updates the file used as main memory
 * @param <T> type of data in file
 */

public class DaoFileImpl<T> implements IDao<Long, DataModel<T>> {

    String filePath;
    int capacity;
    Map<Long,DataModel<T>> memory;

    /* constructor*/
    public DaoFileImpl(String filePath){
        this.filePath = filePath;
        this.capacity = 240;
        memory = new HashMap<>(this.capacity);
        try {
            readFile();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /* constructor*/
    public DaoFileImpl(String filePath, int capacity){
        this.filePath = filePath;
        this.capacity = capacity;
        memory = new HashMap<>(this.capacity);
        try {
            readFile();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * override for interface, delete entity in file
     * @param entity  entity to delete in file
     */
    @Override
    public void delete(DataModel<T> entity) {
        Long key = entity.getDataModelId();
        if(memory.containsKey(key)){
            memory.remove(key);
            try {
                writeToFile();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * override for interface, delete entity in file
     * @param aLong id to find
     * @return the entity that was found
     */
    @Override
    public DataModel<T> find(Long aLong) throws IllegalArgumentException{
        if (aLong == null) throw new IllegalArgumentException("Id cant be null.");
        if(memory.containsKey(aLong)) {
            return memory.get(aLong);
        }else return null;
    }

    /**
     * override for interface, save entity in file
     * @param entity new entity to save in file
     */
    @Override
    public void save(DataModel<T> entity) {
        if (memory.size() >= capacity) {
            System.out.printf("Memory is full, cannot add entity: %s%n", entity.toString());
            return;
        }
        if (entity != null && entity != find(entity.getDataModelId())) {
            memory.put(entity.getDataModelId(), entity);
            try {
                writeToFile();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * help function to read the content file to fileContent List
     */
    public void readFile() throws FileNotFoundException{
        Gson gson = new Gson();
        File file = new File(filePath);
        try (FileReader reader = new FileReader(file)) {
            Type mapType = new TypeToken<HashMap<Long, DataModel<T>>>() {}.getType();
            HashMap<Long,DataModel<T>> tempMemory = gson.fromJson(reader,mapType);
            if(tempMemory != null){
                memory = tempMemory;
            }
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * help function to write the content file to fileContent List
     */
    public void writeToFile() throws FileNotFoundException{
        Gson gson = new Gson();
        String jsonMap = gson.toJson(memory);
        File file = new File(filePath);
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(jsonMap);
            fileWriter.flush();
        } catch (FileNotFoundException exception) {
             exception.printStackTrace();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

    }
}
