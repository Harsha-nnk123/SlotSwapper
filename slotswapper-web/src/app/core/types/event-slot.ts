export type EventStatus = 'BUSY' | 'SWAPPABLE' | 'SWAP_PENDING';

export interface EventSlot {
  id: number;
  ownerId: number;
  title: string;
  startTime: string;
  endTime: string;
  status: EventStatus;
}
