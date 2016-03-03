import java.io.File;
import java.nio.file.attribute.FileTime;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.table.AbstractTableModel;

public class AbstractFileModel extends AbstractTableModel {

	private String[] columnNames;
	protected Vector dataVector;

	public AbstractFileModel(String [] columns) {
		columnNames = columns;
		dataVector = new Vector<>();
	}

	public String getColumnName(int column) {
		return columnNames[column];
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return dataVector.size();
	}

	@Override
	public Object getValueAt(int row, int column) {
		FileModel fm = (FileModel) dataVector.get(row);
		return fm.GetValue(column);

	}
	@Override
    public Class getColumnClass(int column)
    {
        if (column == 0) return ImageIcon.class; 
        return Object.class;
    }

	public void setValueAt(Object value, int row, int column) {
		FileModel fm = (FileModel) dataVector.get(row);
		fm.setValue(value, column);
		fireTableCellUpdated(row, column);
	}

	public boolean hasEmptyRow() {
		if (dataVector.size() == 0)
			return false;
		FileModel fm = (FileModel) dataVector.get(dataVector.size() - 1);
		if (fm.getFileName().trim().equals("") && String.valueOf(fm.getSize()).trim().equals("")
				&& fm.getType().toString().trim().equals("") && fm.getLastModified().toString().trim().equals("")
				&& fm.getLastAccess().toString().trim().equals("") && fm.getCreatedTime().toString().trim().equals("")
				&& fm.getImg() == null) {
			return true;
		} else
			return false;
	}

	public void addEmptyRow() {
//		dataVector.add(new FileModel());
//		fireTableRowsInserted(dataVector.size() - 1, dataVector.size() - 1);
	}
	public void addRow(File f){
		
		dataVector.add(new FileModel(f));
		fireTableRowsInserted(dataVector.size() - 1, dataVector.size() - 1);
	}
	public void DeleteRow(int rowIndex){
		
		dataVector.remove(rowIndex);
		fireTableRowsDeleted(rowIndex, rowIndex);
	}

}
