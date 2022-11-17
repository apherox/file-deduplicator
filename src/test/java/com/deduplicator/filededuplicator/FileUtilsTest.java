package com.deduplicator.filededuplicator;

import com.deduplicator.FileUtils;
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

	@Test
	public void testHashMethodReturnCorrectSHA256Hash() throws IOException, NoSuchAlgorithmException {
		// Given
		final String fileName = "test.txt";
		final Path relativePathToFile = Paths.get("src", "test", "resources", "tests", fileName);

		// when then
		Assertions.assertEquals("AZVxUAYZI3IjpH6pvI33ZZSAZYILBaoj1seuFLbE4PY=", FileUtils.hash(relativePathToFile));
	}

	@Test
	public void testHashMethodReturnSameSHA256HashForSameContent() throws IOException, NoSuchAlgorithmException {
		// Given
		final String fileName = "test.txt";
		final Path relativePathToFile = Paths.get("src", "test", "resources", "tests", fileName);

		final String fileName2 = "test2.txt";
		final Path relativePathToFile2 = Paths.get("src", "test", "resources", "tests", fileName2);

		// when then
		Assertions.assertEquals(FileUtils.hash(relativePathToFile), FileUtils.hash(relativePathToFile2));
	}
}
