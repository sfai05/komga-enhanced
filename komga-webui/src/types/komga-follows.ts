export interface FollowDto {
  id: string
  libraryId: string
  url: string
  title: string | null
  enabled: boolean
  chapterFrom: number | null
  chapterTo: number | null
  addedAt: string
  lastCheckedAt: string | null
}

export interface FollowCreationDto {
  url: string
  title?: string
  chapterFrom?: number
  chapterTo?: number
}

export interface FollowUpdateDto {
  title?: string
  enabled?: boolean
  chapterFrom?: number | null
  chapterTo?: number | null
  clearChapterFrom?: boolean
  clearChapterTo?: boolean
}
