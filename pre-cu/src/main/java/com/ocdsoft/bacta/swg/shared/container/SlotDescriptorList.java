package com.ocdsoft.bacta.swg.shared.container;

import bacta.iff.Iff;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ocdsoft.bacta.swg.shared.foundation.CrcLowerString;
import com.ocdsoft.bacta.tre.TreeFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by crush on 4/22/2016.
 * <p>
 * Manages the list of SlotDescriptor objects.
 * <p>
 * This class provides an interface for creating SlotDescriptor
 * objects based on SlotDescriptor files.  So long as a single reference exists
 * to a SlotDescriptor, that SlotDescriptor will stay loaded and will be returned
 * to any caller that asks for it by the same filename.
 */
@Singleton
public class SlotDescriptorList {
    private static final Logger LOGGER = LoggerFactory.getLogger(SlotDescriptorList.class);

    private final Map<CrcLowerString, SlotDescriptor> descriptors = new HashMap<>();
    private final TreeFile treeFile; //Used for loading unloaded descriptors.
    private final SlotIdManager slotIdManager;

    @Inject
    public SlotDescriptorList(final TreeFile treeFile,
                              final SlotIdManager slotIdManager) {
        this.treeFile = treeFile;
        this.slotIdManager = slotIdManager;
    }

    public SlotDescriptor fetch(final CrcLowerString filename) {
        SlotDescriptor descriptor = descriptors.get(filename);

        if (descriptor != null)
            return descriptor;

        final String filenameString = filename.getString();
        final byte[] fileBytes = treeFile.open(filenameString);

        if (fileBytes == null) {
            LOGGER.warn("Specified SlotDescriptor file [{}] does not exist", filenameString);
            return null;
        }

        final Iff iff = new Iff(filenameString, fileBytes);
        descriptor = new SlotDescriptor(slotIdManager, iff, filename);

        descriptors.put(filename, descriptor);

        descriptor.fetch();

        return descriptor;
    }

    public SlotDescriptor fetch(final String filename) {
        return fetch(new CrcLowerString(filename));
    }

    void stopTracking(final SlotDescriptor descriptor) {
        final CrcLowerString filename = descriptor.getName();
        descriptors.remove(filename);
    }
}
