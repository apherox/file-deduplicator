package com.deduplicator.walker;

import com.deduplicator.BlackList;
import com.deduplicator.statics.Statistics;
import com.deduplicator.storage.AbstractStorage;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.nio.file.Path;
import java.util.Arrays;

/**
 * Implements the {@link FileDeduplicator} interface
 * Provides
 *
 * @author dimov
 */
@RequiredArgsConstructor
public final class FileDeduplicatorImpl implements FileDeduplicator {

	private final AbstractStorage storage;

	/**
	 * Traverse directory recursively and check for duplicates
	 * If file is duplicate then delete it
	 *
	 * @param directory to check if it contains duplicate files
	 */
	@Override
	public void deduplicate(Path directory) {
		File file = directory.toFile();
		if (validate(file)) {
			deduplicateInternal(file);
		}
	}

	/**
	 * Validates if a file is blacklisted or not
	 *
	 * @param file to be validated
	 * @return true if a file is a directory and is not blacklisted
	 */
	private boolean validate(File file) {
		if (!file.isDirectory()) {
			System.err.println("Deduplicate operation invoked on a single file");
			return false;
		} else {
			if (BlackList.isBlackListed(file.getName())) {
				System.err.println("Deduplicate operation invoked on a forbidden directory");
				return false;
			} else {
				return true;
			}
		}
	}

	/**
	 * Traverse directory recursively and add files to a {@link java.util.Map}
	 *
	 * @param file path provided by user to find duplicates within
	 */
	private void deduplicateInternal(File file) {
		if (file.isDirectory()) {
			Statistics.incrementDirectoriesTraversed();
			Arrays.stream(file.listFiles()).forEach(this::deduplicateInternal);
		} else {
			storage.saveOrDeduplicate(file);
		}
	}

}
