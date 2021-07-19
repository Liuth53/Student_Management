package servace;

import JDBC.DAO.AdminDAO;
import JDBC.DAO.DormitoryDAO;
import JDBC.DAO.GradeDAO;
import JDBC.DAO.StudentInfoDAO;
import JDBC.javaBean.AdminInfo;
import JDBC.javaBean.GradeInfo;
import servace.dialog.AddGradeDialog;
import servace.dialog.UpdateAdminDialog;
import servace.dialog.UpdateGradeDialog;
import servace.listener.ActionDoneListener;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;


public class GradeInfoComponent extends Box {
    final int WIDTH=850;
    final int HEIGHT=600;
    JTextArea jta = new JTextArea(6, 30);
    JFrame jf = null;
    private JTable table;
    private Vector<String> titles;
    private Vector<Vector> tableData;
    private DefaultTableModel tableModel;

    AdminDAO adminDAO = new AdminDAO();
    StudentInfoDAO studentInfoDAO = new StudentInfoDAO();
    DormitoryDAO dormitoryDAO = new DormitoryDAO();
    GradeDAO gradeDAO = new GradeDAO();

    public GradeInfoComponent(JFrame jf,int identify,int id2){
        //垂直布局
        super(BoxLayout.Y_AXIS);
        //组装视图
        this.jf = jf;
        JPanel btnPanel = new JPanel();
        Color color = new Color(255, 255, 255, 255);
        btnPanel.setBackground(color);
        btnPanel.setMaximumSize(new Dimension(WIDTH,80));
        btnPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        JButton addBtn = new JButton("Add");
        JButton updateBtn = new JButton("Modify");
        JButton deleteBtn = new JButton("Delete");
        JButton flashBtn = new JButton("Flash");
        JButton upBtn = new JButton("Ascending Order");
        JButton downBtn = new JButton("Descending Order");

        addBtn.addActionListener(e -> {
            //弹出一个对话框，让用户输入图书的信息
            new AddGradeDialog(jf, "Add Information", true, new ActionDoneListener() {
                @Override
                public void done(Object result) {
                    requestData(1);
                }
            }).setVisible(true);
        });

        JButton searchBtn1 = new JButton(new AbstractAction("Search By StudentID") {
            @Override
            public void actionPerformed(ActionEvent e) {
                //弹出输入对话框

                String result = null;
                try {
                    result = JOptionPane.showInputDialog(jf, "StudentID", "Search By Student", JOptionPane.INFORMATION_MESSAGE);
                } catch (HeadlessException headlessException) {
                    headlessException.printStackTrace();
                    System.out.println("Input Error");
                }
                requestData(4,result);
            }
        });

        JButton searchBtn2 = new JButton(new AbstractAction("Search By CourseID") {
            @Override
            public void actionPerformed(ActionEvent e) {
                //弹出输入对话框
                String result = JOptionPane.showInputDialog(jf, "CourseID", "Search By Course", JOptionPane.INFORMATION_MESSAGE);
                requestData(5,result);
            }
        });

        updateBtn.addActionListener(e -> {
            //获取当前表格选中的id
            int selectedRow = table.getSelectedRow();//如果有选中的条目，则返回条目的行号，如果没有选中，那么返回-1

            if (selectedRow==-1){
                JOptionPane.showMessageDialog(jf,"Please select the item you want to modify!");
                return;
            }

            String id = tableModel.getValueAt(selectedRow, 0).toString();
            //弹出一个对话框，让用户修改
            new UpdateGradeDialog(jf, "Modify Information", true, new ActionDoneListener() {
                @Override
                public void done(Object result) {
                    requestData(1);
                }
            },id).setVisible(true);
        });

        deleteBtn.addActionListener(e -> {
            //获取选中的条目
            int selectedRow = table.getSelectedRow();//如果有选中的条目，则返回条目的行号，如果没有选中，那么返回-1

            if (selectedRow==-1){
                JOptionPane.showMessageDialog(jf,"Please select the entry you want to delete!");
                return;
            }

            //防止误操作
            int result = JOptionPane.showConfirmDialog(jf, "Are you sure you want to delete the selected entry?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (result != JOptionPane.YES_OPTION){
                return;
            }

            String id = tableModel.getValueAt(selectedRow, 0).toString();
            String courseID = tableModel.getValueAt(selectedRow,1).toString();
//                System.out.println(id);
//                System.out.println(courseID);
            int result1 = adminDAO.update("delete from studentsgrade where ID = ? AND CourseID= ?",id,courseID);

            if (result1 > 0){
                jta.setText("Successful");
                requestData(1);
            }
            else {jta.setText("Failed");}

            JOptionPane.showMessageDialog(jf, jta.getText(), "Load Message", JOptionPane.INFORMATION_MESSAGE);

        });

        flashBtn.addActionListener(e -> requestData(1));

        upBtn.addActionListener(e -> requestData(2));

        downBtn.addActionListener(e -> requestData(3));

        if (identify == 1) {
            btnPanel.add(addBtn);
            btnPanel.add(updateBtn);
            btnPanel.add(deleteBtn);
//        btnPanel.add(flashBtn);
            btnPanel.add(upBtn);
            btnPanel.add(downBtn);
            btnPanel.add(searchBtn1);
            btnPanel.add(searchBtn2);
            this.add(btnPanel);
        }
        //组装表格
        String[] ts = {"ID","CourseID","Grade"};
        titles = new Vector<>();
        titles.addAll(Arrays.asList(ts));
        tableData = new Vector<>();
        tableModel = new DefaultTableModel(tableData,titles);
        table = new JTable(tableModel){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        //设置只能选中一行
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(table);
        this.add(scrollPane);
        if (identify==1)
        requestData(1);
        else
            requestData(4,id2);
    }

    public void requestData(int i,Object... parameters){
        String sql = null;
        List<GradeInfo> gradeInfoList = null;
        if (i == 1) {
            sql = "select * from studentsgrade";
        }
        if (i == 2){
            sql = "SELECT * FROM studentsgrade ORDER BY ID ASC";
        }
        if (i == 3){
            sql = "SELECT * FROM studentsgrade ORDER BY ID DESC;";
        }
        if (i == 4){
            sql = "SELECT * FROM studentsgrade where ID = ? ;";
        }
        if (i == 5){
            sql = "SELECT * FROM studentsgrade where CourseID = ?;";
        }


        try {
            gradeInfoList = gradeDAO.queryMultiply(sql,GradeInfo.class,parameters);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Check Error！");
        }
//        Vector<Vector>vectors=ListToVector.ListTOVector1(studentInfosList);
        Vector<Vector>vectors = new Vector<>();
        for (int i1 = 0; i1 <= gradeInfoList.size()-1; i1++){
            AdminInfo info = new AdminInfo();
            GradeInfo gradeInfo = new GradeInfo();
            gradeInfo = gradeInfoList.get(i1);
            Vector vector = new Vector<>();
            vector.add(gradeInfo.getID());
            vector.add(gradeInfo.getCourseID());
            vector.add(gradeInfo.getGrade());
            vectors.add(vector);
        }
        //清空表格
        tableData.clear();
        //插入数据
        for (Vector vector : vectors) {
            tableData.add(vector);
//            new AddToModel(tableModel).addValueWithThread(vector);
        }
        //刷新表格
        tableModel.fireTableDataChanged();
    }

}
