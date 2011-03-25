/*
 * Java Genetic Algorithm Library (@!identifier!@).
 * Copyright (c) @!year!@ Franz Wilhelmstötter
 *  
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.	See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 *
 * Author:
 * 	 Franz Wilhelmstötter (franz.wilhelmstoetter@gmx.at)
 * 	 
 */
package org.jenetics.util;

import java.util.Iterator;
import java.util.ListIterator;

/**
 * Mutable ordered sequence.
 * 
 * @see ISeq
 * 
 * @author <a href="mailto:franz.wilhelmstoetter@gmx.at">Franz Wilhelmstötter</a>
 * @version $Id$
 */
public interface MSeq<T> extends Seq<T> {
	
	/**
	 * Set the {@code value} at the given {@code index}.
	 * 
	 * @param index the index of the new value.
	 * @param value the new value.
	 * @throws ArrayIndexOutOfBoundsException if the index is out of range 
	 * 		  {@code (index < 0 || index >= size())}.
	 */
	public void set(final int index, final T value);
	
	/**
	 * Set all array elements to the given {@code value}.
	 *
	 * @param value {@code value} to fill this array with.
	 * @return {@code this} array.
	 */
	public MSeq<T> fill(final T value);
	
	/**
	 * Fills the array with values of the given iterator.
	 * 
	 * @param it the iterator of the values to fill this array.
	 * @return {@code this} array.
	 */
	public MSeq<T> fill(final Iterator<? extends T> it);
	
	/**
	 * Fill the array with the given values.
	 * 
	 * @param values the first initial values of this array
	 * @return {@code this} array.
	 */
	public MSeq<T> fill(final T[] values);
	
	/**
	 * Fill the array with values generated by the given factory.
	 * 
	 * @param factory the value factory.
	 * @return {@code this} array.
	 * @throws NullPointerException if the given {@code factory} is {@code null}.
	 */
	public MSeq<T> fill(final Factory<? extends T> factory);	 
	
	/**
	 * Returns a list iterator over the elements in this sequence (in proper 
	 * sequence). 
	 * 
	 * @return a list iterator over the elements in this list (in proper 
	 *           sequence)
	 */
	public ListIterator<T> listIterator();
	
	/**
	 * Return a read-only projection of this sequence.
	 * 
	 * @return a read-only projection of this sequence
	 */
	public ISeq<T> toISeq();
	
}




