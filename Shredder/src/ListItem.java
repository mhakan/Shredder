import javax.swing.DefaultListModel;

/**
 * 
 */

/**
 * @author Selami
 *
 */

public class ListItem extends DefaultListModel<String> {

	private static ListItem listItems = null;

	public static ListItem GetListModel() {
		if (listItems == null)
			listItems = new ListItem();
		return listItems;

	}

	private ListItem() {

	}

	public void AddListItem(String item) {
		listItems.addElement(item);
	}

	public void ClearListItem() {

		listItems.clear();
	}
	
	public void RemoveAt(int i){
		
		listItems.removeElementAt(i);
		
	}
}
