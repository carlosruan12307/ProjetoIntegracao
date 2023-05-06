import { LoginGuardGuard } from './guard/login-guard.guard';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import {HttpClientModule} from '@angular/common/http'
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './components/home/home.component';
import { MainComponent } from './pages/main/main.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { GoogleloginComponent } from './components/googlelogin/googlelogin.component';
import { TesteComponent } from './components/teste/teste.component';
import { ModalMoviesComponent } from './components/modal-movies/modal-movies.component';
import { OffcanvasUserComponent } from './components/offcanvas-user/offcanvas-user.component';
import { FooterComponent } from './components/footer/footer.component';
import { CardComponent } from './components/card/card.component';
import { FormLoginComponent } from './components/form-login/form-login.component';
import { FormSignUpComponent } from './components/form-sign-up/form-sign-up.component';

@NgModule({
  declarations: [
    AppComponent,

    HomeComponent,
     MainComponent,
     NavbarComponent,
     GoogleloginComponent,
     TesteComponent,
     ModalMoviesComponent,
     OffcanvasUserComponent,
     FooterComponent,
     CardComponent,
     FormLoginComponent,
     FormSignUpComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [LoginGuardGuard],
  bootstrap: [AppComponent]
})
export class AppModule { }
