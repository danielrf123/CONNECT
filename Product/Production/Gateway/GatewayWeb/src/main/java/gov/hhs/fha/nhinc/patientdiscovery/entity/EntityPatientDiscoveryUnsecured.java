package gov.hhs.fha.nhinc.patientdiscovery.entity;

import javax.jws.WebService;
import javax.xml.ws.BindingType;
import org.hl7.v3.RespondingGatewayPRPAIN201305UV02RequestType;
import org.hl7.v3.RespondingGatewayPRPAIN201306UV02ResponseType;

/**
 *
 * @author Neil Webb
 */
@WebService(serviceName = "EntityPatientDiscovery", portName = "EntityPatientDiscoveryPortSoap", endpointInterface = "gov.hhs.fha.nhinc.entitypatientdiscovery.EntityPatientDiscoveryPortType", targetNamespace = "urn:gov:hhs:fha:nhinc:entitypatientdiscovery", wsdlLocation = "WEB-INF/wsdl/EntityPatientDiscoveryUnsecured/EntityPatientDiscovery.wsdl")
@BindingType(value = "http://java.sun.com/xml/ns/jaxws/2003/05/soap/bindings/HTTP/")
public class EntityPatientDiscoveryUnsecured
{
    protected EntityPatientDiscoveryUnsecuredImpl getEntityPatientDiscoveryUnsecuredImpl()
    {
        return new EntityPatientDiscoveryUnsecuredImpl();
    }

    public RespondingGatewayPRPAIN201306UV02ResponseType respondingGatewayPRPAIN201305UV02(RespondingGatewayPRPAIN201305UV02RequestType respondingGatewayPRPAIN201305UV02Request)
    {
        RespondingGatewayPRPAIN201306UV02ResponseType response = null;

        EntityPatientDiscoveryUnsecuredImpl impl = getEntityPatientDiscoveryUnsecuredImpl();
        if(impl != null)
        {
            response = impl.respondingGatewayPRPAIN201305UV02(respondingGatewayPRPAIN201305UV02Request);
        }

        return response;
    }

}
