/**

* @Description:    Data Access Object (DAO)

* @Author:         Lth

* @CreateDate:     2021/5/23 14:18

* @UpdateUser:     Lth

* @UpdateDate:     2021/5/23 14:18

* @UpdateRemark:

* @Version:        1.8

*/
package JDBC.DAO;


import utils.JDBCUtilsByDruid;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

//泛型指定具体类型
public class BasicDAO<T> {

    private QueryRunner qr = new QueryRunner();

    //通用DML操作
    public int update(String sql, Object... parameters){
        Connection connection = null;
        try {
            connection = JDBCUtilsByDruid.getConnection();
            int update = qr.update(connection,sql,parameters);
            return update;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            JDBCUtilsByDruid.close(null,null,connection);
        }
    }

    //查询返回多行结果
    public List<T> queryMultiply(String sql, Class<T>clazz, Object... parameters){

        Connection connection = null;
        try {
            connection = JDBCUtilsByDruid.getConnection();
            return qr.query(connection,sql,new BeanListHandler<T>(clazz),parameters);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            JDBCUtilsByDruid.close(null,null,connection);
        }
    }

    //查询返回单行结果
    public T querySingle(String sql,Class<T> clazz,Object... parameters){
        Connection connection = null;
        try {
            connection = JDBCUtilsByDruid.getConnection();
            return qr.query(connection,sql,new BeanHandler<T>(clazz),parameters);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            JDBCUtilsByDruid.close(null,null,connection);
        }
    }

    //查询返回单行单列结果
    public Object queryScalar(String sql,Object... parameters){
        Connection connection = null;
        try {
            connection = JDBCUtilsByDruid.getConnection();
            return qr.query(connection,sql,new ScalarHandler<T>(),parameters);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            JDBCUtilsByDruid.close(null,null,connection);
        }
    }
}
