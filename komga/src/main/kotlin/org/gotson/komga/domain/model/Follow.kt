package org.gotson.komga.domain.model

import java.time.LocalDateTime

data class Follow(
  val id: String,
  val libraryId: String,
  val url: String,
  val title: String? = null,
  val enabled: Boolean = true,
  val chapterFrom: Double? = null,
  val chapterTo: Double? = null,
  val addedAt: LocalDateTime = LocalDateTime.now(),
  val lastCheckedAt: LocalDateTime? = null,
)
