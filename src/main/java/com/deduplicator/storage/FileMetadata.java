package com.deduplicator.storage;

import com.deduplicator.FileUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;

/**
 * Metadata of a given analyzed file
 *
 * @author dimov
 */
@AllArgsConstructor
public class FileMetadata {

	/**
	 * Size of file
	 **/
	@Getter
	private long size;

	/**
	 * File extension
	 */
	private String extension;

	/**
	 * Base64 encoded SHA256 hash of file content
	 */
	@Setter
	private String sha256Hash;

	/**
	 * Path to the file on storage location
	 */
	private Path path;

	private FileMetadata(long size, String extension) {
		this.size = size;
		this.extension = extension;
	}

	public FileMetadata(long size, String extension, Path path) {
		this(size, extension);
		this.path = path;
	}

	/**
	 * Comparison method with another file
	 * Files are considered to be equal if and only if they have same size, same extension and same content
	 *
	 * @param file file to be compared to
	 * @return true if both files are equal
	 */
	boolean isEqualTo(File file) {
		boolean result = this.size == getSize(file) && this.extension.equals(FileUtils.getExtension(file));
		if (result) {
			String hash;
			try {
				hash = FileUtils.hash(file.toPath());
			} catch (IOException | NoSuchAlgorithmException e) {
				System.err.println(e.getMessage());
				return false;
			}
			return this.getSha256Hash().equals(hash);
		}
		return false;
	}

	/**
	 * Return size of file in bytes
	 *
	 * @param file to get the size of
	 * @return size of file in bytes
	 */
	public static long getSize(File file) {
		try {
			return Files.size(file.toPath());
		} catch (IOException e) {
			System.err.printf("File %s could not be read%n", file.getPath());
		}
		return 0;
	}

	/**
	 * Creates Base64 encoded hash of the content of the file found on {@link Path}
	 *
	 * @return String representation of Base64 encoded SHA256 hash of content of the file
	 */
	private String getSha256Hash() {
		if (StringUtils.isEmpty(sha256Hash)) {
			try {
				sha256Hash = FileUtils.hash(path);
			} catch (IOException | NoSuchAlgorithmException e) {
				System.err.println(e.getMessage());
				return "";
			}
		}
		return sha256Hash;
	}


}
