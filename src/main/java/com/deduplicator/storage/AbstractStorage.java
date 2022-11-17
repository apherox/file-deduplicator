package com.deduplicator.storage;

/**
 * Abstract storage interface
 *
 * @author dimov
 */
public interface AbstractStorage<T> {

	boolean exists(T t);

	void saveOrDeduplicate(T t);

}
