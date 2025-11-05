import { Component, OnInit } from '@angular/core';
import { SwapService } from '../../../core/services/swap.service';
import { AppStore } from '../../../core/store/app.store';
import { CommonModule } from '@angular/common';

@Component({
  standalone: true,
  selector: 'app-requests',
  imports: [CommonModule],
  templateUrl: './request.html'
})
export class Request implements OnInit {

  constructor(public store: AppStore, private swap: SwapService) {}

  ngOnInit() {
    this.refresh();
  }

  refresh() {
    this.swap.incoming().subscribe(r => this.store.incoming.set(r));
    this.swap.outgoing().subscribe(r => this.store.outgoing.set(r));
  }

  respond(id: number, accept: boolean) {
    this.swap.respond(id, accept).subscribe(() => this.refresh());
  }
}
