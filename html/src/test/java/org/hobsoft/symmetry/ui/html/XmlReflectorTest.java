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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.hobsoft.symmetry.ReflectorException;
import org.hobsoft.symmetry.ui.Component;
import org.hobsoft.symmetry.ui.ComponentVisitor;
import org.hobsoft.symmetry.ui.Window;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.mockito.stubbing.Stubber;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

/**
 * Tests {@code XmlReflector}.
 */
public class XmlReflectorTest
{
	// ----------------------------------------------------------------------------------------------------------------
	// fields
	// ----------------------------------------------------------------------------------------------------------------

	private ComponentVisitor<XMLStreamWriter, XMLStreamException> visitor;
	
	private ExpectedException thrown = ExpectedException.none();
	
	// ----------------------------------------------------------------------------------------------------------------
	// public methods
	// ----------------------------------------------------------------------------------------------------------------

	@Before
	public void setUp()
	{
		visitor = mock(ComponentVisitor.class);
	}

	@Rule
	public ExpectedException getThrown()
	{
		return thrown;
	}

	// ----------------------------------------------------------------------------------------------------------------
	// tests
	// ----------------------------------------------------------------------------------------------------------------

	@Test
	public void getComponentTypeReturnsComponent()
	{
		XmlReflector reflector = new XmlReflector(mock(ComponentVisitor.class), anyContentType());
		
		Class<?> actual = reflector.getComponentType();
		
		assertThat(actual, is((Object) Component.class));
	}
	
	@Test
	public void getContentTypeReturnsContentType()
	{
		XmlReflector reflector = new XmlReflector(mock(ComponentVisitor.class), "x/y");
		
		String actual = reflector.getContentType();
		
		assertThat(actual, is("x/y"));
	}
	
	@Test
	public void reflectWithWindowWritesXml() throws XMLStreamException, ReflectorException, IOException
	{
		doStartElement("x").when(visitor).visit(any(Window.class), any(XMLStreamWriter.class));
		doEndElement().when(visitor).endVisit(any(Window.class), any(XMLStreamWriter.class));
		XmlReflector reflector = new XmlReflector(visitor, anyContentType());
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		
		reflector.reflect(new Window(), outputStream);
		
		assertThat(outputStream.toString("UTF-8"), is("<x></x>"));
	}
	
	@Test
	public void reflectWhenExceptionThrowsException() throws XMLStreamException, ReflectorException, IOException
	{
		XMLStreamException exception = new XMLStreamException();
		doThrow(exception).when(visitor).visit(any(Window.class), any(XMLStreamWriter.class));
		XmlReflector reflector = new XmlReflector(visitor, anyContentType());
		
		thrown.expect(ReflectorException.class);
		thrown.expectMessage("Error reflecting component");
		thrown.expectCause(is(exception));
		
		reflector.reflect(new Window(), mock(OutputStream.class));
	}
	
	// ----------------------------------------------------------------------------------------------------------------
	// private methods
	// ----------------------------------------------------------------------------------------------------------------

	private static String anyContentType()
	{
		return "_/_";
	}
	
	private static Stubber doStartElement(final String localName)
	{
		return doAnswer(new Answer<Object>()
		{
			@Override
			public Object answer(InvocationOnMock invocation) throws XMLStreamException
			{
				XMLStreamWriter writer = invocation.getArgumentAt(1, XMLStreamWriter.class);
				writer.writeStartElement(localName);
				return null;
			}
		});
	}
	
	private static Stubber doEndElement()
	{
		return doAnswer(new Answer<Object>()
		{
			@Override
			public Object answer(InvocationOnMock invocation) throws XMLStreamException
			{
				XMLStreamWriter writer = invocation.getArgumentAt(1, XMLStreamWriter.class);
				writer.writeEndElement();
				return null;
			}
		});
	}
}
