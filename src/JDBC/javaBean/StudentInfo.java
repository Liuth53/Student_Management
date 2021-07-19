package JDBC.javaBean;
import java.util.Date;
public class StudentInfo {
    private Integer id;
    private String Name;
    private String sex;
    private String phone;
    private String password;
    private Date birthday;
    private String teacher;


    public StudentInfo() {
    }

    public StudentInfo(Integer id, String name, String sex, String phone, String password, Date birthday, String teacher) {
        this.id = id;
        Name = name;
        this.sex = sex;
        this.phone = phone;
        this.password = password;
        this.birthday = birthday;
        this.teacher = teacher;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    @Override
    public String toString() {
        return "StudentInfo{" +
                "id=" + id +
                ", Name='" + Name + '\'' +
                ", sex='" + sex + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", birthday=" + birthday +
                ", teacher='" + teacher + '\'' +
                '}';
    }
}
