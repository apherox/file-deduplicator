package com.deduplicator.filededuplicator;

import com.deduplicator.FileUtils;
import com.deduplicator.storage.FileMetadata;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;

/**
 * @author dimov
 */
public class FileUtilsTest {

	private final String fileParentDir = "tests";

	@Test
	public void testGetExtensionReturnsCorrectExtension() {
		// Given
		final String fileName = "test.jpg";
		final Path relativePathToFile = Path.of(fileParentDir, fileName);

		// when then
		Assertions.assertEquals("jpg", FileUtils.getExtension(relativePathToFile.toFile()));
	}

	@Test
	public void testGetExtensionReturnsEmptyStringForNoExtension() {
		// Given
		final String fileName = "test";
		final Path relativePathToFile = Path.of(fileParentDir, fileName);

		// when then
		Assertions.assertEquals("", FileUtils.getExtension(relativePathToFile.toFile()));
	}

	@Test
	public void testGetExtensionReturnsEmptyStringForNonExistentFile() {
		// given when then
		Assertions.assertEquals("", FileUtils.getExtension(null));
	}

	@Test
	public void testGetExtensionReturnsCorrectExtensionForMultipleDotsInName() {
		// Given
		final String fileName = "test.1.jpg";
		final Path relativePathToFile = Path.of(fileParentDir, fileName);

		// when then
		Assertions.assertEquals("jpg", FileUtils.getExtension(relativePathToFile.toFile()));
	}
}
