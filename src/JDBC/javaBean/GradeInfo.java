package JDBC.javaBean;

public class GradeInfo {
    private Integer ID;
    private Integer grade;
    private Integer courseID;

    public GradeInfo(Integer ID, Integer grade, Integer courseID) {
        this.ID = ID;
        this.grade = grade;
        this.courseID = courseID;
    }
    public GradeInfo(){};

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Integer getCourseID() {
        return courseID;
    }

    public void setCourseID(Integer courseID) {
        this.courseID = courseID;
    }

    @Override
    public String toString() {
        return "GradeInfo{" +
                "ID=" + ID +
                ", grade=" + grade +
                ", courseID=" + courseID +
                '}';
    }
}
