/**
 * Copyright (c) 2016, RTE (http://www.rte-france.com)
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.powsybl.iidm.xml;

import com.google.auto.service.AutoService;
import com.google.common.base.Joiner;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.io.ByteStreams;
import com.powsybl.commons.PowsyblException;
import com.powsybl.commons.config.PlatformConfig;
import com.powsybl.commons.datasource.DataSource;
import com.powsybl.commons.datasource.ReadOnlyDataSource;
import com.powsybl.iidm.ConversionParameters;
import com.powsybl.iidm.anonymizer.Anonymizer;
import com.powsybl.iidm.anonymizer.SimpleAnonymizer;
import com.powsybl.iidm.import_.ImportOptions;
import com.powsybl.iidm.import_.Importer;
import com.powsybl.iidm.network.Network;
import com.powsybl.iidm.parameters.Parameter;
import com.powsybl.iidm.parameters.ParameterDefaultValueConfig;
import com.powsybl.iidm.parameters.ParameterType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static com.powsybl.iidm.xml.IidmXmlConstants.IIDM_URI;
import static com.powsybl.iidm.xml.IidmXmlConstants.VERSION;

/**
 * @author Geoffroy Jamgotchian <geoffroy.jamgotchian at rte-france.com>
 */
@AutoService(Importer.class)
public class XMLImporter implements Importer {

    private static final Logger LOGGER = LoggerFactory.getLogger(XMLImporter.class);

    private static final String[] EXTENSIONS = {"xiidm", "iidm", "xml"};

    private static final Supplier<XMLInputFactory> XML_INPUT_FACTORY_SUPPLIER = Suppliers.memoize(XMLInputFactory::newInstance);

    public static final String THROW_EXCEPTION_IF_EXTENSION_NOT_FOUND = "iidm.import.xml.throw-exception-if-extension-not-found";

    public static final String IMPORT_FROM_BASE_AND_EXTENSIONS_FILES = "iidm.import.xml.import_from_base_and_extensions_files";

    public static final String IMPORT_FROM_BASE_AND_MULTIPLE_EXTENSION_FILES = "iidm.import.xml.import_from_base_and_multiple_extension_files";

    public static final String EXTENSIONS_LIST = "iidm.import.xml.extensions";



    private static final Parameter IMPORT_FROM_BASE_AND_EXTENSIONS_FILES_PARAMETER
            = new Parameter(IMPORT_FROM_BASE_AND_EXTENSIONS_FILES, ParameterType.BOOLEAN, "import from base and extensions files", Boolean.FALSE);

    private static final Parameter IMPORT_FROM_BASE_AND_MULTIPLE_EXTENSION_FILES_PARAMETER
            = new Parameter(IMPORT_FROM_BASE_AND_MULTIPLE_EXTENSION_FILES, ParameterType.BOOLEAN, "import from base and multiple extension files", Boolean.FALSE);

    private static final Parameter THROW_EXCEPTION_IF_EXTENSION_NOT_FOUND_PARAMETER
            = new Parameter(THROW_EXCEPTION_IF_EXTENSION_NOT_FOUND, ParameterType.BOOLEAN, "Throw exception if extension not found", Boolean.FALSE)
                    .addAdditionalNames("throwExceptionIfExtensionNotFound");

    private static final Parameter EXTENSIONS_LIST_PARAMETER
            = new Parameter(EXTENSIONS_LIST, ParameterType.STRING_LIST, "The list of extension files ", Collections.EMPTY_LIST);

    private final ParameterDefaultValueConfig defaultValueConfig;

    private static final String SUFFIX_MAPPING = "_mapping";

    public XMLImporter() {
        this(PlatformConfig.defaultConfig());
    }

    public XMLImporter(PlatformConfig platformConfig) {
        defaultValueConfig = new ParameterDefaultValueConfig(platformConfig);
    }

    @Override
    public String getFormat() {
        return "XIIDM";
    }

    @Override
    public List<Parameter> getParameters() {
        return Collections.singletonList(THROW_EXCEPTION_IF_EXTENSION_NOT_FOUND_PARAMETER);
    }

    @Override
    public String getComment() {
        return "IIDM XML v " + VERSION + " importer";
    }

    private String findExtension(ReadOnlyDataSource dataSource) throws IOException {
        for (String ext : EXTENSIONS) {
            if (dataSource.exists(null, ext)) {
                return ext;
            }
        }
        return null;
    }

    @Override
    public boolean exists(ReadOnlyDataSource dataSource) {
        try {
            String ext = findExtension(dataSource);
            return exists(dataSource, ext);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private boolean exists(ReadOnlyDataSource dataSource, String ext) throws IOException {
        try {
            if (ext != null) {
                try (InputStream is = dataSource.newInputStream(null, ext)) {
                    // check the first root element is network and namespace is IIDM
                    XMLStreamReader xmlsr = XML_INPUT_FACTORY_SUPPLIER.get().createXMLStreamReader(is);
                    try {
                        while (xmlsr.hasNext()) {
                            int eventType = xmlsr.next();
                            if (eventType == XMLEvent.START_ELEMENT) {
                                String name = xmlsr.getLocalName();
                                String ns = xmlsr.getNamespaceURI();
                                return NetworkXml.NETWORK_ROOT_ELEMENT_NAME.equals(name) && IIDM_URI.equals(ns);
                            }
                        }
                    } finally {
                        try {
                            xmlsr.close();
                        } catch (XMLStreamException e) {
                            LOGGER.error(e.toString(), e);
                        }
                    }
                }
            }
            return false;
        } catch (XMLStreamException e) {
            // not a valid xml file
            return false;
        }
    }

    @Override
    public void copy(ReadOnlyDataSource fromDataSource, DataSource toDataSource) {
        try {
            String ext = findExtension(fromDataSource);
            if (!exists(fromDataSource, ext)) {
                throw new PowsyblException("From data source is not importable");
            }
            // copy iidm file
            try (InputStream is = fromDataSource.newInputStream(null, ext);
                 OutputStream os = toDataSource.newOutputStream(null, ext, false)) {
                ByteStreams.copy(is, os);
            }
            // and also anonymization file if exists
            if (fromDataSource.exists(SUFFIX_MAPPING, "csv")) {
                try (InputStream is = fromDataSource.newInputStream(SUFFIX_MAPPING, "csv");
                     OutputStream os = toDataSource.newOutputStream(SUFFIX_MAPPING, "csv", false)) {
                    ByteStreams.copy(is, os);
                }
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public Network importData(ReadOnlyDataSource dataSource, Properties parameters) {
        return importData(dataSource, parameters, new ArrayList<String>() {
        });
    }

    public Network importData(ReadOnlyDataSource dataSource, Properties parameters, List<String> extensions) {
        Objects.requireNonNull(dataSource);
        Network network;

        ImportOptions options = new ImportOptions()
                .setThrowExceptionIfExtensionNotFound(ConversionParameters.readBooleanParameter(getFormat(), parameters, THROW_EXCEPTION_IF_EXTENSION_NOT_FOUND_PARAMETER, defaultValueConfig))
                .setImportFromBaseAndExtensionsFiles(ConversionParameters.readBooleanParameter(getFormat(), parameters, IMPORT_FROM_BASE_AND_EXTENSIONS_FILES_PARAMETER, defaultValueConfig))
                .setImportFromBaseAndMultipleExtensionFiles(ConversionParameters.readBooleanParameter(getFormat(), parameters, IMPORT_FROM_BASE_AND_MULTIPLE_EXTENSION_FILES_PARAMETER, defaultValueConfig))
                .setExtensions(ConversionParameters.readStringListParameter(getFormat(), parameters, EXTENSIONS_LIST_PARAMETER, defaultValueConfig));


        Anonymizer anonymizer = null;
        long startTime = System.currentTimeMillis();
        try {
            String ext = findExtension(dataSource);
            if (ext == null) {
                throw new PowsyblException("File " + dataSource.getBaseName()
                        + "." + Joiner.on("|").join(EXTENSIONS) + " not found");
            }

            if (dataSource.exists(SUFFIX_MAPPING, "csv")) {
                anonymizer = new SimpleAnonymizer();
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(dataSource.newInputStream(SUFFIX_MAPPING, "csv"), StandardCharsets.UTF_8))) {
                    anonymizer.read(reader);
                }
            }

            //Read the base file
            try (InputStream isb = dataSource.newInputStream(null, ext)) {
                network = NetworkXml.read(isb, options, anonymizer);
            }

            if (options.isImportFromBaseAndExtensionsFiles()) {
                // in this case we have to read all extensions from one single file
                String extension = findExtension(dataSource);
                if (extension == null) {
                    throw new PowsyblException("File " + dataSource.getBaseName()
                            + "." + String.join("|", EXTENSIONS) + " not found");
                }
                try (InputStream ise = dataSource.newInputStream(extensions.iterator().next() +  "." + extension)) {
                    network = NetworkXml.readExtensions(network, ise, options, anonymizer);
                }
            } else if (options.isImportFromBaseAndMultipleExtensionFiles()) {
                // here we'll read all extensions declared in the extensions set
                network = NetworkXml.readExtensions(network, dataSource, options, anonymizer, extensions, ext);
            }

            LOGGER.debug("XIIDM import done in {} ms", System.currentTimeMillis() - startTime);
        } catch (IOException e) {
            throw new PowsyblException(e);
        }
        return network;
    }
}

