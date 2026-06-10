package org.gotson.komga.domain.service

import io.github.oshai.kotlinlogging.KotlinLogging
import org.gotson.komga.domain.model.Follow
import org.gotson.komga.domain.model.Library
import org.gotson.komga.domain.persistence.FollowRepository
import org.gotson.komga.domain.persistence.LibraryRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.UUID

private val logger = KotlinLogging.logger {}

@Service
class FollowService(
  private val followRepository: FollowRepository,
  private val libraryRepository: LibraryRepository,
) {
  fun getAll(libraryId: String): List<Follow> = followRepository.findAllByLibraryId(libraryId)

  fun findById(id: String): Follow? = followRepository.findById(id)

  fun clearLastChecked(id: String) {
    val existing = followRepository.findById(id) ?: return
    followRepository.update(existing.copy(lastCheckedAt = null))
  }

  fun add(
    libraryId: String,
    url: String,
    title: String? = null,
    chapterFrom: Double? = null,
    chapterTo: Double? = null,
  ): Follow {
    if (followRepository.existsByLibraryIdAndUrl(libraryId, url)) {
      throw IllegalArgumentException("URL already in follow list for this library: $url")
    }
    val follow = Follow(
      id = UUID.randomUUID().toString(),
      libraryId = libraryId,
      url = url,
      title = title,
      enabled = true,
      chapterFrom = chapterFrom,
      chapterTo = chapterTo,
      addedAt = LocalDateTime.now(),
    )
    followRepository.insert(follow)
    logger.info { "Added follow entry: ${follow.id} — $url" }
    return follow
  }

  fun update(
    id: String,
    title: String? = null,
    enabled: Boolean? = null,
    chapterFrom: Double? = null,
    chapterTo: Double? = null,
    clearChapterFrom: Boolean = false,
    clearChapterTo: Boolean = false,
  ): Follow {
    val existing = followRepository.findById(id)
      ?: throw NoSuchElementException("Follow entry not found: $id")
    val updated = existing.copy(
      title = title ?: existing.title,
      enabled = enabled ?: existing.enabled,
      chapterFrom = if (clearChapterFrom) null else (chapterFrom ?: existing.chapterFrom),
      chapterTo = if (clearChapterTo) null else (chapterTo ?: existing.chapterTo),
    )
    followRepository.update(updated)
    return updated
  }

  fun delete(id: String) {
    followRepository.delete(id)
    logger.info { "Deleted follow entry: $id" }
  }

  fun importFromFollowTxt(library: Library) {
    val followFile = library.path.resolve("follow.txt").toFile()
    if (!followFile.exists()) return

    val urls = followFile.readLines()
      .map { it.trim() }
      .filter { it.isNotEmpty() && !it.startsWith("#") }

    if (urls.isEmpty()) return

    var imported = 0
    for (url in urls) {
      if (!followRepository.existsByLibraryIdAndUrl(library.id, url)) {
        try {
          followRepository.insert(
            Follow(
              id = UUID.randomUUID().toString(),
              libraryId = library.id,
              url = url,
              addedAt = LocalDateTime.now(),
            ),
          )
          imported++
        } catch (e: Exception) {
          logger.warn(e) { "Failed to import URL from follow.txt: $url" }
        }
      }
    }

    if (imported > 0) {
      logger.info { "Imported $imported entries from follow.txt for library '${library.name}'" }
    }
  }

  fun importAllLibraries() {
    libraryRepository.findAll().forEach { library ->
      try {
        importFromFollowTxt(library)
      } catch (e: Exception) {
        logger.warn(e) { "Failed to import follow.txt for library '${library.name}'" }
      }
    }
  }
}
