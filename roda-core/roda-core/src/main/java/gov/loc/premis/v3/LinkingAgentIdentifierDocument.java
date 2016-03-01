/*
 * An XML document type.
 * Localname: linkingAgentIdentifier
 * Namespace: http://www.loc.gov/premis/v3
 * Java type: gov.loc.premis.v3.LinkingAgentIdentifierDocument
 *
 * Automatically generated - do not modify.
 */
package gov.loc.premis.v3;


/**
 * A document containing one linkingAgentIdentifier(@http://www.loc.gov/premis/v3) element.
 *
 * This is a complex type.
 */
public interface LinkingAgentIdentifierDocument extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(LinkingAgentIdentifierDocument.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s7436A832873D7D1134D0870C60EDA074").resolveHandle("linkingagentidentifier85e1doctype");
    
    /**
     * Gets the "linkingAgentIdentifier" element
     */
    gov.loc.premis.v3.LinkingAgentIdentifierComplexType getLinkingAgentIdentifier();
    
    /**
     * Sets the "linkingAgentIdentifier" element
     */
    void setLinkingAgentIdentifier(gov.loc.premis.v3.LinkingAgentIdentifierComplexType linkingAgentIdentifier);
    
    /**
     * Appends and returns a new empty "linkingAgentIdentifier" element
     */
    gov.loc.premis.v3.LinkingAgentIdentifierComplexType addNewLinkingAgentIdentifier();
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static gov.loc.premis.v3.LinkingAgentIdentifierDocument newInstance() {
          return (gov.loc.premis.v3.LinkingAgentIdentifierDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static gov.loc.premis.v3.LinkingAgentIdentifierDocument newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (gov.loc.premis.v3.LinkingAgentIdentifierDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static gov.loc.premis.v3.LinkingAgentIdentifierDocument parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (gov.loc.premis.v3.LinkingAgentIdentifierDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static gov.loc.premis.v3.LinkingAgentIdentifierDocument parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (gov.loc.premis.v3.LinkingAgentIdentifierDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static gov.loc.premis.v3.LinkingAgentIdentifierDocument parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (gov.loc.premis.v3.LinkingAgentIdentifierDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static gov.loc.premis.v3.LinkingAgentIdentifierDocument parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (gov.loc.premis.v3.LinkingAgentIdentifierDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static gov.loc.premis.v3.LinkingAgentIdentifierDocument parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (gov.loc.premis.v3.LinkingAgentIdentifierDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static gov.loc.premis.v3.LinkingAgentIdentifierDocument parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (gov.loc.premis.v3.LinkingAgentIdentifierDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static gov.loc.premis.v3.LinkingAgentIdentifierDocument parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (gov.loc.premis.v3.LinkingAgentIdentifierDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static gov.loc.premis.v3.LinkingAgentIdentifierDocument parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (gov.loc.premis.v3.LinkingAgentIdentifierDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static gov.loc.premis.v3.LinkingAgentIdentifierDocument parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (gov.loc.premis.v3.LinkingAgentIdentifierDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static gov.loc.premis.v3.LinkingAgentIdentifierDocument parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (gov.loc.premis.v3.LinkingAgentIdentifierDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static gov.loc.premis.v3.LinkingAgentIdentifierDocument parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (gov.loc.premis.v3.LinkingAgentIdentifierDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static gov.loc.premis.v3.LinkingAgentIdentifierDocument parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (gov.loc.premis.v3.LinkingAgentIdentifierDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static gov.loc.premis.v3.LinkingAgentIdentifierDocument parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (gov.loc.premis.v3.LinkingAgentIdentifierDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static gov.loc.premis.v3.LinkingAgentIdentifierDocument parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (gov.loc.premis.v3.LinkingAgentIdentifierDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static gov.loc.premis.v3.LinkingAgentIdentifierDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (gov.loc.premis.v3.LinkingAgentIdentifierDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static gov.loc.premis.v3.LinkingAgentIdentifierDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (gov.loc.premis.v3.LinkingAgentIdentifierDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}