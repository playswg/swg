package com.ocdsoft.bacta.swg.shared.portal;

import com.ocdsoft.bacta.swg.shared.container.Container;
import com.ocdsoft.bacta.swg.shared.foundation.Tag;
import com.ocdsoft.bacta.swg.shared.math.Transform;
import com.ocdsoft.bacta.swg.shared.object.GameObject;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * Created by crush on 4/27/2016.
 */
public class PortalProperty extends Container {
    public static int TAG_PRTP = Tag.convertStringToTag("PRTP");

    public static int getClassPropertyId() {
        return 0x939616B9;
    }

    @Getter
    private PortalPropertyTemplate portalPropertyTemplate;
    private List<CellProperty> cellList;
    private List<FixupRec> fixupList;
    private boolean hasPassablePortalToParentCell;

    public PortalProperty(final GameObject owner, final String filename) {
        super(getClassPropertyId(), owner);

        portalPropertyTemplate = null; //getch portal property template from list.
        //this.cellList = new ArrayList<>(portalPropertyTemplate.getNumberOfCells());
        hasPassablePortalToParentCell = false;
    }

    @AllArgsConstructor
    public static final class FixupRec {
        public final GameObject object;
        public final Transform transform;
    }
}
