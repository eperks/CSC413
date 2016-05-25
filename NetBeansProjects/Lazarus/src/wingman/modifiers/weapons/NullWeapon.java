package wingman.modifiers.weapons;

import wingman.game.*;

/**
 *
 * @author noslide
 */
public class NullWeapon extends AbstractWeapon {

    /**
     *
     * @param theShip
     */
    @Override
	public void fireWeapon(Ship theShip) {
		return;
	}

    /**
     *
     * @param theObject
     */
    @Override
	public void read(Object theObject) {		
	}

}
