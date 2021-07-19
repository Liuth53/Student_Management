package JDBC.javaBean;

public class AdminInfo {
    private Integer TeacherID;
    private String Name;
    private String password;
    private String post;

    public AdminInfo() {
    }

    public AdminInfo(Integer teacherID, String name, String password, String post) {
        TeacherID = teacherID;
        Name = name;
        this.password = password;
        this.post = post;
    }

    public Integer getTeacherID() {
        return TeacherID;
    }

    public void setTeacherID(Integer teacherID) {
        TeacherID = teacherID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    @Override
    public String toString() {
        return "AdminInfo{" +
                "TeacherID=" + TeacherID +
                ", Name='" + Name + '\'' +
                ", password='" + password + '\'' +
                ", post='" + post + '\'' +
                '}';
    }
}
