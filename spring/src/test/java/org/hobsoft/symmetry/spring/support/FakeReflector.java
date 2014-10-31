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
package org.hobsoft.symmetry.spring.support;

import org.hobsoft.symmetry.Reflector;

/**
 * Fake reflector for use by integration tests.
 */
public class FakeReflector implements Reflector<FakeComponent>
{
	// ----------------------------------------------------------------------------------------------------------------
	// Reflector methods
	// ----------------------------------------------------------------------------------------------------------------

	@Override
	public Class<FakeComponent> getComponentType()
	{
		return FakeComponent.class;
	}
	
	@Override
	public String getContentType()
	{
		return "text/html";
	}
}
