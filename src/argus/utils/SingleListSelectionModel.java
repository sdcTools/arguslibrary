package argus.utils;

import javax.swing.DefaultListSelectionModel;

/**
 * Keeps a single item selected and doesn't deselect a selected item if it is 
 * clicked and the cltr-key is pressed down.
 */
public class SingleListSelectionModel extends DefaultListSelectionModel {

    @Override
    public void removeSelectionInterval(int index0, int index1) {
        // we don't want the control deselect stuff
        if (!getValueIsAdjusting()) {
            super.removeSelectionInterval(index0, index1);
        }
    }

    @Override
    public int getSelectionMode() {
        return SINGLE_SELECTION;
    }
}
