/*
 * This file is part of ModpackInfo, licensed under the MIT License (MIT).
 *
 * Copyright (c) OreCruncher
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package org.blockartistry.mod.ModpackInfo.Xml;

import java.io.Reader;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;

import org.blockartistry.mod.ModpackInfo.PackInfo;
import org.blockartistry.mod.ModpackInfo.attributes.AttributeProvider;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.ModMetadata;

/**
 * 
 * Set of helper methods to assist in handling the internal Xml document
 * containing information about the modpack.
 *
 */
public class XmlHelpers {

	/**
	 * Traverses Forge mod information collecting the data into an Xml Document
	 * structure.
	 * 
	 * @param info
	 *            Information about the modpack to fold into the document
	 * @param mods
	 *            List of mods to include in the output
	 * @return An Xml document suitable for further processing
	 */
	public static Document toXml(PackInfo info, List<ModContainer> mods) {

		Document doc = null;

		try {

			// Get an Xml document to put the data into
			doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
					.newDocument();

			// Add our root node to the document
			Element root = doc.createElement("ModpackInfo");
			doc.appendChild(root);

			// Add our pack info
			addPackInfo(doc, root, info);

			// Create our list of mods
			Element modList = doc.createElement("modlist");
			root.appendChild(modList);

			// Traverse the mod list adding information to the document
			for (ModContainer mod : mods)
				addModInfo(doc, modList, mod);

			// Add environment information
			addEnvironmentInfo(doc, root);

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			doc = null;
		}

		return doc;

	}

	/**
	 * Saves and Xml document to the specified output, performing the necessary
	 * translation along the way.
	 * 
	 * @param doc
	 *            Xml document to transform
	 * @param xsl
	 *            Xsl sheet that describes the transformation
	 * @param provider
	 *            AttributeProvider that is used to decorate the output with
	 *            format attributes
	 * @param output
	 *            Result target of the transformation
	 * @throws TransformerException
	 */
	public static void saveTo(Document doc, Reader xsl,
			AttributeProvider provider, Result output)
			throws TransformerException {
		TransformerFactory factory = TransformerFactory.newInstance();
		Templates template = factory.newTemplates(new StreamSource(xsl));
		Transformer xformer = template.newTransformer();
		Source source = new DOMSource(doc);

		// Transform!
		provider.applyAttributeCodes(xformer);
		xformer.transform(source, output);
	}

	protected static String getOSVersionString() {
		return String.format("%s (%s)", System.getProperty("os.name"),
				System.getProperty("os.arch"));
	}

	protected static String getJavaVersionString() {
		return String.format("%s %s (%s)", System.getProperty("java.vendor"),
				System.getProperty("java.version"),
				System.getProperty("java.vendor.url"));
	}

	protected static void addProperty(Document doc, Element parent,
			String name, String value) {

		Element e = doc.createElement(name);
		Text tn = doc.createTextNode(value);
		e.appendChild(tn);
		parent.appendChild(e);
	}

	protected static void addPackInfo(Document doc, Element parent,
			PackInfo info) {

		Element pack = doc.createElement("Pack");

		if (info.hasValidName())
			addProperty(doc, pack, "name", info.getName());

		if (info.hasValidVersion())
			addProperty(doc, pack, "version", info.getVersion());

		if (info.hasValidDescription())
			addProperty(doc, pack, "description", info.getDescription());

		if (info.hasValidCredits())
			addProperty(doc, pack, "credits", info.getCredits());

		if (info.hasValidAuthors())
			addProperty(doc, pack, "authors", info.getAuthors());

		if (info.hasValidWebsite())
			addProperty(doc, pack, "website", info.getWebsite());

		parent.appendChild(pack);
	}

	protected static void addModInfo(Document doc, Element parent,
			ModContainer mod) {

		Element modInfo = doc.createElement("Mod");
		modInfo.setAttribute("name", mod.getName());
		modInfo.setAttribute("id", mod.getModId());
		modInfo.setAttribute("version", mod.getDisplayVersion());

		ModMetadata meta = mod.getMetadata();

		if (meta != null) {

			if (meta.description != null && !meta.description.isEmpty()) {
				addProperty(doc, modInfo, "description", meta.description);
			}

			if (meta.credits != null && !meta.credits.isEmpty()) {
				addProperty(doc, modInfo, "credits", meta.credits);
			}

			if (meta.authorList != null && !meta.authorList.isEmpty()) {
				addProperty(doc, modInfo, "authors",
						String.join(", ", meta.authorList));
			}

			addProperty(doc, modInfo, "url",
					meta.url.isEmpty() ? "Not supplied" : meta.url);
		}

		parent.appendChild(modInfo);
	}

	protected static void addEnvironmentInfo(Document doc, Element parent) {

		Element env = doc.createElement("Environment");
		addProperty(doc, env, "os", getOSVersionString());
		addProperty(doc, env, "java", getJavaVersionString());
		parent.appendChild(env);
	}
}
