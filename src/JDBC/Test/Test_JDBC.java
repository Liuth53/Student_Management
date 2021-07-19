package JDBC.Test;

import JDBC.DAO.AdminDAO;
import JDBC.DAO.DormitoryDAO;
import JDBC.DAO.GradeDAO;
import JDBC.DAO.StudentInfoDAO;
import JDBC.javaBean.AdminInfo;
import JDBC.javaBean.DormitoryInfo;
import JDBC.javaBean.GradeInfo;
import JDBC.javaBean.StudentInfo;
//import org.junit.Test;
import org.junit.jupiter.api.Test;

import java.util.List;

public class Test_JDBC {
    @Test
    public void testjdbc(){
        AdminDAO adminDAO = new AdminDAO();
        StudentInfoDAO studentInfoDAO = new StudentInfoDAO();
        DormitoryDAO dormitoryDAO = new DormitoryDAO();
        GradeDAO gradeDAO = new GradeDAO();
        List<DormitoryInfo> dormitoryInfoList = dormitoryDAO.queryMultiply("select * from dormitory where `ID` >= ?", DormitoryInfo.class,1);
        List<GradeInfo> gradeInfoList = gradeDAO.queryMultiply("select * from studentsgrade where `ID` >= ?", GradeInfo.class,1);
        List<AdminInfo> adminInfoList = adminDAO.queryMultiply("select * from administered where TeacherID >= ?", AdminInfo.class,1);
//        List<StudentInfo> studentInfosList = studentInfoDAO.queryMultiply("select * from basicinformation where ID >= ?",StudentInfo.class,1);
        List<StudentInfo> studentInfosList = studentInfoDAO.queryMultiply("SELECT * FROM basicinformation ORDER BY ID DESC;",StudentInfo.class);

//        for(AdminInfo adminInfo : adminInfoList){
//            System.out.println(adminInfo);
//        }
        for (StudentInfo studentInfo :studentInfosList){
            System.out.println(studentInfo);
        }
//        for(GradeInfo gradeInfo : gradeInfoList){
//            System.out.println(gradeInfo);
//        }
//        for(DormitoryInfo dormitoryInfo : dormitoryInfoList){
//            System.out.println(dormitoryInfo);
//        }
    }
}
