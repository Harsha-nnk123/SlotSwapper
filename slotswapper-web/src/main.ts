import { bootstrapApplication } from '@angular/platform-browser';
import { provideRouter } from '@angular/router';
import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { routes } from './app/app.routes';
import { RootComponent } from './app/layout/root/root';
import { authInterceptor } from './app/core/interceptors/auth.interceptors';

bootstrapApplication(RootComponent, {
  providers: [
    provideRouter(routes),
    provideHttpClient(withInterceptors([authInterceptor]))
  ]
});
