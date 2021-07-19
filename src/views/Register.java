package views;

import JDBC.DAO.*;
import JDBC.javaBean.AdminInfo;
import utils.ScreenUtils;
import views.modules.BackGroundPanel;
import views.modules.DateChooserJButton;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Register extends JFrame {
    public void register() throws IOException {
        JFrame register = new JFrame("Register");
        //窗口总体布局
        int Height = ScreenUtils.getScreenHeight();
        int Width = ScreenUtils.getScreenWidth();
        register.setBounds((Width-500)/2,(Height-500)/2,Height/2,Width/6);
        register.setIconImage(ImageIO.read(new File("src/icons/icon1.jpg")));
        BackGroundPanel backGroundPanel = new BackGroundPanel(ImageIO.read(new File("src/icons/backwin11.jpg")));

        Box vBox = Box.createVerticalBox();
        Box uBox = Box.createHorizontalBox();
        Box pBox = Box.createHorizontalBox();
        Box tBox = Box.createHorizontalBox();
        Box gBox = Box.createHorizontalBox();
        Box Box1 = Box.createHorizontalBox();
        Box dBox = Box.createHorizontalBox();
        Box btnBox = Box.createHorizontalBox();

        JLabel uLabel = new JLabel("User Name：");
        JTextField uField = new JTextField(15);
        JLabel pLabel = new JLabel("Password ：");
        JTextField pField = new JTextField(15);
        JLabel tLabel = new JLabel("Phone    ：    ");
        JTextField tField = new JTextField(15);
        JLabel gLabel = new JLabel("Sex      ：        ");
        JTextField bField1 = new JTextField(15);
        JLabel Label1 = new JLabel("Teacher  ：");
        JComboBox<Object> objectJComboBox = new JComboBox<>();
        JLabel dLabel = new JLabel("Birthday ：");
        DateChooserJButton dateChooserJButton = new DateChooserJButton();

        JButton registerBtn = new JButton("sign in");
        JButton backBtn = new JButton("sign up");

        AdminDAO adminDAO = new AdminDAO();
        StudentInfoDAO studentInfoDAO = new StudentInfoDAO();
        DormitoryDAO dormitoryDAO = new DormitoryDAO();
        GradeDAO gradeDAO = new GradeDAO();
        JComboBox<Object> comboBox = new JComboBox<>();
        ArrayList<String> teachers = new ArrayList<>();
        List<AdminInfo> adminInfos = adminDAO.queryMultiply(
                "select Name from administered where TeacherID >= ?", AdminInfo.class, 1);
        for (AdminInfo info:adminInfos){
            teachers.add(info.getName());
            comboBox.addItem(info.getName());

        }


        uBox.add(uLabel);
        uBox.add(Box.createHorizontalStrut(20));
        uBox.add(uField);

        pBox.add(pLabel);
        pBox.add(Box.createHorizontalStrut(20));
        pBox.add(pField);

        tBox.add(tLabel);
        tBox.add(Box.createHorizontalStrut(20));
        tBox.add(tField);
        //性别实现单选的效果
        JRadioButton maleBtn = new JRadioButton("man",true);
        JRadioButton femaleBtn = new JRadioButton("woman",false);
        ButtonGroup bg = new ButtonGroup();
        bg.add(maleBtn);
        bg.add(femaleBtn);

        //横向视图组装
        gBox.add(gLabel);
        gBox.add(Box.createHorizontalStrut(20));
        gBox.add(maleBtn);
        gBox.add(femaleBtn);
        gBox.add(Box.createHorizontalStrut(120));
        btnBox.add(registerBtn);
        btnBox.add(Box.createHorizontalStrut(80));
        btnBox.add(backBtn);

        Box1.add(Label1);
        Box1.add(Box.createHorizontalStrut(20));
        Box1.add(comboBox);

        dBox.add(dLabel);
        dBox.add(Box.createHorizontalStrut(20));
        dBox.add(dateChooserJButton);
        dBox.add(Box.createHorizontalStrut(180));

        //纵向视图组装
        vBox.add(Box.createVerticalStrut(50));
        vBox.add(uBox);
        vBox.add(Box.createVerticalStrut(20));
        vBox.add(pBox);
        vBox.add(Box.createVerticalStrut(20));
        vBox.add(tBox);
        vBox.add(Box.createVerticalStrut(20));
        vBox.add(gBox);
        vBox.add(Box.createVerticalStrut(20));
        vBox.add(dBox);
        vBox.add(Box.createVerticalStrut(20));
        vBox.add(Box1);
        vBox.add(Box.createVerticalStrut(20));
        vBox.add(btnBox);
        backGroundPanel.add(vBox);
        register.add(backGroundPanel);


        StringBuffer teacherName = new StringBuffer();
        comboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() ==ItemEvent.SELECTED){
                    teacherName.delete(0,teacherName.length());
                    teacherName.append(e.getItem().toString());
                    System.out.println(teacherName);
                }
            }
        });

        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //返回到登录页面即可
                try {
                    new LoginInterface().init();
                    register.dispose();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });





            registerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = uField.getText().trim();
                String password = pField.getText().trim();
                String phone = tField.getText().trim();
                Date birthday = dateChooserJButton.getDate();
                Timestamp timestamp = new Timestamp(birthday.getTime());
                String teacher = comboBox.getSelectedItem().toString();
                String gender = bg.isSelected(maleBtn.getModel())? maleBtn.getText():femaleBtn.getText();
                int i = studentInfoDAO.update("insert into basicinformation values(?,?,?,?,?,?,?)",
                        null,username, password,gender,phone, timestamp,teacher);
                System.out.println(i>0? "Success":"Fail");

            }
        });
            register.pack();
        register.setVisible(true);
        register.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }
}
