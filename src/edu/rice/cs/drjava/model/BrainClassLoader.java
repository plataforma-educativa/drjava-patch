/*BEGIN_COPYRIGHT_BLOCK
 *
 * This file is part of DrJava.  Download the current version of this project from http://www.drjava.org/
 * or http://sourceforge.net/projects/drjava/
 *
 * DrJava Open Source License
 * 
 * Copyright (C) 2001-2005 JavaPLT group at Rice University (javaplt@rice.edu).  All rights reserved.
 *
 * Developed by:   Java Programming Languages Team, Rice University, http://www.cs.rice.edu/~javaplt/
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
 * documentation files (the "Software"), to deal with the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and 
 * to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * 
 *     - Redistributions of source code must retain the above copyright notice, this list of conditions and the 
 *       following disclaimers.
 *     - Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the 
 *       following disclaimers in the documentation and/or other materials provided with the distribution.
 *     - Neither the names of DrJava, the JavaPLT, Rice University, nor the names of its contributors may be used to 
 *       endorse or promote products derived from this Software without specific prior written permission.
 *     - Products derived from this software may not be called "DrJava" nor use the term "DrJava" as part of their 
 *       names without prior written permission from the JavaPLT group.  For permission, write to javaplt@rice.edu.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO 
 * THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE 
 * CONTRIBUTORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS 
 * WITH THE SOFTWARE.
 * 
 *END_COPYRIGHT_BLOCK*/

package edu.rice.cs.drjava.model;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

import edu.rice.cs.drjava.model.repl.WrapperClassLoader;


public class BrainClassLoader extends ClassLoader {
  
  ClassLoader projectCL;
  ClassLoader buildCL;
  ClassLoader projectFilesCL;
  ClassLoader externalFilesCL;
  ClassLoader extraCL;
  ClassLoader systemCL;
  
  public BrainClassLoader(ClassLoader p, ClassLoader b, ClassLoader pf, ClassLoader ef, ClassLoader e) {
    projectCL = p;
    buildCL = b;
    projectFilesCL = pf;
    externalFilesCL = ef;
    extraCL = e;
    systemCL = new WrapperClassLoader(this.getClass().getClassLoader().getSystemClassLoader());
  }
  
  /** Handles getting the resource for loading a class. */
  public URL getResource(String name) {
    URL resource = projectCL.getResource(name);
    if (resource != null) return resource;
    
    resource = buildCL.getResource(name);
    if (resource != null) return resource;
    
    resource = projectFilesCL.getResource(name);
    if (resource != null) return resource;
    
    resource = externalFilesCL.getResource(name);
    if (resource != null) return resource;
    
    resource = extraCL.getResource(name);
    if (resource != null) return resource;

    resource = systemCL.getResource(name);
    if (resource != null) return resource;

    return resource;
  }
  
  /* Parche agregado para que también resuelva correctamente la delegación del método */
  public Enumeration<URL> getResources(String name) throws IOException {
    Enumeration<URL> resources = projectCL.getResources(name);
    if (resources != null && resources.hasMoreElements()) return resources;
  
    resources = buildCL.getResources(name);
    if (resources != null && resources.hasMoreElements()) return resources;
  
    resources = projectFilesCL.getResources(name);
    if (resources != null && resources.hasMoreElements()) return resources;
  
    resources = externalFilesCL.getResources(name);
    if (resources != null && resources.hasMoreElements()) return resources;
  
    resources = extraCL.getResources(name);
    if (resources != null && resources.hasMoreElements()) return resources;

    resources = systemCL.getResources(name);
    if (resources != null && resources.hasMoreElements()) return resources;

    return resources;
  }
}



