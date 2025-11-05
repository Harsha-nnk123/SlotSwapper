import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private base = `${environment.api}/auth`;
  constructor(private http: HttpClient) {}
  login(data:{email:string;password:string}) { return this.http.post<any>(`${this.base}/login`, data); }
  register(data:{name:string;email:string;password:string}) { return this.http.post(`${this.base}/register`, data); }
}
