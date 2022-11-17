package com.deduplicator.storage;

import com.deduplicator.FileUtils;
import com.deduplicator.statics.Statistics;

import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents In Memory helper data structure to memorize all the traversed files
 * in the provided path
 *
 * @author dimov
 */
public final class InMemoryStorage implements AbstractStorage<File> {

	private final Map<String, FileMetadata> fileMap = new HashMap<>();

	/**
	 * Checks if a newly traversed file exists already on the map of collected files
	 *
	 * @param file file to be analyzed
	 * @return true if file is a duplicate
	 */
	@Override
	public boolean exists(File file) {
		if (Files.isSymbolicLink(file.toPath())) {
			return false;
		}
		return fileMap.values().stream()
				.anyMatch(metadata -> metadata.isEqualTo(file));
	}

	/**
	 * Save file to the map if it is not a duplicate
	 * Remove file if it is found to be a duplicate of another one
	 *
	 * @param file to be saved
	 */
	@Override
	public void saveOrDeduplicate(File file) {
		Statistics.incrementTotalExaminedFiles();
		if (!Files.isSymbolicLink(file.toPath())) {
			long size = FileMetadata.getSize(file);
			Statistics.incrementTotalExaminedSpace(size);
			if (size > 0) {
				if (exists(file)) {
					remove(file);
				} else {
					fileMap.put(file.getPath(), new FileMetadata(size, FileUtils.getExtension(file), file.toPath()));
				}
			}
		}
	}

	/**
	 * Remove file from the storage
	 *
	 * @param file
	 */
	private void remove(File file) {
		long size = FileMetadata.getSize(file);
		if (file.delete()) {
			Statistics.incrementDeletedFiles();
			Statistics.incrementFreedUpSpace(size);
			System.out.printf("%s deleted%n", file.getPath());
		} else {
			Statistics.incrementNonDeletableFiles();
			System.err.printf("Deletion of %s failed%n", file.getPath());
		}
	}

}
