import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  template: `
  <div class="grid place-items-center min-h-screen bg-blue-50">
    <form class="card p-6 w-full max-w-md space-y-4" (ngSubmit)="submit()">
      <h1 class="text-xl font-semibold text-center text-blue-700">Login</h1>

      <input class="input" type="email" [(ngModel)]="email" name="email" placeholder="Email" required>
      <input class="input" type="password" [(ngModel)]="password" name="password" placeholder="Password" required>

      <button type="submit" class="btn btn-primary w-full">Login</button>

      <div class="text-center text-sm">
        Don’t have an account? <a routerLink="/register" class="text-blue-700">Register</a>
      </div>
    </form>
  </div>
  `
})
export class Login {
  email = '';
  password = '';

  constructor(private auth:AuthService, private router: Router) {}

  submit() {
    this.auth.login({ email:this.email, password:this.password }).subscribe(res => {
      localStorage.setItem('token', res.token);
      this.router.navigate(['/dashboard']);
    });
  }
}
