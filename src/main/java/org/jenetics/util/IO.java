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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 *
 * Author:
 *     Franz Wilhelmstötter (franz.wilhelmstoetter@gmx.at)
 *     
 */
package org.jenetics.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.file.Path;

import javolution.xml.XMLObjectReader;
import javolution.xml.XMLObjectWriter;
import javolution.xml.stream.XMLStreamException;

/**
 * @author <a href="mailto:franz.wilhelmstoetter@gmx.at">Franz Wilhelmstötter</a>
 * @version $Id: org.eclipse.jdt.ui.prefs 421 2010-03-18 22:41:17Z fwilhelm $
 */
public abstract class IO {

	protected IO() {
	}
	
	public void write(final Object object, final String path)
		throws IOException
	{
		write(object, new File(path));
	}
	
	public void write(final Object object, final Path path) 
		throws IOException
	{
		write(object, path.toFile());
	}
	
	public void write(final Object object, final File file)
		throws IOException
	{
		try (final FileOutputStream out = new FileOutputStream(file)) {
			write(object, out);
		}
	}
	
	public abstract void write(final Object object, final OutputStream out) 
		throws IOException;	
	
	public <T> T read(final Class<T> type, final String path)
		throws IOException
	{
		try (final FileInputStream in = new FileInputStream(new File(path))) {
			return read(type, in);
		}
	}
		
	public <T> T read(final Class<T> type, final Path path)
		throws IOException
	{
		try (final FileInputStream in = new FileInputStream(path.toFile())) {
			return read(type, in);
		}
	}
	
	public <T> T read(final Class<T> type, final File file)
		throws IOException
	{
		try (final FileInputStream in = new FileInputStream(file)) {
			return read(type, in);
		}
	}	
	
	public abstract <T> T read(final Class<T> type, final InputStream in) 
		throws IOException;
	
	
	public static final IO xml = new IO() {
		
		@Override
		public void write(final Object object, final OutputStream out) 
			throws IOException 
		{
			try {
				final OutputStream nco = new NonClosableOutputStream(out);
				final XMLObjectWriter writer = XMLObjectWriter.newInstance(nco);
				try {
					writer.write(object);
				} finally {
					writer.reset();
				}
			} catch (XMLStreamException e) {
				throw new IOException(e);
			}
		}	
		
		@Override
		public <T> T read(final Class<T> type, final InputStream in) 
			throws IOException 
		{
			try {
				final InputStream nci = new NonClosableInputStream(in);
				final XMLObjectReader reader = XMLObjectReader.newInstance(nci);
				try {
					return type.cast(reader.read());
				} finally {
					reader.reset();
				}
			} catch (XMLStreamException e) {
				throw new IOException(e);
			}
		}		
	};
	
	public static IO object = new IO() {
		
		@Override
		public void write(final Object object, final OutputStream out) 
			throws IOException
		{
			final ObjectOutputStream oout = new ObjectOutputStream(out);
			oout.writeObject(object);
			out.flush();
		}
		
		@Override
		public <T> T read(final Class<T> type, final InputStream in) 
			throws IOException
		{
			final ObjectInputStream oin = new ObjectInputStream(in);
			try {
				return type.cast(oin.readObject());
			} catch (ClassNotFoundException e) {
				throw new IOException(e);
			}
		}
	};

	
	
	private static final class NonClosableOutputStream extends OutputStream {
		private final OutputStream _adoptee;
		
		public NonClosableOutputStream(final OutputStream adoptee) {
			_adoptee = adoptee;
		}

		@Override
		public void close() throws IOException {
			//Ignore close call.
			_adoptee.flush();
		}

		@Override
		public boolean equals(Object obj) {
			return _adoptee.equals(obj);
		}

		@Override
		public void flush() throws IOException {
			_adoptee.flush();
		}

		@Override
		public int hashCode() {
			return _adoptee.hashCode();
		}

		@Override
		public String toString() {
			return _adoptee.toString();
		}

		@Override
		public void write(byte[] b, int off, int len) throws IOException {
			_adoptee.write(b, off, len);
		}

		@Override
		public void write(byte[] b) throws IOException {
			_adoptee.write(b);
		}

		@Override
		public void write(int b) throws IOException {
			_adoptee.write(b);
		}
		
	}
	
	private static final class NonClosableInputStream extends InputStream {
		private final InputStream _adoptee;
		
		public NonClosableInputStream(final InputStream adoptee) {
			_adoptee = adoptee;
		}

		@Override
		public int available() throws IOException {
			return _adoptee.available();
		}

		@Override
		public void close() throws IOException {
		}

		@Override
		public void mark(int readlimit) {
			_adoptee.mark(readlimit);
		}

		@Override
		public boolean markSupported() {
			return _adoptee.markSupported();
		}

		@Override
		public int read() throws IOException {
			return _adoptee.read();
		}

		@Override
		public int read(byte[] b, int off, int len) throws IOException {
			return _adoptee.read(b, off, len);
		}

		@Override
		public int read(byte[] b) throws IOException {
			return _adoptee.read(b);
		}

		@Override
		public void reset() throws IOException {
			_adoptee.reset();
		}

		@Override
		public long skip(long n) throws IOException {
			return _adoptee.skip(n);
		}	
	}
	
}
