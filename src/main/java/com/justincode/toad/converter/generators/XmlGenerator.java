package com.justincode.toad.converter.generators;

import com.justincode.toad.converter.dao.Attribute;
import com.justincode.toad.converter.dao.Image;
import com.justincode.toad.converter.dao.Product;
import com.justincode.toad.converter.dao.Variant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;
import java.io.StringWriter;
import java.util.List;

@Slf4j
@Service
public class XmlGenerator {
    public String generateXmlFile(List<Product> productList) {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            // root element
            Element rootElement = doc.createElement("root");
            doc.appendChild(rootElement);
            Element productsElement = doc.createElement("products");
            rootElement.appendChild(productsElement);
            for (Product product : productList) {
                productsElement.appendChild(createProductElement(doc, product));
            }

            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(doc), new StreamResult(writer));
            return writer.getBuffer().toString();
        } catch (ParserConfigurationException e) {
            log.error("Failed to instantiate newDocumentBuilder {}, Stack trace:{}", e.getMessage(), e.getStackTrace());
        } catch (TransformerConfigurationException e) {
            log.error("Failed to configure TransformerFactory {}, Stack trace:{}", e.getMessage(), e.getStackTrace());
        } catch (TransformerException e) {
            log.error("Failed to Transform {}, Stack trace:{}", e.getMessage(), e.getStackTrace());
        }
        return "Error creating xml file";
    }

    private Element createProductElement(Document doc, Product product) {
        Element productElement = doc.createElement("product");

        if (product.getUrl().isPresent()) {
            addProductParameter(doc, productElement, "url", product.getUrl().get());
        }

        addProductParameter(doc, productElement, "id", product.getId().toString());

        if (product.getBarcode().isPresent()) {
            addProductParameter(doc, productElement, "barcode", product.getBarcode().get().toString());
        }

        addProductParameter(doc, productElement, "category", product.getCategory());
        addProductParameter(doc, productElement, "title", product.getTitle());
        addProductParameter(doc, productElement, "description", product.getDescription());
        addProductParameter(doc, productElement, "price", String.valueOf(product.getPrice()));

        if (product.getOldPrice().isPresent()) {
            addProductParameter(doc, productElement, "price_old", String.valueOf(product.getOldPrice().get()));
        }

        if (product.getPrimeCost().isPresent()) {
            addProductParameter(doc, productElement, "prime_costs", String.valueOf(product.getPrimeCost().get()));
        }

        addProductParameter(doc, productElement, "quantity", String.valueOf(product.getQuantity()));

        if (product.getWarranty().isPresent()) {
            addProductParameter(doc, productElement, "warranty", product.getWarranty().get());
        }

        if (product.getDeliveryDate().isPresent()) {
            addProductParameter(doc, productElement, "delivery_date", product.getDeliveryDate().get());
        }

        if (product.getDeliveryText().isPresent()) {
            addProductParameter(doc, productElement, "delivery_text", product.getDeliveryText().get());
        }

        addProductParameter(doc, productElement, "manufacturer", String.valueOf(product.getManufacturer()));

        addProductImages(doc, productElement, product.getImages());

        if (product.getAttributes().isPresent()) {
            addAttributes(doc, productElement, product.getAttributes().get());
        }

        if (product.getVariants().isPresent()) {
            addVariants(doc, productElement, product.getVariants().get());
        }

        return productElement;
    }

    private void addVariants(Document doc, Element productElement, List<Variant> variants) {
        Element variantsElement = doc.createElement("variants");
        for (Variant variant : variants) {
            Element variantElement = doc.createElement("variant");
            Attr title = doc.createAttribute("title");
            title.setValue(variant.getTitle());
            variantElement.setAttributeNode(title);
            variant.getOptions().forEach(option -> {
                Element optionElement = doc.createElement("title");
                optionElement.appendChild(doc.createTextNode(option));
                variantElement.appendChild(optionElement);
            });
            variantsElement.appendChild(variantElement);
        }
        productElement.appendChild(variantsElement);
    }

    private void addAttributes(Document doc, Element productElement, List<Attribute> attributes) {
        Element attributesElement = doc.createElement("attributes");
        for (Attribute attribute : attributes) {
            Element attributeElement = doc.createElement("attribute");
            Attr title = doc.createAttribute("title");
            title.setValue(attribute.getTitle());
            attributeElement.setAttributeNode(title);
            attributeElement.appendChild(doc.createTextNode(attribute.getValue()));
            attributesElement.appendChild(attributeElement);
        }
        productElement.appendChild(attributesElement);
    }

    private void addProductImages(Document doc, Element productElement, List<Image> images) {
        Element imagesElement = doc.createElement("images");
        for (Image image : images) {
            Element imageElement = doc.createElement("image");
            imageElement.appendChild(doc.createTextNode(image.getUrl()));
            imagesElement.appendChild(imageElement);
        }
        productElement.appendChild(imagesElement);
    }

    private void addProductParameter(Document doc, Element productElement, String newElementName, String newElementValue) {
        Element newElement = doc.createElement(newElementName);
        newElement.appendChild(doc.createTextNode(newElementValue));
        productElement.appendChild(newElement);
    }


}
