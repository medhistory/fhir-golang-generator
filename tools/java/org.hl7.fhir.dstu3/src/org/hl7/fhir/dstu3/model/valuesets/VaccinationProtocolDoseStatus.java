package org.hl7.fhir.dstu3.model.valuesets;

/*
  Copyright (c) 2011+, HL7, Inc.
  All rights reserved.
  
  Redistribution and use in source and binary forms, with or without modification, 
  are permitted provided that the following conditions are met:
  
   * Redistributions of source code must retain the above copyright notice, this 
     list of conditions and the following disclaimer.
   * Redistributions in binary form must reproduce the above copyright notice, 
     this list of conditions and the following disclaimer in the documentation 
     and/or other materials provided with the distribution.
   * Neither the name of HL7 nor the names of its contributors may be used to 
     endorse or promote products derived from this software without specific 
     prior written permission.
  
  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND 
  ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED 
  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. 
  IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, 
  INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT 
  NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR 
  PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, 
  WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
  ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE 
  POSSIBILITY OF SUCH DAMAGE.
  
*/

// Generated on Thu, Feb 25, 2016 10:54+1100 for FHIR v1.3.0


import org.hl7.fhir.dstu3.exceptions.FHIRException;

public enum VaccinationProtocolDoseStatus {

        /**
         * null
         */
        COUNT, 
        /**
         * null
         */
        NOCOUNT, 
        /**
         * added to help the parsers
         */
        NULL;
        public static VaccinationProtocolDoseStatus fromCode(String codeString) throws FHIRException {
            if (codeString == null || "".equals(codeString))
                return null;
        if ("count".equals(codeString))
          return COUNT;
        if ("nocount".equals(codeString))
          return NOCOUNT;
        throw new FHIRException("Unknown VaccinationProtocolDoseStatus code '"+codeString+"'");
        }
        public String toCode() {
          switch (this) {
            case COUNT: return "count";
            case NOCOUNT: return "nocount";
            default: return "?";
          }
        }
        public String getSystem() {
          return "http://hl7.org/fhir/vaccination-protocol-dose-status";
        }
        public String getDefinition() {
          switch (this) {
            case COUNT: return "";
            case NOCOUNT: return "";
            default: return "?";
          }
        }
        public String getDisplay() {
          switch (this) {
            case COUNT: return "Counts";
            case NOCOUNT: return "Does not Count";
            default: return "?";
          }
    }


}

