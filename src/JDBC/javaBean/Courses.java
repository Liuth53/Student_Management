package JDBC.javaBean;

public class Courses {
    private int courseNumber;
    private String courseName;
    private String year;
    private String term;
    private String teacher;

    public Courses() {
    }

    public Courses(int courseNumber, String courseName, String year, String term, String teacher) {
        this.courseNumber = courseNumber;
        this.courseName = courseName;
        this.year = year;
        this.term = term;
        this.teacher = teacher;
    }

    public int getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(int courseNumber) {
        this.courseNumber = courseNumber;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    @Override
    public String toString() {
        return "Courses{" +
                "courseNumber=" + courseNumber +
                ", courseName='" + courseName + '\'' +
                ", year='" + year + '\'' +
                ", term='" + term + '\'' +
                ", teacher='" + teacher + '\'' +
                '}';
    }
}
