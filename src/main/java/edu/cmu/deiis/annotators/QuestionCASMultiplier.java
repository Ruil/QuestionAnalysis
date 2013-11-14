/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package edu.cmu.deiis.annotators;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasMultiplier_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.AbstractCas;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

/**
 * An example CasMultiplier, which breaks large text documents into smaller
 * segments. The minimum size of the segments as determined by the "SegmentSize"
 * configuration parameter, but the break between segments will always occur at
 * the next newline character, so segments will not be exactly that size.
 */
public class QuestionCASMultiplier extends JCasMultiplier_ImplBase {

	private String questionText;
	private boolean mutex;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.uima.analysis_component.AnalysisComponent_ImplBase#initialize
	 * (org.apache.uima.UimaContext)
	 */
	public void initialize(UimaContext aContext)
			throws ResourceInitializationException {
		super.initialize(aContext);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see JCasMultiplier_ImplBase#process(JCas)
	 */
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		questionText = aJCas.getDocumentText();
		mutex = true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.uima.analysis_component.AnalysisComponent#hasNext()
	 */
	public boolean hasNext() throws AnalysisEngineProcessException {
		return mutex;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.uima.analysis_component.AnalysisComponent#next()
	 */
	public AbstractCas next() throws AnalysisEngineProcessException {
		mutex = false;
		JCas jcas = getEmptyJCas();
		try {
			jcas.setDocumentText(questionText);
			return jcas;
		} catch (Exception e) {
			jcas.release();
			throw new AnalysisEngineProcessException(e);
		}
	}

}