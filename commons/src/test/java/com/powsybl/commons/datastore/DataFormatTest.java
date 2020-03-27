/**
 * Copyright (c) 2020, RTE (http://www.rte-france.com)
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.powsybl.commons.datastore;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Properties;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;

/**
 * @author Giovanni Ferrari <giovanni.ferrari at techrain.eu>
 */
public class DataFormatTest {

    private FileSystem fileSystem;

    protected Path testDir1;

    protected Path testDir2;

    @Before
    public void setUp() throws Exception {
        fileSystem = Jimfs.newFileSystem(Configuration.unix());

        testDir1 = fileSystem.getPath("/store1");
        Files.createDirectories(testDir1);
        testDir2 = fileSystem.getPath("/store2");
        Files.createDirectories(testDir2);
    }

    @After
    public void tearDown() throws Exception {
        fileSystem.close();
    }

    @Test
    public void testDataFormat() {

        DataStore ds = new PathDataStore(Paths.get(testDir1.toUri()));
        DataFormat df = new DummyDataFormat();
        DataResolver dr = df.getDataResolver(new Properties());
        String mainFileName = "dummy.txt";

        try {
            Optional<DataPackage> dp = dr.resolve(ds, mainFileName);
            assertFalse(dp.isPresent());

            ds.newOutputStream(mainFileName);

            dp = dr.resolve(ds, mainFileName);
            assertTrue(dp.isPresent());

            DataPackage pack = dp.get();

            Optional<DataEntry> main = pack.getEntry(mainFileName);
            assertTrue(main.isPresent());
            assertEquals(mainFileName, main.get().getName());

            Optional<DataEntry> de = pack.getEntries().stream().filter(e -> e.getName().equals(mainFileName)).findFirst();
            assertTrue(de.isPresent());

            DataStore ds2 = new PathDataStore(Paths.get(testDir2.toUri()));
            pack.copyTo(ds2);

            Optional<DataPackage> dp2 = dr.resolve(ds2, mainFileName);

            assertTrue(dr.validate(dp2.get()));

        } catch (IOException | NonUniqueResultException e) {
            fail();
        }
    }

}
