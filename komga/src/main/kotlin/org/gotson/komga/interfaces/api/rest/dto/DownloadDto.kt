package org.gotson.komga.interfaces.api.rest.dto

import jakarta.validation.constraints.NotBlank
import java.time.LocalDateTime

data class DownloadDto(
  val id: String,
  val sourceUrl: String,
  val title: String?,
  val status: String,
  val progressPercent: Int,
  val currentChapter: Int,
  val totalChapters: Int?,
  val libraryId: String?,
  val errorMessage: String?,
  val createdDate: LocalDateTime,
  val startedDate: LocalDateTime?,
  val completedDate: LocalDateTime?,
  val priority: Int,
  val retryCount: Int,
  val maxRetries: Int,
  val resumeAt: LocalDateTime?,
)

data class DownloadCreateDto(
  val sourceUrl: String,
  val title: String?,
  val libraryId: String?,
  val priority: Int = 5,
)

data class DownloadActionDto(
  val action: String, // pause, resume, cancel, retry
)

data class FollowTxtDto(
  val libraryId: String,
  val libraryName: String,
  val content: String,
)

data class FollowTxtUpdateDto(
  val content: String,
)

data class SchedulerSettingsDto(
  val enabled: Boolean,
  val intervalHours: Int,
  val scheduleMode: String,
  val checkTime: String?,
  val lastCheckTime: String?,
)

data class SchedulerSettingsUpdateDto(
  val enabled: Boolean,
  val intervalHours: Int,
  val scheduleMode: String = "interval",
  val checkTime: String? = null,
)

data class ClearResultDto(
  val deletedCount: Int,
  val status: String,
  val message: String,
)

data class ChapterCheckResultDto(
  val url: String,
  val mangaId: String?,
  val title: String?,
  val apiChapterCount: Int,
  val downloadedChapterCount: Int,
  val filesystemChapterCount: Int,
  val newChaptersEstimate: Int,
  val needsDownload: Boolean,
  val error: String?,
)

data class ChapterCheckSummaryDto(
  val totalManga: Int,
  val checkedCount: Int,
  val needsDownloadCount: Int,
  val upToDateCount: Int,
  val errorCount: Int,
  val results: List<ChapterCheckResultDto>,
  val durationMs: Long,
)

data class FollowDto(
  val id: String,
  val libraryId: String,
  val url: String,
  val title: String?,
  val enabled: Boolean,
  val chapterFrom: Double?,
  val chapterTo: Double?,
  val addedAt: LocalDateTime,
  val lastCheckedAt: LocalDateTime?,
)

data class FollowCreationDto(
  @field:NotBlank val url: String,
  val title: String? = null,
  val chapterFrom: Double? = null,
  val chapterTo: Double? = null,
)

data class FollowUpdateDto(
  val title: String? = null,
  val enabled: Boolean? = null,
  val chapterFrom: Double? = null,
  val chapterTo: Double? = null,
  val clearChapterFrom: Boolean = false,
  val clearChapterTo: Boolean = false,
)
