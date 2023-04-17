import { LoginGuardGuard } from './guard/login-guard.guard';
import { MainComponent } from './pages/main/main.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FormLoginComponent } from './components/form-login/form-login.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { TesteComponent } from './components/teste/teste.component';

const routes: Routes = [
  { path: '', component: FormLoginComponent },
  { path: 'teste', component: TesteComponent },
  { path: 'home', component: MainComponent, canActivate: [LoginGuardGuard] },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
