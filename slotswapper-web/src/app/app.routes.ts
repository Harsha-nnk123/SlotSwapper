import { Routes } from '@angular/router';
import { authGuard } from './core/guards/auth.guard';

export const routes: Routes = [
  { path: 'login', loadComponent: () => import('./pages/auth/login/login').then(m => m.Login) },
  { path: 'register', loadComponent: () => import('./pages/auth/register/register').then(m => m.Register) },

  {
    path: '',
    canActivate: [authGuard],
    children: [
      { path: 'dashboard', loadComponent: () => import('./pages/dashboard/dashboard/dashboard').then(m => m.Dashboard) },
      { path: 'events', loadComponent: () => import('./pages/events/event/event').then(m => m.Event) },
      { path: 'market', loadComponent: () => import('./pages/market/market/market').then(m => m.Market) },
      { path: 'requests', loadComponent: () => import('./pages/requests/request/request').then(m => m.Request) },
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' }
    ]
  },

  { path: '**', redirectTo: '' }
];
