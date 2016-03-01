/*
 * An XML document type.
 * Localname: linkingEventIdentifierValue
 * Namespace: http://www.loc.gov/premis/v3
 * Java type: gov.loc.premis.v3.LinkingEventIdentifierValueDocument
 *
 * Automatically generated - do not modify.
 */
package gov.loc.premis.v3;


/**
 * A document containing one linkingEventIdentifierValue(@http://www.loc.gov/premis/v3) element.
 *
 * This is a complex type.
 */
public interface LinkingEventIdentifierValueDocument extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(LinkingEventIdentifierValueDocument.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s7436A832873D7D1134D0870C60EDA074").resolveHandle("linkingeventidentifiervalue7f23doctype");
    
    /**
     * Gets the "linkingEventIdentifierValue" element
     */
    java.lang.String getLinkingEventIdentifierValue();
    
    /**
     * Gets (as xml) the "linkingEventIdentifierValue" element
     */
    org.apache.xmlbeans.XmlString xgetLinkingEventIdentifierValue();
    
    /**
     * Sets the "linkingEventIdentifierValue" element
     */
    void setLinkingEventIdentifierValue(java.lang.String linkingEventIdentifierValue);
    
    /**
     * Sets (as xml) the "linkingEventIdentifierValue" element
     */
    void xsetLinkingEventIdentifierValue(org.apache.xmlbeans.XmlString linkingEventIdentifierValue);
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static gov.loc.premis.v3.LinkingEventIdentifierValueDocument newInstance() {
          return (gov.loc.premis.v3.LinkingEventIdentifierValueDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static gov.loc.premis.v3.LinkingEventIdentifierValueDocument newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (gov.loc.premis.v3.LinkingEventIdentifierValueDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static gov.loc.premis.v3.LinkingEventIdentifierValueDocument parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (gov.loc.premis.v3.LinkingEventIdentifierValueDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static gov.loc.premis.v3.LinkingEventIdentifierValueDocument parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (gov.loc.premis.v3.LinkingEventIdentifierValueDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static gov.loc.premis.v3.LinkingEventIdentifierValueDocument parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (gov.loc.premis.v3.LinkingEventIdentifierValueDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static gov.loc.premis.v3.LinkingEventIdentifierValueDocument parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (gov.loc.premis.v3.LinkingEventIdentifierValueDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static gov.loc.premis.v3.LinkingEventIdentifierValueDocument parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (gov.loc.premis.v3.LinkingEventIdentifierValueDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static gov.loc.premis.v3.LinkingEventIdentifierValueDocument parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (gov.loc.premis.v3.LinkingEventIdentifierValueDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static gov.loc.premis.v3.LinkingEventIdentifierValueDocument parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (gov.loc.premis.v3.LinkingEventIdentifierValueDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static gov.loc.premis.v3.LinkingEventIdentifierValueDocument parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (gov.loc.premis.v3.LinkingEventIdentifierValueDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static gov.loc.premis.v3.LinkingEventIdentifierValueDocument parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (gov.loc.premis.v3.LinkingEventIdentifierValueDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static gov.loc.premis.v3.LinkingEventIdentifierValueDocument parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (gov.loc.premis.v3.LinkingEventIdentifierValueDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static gov.loc.premis.v3.LinkingEventIdentifierValueDocument parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (gov.loc.premis.v3.LinkingEventIdentifierValueDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static gov.loc.premis.v3.LinkingEventIdentifierValueDocument parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (gov.loc.premis.v3.LinkingEventIdentifierValueDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static gov.loc.premis.v3.LinkingEventIdentifierValueDocument parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (gov.loc.premis.v3.LinkingEventIdentifierValueDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static gov.loc.premis.v3.LinkingEventIdentifierValueDocument parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (gov.loc.premis.v3.LinkingEventIdentifierValueDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static gov.loc.premis.v3.LinkingEventIdentifierValueDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (gov.loc.premis.v3.LinkingEventIdentifierValueDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static gov.loc.premis.v3.LinkingEventIdentifierValueDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (gov.loc.premis.v3.LinkingEventIdentifierValueDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}