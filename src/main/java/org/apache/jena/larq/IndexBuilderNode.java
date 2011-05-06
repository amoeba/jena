/*
 * (c) Copyright 2006, 2007, 2008, 2009 Hewlett-Packard Development Company, LP
 * (c) Copyright 2010 Talis Systems Ltd.
 * All rights reserved.
 * [See end of file]
 */

package org.apache.jena.larq;

import java.io.File;
import java.io.IOException;
import java.io.Reader;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.search.Query;

import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.rdf.model.RDFNode;

/** Helper class for index creation from external content.
 *  
 *  Once completed, the index builder should be closed for writing,
 *  then the getIndex() called.
 *  
 *  To update the index once closed, the application should create a new index builder.
 *  Any index readers (e.g. IndexLARQ objects)
 *  need to be recreated and registered. 
 */

public class IndexBuilderNode extends IndexBuilderBase
{
    /** Create an in-memory index */
    public IndexBuilderNode() { super() ; }
    
    /** Manage a Lucene index that has already been created */
    public IndexBuilderNode(IndexWriter existingWriter) { super(existingWriter) ; }
    
    /** Create an on-disk index */
    public IndexBuilderNode(File fileDir) { super(fileDir) ; }
    
    /** Create an on-disk index */
    public IndexBuilderNode(String fileDir) { super(fileDir) ; }

    public void index(RDFNode rdfNode, String indexStr)
    {
        try {
        	if ( avoidDuplicates() ) unindex(rdfNode, indexStr);
            Document doc = new Document() ;
            LARQ.store(doc, rdfNode.asNode()) ;
            LARQ.index(doc, rdfNode.asNode(), indexStr) ;
            getIndexWriter().addDocument(doc) ;
        } catch (IOException ex)
        { throw new ARQLuceneException("index", ex) ; }
    }
   
    public void index(RDFNode rdfNode, Reader indexStream)
    {
        try {
        	if ( avoidDuplicates() ) unindex(rdfNode, indexStream);
            Document doc = new Document() ;
            LARQ.store(doc, rdfNode.asNode()) ;
            LARQ.index(doc, rdfNode.asNode(), indexStream) ;
            getIndexWriter().addDocument(doc) ;
        } catch (IOException ex)
        { throw new ARQLuceneException("index", ex) ; }
    }
    
    public void index(Node node, String indexStr)
    {
        try {
        	if ( avoidDuplicates() ) unindex(node, indexStr);
            Document doc = new Document() ;
            LARQ.store(doc, node) ;
            LARQ.index(doc, node, indexStr) ;
            getIndexWriter().addDocument(doc) ;
        } catch (IOException ex)
        { throw new ARQLuceneException("index", ex) ; }
    }
   
    public void index(Node node, Reader indexStream)
    {
        try {
        	if ( avoidDuplicates() ) unindex(node, indexStream);
            Document doc = new Document() ;
            LARQ.store(doc, node) ;
            LARQ.index(doc, node, indexStream) ;
            getIndexWriter().addDocument(doc) ;
        } catch (IOException ex)
        { throw new ARQLuceneException("index", ex) ; }
    }
    
    public void unindex(RDFNode rdfNode, String indexStr)
    {
        try {
            Query query = LARQ.unindex(rdfNode.asNode(), indexStr);
            getIndexWriter().deleteDocuments(query);
        } catch (Exception ex)
        { throw new ARQLuceneException("unindex", ex) ; } 
    }
    
    public void unindex(Node node, String indexStr)
    {
        try {
            Query query = LARQ.unindex(node, indexStr);
            getIndexWriter().deleteDocuments(query);
        } catch (Exception ex)
        { throw new ARQLuceneException("unindex", ex) ; } 
    }

    public void unindex(RDFNode rdfNode, Reader inputStream)
    {
    	unindex(rdfNode.asNode(), inputStream) ;
    }
    
    public void unindex(Node node, Reader inputStream)
    {
        try {
            Query query = LARQ.unindex(node, inputStream);
            getIndexWriter().deleteDocuments(query);
        } catch (Exception ex)
        { throw new ARQLuceneException("unindex", ex) ; } 
    }
    
}

/*
 * (c) Copyright 2006, 2007, 2008, 2009 Hewlett-Packard Development Company, LP
 * (c) Copyright 2010 Talis Systems Ltd.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. The name of the author may not be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */