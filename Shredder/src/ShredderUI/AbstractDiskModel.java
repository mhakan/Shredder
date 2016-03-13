package ShredderUI;

import java.io.File;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.table.AbstractTableModel;

public class AbstractDiskModel extends AbstractTableModel {
	private String[] columnNames;
	protected Vector dataVector;

	public AbstractDiskModel(String[] columns) {
		columnNames = columns;
		dataVector = new Vector<>();
	}

	public String getColumnName(int column) {
		return columnNames[column];
	}

	public int getColumnCount() {
		return columnNames.length;
	}

	public int getRowCount() {
		return dataVector.size();
	}

	public Object getValueAt(int row, int column) {
		DiskModel dm = (DiskModel) dataVector.get(row);
		return dm.GetValue(column);

	}

	@Override
	public Class getColumnClass(int column) {
		if (column == 0)
			return ImageIcon.class;
		return Object.class;
	}

	public void setValueAt(Object value, int row, int column) {
		DiskModel dm = (DiskModel) dataVector.get(row);
		dm.setValue(value, column);
		fireTableCellUpdated(row, column);
	}

	public void RemoveAll() {

		int i = dataVector.size();
		dataVector.clear();
		fireTableRowsDeleted(0, i);
	}

	public void addEmptyRow() {
		// dataVector.add(new FileModel());
		// fireTableRowsInserted(dataVector.size() - 1, dataVector.size() - 1);
	}

	public void addRow(String[] f) {

		dataVector.add(new DiskModel(f));
		fireTableRowsInserted(dataVector.size() - 1, dataVector.size() - 1);
	}

	public void DeleteRow(int rowIndex) {

		dataVector.remove(rowIndex);
		fireTableRowsDeleted(rowIndex, rowIndex);
	}

}