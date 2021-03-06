package com.ocdsoft.tre;

import com.ocdsoft.bacta.tre.TreeFile;
import com.ocdsoft.bacta.tre.UnsupportedTreeFileException;
import com.ocdsoft.bacta.tre.UnsupportedTreeFileVersionException;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;

/**
 * Created by crush on 12/16/2014.
 */
public class TreeFileTest {
    private static final String resourcesPath = new File(Paths.get("build", "resources", "test").toUri()).getAbsolutePath();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void shouldExistInTreeFile() throws Exception {
        TreeFile treeFile = new TreeFile();
        treeFile.addSearchTree(resourcesPath + "/test.tre", 0);
        Assert.assertNotNull(treeFile.open("does/exist.iff"));
    }

    @Test
    public void shouldNotExistInTreeFile() throws Exception {
        TreeFile treeFile = new TreeFile();
        treeFile.addSearchTree(resourcesPath + "/test.tre", 0);
        Assert.assertNull(treeFile.open("does/not/exist.iff"));
    }

    @Test
    public void shouldThrowUnsupportedTreeFileException() throws Exception {
        TreeFile treeFile = new TreeFile();
        exception.expect(UnsupportedTreeFileException.class);
        treeFile.addSearchTree(resourcesPath + "/wrong-type.tre", 0);
        treeFile.open("does/not/exist.iff"); //Have to actually try and open a file in the tre archive.
    }

    @Test
    public void shouldThrowUnsupportedTreeFileVersionException() throws Exception {
        TreeFile treeFile = new TreeFile();
        exception.expect(UnsupportedTreeFileVersionException.class);
        treeFile.addSearchTree(resourcesPath + "/wrong-version.tre", 0);
        treeFile.open("does/not/exist.iff");
    }

    @Test
    public void shouldThrowFileNotFoundException() throws Exception {
        TreeFile treeFile = new TreeFile();
        exception.expect(FileNotFoundException.class);
        treeFile.addSearchTree(resourcesPath + "/non-existing-file.tre", 0);
    }
}
