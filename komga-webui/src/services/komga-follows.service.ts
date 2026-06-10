import {AxiosInstance} from 'axios'
import {FollowCreationDto, FollowDto, FollowUpdateDto} from '@/types/komga-follows'

const API_FOLLOWS = '/api/v1/downloads/follows'

export default class KomgaFollowsService {
  private http: AxiosInstance

  constructor(http: AxiosInstance) {
    this.http = http
  }

  async getAll(libraryId: string): Promise<FollowDto[]> {
    return (await this.http.get(`${API_FOLLOWS}/${libraryId}`)).data
  }

  async add(libraryId: string, dto: FollowCreationDto): Promise<FollowDto> {
    return (await this.http.post(`${API_FOLLOWS}/${libraryId}`, dto)).data
  }

  async update(libraryId: string, id: string, dto: FollowUpdateDto): Promise<FollowDto> {
    return (await this.http.patch(`${API_FOLLOWS}/${libraryId}/${id}`, dto)).data
  }

  async remove(libraryId: string, id: string): Promise<void> {
    await this.http.delete(`${API_FOLLOWS}/${libraryId}/${id}`)
  }

  async checkNow(libraryId: string): Promise<void> {
    await this.http.post(`${API_FOLLOWS}/${libraryId}/check-now`)
  }
}
