package views;

import JDBC.DAO.AdminDAO;
import JDBC.DAO.StudentInfoDAO;
import JDBC.javaBean.AdminInfo;
import JDBC.javaBean.StudentInfo;
import utils.ScreenUtils;
import views.modules.BackGroundPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class LoginInterface {
    public static void main(String[] args) throws IOException {
        new LoginInterface().init();
    }

    public void init() throws IOException {
        AdminDAO adminDAO = new AdminDAO();
        StudentInfoDAO studentInfoDAO = new StudentInfoDAO();

        JFrame jf = new JFrame("Loading Message");
        JTextArea jta = new JTextArea(6, 15);
        JFrame jFrame = new JFrame("欢迎使用AA大学学生信息管理系统-R交通191-28-柳天航");
        Label label1 = new Label("Student Management System");
        Label label2 = new Label("Welcome to use Student Management System");
        JLabel uLabel = new JLabel("User Name:");
        JTextField uTextField = new JTextField(15);
        JLabel pLabel = new JLabel("Password :");
        JTextField pTextField = new JTextField(15);

        JButton jButton1 = new JButton("LoginInterface As Users");
        JButton jButton2 = new JButton("LoginInterface As Admins");
        JButton jButton3 = new JButton("Register");


        //用户身份验证
        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String userName = uTextField.getText().trim();
                String password = pTextField.getText().trim();
                StudentInfo result1 = studentInfoDAO.querySingle("select Name , password  from basicinformation where Name =? and password =?",
                        StudentInfo.class, userName, password);
                if (result1 != null){
                    jta.setText("Successful");
                    try {
                        new UserInterface().init(userName);
                        jFrame.dispose();
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
                else {jta.setText("Failed"); }

                JOptionPane.showMessageDialog(jf, jta.getText(), "Load Message", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        //管理员身份验证
        jButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userName = uTextField.getText().trim();
                String password = pTextField.getText().trim();
                AdminInfo result1 = adminDAO.querySingle("select Name , password  from administered where Name =? and password =?",
                        AdminInfo.class, userName, password);
//                System.out.println(result1 !=null ?"Successful":"Fail");
                if (result1 != null){
                    jta.setText("Successful");
                    try {
                        new ManagerInterface().init(userName);
                        jFrame.dispose();
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
                else {jta.setText("Failed");}

                JOptionPane.showMessageDialog(jf, jta.getText(), "Load Message", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        //注册按钮
        jButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //打开注册界面
                Register register = new Register();
                try {
                    register.register();
                    jFrame.dispose();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });


        //窗口总体布局
        int Height = ScreenUtils.getScreenHeight();
        int Width = ScreenUtils.getScreenWidth();
        jFrame.setBounds((Width-500)/2,(Height-500)/2,Height/2,Width/4);
        jFrame.setIconImage(ImageIO.read(new File("src/icons/icon1.jpg")));
        BackGroundPanel backGroundPanel = new BackGroundPanel(ImageIO.read(new File("src/icons/backwin11.jpg")));
        //容器定义
        Box vbox = Box.createVerticalBox();
        Box ubox = Box.createHorizontalBox();
        Box pbox = Box.createHorizontalBox();
        Box btnbox = Box.createHorizontalBox();
        //用户名组件组装
        ubox.add(uLabel);
        ubox.add(Box.createHorizontalStrut(20));
        ubox.add(uTextField);
        //密码组件组装
        pbox.add(pLabel);
        pbox.add(Box.createHorizontalStrut(20));
        pbox.add(pTextField);
        //按钮组装
//        pbox.add(Box.createHorizontalStrut(20));
        btnbox.add(jButton1);
//        pbox.add(Box.createHorizontalStrut(20));
        btnbox.add(jButton2);
//        pbox.add(Box.createHorizontalStrut(20));
        btnbox.add(jButton3);
        //纵向视图组装
        vbox.add(Box.createVerticalStrut(Height/6));
        vbox.add(ubox);
        vbox.add(Box.createVerticalStrut(50));
        vbox.add(pbox);
        vbox.add(Box.createVerticalStrut(50));
        vbox.add(btnbox);




        backGroundPanel.add(vbox);
//        jFrame.setResizable(true);
        jFrame.add(backGroundPanel,BorderLayout.CENTER);
        jFrame.setVisible(true);
//        jFrame.pack();
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }


}
