package com.deduplicator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Optional;

/**
 * Provides helper methods for hashing and extension retrieval
 *
 * @author dimov
 */
public class FileUtils {

	/**
	 * Create Base64Encoded SHA256 hash of file content
	 *
	 * @param path to file
	 * @return String representation of Base64 encoded SHA256 hash
	 * @throws IOException              if file can not be read
	 * @throws NoSuchAlgorithmException if algorithm does not exist
	 */
	public static String hash(Path path) throws IOException, NoSuchAlgorithmException {
		byte[] fileContents = Files.readAllBytes(path);
		byte[] digest = MessageDigest.getInstance("SHA-256").digest(fileContents);
		return Base64.getEncoder().encodeToString(digest);
	}

	/**
	 * Get extensaion of a file
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
