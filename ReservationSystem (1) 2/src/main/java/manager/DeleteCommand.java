/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package manager;

import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author PARKSOHYUN
 */
public class DeleteCommand implements Command {
    private DefaultTableModel model;
    private int row;
    private Object[] deletedRowData;
    private ProductData productData;
    private String filename;
    private String delimiter;
    private ProductTablePanel productTablePanel;

    public DeleteCommand(DefaultTableModel model, int row, ProductData productData, ProductTablePanel productTablePanel) {
        this.model = model;
        this.row = row;
        this.productData = productData;
        this.productTablePanel = productTablePanel;
       
    }

    @Override
    public void execute() {
        // 삭제된 행의 데이터를 저장
        deletedRowData = new Object[model.getColumnCount()];
        for (int i = 0; i < model.getColumnCount(); i++) {
            deletedRowData[i] = model.getValueAt(row, i);
        }
        
        // 선택한 행을 삭제
        model.removeRow(row);
        System.out.println("삭제되었습니다.");
        // 파일 데이터 갱신
        String panelName = productTablePanel.getCurrentPanelName();
        List<String[]> currentData = productTablePanel.getDataForPanel(panelName);
        productData.updateFileData(currentData, productTablePanel.getFilename(), productTablePanel.getDelimiter());

    }

    @Override
    public void undo() {
        // 삭제한 행을 다시 추가합니다.
        model.insertRow(row, deletedRowData);
        System.out.println("삭제된 행이 복구되었습니다.");
        // 파일 데이터 갱신
        String panelName = productTablePanel.getCurrentPanelName();
        List<String[]> currentData = productTablePanel.getDataForPanel(panelName);
        productData.updateFileData(currentData, productTablePanel.getFilename(), productTablePanel.getDelimiter());
        
    }
}
