package me.android.common.db.wd;

import java.util.Map;
import java.util.Set;

import me.android.common.db.BaseBean;
import wd.android.util.util.MyLog;
import wd.android.util.util.ObjectUtil;
import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

/**
 * 数据库包装类
 */
class DatabaseHelper {
    private SQLiteOpenHelper mDBHelper;
    private SQLiteDatabase mDatabase;
    private Context mContext;
    private String mDbName = null;

    /**
     * 初始化
     *
     * @param context
     */
    DatabaseHelper(Context context) {
        mContext = context;
        mDbName = context.getPackageName() + ".db";
    }

    /**
     * 打开一个可读数据库
     *
     * @return
     * @throws SQLException
     */
    DatabaseHelper open() throws SQLException {
        mDBHelper = new OrmDatabaseHelper(mContext, mDbName, null,
                DatabaseConfig.DB_VERSION, DatabaseConfig.TABLE_LIST);
        mDatabase = mDBHelper.getWritableDatabase();
        return this;
    }

    /**
     * 关闭数据库
     */
    void close() {
        mDBHelper.close();
    }

    /**
     * 保存数据
     *
     * @param tableName
     * @param initialValues
     * @return
     */
    long save(String tableName, ContentValues initialValues) {
        long i = mDatabase.insert(tableName, null, initialValues);
        return i;
    }

    /**
     * 更新数据
     *
     * @param tableName
     * @param args
     * @param whereClause
     * @return
     */
    boolean update(String tableName, ContentValues args, String whereClause) {
        boolean flag = mDatabase.update(tableName, args, whereClause, null) > 0;
        return flag;
    }

    /**
     * 查询数据
     *
     * @param tableName
     * @param columns
     * @param querySql
     * @return
     */
    Cursor query(String tableName, String[] columns, String querySql) {
        Cursor mCursor = mDatabase.query(true, tableName, columns, querySql,
                null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    /**
     * 查询数据，并根据排序条件排好序
     *
     * @param tableName
     * @param columns
     * @param querySql
     * @param groupBy
     * @param orderBy
     * @return
     */
    Cursor queryByOrderBy(String tableName, String[] columns, String querySql,
                          String groupBy, String orderBy) {
        Cursor mCursor = mDatabase.query(true, tableName, columns, querySql,
                null, groupBy, null, orderBy, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    /**
     * 删除数据
     *
     * @param tableName
     * @param whereClause
     * @return
     */
    boolean delete(String tableName, String whereClause) {
        boolean flag = mDatabase.delete(tableName, whereClause, null) > 0;
        return flag;
    }

    /**
     * 查询数据
     *
     * @param tableName
     * @param columns
     * @param orderBy
     * @param selection
     * @return
     */
    Cursor queryAllByOrderByAndWhere(String tableName, String[] columns,
                                     String orderBy, String selection) {
        Cursor mCursor = mDatabase.query(true, tableName, columns, selection,
                null, null, null, orderBy, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    /**
     * 删除数据
     *
     * @param tableName
     * @return
     */
    int deleteAll(String tableName) {
        return mDatabase.delete(tableName, null, null);
    }

    private class MyDatabaseHelper extends SQLiteOpenHelper {
        private Map<String, String> createSQLs;

        public MyDatabaseHelper(Context context, String dbName,
                                CursorFactory factory, int version, Map<String, String> sqls) {
            super(context, dbName, factory, version);
            this.createSQLs = sqls;
            MyLog.i("version = " + version);
        }

        public void onCreate(SQLiteDatabase db) {
            if (createSQLs != null && createSQLs.size() > 0) {
                for (String table : createSQLs.values()) {
                    db.execSQL(table);
                }
            }
        }

        @TargetApi(11)
        public void onDowngrade(SQLiteDatabase db, int oldVersion,
                                int newVersion) {
            updateDatabase(db, oldVersion, newVersion);
            // super.onDowngrade(db, oldVersion, newVersion);
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            updateDatabase(db, oldVersion, newVersion);
        }

        private void updateDatabase(SQLiteDatabase db, int oldVersion,
                                    int newVersion) {
            MyLog.i("newVersion = " + newVersion + ",oldVersion = "
                    + oldVersion);
            if (newVersion != oldVersion) {
                if (createSQLs != null && createSQLs.size() > 0) {
                    for (String table : createSQLs.keySet()) {
                        db.execSQL("DROP TABLE IF EXISTS  " + table);
                    }
                }
                onCreate(db);
            }
        }
    }

    private class OrmDatabaseHelper extends OrmLiteSqliteOpenHelper {

        private Set<Class<? extends BaseBean>> tables = ObjectUtil.newHashSet();

        public OrmDatabaseHelper(Context context, String dbName,
                                 CursorFactory factory, int version,
                                 Set<Class<? extends BaseBean>> tables) {
            super(context, dbName, factory, version);
            if (tables != null) {
                this.tables.addAll(tables);
            }
            MyLog.i("version = " + version);
        }

        @Override
        public void onCreate(SQLiteDatabase database,
                             ConnectionSource connectionSource) {
            try {
                for (Class<?> clazz : tables) {
                    TableUtils.createTable(connectionSource, clazz);
                }
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }

        @TargetApi(11)
        public void onDowngrade(SQLiteDatabase database, int oldVersion,
                                int newVersion) {
            updateDatabase(database, connectionSource, oldVersion, newVersion);
        }

        @Override
        public void onUpgrade(SQLiteDatabase database,
                              ConnectionSource connectionSource, int oldVersion,
                              int newVersion) {
            updateDatabase(database, connectionSource, oldVersion, newVersion);
        }

        private void updateDatabase(SQLiteDatabase database,
                                    ConnectionSource connectionSource, int oldVersion,
                                    int newVersion) {
            MyLog.i("newVersion = " + newVersion + ",oldVersion = "
                    + oldVersion);
            if (newVersion != oldVersion) {
                try {
                    for (Class<?> clazz : tables) {
                        TableUtils.dropTable(connectionSource, clazz, true);
                        TableUtils.createTable(connectionSource, clazz);
                    }
                } catch (java.sql.SQLException e) {
                    e.printStackTrace();
                }
                // onCreate(database, connectionSource);
            }
        }
    }
}
