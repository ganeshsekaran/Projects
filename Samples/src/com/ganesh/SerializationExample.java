package com.ganesh;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class SerializationExample implements Serializable {

	private void writeObject(ObjectOutputStream os) throws IOException {
		os.defaultWriteObject();
	}

	private void readObject(ObjectInputStream io) throws IOException,
			ClassNotFoundException {
		io.defaultReadObject();
	}

}
