import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  template: `
  <div class="grid place-items-center min-h-screen bg-gradient-to-br from-blue-50 to-slate-50">
    <form class="card p-8 w-full max-w-md space-y-4" (ngSubmit)="submit()">
      <h1 class="text-2xl font-semibold text-center text-blue-700">Create account</h1>
      <input class="input" name="name" [(ngModel)]="name" placeholder="Name" required>
      <input class="input" type="email" name="email" [(ngModel)]="email" placeholder="Email" required>
      <input class="input" type="password" name="password" [(ngModel)]="password" placeholder="Password" required>
      <button class="btn btn-primary w-full">Register</button>
      <div class="text-center text-sm">
        Already have an account? <a routerLink="/login" class="text-blue-700 hover:underline">Sign in</a>
      </div>
    </form>
  </div>
  `
})
export class Register {
  name=''; email=''; password='';
  constructor(private auth:AuthService, private router:Router){}
  submit() {
  this.auth.register({ name:this.name, email:this.email, password:this.password })
    .subscribe(() => this.router.navigate(['/login']));
}

}
