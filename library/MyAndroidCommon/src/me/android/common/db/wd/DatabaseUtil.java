package me.android.common.db.wd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.List;

public class DatabaseUtil {
    private DatabaseHelper dbHelper;

    /**
     * 初始化资源
     *
     * @param context
     */
    DatabaseUtil(Context context) {
        dbHelper = new DatabaseHelper(context);
        dbHelper.open();
    }

    /**
     * 创建资源
     */
    void relese() {
        if (dbHelper != null) {
            dbHelper.close();
        }
    }

    /**
     * 查询单条记录
     *
     * @param clazz
     * @param querySql
     * @return
     */
    public <T> T query(Class<T> beanClazz, String querySql) {
        String tableName = SqlUtil.getTableName(beanClazz);
        Cursor cursor = dbHelper.query(tableName, null, querySql);
        T object = SqlUtil.cursor2VO(cursor, beanClazz);
        cursor.close();
        return object;
    }

    /**
     * 查询集合
     *
     * @param <T>
     * @param clazz
     * @param querySql
     * @return
     */
    public <T> List<T> queryList(Class<T> clazz, String querySql) {
        String tableName = SqlUtil.getTableName(clazz);
        Cursor cursor = dbHelper.query(tableName, null, querySql);
        List<T> list = SqlUtil.cursor2VOList(cursor, clazz);
        cursor.close();
        return list;
    }

    /**
     * 查询数据，并根据排序条件排好序
     *
     * @param clazz
     * @param columns
     * @param querySql
     * @param groupBy
     * @param orderBy
     * @return
     */
    public <T> List<T> queryByOrderBy(Class<T> clazz, String[] columns,
                                      String querySql, String groupBy, String orderBy) {
        String tableName = SqlUtil.getTableName(clazz);
        Cursor cursor = dbHelper.queryByOrderBy(tableName, columns, querySql,
                groupBy, orderBy);
        List<T> list = SqlUtil.cursor2VOList(cursor, clazz);
        cursor.close();
        return list;
    }

    /**
     * 查询数据
     *
     * @param clazz
     * @param columns
     * @param orderBy
     * @param selection
     * @return
     */
    public <T> List<T> queryAllByOrderByAndWhere(Class<T> clazz,
                                                 String[] columns, String orderBy, String selection) {
        String tableName = SqlUtil.getTableName(clazz);
        Cursor cursor = dbHelper.queryAllByOrderByAndWhere(tableName, columns,
                selection, orderBy);
        List<T> list = SqlUtil.cursor2VOList(cursor, clazz);
        cursor.close();
        return list;
    }

    /**
     * 保存数据
     *
     * @param model
     * @return
     */
    public long save(Object model) {
        String tableName = SqlUtil.getTableName(model.getClass());
        ContentValues contentValues = SqlUtil.getContentValues(model);
        return dbHelper.save(tableName, contentValues);
    }

    /**
     * 更新数据
     *
     * @param model
     * @param whereClause
     * @return
     */
    public boolean update(Object model, String whereClause) {
        String tableName = SqlUtil.getTableName(model.getClass());
        ContentValues contentValues = SqlUtil.getContentValues(model);
        return dbHelper.update(tableName, contentValues, whereClause);
    }

    /**
     * 删除数据
     *
     * @param clazz
     * @param whereClause
     * @return
     */
    public boolean delete(Class<?> clazz, String whereClause) {
        String tableName = SqlUtil.getTableName(clazz);
        return dbHelper.delete(tableName, whereClause);
    }

    /**
     * 删除数据
     *
     * @param clazz
     * @return
     */
    public int deleteAll(Class<?> clazz) {
        String tableName = SqlUtil.getTableName(clazz);
        return dbHelper.deleteAll(tableName);
    }
}
