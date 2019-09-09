package cn.faker.repaymodel.util.db.litpal;

import android.text.TextUtils;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Function : Litpal数据库工具类  膜拜鸿神！
 * Remarks  :
 * Created by Mr.C on 2019/1/11 0011.
 */
public class LitPalUtils {
    public static <T extends LitePalSupport> void saveAll(Collection<T> datas) {
        LitePal.saveAll(datas);
    }

    /**
     * 分页查询数据
     * 可添加筛选 where NAME > ? ,20
     *
     * @param t
     * @param page       页数从1开始
     * @param size       每页多少条数据
     * @param <T>        查询的表
     * @param conditions 搜索条件
     * @return
     */
    public static <T> List<T> selectPage(Class<T> t, int page, int size, String... conditions) {
        if (page <= 0) {
            return null;
        }
        int offset = (page - 1) * size;
        List<T> datas = LitePal.where(conditions).offset(offset).limit(size).find(t);
        return datas;
    }

    /**
     * between
     *
     * @param t
     * @param page
     * @param size
     * @param conditions
     * @param <T>
     * @return
     */
    @Deprecated
    public static <T> List<T> selectPage(Class<T> t, int page, int size, String between_start, String between_end, String... conditions) {
        if (page <= 0) {
            return null;
        }
        String[] valueArray = {"Where BETWEEN ? AND ?", between_start, between_end};
        int offset = (page - 1) * size;
        List<T> datas = LitePal.where(conditions).offset(offset).limit(size).find(t);
        return datas;
    }

    /**
     * 分页查询数据
     * 可添加筛选 where NAME > ? ,20
     *
     * @param t
     * @param <T>        查询的表
     * @param page       页数从1开始
     * @param size       每页多少条数据
     * @param column     排序条件
     * @param conditions 搜索条件
     * @return
     */
    public static <T> List<T> selectPage(Class<T> t, int page, int size, String column, String... conditions) {
        if (page <= 0) {
            return null;
        }
        int offset = (page - 1) * size;
        List<T> datas = LitePal.where(conditions).offset(offset).limit(size).order(column).find(t);
        return datas;
    }

    /**
     * 用于输出无照片的档案
     *
     * @param t
     * @param conditions
     * @param <T>
     * @return
     */
/*    public static <T> List<T> selecActivitytWhere(Class<T> t, String... conditions) {
        List<T> list = LitePal.select("id", "nId", "cadreName", "professional", "area", "arcNumber", "workPlace", "sex", "flag", "status", "cadreStatus", "spelling", "createTime", "editTime", "virson", "rfid", "cabId", "onFrameTime", "offFrameTime").where(conditions).find(t);
        return list;
    }

    public static <T> T selectsoloActivitytWhere(Class<T> t, String... conditions) {
        List<T> list = selecActivitytWhere(t, conditions);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }*/

    /**
     * 筛选数据 无分页
     *
     * @param t
     * @param conditions
     * @param <T>
     * @return
     */
    public static <T> List<T> selectWhere(Class<T> t, String... conditions) {
        List<T> list = LitePal.where(conditions).find(t);
        return list;
    }

    public static <T> List<T> selectWheres(String column, Class<T> t, String... conditions) {
        List<T> list = LitePal.where(conditions).order(column).find(t);
        return list;
    }


    /**
     * 筛选数据 无分页 排序
     *
     * @param t
     * @param conditions
     * @param <T>
     * @return
     */
    public static <T> List<T> selectWhere(String column, Class<T> t, String... conditions) {
        List<T> list = LitePal.where(conditions).order(column).find(t);
        return list;
    }

    public static <T> int selectCount(Class<T> t) {
        try {
            return LitePal.count(t);
        } catch (Exception e) {
            return 0;
        }
    }

    public static <T> int selectCount(Class<T> t, String... conditions) {
        try {
            return LitePal.where(conditions).count(t);
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 筛选数据 无分页
     *
     * @param t
     * @param conditions
     * @param <T>
     * @return
     */
    public static <T> T selectsoloWhere(Class<T> t, String... conditions) {
        List<T> list = LitePal.where(conditions).find(t);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    public static <T> T selectsoloOrderby(int i, String column, Class<T> t, String... conditions) {
        List<T> list = LitePal.limit(i).order(column).find(t);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    public static <T> T selectsoloWhere(String column, Class<T> t, String... conditions) {
        List<T> list = selectorderWhere(column, t, conditions);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    public static <T> List<T> selectorderWhere(String column, Class<T> t, String... conditions) {
        List<T> list = LitePal.where(conditions).order(column).find(t);
        return list;
    }

    /**
     * 将集合参数转为sql 要替换的sql中的参数用？代替 注意该方法非线程安全
     *
     * @param sql
     * @param parameters
     * @return
     */
    public static String toOrParameters(String sql, List<String> parameters) {
        if (parameters == null || parameters.size() <= 0 || TextUtils.isEmpty(sql)) {
            return null;
        }
        final String wildcard = "?";
        if (!sql.contains(wildcard)) {
            throw new RuntimeException("sql参数中不含？通配符");
        }

        StringBuilder stringBuilder = new StringBuilder();
        int size = parameters.size();
        for (int i = 0; i < size; i++) {
            if (i != size - 1) {
                stringBuilder.append(sql);
                stringBuilder.append(" OR ");
            } else {
                stringBuilder.append(sql);
            }
        }
        for (String parameter : parameters) {
            int index = stringBuilder.indexOf("?");
            stringBuilder.replace(index, index + 1, "'" + parameter + "'");
        }
        return stringBuilder.toString();
    }

    public static String[] ofWhereArray(Map<String, String> map) {
        if (map == null || map.size() < 1) {
            return null;
        }
        ArrayList<String> list = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        int i = 0;
        for (String key : map.keySet()) {
            if (i == 0) {
                stringBuilder.append(key + "=?");
            } else {
                stringBuilder.append(" AND " + key + "=?");
            }
            list.add(map.get(key));
            i++;
        }
        list.add(0, stringBuilder.toString());
        return list.toArray(new String[list.size()]);
    }

    public static String[] ofORArray(Map<String, String> map) {
        if (map == null || map.size() < 1) {
            return null;
        }
        ArrayList<String> list = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        int i = 0;
        for (String key : map.keySet()) {
            if (i == 0) {
                stringBuilder.append(key + "=?");
            } else {
                stringBuilder.append(" OR " + key + "=?");
            }
            list.add(map.get(key));
            i++;
        }
        list.add(0, stringBuilder.toString());
        return list.toArray(new String[list.size()]);
    }

    /**
     * 因为mapp不允许key重复 因此特意有此方法，但只能用于单参数
     *
     * @param pol
     * @param values
     * @return
     */
    public static String[] ofORqnmbdArray(String s, String pol, List<String> values) {
        if (TextUtils.isEmpty(pol) || values == null || values.size() < 1) {
            return null;
        }
        ArrayList<String> list = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        int i = 0;
        stringBuilder.append(s);
        for (Object vl : values) {
            if (i == 0) {
                if (values.size() == 1) {
                    stringBuilder.append(pol + "=?");
                } else {
                    stringBuilder.append("(" + pol + "=?");
                }
            } else {
                if (i + 1 == values.size()) {
                    stringBuilder.append(" OR " + pol + "=? )");
                } else {
                    stringBuilder.append(" OR " + pol + "=?");
                }
            }

            list.add(String.valueOf(vl));
            i++;
        }
        list.add(0, stringBuilder.toString());
        return list.toArray(new String[list.size()]);
    }


    /**
     * 因为mapp不允许key重复 因此特意有此方法，但只能用于单参数
     *
     * @param pol
     * @param values
     * @return
     */
    public static String[] ofORArray(String pol, List<String> values) {
        if (TextUtils.isEmpty(pol) || values == null || values.size() < 1) {
            return null;
        }
        ArrayList<String> list = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        int i = 0;
        for (Object vl : values) {
            if (i == 0) {
                stringBuilder.append(pol + "=?");
            } else {
                stringBuilder.append(" OR " + pol + "=?");
            }
            list.add(String.valueOf(vl));
            i++;
        }
        list.add(0, stringBuilder.toString());
        return list.toArray(new String[list.size()]);
    }

    public static String[] ofLikeArray(Map<String, String> map) {
        if (map == null || map.size() < 1) {
            return null;
        }
        ArrayList<String> list = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        int i = 0;
        for (String key : map.keySet()) {
            if (i == 0) {
                stringBuilder.append(key + " LIKE ?");
            } else {
                stringBuilder.append(" AND " + key + " LIKE ?");
            }
            list.add("%" + map.get(key) + "%");
            i++;
        }
        list.add(0, stringBuilder.toString());
        return list.toArray(new String[list.size()]);
    }

    public static <T> int deleteData(Class<T> t, String... conditions) {
        return LitePal.deleteAll(t, conditions);
    }
}
