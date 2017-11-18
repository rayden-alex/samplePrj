package myProg.springMvc;


import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public interface BaseDAO<T extends Model<ID>, ID extends Serializable> extends EntityManagerAware {
    Class<T> getEntityClass();

    default void persist(T entity) {
        if (entity.isNew()) {
            entity.assignId();
        }

        getEntityManager().persist(entity);
        getEntityManager().flush();
    }

    default T find(ID id) {
        return getEntityManager().find(getEntityClass(), id);
    }

    default void delete(T entity) {
        getEntityManager().remove(entity);
    }

    default List<T> findByQuery(String jpqlQueryString) {
        return findByQueryWithParams(jpqlQueryString, Collections.emptyMap());
    }

    default List<T> findByQueryWithParams(String jpqlQueryString, Map<String, Object> params) {
        TypedQuery<T> query = getEntityManager().createQuery(jpqlQueryString, getEntityClass());
        params.forEach(query::setParameter);

        return query.getResultList();
    }
}
