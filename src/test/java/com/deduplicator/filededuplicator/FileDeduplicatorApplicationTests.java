package com.deduplicator.filededuplicator;

import com.deduplicator.storage.InMemoryStorage;
import com.deduplicator.walker.FileDeduplicatorImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertLinesMatch;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FileDeduplicatorApplicationTests {

	@Test
	void givenTempDirectory_whenWriteToFile_thenContentIsCorrect(@TempDir Path tempDir) throws IOException {
		// Given
		Path numbers1 = tempDir.resolve("numbers1.txt");
		Path numbers2 = tempDir.resolve("numbers2.txt");

		// When
		List<String> lines = Arrays.asList("1", "2", "3");
		Files.write(numbers1, lines);
		Files.write(numbers2, lines);

		// Then
		assertAll(
				() -> assertTrue(Files.exists(numbers1), "File should exist"),
				() -> assertTrue(Files.exists(numbers2), "File should exist"),
				() -> assertLinesMatch(lines, Files.readAllLines(numbers1)),
				() -> assertLinesMatch(lines, Files.readAllLines(numbers2)));
	}

	@Test
	void givenTempDirectory_whenTwoDifferentFilesExist_thenDuplicatorDoesNotDeleteOne(@TempDir Path tempDir) throws IOException {
		// Given
		Path numbers1 = tempDir.resolve("numbers1.txt");
		Path numbers2 = tempDir.resolve("numbers2.txt");

		List<String> lines1 = Arrays.asList("1", "2", "3");
		List<String> lines2 = Arrays.asList("1", "2", "3", "4");

		Files.write(numbers1, lines1);
		Files.write(numbers2, lines2);

		assertAll(() -> assertTrue(Files.list(tempDir).count() == 2));

		// When
		new FileDeduplicatorImpl(new InMemoryStorage()).deduplicate(tempDir);

		// Then
		assertAll(() -> assertTrue(Files.list(tempDir).count() == 2));
	}

	@Test
	void givenTempDirectory_whenTwoEqualFilesExist_thenDuplicateIsRemoved(@TempDir Path tempDir) throws IOException {
		// Given
		Path numbers1 = tempDir.resolve("numbers1.txt");
		Path numbers2 = tempDir.resolve("numbers2.txt");

		List<String> lines = Arrays.asList("1", "2", "3");
		Files.write(numbers1, lines);
		Files.write(numbers2, lines);

		assertAll(() -> assertTrue(Files.list(tempDir).count() == 2));

		// When
		new FileDeduplicatorImpl(new InMemoryStorage()).deduplicate(tempDir);

		// Then
		assertAll(() -> assertTrue(Files.list(tempDir).count() == 1));
	}

	@Test
	void givenTempDirectory_whenThreeEqualFilesExistInDifferentDirs_thenDuplicatesAreRemoved(@TempDir Path tempDir) throws IOException {
		// Given
		Path numbers1 = tempDir.resolve("numbers1.txt");
		Path numbers2 = tempDir.resolve("numbers2.txt");
		Path subdir = Files.createDirectories(tempDir.resolve("subdir"));
		Path numbers3 = subdir.resolve("numbers3.txt");

		List<String> lines = Arrays.asList("1", "2", "3");
		Files.write(numbers1, lines);
		Files.write(numbers2, lines);
		Files.write(numbers3, lines);

		assertAll(
				() -> assertTrue(Files.list(tempDir).count() == 3),
				() -> assertTrue(Files.list(subdir).count() == 1));

		// When
		new FileDeduplicatorImpl(new InMemoryStorage()).deduplicate(tempDir);

		// Then
		assertAll(
				() -> assertTrue(Files.list(tempDir).count() == 2),
				() -> assertTrue(Files.list(subdir).count() == 0));
	}
}
