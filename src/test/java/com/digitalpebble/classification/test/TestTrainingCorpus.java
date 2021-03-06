/**
 * Copyright 2009 DigitalPebble Ltd
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.digitalpebble.classification.test;

import java.util.Iterator;

import com.digitalpebble.classification.Document;
import com.digitalpebble.classification.Field;
import com.digitalpebble.classification.RAMTrainingCorpus;

public class TestTrainingCorpus extends AbstractLearnerTest {

	public void testMultiField() throws Exception {
		Field[] fields = new Field[3];
		fields[0] = new Field("title", new String[] { "This", "is", "a",
				"title" });
		fields[1] = new Field("abstract", new String[] { "abstract" });
		fields[2] = new Field("content", new String[] { "This", "is", "the",
				"content", "this", "will", "have", "a", "large", "value" });
		Document doc = learner.createDocument(fields, "large");

		String[] simplecontent = new String[] { "This", "is", "the", "content",
				"this", "will", "have", "a", "small", "value" };
		Document doc2 = learner.createDocument(simplecontent, "small");

		// com.digitalpebble.classification.TrainingCorpus tc =
		// learner.getFileTrainingCorpus();
		com.digitalpebble.classification.TrainingCorpus tc = new RAMTrainingCorpus();
		tc.addDocument(doc);
		tc.addDocument(doc2);
		tc.close();

		int numDoc = 0;

		Iterator iter = tc.iterator();
		while (iter.hasNext()) {
			numDoc++;
			iter.next();
		}

		assertEquals(2, numDoc);

		tc = learner.getFileTrainingCorpus();
		tc.addDocument(doc);
		tc.addDocument(doc2);
		tc.close();

		numDoc = 0;

		iter = tc.iterator();
		while (iter.hasNext()) {
			numDoc++;
			iter.next();
		}

		assertEquals(2, numDoc);

		learner.learn(tc);

	}

}
