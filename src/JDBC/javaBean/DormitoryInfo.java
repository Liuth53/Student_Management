package JDBC.javaBean;

public class DormitoryInfo {
    private Integer ID;
    private Integer dormitoryNum;
    private Integer bedNumber;

    public DormitoryInfo() {}

    public DormitoryInfo(Integer ID, Integer dormitoryNum, Integer bedNumber) {
        this.ID = ID;
        this.dormitoryNum = dormitoryNum;
        this.bedNumber = bedNumber;
    }


    public Integer getBedNumber() {
        return bedNumber;
    }

    public void setBedNumber(Integer bedNumber) {
        this.bedNumber = bedNumber;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Integer getDormitoryNum() {
        return dormitoryNum;
    }

    public void setDormitoryNum(Integer dormitoryNum) {
        this.dormitoryNum = dormitoryNum;
    }

    @Override
    public String toString() {
        return "DormitoryInfo{" +
                "ID=" + ID +
                ", dormitoryNum=" + dormitoryNum +
                ", bedNumber=" + bedNumber +
                '}';
    }
}

