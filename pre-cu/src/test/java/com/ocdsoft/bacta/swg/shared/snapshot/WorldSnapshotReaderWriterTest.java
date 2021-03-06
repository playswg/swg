package com.ocdsoft.bacta.swg.shared.snapshot;

import com.ocdsoft.bacta.swg.server.game.object.template.server.ServerCreatureObjectTemplateTest;
import com.ocdsoft.bacta.tre.TreeFile;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.nio.file.Paths;

/**
 * Created by crush on 6/4/2016.
 */
public final class WorldSnapshotReaderWriterTest {
    private static final String resourcesPath = new File(Paths.get("build", "resources", "test").toUri()).getPath();
    private final TreeFile treeFile = new TreeFile();

    @Before
    public void setup() {
        treeFile.addSearchPath(resourcesPath, 1);
    }

    @Test
    public void shouldLoadSnapshot() {
        final WorldSnapshotReaderWriter worldSnapshotReaderWriter = new WorldSnapshotReaderWriter(treeFile);
        Assert.assertTrue(worldSnapshotReaderWriter.load("dungeon1"));
        Assert.assertEquals(48, worldSnapshotReaderWriter.getNumberOfNodes());
        Assert.assertEquals(3216, worldSnapshotReaderWriter.getTotalNumberOfNodes());
    }
}
