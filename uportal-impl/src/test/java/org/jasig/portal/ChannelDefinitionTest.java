/**
 * Copyright (c) 2000-2009, Jasig, Inc.
 * See license distributed with this file and available online at
 * https://www.ja-sig.org/svn/jasig-parent/tags/rel-10/license-header.txt
 */
package org.jasig.portal;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import junit.framework.TestCase;

import org.jasig.portal.channel.IChannelDefinition;
import org.jasig.portal.channel.dao.jpa.ChannelDefinitionImpl;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Testcase for ChannelDefinition class.
 * @version $Revision$ $Date$
 */
public class ChannelDefinitionTest extends TestCase {

    /**
     * Test that ChannelDefinition identifies CPortletAdaptor as a portlet but
     * does not identify CGenericXSLT as a portlet.
     */
    public void testIsPortlet() {
        IChannelDefinition cd = new ChannelDefinitionImpl(72);
        
        cd.setJavaClass("org.jasig.portal.channels.CGenericXSLT");
        assertFalse(cd.isPortlet());
        
        cd.setJavaClass("org.jasig.portal.channels.portlet.CSpringPortletAdaptor");
        assertTrue(cd.isPortlet());
        
    }
    
    /**
     * Test Element representation of channel to see that its node name is 
     * 'channel' and that it has the proper owning document and that the channel
     * element has the correct identifer such that it can be gotten by ID.
     * @throws ParserConfigurationException
     */
    public void testGetDocument() throws ParserConfigurationException {
        IChannelDefinition cd = new ChannelDefinitionImpl(73);
        
        cd.setDescription("A test channel description.");
        cd.setEditable(false);
        cd.setFName("test_fname");
        cd.setHasAbout(true);
        cd.setHasHelp(false);
        cd.setIsSecure(false);
        cd.setJavaClass("org.jasig.portal.channels.CGenericXSLT");
        cd.setName("testName");
        cd.setTimeout(500);
        cd.setTitle("testTitle");
        cd.setTypeId(12);
        
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        
        Element channelElement = cd.getDocument(doc, "testId");
        
        assertEquals("channel", channelElement.getNodeName());
        assertSame(doc, channelElement.getOwnerDocument());
        
        doc.appendChild(channelElement);
        
        assertSame(channelElement, doc.getElementById("testId"));
        
        assertEquals("A test channel description.", channelElement.getAttribute("description"));
        assertEquals("false", channelElement.getAttribute("editable"));
        assertEquals("test_fname", channelElement.getAttribute("fname"));
        assertEquals("true", channelElement.getAttribute("hasAbout"));
        assertEquals("false", channelElement.getAttribute("hasHelp"));
        assertEquals("false", channelElement.getAttribute("secure"));
        assertEquals("org.jasig.portal.channels.CGenericXSLT", channelElement.getAttribute("class"));
        assertEquals("testName", channelElement.getAttribute("name"));
        assertEquals("500", channelElement.getAttribute("timeout"));
        assertEquals("testTitle", channelElement.getAttribute("title"));
        assertEquals("12", channelElement.getAttribute("typeID"));

        
    }

}