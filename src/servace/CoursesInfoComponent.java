package servace;

import JDBC.DAO.*;
import JDBC.javaBean.AdminInfo;
import JDBC.javaBean.Courses;
import servace.dialog.AddAdminDialog;
import servace.dialog.UpdateAdminDialog;
import servace.listener.ActionDoneListener;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;


public class CoursesInfoComponent extends Box {
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
    CoursesDAO coursesDAO = new CoursesDAO();

    public CoursesInfoComponent(JFrame jf,int identify){
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
            new AddAdminDialog(jf, "Add Information", true, new ActionDoneListener() {
                @Override
                public void done(Object result) {
                    requestData(1);
                }
            }).setVisible(true);
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
            new UpdateAdminDialog(jf, "Modify Information", true, new ActionDoneListener() {
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

            int result1 = coursesDAO.update("delete from courses where CourseNumber = ?",id);

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
        if (identify == 1){
            btnPanel.add(addBtn);
            btnPanel.add(updateBtn);
            btnPanel.add(deleteBtn);
            btnPanel.add(flashBtn);
        }
        btnPanel.add(upBtn);
        btnPanel.add(downBtn);
        this.add(btnPanel);

        //组装表格
        String[] ts = {"ID","Course","teacher","Year","Term"};
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

        requestData(1);
    }

    public void requestData(int i){
        String sql = null;
        List<Courses> coursesList;
        if (i == 1) {
            sql = "select * from courses";
        }
        if (i == 2){
            sql = "SELECT * FROM courses ORDER BY CourseNumber ASC";
        }
        if (i == 3){
            sql = "SELECT * FROM courses ORDER BY CourseNumber DESC;";
        }
        coursesList = coursesDAO.queryMultiply(sql, Courses.class);
//        Vector<Vector>vectors=ListToVector.ListTOVector1(studentInfosList);
        Vector<Vector>vectors = new Vector<>();
        for (int i1 = 0; i1 <= coursesList.size()-1; i1++){
            Courses info = new Courses();
            info = coursesList.get(i1);
            Vector vector = new Vector<>();
            vector.add(info.getCourseNumber());
            vector.add(info.getCourseName());
            vector.add(info.getTeacher());
            vector.add(info.getYear());
            vector.add(info.getTerm());
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
