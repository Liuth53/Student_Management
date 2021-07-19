package servace.dialog;


import JDBC.DAO.AdminDAO;
import JDBC.DAO.DormitoryDAO;
import JDBC.DAO.GradeDAO;
import JDBC.DAO.StudentInfoDAO;
import JDBC.javaBean.AdminInfo;
import servace.listener.ActionDoneListener;
import utils.ScreenUtils;
import views.modules.DateChooserJButton;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.*;

public class AddDialog extends JFrame {
    final int WIDTH = 400;
    final int HEIGHT = 400;

    private ActionDoneListener listener;

    AdminDAO adminDAO = new AdminDAO();
    StudentInfoDAO studentInfoDAO = new StudentInfoDAO();
    DormitoryDAO dormitoryDAO = new DormitoryDAO();
    GradeDAO gradeDAO = new GradeDAO();



    public AddDialog(JFrame jf, String title, boolean isModel, ActionDoneListener listener){
//        super(jf,title,isModel);
        this.listener  = listener;
        //组装视图
        this.setBounds((ScreenUtils.getScreenWidth()-WIDTH)/2,(ScreenUtils.getScreenHeight()-HEIGHT)/2,WIDTH,HEIGHT);

        Box vBox = Box.createVerticalBox();

        //组装名称
        Box nameBox = Box.createHorizontalBox();
        JLabel nameLabel = new JLabel("Name：");
        JTextField nameField = new JTextField(25);

        nameBox.add(nameLabel);
        nameBox.add(Box.createHorizontalStrut(20));
        nameBox.add(nameField);

        //组装密码
        Box pwBox = Box.createHorizontalBox();
        JLabel pwLabel = new JLabel("Password：");
        JTextField pwField = new JTextField(15);

        pwBox.add(pwLabel);
        pwBox.add(Box.createHorizontalStrut(20));
        pwBox.add(pwField);

        //组装性别
        //性别实现单选的效果
        Box sexBox = Box.createHorizontalBox();
        JLabel sexLabel = new JLabel("Sex：");
        JRadioButton maleBtn = new JRadioButton("man",true);
        JRadioButton femaleBtn = new JRadioButton("woman",false);
        ButtonGroup bg = new ButtonGroup();
        bg.add(maleBtn);
        bg.add(femaleBtn);
        sexBox.add(sexLabel);
        sexBox.add(Box.createHorizontalStrut(20));
        sexBox.add(maleBtn);
        sexBox.add(femaleBtn);
        sexBox.add(Box.createHorizontalStrut(120));

//        JTextField sexField = new JTextField(15);



//        sexBox.add(Box.createHorizontalStrut(20));


        //组装phone
        Box phoneBox = Box.createHorizontalBox();
        JLabel phoneLabel = new JLabel("Phone：");
        JTextField phoneField = new JTextField(15);

        phoneBox.add(phoneLabel);
        phoneBox.add(Box.createHorizontalStrut(20));
        phoneBox.add(phoneField);


        //组装生日
        Box birBox = Box.createHorizontalBox();
        JLabel birLabel = new JLabel("Birthday：");
//        JTextArea birArea = new JTextArea(3,15);
        DateChooserJButton dateChooserJButton = new DateChooserJButton();

        birBox.add(birLabel);
        birBox.add(Box.createHorizontalStrut(20));
        birBox.add(dateChooserJButton);

        JLabel Label1 = new JLabel("Teacher  ：");
        JComboBox<Object> comboBox = new JComboBox<>();
        ArrayList<String> teachers = new ArrayList<>();
        List<AdminInfo> adminInfos = adminDAO.queryMultiply("select Name from administered where TeacherID >= ?", AdminInfo.class, 1);
        for (AdminInfo info:adminInfos){
            teachers.add(info.getName());
            comboBox.addItem(info.getName());

        }
        Box Box1 = Box.createHorizontalBox();
        Box1.add(Label1);
        Box1.add(Box.createHorizontalStrut(20));
        Box1.add(comboBox);
        StringBuffer teacherName = new StringBuffer();
        comboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() ==ItemEvent.SELECTED){
                    teacherName.delete(0,teacherName.length());
                    teacherName.append(e.getItem().toString());
//                    System.out.println(teacherName);
                }
            }
        });

        //组装按钮
        Box btnBox = Box.createHorizontalBox();
        JButton addBtn = new JButton("Add");
        JButton close = new JButton("Close");
        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //获取用户的录入
                String name = nameField.getText().trim();
                String password = pwField.getText().trim();
                String sex = bg.isSelected(maleBtn.getModel())? maleBtn.getText():femaleBtn.getText();
                String phone = phoneField.getText().trim();
//                String birthday = dateChooserJButton.getText().trim();
                Date birthday = dateChooserJButton.getDate();
                String teacher = comboBox.getSelectedItem().toString();

                Map<String,String> params = new HashMap<>();
                params.put("name",name);
                params.put("sex", sex);
                params.put("phone", phone);
                params.put("password", password);
                params.put("birthday", String.valueOf(birthday));

                int i = studentInfoDAO.update("insert into basicinformation values(?,?,?,?,?,?,?)", null, name, password, sex, phone, birthday, teacher);
                JTextArea jta = new JTextArea(6, 30);
                if (i != -1){
                    jta.setText("Successful");
                    dispose();
                }
                else {jta.setText("Failed"); }
                JOptionPane.showMessageDialog(jf, jta.getText(), "Load Message", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        //组装纵向视图
        btnBox.add(addBtn);
        vBox.add(Box.createVerticalStrut(20));
        vBox.add(nameBox);
        vBox.add(Box.createVerticalStrut(15));
        vBox.add(pwBox);
        vBox.add(Box.createVerticalStrut(15));
        vBox.add(sexBox);
        vBox.add(Box.createVerticalStrut(15));
        vBox.add(phoneBox);
        vBox.add(Box.createVerticalStrut(15));
        vBox.add(birBox);
        vBox.add(Box.createVerticalStrut(15));
        vBox.add(Box1);
        vBox.add(Box.createVerticalStrut(15));
        vBox.add(btnBox);

        //为了左右有间距，在vBox外层封装一个水平的Box，添加间隔
        Box hBox = Box.createHorizontalBox();
        hBox.add(Box.createHorizontalStrut(20));
        hBox.add(vBox);
        hBox.add(Box.createHorizontalStrut(20));
        this.add(hBox);

    }

}
