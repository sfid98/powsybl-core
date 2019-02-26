/**
 * Copyright (c) 2019, RTE (http://www.rte-france.com)
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.powsybl.iidm;

import com.google.common.collect.Sets;

import java.util.Optional;
import java.util.Set;

/**
 * @author Chamseddine BENHAMED <chamseddine.benhamed at rte-france.com>
 */
public abstract class AbstractOptions<T> {
    protected Set<String> extensions = Sets.newHashSet("ALL");

    protected IidmImportExportMode mode = IidmImportExportMode.UNIQUE_FILE;

    public AbstractOptions() {
    }

    public abstract T setExtensions(Set<String> extensions);

    public abstract T setMode(IidmImportExportMode mode);

    public abstract T addExtension(String extension);

    public Set<String> getExtensions() {
        return this.extensions;
    }

    public boolean withNoExtension() {
        return extensions.isEmpty();
    }

    public  boolean withAllExtensions() {
        return extensions.size() == 1 && extensions.iterator().next().equals("ALL");
    }

    public  boolean withExtension(String extensionName) {
        return withAllExtensions() || extensions.contains(extensionName);
    }

    public IidmImportExportMode getMode() {
        return this.mode;
    }

}