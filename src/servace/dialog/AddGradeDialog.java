package servace.dialog;

import JDBC.DAO.AdminDAO;
import servace.listener.ActionDoneListener;
import utils.ScreenUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddGradeDialog extends JDialog {
    final int WIDTH = 400;
    final int HEIGHT = 300;
    AdminDAO adminDAO = new AdminDAO();
    private ActionDoneListener listener;

    public AddGradeDialog(JFrame jf, String title, boolean isModel, ActionDoneListener listener){
        super(jf,title,isModel);
        this.listener  = listener;
        //组装视图
        this.setBounds((ScreenUtils.getScreenWidth()-WIDTH)/2,(ScreenUtils.getScreenHeight()-HEIGHT)/2,WIDTH,HEIGHT);

        Box vBox = Box.createVerticalBox();
        //组装ID
        Box iDBox = Box.createHorizontalBox();
        JLabel iDLabel = new JLabel("StudentID:");
        JTextField iDField = new JTextField(15);

        iDBox.add(iDLabel);
        iDBox.add(Box.createHorizontalStrut(20));
        iDBox.add(iDField);

        //组装姓名
        Box cBox = Box.createHorizontalBox();
        JLabel cLabel = new JLabel("CourseID:");
        JTextField cField = new JTextField(15);

        cBox.add(cLabel);
        cBox.add(Box.createHorizontalStrut(20));
        cBox.add(cField);

        //组装密码
        Box passwordBox = Box.createHorizontalBox();
        JLabel passwordLabel = new JLabel("Grade:");
        JTextField gradeField= new JTextField(15);

        passwordBox.add(passwordLabel);
        passwordBox.add(Box.createHorizontalStrut(20));
        passwordBox.add(gradeField);

        //组装按钮
        Box btnBox = Box.createHorizontalBox();
        JButton addBtn = new JButton("Add");
        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //获取用户的录入
                String name = cField.getText().trim();
                String grade = gradeField.getText().trim();

                int id =Integer.parseInt(iDField.getText().trim()) ;




                int i = adminDAO.update("insert IGNORE into studentsgrade values(?,?,?)", id, name , grade);
                JTextArea jta = new JTextArea(6, 30);
                if (i != -1){
                    jta.setText("Successful");
                    dispose();

                }
                else {jta.setText("Failed"); }

                JOptionPane.showMessageDialog(jf, jta.getText(), "Load Message", JOptionPane.INFORMATION_MESSAGE);
            }
        });


        btnBox.add(addBtn);
        vBox.add(Box.createVerticalStrut(20));
        vBox.add(iDBox);
        vBox.add(Box.createVerticalStrut(20));
        vBox.add(cBox);
        vBox.add(Box.createVerticalStrut(15));
        vBox.add(passwordBox);
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
