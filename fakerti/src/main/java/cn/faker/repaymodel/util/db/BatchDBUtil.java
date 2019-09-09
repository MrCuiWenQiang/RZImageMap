package cn.faker.repaymodel.util.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;

import org.litepal.parser.LitePalAttr;
import org.litepal.parser.LitePalParser;
import org.litepal.tablemanager.Connector;

import java.util.List;

import cn.faker.repaymodel.util.error.ErrorUtil;


/**
 * Function : sqlite 批量写入     因遇到大量数据时候 litepal插入速度慢    现用原生代替批量
 * Remarks  :
 * Created by Mr.C on 2018/9/26 0026.
 */
public class BatchDBUtil {
    private static BatchDBUtil batchDBUtil;

    public static BatchDBUtil init(Context context) {
        if (batchDBUtil == null) {
            synchronized (BatchDBUtil.class) {
                if (batchDBUtil == null) {
                    LitePalParser.parseLitePalConfiguration();
                    LitePalAttr mLitePalAttr = LitePalAttr.getInstance();
                    batchDBUtil = new BatchDBUtil(context, mLitePalAttr.getDbName(), null, mLitePalAttr.getVersion());
                }
            }
        }
        return batchDBUtil;
    }

    private Context mContext;
    private String dbName;

    public BatchDBUtil(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
//        super(context, name+ ".db", factory, version);
        this.mContext = context;
        this.dbName = name;
    }


    public BatchDBUtil(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
//        super(context, name+ ".db", factory, version, errorHandler);
        this.mContext = context;
        this.dbName = name;
    }

    public void saves(String name, List<ContentValues> sqls) {
        SQLiteDatabase db = Connector.getDatabase();
//        SQLiteDatabase db = getWritableDatabase();
//        SQLiteDatabase db = SQLiteDatabase.openDatabase(mContext.getDatabasePath(dbName+ ".db").getPath(),null,SQLiteDatabase.NO_LOCALIZED_COLLATORS|SQLiteDatabase.OPEN_READWRITE);
        db.beginTransaction();
        try {
            for (ContentValues contentValues : sqls) {
                db.insert(name, null, contentValues);
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            ErrorUtil.showError(e);
        } finally {
            db.endTransaction();
            db.close();
        }
    }


}
