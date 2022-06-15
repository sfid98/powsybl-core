package com.powsybl.iidm.xml;

import com.powsybl.commons.AbstractConverterTest;
import com.powsybl.iidm.export.ExportOptions;
import com.powsybl.iidm.network.Network;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class EncodingTest {

    @Test
    public void testEncodingISO88591ToISO88591() throws IOException {
        Network network = NetworkXml.read(getClass().getResourceAsStream("/encoding/network.xml"));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ExportOptions options = new ExportOptions();
        options.setCharset(StandardCharsets.ISO_8859_1);
        NetworkXml.write(network, options, baos);
        InputStream is = new ByteArrayInputStream(baos.toByteArray());
        AbstractConverterTest.compareXml(getClass().getResourceAsStream("/encoding/network.xml"), is);
    }

    @Test
    public void testEncodingISO88591ToUTF8() throws IOException {
        Network network = NetworkXml.read(getClass().getResourceAsStream("/encoding/network.xml"));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ExportOptions options = new ExportOptions();
        options.setCharset(StandardCharsets.UTF_8);
        NetworkXml.write(network, options, baos);
        InputStream is = new ByteArrayInputStream(baos.toByteArray());
        AbstractConverterTest.compareXml(getClass().getResourceAsStream("/encoding/network.xml"), is);
    }
}
