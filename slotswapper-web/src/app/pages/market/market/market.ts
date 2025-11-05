import { Component, inject, OnInit } from '@angular/core';
import { EventService } from '../../../core/services/event.service';
import { SwapService } from '../../../core/services/swap.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-market',
  standalone: true,
  templateUrl: './market.html',
  imports: [CommonModule,FormsModule]
})
export class Market implements OnInit {
  private events = inject(EventService);
  private swaps = inject(SwapService);

  mySlots: any[] = [];
  swappable: any[] = [];
  selectedMySlot: number | null = null;

  ngOnInit() {
    this.events.myEvents().subscribe(r => this.mySlots = r.filter(e => e.status === 'SWAPPABLE'));
    this.events.market().subscribe(r => this.swappable = r);
  }

  request(theirSlotId: number) {
    if (!this.selectedMySlot) return alert("Select your slot first");

    this.swaps.createRequest(this.selectedMySlot, theirSlotId).subscribe(() => {
      alert("Swap request sent!");
      this.ngOnInit();
    });
  }
}
