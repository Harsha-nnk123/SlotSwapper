import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';

@Injectable({ providedIn: 'root' })
export class SwapService {
  private http = inject(HttpClient);
  private base = environment.api + '/api';

  incoming() {
    return this.http.get<any[]>(`${this.base}/requests/incoming`);
  }

  outgoing() {
    return this.http.get<any[]>(`${this.base}/requests/outgoing`);
  }

  request(mySlotId: number, theirSlotId: number) {
    return this.http.post<any>(`${this.base}/swap-request`, { mySlotId, theirSlotId });
  }

  respond(id: number, accept: boolean) {
    return this.http.post<any>(`${this.base}/swap-response/${id}`, { accept });
  }
  createRequest(mySlotId: number, theirSlotId: number) {
    return this.http.post(`${this.base}/swap-request`, { mySlotId, theirSlotId });
  }
}
