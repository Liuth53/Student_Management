package servace;

import JDBC.DAO.StudentInfoDAO;
import JDBC.javaBean.StudentInfo;
import servace.listener.ActionDoneListener;
import servace.dialog.AddDialog;
import servace.dialog.UpdateDialog;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;


public class BasicInfoComponent extends Box {
    final int WIDTH=850;
    final int HEIGHT=600;
    JTextArea jta = new JTextArea(6, 30);
    JFrame jf = null;
    private JTable table;
    private Vector<String> titles;
    private Vector<Vector> tableData;
    private DefaultTableModel tableModel;
    StudentInfoDAO studentInfoDAO = new StudentInfoDAO();

    public BasicInfoComponent(JFrame jf,int identify,int id){
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
            //弹出一个对话框，让用户输入信息
            new AddDialog(jf, "Add Information", true, result -> requestData(1)).setVisible(true);
        });
        updateBtn.addActionListener(e -> {
            //获取当前表格选中的id
            int selectedRow = table.getSelectedRow();//如果有选中的条目，则返回条目的行号，如果没有选中，那么返回-1

            if (selectedRow==-1){
                JOptionPane.showMessageDialog(jf,"Please select the item you want to modify!");
                return;
            }

            String id1 = tableModel.getValueAt(selectedRow, 0).toString();
            //弹出一个对话框，让用户修改
            new UpdateDialog(jf, "Modify Information", true, result -> requestData(1), id1).setVisible(true);
        });
        deleteBtn.addActionListener(e -> {
            //获取选中的条目
            int selectedRow = table.getSelectedRow();//如果有选中的条目，则返回条目的行号，如果没有选中，那么返回-1

            if (selectedRow==-1){
                JOptionPane.showMessageDialog(jf,"Please select the entry you want to delete!");
                return;
            }

            //防止误操作
            int result = JOptionPane.showConfirmDialog(jf,
                    "Are you sure you want to delete the selected entry?",
                    "Confirm", JOptionPane.YES_NO_OPTION);
            if (result != JOptionPane.YES_OPTION){
                return;
            }

            String id12 = tableModel.getValueAt(selectedRow, 0).toString();

//                System.out.println(id);

            int result1 = studentInfoDAO.update("delete from basicinformation where ID = ?", id12);

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
            btnPanel.add(flashBtn);
            btnPanel.add(upBtn);
            btnPanel.add(downBtn);
            this.add(btnPanel);
        }
        //组装表格
        String[] ts = {"ID","Name","password","Sex","Phone","Birthday","Teacher"};
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
        if (identify == 1)
        requestData(1);
        else {requestData(4,id);}
    }
    //请求数据
    public void requestData(int i,Object... parameters){
        String sql = null;
        List<StudentInfo> studentInfosList = null;
        if (i == 1) {
            sql = "select * from basicinformation where ID >= ?";
            studentInfosList = studentInfoDAO.queryMultiply(sql,StudentInfo.class,1);
        }
        if (i == 2){
            sql = "SELECT * FROM basicinformation ORDER BY ID ASC";
            studentInfosList = studentInfoDAO.queryMultiply(sql,StudentInfo.class);
        }
        if (i == 3){
            sql = "SELECT * FROM basicinformation ORDER BY ID DESC;";
            studentInfosList = studentInfoDAO.queryMultiply(sql,StudentInfo.class);
        }
        if (i == 4) {
            sql = "select * from basicinformation where ID = ?";
            studentInfosList = studentInfoDAO.queryMultiply(sql,StudentInfo.class,parameters);
        }

//        Vector<Vector>vectors=ListToVector.ListTOVector1(studentInfosList);
        Vector<Vector>vectors = new Vector<>();
        for (int i1 = 0; i1 <=studentInfosList.size()-1; i1++){
            StudentInfo studentInfo1 = new StudentInfo();
            studentInfo1 = studentInfosList.get(i1) ;
            Vector vector = new Vector<>();
            vector.add(studentInfo1.getId());
            vector.add(studentInfo1.getName());
            vector.add(studentInfo1.getPassword());
            vector.add(studentInfo1.getSex());
            vector.add(studentInfo1.getPhone());
            vector.add(studentInfo1.getBirthday());
            vector.add(studentInfo1.getTeacher());
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
