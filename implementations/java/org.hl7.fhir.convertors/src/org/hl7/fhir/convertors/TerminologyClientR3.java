package org.hl7.fhir.convertors;

import java.net.URISyntaxException;
import java.util.Map;

import org.hl7.fhir.dstu3.utils.client.FHIRToolingClient;
import org.hl7.fhir.exceptions.FHIRException;
import org.hl7.fhir.r4.context.HTMLClientLogger;
import org.hl7.fhir.r4.model.CapabilityStatement;
import org.hl7.fhir.r4.model.Parameters;
import org.hl7.fhir.r4.model.TerminologyCapabilities;
import org.hl7.fhir.r4.model.ValueSet;
import org.hl7.fhir.r4.terminologies.TerminologyClient;

public class TerminologyClientR3 implements TerminologyClient {

  private FHIRToolingClient client; // todo: use the R2 client
  
  public TerminologyClientR3(String address) throws URISyntaxException {
    client = new FHIRToolingClient(address);
  }

  @Override
  public TerminologyCapabilities getTerminologyCapabilities() throws FHIRException {
    return (TerminologyCapabilities) VersionConvertor_30_40.convertTerminologyCapabilities(client.getTerminologyCapabilities(), false);
  }

  @Override
  public String getAddress() {
    return client.getAddress();
  }

  @Override
  public ValueSet expandValueset(ValueSet vs, Parameters p, Map<String, String> params) throws FHIRException {
    org.hl7.fhir.dstu3.model.ValueSet vs2 = (org.hl7.fhir.dstu3.model.ValueSet) VersionConvertor_30_40.convertResource(vs, false);
    org.hl7.fhir.dstu3.model.Parameters p2 = (org.hl7.fhir.dstu3.model.Parameters) VersionConvertor_30_40.convertResource(p, false);
    vs2 = client.expandValueset(vs2, null, params); // todo: second parameter
    return (ValueSet) VersionConvertor_30_40.convertResource(vs2, false);
  }

  @Override
  public Parameters validateCS(Parameters pin) throws FHIRException {
    org.hl7.fhir.dstu3.model.Parameters p2 = (org.hl7.fhir.dstu3.model.Parameters) VersionConvertor_30_40.convertResource(pin, false);
    p2 = client.operateType(org.hl7.fhir.dstu3.model.CodeSystem.class, "validate-code", p2);
    return (Parameters) VersionConvertor_30_40.convertResource(p2, false);
  }

  @Override
  public Parameters validateVS(Parameters pin) throws FHIRException {
    org.hl7.fhir.dstu3.model.Parameters p2 = (org.hl7.fhir.dstu3.model.Parameters) VersionConvertor_30_40.convertResource(pin, false);
    p2 = client.operateType(org.hl7.fhir.dstu3.model.ValueSet.class, "validate-code", p2);
    return (Parameters) VersionConvertor_30_40.convertResource(p2, false);
  }

  @Override
  public void setTimeout(int i) {
    // ignored in this version - need to roll R4 internal changes back to R2 if desired
  }

  @Override
  public void setLogger(HTMLClientLogger txLog) {
    // ignored in this version - need to roll R4 internal changes back to R2 if desired
  }

  @Override
  public CapabilityStatement getCapabilitiesStatementQuick() throws FHIRException {
    return (CapabilityStatement) VersionConvertor_30_40.convertResource(client.getCapabilitiesStatementQuick(), false);
  }

  @Override
  public Parameters lookupCode(Map<String, String> params) throws FHIRException {
    return (Parameters) VersionConvertor_30_40.convertResource(client.lookupCode(params), false);
  }
  
}
