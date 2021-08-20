package main.java.com.mbj.dm;

import java.util.Objects;


/**
 *Holds the information of the memory and its functions
 * @param <T> content type
 */
public class DataModel<T>   {
    Long id;
    T content;

    public Long getDataModelId() {
        return id;
    }

    public void setDataModelId(Long id) {
        this.id = id;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public DataModel(Long id, T content) {
        this.id = id;
        this.content = content;
    }

    @Override
    public String toString() {
        return "DataModel{" +
                "id=" + id +
                ", content=" + content +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DataModel)) return false;
        DataModel<?> dataModel = (DataModel<?>) o;
        return id.equals(dataModel.id) && getContent().equals(dataModel.getContent());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, getContent());
    }

}
