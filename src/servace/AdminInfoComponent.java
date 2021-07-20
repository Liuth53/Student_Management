package servace;

import JDBC.DAO.AdminDAO;
import JDBC.javaBean.AdminInfo;
import servace.listener.ActionDoneListener;
import servace.dialog.AddAdminDialog;
import servace.dialog.UpdateAdminDialog;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;


public class AdminInfoComponent extends Box {
    final int WIDTH=850;
    final int HEIGHT=600;
    JTextArea jta = new JTextArea(6, 30);
    JFrame jf = null;
    private JTable table;
    private Vector<String> titles;
    private Vector<Vector> tableData;
    private DefaultTableModel tableModel;

    AdminDAO adminDAO = new AdminDAO();


    public AdminInfoComponent(JFrame jf,int identify,int id){
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


//        Lambda 表达式，也可称为闭包，它是推动 Java 8 发布的最重要新特性。
//
//        Lambda 允许把函数作为一个方法的参数（函数作为参数传递进方法中）。
//
//        使用 Lambda 表达式可以使代码变的更加简洁紧凑。


//        addBtn.addActionListener(new ActionListener() {       //使用匿名类创建监听器
//            @Override
//            public void actionPerformed(ActionEvent e) 
//                //匿名类创建窗口
//                new AddAdminDialog(jf, "Add Information", true, new ActionDoneListener() {
//                    @Override
//                    public void done(Object result) {
//                        requestData(1);
//                    }
//                }).setVisible(true);
//            }
//        });

        //弹出一个对话框，让用户输入信息
        addBtn.addActionListener(e -> new AddAdminDialog(jf, "Add Information", true, result -> requestData(1)).setVisible(true));

        updateBtn.addActionListener(e -> {
            //获取当前表格选中的id
            int selectedRow = table.getSelectedRow();//如果有选中的条目，则返回条目的行号，如果没有选中，那么返回-1

            if (selectedRow==-1){
                JOptionPane.showMessageDialog(jf,"Please select the item you want to modify!");
                return;
            }

            String id1 = tableModel.getValueAt(selectedRow, 0).toString();
            //弹出一个对话框，让用户修改
            new UpdateAdminDialog(jf, "Modify Information", true, result -> {
                if (identify == 1)
                requestData(1);
            }, id1).setVisible(true);
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

            String id12 = tableModel.getValueAt(selectedRow, 0).toString();

//                System.out.println(id);

            int result1 = adminDAO.update("delete from administered where TeacherID = ?", id12);

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
            btnPanel.add(upBtn);
            btnPanel.add(downBtn);
            this.add(btnPanel);
        }
        //组装表格
        String[] ts = {"ID","Name","password","Post"};
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
        List<AdminInfo> adminInfosList;
        if (i == 1) {
            sql = "select * from administered";
        }
        if (i == 2){
            sql = "SELECT * FROM administered ORDER BY TeacherID ASC";
        }
        if (i == 3){
            sql = "SELECT * FROM administered ORDER BY TeacherID DESC;";
        }
        adminInfosList = adminDAO.queryMultiply(sql, AdminInfo.class);

//        Vector<Vector>vectors=ListToVector.ListTOVector1(studentInfosList);
        Vector<Vector>vectors = new Vector<>();
        for (int i1 = 0; i1 <= adminInfosList.size()-1; i1++){
            AdminInfo info;
            info = adminInfosList.get(i1);
            Vector vector = new Vector<>();
            vector.add(info.getTeacherID());
            vector.add(info.getName());
            vector.add(info.getPassword());
            vector.add(info.getPost());
            vectors.add(vector);
        }
        //清空表格
        tableData.clear();
        //插入数据
        //            new AddToModel(tableModel).addValueWithThread(vector);
        tableData.addAll(vectors);
        //刷新表格
        tableModel.fireTableDataChanged();
    }

}
