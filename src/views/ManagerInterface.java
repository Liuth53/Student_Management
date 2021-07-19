package views;

import JDBC.DAO.AdminDAO;
import JDBC.DAO.DormitoryDAO;
import JDBC.DAO.GradeDAO;
import JDBC.DAO.StudentInfoDAO;
import JDBC.javaBean.AdminInfo;
import servace.*;
import utils.ScreenUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ManagerInterface {
    JFrame jf = new JFrame("AA大学学生管理系统-R交通191-28-柳天航");

    final int WIDTH = 1000;
    final int HEIGHT = 600;

    //组装视图
    public void init(String Name) throws Exception {
        AdminDAO adminDAO = new AdminDAO();
        StudentInfoDAO studentInfoDAO = new StudentInfoDAO();
        DormitoryDAO dormitoryDAO = new DormitoryDAO();
        GradeDAO gradeDAO = new GradeDAO();
        //获取用户名
        int id= adminDAO.querySingle("select * from administered where Name = ? ", AdminInfo.class, Name).getTeacherID();
//        jf.setTitle(jf.getTitle()+" Welcome!"+Name);
        //给窗口设置属性
        jf.setBounds((ScreenUtils.getScreenWidth() - WIDTH) / 2, (ScreenUtils.getScreenHeight() - HEIGHT) / 2, WIDTH, HEIGHT);
        jf.setResizable(false);
        jf.setIconImage(ImageIO.read(new File("src/icons/icon1.jpg")));
        //设置分割面板
        JSplitPane sp = new JSplitPane();
        //设置菜单栏
        JMenuBar jmb = new JMenuBar();
        JMenu jMenu = new JMenu("Setting");
        JMenuItem m1 = new JMenuItem("Switch Account");
        JMenuItem m2 = new JMenuItem("Exit");
        m1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new LoginInterface().init();
                    jf.dispose();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        m2.addActionListener(e -> System.exit(0));
        jMenu.add(m1);
        jMenu.add(m2);
        jmb.add(jMenu);

        jf.setJMenuBar(jmb);

        //支持连续布局
        sp.setContinuousLayout(true);
        sp.setDividerLocation(150);
        sp.setDividerSize(7);

        //设置左侧内容
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Management");
        DefaultMutableTreeNode gradeInfoManage = new DefaultMutableTreeNode("GradeInformation");
        DefaultMutableTreeNode basicInfManage = new DefaultMutableTreeNode("BasicInformation");
        DefaultMutableTreeNode AdminManage = new DefaultMutableTreeNode("AdministeredInformation");
        DefaultMutableTreeNode dormitoryInfo = new DefaultMutableTreeNode("DormitoryInformation");
        DefaultMutableTreeNode coursesInfo = new DefaultMutableTreeNode("CoursesInformation");

        root.add(basicInfManage);
        root.add(gradeInfoManage);
        root.add(dormitoryInfo);
        root.add(AdminManage);
        root.add(coursesInfo);


        //窗口组件颜色设置
        Color color = new Color(203,220,217);
        JTree tree = new JTree(root);
        MyRenderer myRenderer = new MyRenderer();
        myRenderer.setBackgroundNonSelectionColor(color);
        myRenderer.setBackgroundSelectionColor(new Color(140,140,140));
        tree.setCellRenderer(myRenderer);
        tree.setBackground(color);

        //设置当前tree默认选项
        tree.setSelectionRow(1);
        //当条目选中变化后，这个方法会执行
        tree.addTreeSelectionListener(e -> {
            //得到当前选中的结点对象
            Object lastPathComponent = e.getNewLeadSelectionPath().getLastPathComponent();

            if (gradeInfoManage.equals(lastPathComponent)){
                sp.setRightComponent(new GradeInfoComponent(jf,1,id));
                sp.setDividerLocation(150);
            }else if (basicInfManage.equals(lastPathComponent)){
                sp.setRightComponent(new BasicInfoComponent(jf,1,id));
                sp.setDividerLocation(150);
            } if (AdminManage.equals(lastPathComponent)){
                sp.setRightComponent(new AdminInfoComponent(jf,1,id));
                sp.setDividerLocation(150);
            } if (dormitoryInfo.equals(lastPathComponent)){
                sp.setRightComponent(new DormitoryComponent(jf,1,id));
                sp.setDividerLocation(150);
            }if (coursesInfo.equals(lastPathComponent)){
                sp.setRightComponent(new CoursesInfoComponent(jf,1));
                sp.setDividerLocation(150);
            }

        });
        sp.setRightComponent(new BasicInfoComponent(jf,1,id));
        sp.setLeftComponent(tree);
//        sp.setLeftComponent();
        jf.add(sp);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    //自定义结点绘制器
    private class MyRenderer extends DefaultTreeCellRenderer {
        private ImageIcon rootIcon = null;
        private ImageIcon userManageIcon = null;
        private ImageIcon gradeInfoIcon = null;
        private ImageIcon adminManageIcon = null;
        private ImageIcon DormManageIcon = null;
        private ImageIcon coursesInfoIcon = null;

        public MyRenderer() {
            rootIcon = new ImageIcon("src/icons/icon_user.png");
            userManageIcon = new ImageIcon("src/icons/icon_user.png");
            gradeInfoIcon = new ImageIcon("src/icons/icon1_book.png");
            adminManageIcon = new ImageIcon("src/icons/icon_Manager.png");
            DormManageIcon = new ImageIcon("src/icons/systemManage.png");
            coursesInfoIcon = new ImageIcon("src/icons/icon1_book.png");
        }

        //当绘制树的每个结点时，都会调用这个方法
        @Override
        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
            //使用默认绘制
            super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

            ImageIcon image = null;
            switch (row) {
                case 0:
                    image = rootIcon;
                    break;
                case 1:
                    image = userManageIcon;
                    break;
                case 2:
                    image = gradeInfoIcon;
                    break;
                case 3:
                    image = adminManageIcon;
                    break;
                case 4:
                    image = DormManageIcon;
                    break;
                case 5:
                    image = coursesInfoIcon;
                    break;
            }

            this.setIcon(image);
            return this;
        }
    }

}
