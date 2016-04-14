package org.hl7.fhir.dstu3.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import org.hl7.fhir.dstu3.model.ElementDefinition;
import org.hl7.fhir.dstu3.model.StructureDefinition;
import org.hl7.fhir.dstu3.utils.ShExGenerator.HTMLLinkPolicy;

public class ShExGenerator {

  public enum HTMLLinkPolicy {
    NONE, EXTERNAL, INTERNAL
  }

  /**
   * this makes interal metadata services avaialble to the generator - retrieving structure definitions, and value set expansion etc 
   */
  private IWorkerContext context;

  public ShExGenerator(IWorkerContext context) {
    super();
    this.context = context;
  }
  
  public String generate(HTMLLinkPolicy links, StructureDefinition structure) {
    List<StructureDefinition> list = new ArrayList<StructureDefinition>();
    list.add(structure);
    return generate(links, list);
  }
  
  public class SortById implements Comparator<StructureDefinition> {

    @Override
    public int compare(StructureDefinition arg0, StructureDefinition arg1) {
      return arg0.getId().compareTo(arg1.getId());
    }

  }

  /** 
   * this is called externally to generate a set of structures to a single ShEx file
   * generally, it will be called with a single structure, or a long list of structures (all of them)
   * 
   * @return the ShEx source for the provided structures
   */
  public String generate(HTMLLinkPolicy links, List<StructureDefinition> structures) {
    Collections.sort(structures, new SortById());

    StringBuilder b = new StringBuilder();
    b.append("PREFIX fhir: <http://hl7.org/fhir/>\r\n");
    Set<String> done = new HashSet<String>();
    
    for (StructureDefinition sd : structures) {
      b.append("<");
      b.append(sd.getId());
      b.append("> {\r\n");

      b.append("  a [fhir:");
      b.append(sd.getId());
      b.append("]");

      Queue<ElementDefinition> queue = new LinkedList<ElementDefinition>();
      
      for (ElementDefinition ed : sd.getSnapshot().getElement()) {
        if (ed.getPath().contains(".")) // ignore the first one
          genElement(b, sd, queue, ed, true);
      }
      b.append("\r\n}\r\n");
      
      while (!queue.isEmpty()) {
        ElementDefinition ed = queue.poll();
        if (done.contains(ed.getPath())) // watch for recursion
          break;
        done.add(ed.getPath());
        b.append("<");
        b.append(ed.getPath());
        b.append("> {\r\n");
        boolean hasProps = false;
        List<ElementDefinition> children = ProfileUtilities.getChildList(sd, ed);
        for (ElementDefinition child : children) {
          hasProps = genElement(b, sd, queue, child, hasProps);
        }

        genElement(b, sd, queue, ed, false);
        b.append("\r\n}\r\n");
      }
    }
    return b.toString();
    
  }

  private boolean genElement(StringBuilder b, StructureDefinition sd, Queue<ElementDefinition> queue, ElementDefinition ed, boolean hasProps) {
    String id = ed.hasBase() ? ed.getBase().getPath() : ed.getPath();
    
    List<ElementDefinition> children = ProfileUtilities.getChildList(sd, ed);
    // todo : check for content reference
    
    if (children.size() > 0) {
      // inline anonymous type
      queue.add(ed);
      if (hasProps)
        b.append(",\r\n");
      else
        hasProps = true;
      b.append("  fhir:");
      b.append(id);
      b.append(" @<fhir:");
      b.append(id);
      b.append(">");
      if ("*".equals(ed.getMax())) {
        if (ed.getMin() == 0)
          b.append("*");
        else
          b.append("+");
      } else if (ed.getMin() == 0) {
        b.append("?");
      }
    } else if (ed.getType().size() == 1) {
      // simple case, one type
      if (hasProps)
        b.append(",\r\n");
      else
        hasProps = true;
      b.append("  fhir:");
      b.append(id);
      b.append(" @<fhir:");
      b.append(ed.getType().get(0).getCode()); 
      // todo: check for profile(s)
      b.append(">");
      if ("*".equals(ed.getMax())) {
        if (ed.getMin() == 0)
          b.append("*");
        else
          b.append("+");
      } else if (ed.getMin() == 0) {
        b.append("?");
      }
    } else { 
      // multiple types
    }
    return hasProps;
  }
}
