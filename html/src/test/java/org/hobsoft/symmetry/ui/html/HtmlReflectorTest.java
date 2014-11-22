/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.hobsoft.symmetry.ui.html;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Tests {@code HtmlReflector}.
 */
public class HtmlReflectorTest
{
	// ----------------------------------------------------------------------------------------------------------------
	// tests
	// ----------------------------------------------------------------------------------------------------------------

	@Test
	public void constructorSetsComponentVisitor()
	{
		HtmlReflector reflector = new HtmlReflector();
		
		assertThat(reflector.getComponentVisitor(), is(instanceOf(HtmlComponentVisitor.class)));
	}
	
	@Test
	public void constructorSetsContentType()
	{
		HtmlReflector reflector = new HtmlReflector();
		
		assertThat(reflector.getContentType(), is("text/html"));
	}
}