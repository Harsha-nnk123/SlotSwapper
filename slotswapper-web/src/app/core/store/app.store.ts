import { Injectable, signal } from '@angular/core';
import { EventSlot } from '../types/event-slot';

@Injectable({ providedIn: 'root' })
export class AppStore {
  user = signal<{id:number;name:string;email:string}|null>(null);
  events = signal<EventSlot[]>([]);
  swappable = signal<EventSlot[]>([]);
  incoming = signal<any[]>([]);
  outgoing = signal<any[]>([]);
}
