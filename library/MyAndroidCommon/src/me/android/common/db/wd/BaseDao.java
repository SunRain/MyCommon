package me.android.common.db.wd;

import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * DAO基类
 *
 * @param <T> JaveBean类
 */
public abstract class BaseDao<T> {
    protected DatabaseUtil dbUtil = null;
    protected Class<T> beanClazz = null;

    @SuppressWarnings("unchecked")
    protected BaseDao(DatabaseUtil dbUtil) {
        this.dbUtil = dbUtil;
        this.beanClazz = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * 查询排好序的数据集合
     *
     * @param orderBy
     * @return
     */
    public final List<T> queryByOrderBy(String orderBy) {
        List<T> list = dbUtil.queryByOrderBy(beanClazz, null, null, null,
                orderBy);
        return list;
    }

    /**
     * 插入数据，如果数据存在则更新该条数据
     *
     * @param model
     * @param querySql 查询语句
     * @return
     */
    protected final boolean saveOrUpdate(Object model, String querySql) {
        Object object = dbUtil.query(model.getClass(), querySql);
        if (object != null) {
            return dbUtil.update(model, querySql);
        } else {
            return dbUtil.save(model) != -1;
        }
        // List<Object> list = dbUtil.queryList(model.getClass(), querySql);
        // if (list.size() > 0) {
        // return dbUtil.update(model, querySql);
        // } else {
        // return dbUtil.save(model) != -1;
        // }
    }

    /**
     * 删除数据
     *
     * @param querySql 查询语句
     * @return
     */
    protected final boolean delete(String querySql) {
        return dbUtil.delete(beanClazz, querySql);
    }

    /**
     * 查询数据
     *
     * @param querySql 查询语句
     * @return
     */
    protected final T query(String querySql) {
        T object = dbUtil.query(beanClazz, querySql);
        return object;
    }
}
