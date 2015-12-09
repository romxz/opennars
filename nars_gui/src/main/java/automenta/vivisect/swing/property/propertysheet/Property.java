/**
 * L2FProd Common v9.2 License.
 *
 * Copyright 2005 - 2009 L2FProd.com
 *
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
package automenta.vivisect.swing.property.propertysheet;

import java.beans.PropertyChangeListener;
import java.io.Serializable;


/**
 * Property. <br>
 * Component of a PropertySheet, based on the java.beans.PropertyDescriptor for
 * easy wrapping of beans in PropertySheet.
 */
public interface Property extends Serializable, Cloneable {

	String getName();

	String getDisplayName();

	String getShortDescription();

	Class<?> getType();

	Object getValue();

	void setValue(Object value);

	boolean isEditable();

	String getCategory();

	void readFromObject(Object object);

	void writeToObject(Object object);

	void addPropertyChangeListener(PropertyChangeListener listener);

	void removePropertyChangeListener(PropertyChangeListener listener);

	Object clone();

	Property getParentProperty();

	Property[] getSubProperties();
}
