import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { EventSlot } from '../types/event-slot';

@Injectable({ providedIn: 'root' })
export class EventService {
  private base = `${environment.api}/events`;
  constructor(private http: HttpClient) {}
  myEvents(){ return this.http.get<EventSlot[]>(this.base); }
  create(data:any){ return this.http.post<EventSlot>(this.base, data); }
  update(id:number, data:any){ return this.http.put<EventSlot>(`${this.base}/${id}`, data); }
  delete(id:number){ return this.http.delete(`${this.base}/${id}`); }
  swappable(){ return this.http.get<EventSlot[]>(`${this.base}/swoppable`); }
  market() {
    return this.http.get<any[]>(`${this.base}/swappable`);
  }
}
