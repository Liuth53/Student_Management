package utils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Vector;

public class AddToModel {
    public AddToModel(DefaultTableModel model) {
        this.model = model;
    }

    DefaultTableModel model;
    public void deleteRows(int rowCount) throws Exception {
        if (rowCount >= model.getColumnCount()) {
            throw new Exception("删除的行数不能超过model的总行数！");
        } else {
            for (int i = rowCount - 1; i >= 0; i--) {
                model.removeRow(i);
            }
        }
    }
    public void addValueWithThread(final Vector value) {
        Thread thread = new Thread() {
            public void run() {
                Runnable runnable = new Runnable() {
                    public void run() {
                        model.addRow(value);
                        if (model.getRowCount() > 5) {
                            try {
                                deleteRows(2);
                            } catch (Exception e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    }
                };
                SwingUtilities.invokeLater(runnable);
            }
        };
        thread.start();
    }
}
