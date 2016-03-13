package ShredderUI;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

public class EvenOddRenderer implements TableCellRenderer {

	public EvenOddRenderer() {
		super();

	}

	public static final DefaultTableCellRenderer render = new DefaultTableCellRenderer();

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		Component renderer = render.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		((JLabel) renderer).setOpaque(true);
		Color foreground = Color.BLACK, background = Color.white;
		if (isSelected) {
			foreground = Color.WHITE;
			background = Color.BLUE;
		} else {
			if (row % 2 == 0) {
				foreground = Color.BLACK;
				background = Color.LIGHT_GRAY;
			} else if (row % 2 == 1) {
				foreground = Color.BLACK;
				background = (Color.WHITE);
			}
		}
		renderer.setForeground(foreground);
		renderer.setBackground(background);
		return renderer;

	}
}
