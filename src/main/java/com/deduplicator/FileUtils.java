package com.deduplicator;

import java.io.File;
import java.util.Optional;

/**
 * Provides helper methods for hashing and extension retrieval
 *
 * @author dimov
 */
public class FileUtils {

	/**
	 * Get extension of a file
	 *
	 * @param file to get extension of
	 * @return file extension
	 */
	public static String getExtension(File file) {
		return Optional.ofNullable(file)
				.map(File::getName)
				.filter(f -> f.contains("."))
				.map(f -> f.substring(f.lastIndexOf(".") + 1))
				.orElse("");
	}
}
