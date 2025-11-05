import { Component, OnInit } from '@angular/core';
import { EventService } from '../../../core/services/event.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-events',
  templateUrl: './event.html',
  imports:[FormsModule,CommonModule]
})
export class Event implements OnInit {
  events: any[] = [];

  form = {
    title: '',
    startTime: '',
    endTime: '',
    status: 'BUSY'
  };

  constructor(private eventsSvc: EventService) {}

  ngOnInit() {
    this.load();
  }

  load() {
    this.eventsSvc.myEvents().subscribe(r => this.events = r);
  }

  save() {
    const data = {
      ...this.form,
      startTime: new Date(this.form.startTime).toISOString(),
      endTime: new Date(this.form.endTime).toISOString()
    };

    this.eventsSvc.create(data).subscribe(() => {
      this.form = { title: '', startTime: '', endTime: '', status: 'BUSY' };
      this.load();
    });
  }
}
