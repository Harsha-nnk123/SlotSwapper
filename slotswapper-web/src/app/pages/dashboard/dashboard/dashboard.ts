import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AppStore } from '../../../core/store/app.store';
import { EventService } from '../../../core/services/event.service';

@Component({
  standalone: true,
  imports: [CommonModule],
  templateUrl: './dashboard.html'
})
export class Dashboard {
  constructor(public store:AppStore, private ev:EventService){ this.load(); }
  load(){ this.ev.myEvents().subscribe(r=> this.store.events.set(r)); }
}
