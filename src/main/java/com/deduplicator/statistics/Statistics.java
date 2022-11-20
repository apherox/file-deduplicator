package com.deduplicator.statistics;

import lombok.AccessLevel;
import lombok.Getter;

/**
 * Collects statistics during application run
 *
 * @author dimov
 */
public final class Statistics {

	@Getter(AccessLevel.PRIVATE)
	private static Long deletedFilesCount = 0L;

	@Getter(AccessLevel.PRIVATE)
	private static Long nonDeletableFiles = 0L;

	@Getter(AccessLevel.PRIVATE)
	private static Long freedUpSpace = 0L;

	@Getter(AccessLevel.PRIVATE)
	private static Long totalExaminedFiles = 0L;

	@Getter(AccessLevel.PRIVATE)
	private static Long totalDirectoriesTraversed = 0L;

	@Getter(AccessLevel.PRIVATE)
	private static Long totalExaminedSpace = 0L;

	public static void incrementDeletedFiles() {
		deletedFilesCount++;
	}

	public static void incrementNonDeletableFiles() {
		nonDeletableFiles++;
	}

	public static void incrementFreedUpSpace(long size) {
		freedUpSpace += size;
	}

	public static void incrementTotalExaminedFiles() {
		totalExaminedFiles++;
	}

	public static void incrementTotalExaminedSpace(long size) {
		totalExaminedSpace += size;
	}

	public static void incrementDirectoriesTraversed() {
		totalDirectoriesTraversed++;
	}

	public static void printStatistics() {
		System.out.println("Statistics collected:");
		System.out.println("---------------------------------------------------------------");
		System.out.printf("Total examined space: %d bytes%n", getTotalExaminedSpace());
		System.out.printf("Total examined files: %d%n", getTotalExaminedFiles());
		System.out.printf("Directories traversed: %d%n", getTotalDirectoriesTraversed());
		System.out.printf("Total deleted files: %d%n", getDeletedFilesCount());
		System.out.printf("Total non-deletable files: %d%n", getNonDeletableFiles());
		System.out.printf("Total freed up memory: %d bytes%n", getFreedUpSpace());
		System.out.println("---------------------------------------------------------------");
	}
}
