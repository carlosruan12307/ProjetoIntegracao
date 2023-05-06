import { LoginGuardGuard } from './guard/login-guard.guard';
import { MainComponent } from './pages/main/main.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { TesteComponent } from './components/teste/teste.component';
import { AppComponent } from './app.component';
import { FormSignUpComponent } from './components/form-sign-up/form-sign-up.component';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'movies', component: MainComponent, canActivate: [LoginGuardGuard] },
  { path: 'series', component: MainComponent, canActivate: [LoginGuardGuard] },
  {
    path: 'cadastro',
    component: HomeComponent,
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
